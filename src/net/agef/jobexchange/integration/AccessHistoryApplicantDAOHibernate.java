/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.AccessHistoryApplicant;
import net.agef.jobexchange.domain.SearchHistoryApplicant;

import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author mutabor
 *
 */
public class AccessHistoryApplicantDAOHibernate extends AbstractHibernateDAO<AccessHistoryApplicant, Long> implements AccessHistoryApplicantDAO{

	public AccessHistoryApplicantDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	@Override
	public AccessHistoryApplicant doRetrieve(Long accessHistoryApplicantId,
			boolean detached) {
		try {
			return super.doRetrieve(accessHistoryApplicantId, detached);
		} catch (Exception e) {
			return null;
		}
	}

}
