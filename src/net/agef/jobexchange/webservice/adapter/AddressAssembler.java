/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.webservice.entities.AddressDTO;


/**
 * @author AGEF
 *
 */
public interface AddressAssembler {

	public AddressDTO createDTO(Address address); 
	public Address createDomainObj(AddressDTO dto) throws CountryNotFoundException;
	public Address updateDomainObj(AddressDTO dto, Address existingAddress) throws CountryNotFoundException;

	
}
