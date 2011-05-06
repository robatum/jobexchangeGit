package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.AddressEnum;
import net.agef.jobexchange.domain.ContactPerson;
import net.agef.jobexchange.domain.TitleEnum;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.webservice.entities.AddressDTO;
import net.agef.jobexchange.webservice.entities.ContactPersonDTO;

public class ContactPersonAssemblerWorker implements ContactPersonAssembler{

	private AddressAssembler ada;
	
	public ContactPersonAssemblerWorker(AddressAssembler addressAssembler){
		this.ada = addressAssembler;
	}
	
	@Override
	public ContactPersonDTO createDTO(ContactPerson contactPerson) {
		ContactPersonDTO dto = new ContactPersonDTO();
		
		if(contactPerson!=null){
			if(contactPerson.getAddresses()!=null){
				dto.setAddresses(contactPerson.getAddresses().toString());
			}
			if(contactPerson.getTitle()!=null){
				dto.setTitle(contactPerson.getTitle().toString());
			}
			dto.setEmailBusiness(contactPerson.getEmailBusiness());
			dto.setFamilyName(contactPerson.getFamilyName());
			dto.setFathersName(contactPerson.getFathersName());
			dto.setGivenName(contactPerson.getGivenName());
			dto.setInternet(contactPerson.getInternet());
			dto.setPosition(contactPerson.getPosition());
		}
		return dto;
	}
	
	@Override
	public ContactPersonDTO createDTOByUser(User user) {
		ContactPersonDTO dto = new ContactPersonDTO();
		if(user!=null){
			if(user.getAddresses()!=null){
				dto.setAddresses(user.getAddresses().toString());
			}
			if(user.getTitle()!=null){
				dto.setTitle(user.getTitle().toString());
			}
			dto.setEmailBusiness(user.getEmailBusiness());
			dto.setFamilyName(user.getFamilyName());
			dto.setFathersName(user.getFathersName());
			dto.setGivenName(user.getGivenName());
			dto.setInternet(user.getInternet());
			dto.setPosition(user.getPosition());
		}
		return dto;
	}

	@Override
	public ContactPerson createDomainObj(ContactPersonDTO dto, AddressDTO addressDTO) throws EnumValueNotFoundException, CountryNotFoundException {
		ContactPerson contactPerson = new ContactPerson();	
		
		if(dto!=null){
			if(addressDTO!=null){
				contactPerson.setContactPersonAddress(ada.createDomainObj(addressDTO));	
			}
			if(dto.getAddresses()!=null){
				contactPerson.setAddresses(AddressEnum.fromValue(dto.getAddresses()));
			}
			if(dto.getTitle()!=null){
				contactPerson.setTitle(TitleEnum.fromValue(dto.getTitle()));
			}
			contactPerson.setEmailBusiness(dto.getEmailBusiness());
			contactPerson.setFamilyName(dto.getFamilyName());
			contactPerson.setFathersName(dto.getFathersName());
			contactPerson.setGivenName(dto.getGivenName());
			contactPerson.setInternet(dto.getInternet());
			contactPerson.setPosition(dto.getPosition());
		}
		return contactPerson;
	}

	@Override
	public ContactPerson updateDomainObj(ContactPerson contactPersonDomain, ContactPersonDTO dto, AddressDTO addressDTO) throws EnumValueNotFoundException, CountryNotFoundException {
		ContactPerson contactPerson = contactPersonDomain;
		if(dto!=null){
			if(addressDTO!=null){
				contactPerson.setContactPersonAddress(ada.updateDomainObj(addressDTO, contactPerson.getContactPersonAddress()));	
			}
			if(dto.getAddresses()!=null){
				contactPerson.setAddresses(AddressEnum.fromValue(dto.getAddresses()));
			}
			if(dto.getTitle()!=null){
				contactPerson.setTitle(TitleEnum.fromValue(dto.getTitle()));
			}
			contactPerson.setEmailBusiness(dto.getEmailBusiness());
			contactPerson.setFamilyName(dto.getFamilyName());
			contactPerson.setFathersName(dto.getFathersName());
			contactPerson.setGivenName(dto.getGivenName());
			contactPerson.setInternet(dto.getInternet());
			contactPerson.setPosition(dto.getPosition());
		}
		return contactPerson;
	}

	

}
