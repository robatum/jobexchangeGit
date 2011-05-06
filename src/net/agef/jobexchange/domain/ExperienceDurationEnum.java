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
public enum ExperienceDurationEnum implements Serializable{
	ZERO_TO_ONE("ZERO_TO_ONE"), 
	ONE_TO_TWO("ONE_TO_TWO"), 
	TWO_TO_FIVE("TWO_TO_FIVE"), 
	FIVE_TO_TEN("FIVE_TO_TEN"),
	MORE_THEN_TEN("MORE_THEN_TEN"); 
	
	private final String value; 
	
	ExperienceDurationEnum(String v) { value = v; }

	public String value() { return value; }

	public static ExperienceDurationEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (ExperienceDurationEnum c: ExperienceDurationEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			}
		}
		System.out.println("Unable to Parse ExpDuration: "+v); 
		throw new EnumValueNotFoundException(); 
	}
}
