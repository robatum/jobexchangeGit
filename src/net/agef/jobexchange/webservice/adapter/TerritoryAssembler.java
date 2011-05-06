/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.exceptions.TerritoryNotFoundException;
import net.agef.jobexchange.webservice.entities.TerritoryDTO;

/**
 * @author AGEF
 *
 */
public interface TerritoryAssembler {
	
	public TerritoryDTO createDTO(Territory territory); 
	public Territory getDomainObj(TerritoryDTO dto) throws TerritoryNotFoundException;


}
