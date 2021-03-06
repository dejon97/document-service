package util;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

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
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class Util {

	static { ImageIO.scanForPlugins(); }
	
	public static HttpHost getHttpHost() {
		//return new HttpHost("ephesoft0002.dev.promontech.com", 8080, "http");
		return new HttpHost("ephesoft.dev.promontech.com", 443, "https");
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
	            new UsernamePasswordCredentials("ephesoft", "themesoft"));
	    
	    CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
	    
	    return httpclient;
	}
	
	public static JSONObject fromXMLtoJSONObject(String xmlValue) throws JDOMException, IOException {
		
		JSONObject extractedData = new JSONObject();
		
		SAXBuilder saxBuilder = new SAXBuilder();
		
		InputStream resultInputStream = new ByteArrayInputStream(xmlValue.getBytes("UTF-8"));
		
		Document document = saxBuilder.build(resultInputStream);
		
		List<?> resultsList = document.getRootElement().getChildren("Result");
		
		if (resultsList != null && resultsList.size() > 0) {
			 Element resultElement = (Element) resultsList.get(0);
			 
			 @SuppressWarnings("unchecked")
			 List<Element> documentList = resultElement.getChild("Batch").getChild("Documents").getChildren();
			 				 
			 for (int idx = 0; idx < documentList.size(); idx++) {				 
				 extractedData.put("DocumentType", documentList.get(idx).getChildText("Type"));
				 
				Element documentLevelFieldsNode = documentList.get(idx).getChild("DocumentLevelFields");
				@SuppressWarnings("rawtypes")
				List documentLevelFieldList = documentLevelFieldsNode.getChildren("DocumentLevelField");
				
				for (int j = 0; j < documentLevelFieldList.size(); j++) {
					Element fieldNode = (Element) documentLevelFieldList.get(j);
					System.out.println(fieldNode.getChild("Name").getValue() + " = " + fieldNode.getChild("Value").getValue());
										
					extractedData.put(fieldNode.getChild("Name").getValue(), fieldNode.getChild("Value").getValue()); 
				}
				
				Element dataTableElement = documentList.get(idx).getChild("DataTables");
				
				if (dataTableElement != null) {
					@SuppressWarnings("unchecked")
					List<Element> dataTableList = dataTableElement.getChildren();
					
					JSONArray dataTables = new JSONArray();
					
					for (int idxDataTable = 0; idxDataTable < dataTableList.size(); idxDataTable++) {			
						String tableName = dataTableList.get(idxDataTable).getChild("Name").getValue();
						
						System.out.println("Table Name: " + tableName);
						
						JSONArray dataTableData = new JSONArray();
						
						@SuppressWarnings("unchecked")
						List<Element> rowList = dataTableList.get(idxDataTable).getChild("Rows").getChildren();
						
						for (int idxRow = 0; idxRow < rowList.size(); idxRow++) {
							
							JSONObject dataTableRow = new JSONObject();
							
							@SuppressWarnings("unchecked")
							List<Element> colList = rowList.get(idxRow).getChild("Columns").getChildren();	
							
							for (int idxCol = 0; idxCol < colList.size(); idxCol++) {
								String colName  = colList.get(idxCol).getChild("Name").getValue();
								String colValue = ((colList.get(idxCol).getChild("Value") != null) ? colList.get(idxCol).getChild("Value").getValue() : "");
								
								dataTableRow.put(colName, colValue);
								
								//System.out.println(colList.get(idxCol).getChild("Name").getValue() + " = " + ((colList.get(idxCol).getChild("Value") != null) ? colList.get(idxCol).getChild("Value").getValue() : ""));				
							}
							
							if (dataTableRow.length() > 0) {
								dataTableData.put(dataTableRow);
							}
						}
						
						if (dataTableData.length() > 0) {
							JSONObject dataTableObject = new JSONObject();
							
							dataTableObject.put("TableName", tableName);
							dataTableObject.put("TableData", dataTableData);
							
							dataTables.put(dataTableObject);
						}
					}
					
					if (dataTables.length() > 0) {
						extractedData.put("DataTables", dataTables);
					}
				}
				
			 }
						 
		 }
		 
		 return extractedData;
	}

	public static JSONArray fromXMLtoJSONArray(String xmlValue) throws JDOMException, IOException {
		
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
	
	public static File convertFile(MultipartFile filePart) {
	
		File tiffFile = null;
		
		ImageOutputStream ios = null;
		ImageWriter writer    = null;
		
		try {
			IIORegistry reg = IIORegistry.getDefaultInstance();
			Iterator<ImageWriterSpi> spIt = reg.getServiceProviders(ImageWriterSpi.class, false);
			
			while (spIt.hasNext()) {
				System.out.println(spIt.next().getDescription(Locale.ENGLISH));
			}
			
			Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("TIFF");
			
			if (it.hasNext()) {
				writer = (ImageWriter)it.next();
				
				tiffFile = File.createTempFile("ephesoft-tmp-file-name", "png");
				
				ios = ImageIO.createImageOutputStream(tiffFile);
				
				writer.setOutput(ios);
				
				ImageWriteParam writeParam = new ImageWriteParam(Locale.ENGLISH);
				
				//writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				
				//writeParam.setCompressionType("PackBits");
	
				IIOImage iioImage = new IIOImage(ImageIO.read(filePart.getInputStream()), null, null);
	
				writer.write(null, iioImage, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tiffFile;
	}

	public static File convertFileToPDF(MultipartFile filePart) {
		
		File pdfFile = null;
			   
		try {
	        pdfFile = File.createTempFile("ephesoft-tmp-file-name", ".pdf");
	               
	        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
	        
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
	        
	        writer.open();
	        document.open();
	        
	        Image img = Image.getInstance(filePart.getBytes());
	        //img.setOriginalType(Image.ORIGINAL_JPEG);
	        
	        //img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight()); 
	        
	        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
	                - document.rightMargin()) / img.getWidth()) * 100;

	        System.out.println("height " + img.getHeight() + " width " + img.getWidth());
	        System.out.println(" scaler " + scaler);
	        
	        img.scalePercent(scaler);
	        
	        document.add(img);
	        
	        document.close();
	        writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pdfFile;
	}
	
	public static String convertToISO8601(String selectedDate) {
		String retValue = null;
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat df_output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		
        try {
        	Date date = df.parse(selectedDate);        	
        	retValue = df_output.format(date);       	
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return retValue;
	}
}
