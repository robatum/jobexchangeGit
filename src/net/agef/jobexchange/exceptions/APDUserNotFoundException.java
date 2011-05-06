/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;


/**
 * @author AGEF
 *
 */
public class APDUserNotFoundException extends Exception{
	private Logger logger = Logger.getLogger(APDUserNotFoundException.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -311099966131172035L;

	public APDUserNotFoundException(){
		
	}
	
	public APDUserNotFoundException(String userId){
		logger.error("APD user with id: "+userId+" doesn't exist.");
		
	}
}
