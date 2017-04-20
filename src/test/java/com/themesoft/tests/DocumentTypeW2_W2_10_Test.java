package com.themesoft.tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import com.themesoft.service.FileService;

public class DocumentTypeW2_W2_10_Test {

	//http://www.vogella.com/tutorials/JUnit/article.html
	
	@Test
	public void testW2() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-10.pdf");
			//System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100-00-0008", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "91700", response.get("1712"));			
			assertEquals("W2 Federal Income Tax Withheld Amount", "20006", response.get("1713"));
			assertEquals("W2 Social Security Wages Amount", "91700", response.get("1714"));	
			assertEquals("W2 Social Security Tax Withheld Amount", "5685", response.get("1715"));	
			assertEquals("W2 Medicare Wages and Tips Amount", "91700", response.get("1716"));			
			assertEquals("W2 Medicare Tax Withheld Amount", "1330", response.get("1717"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
