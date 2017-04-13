/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.themesoft.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Ignore;
/**
 *
 * @author meena.prabhak
 */
import com.themesoft.service.FileService;
public class DocumentTypePayStub_Paystub2_Test {
    @Test
    public void Testpaystub_2(){
    try{
            JSONObject response=FileService.submitFile("paystub-2.pdf");
            System.out.println(response.toString());
           
           assertEquals("Pay Stub Earning Periodic Unit Count","50",response.get("1720"));
           assertEquals("Pay Stub Earning Rate Amount","50",response.get("1719"));
           assertEquals("Pay Stub Periodic Income Amount","2,500.00",response.get("1812"));
           assertEquals("Pay Stub Year to Date Income Amount","12,500.00",response.get("1813"));
           assertEquals("Income Source End Date","11/08/13",response.get("1687"));
           assertEquals("Pay Stub Periodic Earning Amount","1,540.81",response.get("1733"));
           assertEquals("Pay Stub Earning Type","GROSS WAGES",response.get("1718"));
    
    }
    catch(Exception e){
    e.printStackTrace();
    }
    }
}
