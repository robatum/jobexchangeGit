package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum ContractDurationEnum implements Serializable{
	SHORTTERM_1_TO_3_MONTH("SHORTTERM_1_TO_3_MONTH"), 
	MEDIUMTERM_3_MONTH_TO_2_YEARS("MEDIUMTERM_3_MONTH_TO_2_YEARS"),
	LONGTERM_2_YEARS_AND_LONGER("LONGTERM_2_YEARS_AND_LONGER"),
	PERMANENT("PERMANENT")
	,
	LONGTERM_3_MONTH_TO_2_YEARS("LONGTERM_3_MONTH_TO_2_YEARS")
	;
	
	
	private final String value; 
	
	ContractDurationEnum(String v) { value = v; }

	public String value() { return value; }

	public static ContractDurationEnum fromValue(String v) throws EnumValueNotFoundException{ 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (ContractDurationEnum c: ContractDurationEnum.values()) {
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			} 
		}
		System.out.println("Unable to Parse ContactDuration: "+v);
		throw new EnumValueNotFoundException(); 
	} 
}
