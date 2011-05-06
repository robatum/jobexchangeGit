/**
 * 
 */
package net.agef.jobexchange.integration;


import net.agef.jobexchange.domain.SearchHistoryJobs;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author mutabor
 *
 */
public interface SearchHistoryJobsDAO extends GenericDAO<SearchHistoryJobs, Long>{
	
	@CommitAfter
	public SearchHistoryJobs doSave(SearchHistoryJobs searchHistoryJobs);

	public SearchHistoryJobs doRetrieve(Long searchHistoryJobsId, boolean detached);
	
	@CommitAfter
	public SearchHistoryJobs doRefresh(SearchHistoryJobs searchHistoryJobs);

}
