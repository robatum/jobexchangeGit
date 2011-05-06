/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.List;

import org.chenillekit.hibernate.daos.GenericDAO;

import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Territory;



/**
 * @author Administrator
 *
 */
public interface CountryDAO extends GenericDAO<Country, Long>{

	public List<Country> findRelatedCountries(Territory territory);
	
	public Country findCountryByName(String countryName);
	
	public Country findCountryByISONumber(Integer isoNumber);
	
}
