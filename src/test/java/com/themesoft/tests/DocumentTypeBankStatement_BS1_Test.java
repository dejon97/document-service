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
public class DocumentTypeBankStatement_BS1_Test {
    
    @Test
    public void bankstatement_1(){
    
        try{
            JSONObject response=FileService.submitFile("BankStatement-1.pdf");
            System.out.println(response.toString());
            assertEquals("Account Owner name","ASHLEY WILLIAMS",response.get("AccOwnername"));
            assertEquals("Financial Institution","Wells Fargo",response.get("FinancialInstitution"));
            assertEquals("Account Type","Checking",response.get("Account Type"));
            assertEquals("Qualifying Asset final value amount","$ 104,000.00",response.get("1742"));
            assertEquals("Beginning Date","3/1",response.get("Date Beginning"));
            assertEquals("Date End","3/31",response.get("Date End"));
            assertEquals("Statement Date","",response.get("Statement Date"));
            }
        catch(Exception e ){e.printStackTrace();}
    
    }
}
