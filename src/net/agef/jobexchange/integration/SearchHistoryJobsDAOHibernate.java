/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.SearchHistoryJobs;
import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author mutabor
 *
 */
public class SearchHistoryJobsDAOHibernate extends AbstractHibernateDAO<SearchHistoryJobs, Long> implements SearchHistoryJobsDAO{

	public SearchHistoryJobsDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	@Override
	public SearchHistoryJobs doRetrieve(Long searchHistoryJobsId,
			boolean detached) {
		try {
			return super.doRetrieve(searchHistoryJobsId, detached);
		} catch (Exception e) {
			return null;
		}
	}

}
