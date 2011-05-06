/**
 * 
 */
package net.agef.jobexchange.application;

import java.util.List;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.TerritoryNotFoundException;

/**
 * @author Administrator
 *
 */
public interface LocationWorker {

	public List<Territory> getAllTerritories();
	
	public List<Country> getRelatedCountries(Territory territory);
	
	public Territory getTerritoryById(Long territoryId);
	
	public Country getCountryById(Long countryId);
	
	public Country getCountryByName(String countryName) throws CountryNotFoundException;
	
	public Country getCountryByISONumber(Integer isoNumber) throws CountryNotFoundException;
	
	public Territory getTerritoryByName(String territoryName) throws TerritoryNotFoundException;
	
	public Territory getTerritoryByISONumber(Integer isoNumber) throws TerritoryNotFoundException;
}
