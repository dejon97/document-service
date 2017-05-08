/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.themesoft.tests;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.json.JSONObject;
import com.themesoft.service.FileService;
/**
 *
 * @author meena.prabhak
 */
public class DocumentTypeDriversLicense_DL1_Test {
    
    @Test
    public void driverslicense_1(){
    
        try{
            JSONObject response=FileService.submitFile("DL-AL.tiff");
            System.out.println(response.toString());
            assertEquals("Applicant Birth Date","03-21-1968",response.get("53"));
            }
        catch(Exception e ){e.printStackTrace();}
    
    }
}
