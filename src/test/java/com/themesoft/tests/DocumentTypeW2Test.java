package com.themesoft.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import com.themesoft.service.FileService;

public class DocumentTypeW2Test {

	//http://www.vogella.com/tutorials/JUnit/article.html
	
	@Test
	public void testW2_2() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-2.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "123-45-6789", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "6,835.00", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "725.00", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "50,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "3,100.00", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "0", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "0", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testW2_3() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-3.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100-00-0009", response.get("84"));
			assertEquals("Income Source Year", "2015", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "5708.56", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "609", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "41999", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "2604", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "41999", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "41999", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
	public void testW2_4() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-4.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testW2_5() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-5.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
	public void testW2_6() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-6.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}	
	
	@Test
	public void testW2_7() {
				
		try {
			
			JSONObject response = FileService.submitFile("w2-7.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}	
	
	public void testW2_8() {
		
		try {
			
			JSONObject response = FileService.submitFile("w2-8.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void testW2_9() {
		
		try {
			
			JSONObject response = FileService.submitFile("w2-9.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}	
	
	public void testW2_10() {
		
		try {
			
			JSONObject response = FileService.submitFile("w2-10.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	public void testW2_11() {
		
		try {
			
			JSONObject response = FileService.submitFile("w2-11.pdf");
			System.out.println(response.toString());
			
			assertEquals("Applicant SSN", "100000006", response.get("84"));
			assertEquals("Income Source Year", "2014", response.get("1688"));
			assertEquals("W2 Federal Income Tax Withheld Amount", "19,147", response.get("1713"));
			assertEquals("W2 Medicare Tax Withheld Amount", "1,385", response.get("1717"));
			assertEquals("W2 Medicare Wages and Tips Amount", "95,000.00", response.get("1716"));
			assertEquals("W2 Social Security Tax Withheld Amount", "5,921.", response.get("1715"));
			assertEquals("W2 Social Security Wages Amount", "95,500.00", response.get("1714"));
			assertEquals("W2 Wages Tips Other Compensation Amount", "95,500.00", response.get("1712"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
}
