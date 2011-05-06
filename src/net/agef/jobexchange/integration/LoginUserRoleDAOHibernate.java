/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.LoginUserRole;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class LoginUserRoleDAOHibernate extends AbstractHibernateDAO<LoginUserRole, Long> implements LoginUserRoleDAO{

	public LoginUserRoleDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

}
