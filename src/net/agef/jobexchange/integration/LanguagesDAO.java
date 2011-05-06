/**
 * 
 */
package net.agef.jobexchange.integration;

import org.chenillekit.hibernate.daos.GenericDAO;

import net.agef.jobexchange.domain.Languages;



/**
 * @author AGEF
 *
 */
public interface LanguagesDAO extends GenericDAO<Languages, Long>{
	
	public Languages findLanguageByISOName(String isoName);
	
	public Languages findLanguageByName(String name);

}
