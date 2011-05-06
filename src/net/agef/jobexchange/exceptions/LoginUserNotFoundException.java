/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;


/**
 * @author AGEF
 *
 */
public class LoginUserNotFoundException extends Exception{
	private Logger logger = Logger.getLogger(LoginUserNotFoundException.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1413849611873505626L;
	
	public LoginUserNotFoundException(){
		
	}
	
	public LoginUserNotFoundException(String userName){
		logger.error("Can't find LoginUser: "+userName);
	}

}
