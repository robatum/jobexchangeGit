/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;


import net.agef.jobexchange.domain.ContactPerson;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.webservice.entities.AddressDTO;
import net.agef.jobexchange.webservice.entities.ContactPersonDTO;

/**
 * @author AGEF
 *
 */
public interface ContactPersonAssembler {

	public ContactPersonDTO createDTO(ContactPerson contactPerson); 
	public ContactPersonDTO createDTOByUser(User user); 	
	public ContactPerson createDomainObj(ContactPersonDTO dto, AddressDTO addressDTO) throws CountryNotFoundException, EnumValueNotFoundException;
	public ContactPerson updateDomainObj(ContactPerson contactPersonDomain, ContactPersonDTO dto, AddressDTO addressDTO) throws CountryNotFoundException, EnumValueNotFoundException;
	
}
