/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.webservice.entities.JobSearchResultDTO;

/**
 * @author agef
 *
 */
public class JobSearchResultAssemblerWorker implements JobSearchResultAssembler{

	
	private CountryAssembler ca;
	
	/**
	 * 
	 */
	public JobSearchResultAssemblerWorker(CountryAssembler countryAssembler) {
		this.ca = countryAssembler;
	}
	
	@Override
	public JobSearchResultDTO createDTO(JobImpl job) {
		JobSearchResultDTO dto = new JobSearchResultDTO();
		
		if(job!=null){

			if(job.getCountryOfEmployment()!=null){
				//dto.setCountryOfEmployment(ca.createDTO(job.getCountryOfEmployment()));
			}
			
			if(dto.getJobDescription() != null && !dto.getJobDescription().contains("(m/f)")){
				dto.setJobDescription(job.getJobDescription()+" (m/f)");
			}else dto.setJobDescription(job.getJobDescription());
			dto.setJobOfferId(job.getJobOfferId());
			

			if(job.getOrganisationIndustrySector()!=null){
				dto.setOrganisationIndustrySectorId(job.getOrganisationIndustrySector().getSectorId().toString());
			}
		}
		return dto;
	}
	

}
