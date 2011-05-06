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
public enum TeamSizeEnum implements Serializable{
	ONE_TO_TEN("ONE_TO_TEN"), 
	ELEVEN_TO_THIRTY("ELEVEN_TO_THIRTY"), 
	THIRTYONE_TO_HUNDRED("THIRTYONE_TO_HUNDRED"), 
	MORE_THEN_HUNDRED("MORE_THEN_HUNDRED"); 
	
	private final String value; 
	
	TeamSizeEnum(String v) { value = v; }

	public String value() { return value; }

	public static TeamSizeEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (TeamSizeEnum c: TeamSizeEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			} 
		}
		System.out.println("Unable to Parse TeamSize: "+v); 
		throw new EnumValueNotFoundException(); 
	}
}

