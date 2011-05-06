package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.WorkTypeEnum;
import net.agef.jobexchange.domain.WorkUserType;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.webservice.entities.WorkUserTypeDTO;

public class WorkUserTypeAssemblerWorker implements WorkUserTypeAssembler {

	@Override
	public WorkUserTypeDTO createDTO(WorkUserType workType) {
		WorkUserTypeDTO workerUserTypeDTO = new WorkUserTypeDTO();
		if (workType != null){
			workerUserTypeDTO.setWorkType(workType.getWorkType().value());
		}
		return workerUserTypeDTO;
	}

	@Override
	public WorkUserType createDomainObj(WorkUserTypeDTO dto) throws EnumValueNotFoundException {
		WorkUserType workType = new WorkUserType();
		if(dto != null){
			workType.setWorkType(WorkTypeEnum.fromValue(dto.getWorkType()));
		}
		return workType;
	}

	@Override
	public WorkUserType updateDomainObj(WorkUserTypeDTO dto, WorkUserType existingWorkUserType) throws EnumValueNotFoundException {
		existingWorkUserType.setWorkType(WorkTypeEnum.fromValue(dto.getWorkType()));
		return existingWorkUserType;
	}

}
