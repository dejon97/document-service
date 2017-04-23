package com.themesoft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import util.Util;

public class ExportBatchClassService {

/*
 * https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html
 * 
	  static {
	      System.setProperty("org.apache.commons.logging.Log",
	                         "org.apache.commons.logging.impl.NoOpLog");
	   }
	   
	//java.util.logging.Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
	java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.OFF);
	java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.OFF);
	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
	System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
			   
*/
	  
	public static void main(String[] args) {
				  
		HttpHost httpHost = Util.getHttpHost();

		String url = "http://ephesoft0002.dev.promontech.com:8080/dcma/rest/exportBatchClass";

		HttpPost request = new HttpPost(url);

		CloseableHttpClient httpClient = Util.getHttpClient(httpHost);
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("identifier", "BC7"));
			nvps.add(new BasicNameValuePair("lucene-search-classification-sample", "true"));
			nvps.add(new BasicNameValuePair("image-classification-sample", "false"));
			request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpClientContext localContext = Util.getLocalContent(httpHost);
			CloseableHttpResponse response = httpClient.execute(httpHost, request, localContext);

			int statusCode = response.getStatusLine().getStatusCode();
			// StringBuffer result = new StringBuffer();
			if (statusCode == 200) {
							
				HttpEntity resEntity = response.getEntity();
				
				if (resEntity != null) {
					//System.out.println("Response content length" + resEntity.getContentLength());
					
					InputStream inContent = resEntity.getContent();
					
					//String responseBody = EntityUtils.toString(resEntity);
					//System.out.println("Data:" + responseBody);

					File zip = new File("D:\\Users\\john.mccullough\\tmp\\BC7output.zip");
					FileOutputStream fos = new FileOutputStream(zip);

					try {
						byte[] buf = new byte[8192];
						
						int len = inContent.read(buf);
						
						while (len > 0) {
							//System.out.println("xfer " + len);
							
							fos.write(buf, 0, len);
							len = inContent.read(buf);
						}
					} finally {
						if (fos != null) {
							fos.close();
						}
						
						System.out.println("Batch Class Exported Successfully");
					}
				}
			} else if (statusCode == 403)
				System.out.println("Invalid Username/Password");
			else
				System.out.println(response.toString());

		} catch (Exception e) {
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
