/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import java.util.List;

import net.agef.jobexchange.domain.PortalIdentifier;
import net.agef.jobexchange.domain.PortalIdentifierEnum;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

/**
 * @author Andreas Pursian
 *
 */
public interface PortalIdAssembler {
	
	public Byte[] createDTO(List<PortalIdentifier> portalIdList); 
	public List<PortalIdentifier> createDomainObj(Byte[] dto) throws EnumValueNotFoundException;
	public List<PortalIdentifier> updateDomainObj(Byte[] dto, List<PortalIdentifier> existingPortalIds) throws EnumValueNotFoundException;


}
