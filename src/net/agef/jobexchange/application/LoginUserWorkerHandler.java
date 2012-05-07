/**
 * 
 */
package net.agef.jobexchange.application;

import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotDeletedException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.integration.LoginUserDAO;
import nu.localhost.tapestry5.springsecurity.services.LogoutService;

import org.apache.tapestry5.annotations.OnEvent;
import org.slf4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

/**
 * @author AGEF
 *
 */
public class LoginUserWorkerHandler implements LoginUserWorker{
	
	private Logger logger;
	private LoginUserDAO loginUserDAO;
	private LogoutService logoutService; 
	
	public LoginUserWorkerHandler(LoginUserDAO loginUserDAO, LogoutService logoutService, Logger logger){
		this.logger = logger;
		this.loginUserDAO = loginUserDAO;
		this.logoutService = logoutService;
	}
	
	public void addLoginUser(LoginUser user) throws ObjectNotSavedException, PassedAttributeIsNullException{
			if (user != null) {
				try {
					loginUserDAO.doSave(user);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ObjectNotSavedException();
				}
			} else throw new PassedAttributeIsNullException();
	}
	
	
	public void deleteLoginUser(Long loginUserId) throws ObjectNotDeletedException, PassedAttributeIsNullException{
		if (loginUserId != null) {
			try {
				loginUserDAO.doDelete(loginUserId);
			} catch (Exception e) {
				throw new ObjectNotDeletedException();
			}
		}else throw new PassedAttributeIsNullException();
	
	}
	
	public LoginUser getUserByName(String userName) throws LoginUserNotFoundException{		
		LoginUser user = loginUserDAO.findLoginUserByName(userName);
		if(user!=null){
			return user;
		} else throw new LoginUserNotFoundException(userName);
	}
	
	public LoginUser getLoggedInUser() throws LoginUserNotFoundException{
		Authentication myAuth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Username :"+ myAuth.getName());
		LoginUser user = this.getUserByName(myAuth.getName());
		return user;
	}
	
	public Boolean isLoggedInUser(){
		Authentication myAuth = SecurityContextHolder.getContext().getAuthentication();
		return (myAuth!=null) || (myAuth.isAuthenticated() && !myAuth.getName().equals("anonymous"));
	}
	
	public String getLoggedInUserName(){
		Authentication myAuth = SecurityContextHolder.getContext().getAuthentication();
		if((myAuth!=null) || (myAuth.isAuthenticated() && !myAuth.getName().equals("anonymous"))){
			return myAuth.getName();
		}
		return null;
	}
	
	@OnEvent(component = "logout")
    public void logoutUser() {
        logoutService.logout();
    }

}
