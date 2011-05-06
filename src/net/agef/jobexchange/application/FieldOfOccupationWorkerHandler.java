/**
 * 
 */
package net.agef.jobexchange.application;

import java.util.Collection;
import java.util.List;

import org.chenillekit.hibernate.utils.QueryParameter;

import net.agef.jobexchange.domain.IndustrySector;
import net.agef.jobexchange.domain.OccupationalField;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.exceptions.OccupationalFieldNotFoundException;
import net.agef.jobexchange.integration.IndustrySectorDAO;
import net.agef.jobexchange.integration.OccupationalFieldDAO;

/**
 * @author AGEF
 *
 */
public class FieldOfOccupationWorkerHandler implements FieldOfOccupationWorker{
	
	private OccupationalFieldDAO fieldDAO;
	private IndustrySectorDAO sectorDAO;
	
	public FieldOfOccupationWorkerHandler(OccupationalFieldDAO fieldDAO, IndustrySectorDAO sectorDAO){
		this.fieldDAO = fieldDAO;
		this.sectorDAO = sectorDAO;		
	}
	
	public IndustrySector getIndustrySectorById(Long sectorId) throws IndustrySectorNotFoundException {
		IndustrySector sector = sectorDAO.findIndustrySectorById(sectorId);
		if(sector!=null){
			return sector;
		}else throw new IndustrySectorNotFoundException();
	}
	
	public OccupationalField getOccupationalFieldById(Long fieldId) throws OccupationalFieldNotFoundException{
		OccupationalField field = fieldDAO.findOccupationalFieldById(fieldId);
		if(field!=null){
			return field;
		} else throw new OccupationalFieldNotFoundException();
	}
	
	public OccupationalField getOccupationalFieldByName(String fieldName) throws OccupationalFieldNotFoundException{
		OccupationalField field = fieldDAO.findOccupationalFieldByName(fieldName);
		if(field!=null){
			return field;
		} else throw new OccupationalFieldNotFoundException();
	}
	
	@Override
	public Collection<OccupationalField> getOccupationalSubFieldsByMainFieldId(Long fieldId)  throws OccupationalFieldNotFoundException{
		Collection<OccupationalField> fieldList = fieldDAO.findOccupationalSubFieldsByMainFieldId(fieldId);
		if(fieldList!=null){
			return fieldList;
		} else throw new OccupationalFieldNotFoundException();
	}
	
	@Override
	public Collection<OccupationalField> getOccupationalMainFields() throws OccupationalFieldNotFoundException{
		Collection<OccupationalField> fieldList = fieldDAO.findOccupationalMainFields();
		if(fieldList!=null){
			return fieldList;
		} else throw new OccupationalFieldNotFoundException();
	}
	
}
