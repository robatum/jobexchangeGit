/**
 * 
 */
package net.agef.jobexchange.webservice.entities;



/**
 * 
 *
 * Die Klasse AddressDTO ist eine Domain Klasse und haelt Getter und Setter-Methoden 
 * zum Abfragen/Setzen von Adressrelevanten Daten im Kontext eines Nutzers oder einer Kontakt-
 * person bereit.
 * 
 * @author Andreas Pursian
 */
public class AddressDTO {

	
	private String address1;
	private String address2;
	private String city;
	private String federalState;
	private String zipCode;
	private CountryDTO country;
	private String phoneNumber;
	private String mobileNumber;
	private String faxNumber;
	
	
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the federalState
	 */
	public String getFederalState() {
		return federalState;
	}
	/**
	 * @param federalState the federalState to set
	 */
	public void setFederalState(String federalState) {
		this.federalState = federalState;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the country
	 * 
	 * Liefert ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) des Landes 
	 * der Adresse enthaelt.
	 */
	public CountryDTO getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 * 
	 * Erwartet ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) oder die ISO Nummer des Landes 
	 * der Adresse enthaelt.
	 * 
	 */
	public void setCountry(CountryDTO country) {
		this.country = country;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}
	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
	
	
}
