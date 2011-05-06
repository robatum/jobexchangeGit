/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.List;

import net.agef.jobexchange.domain.Languages;

import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class LanguagesDAOHibernate extends AbstractHibernateDAO<Languages, Long> implements LanguagesDAO{

	public LanguagesDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	
	@Override
	public Languages findLanguageByISOName(String isoName) {
		List<Languages> languageList = this.findByQuery("From Languages l WHERE l.isoNameShort = :isoName ", new QueryParameter("isoName", isoName));
		if(!languageList.isEmpty()){
			return languageList.get(0);
		}
		return null;
	}


	@Override
	public Languages findLanguageByName(String name) {
		List<Languages> languageList = this.findByQuery("From Languages l WHERE l.isoNameLong = :name ", new QueryParameter("name", name));
		if(!languageList.isEmpty()){
			return languageList.get(0);
		}
		return null;
	}
}
