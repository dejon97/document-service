
package com.themesoft.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Ignore;

import com.themesoft.service.FileService;


/**
 *
 * @author meena.prabhak
 */
public class DocumentTypePayStub_Paystub3_Test {
    
    @Test
    public void Testpaystub_3(){
    try{
        
            JSONObject response=FileService.submitFile("paystub-3.pdf");
            System.out.println(response.toString());
            assertEquals("Pay Stub Earning Periodic Unit Count","",response.get("1720"));
            assertEquals("Pay Stub Earning Rate Amount","",response.get("1719"));
            assertEquals("Pay Stub Periodic Income Amount","1,000.00",response.get("1812"));
            assertEquals("Pay Stub Year to Date Income Amount","5,000.00",response.get("1813"));
            assertEquals("Income Source End Date","01/31/14",response.get("1687"));
            assertEquals("Pay Stub Periodic Earning Amount","709.74",response.get("1733"));
            assertEquals("Pay Stub Earning Type","GROSS WAGES",response.get("1718"));
    }
    catch(Exception e){
    e.printStackTrace();
    }
    }
    
}

