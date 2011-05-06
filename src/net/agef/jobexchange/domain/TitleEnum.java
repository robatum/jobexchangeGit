package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum TitleEnum implements Serializable{
	PROF_DR("PROF_DR"), 
	PROF("PROF"), 
	DR_MULT("DR_MULT"), 
	DR("DR"), 
	DR_PHD("DR_PHD"), 
	DIPL_ING("DIPL_ING"); 
	
	private final String value; 
	
	TitleEnum(String v) { value = v; }

	public String value() { return value; }

	public static TitleEnum fromValue(String v) throws EnumValueNotFoundException { 
		
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (TitleEnum c: TitleEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			} 
		}
		System.out.println("Unable to Parse Title: "+v);  
		throw new EnumValueNotFoundException(); 
	}
}
