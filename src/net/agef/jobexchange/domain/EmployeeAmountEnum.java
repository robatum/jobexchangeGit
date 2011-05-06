/**
 * 
 */
package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

/**
 * @author AGEF
 *
 */
public enum EmployeeAmountEnum implements Serializable{
	
	ME("ME"),
	TWO_TO_TEN("TWO_TO_TEN"), 
	ELEVEN_TO_FIFTY("ELEVEN_TO_FIFTY"), 
	FIFTYONE_TO_TWOHUNDRED("FIFTYONE_TO_TWOHUNDRED"), 
	TWOHUNDREDONE_TO_FIVEHUNDRED("TWOHUNDREDONE_TO_FIVEHUNDRED"),
	FIVEHUNDREDONE_TO_THOUSAND("FIVEHUNDREDONE_TO_THOUSAND"),
	THOUSANDONE_TO_FIVETHOUSAND("THOUSANDONE_TO_FIVETHOUSAND"),
	FIVETHOUSANDONE_TO_TENTHOUSAND("FIVETHOUSANDONE_TO_TENTHOUSAND"),
	MORE_THEN_TENTHOUSAND("MORE_THEN_TENTHOUSAND"); 
	
	private final String value; 
	
	EmployeeAmountEnum(String v) { value = v; }

	public String value() { return value; }

	public static EmployeeAmountEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (EmployeeAmountEnum c: EmployeeAmountEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			} 
		}
		System.out.println("Unable to Parse EmployeeAmount: "+v); 
		throw new EnumValueNotFoundException(); 
	}

}
