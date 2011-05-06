/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;


/**
 * @author AGEF
 *
 */
public class InwentUserNotFoundException extends Exception{
	private Logger logger = Logger.getLogger(InwentUserNotFoundException.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -311099966131172035L;

	public InwentUserNotFoundException(){
		
	}
	
	public InwentUserNotFoundException(String userId){
		logger.error("Inwent user with id: "+userId+" doesn't exist.");
		
	}
}
