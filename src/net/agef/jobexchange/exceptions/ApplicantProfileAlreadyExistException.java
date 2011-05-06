/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author AGEF
 *
 */
public class ApplicantProfileAlreadyExistException extends Exception{
	private Logger logger = Logger.getLogger(ApplicantProfileAlreadyExistException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 3721911109559397846L;

	public ApplicantProfileAlreadyExistException(){
		
	}
	
	public ApplicantProfileAlreadyExistException(String userId){
		logger.error("Applicant profile for user: "+userId+" already exists.");
	}

}
