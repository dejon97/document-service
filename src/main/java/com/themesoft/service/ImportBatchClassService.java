package com.themesoft.service;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;

import util.Util;

public class ImportBatchClassService {
	
	public static void main(String[] args) {
		HttpHost httpHost = Util.getHttpHost();
		
		String serviceCall = String.format("%s://%s/%s", 
				httpHost.getSchemeName(), httpHost.getHostName(), "dcma/rest/importBatchClass");

		HttpPost request = new HttpPost(serviceCall);
		
		CloseableHttpClient httpClient = Util.getHttpClient(httpHost);
		
		try {
			File configFile        = new File(".\\SampleFiles\\import-config-file.xml");
			File zipBatchClassFile = new File("D:\\Users\\john.mccullough\\tmp\\BC7output.zip");
						
			FileBody configBody        = new FileBody(configFile);
			FileBody zipBatchClassBody = new FileBody(zipBatchClassFile);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
            		.addPart(configBody.getFilename(), configBody)
                    .addPart(zipBatchClassBody.getFilename(), zipBatchClassBody)
                    .build();

            request.setEntity(reqEntity);
	            	
            HttpClientContext localContext = Util.getLocalContent(httpHost);
            
			CloseableHttpResponse response = httpClient.execute(httpHost, request, localContext);
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if (statusCode == 200) {
				System.out.println("Batch class imported successfully");
			} else if (statusCode == 403) {
				System.out.println("Invalid username/password.");
			} else {
				System.out.println(response.toString());
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
	}
}
