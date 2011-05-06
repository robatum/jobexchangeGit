/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.JobApplication;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.webservice.entities.JobApplicationDTO;

/**
 * @author mutabor
 *
 */
public interface JobApplicationAssembler {
	
	public JobApplicationDTO createDTO(JobApplication application); 
	public JobApplication createDomainObj(JobApplicationDTO dto)throws APDUserNotFoundException, JobOfferNotFoundException ;
	

}
