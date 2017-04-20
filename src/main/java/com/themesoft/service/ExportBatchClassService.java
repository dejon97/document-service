package com.themesoft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import util.Util;


public class ExportBatchClassService {

	public static void main(String[] args) {
		HttpHost httpHost = Util.getHttpHost();
                
                String url= "http://ephesoft0002.dev.promontech.com:8080/dcma/rest/exportBatchClass";
 
                HttpPost request=new HttpPost(url);
                CloseableHttpClient httpClient = Util.getHttpClient(httpHost);
                try{
                StringBody bcId   = new StringBody("BC7", ContentType.TEXT_PLAIN);
                HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("batchClassIdentifier", bcId)
                .build();
                request.setEntity(reqEntity);
      
                
                HttpClientContext localContext = Util.getLocalContent(httpHost);
                CloseableHttpResponse response = httpClient.execute(httpHost, request, localContext); 
                
                int statusCode = response.getStatusLine().getStatusCode();
                StringBuffer result = new StringBuffer();
               
                if(statusCode == 200){
                   System.out.println("Batch Class Exported Successfully");
                    HttpEntity resEntity = response.getEntity();
                    BufferedReader rd = new BufferedReader(
					new InputStreamReader(resEntity.getContent()));
                    String line = "";
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                    result.append(line);
                                                        }
                File zipBatchClass= new File("C:\\sample\\BC7output.zip");
                FileOutputStream fos= new FileOutputStream(zipBatchClass);
                   try{
                       byte[] buf=new byte[1024];
                       int len=rd.read();
                       while(len>0){
                       fos.write(buf,0,len);
                       len=rd.read();
                       }
                   }
                   finally{
                       if(fos!=null){
                           fos.close();
                       }
                   }

                }
                else if(statusCode == 403){
                
                System.out.println("Invalid Username/Password");
                }
                else {
				System.out.println(response.toString());
			}	
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {
            try {
            	httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        }
}
