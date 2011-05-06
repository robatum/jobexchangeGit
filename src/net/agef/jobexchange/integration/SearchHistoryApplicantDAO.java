/**
 * 
 */
package net.agef.jobexchange.integration;


import net.agef.jobexchange.domain.SearchHistoryApplicant;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author mutabor
 *
 */
public interface SearchHistoryApplicantDAO extends GenericDAO<SearchHistoryApplicant, Long>{
	
	@CommitAfter
	public SearchHistoryApplicant doSave(SearchHistoryApplicant searchHistoryApplicant);
	
	public SearchHistoryApplicant doRetrieve(Long searchHistoryApplicantId, boolean detached);
	
	@CommitAfter
	public SearchHistoryApplicant doRefresh(SearchHistoryApplicant searchHistoryApplicant);

}
