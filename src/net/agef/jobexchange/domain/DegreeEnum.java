package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum DegreeEnum implements Serializable{
	BACHELOR("BACHELOR"), 
	MASTER("MASTER"), 
	DIPLOMA("DIPLOMA"), 
	MAGISTER("MAGISTER"),
	STATE_EXAMINATION("STATE_EXAMINATION"),
	VOCATIONAL_TRAINING("VOCATIONAL_TRAINING"),
	DR_PHD("DR_PHD"),
	PROF("PROF"), 
	OTHER("OTHER"); 
	
	private final String value; 
	
	DegreeEnum(String v) { value = v; }

	public String value() { return value; }

	public static DegreeEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (DegreeEnum c: DegreeEnum.values()) { 
				if (c.value.equals(v.toUpperCase())) 
					{ 
						return c; 
					}
			} 
		}
		System.out.println("Unable to Parse Degree: "+v); 
		throw new EnumValueNotFoundException(); 
	}
}
