/**
 * 
 */
package net.agef.jobexchange.webservice.entities;


/**
 * 
 * 
 * Die Klasse ContactPersonDTO ist eine Domain Klasse und haelt Getter und Setter-Methoden 
 * zum Abfragen/Setzen von Kontaktpersonen relevanten Daten im Kontext der Jobboerse bereit.
 *
 * @author Andreas Pursian
 */
public class ContactPersonDTO{


	private String addresses;
	private String title;
	private String familyName;
	private String givenName;
	private String fathersName;
	private String position;
	private String emailBusiness;
	private String internet;
	
	
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * MR,
	 * MRS
	 * 
	 * @return the addresses
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
	 * @param addresses the addresses to set
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
	
	
	
	
	
}
