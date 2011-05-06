/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.SearchHistoryApplicant;
import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author mutabor
 *
 */
public class SearchHistoryApplicantDAOHibernate extends AbstractHibernateDAO<SearchHistoryApplicant, Long> implements SearchHistoryApplicantDAO{

	public SearchHistoryApplicantDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	@Override
	public SearchHistoryApplicant doRetrieve(Long searchHistoryApplicantId,
			boolean detached) {
		try {
			return super.doRetrieve(searchHistoryApplicantId, detached);
		} catch (Exception e) {
			return null;
		}
	}

}
