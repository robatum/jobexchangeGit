package net.agef.jobexchange.application;


import java.util.Collection;
import java.util.List;

import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Currency;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.JobActiveEnum;
import net.agef.jobexchange.domain.JobApplication;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.webservice.entities.JobSearchResultDTO;


public interface JobWorker {

	
	public Collection<JobImpl> getAllJobOffers();
	
	public Collection<JobImpl> getJobOffersByPortalUser(Long userId) throws APDUserNotFoundException;
	
	public Long addJobOffer(JobImpl jobOffer) throws ObjectNotSavedException, PassedAttributeIsNullException;
	
	public Long addJobOffer(JobImpl jobOffer, DataProvider dataProvider) throws ObjectNotSavedException, PassedAttributeIsNullException;
	
	public void setJobOfferOnlineStatus(JobImpl jobOffer, Boolean onlineStatus) throws ObjectNotSavedException, CantChangeOnlineStateException;
	
	public JobImpl getJobOfferByCobraId(Long cobraJobId) throws JobOfferNotFoundException;
		
	public List<String> getAutoCompleteResults(String partialString);
	
	public Collection<JobImpl> getMoreLikeThis(Long jobOfferId);
	
	public Collection<JobImpl> getJobOfferByCriteria(String criteria, Country country, Territory territory);
	
	public Integer getJobOfferResultAmountByCriteria(final String criteria,final Country country,final Territory territory);
	
	public Collection<JobSearchResultDTO> getJobOfferByCriteria(String criteria, Country country, Territory territory, Integer resultAmount, Integer pageIndexStart);		
	
	public Collection<JobImpl> getJobOfferByCriteriaGetjobs(String criteria, Country country, Territory territory);
	
	public int getOnlineJobOffersByUser(Long userId);
	
	public Long increaseJobApplicationLinkCounter(Long jobOfferId) throws ObjectNotSavedException, JobOfferNotFoundException;
	
	public JobImpl getJobOfferDetails(Long JobOfferId) throws JobOfferNotFoundException;
	
	public void deleteJobOffer(Long jobOfferId) throws JobOfferNotFoundException, PassedAttributeIsNullException;
	
	public void deleteJobOfferByCobraId(Long cobraJobId) throws JobOfferNotFoundException, PassedAttributeIsNullException;

	public void modifyJobOffer(JobImpl jobOffer) throws JobOfferNotFoundException, PassedAttributeIsNullException;

	public void applyToJobOffer(Long jobOfferId, Long userGuid, String contactNote) throws JobOfferNotFoundException, APDUserNotFoundException, ObjectNotSavedException;
	
	public Collection<JobApplication> getReceivedJobOfferApplications(Long apdUserId) throws APDUserNotFoundException;
	
	public Collection<JobImpl> getJobOfferBySector(Long[] sectorIds);
	
	public Currency getCurrencyByNameOrIsoNumber(String currencyValue);

	public Address getJobOwnerAddress(Long jobOfferId) throws JobOfferNotFoundException;
	
	public Collection<JobImpl> getOutdatedJobOffers();
	
	public Collection<JobImpl> getUpdatedJobOffers();

	public Collection<JobImpl> getJobOffersByPortalUserAndCriteria(Long apdUserId, JobActiveEnum jobActive, Country country, Territory territory, int numberOfResults, int indexStart) throws APDUserNotFoundException;
}
