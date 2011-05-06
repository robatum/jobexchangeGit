/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.Collection;
import java.util.List;

import net.agef.jobexchange.domain.OccupationalField;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class OccupationalFieldDAOHibernate extends AbstractHibernateDAO<OccupationalField, Long> implements OccupationalFieldDAO{

	public OccupationalFieldDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}
	
	@Override
	public OccupationalField findOccupationalFieldById(Long fieldId) {
		List<OccupationalField> fieldList = this.findByQuery("From OccupationalField o WHERE o.fieldId = :fieldId ", new QueryParameter("fieldId", fieldId));
		if(!fieldList.isEmpty()){
			return fieldList.get(0);
		}
		return null;
	}
	
	@Override
	public OccupationalField findOccupationalFieldByName(String fieldName) {
		List<OccupationalField> fieldList = this.findByQuery("From OccupationalField o WHERE o.fieldNameGerman = :fieldName or o.fieldNameEnglish = :fieldName", new QueryParameter("fieldName", fieldName));
		if(!fieldList.isEmpty()){
			return fieldList.get(0);
		}
		return null;
	}
	
	@Override
	public Collection<OccupationalField> findOccupationalSubFieldsByMainFieldId(Long fieldId) {
		List<OccupationalField> fieldList = this.findByQuery("From OccupationalField o WHERE o.parentFieldId = :fieldId ", new QueryParameter("fieldId", fieldId));
		return fieldList;
	}
	
	@Override
	public Collection<OccupationalField> findOccupationalMainFields() {
			List<OccupationalField> fieldList = this.findByQuery("From OccupationalField o WHERE o.parentFieldId = 0");
		return fieldList;
	}

}
