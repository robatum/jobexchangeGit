package net.agef.jobexchange.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;


public enum PortalIdentifierEnum implements Serializable{
	APD(1), 
	IGC(2); 	
	private final Integer portalId; 
	
	PortalIdentifierEnum(Integer v) { portalId = v; }

	public Integer portalId() { return portalId; }

	public static PortalIdentifierEnum fromPortalId(Integer v) throws EnumValueNotFoundException { 
		if(v!=null) {
			if(v.equals("") || v.equals("-1"))
				return null;
			
			for (PortalIdentifierEnum c: PortalIdentifierEnum.values()) { 
				if (c.portalId.equals(v)) 
					{ 
						return c; 
					}
			} 
		}
		System.out.println("Unable to Parse PortalIdentifier: "+v); 
		throw new EnumValueNotFoundException(); 
	}
	
}
