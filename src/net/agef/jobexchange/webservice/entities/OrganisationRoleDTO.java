/**
 * 
 */
package net.agef.jobexchange.webservice.entities;


/**
 * 
 * 
 * Die Klasse OrganisationRoleDTO ist eine abgeleitete Domain Klasse (von der abstrakten Superklassen AbstractUserRoleDTO) 
 * und haelt Getter und Setter-Methoden zum Abfragen/ Setzen von Rollen spezifischen Daten im 
 * Kontext der Jobboerse bereit. Die spezifische Rollenklasse repraesentiert dabei die Rolle eines Nutzers 
 * innerhalb der APD Plattform / Jobboerse. 
 * 
 * @author Andreas Pursian
 */
public class OrganisationRoleDTO extends AbstractUserRoleDTO {
	
	private String organisationName;
	private String organisationDescription;
	private String organisationIndustrySector;
	private String organisationEmployeeAmount;
	
	
	/**
	 * @return the organisationName
	 */
	public String getOrganisationName() {
		return organisationName;
	}
	/**
	 * @param organisationName the organisationName to set
	 */
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	/**
	 * Liefert die Beschreibung der Organisation.
	 * 
	 * @return the organisationDescription
	 * 
	 */
	public String getOrganisationDescription() {
		return organisationDescription;
	}
	/**
	 * Erwartet die Beschreibung der Organisation.
	 * 
	 * @param organisationDescription the organisationDescription to set
	 * 
	 */
	public void setOrganisationDescription(String organisationDescription) {
		this.organisationDescription = organisationDescription;
	}
	/**
	 * @return the organisationIndustrySector
	 */
	public String getOrganisationIndustrySector() {
		return organisationIndustrySector;
	}
	/**
	 * @param organisationIndustrySector the organisationIndustrySector to set
	 */
	public void setOrganisationIndustrySector(String organisationIndustrySector) {
		this.organisationIndustrySector = organisationIndustrySector;
	}
	/**
	 * @return the organisationEmployeeAmount
	 */
	public String getOrganisationEmployeeAmount() {
		return organisationEmployeeAmount;
	}
	/**
	 * @param organisationEmployeeAmount the organisationEmployeeAmount to set
	 */
	public void setOrganisationEmployeeAmount(String organisationEmployeeAmount) {
		this.organisationEmployeeAmount = organisationEmployeeAmount;
	}

}
