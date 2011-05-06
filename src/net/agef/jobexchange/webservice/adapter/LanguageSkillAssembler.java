/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.LanguageSkill;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.LanguageNotFoundException;
import net.agef.jobexchange.webservice.entities.LanguageSkillDTO;

/**
 * @author AGEF
 *
 */
public interface LanguageSkillAssembler {
	
	public LanguageSkillDTO createDTO(LanguageSkill language); 
	public LanguageSkill createDomainObj(LanguageSkillDTO dto)throws LanguageNotFoundException, EnumValueNotFoundException;
	public LanguageSkill updateDomainObj(LanguageSkillDTO dto, LanguageSkill existingLanguage) throws LanguageNotFoundException, EnumValueNotFoundException;


}
