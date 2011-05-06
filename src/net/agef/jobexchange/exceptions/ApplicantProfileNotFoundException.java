/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;

/**
 * @author AGEF
 *
 */
public class ApplicantProfileNotFoundException extends Exception{
	private Logger logger = Logger.getLogger(ApplicantProfileNotFoundException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 4172502300985584189L;
	
	public ApplicantProfileNotFoundException(){
		
	}
	
	public ApplicantProfileNotFoundException(String profileId){
		logger.error("Applicant profile with id: "+profileId+" cant't be found.");
		
	}

}
