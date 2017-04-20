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
			
			assertEquals("Applicant SSN", "100000004", response.get("84"));
			assertEquals("Income Source Year", "2015", response.get("1688"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "112000", response.get("1712"));			
			assertEquals("W2 Federal Income Tax Withheld Amount", "23767", response.get("1713"));
			assertEquals("W2 Social Security Wages Amount", "112000", response.get("1714"));	
			assertEquals("W2 Social Security Tax Withheld Amount", "6944", response.get("1715"));
			assertEquals("W2 Medicare Wages and Tips Amount", "112000", response.get("1716"));			
			assertEquals("W2 Medicare Tax Withheld Amount", "1624", response.get("1717"));
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
