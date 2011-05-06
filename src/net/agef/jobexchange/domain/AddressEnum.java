package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum AddressEnum implements Serializable{
	MR("MR"), 
	MRS("MRS");
	
	private final String value; 
	
	AddressEnum(String v) { value = v; }

	public String value() { return value; }

	public static AddressEnum fromValue(String v) throws EnumValueNotFoundException{ 
		
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (AddressEnum c: AddressEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				}  
			} 
		}
		System.out.println("Unable to Parse Addresses: "+v);
		throw new EnumValueNotFoundException(); 
	} 
}
