package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.WorkUserType;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.webservice.entities.WorkUserTypeDTO;

public interface WorkUserTypeAssembler {
	WorkUserTypeDTO createDTO(WorkUserType workType); 
	public WorkUserType createDomainObj(WorkUserTypeDTO dto)throws EnumValueNotFoundException;
	public WorkUserType updateDomainObj(WorkUserTypeDTO dto, WorkUserType existingWorkUserType)throws EnumValueNotFoundException;
}
