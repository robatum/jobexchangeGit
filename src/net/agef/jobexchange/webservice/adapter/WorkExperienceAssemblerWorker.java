/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;


import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.WorkExperience;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.webservice.adapter.util.DateUtil;
import net.agef.jobexchange.webservice.entities.WorkExperienceDTO;

/**
 * @author AGEF
 *
 */
public class WorkExperienceAssemblerWorker implements WorkExperienceAssembler{

	private OccupationalFieldAssembler oa;
	private IndustrySectorAssembler ia;
	
	public WorkExperienceAssemblerWorker(IndustrySectorAssembler industrySectorAssembler, OccupationalFieldAssembler occupationalFieldAssembler){
		this.oa = occupationalFieldAssembler;
		this.ia = industrySectorAssembler;
	}
	
	@Override
	public WorkExperienceDTO createDTO(WorkExperience work) {
		WorkExperienceDTO dto = new WorkExperienceDTO();
		
		if(work!= null){
			
			dto.setCompany(work.getCompany());
			dto.setHomepage(work.getHomepage());
			dto.setFinished(DateUtil.date2Calendar(work.getFinished()));
			if(work.getUntilToday() != null){
				dto.setUntilToday(work.getUntilToday().value());
			}
			dto.setJobDescription(work.getJobDescription());
			dto.setJobTitle(work.getJobTitle());
			dto.setSector(ia.createDTO(work.getSector()));
			dto.setStarted(DateUtil.date2Calendar(work.getStarted()));

			if(work.getManagementExperience() != null){
				dto.setManagementExperience(work.getManagementExperience().value());
			}
				
			if(work.getOccupationalField()!=null){
				if(work.getOccupationalField().getParentFieldId()!=0) {
					dto.setOccupationalSubField(work.getOccupationalField().getFieldId().toString());
					dto.setOccupationalField(work.getOccupationalField().getParentFieldId().toString());
				} else {
					dto.setOccupationalField(oa.createDTO(work.getOccupationalField()));
				}
				
			}	
		}
		
		return dto;
	}

	@Override
	public WorkExperience createDomainObj(WorkExperienceDTO dto) throws IndustrySectorNotFoundException, EnumValueNotFoundException {
		WorkExperience work = new WorkExperience();
		
		if(dto != null){
			
			
			//System.out.println("DTOGetFineshed: "+dto.getFinished()+" WorkGetFinished: " + work.getFinished());
			work.setJobDescription(dto.getJobDescription());
			work.setJobTitle(dto.getJobTitle());
			work.setSector(ia.getDomainObj(dto.getSector()));
			work.setStarted(DateUtil.calendar2Date(dto.getStarted()));
			work.setFinished(DateUtil.calendar2Date(dto.getFinished()));

			if(dto.getUntilToday() != null)
				work.setUntilToday(DecisionYesNoEnum.valueOf(dto.getUntilToday()));
			work.setCompany(dto.getCompany());
			work.setHomepage(dto.getHomepage());
			if(dto.getManagementExperience() != null){
				work.setManagementExperience(DecisionYesNoEnum.fromValue(dto.getManagementExperience()));
			}
			//System.out.println("DTOGetStarted: "+dto.getStarted()+" WorkGetStarted: "+ work.getStarted());
			
			//Es wird jeweils nur die Unterkategorie gespeichert, da sich die Oberkategorie hieraus ableiten laesst
			if(dto.getOccupationalField()!=null){	
				if(dto.getOccupationalSubField()!=null){
					work.setOccupationalField(oa.getDomainObj(dto.getOccupationalSubField()));
				} else work.setOccupationalField(oa.getDomainObj(dto.getOccupationalField()));
			}
			
		}
		return work;
	}

	@Override
	public WorkExperience updateDomainObj(WorkExperienceDTO dto, WorkExperience existingWorkExperience) throws IndustrySectorNotFoundException, EnumValueNotFoundException {
		return createDomainObj(dto);
//		WorkExperience work = existingWorkExperience;
//		
//		if(dto != null){
//			work.setJobDescription(dto.getJobDescription());
//			work.setJobTitle(dto.getJobTitle());
//			work.setSector(ia.getDomainObj(dto.getSector()));
//			work.setStarted(DateUtil.calendar2Date(dto.getStarted()));
//			work.setFinished(DateUtil.calendar2Date(dto.getFinished()));
//
//			work.setUntilToday(DecisionYesNoEnum.valueOf(dto.getUntilToday()));
//			work.setCompany(dto.getCompany());
//			work.setHomepage(dto.getHomepage());
//			work.setManagementExperience(DecisionYesNoEnum.fromValue(dto.getManagementExperience()));
//			
//			//Es wird jeweils nur die Unterkategorie gespeichert, da sich die Oberkategorie hieraus ableiten laesst
//			if(dto.getOccupationalField()!=null){	
//				if(dto.getOccupationalSubField()!=null){
//					work.setOccupationalField(oa.getDomainObj(dto.getOccupationalSubField()));
//				} else work.setOccupationalField(oa.getDomainObj(dto.getOccupationalField()));
//			}
//
//		}
//		
//		return work;
	}

}
