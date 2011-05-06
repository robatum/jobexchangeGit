/**
 * 
 */
package net.agef.jobexchange.integration;



import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.JobActiveEnum;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.User;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author Administrator
 *
 */
public interface UserDAO extends GenericDAO<User, Long>{
	
	public User findCobraUserByID(Long cobraSuperID, boolean isOrganisationUser);
	
	public User findAPDUserByID(Long apdUserID);
	
	public User doRetrieve(Long userId, boolean detached);
	
	@CommitAfter
	public User doSave(User user);
	
	@CommitAfter
	public User doRefresh(User user);
	
	@CommitAfter
	public void doDelete(User user);
	
	@CommitAfter
	public void doDelete(Long  userId);

	@CommitAfter
	public User findInwentUserByID(Long applicantProfileOwnerId);
	
}
