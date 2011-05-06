/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.WorkExperience;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.webservice.entities.WorkExperienceDTO;

/**
 * @author AGEF
 *
 */
public interface WorkExperienceAssembler {

	public WorkExperienceDTO createDTO(WorkExperience work); 
	public WorkExperience createDomainObj(WorkExperienceDTO dto) throws IndustrySectorNotFoundException, EnumValueNotFoundException;
	public WorkExperience updateDomainObj(WorkExperienceDTO dto, WorkExperience existingWorkExperience) throws IndustrySectorNotFoundException, EnumValueNotFoundException;
	
}
