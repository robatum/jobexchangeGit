/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.webservice.entities.AbstractUserRoleDTO;
import net.agef.jobexchange.webservice.entities.UserDTO;

/**
 * @author AGEF
 *
 */
public interface UserAssembler {
	
	public UserDTO createDTO(User user); 
	public User createDomainObj(UserDTO dto)throws EnumValueNotFoundException, CountryNotFoundException;
	public User updateDomainObjByApdId(UserDTO dto, Long apdUserId)throws APDUserNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
	public User updateDomainObjByCobraId(UserDTO dto, Long cobraUserId) throws CobraUserNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
	public User updateDomainObjRole(AbstractUserRoleDTO dto, Long userId)throws APDUserNotFoundException, EnumValueNotFoundException, CountryNotFoundException;

}
