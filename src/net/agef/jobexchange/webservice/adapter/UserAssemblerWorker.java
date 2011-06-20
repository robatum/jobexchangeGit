/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;



import net.agef.jobexchange.domain.AddressEnum;
import net.agef.jobexchange.domain.AlumniRole;
import net.agef.jobexchange.domain.EmployeeAmountEnum;
import net.agef.jobexchange.domain.OrganisationRole;
import net.agef.jobexchange.domain.OrganisationRoleData;
import net.agef.jobexchange.domain.TitleEnum;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.integration.UserDAO;
import net.agef.jobexchange.webservice.adapter.util.DateUtil;
import net.agef.jobexchange.webservice.entities.AbstractUserRoleDTO;
import net.agef.jobexchange.webservice.entities.AlumniRoleDTO;
import net.agef.jobexchange.webservice.entities.OrganisationRoleDTO;
import net.agef.jobexchange.webservice.entities.UserDTO;

/**
 * @author AGEF
 *
 */
public class UserAssemblerWorker implements UserAssembler{
	
	private UserDAO userDAO;
	private IndustrySectorAssembler industryAssembler;
	private AddressAssembler ada;
	private CountryAssembler ca;
	
	public UserAssemblerWorker(AddressAssembler addressAssembler, CountryAssembler countryAssembler, UserDAO userDAO, IndustrySectorAssembler industryAssembler) {
		this.userDAO = userDAO;
		this.ada = addressAssembler;
		this.ca = countryAssembler;
		this.industryAssembler = industryAssembler;
	}

	@Override
	public UserDTO createDTO(User user) {
		UserDTO dto = new UserDTO();
		
		if(user!=null){
			if(user.getAddresses()!=null){
				dto.setAddresses(user.getAddresses().toString());
			}
//			dto.setApdUserId(user.getApdUserId());
			dto.setCobraUserId(user.getCobraSuperId());
			dto.setPortalUserId(user.getPortalUserId());
			dto.setPortalId(user.getPortalId().byteValue());
			dto.setCurrentAddress(ada.createDTO(user.getCurrentContactAddress()));
			dto.setAlternativeAddress(ada.createDTO(user.getAlternativeContactAddress()));
			dto.setCitizenship1(user.getCitizenship1());
			dto.setCitizenship2(user.getCitizenship2());
			dto.setDateOfBirth(DateUtil.date2Calendar(user.getDateOfBirth()));
			dto.setEmailBusiness(user.getEmailBusiness());
			dto.setEmailPrivate(user.getEmailPrivate());
			dto.setFamilyName(user.getFamilyName());
			dto.setFathersName(user.getFathersName());
			dto.setPosition(user.getPosition());
			dto.setGivenName(user.getGivenName());
			dto.setInternet(user.getInternet());
			if (user.getNationality() != null){
				dto.setNationality(ca.createDTO(user.getNationality()));
			}
			if (user.getTitle()!=null){
				dto.setTitle(user.getTitle().toString());
			}
			if(user.getUserRole()instanceof OrganisationRole){
				OrganisationRoleDTO orgRole = new OrganisationRoleDTO();
				orgRole.setOrganisationDescription(user.getUserRoleData().getOrganisationDescription());
				orgRole.setOrganisationName(user.getUserRoleData().getOrganisationName());
				orgRole.setOrganisationIndustrySector(user.getUserRoleData().getIndustrySector().getId().toString());
				((OrganisationRoleDTO)orgRole).setOrganisationEmployeeAmount(((OrganisationRoleData)user.getUserRoleData()).getOrganisationEmployeeAmount().toString());
				dto.setUserRole(orgRole);
			} else {
				AlumniRoleDTO orgRole = new AlumniRoleDTO();
				orgRole.setOrganisationDescription(user.getUserRoleData().getOrganisationDescription());
				orgRole.setOrganisationName(user.getUserRoleData().getOrganisationName());
				orgRole.setOrganisationIndustrySector(user.getUserRoleData().getIndustrySector().getId().toString());
				dto.setUserRole(orgRole);
			}
		} else
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Hier muss noch einmal korrekt abgefragt werden was los ist wenn kein user übergeben wurde
				e.printStackTrace();
			}
		return dto;
	}

	@Override
	public User createDomainObj(UserDTO dto) throws EnumValueNotFoundException, CountryNotFoundException {
		User user;
		if(dto.getUserRole() instanceof OrganisationRoleDTO){
			user = new User(new OrganisationRole());
			if(dto.getUserRole()!= null){
				user.getUserRoleData().setOrganisationDescription(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationName());
				if(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()));
				}
				if(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationEmployeeAmount() != null){
					((OrganisationRoleData)user.getUserRoleData()).setOrganisationEmployeeAmount(EmployeeAmountEnum.fromValue(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationEmployeeAmount()));
				}
					
			}
		} else {
			user = new User(new AlumniRole());
			if(dto.getUserRole()!= null){
				user.getUserRoleData().setOrganisationDescription(((AlumniRoleDTO)dto.getUserRole()).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((AlumniRoleDTO)dto.getUserRole()).getOrganisationName());
				if(((AlumniRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((AlumniRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()));
				}
			}
		}
		
		
		if(dto!=null){
			if(dto.getAddresses() != null){
				user.setAddresses(AddressEnum.fromValue(dto.getAddresses()));
			}
			user.setCobraSuperId(dto.getCobraUserId());
//			user.setApdUserId(dto.getApdUserId());
			user.setPortalUserId(dto.getPortalUserId());
			user.setPortalId(dto.getPortalId().longValue());
			user.setAddress1(ada.createDomainObj(dto.getCurrentAddress()));
			user.setAddress2(ada.createDomainObj(dto.getAlternativeAddress()));
			user.setCitizenship1(dto.getCitizenship1());
			user.setCitizenship2(dto.getCitizenship2());
			user.setDateOfBirth(DateUtil.calendar2Date(dto.getDateOfBirth()));
			user.setEmailBusiness(dto.getEmailBusiness());
			user.setEmailPrivate(dto.getEmailPrivate());
			user.setFamilyName(dto.getFamilyName());
			user.setFathersName(dto.getFathersName());
			user.setGivenName(dto.getGivenName());
			user.setPosition(dto.getPosition());
			user.setInternet(dto.getInternet());
			if (dto.getNationality() != null){
				user.setNationality(ca.getDomainObj(dto.getNationality()));
			}
			if(dto.getTitle()!=null){
				user.setTitle(TitleEnum.fromValue(dto.getTitle()));
			}
		}
		return user;
	}

	@Override
	public User updateDomainObjByApdId(UserDTO dto, Long portalUserId) throws APDUserNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		User user = userDAO.findPortalUserByID(portalUserId);
		
		if(dto.getUserRole()instanceof OrganisationRoleDTO){
			if(dto.getUserRole()!= null){
				user.getUserRoleData().setOrganisationDescription(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationName());
				if(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()));
				}
				if(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationEmployeeAmount() != null){
					((OrganisationRoleData)user.getUserRoleData()).setOrganisationEmployeeAmount(EmployeeAmountEnum.fromValue(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationEmployeeAmount()));
				}
			}
		}else {
			if(dto.getUserRole()!= null){
				user.getUserRoleData().setOrganisationDescription(((AlumniRoleDTO)dto.getUserRole()).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((AlumniRoleDTO)dto.getUserRole()).getOrganisationName());
				if(((AlumniRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((AlumniRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()));
				}
			}
		}
		
		if(user!=null){
			if(dto.getAddresses() != null){
				user.setAddresses(AddressEnum.fromValue(dto.getAddresses()));
			}
//			user.setApdUserId(dto.getUserId()); // nicht updaten, da unveraenderlich
			user.setCobraSuperId(dto.getCobraUserId());
//			user.setPortalUserId(dto.getPortalUserId()); // nicht updaten, da unveraenderlich
			dto.setPortalId(user.getPortalId().byteValue());
			user.setAddress1(ada.updateDomainObj(dto.getCurrentAddress(),user.getAddress1()));
			user.setAddress2(ada.updateDomainObj(dto.getAlternativeAddress(),user.getAddress2()));
			user.setCitizenship1(dto.getCitizenship1());
			user.setCitizenship2(dto.getCitizenship2());
			user.setDateOfBirth(DateUtil.calendar2Date(dto.getDateOfBirth()));
			user.setEmailBusiness(dto.getEmailBusiness());
			user.setEmailPrivate(dto.getEmailPrivate());
			user.setFamilyName(dto.getFamilyName());
			user.setFathersName(dto.getFathersName());
			user.setGivenName(dto.getGivenName());
			user.setPosition(dto.getPosition());
			user.setInternet(dto.getInternet());
			if (dto.getNationality() != null){
				user.setNationality(ca.getDomainObj(dto.getNationality()));
			}
			if(dto.getTitle()!=null){
				user.setTitle(TitleEnum.fromValue(dto.getTitle()));
			}
		}
		return user;
	}
	
	@Override
	public User updateDomainObjByCobraId(UserDTO dto, Long cobraUserId) throws CobraUserNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		User user;
		
		if(dto.getUserRole()instanceof OrganisationRoleDTO){
			user = userDAO.findCobraUserByID(cobraUserId,true);
			if(user != null && dto.getUserRole()!= null){
				user.getUserRoleData().setOrganisationDescription(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationName());
				if(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()));
				}
				if(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationEmployeeAmount() != null){
					((OrganisationRoleData)user.getUserRoleData()).setOrganisationEmployeeAmount(EmployeeAmountEnum.fromValue(((OrganisationRoleDTO)dto.getUserRole()).getOrganisationEmployeeAmount()));
				}
			}
		}else {
			user = userDAO.findCobraUserByID(cobraUserId,false);
			if(user != null && dto.getUserRole()!= null){
				user.getUserRoleData().setOrganisationDescription(((AlumniRoleDTO)dto.getUserRole()).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((AlumniRoleDTO)dto.getUserRole()).getOrganisationName());
				if(((AlumniRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((AlumniRoleDTO)dto.getUserRole()).getOrganisationIndustrySector()));
				}
			}
		}
		
		if(user!=null){
			if(dto.getAddresses() != null){
				user.setAddresses(AddressEnum.fromValue(dto.getAddresses()));
			}
//			user.setApdUserId(dto.getApdUserId()); 
			//user.setCobraSuperId(dto.getCobraUserId()); // nicht updaten, da unveraenderlich
			user.setAddress1(ada.updateDomainObj(dto.getCurrentAddress(),user.getAddress1()));
			user.setAddress2(ada.updateDomainObj(dto.getAlternativeAddress(),user.getAddress2()));
			user.setCitizenship1(dto.getCitizenship1());
			user.setCitizenship2(dto.getCitizenship2());
			user.setDateOfBirth(DateUtil.calendar2Date(dto.getDateOfBirth()));
			user.setEmailBusiness(dto.getEmailBusiness());
			user.setEmailPrivate(dto.getEmailPrivate());
			user.setFamilyName(dto.getFamilyName());
			user.setFathersName(dto.getFathersName());
			user.setGivenName(dto.getGivenName());
			user.setPosition(dto.getPosition());
			user.setInternet(dto.getInternet());
			if (dto.getNationality() != null){
				user.setNationality(ca.getDomainObj(dto.getNationality()));
			}
			if(dto.getTitle()!=null){
				user.setTitle(TitleEnum.fromValue(dto.getTitle()));
			}
		} else throw new CobraUserNotFoundException();
		return user;
	}
	
	

	@Override
	public User updateDomainObjRole(AbstractUserRoleDTO dto, Long portalUserId) throws APDUserNotFoundException, EnumValueNotFoundException {
		User user = userDAO.findPortalUserByID(portalUserId);
		
		if(dto instanceof OrganisationRoleDTO){
			if(dto != null){
				user.setUserRole(new OrganisationRole());
				user.getUserRoleData().setOrganisationDescription(((OrganisationRoleDTO)dto).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((OrganisationRoleDTO)dto).getOrganisationName());
				if(((OrganisationRoleDTO)dto).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((OrganisationRoleDTO)dto).getOrganisationIndustrySector()));
				}
				if(((OrganisationRoleDTO)dto).getOrganisationEmployeeAmount() != null){
					((OrganisationRoleData)user.getUserRoleData()).setOrganisationEmployeeAmount(EmployeeAmountEnum.fromValue(((OrganisationRoleDTO)dto).getOrganisationEmployeeAmount()));
				}
			}
		}else {
			if(dto != null){
				user.setUserRole(new AlumniRole());
				user.getUserRoleData().setOrganisationDescription(((AlumniRoleDTO)dto).getOrganisationDescription());
				user.getUserRoleData().setOrganisationName(((AlumniRoleDTO)dto).getOrganisationName());
				if(((AlumniRoleDTO)dto).getOrganisationIndustrySector()!=null){
					user.getUserRoleData().setIndustrySector(industryAssembler.getDomainObj(((AlumniRoleDTO)dto).getOrganisationIndustrySector()));
				}
			}
		}
		return user;
	}


}
