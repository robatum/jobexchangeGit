/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.application.FieldOfOccupationWorker;
import net.agef.jobexchange.domain.OccupationalField;

/**
 * @author AGEF
 *
 */
public class OccupationalFieldAssemblerWorker implements OccupationalFieldAssembler{

	FieldOfOccupationWorker fw;
	
	public OccupationalFieldAssemblerWorker(FieldOfOccupationWorker fieldOfOccupationWorker){
		this.fw = fieldOfOccupationWorker;
	}
	
	@Override
	public String createMainFromSubFieldDTO(OccupationalField field) {
		if (field!=null) { 
			return field.getParentFieldId().toString();
		} else {
			return null;
		}
	}
	
	
	@Override
	public String createDTO(OccupationalField field) {
		if (field!=null) { 
			return field.getFieldId().toString();
		} else {
			return null;
		}
	}

	@Override
	public OccupationalField getDomainObj(String field) {
		if(field!=null && !field.equals("")){
			try {
				//if field is provided by id
				if (org.apache.commons.lang.StringUtils.isNumeric(field)) {
					return fw.getOccupationalFieldById(new Long(field));
				} else // else if field is provided by name 
				{
					return fw.getOccupationalFieldByName(field);
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}
