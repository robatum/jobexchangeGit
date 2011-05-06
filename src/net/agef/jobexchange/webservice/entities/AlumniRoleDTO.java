/**
 * 
 */
package net.agef.jobexchange.webservice.entities;


/**
 * 
 *
 * Die Klasse AlumniRoleDTO ist eine abgeleitete Domain Klasse (von der abstrakten Superklassen AbstractUserRoleDTO) 
 * und haelt Getter und Setter-Methoden zum Abfragen/ Setzen von Rollen spezifischen Daten im 
 * Kontext der Jobboerse bereit. Die spezifische Rollenklasse repraesentiert dabei die Rolle eines Nutzers 
 * innerhalb der APD Plattform / Jobboerse.
 * 
 * @author Andreas Pursian

 */
public class AlumniRoleDTO extends AbstractUserRoleDTO {

	
	private String organisationName;
	private String organisationDescription;
	private String organisationIndustrySector;
	
	
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
	 * @return the organisationDescription
	 */
	public String getOrganisationDescription() {
		return organisationDescription;
	}
	/**
	 * @param organisationDescription the organisationDescription to set
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
	

}
