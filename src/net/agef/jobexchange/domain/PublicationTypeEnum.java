package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum PublicationTypeEnum implements Serializable{
	FULL_WITH_COMPLETE_ADDRESS("FULL_WITH_COMPLETE_ADDRESS"),
	ANONYMOUS_WITH_SECTOR_AND_JOB_REFERENCE_NUMBER("ANONYMOUS_WITH_SECTOR_AND_JOB_REFERENCE_NUMBER"),
	FULL_WITH_COMPLETE_ADDRESS_AND_COMMUNITY("FULL_WITH_COMPLETE_ADDRESS_AND_COMMUNITY");
	
	private final String value; 
	
	PublicationTypeEnum(String v) { value = v; }

	public String value() { return value; }

	public static PublicationTypeEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (PublicationTypeEnum c: PublicationTypeEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			}
		}
		throw new EnumValueNotFoundException(); 
	} 
}
