package net.agef.jobexchange.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

@XmlType(name = "DecisionYesNo") 
@XmlEnum 
public enum DecisionYesNoEnum implements Serializable{
	@XmlEnumValue("YES")
	YES("YES"), 
	@XmlEnumValue("NO")
	NO("NO");
	
	private final String value; 
	
	DecisionYesNoEnum(String v) { value = v; }

	public String value() { return value; }

	public static DecisionYesNoEnum fromValue(String v) throws EnumValueNotFoundException { 
		if(v!=null) {
			v = v.trim();
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (DecisionYesNoEnum c: DecisionYesNoEnum.values()) { 
				if (c.value.equals(v)) 
				{ 
					return c; 
				}
			}
		}
		System.out.println("Unable to Parse DecYesNo: "+v); 
		throw new EnumValueNotFoundException(); 
	} 
}
