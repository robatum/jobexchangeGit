/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.AccessHistoryApplicant;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author mutabor
 *
 */
public interface AccessHistoryApplicantDAO extends GenericDAO<AccessHistoryApplicant, Long>{
	
	@CommitAfter
	public AccessHistoryApplicant doSave(AccessHistoryApplicant accessHistoryApplicant);
	
	public AccessHistoryApplicant doRetrieve(Long accessHistoryApplicantId,boolean detached);
}
