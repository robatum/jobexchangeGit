package net.agef.jobexchange.integration;

import java.util.Collection;
import java.util.List;


import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;


import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.JobActiveEnum;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.Territory;

public interface JobDAO extends GenericDAO<JobImpl, Long>{

	public Collection<JobImpl> findByCriteria(String criteria);
	
	public Collection<JobImpl> findJobOffersByUser(Long userId);
	
	public List<JobImpl> findAll();
	
	public Collection<JobImpl> findJobOffersByUserAndOnlineState(Long userId, Boolean onlineState);
	
	public Collection<JobImpl> findOutdatedJobOffers();
	
	public Collection<JobImpl> findUpdatedJobOffers();
	
	public JobImpl findJobOfferByCobraId(Long cobraJobId);
	
	@CommitAfter
	public JobImpl doRetrieve(Long jobOfferId, boolean detached);

	@CommitAfter
	public JobImpl doSave(JobImpl jobOffer);
	
	@CommitAfter
	public JobImpl doRefresh(JobImpl jobOffer);
	
	@CommitAfter
	public void doDelete(Long jobOfferId);
	
	@CommitAfter
	public void doDelete(JobImpl jobOffer);
	
	@CommitAfter
	public void doDeleteByCobraId(Long cobraJobId);

	@CommitAfter
	public Collection<JobImpl> findJobOffersByUserAndCriteria(Long userId, JobActiveEnum jobActive, Country country, Territory territory, int numberOfResults, int indexStart);
}
