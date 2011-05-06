/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.DegreeEnum;
import net.agef.jobexchange.domain.Education;
import net.agef.jobexchange.domain.EducationInstituteTypeEnum;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.webservice.adapter.util.DateUtil;
import net.agef.jobexchange.webservice.entities.EducationDTO;

/**
 * @author AGEF
 *
 */
public class EducationAssemblerWorker implements EducationAssembler{

	private CountryAssembler ca;
	
	public EducationAssemblerWorker(CountryAssembler countryAssembler){
		this.ca = countryAssembler;
	}
	
	@Override
	public EducationDTO createDTO(Education edu) {
		EducationDTO dto = new EducationDTO();
		
		if(edu != null){
			if(edu.getCountry()!=null){
				dto.setCountry(ca.createDTO(edu.getCountry())); 
			}
			
			dto.setStarted(DateUtil.date2Calendar(edu.getStarted()));
			dto.setDateOfGraduation(DateUtil.date2Calendar(edu.getDateOfGraduation()));
			
			if(edu.getUntilToday() != null){
				dto.setUntilToday(edu.getUntilToday().value());// 2010-09-27
			}
			if(edu.getDegree() != null){
				dto.setDegree(edu.getDegree().toString());
			}
			if(edu.getEducationalInstitute()!= null){
				dto.setEducationalInstitute(edu.getEducationalInstitute().toString());
			}
			dto.setField(edu.getField());
			dto.setFieldSpecialization(edu.getFieldSpecialization());
			
			dto.setInstituteName(edu.getInstituteName());
			dto.setLocation(edu.getLocation());
		}
		return dto;
	}

	@Override
	public Education createDomainObj(EducationDTO dto) throws EnumValueNotFoundException {
		Education edu = new Education();
		
		return createDTOFromEducation(dto, edu);
	}
	
	@Override
	public Education updateDomainObj(EducationDTO dto, Education existingEducation) throws EnumValueNotFoundException{
		Education edu = existingEducation;
		
		return createDTOFromEducation(dto, edu);
	}

	private Education createDTOFromEducation(EducationDTO dto, Education edu) throws EnumValueNotFoundException {
		if(dto!= null){
			if(dto.getCountry()!=null){
				try {
					edu.setCountry(ca.getDomainObj(dto.getCountry()));
				} catch (CountryNotFoundException e) {
					e.printStackTrace();
				}
			}
			edu.setStarted(DateUtil.calendar2Date(dto.getStarted()));
			if(dto.getUntilToday() != null)
				edu.setUntilToday(DecisionYesNoEnum.valueOf(dto.getUntilToday()));
			if(dto.getDateOfGraduation() != null)
				edu.setDateOfGraduation(DateUtil.calendar2Date(dto.getDateOfGraduation()));
			if(dto.getDegree()!=null){
				edu.setDegree(DegreeEnum.fromValue(dto.getDegree()));
			}
			if(dto.getEducationalInstitute()!=null){
				edu.setEducationalInstitute(EducationInstituteTypeEnum.fromValue(dto.getEducationalInstitute()));
			}
			edu.setField(dto.getField());
			edu.setFieldSpecialization(dto.getFieldSpecialization());
			edu.setInstituteName(dto.getInstituteName());
			edu.setLocation(dto.getLocation());
		}
		return edu;
	}
}
