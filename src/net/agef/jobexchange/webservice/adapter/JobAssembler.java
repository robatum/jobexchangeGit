/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.OccupationalFieldNotFoundException;
import net.agef.jobexchange.webservice.entities.JobDTO;

/**
 * @author Administrator
 *
 */
public interface JobAssembler {
	
	public JobDTO createDTO(JobImpl job); 
	public JobImpl createDomainObjByApdId(JobDTO job)throws APDUserNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException ;
	public JobImpl createDomainObjByCobraId(JobDTO dto) throws CobraUserNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException ;
	public JobImpl updateDomainObjByApdId(JobDTO job)throws JobOfferNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
	public JobImpl updateDomainObjByCobraId(JobDTO dto) throws JobOfferNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
}
