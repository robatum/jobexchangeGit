/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.webservice.entities.CountryDTO;



/**
 * @author AGEF
 *
 */
public interface CountryAssembler {
	
	public CountryDTO createDTO(Country country); 
	public Country getDomainObj(CountryDTO dto) throws CountryNotFoundException;

}
