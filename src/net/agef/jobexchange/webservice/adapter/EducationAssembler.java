/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.Education;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.webservice.entities.EducationDTO;

/**
 * @author AGEF
 *
 */
public interface EducationAssembler {
	
	public EducationDTO createDTO(Education edu); 
	public Education createDomainObj(EducationDTO dto)throws EnumValueNotFoundException;
	public Education updateDomainObj(EducationDTO dto, Education existingEducation)throws EnumValueNotFoundException;

}
