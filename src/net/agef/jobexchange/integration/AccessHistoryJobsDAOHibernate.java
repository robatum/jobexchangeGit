/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.AccessHistoryApplicant;
import net.agef.jobexchange.domain.AccessHistoryJobs;

import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author mutabor
 *
 */
public class AccessHistoryJobsDAOHibernate extends AbstractHibernateDAO<AccessHistoryJobs, Long> implements AccessHistoryJobsDAO{

	public AccessHistoryJobsDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	@Override
	public AccessHistoryJobs doRetrieve(Long accessHistoryJobsId,
			boolean detached) {
		try {
			return super.doRetrieve(accessHistoryJobsId, detached);
		} catch (Exception e) {
			return null;
		}
	}

}
