package com.themesoft.tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import com.themesoft.service.FileService;

public class DocumentTypeW2_W2_111_Test {
	
	@Test
	public void testW2() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-111.tiff");
			//System.out.println(response.toString());
			
			//assertEquals("Applicant SSN", "123-45-6789", response.get("84"));
			//assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "18866.14", response.get("1712"));			
			assertEquals("W2 Federal Income Tax Withheld Amount", "2252.91", response.get("1713"));
			assertEquals("W2 Social Security Wages Amount", "20312.82", response.get("1714"));	
			assertEquals("W2 Social Security Tax Withheld Amount", "1259.39", response.get("1715"));	
			assertEquals("W2 Medicare Wages and Tips Amount", "20312.82", response.get("1716"));			
			assertEquals("W2 Medicare Tax Withheld Amount", "294.54", response.get("1717"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
