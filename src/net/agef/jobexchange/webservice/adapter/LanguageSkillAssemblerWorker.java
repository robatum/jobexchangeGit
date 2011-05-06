/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.LanguageSkill;
import net.agef.jobexchange.domain.LanguageSkillsEnum;
import net.agef.jobexchange.domain.Languages;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.LanguageNotFoundException;
import net.agef.jobexchange.integration.LanguagesDAO;
import net.agef.jobexchange.webservice.entities.LanguageSkillDTO;

/**
 * @author AGEF
 *
 */
public class LanguageSkillAssemblerWorker implements LanguageSkillAssembler{
	
	private LanguagesDAO languagesDAO;
	
	public LanguageSkillAssemblerWorker(LanguagesDAO languagesDAO){
		this.languagesDAO = languagesDAO;
	}

	@Override
	public LanguageSkillDTO createDTO(LanguageSkill language) {
		LanguageSkillDTO dto = new LanguageSkillDTO();
		
		if(language!=null){
			if(language.getName()!=null)
			dto.setName(language.getName().getIsoNameShort());
			if(language.getLevel()!=null)
			dto.setLevel(language.getLevel().toString());
		}
		return dto;
	}

	@Override
	public LanguageSkill createDomainObj(LanguageSkillDTO dto) throws LanguageNotFoundException, EnumValueNotFoundException {
		LanguageSkill languageSkill = new LanguageSkill();
		Languages language;
		if(dto!=null){
			//if language is provided by short iso name
			if(dto.getName()!=null  && !dto.getName().equals("")){
				if(dto.getName().length()==2){
					language = languagesDAO.findLanguageByISOName(dto.getName());
				} else // else if language is provided by name 
					{
						language = languagesDAO.findLanguageByName(dto.getName());
				}
				if(language==null) throw new LanguageNotFoundException();
				languageSkill.setName(language);
			} else throw new LanguageNotFoundException();
			if(dto.getLevel()!= null && !dto.getLevel().equals("")){
				languageSkill.setLevel(LanguageSkillsEnum.fromValue(dto.getLevel()));
			} else languageSkill.setLevel(null);
		}
		return languageSkill;
	}

	@Override
	public LanguageSkill updateDomainObj(LanguageSkillDTO dto, LanguageSkill existingLanguage) throws LanguageNotFoundException, EnumValueNotFoundException {
		LanguageSkill languageSkill = existingLanguage;
		Languages language;
		if(dto!=null){
			//if language is provided by short iso name
			if(dto.getName()!=null && !dto.getName().equals("")){
				if(dto.getName().length()==2){
					language = languagesDAO.findLanguageByISOName(dto.getName());
				} else // else if language is provided by name 
					{
						language = languagesDAO.findLanguageByName(dto.getName());
				}
				if(language==null) throw new LanguageNotFoundException();
				languageSkill.setName(language);
			} else throw new LanguageNotFoundException();
			if(dto.getLevel()!= null && !dto.getLevel().equals("")){
				languageSkill.setLevel(LanguageSkillsEnum.fromValue(dto.getLevel()));
			} else languageSkill.setLevel(null);
		}
		return languageSkill;
	}

}
