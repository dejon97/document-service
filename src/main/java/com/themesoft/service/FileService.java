package com.themesoft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.json.JSONArray;
import org.json.JSONObject;

import util.Util;

public class FileService {

	public static JSONObject submitFile(String fileName) throws ClientProtocolException, IOException, JDOMException  {
		
		String testFileFolder = ".\\TestFiles";
		
		Path path = Paths.get(testFileFolder, fileName);
		
		System.out.println(path.getFileName());
		
		File submitFile = path.toFile();
				
		HttpHost httpHost = Util.getHttpHost();
						
		String serviceCall = String.format("%s://%s/%s", 
				httpHost.getSchemeName(), httpHost.getHostName(), "dcma/rest/ocrClassifyExtract");
		
		System.out.println(serviceCall);
	
		HttpPost request = new HttpPost(serviceCall);
		
		CloseableHttpClient httpClient = Util.getHttpClient(httpHost);
		
		JSONObject extractResults = new JSONObject();
		
		FileBody   inFile = new FileBody(submitFile);

		StringBody bcId   = new StringBody("BC7", ContentType.TEXT_PLAIN);

        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart(inFile.getFilename(), inFile)
                .addPart("batchClassIdentifier", bcId)
                .build();

        request.setEntity(reqEntity);
            	
        HttpClientContext localContext = Util.getLocalContent(httpHost);
        
		CloseableHttpResponse response = httpClient.execute(httpHost, request, localContext);
		
		try {
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(resEntity.getContent()));
				
				StringBuffer result = new StringBuffer();
				
				String line = "";
				while ((line = rd.readLine()) != null) {
					System.out.println(line);
					result.append(line);
				}
	
				EntityUtils.consume(resEntity);
				
				System.out.println("Before fromXMLtoJSONDocument " + result.toString());
				
				extractResults = Util.fromXMLtoJSONObject(result.toString());
			} else {
				JSONObject callFailed = new JSONObject();
				
				callFailed.put("status", response.getStatusLine().getStatusCode());
				
				extractResults = callFailed;
			}
		} finally {
			response.close();
		}
				
		return extractResults;
	}
}
