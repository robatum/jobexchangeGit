/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.List;

import net.agef.jobexchange.domain.LoginUser;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class LoginUserDAOHibernate extends AbstractHibernateDAO<LoginUser, Long> implements LoginUserDAO{

	public LoginUserDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}
	
	public LoginUser findLoginUserByName(String userName){
		List<LoginUser> user = this.findByQuery("From LoginUser lu WHERE  lu.username = :userName", new QueryParameter("userName", userName));
		if (!user.isEmpty()) {
			return user.remove(0);
		} else return null;
	}
	

}
