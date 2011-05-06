package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum LanguageSkillsEnum implements Serializable{
	NO_SKILLS("NO_SKILLS"),
	MOTHER_TONGUE("MOTHER_TONGUE"), 
	BUSINESS_FLUENT("BUSINESS_FLUENT"), 
	FLUENT("FLUENT"), 
	BASIC_KNOWLEDGE("BASIC_KNOWLEDGE");
	
	private final String value; 
	
	LanguageSkillsEnum(String v) { value = v; }

	public String value() { return value; }

	public static LanguageSkillsEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (LanguageSkillsEnum c: LanguageSkillsEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				} 
			}
		}
		System.out.println("Unable to Parse LangSkill: "+v); 
		throw new EnumValueNotFoundException(); 
	}
}
