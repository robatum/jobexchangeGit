package net.agef.jobexchange.webservice.entities;

import java.util.Calendar;

/**
 * 
 *
 * Die Klasse WorkExperienceDTO ist eine Domain Klasse und haelt Getter und Setter-Methoden 
 * zum Abfragen/Setzen von Arbeitserfahrungsrelevanten Daten im Kontext eines Stellenangebotes oder
 * eines Bewerber-/Expertenprofils bereit.
 * 
 * @author Andreas Pursian
 */
public class WorkExperienceDTO {
	
	private Calendar started;
	private Calendar finished;
	private String jobTitle;
	private String jobDescription;
	private String sector;
	private String occupationalField;
	private String occupationalSubField;
	
	/* Bewerberprofil Sommer 2010 */
	private String untilToday;
	private String company;
	private String homepage;
	private String managementExperience;
	/* Ende Bewerberprofil Sommer 2010 */
	
	/**
	 * @return the started
	 */
	public Calendar getStarted() {
		return started;
	}
	/**
	 * @param started the started to set
	 */
	public void setStarted(Calendar started) {
		this.started = started;
	}
	/**
	 * @return the finished
	 */
	public Calendar getFinished() {
		return finished;
	}
	/**
	 * @param finished the finished to set
	 */
	public void setFinished(Calendar finished) {
		this.finished = finished;
	}
	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * @return the jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}
	/**
	 * @param jobDescription the jobDescription to set
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	/**
	 * Liefert die Bereichs Id der Branchentabelle.
	 * 
	 * @return the sector
	 * 
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * Erwartet die Bereichs Id der Branchentabelle.
	 * 
	 * @param sector the sector to set
	 * 
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	/**
	 * Liefert die Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @return the occupationalField
	 */
	public String getOccupationalField() {
		return occupationalField;
	}
	/**
	 * Erwartet die Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @param occupationalField the occupationalField to set
	 */
	public void setOccupationalField(String occupationalField) {
		this.occupationalField = occupationalField;
	}

	/**
	 * Liefert die Sub-Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @return the occupationalSubField
	 */	
	public String getOccupationalSubField() {
		return occupationalSubField;
	}
	/**
	 * Erwartet die Sub-Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @param occupationalSubField the occupationalSubField to set
	 */
	public void setOccupationalSubField(String occupationalSubField) {
		this.occupationalSubField = occupationalSubField;
	}
	public void setUntilToday(String untilToday) {
		this.untilToday = untilToday;
	}
	public String getUntilToday() {
		return untilToday;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompany() {
		return company;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setManagementExperience(String managementExperience) {
		this.managementExperience = managementExperience;
	}
	public String getManagementExperience() {
		return managementExperience;
	}

}
