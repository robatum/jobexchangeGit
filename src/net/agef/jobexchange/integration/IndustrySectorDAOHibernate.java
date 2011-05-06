/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.List;

import net.agef.jobexchange.domain.IndustrySector;

import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class IndustrySectorDAOHibernate extends AbstractHibernateDAO<IndustrySector, Long> implements IndustrySectorDAO{

	public IndustrySectorDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}
	
	@Override
	public IndustrySector findIndustrySectorById(Long sectorId) {
		List<IndustrySector> sectorList = this.findByQuery("From IndustrySector i WHERE i.sectorId = :sectorId ", new QueryParameter("sectorId", sectorId));
		if(!sectorList.isEmpty()){
			return sectorList.get(0);
		}
		return null;
	}

	@Override
	public IndustrySector findIndustrySectorByName(String sectorName) {
		List<IndustrySector> sectorList = this.findByQuery("From IndustrySector i WHERE i.sectorNameGerman = :sectorName or i.sectorNameEnglish = :sectorName", new QueryParameter("sectorName", sectorName));
		if(!sectorList.isEmpty()){
			return sectorList.get(0);
		}
		return null;
	}

}
