/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.exceptions.TerritoryNotFoundException;
import net.agef.jobexchange.webservice.entities.TerritoryDTO;

/**
 * @author AGEF
 *
 */
public class TerritoryAssemblerWorker implements TerritoryAssembler{

	private LocationWorker lw;
	
	public TerritoryAssemblerWorker(LocationWorker locationWorker) {
		this.lw = locationWorker;
	}
	
	@Override
	public TerritoryDTO createDTO(Territory territory) {
		if (territory!=null) {
			TerritoryDTO territoryDTO = new TerritoryDTO(territory.getNameEnglish()); 
			return territoryDTO;
		} else return null;
	}

	@Override
	public Territory getDomainObj(TerritoryDTO dto) throws TerritoryNotFoundException {
		if(dto!=null && dto.getTerritory() != null && !dto.getTerritory().equals("") && !dto.getTerritory().equals("NULL") ){
			try {
				//if country is provided by iso number
				if(org.apache.commons.lang.StringUtils.isNumeric(dto.getTerritory())){
					return lw.getTerritoryByISONumber(new Integer(dto.getTerritory()));
				} else // else if country is provided by name 
					{
						return lw.getTerritoryByName(dto.getTerritory());
				}
			} catch (NumberFormatException e) {
				System.err.println("Territory could not be parsed: "+dto.getTerritory());
				return null;
			}
		}
		return null;
	}

}
