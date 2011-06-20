/**
 * 
 */
package net.agef.jobexchange.webservice.entities;

import java.util.Calendar;

/**
 * 
 *
 * Die Klasse UserDTO ist eine Domain Klasse und hält Getter und Setter-Methoden
 * zum Abfragen/ Setzen von Nuter relevanten Daten im Kontext der Jobboerse bereit.
 * 
 * @author Andreas Pursian
 */
public class UserDTO {
	
	private Long apdUserId;
	private Long cobraUserId;
	private Long portalUserId;
	private Byte portalId;
	private String dataProvider;
	
	private String addresses;
	private String title;
	private String familyName;
	private String givenName;
	private String fathersName;
	private String position;
	private Calendar dateOfBirth;
	private String emailPrivate;
	private String emailBusiness;
	private String internet;
	private CountryDTO nationality;
	private String citizenship1;
	private String citizenship2;
	
	
	private AddressDTO currentAddress;
	private AddressDTO alternativeAddress;
	
	private AbstractUserRoleDTO userRole;
	
	
	/**
	 * @return the userId
	 */
	public Long getApdUserId() {
		return apdUserId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setApdUserId(Long userId) {
		this.apdUserId = userId;
	}
	/**
	 * @return the cobraUserId
	 */
	public Long getCobraUserId() {
		return cobraUserId;
	}
	/**
	 * @param cobraUserId the cobraUserId to set
	 */
	public void setCobraUserId(Long cobraUserId) {
		this.cobraUserId = cobraUserId;
	}
	public void setPortalUserId(Long portalUserId) {
		this.portalUserId = portalUserId;
	}
	public Long getPortalUserId() {
		return portalUserId;
	}
	public void setPortalId(Byte portalId) {
		this.portalId = portalId;
	}
	public Byte getPortalId() {
		return portalId;
	}
	/**
	 * @return the dataProvider
	 */
	public String getDataProvider() {
		return dataProvider;
	}
	/**
	 * @param dataProvider the dataProvider to set
	 */
	public void setDataProvider(String dataProvider) {
		this.dataProvider = dataProvider;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * MR,
	 * MRS
	 * 
	 * @return the address
	 * 
	 */
	public String getAddresses() {
		return addresses;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * MR,
	 * MRS
	 * 
	 * @param address the address to set
	 * 
	 */
	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * PROF_DR,
	 * PROF,
	 * DR_MULT,
	 * DR,
	 * DIPL_ING
	 * 
	 * @return the title
	 * 
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * PROF_DR,
	 * PROF,
	 * DR_MULT,
	 * DR,
	 * DIPL_ING
	 * 
	 * @param title the title to set
	 * 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}
	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}
	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	/**
	 * @return the fathersName
	 */
	public String getFathersName() {
		return fathersName;
	}
	/**
	 * @param fathersName the fathersName to set
	 */
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the dateOfBirth
	 */
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the emailPrivate
	 */
	public String getEmailPrivate() {
		return emailPrivate;
	}
	/**
	 * @param emailPrivate the emailPrivate to set
	 */
	public void setEmailPrivate(String emailPrivate) {
		this.emailPrivate = emailPrivate;
	}
	/**
	 * @return the emailBusiness
	 */
	public String getEmailBusiness() {
		return emailBusiness;
	}
	/**
	 * @param emailBusiness the emailBusiness to set
	 */
	public void setEmailBusiness(String emailBusiness) {
		this.emailBusiness = emailBusiness;
	}
	/**
	 * @return the internet
	 */
	public String getInternet() {
		return internet;
	}
	/**
	 * @param internet the internet to set
	 */
	public void setInternet(String internet) {
		this.internet = internet;
	}
	/**
	 * Liefert ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) des Landes 
	 * der Nationalitaet enthaelt.
	 * 
	 * @return the nationality
	 * 
	 */
	public CountryDTO getNationality() {
		return nationality;
	}
	/**
	 * Erwartet ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) oder die ISO Nummer des Landes 
	 * der Nationalitaet enthaelt.
	 * 
	 * @param nationality the nationality to set
	 * 
	 */
	public void setNationality(CountryDTO nationality) {
		this.nationality = nationality;
	}
	/**
	 * @return the citizenship1
	 */
	public String getCitizenship1() {
		return citizenship1;
	}
	/**
	 * @param citizenship1 the citizenship1 to set
	 */
	public void setCitizenship1(String citizenship1) {
		this.citizenship1 = citizenship1;
	}
	/**
	 * @return the citizenship2
	 */
	public String getCitizenship2() {
		return citizenship2;
	}
	/**
	 * @param citizenship2 the citizenship2 to set
	 */
	public void setCitizenship2(String citizenship2) {
		this.citizenship2 = citizenship2;
	}
	/**
	 * Liefert ein Objekt der Klasse AddressDTO, welches die aktuelle Adresse mit allen relevanten Adressdaten
	 * eines Nutzers enthaelt.
	 * 
	 * @return the currentAddress
	 * 
	 */
	public AddressDTO getCurrentAddress() {
		return currentAddress;
	}
	/**
	 * Erwartet ein Objekt der Klasse AddressDTO, welches die aktuelle Adresse mit allen relevanten Adressdaten
	 * eines Nutzers enthaelt.
	 * 
	 * @param currentAddress the currentAddress to set
	 * 
	 */
	public void setCurrentAddress(AddressDTO currentAddress) {
		this.currentAddress = currentAddress;
	}
	/**
	 * Liefert ein Objekt der Klasse AddressDTO, welches die sekundaere Adresse mit allen relevanten Adressdaten
	 * eines Nutzers enthaelt.
	 * 
	 * @return the alternativeAddress
	 * 
	 */
	public AddressDTO getAlternativeAddress() {
		return alternativeAddress;
	}
	/**
	 * Erwartet ein Objekt der Klasse AddressDTO, welches die sekundaere Adresse mit allen relevanten Adressdaten
	 * eines Nutzers enthaelt.
	 * 
	 * @param alternativeAddress the alternativeAddress to set
	 * 
	 */
	public void setAlternativeAddress(AddressDTO alternativeAddress) {
		this.alternativeAddress = alternativeAddress;
	}
	/**
	 * Liefert ein konkretes Objekt der Rollen Klassen AlumniRoleDTO oder OrganisationRoleDTO.
	 * 
	 * @return the userRole
	 * 
	 */
	public AbstractUserRoleDTO getUserRole() {
		return userRole;
	}
	/**
	 * Erwartet ein konkretes Objekt der Rollen Klassen AlumniRoleDTO oder OrganisationRoleDTO.
	 * 
	 * @param userRole the userRole to set
	 * 
	 */
	public void setUserRole(AbstractUserRoleDTO userRole) {
		this.userRole = userRole;
	}


}
