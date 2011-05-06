/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.OccupationalField;

/**
 * @author AGEF
 *
 */
public interface OccupationalFieldAssembler {
	
	public String createMainFromSubFieldDTO(OccupationalField field);
	public String createDTO(OccupationalField field); 
	public OccupationalField getDomainObj(String field);

}
