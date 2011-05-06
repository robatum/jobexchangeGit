/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.domain.JobApplication;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.integration.UserDAO;
import net.agef.jobexchange.webservice.entities.JobApplicationDTO;


/**
 * @author mutabor
 *
 */
public class JobApplicationAssemblerWorker implements JobApplicationAssembler{

	private UserDAO userDAO;	
	private JobWorker jw;
	
	public JobApplicationAssemblerWorker(UserDAO userDAO, JobWorker jobWorker) {
		this.userDAO = userDAO;
		this.jw = jobWorker;
	}
	
	@Override
	public JobApplicationDTO createDTO(JobApplication application) {
		JobApplicationDTO dto = new JobApplicationDTO();
		
		if(application!=null){
			if(application.getRelatedJob()!= null) {
				dto.setRelatedJobId(application.getRelatedJob().getJobOfferId());
				dto.setRelatedJobDescription(application.getRelatedJob().getJobDescription());
			}
			if(application.getJobApplicationOwner()!=null)	{
				dto.setApplicationOwnerId(application.getJobApplicationOwner().getApdUserId());
				dto.setApplicationOwnerName(application.getJobApplicationOwner().getFullUserName());
			}
			dto.setContactNote(application.getContactNote());
			
		}
		return dto;
	}

	@Override
	public JobApplication createDomainObj(JobApplicationDTO dto)
			throws APDUserNotFoundException, JobOfferNotFoundException {
		
		JobApplication application = new JobApplication();

		if(dto!=null){
			
			if(dto.getApplicationOwnerId()!=null){
				User user = userDAO.findAPDUserByID(dto.getApplicationOwnerId());
				if(user!=null) {
					application.setJobApplicationOwner(user);
				} else throw new APDUserNotFoundException(dto.getApplicationOwnerId().toString());
			}
		
			if(dto.getRelatedJobId()!=null){
				application.setRelatedJob(jw.getJobOfferDetails(dto.getRelatedJobId()));
			}
			application.setContactNote(dto.getContactNote());
		
		}
		return application;
	}

}
