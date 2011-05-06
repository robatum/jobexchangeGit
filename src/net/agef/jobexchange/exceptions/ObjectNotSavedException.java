/**
 * 
 */
package net.agef.jobexchange.exceptions;

import org.apache.log4j.Logger;

/**
 * @author AGEF
 * 
 */
public class ObjectNotSavedException extends Exception {
	private Logger logger = Logger.getLogger(ObjectNotSavedException.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = -4576186737274820490L;

	public ObjectNotSavedException() {

	}

	public ObjectNotSavedException(String objectId) {
		logger.error("Object with id: " + objectId + " could not be saved.");
	}
}
