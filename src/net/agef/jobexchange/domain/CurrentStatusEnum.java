/**
 * 
 */
package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

/**
 * @author mutabor
 *
 */
public enum CurrentStatusEnum implements Serializable{
	SEEKING_EMPLOYMENT("SEEKING_EMPLOYMENT"), 
	STUDENT("STUDENT"),
	EMPLOYEE("EMPLOYEE"),
	SELF_EMPLOYED("SELF_EMPLOYED"),
	FREELANCER("FREELANCER");
	
	
	private final String value; 
	
	CurrentStatusEnum(String v) { value = v; }

	public String value() { return value; }

	public static CurrentStatusEnum fromValue(String v) throws EnumValueNotFoundException{ 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (CurrentStatusEnum c: CurrentStatusEnum.values()) {
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			} 
		}
		System.out.println("Unable to Parse CurrentStatusEnum: "+v);
		throw new EnumValueNotFoundException(); 
	} 
}
