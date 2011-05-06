/**
 * 
 */
package net.agef.jobexchange.services.internal;

import java.util.Collection;
import java.util.Iterator;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;

/**
 * @author agef
 *
 */
public class JobOfferOnlineStateCheckImpl implements JobOfferOnlineStateCheck{

	private Integer counter = 0;
	
	public JobOfferOnlineStateCheckImpl() {
		System.out.println("Instantiating Job Offer Online State Job");
	}
	


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.counter++;
		
		System.out.println("Executing job: jobOfferOnlineStateCheck ...."+this.counter);
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		//JobDataMap dataMap = context.getMergedJobDataMap();
		//System.out.println("JW serialized: "+dataMap.get("jobWorker").toString());
		JobWorker jw = (JobWorker)dataMap.get("jobWorker");
		//System.out.println("JW deserialized: "+jw.toString());
		if (jw!=null){			
			Collection<JobImpl> outdatedJobOffers = jw.getOutdatedJobOffers();
			if(outdatedJobOffers!=null && !outdatedJobOffers.isEmpty()){
				System.out.println("Jobcollection is not null, executing offline script ...");
				Iterator<JobImpl> it = outdatedJobOffers.iterator();
				while(it.hasNext()){
					try {
						JobImpl jobOffer = it.next();
						System.out.println("Set jobState for job "+jobOffer.getJobOfferId()+" offline");
						jw.setJobOfferOnlineStatus(jobOffer, false);
					} catch (ObjectNotSavedException e) {
						e.printStackTrace();
					} catch (CantChangeOnlineStateException e) {
						e.printStackTrace();
					}
				}
			} else System.out.println("(Offline) All jobs are up to date, no onlineStateChange necessary");
		
			Collection<JobImpl> updatedJobOffers = jw.getUpdatedJobOffers();
			if(updatedJobOffers!=null && !updatedJobOffers.isEmpty()){
				System.out.println("Jobcollection is not null, executing online script ...");
				Iterator<JobImpl> it = updatedJobOffers.iterator();
				while(it.hasNext()){
					try {
						JobImpl jobOffer = it.next();
						System.out.println("Set jobState for job "+jobOffer.getJobOfferId()+" online");
						jw.setJobOfferOnlineStatus(jobOffer, true);
					} catch (ObjectNotSavedException e) {
						e.printStackTrace();
					} catch (CantChangeOnlineStateException e) {
						e.printStackTrace();
					}
				}
			} else System.out.println("(Online) All jobs are up to date, no onlineStateChange necessary");

		
		} else{
			System.out.println("Problems with JobWorker service, servicehandler is null.");
		}
	
	}
}
