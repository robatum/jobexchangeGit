/**
 * 
 */
package net.agef.jobexchange.integration;


import java.util.List;

import net.agef.jobexchange.domain.Territory;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;



/**
 * @author Administrator
 *
 */
public class TerritoryDAOHibernate extends AbstractHibernateDAO<Territory, Long> implements TerritoryDAO{

	public TerritoryDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	@Override
	public Territory findTerritoryByName(String territoryName){
		List<Territory> territoryList = this.findByQuery("From Territory t WHERE t.nameEnglish = :territoryName ", new QueryParameter("territoryName", territoryName));
		if(!territoryList.isEmpty()){
			return territoryList.get(0);
		}
		return null;
	}

	@Override
	public Territory findTerritoryByISONumber(Integer isoNumber) {
		List<Territory> territoryList = this.findByQuery("From Territory t WHERE t.isoNumber = :isoNumber ", new QueryParameter("isoNumber", isoNumber));
		if(!territoryList.isEmpty()){
			return territoryList.get(0);
		}
		return null;
	}
	
	
}
