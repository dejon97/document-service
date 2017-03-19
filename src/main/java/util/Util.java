package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Util {

	public static HttpHost getHttpHost() {
		return new HttpHost("ephesoft.dev.promontech.com", 80, "http");
	}
	
	public static HttpClientContext getLocalContent(HttpHost httpHost) {
		
	    // Create AuthCache instance
	    AuthCache authCache = new BasicAuthCache();
	    
	    // Generate BASIC scheme object and add it to the local auth cache
	    BasicScheme basicAuth = new BasicScheme();
	    authCache.put(httpHost, basicAuth);
	
	    // Add AuthCache to the execution context
	    HttpClientContext localContext = HttpClientContext.create();
	    
	    localContext.setAuthCache(authCache);
	    
	    return localContext;
	}
	
	public static CloseableHttpClient getHttpClient(HttpHost httpHost) {
	    CredentialsProvider credsProvider = new BasicCredentialsProvider();
	    
	    credsProvider.setCredentials(
	            new AuthScope(httpHost.getHostName(), httpHost.getPort()),
	            new UsernamePasswordCredentials("ephesoft", "demo"));
	    
	    CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
	    
	    return httpclient;
	}
	
	public static JSONArray fromXMLtoJSONDocument(String xmlValue) throws JDOMException, IOException {
		
		JSONArray extractedDataList = new JSONArray();
		
		SAXBuilder saxBuilder = new SAXBuilder();
		
		InputStream resultInputStream = new ByteArrayInputStream(xmlValue.getBytes("UTF-8"));
		
		Document document = saxBuilder.build(resultInputStream);
		
		List<?> resultsList = document.getRootElement().getChildren("Result");
		
		if (resultsList != null && resultsList.size() > 0) {
			 Element resultElement = (Element) resultsList.get(0);
			 
			 @SuppressWarnings("unchecked")
			 List<Element> documentList = resultElement.getChild("Batch").getChild("Documents").getChildren();
			 				 
			 for (int idx = 0; idx < documentList.size(); idx++) {
				 
				 JSONArray extractResults = new JSONArray();
				 
				 JSONObject extractResult = new JSONObject();
				 
				 System.out.println(documentList.get(idx).getChildText("Type"));
				 
				 extractResult.put("order", "0");
				 extractResult.put("name", "DocumentType");
				 extractResult.put("value", documentList.get(idx).getChildText("Type"));
				 
				 extractResults.put(extractResult);
				 
				Element documentLevelFieldsNode = documentList.get(idx).getChild("DocumentLevelFields");
				@SuppressWarnings("rawtypes")
				List documentLevelFieldList = documentLevelFieldsNode.getChildren("DocumentLevelField");
				
				for (int j = 0; j < documentLevelFieldList.size(); j++) {
					Element fieldNode = (Element) documentLevelFieldList.get(j);
					System.out.println(fieldNode.getChild("Name").getValue() + " = " + fieldNode.getChild("Value").getValue());
					
					extractResult = new JSONObject();
					
					extractResult.put("order", fieldNode.getChild("FieldOrderNumber").getValue());
					extractResult.put("name", fieldNode.getChild("Name").getValue());
					extractResult.put("value", fieldNode.getChild("Value").getValue());
					 
					extractResults.put(extractResult);
				}
				
				extractedDataList.put(extractResults);	
			 }
		 }
		 
		 return extractedDataList;
	}
	
	public static String getFileExtension(String filename) {
		String extension = "";

		int idx = filename.lastIndexOf('.');
		
		if (idx > 0) {
		    extension = filename.substring(idx);
		}
		
		return extension;
	}
	
}
