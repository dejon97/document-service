package hello;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import util.Util;

//https://spring.io/blog/2015/06/08/cors-support-in-spring-framework

@RestController
public class HelloController {
	
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
	@RequestMapping("/batchclasses")
	public String getBatchClassList() {
		
		HttpHost httpHost = Util.getHttpHost();
		
		String url = "http://ephesoft.dev.promontech.com/dcma/rest/getBatchClassList";
		HttpGet request = new HttpGet(url);
		
		CloseableHttpClient httpClient = Util.getHttpClient(httpHost);
				
		StringBuffer result = new StringBuffer();
				
		try {

			HttpClientContext localContext = Util.getLocalContent(httpHost);
			
			CloseableHttpResponse response = httpClient.execute(httpHost, request, localContext);
	
			System.out.println("Response Code : "
			                + response.getStatusLine().getStatusCode());
	
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
				
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
            try {
            	httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		
		System.out.println(result.toString());
		
		return result.toString();
	}
	
	@CrossOrigin
	//@CrossOrigin(origins = "http://localhost")
	//@RequestMapping(value = "/documents", method = RequestMethod.POST)
	@PostMapping("/documents")
	public String uploadDocument(@RequestParam("file") MultipartFile[] fileList, RedirectAttributes redirectAttrbutes) {
		
		HttpHost httpHost = Util.getHttpHost();
		
		//String url = "http://ephesoft.dev.promontech.com/dcma/rest/initiateOcrClassifyExtract";
		
		String url = "http://ephesoft.dev.promontech.com/dcma/rest/ocrClassifyExtract";
		
		HttpPost request = new HttpPost(url);
		
		CloseableHttpClient httpClient = Util.getHttpClient(httpHost);
						
		JSONArray[] extractResults = new JSONArray[1];
		
		InputStream inputStream    = null;
		OutputStream outputStream  = null;
	
		try {
			if (fileList != null && fileList.length > 0) {
				
				if (fileList.length > 1) {
					extractResults = new JSONArray[fileList.length];
				}
				
				for (int idx = 0; idx < fileList.length; idx++) {
					
					File tmpFile = File.createTempFile("ephesoft-tmp-file-name", ".pdf");
				   	System.out.println("Temp file : " + tmpFile.getAbsolutePath());
			    	
				   	inputStream  = fileList[idx].getInputStream();
				   	outputStream = new FileOutputStream(tmpFile.getAbsolutePath());
				   	
					int read = 0;
					byte[] bytes = new byte[1024];
		
					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
					
					inputStream.close();
					outputStream.close();
					
					System.out.println("Done!");
				   	
					FileBody   inFile = new FileBody(tmpFile);
					StringBody bcId   = new StringBody("BC8", ContentType.TEXT_PLAIN);
		
		            HttpEntity reqEntity = MultipartEntityBuilder.create()
		                    .addPart(inFile.getFilename(), inFile)
		                    .addPart("batchClassIdentifier", bcId)
		                    .build();
		
		            request.setEntity(reqEntity);
			            	
		            HttpClientContext localContext = Util.getLocalContent(httpHost);
		            
					CloseableHttpResponse response = httpClient.execute(httpHost, request, localContext);
					
					StringBuffer result = new StringBuffer();
					
					try {
						HttpEntity resEntity = response.getEntity();
						
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(resEntity.getContent()));
						
						String line = "";
						while ((line = rd.readLine()) != null) {
							System.out.println(line);
							result.append(line);
						}
		
						EntityUtils.consume(resEntity);
						
						System.out.println("Before fromXMLtoJSONDocument " + result.toString());
						
						extractResults[idx] = Util.fromXMLtoJSONDocument(result.toString());
					} finally {
						response.close();
					}
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
            try {
            	httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            if (inputStream != null) {
    			try {
    				inputStream.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		if (outputStream != null) {
    			try {
    				// outputStream.flush();
    				outputStream.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
        }
		
		JSONArray resultsList = new JSONArray();
		
		if (extractResults != null && extractResults.length > 0) {
			if (extractResults.length == 1) {
				resultsList = extractResults[0];
			} else {
				for (int idx = 0; idx < extractResults.length; idx++) {				

					JSONArray workArray = extractResults[idx];
					
					for (int idxWork = 0; idxWork < workArray.length(); idxWork++) {
						resultsList.put(workArray.getJSONArray(idxWork));
					}
				}
			}
		}
		
		System.out.println(resultsList.toString());
		
		return resultsList.toString();
	}
	
    @RequestMapping(value = "/documents/status/{docStatusId}",  method = RequestMethod.GET)
    public String getDocumentStatus(@PathVariable(value="docStatusId") String docStatusId) {


		HttpHost httpHost = Util.getHttpHost();
		
		String url = String.format("http://ephesoft.dev.promontech.com/dcma/rest/checkWSStatus?ocrToken=%s", docStatusId);
		
		HttpGet request = new HttpGet(url);

		CloseableHttpClient httpClient = Util.getHttpClient(httpHost);
				
		StringBuffer result = new StringBuffer();
				
		try {

			HttpClientContext localContext = Util.getLocalContent(httpHost);
			
			CloseableHttpResponse response = httpClient.execute(httpHost, request, localContext);
	
			System.out.println("Response Code : "
			                + response.getStatusLine().getStatusCode());
	
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
				
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			SAXBuilder saxBuilder = new SAXBuilder();
			
			InputStream resultInputStream = new ByteArrayInputStream(result.toString().getBytes("UTF-8"));
			
			Document document = saxBuilder.build(resultInputStream);
			
			List<?> resultsList = document.getRootElement().getChildren("Result");
			
			 for (int idx = 0; idx < resultsList.size(); idx++) {
				 System.out.println(resultsList.get(idx).toString());
			 }
			
			 Element resultElement = (Element) resultsList.get(0);
			 
			 @SuppressWarnings("unchecked")
			 List<Element> documentList = resultElement.getChild("Batch").getChild("Documents").getChildren();
			 
			 for (int idx = 0; idx < documentList.size(); idx++) {
				 System.out.println(documentList.get(idx).getChildText("Type"));

			 }
			 

			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
            try {
            	httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		
		System.out.println(result.toString());
		
		return result.toString();
    }
	
}
