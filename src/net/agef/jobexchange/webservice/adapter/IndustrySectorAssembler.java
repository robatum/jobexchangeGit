/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.IndustrySector;

/**
 * @author AGEF
 *
 */
public interface IndustrySectorAssembler {

	public String createDTO(IndustrySector sector); 
	public IndustrySector getDomainObj(String sector);
	
}
