/**
 * 
 */
package net.agef.jobexchange.application;

import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotDeletedException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;

/**
 * @author AGEF
 *
 */
public interface LoginUserWorker {
	
	public void addLoginUser(LoginUser user) throws ObjectNotSavedException, PassedAttributeIsNullException;
	public void deleteJobOffer(Long loginUserId) throws ObjectNotDeletedException, PassedAttributeIsNullException;
	public LoginUser getUserByName(String userName) throws LoginUserNotFoundException;
	public LoginUser getLoggedInUser() throws LoginUserNotFoundException;
	public Boolean isLoggedInUser();
	public String getLoggedInUserName();
	public void logoutUser();
}
