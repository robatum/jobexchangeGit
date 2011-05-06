/**
 * 
 */
package net.agef.jobexchange.integration;


import java.util.List;

import net.agef.jobexchange.domain.DataProvider;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class DataProviderDAOHibernate extends AbstractHibernateDAO<DataProvider, Long> implements DataProviderDAO{

	public DataProviderDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}
	
	public DataProvider findDataProviderByName(String dataProviderName){
		List<DataProvider> dataProvider = this.findByQuery("From DataProvider dp WHERE  dp.providerName = :dataProviderName", new QueryParameter("dataProviderName", dataProviderName));
		if (!dataProvider.isEmpty()) {
			return dataProvider.get(0);
		} else return null;
	}

}
