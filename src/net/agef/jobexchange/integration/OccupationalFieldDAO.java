/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.Collection;

import org.chenillekit.hibernate.daos.GenericDAO;

import net.agef.jobexchange.domain.OccupationalField;


/**
 * @author AGEF
 *
 */
public interface OccupationalFieldDAO extends GenericDAO<OccupationalField, Long>{

	public OccupationalField findOccupationalFieldById(Long fieldId);
	public OccupationalField findOccupationalFieldByName(String fieldName);
	public Collection<OccupationalField> findOccupationalSubFieldsByMainFieldId(Long fieldId);
	public Collection<OccupationalField> findOccupationalMainFields();
}
