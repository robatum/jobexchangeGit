/**
 * 
 */
package net.agef.jobexchange.application;

import java.util.Collection;

import net.agef.jobexchange.domain.IndustrySector;
import net.agef.jobexchange.domain.OccupationalField;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.exceptions.OccupationalFieldNotFoundException;

/**
 * @author AGEF
 *
 */
public interface FieldOfOccupationWorker {

	public IndustrySector getIndustrySectorById(Long sectorId) throws IndustrySectorNotFoundException;
	public OccupationalField getOccupationalFieldById(Long fieldId) throws OccupationalFieldNotFoundException;
	public OccupationalField getOccupationalFieldByName(String fieldName) throws OccupationalFieldNotFoundException;
	public Collection<OccupationalField> getOccupationalSubFieldsByMainFieldId(Long fieldId) throws OccupationalFieldNotFoundException;
	public Collection<OccupationalField> getOccupationalMainFields() throws OccupationalFieldNotFoundException;
}
