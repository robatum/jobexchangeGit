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
public enum EducationInstituteTypeEnum implements Serializable{
	UNIVERSITY("UNIVERSITY"), 
	UNIVERSITY_OF_APPLIED_SCIENCES("UNIVERSITY_OF_APPLIED_SCIENCES"), 
	VOCATIONAL_SCHOOL("VOCATIONAL_SCHOOL"),
	GRAMMAR_SCHOOL("GRAMMAR_SCHOOL"); 
	
	private final String value; 
	
	EducationInstituteTypeEnum(String v) { value = v; }

	public String value() { return value; }

	public static EducationInstituteTypeEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (EducationInstituteTypeEnum c: EducationInstituteTypeEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				}  
			} 
		}
		System.out.println("Unable to Parse EduType: "+v);
		throw new EnumValueNotFoundException(); 
	}
}
