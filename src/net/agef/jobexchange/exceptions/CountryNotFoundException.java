/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;


/**
 * @author AGEF
 *
 */
public class CountryNotFoundException extends Exception {
	private Logger logger = Logger.getLogger(CountryNotFoundException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 5972800487215547843L;
	
	public CountryNotFoundException(){
		
	}

	public CountryNotFoundException(String countryValue){
		logger.error("Cant't find country: "+countryValue);	
	}
}
