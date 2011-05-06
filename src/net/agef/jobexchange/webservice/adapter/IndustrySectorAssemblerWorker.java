/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.IndustrySector;
import net.agef.jobexchange.integration.IndustrySectorDAO;

/**
 * @author AGEF
 *
 */
public class IndustrySectorAssemblerWorker implements IndustrySectorAssembler{
	
	private IndustrySectorDAO industryDAO;
	
	public IndustrySectorAssemblerWorker(IndustrySectorDAO industryDAO){
		this.industryDAO = industryDAO;
	}

	@Override
	public String createDTO(IndustrySector sector) {
		if (sector!=null) { 
			return sector.getSectorId().toString();
		} else return null;
	}

	@Override
	public IndustrySector getDomainObj(String sector) {
		if(sector!=null && !sector.equals("")){
			try {
				//if sector is provided by iso number
				if (org.apache.commons.lang.StringUtils.isNumeric(sector)) {
					return industryDAO.findIndustrySectorById(new Long(sector));
				} else // else if sector is provided by name 
				{
					return industryDAO.findIndustrySectorByName(sector);
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}
