/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;


import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.webservice.entities.CountryDTO;

/**
 * @author AGEF
 *
 */
public class CountryAssemblerWorker implements CountryAssembler{

	private LocationWorker lw;
	
	public CountryAssemblerWorker(LocationWorker locationWorker) {
		this.lw = locationWorker;
	}
	
	@Override
	public CountryDTO createDTO(Country country) {		
		if (country!=null) {
			CountryDTO countryDTO = new CountryDTO(country.getIsoNumber().toString()); 
			return countryDTO;
		} else return null;
		
	}

	@Override
	public Country getDomainObj(CountryDTO countryDTO) throws CountryNotFoundException {
		if(countryDTO!=null && countryDTO.getCountry()!= null && !countryDTO.getCountry().equals("") && !countryDTO.getCountry().equals("NULL")){
			try {
				//if country is provided by iso number
				if(org.apache.commons.lang.StringUtils.isNumeric(countryDTO.getCountry())){
					return lw.getCountryByISONumber(new Integer(countryDTO.getCountry()));
				} else // else if country is provided by name 
					{
						return lw.getCountryByName(countryDTO.getCountry());
				}
			} catch (NumberFormatException e) {
				System.err.println("Territory could not be parsed: "+countryDTO.getCountry());
				return null;
			}
		}
		return null;
	}

}
