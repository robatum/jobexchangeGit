/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;


/**
 * @author AGEF
 *
 */
public class TerritoryNotFoundException extends Exception{
	private Logger logger = Logger.getLogger(TerritoryNotFoundException.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 8482941191767765885L;
	
	public TerritoryNotFoundException(){
		
	}
	
	public TerritoryNotFoundException(String territoryValue){
		logger.error("Can't find territory: "+territoryValue);
	}

}
