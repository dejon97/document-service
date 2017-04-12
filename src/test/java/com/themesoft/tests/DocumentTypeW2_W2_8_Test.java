package com.themesoft.tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import com.themesoft.service.FileService;

public class DocumentTypeW2_W2_8_Test {

	//http://www.vogella.com/tutorials/JUnit/article.html
	
	@Test
	public void testW2() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-8.pdf");
			//System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "123-45-6789", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "48,500.00", response.get("1712"));			
			assertEquals("W2 Federal Income Tax Withheld Amount", "6,835.00", response.get("1713"));
			assertEquals("W2 Social Security Wages Amount", "50,000.00", response.get("1714"));	
			assertEquals("W2 Social Security Tax Withheld Amount", "3,100.00", response.get("1715"));
			assertEquals("W2 Medicare Wages and Tips Amount", "50,000.00", response.get("1716"));			
			assertEquals("W2 Medicare Tax Withheld Amount", "725.00", response.get("1717"));
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
