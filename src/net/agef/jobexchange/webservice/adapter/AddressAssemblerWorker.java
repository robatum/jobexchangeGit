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
public class AddressAssemblerWorker implements AddressAssembler{
	
	private CountryAssembler ca;
	
	
	public AddressAssemblerWorker(CountryAssembler countryAssembler) {
		this.ca = countryAssembler;
	}


	@Override
	public AddressDTO createDTO(Address address) {
		AddressDTO dto = new AddressDTO();
		if(address!=null){
			dto.setAddress1(address.getAddress1());
			dto.setAddress2(address.getAddress2());
			dto.setCity(address.getCity());
			dto.setCountry(ca.createDTO(address.getCountry()));
			dto.setFaxNumber(address.getFaxNumber());
			dto.setFederalState(address.getFederalState());
			dto.setMobileNumber(address.getMobileNumber());
			dto.setPhoneNumber(address.getPhoneNumber());
			dto.setZipCode(address.getZipCode());
		}
		return dto;
	}

	@Override
	public Address createDomainObj(AddressDTO dto) throws CountryNotFoundException {
		Address address = new Address();

		if(dto!=null){
			address.setAddress1(dto.getAddress1());
			address.setAddress2(dto.getAddress2());
			address.setCity(dto.getCity());
			address.setCountry(ca.getDomainObj(dto.getCountry()));
			address.setFaxNumber(dto.getFaxNumber());
			address.setFederalState(dto.getFederalState());
			address.setMobileNumber(dto.getMobileNumber());
			address.setPhoneNumber(dto.getPhoneNumber());
			address.setZipCode(dto.getZipCode());
		}
		return address;
	}

	@Override
	public Address updateDomainObj(AddressDTO dto, Address existingAddress) throws CountryNotFoundException {
		Address address = existingAddress;
		
		if(dto!=null){
			address.setAddress1(dto.getAddress1());
			address.setAddress2(dto.getAddress2());
			address.setCity(dto.getCity());
			address.setCountry(ca.getDomainObj(dto.getCountry()));
			address.setFaxNumber(dto.getFaxNumber());
			address.setFederalState(dto.getFederalState());
			address.setMobileNumber(dto.getMobileNumber());
			address.setPhoneNumber(dto.getPhoneNumber());
			address.setZipCode(dto.getZipCode());
		}
		return address;
	}

}
