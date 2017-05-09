/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.themesoft.tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.themesoft.service.FileService;
public class DocumentTypePayStub_Paystub1_Test {
    
    @Test
    public void testPayStub_1(){
    
        try{
           JSONObject response=FileService.submitFile("paystub-1.pdf");
           System.out.println(response.toString());
           
           assertEquals("Pay Stub Earning Periodic Unit Count","",response.get("1720"));
           //assertEquals("Pay Stub Earning Rate Amount","",response.get("1719"));
           assertEquals("Pay Stub Periodic Income Amount","1758.33",response.get("1812"));
           assertEquals("Pay Stub Year to Date Income Amount","3517.62",response.get("1813"));
           assertEquals("Income Source End Date","2/01/16",response.get("1687"));
           assertEquals("Pay Stub Periodic Earning Amount","1267.18",response.get("1733"));
           assertEquals("Pay Stub Earning Type","REG CASHOP",response.get("1718"));
           
           JSONArray  tableList = response.getJSONArray("DataTables");           
           JSONObject table     = tableList.getJSONObject(0);
           
           JSONArray  tableDatas = table.getJSONArray("TableData");
           JSONObject tableData  = tableDatas.getJSONObject(0);

           assertEquals("REG", tableData.get("Type"));
           assertEquals("1708.33", tableData.get("Total"));
          
           tableData  = tableDatas.getJSONObject(1);

           assertEquals("CASHOP", tableData.get("Type"));
           assertEquals("50.00", tableData.get("Total"));
                    
        }
        catch(Exception e){
        e.printStackTrace();
        }
    }
    
}
