/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;

/**
 * @author AGEF
 * 
 */
public class DataProviderNotFoundException extends Exception {
	private Logger logger = Logger.getLogger(DataProviderNotFoundException.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = -4245594053752174830L;

	public DataProviderNotFoundException() {

	}

	public DataProviderNotFoundException(String providerName) {
		logger.error("Can't find dataprovider: " + providerName);
	}

}
