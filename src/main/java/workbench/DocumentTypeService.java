package workbench;

import java.util.HashMap;
import java.util.Map;

public class DocumentTypeService {
	
	static Map<String, String> w2DocumentMapping = null;
	static Map<String, String> payStubMapping = null;
	
	static {
		w2DocumentMapping = new HashMap<String, String>();
		
		w2DocumentMapping.put("ECMDocumentType", "W2_Form");
		//w2DocumentMapping.put("DocumentType", "ld:DocumentType");
		w2DocumentMapping.put("SSN-TaxId", "ld:SSN");
		w2DocumentMapping.put("EmployerName", "w2:EmployerName");
		w2DocumentMapping.put("EmployerAddress", "w2:EmployerAddress");
		w2DocumentMapping.put("Year", "w2:Year");
		w2DocumentMapping.put("WagesTipsOther", "w2:WagesTips");
		w2DocumentMapping.put("FederalIncomeTaxWithheld", "w2:FederalIncomeTaxWithheld");
		w2DocumentMapping.put("SocialSecurityWages", "w2:SocialSecurityWages");
		//w2DocumentMapping.put("WagesTipsOther", "w2:SocialSecurityTaxWithheld");
		w2DocumentMapping.put("MedicareWagesTips", "w2:MedicareWages");
		w2DocumentMapping.put("MedicareTaxWithheld", "w2:MedicareTaxWithheld");
		w2DocumentMapping.put("AllocatedTips", "w2:LocalWages");
		
		payStubMapping = new HashMap<String, String>();
		
		payStubMapping.put("ECMDocumentType", "PayStub");
		payStubMapping.put("Hours", "paystub:NumberHours");
		payStubMapping.put("Rate", "paystub:EmployerAddress");
		payStubMapping.put("Period amount", "paystub:PeriodAmount");
		payStubMapping.put("YTD amount", "paystub:YearToDateEarnings");
		//payStubMapping.put("Date", "paystub:PayDate");
		payStubMapping.put("Net Pay", "paystub:NetPay");
		//payStubMapping.put("Income Type", "w2:MedicareWages");
			
	}
	
	public static  Map<String, String> getMapping(String documentType) {
		
		Map<String, String> retValue = null;
		
		if (documentType.equalsIgnoreCase("W2")) {
			retValue = w2DocumentMapping;
		} else if (documentType.equalsIgnoreCase("Paystub")) {
			retValue = payStubMapping;
		}
	
		return retValue;
	}
}
