/**
 * 
 */
package net.agef.jobexchange.integration;

import org.chenillekit.hibernate.daos.GenericDAO;

import net.agef.jobexchange.domain.DataProvider;



/**
 * @author AGEF
 *
 */
public interface DataProviderDAO extends GenericDAO<DataProvider, Long>{
	
	public DataProvider findDataProviderByName(String dataProviderName);

}
