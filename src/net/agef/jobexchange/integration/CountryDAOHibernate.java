/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.List;

import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Territory;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author Administrator
 *
 */
public class CountryDAOHibernate extends AbstractHibernateDAO<Country, Long> implements CountryDAO{

	public CountryDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	public List<Country> findRelatedCountries(Territory territory){
		List<Country> countryList = this.findByQuery("From Country c WHERE c.parentTerritory = :parentTerritory ", new QueryParameter("parentTerritory", territory));
		if(!countryList.isEmpty()){
			return countryList;
		}
		return null;
	}
	
	public Country findCountryByName(String countryName){
		List<Country> countryList = this.findByQuery("From Country c WHERE c.shortEnglishName = :countryName", new QueryParameter("countryName", countryName));
		if(!countryList.isEmpty()){
			return countryList.get(0);
		}
		return null;
	}

	@Override
	public Country findCountryByISONumber(Integer isoNumber) {
		List<Country> countryList = this.findByQuery("From Country c WHERE c.isoNumber = :isoNumber ", new QueryParameter("isoNumber", isoNumber));
		if(!countryList.isEmpty()){
			return countryList.get(0);
		}
		return null;
	}
	
}
