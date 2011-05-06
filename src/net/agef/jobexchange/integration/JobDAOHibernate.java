package net.agef.jobexchange.integration;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.JobActiveEnum;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.Territory;

import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;

public class JobDAOHibernate extends AbstractHibernateDAO<JobImpl, Long> implements JobDAO{

	private static final Integer jobOfferIdCorrection = 23;
	private Session session;
	private Logger logger;
	
	public JobDAOHibernate(Logger logger, Session session) {
		super(logger, session);
		this.session = session;
		this.logger = logger;
	}

	@Override
	public Collection<JobImpl> findJobOffersByUser(Long userId) {
		Collection<JobImpl> jobOffers = this.findByQuery("From JobImpl j WHERE  j.jobOfferOwner.id = :userId order by j.countryOfEmployment.shortEnglishName", new QueryParameter("userId", userId));
		return jobOffers;
	} 
	
	
	public Collection<JobImpl> findByCriteria(String criteria){
		Collection<JobImpl> jobOffers = this.findByQuery("From JobImpl j WHERE  j.organisationName = :orgName", new QueryParameter("orgName", criteria));
		return jobOffers;
	}
	
	
	public Collection<JobImpl> findJobOffersByUserAndOnlineState(Long userId, Boolean onlineState){
		Collection<JobImpl> jobOffers = this.findByQuery("From JobImpl j WHERE  j.jobOfferOwner.id = :userId and j.onlineStatus = :onlineState", new QueryParameter("userId", userId), new QueryParameter("onlineState", onlineState));
		return jobOffers;
	}
	
	
	@Override
	public JobImpl findJobOfferByCobraId(Long cobraJobId){
		List<JobImpl> jobs = this.findByQuery("From JobImpl j WHERE  j.cobraJobId = :cobraJobId", new QueryParameter("cobraJobId", cobraJobId));
		if (!jobs.isEmpty()) {
			return jobs.remove(0);
		} else return null;
	}
	
	public Collection<JobImpl> findOutdatedJobOffers(){
		Collection<JobImpl> jobOffers = null;
		try {
			jobOffers = this.findByQuery("From JobImpl j WHERE  j.jobOfferExpireDate < :today and j.onlineStatus = :onlineState and j.cobraJobId is null", new QueryParameter("today", new Date()), new QueryParameter("onlineState", true));
		} catch (Exception e) {
			System.out.println("There was a problem while executing query for outdated job offers");
			e.printStackTrace();
		}
		return jobOffers;
	}
	
	public Collection<JobImpl> findUpdatedJobOffers(){
		Collection<JobImpl> jobOffers = null;
		try {
			jobOffers = this.findByQuery("From JobImpl j WHERE  j.jobOfferExpireDate >= :today and j.onlineStatus = :onlineState and j.cobraJobId is null", new QueryParameter("today", new Date()), new QueryParameter("onlineState", false));
		} catch (Exception e) {
			System.out.println("There was a problem while executing query for updated job offers");
			e.printStackTrace();
		}
		return jobOffers;
	}
	
//	@Override
//	public JobImpl doRefresh(JobImpl jobOffer){
//		System.out.println("Fuehre Update durch fuer JobOffer"+jobOffer.getJobOfferId());
//		return super.doSave(jobOffer);
//	}
	
	@Override
	public JobImpl doRetrieve(Long jobOfferId, boolean detached) {
//		List<JobImpl> jobs = this.findByQuery("From JobImpl j WHERE  j.cobraJobId = :cobraJobId", new QueryParameter("cobraJobId", jobOfferId-jobOfferIdCorrection));
//		if (!jobs.isEmpty()) {
//			return jobs.remove(0);
//		} else return null;
		
		try {
			return super.doRetrieve(jobOfferId - jobOfferIdCorrection, detached);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<JobImpl> findAll(){
		Collection<JobImpl> jobs = this.findByQuery("From JobImpl");
		if (!jobs.isEmpty()) {
			return (List<JobImpl>) jobs;
		} else return null;
	}

//	@Override
//	public void doDelete(JobImpl jobOffer) {
//		super.doDelete(jobOffer.getJobOfferId()-jobOfferIdCorrection);
//	}
	
	@Override
	public void doDelete(Long jobOfferId) {	
			super.doDelete(jobOfferId);
	}
	
	@Override
	public void doDeleteByCobraId(Long cobraJobId) {
			JobImpl job = this.findJobOfferByCobraId(cobraJobId);
			this.doDelete(job);
			//this.deleteByQuery("From JobImpl j Where j.cobraJobId = :cobraJobId",new QueryParameter("cobraJobId", cobraJobId));	
	}

	@Override
	public Collection<JobImpl> findJobOffersByUserAndCriteria(Long userId, JobActiveEnum jobActive, Country country, Territory territory, int numberOfResults, int indexStart) {
		Criteria query = this.session.createCriteria(JobImpl.class).add(Restrictions.eq("jobOfferOwner.id", userId));
		if(jobActive == JobActiveEnum.ACTIVE){
			query.add(Restrictions.ge("jobOfferExpireDate", new Date()));
			logger.info("Searching for ACTIVE jobs");
		}
		else if(jobActive == JobActiveEnum.ENDED){
			query.add(Restrictions.lt("jobOfferExpireDate", new Date()));
			logger.info("Searching for ENDED jobs");
		}
		if (country != null){
			logger.info("Searching for jobs in " + country.getIsoNameLong());
			query.add(Restrictions.eq("countryOfEmployment", country));
		}
		else if (territory != null){
			query.add(Restrictions.in("countryOfEmployment", territory.getRelatedCountries()));

			logger.info("Searching for jobs in " + territory.getRelatedCountries());
		}
		
		// applying the restrictions to the number of results
		query.setFirstResult(indexStart);
		query.setMaxResults(numberOfResults);
		
		Collection<JobImpl> jobOffers = (Collection<JobImpl>) query.list();
		return jobOffers;
	}

}
