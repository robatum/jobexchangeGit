/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.AccessHistoryJobs;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author mutabor
 *
 */
public interface AccessHistoryJobsDAO extends GenericDAO<AccessHistoryJobs, Long>{
	
	@CommitAfter
	public AccessHistoryJobs doSave(AccessHistoryJobs accesshHistoryJobs);
	
	public AccessHistoryJobs doRetrieve(Long accessHistoryJobsId,boolean detached);
}
