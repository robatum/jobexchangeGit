/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.JobApplication;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class JobApplicationDAOHibernate extends AbstractHibernateDAO<JobApplication, Long> implements JobApplicationDAO{

	public JobApplicationDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

}
