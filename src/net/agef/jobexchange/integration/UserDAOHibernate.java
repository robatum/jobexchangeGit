/**
 * 
 */
package net.agef.jobexchange.integration;


import java.util.List;

import net.agef.jobexchange.domain.AlumniRole;
import net.agef.jobexchange.domain.OrganisationRole;
import net.agef.jobexchange.domain.User;

import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;

/**
 * @author Administrator
 *
 */
public class UserDAOHibernate extends AbstractHibernateDAO<User, Long> implements UserDAO{
	
	private Session session;
	
	public UserDAOHibernate(Logger logger, Session session)
    {
        super(logger, session);
        this.session = session;
    }
	
	public User findCobraUserByID(Long cobraSuperID, boolean isOrganisationUser) {
		List<User> user = this.findByQuery("From User u WHERE  u.cobraSuperId = :cobraId", new QueryParameter("cobraId", cobraSuperID));
		if (!user.isEmpty()) {
			for (int i =0; i < user.size();i++){
				if((user.get(i).getUserRole() instanceof OrganisationRole) && isOrganisationUser){
					return user.remove(i);
				} else if((user.get(i).getUserRole() instanceof AlumniRole)  && !isOrganisationUser){
						return user.remove(i);
					}
			}
		} 
		return null;
	}
	
	public User findPortalUserByID(Long portalUserID) {
		List<User> user = this.findByQuery("From User u WHERE  u.portalUserId = :portalId", new QueryParameter("portalId", portalUserID));
		if (!user.isEmpty()) {
			return user.remove(0);
		} else return null;
	}
	
	@Override
	public User doRetrieve(Long userId, boolean detached){
		try {
			return super.doRetrieve(userId, detached);
		} catch (Exception e) {
			return null;
		}
	}

//	@Override
//	public User findInwentUserByID(Long applicantProfileOwnerId) {
//		try {
//			return (User) session.createCriteria(User.class).add(Restrictions.eq("inwentUserId", applicantProfileOwnerId)).uniqueResult();
//		}
//		catch (Exception e){
//			return null;
//		}
//	}

}
