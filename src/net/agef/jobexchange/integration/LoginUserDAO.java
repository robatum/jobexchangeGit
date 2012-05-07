/**
 * 
 */
package net.agef.jobexchange.integration;


import net.agef.jobexchange.domain.LoginUser;


import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author AGEF
 *
 */
public interface LoginUserDAO extends GenericDAO<LoginUser, Long>{

	public LoginUser findLoginUserByName(String userName);
	
	@CommitAfter
	public LoginUser doSave(LoginUser loginUser);
	
	@CommitAfter
	public void doDelete(LoginUser loginUser);
	
}
