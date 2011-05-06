/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;


/**
 * @author AGEF
 *
 */
public class CobraUserNotFoundException extends Exception{
	private Logger logger = Logger.getLogger(CobraUserNotFoundException.class);	/**
	 * 
	 */
	private static final long serialVersionUID = 8299505980599991045L;

	public CobraUserNotFoundException(){
		
	}
	
	public CobraUserNotFoundException(String userId){
		logger.error("Cant't find cobraUser: "+userId);
		
	}
}
