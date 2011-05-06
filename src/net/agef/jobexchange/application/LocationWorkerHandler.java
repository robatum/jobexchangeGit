/**
 * 
 */
package net.agef.jobexchange.application;


import java.util.List;

import org.slf4j.Logger;

import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.TerritoryNotFoundException;
import net.agef.jobexchange.integration.CountryDAO;
import net.agef.jobexchange.integration.TerritoryDAO;

/**
 * @author Administrator
 *
 */
public class LocationWorkerHandler implements LocationWorker{

	private CountryDAO countryDAO;
	private TerritoryDAO territoryDAO;
	
	public LocationWorkerHandler(TerritoryDAO territoryDAO, CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
		this.territoryDAO = territoryDAO;
	}
	
	public List<Territory> getAllTerritories(){
		return territoryDAO.findAll();
	}
	
	public List<Country> getRelatedCountries(Territory territory){
		return countryDAO.findRelatedCountries(territoryDAO.doRetrieve(territory.getId(), false));
	}
	
	public Territory getTerritoryById(Long territoryId){
		return territoryDAO.doRetrieve(territoryId, true);
	}
	
	public Country getCountryById(Long countryId){
		return countryDAO.doRetrieve(countryId, true);
	}
	
	@Override
	public Country getCountryByName(String countryName) throws CountryNotFoundException{
		if(countryName != null && !countryName.equals("")) {
			Country country = countryDAO.findCountryByName(countryName);
			
			if(country!=null){
				return country;
			} else throw new CountryNotFoundException(countryName);
		} else return null;
	}

	@Override
	public Country getCountryByISONumber(Integer isoNumber) throws CountryNotFoundException {
		if(isoNumber != null) {
			Country country = countryDAO.findCountryByISONumber(isoNumber);
			if(country!=null){
				return country;
			} else throw new CountryNotFoundException(isoNumber.toString());
		} else return null;
	}

	@Override
	public Territory getTerritoryByISONumber(Integer isoNumber) throws TerritoryNotFoundException {
		if(isoNumber != null) {
			Territory territory = territoryDAO.findTerritoryByISONumber(isoNumber);
			if(territory!=null){
				return territory;
			} else throw new TerritoryNotFoundException(isoNumber.toString());
		} else return null;
	}

	@Override
	public Territory getTerritoryByName(String territoryName) throws TerritoryNotFoundException {
		if(territoryName != null && !territoryName.equals("")) {
			Territory territory = territoryDAO.findTerritoryByName(territoryName);
			if(territory!=null){
				return territory;
			} else throw new TerritoryNotFoundException(territoryName);
		} else return null;
	}
	
}
