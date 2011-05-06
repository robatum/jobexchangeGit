/**
 * 
 */
package net.agef.jobexchange.webservice.entities;

import java.util.Calendar;


/**
 * @author Andreas Pursian
 *
 */
public class EducationDTO {
	
	private String educationalInstitute;
	private String degree;
	private String fieldSpecialization;
	private Calendar dateOfGraduation;
	private CountryDTO country;
	private String location;
	private String instituteName;
	
	/* Bewerberprofil Sommer 2010 */
	private Calendar started;
	private String untilToday;
	private String field; // Fachrichtung
	/* Ende Bewerberprofil Sommer 2010 */
	
	
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * UNIVERSITY,
	 * UNIVERSITY_OF_APPLIED_SCIENCES,
	 * VOCATIONAL_SCHOOL,
	 * GRAMMAR_SCHOOL
	 * 
	 * @return the educationalInstitute
	 * 
	 */
	public String getEducationalInstitute() {
		return educationalInstitute;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * UNIVERSITY,
	 * UNIVERSITY_OF_APPLIED_SCIENCES,
	 * VOCATIONAL_SCHOOL,
	 * GRAMMAR_SCHOOL
	 * 
	 * @param educationalInstitute the educationalInstitute to set
	 * 
	 */
	public void setEducationalInstitute(String educationalInstitute) {
		this.educationalInstitute = educationalInstitute;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * BACHELOR,
	 * MASTER,
	 * DIPLOMA,
	 * MAGISTER,
	 * VOCATIONAL_TRAINING,
	 * DR,
	 * PROF
	 * 
	 * @return the degree
	 * 
	 */
	public String getDegree() {
		return degree;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte:
	 * 
	 * BACHELOR,
	 * MASTER,
	 * DIPLOMA,
	 * MAGISTER,
	 * VOCATIONAL_TRAINING,
	 * DR,
	 * PROF
	 * 
	 * @param degree the degree to set
	 * 
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	/**
	 * @return the fieldSpecialization
	 */
	public String getFieldSpecialization() {
		return fieldSpecialization;
	}
	/**
	 * @param fieldSpecialization the fieldSpecialization to set
	 */
	public void setFieldSpecialization(String fieldSpecialization) {
		this.fieldSpecialization = fieldSpecialization;
	}
	/**
	 * @return the dateOfGraduation
	 */
	public Calendar getDateOfGraduation() {
		return dateOfGraduation;
	}
	/**
	 * @param dateOfGraduation the dateOfGraduation to set
	 */
	public void setDateOfGraduation(Calendar dateOfGraduation) {
		this.dateOfGraduation = dateOfGraduation;
	}
	/**
	 * Liefert ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) des Landes 
	 * in dem die Ausbildung statt fand enthaelt.
	 * 
	 * @return the country
	 * 
	 */
	public CountryDTO getCountry() {
		return country;
	}
	/**
	 * Erwartet ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) oder die ISO Nummer des Landes 
	 * in dem die Ausbildung statt fand enthaelt.
	 * 
	 * @param country the country to set
	 * 
	 */
	public void setCountry(CountryDTO country) {
		this.country = country;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the instituteName
	 */
	public String getInstituteName() {
		return instituteName;
	}
	/**
	 * @param instituteName the instituteName to set
	 */
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public void setUntilToday(String untilToday) {
		this.untilToday = untilToday;
	}
	public String getUntilToday() {
		return untilToday;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getField() {
		return field;
	}
	public void setStarted(Calendar started) {
		this.started = started;
	}
	public Calendar getStarted() {
		return started;
	}

	
	
}
