/**
 * 
 */
package net.agef.jobexchange.webservice.entities;


/**
 * @author Andreas Pursian
 *
 */
public class CountryDTO {
	

	public CountryDTO(){		
	}
	
	public CountryDTO(String countryName){
		this.country = countryName;
	}
	
	private String country;
	@Deprecated
	private Integer isoNumber;
	
	/**
	 * Liefert den Namen des Landes in englischer Kurzschreibweise.
	 * 
	 * @return the country
	 * 
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Erwartet den Namen (englishe Kurzschreibweise) oder die ISO Nummer des Landes.
	 * 
	 * @param country the country to set
	 * 
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param isoNumber the isoNumber to set
	 */
	@Deprecated
	public void setIsoNumber(Integer isoNumber) {
		this.isoNumber = isoNumber;
	}

	/**
	 * @return the isoNumber
	 */
	@Deprecated
	public Integer getIsoNumber() {
		return isoNumber;
	}

}
