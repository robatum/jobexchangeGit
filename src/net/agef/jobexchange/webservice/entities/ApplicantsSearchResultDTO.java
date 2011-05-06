/**
 * 
 */
package net.agef.jobexchange.webservice.entities;

import net.agef.jobexchange.domain.AvailabilityEnum;
import net.agef.jobexchange.domain.ContractDurationEnum;
import net.agef.jobexchange.domain.LanguageSkillsEnum;

/**
 * 
 * 
 * Die Klasse ApplicantSearchResultDTO ist eine auf die Anforderungen von Suchanfragen reduzierte
 * Domain Klasse und haelt Getter und Setter-Methoden
 * zum Abfragen/ Setzen von bewerberrelevanten Daten im Kontext der Jobboerse bereit.
 * 
 * @author Andreas Pursian
 * 
 */
public class ApplicantsSearchResultDTO {

	/**
	 * 
	 */
	public ApplicantsSearchResultDTO() {
	}
	
	
	//private float __HSearch_Score;
	private Long id;
	@SuppressWarnings("unused")
	private Long applicantProfileId;
	private String fieldOfHighestDegree;
	private String combinedWorkExperiences;
	private String currentCountryOfResidence;
	private String durationOfContract;
	private String languageSkillsGerman;
	private String languageSkillsEnglish;
	private String availability;
	
	
//	/**
//	 * Enthaelt den relativen Scorring (Relevanz) Wert des aktuellen Ergebnissobjekts. Dieser Wert
//	 * ist fuer jedes Teilobjekt des Suchergebnisses eindeutig und kann z.B. für eine Sortierung nach Relevanz
//	 * genutzt werden.
//	 * 
//	 * @return the __HSearch_Score
//	 */
//	public float get__HSearch_Score() {
//		return __HSearch_Score;
//	}
//	
//	/**
//	 * 
//	 * @param __HSearch_Score the __HSearch_Score to set
//	 */
//	public void set__HSearch_Score(float __HSearch_Score) {
//		this.__HSearch_Score = __HSearch_Score;
//	}
	
	
	/**
	 *  Temporäre Variable. Kann ignoriert werden ...
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	

	/**
	 * 
	 * Die eindeutige ID des Bewerberprofils in der Jobboerse.
	 * 
	 * @return the applicantProfileId
	 */
	public Long getApplicantProfileId() {
		return id+23;
	}
	
	
	/**
	 * 
	 * 
	 * @param applicantProfileId the applicantProfileId to set
	 */
	public void setApplicantProfileId(Long applicantProfileId) {
		this.applicantProfileId = applicantProfileId;
	}

	/**
	 * Enthaelt die Angabe zum Bereich des hoechten erreichten Abschlusses. Z.B den Namen des Studienfachs,
	 * bzw. die Bezeichnung der Ausbildung, Dissertation, etc.
	 * 
	 * @return the fieldOfHighestDegree
	 */
	public String getFieldOfHighestDegree() {
		return fieldOfHighestDegree;
	}
	/**
	 * @paramfieldOfHighestDegree the fieldOfHighestDegree to set
	 */
	public void setFieldOfHighestDegree(
			String fieldOfHighestDegree) {
				this.fieldOfHighestDegree = fieldOfHighestDegree;
	}
	
	/**
	 * Enthaelt die Stellenbezeichnungen aller Arbeitserfahrungen eines Bewerberprofils. Mehrere Arbeitserfahrungen
	 * sind jeweils durch ein Semikolon von einander getrennt. 
	 * 
	 * @return the combinedWorkExperiences
	 */
	public String getCombinedWorkExperiences() {
		return combinedWorkExperiences;
	}
	
	/**
	 * @param combinedWorkExperiences the combinedWorkExperiences to set
	 */
	public void setCombinedWorkExperiences(String combinedWorkExperiences) {
		this.combinedWorkExperiences = combinedWorkExperiences;
	}

	
	
	/**
	 * @param currentCountryOfResidence the currentCountryOfResidence to set
	 */
	public void setCurrentCountryOfResidence(String currentCountryOfResidence) {
		this.currentCountryOfResidence = currentCountryOfResidence;
	}

	/**
	 * @return the currentCountryOfResidence
	 */
	public String getCurrentCountryOfResidence() {
		return currentCountryOfResidence;
	}

	/**
	 * Enthaelt die vom Arbeitgeber spezifizierte voraussichtliche Dauer des Anstellungsverhaeltnisses, also der 
	 * Laufzeit der Stelle.
	 * 
	 * @return the durationOfContract
	 */
	public String getDurationOfContract() {
		return durationOfContract;
	}
	/**
	 * @param durationOfContract the durationOfContract to set
	 */
	public void setDurationOfContract(ContractDurationEnum durationOfContract) {
		if(durationOfContract != null)
			this.durationOfContract = durationOfContract.toString();
	}
	/**
	 * Enthaelt den Kenntnissstand in der deutschen Sprache.
	 * 
	 * @return the languageSkillsGerman
	 */
	public String getLanguageSkillsGerman() {
		return languageSkillsGerman;
	}
	/**
	 * @param languageSkillsGerman the languageSkillsGerman to set
	 */
	public void setLanguageSkillsGerman(LanguageSkillsEnum languageSkillsGerman) {
		if(languageSkillsGerman != null)
		this.languageSkillsGerman = languageSkillsGerman.toString();
	}
	
	/**
	 *
	 *  Enthaelt den Kenntnissstand in der englischen Sprache.
	 * 
	 * @return the languageSkillsEnglish
	 */
	public String getLanguageSkillsEnglish() {
		return languageSkillsEnglish;
	}
	/**
	 * @param languageSkillsEnglish the languageSkillsEnglish to set
	 */
	public void setLanguageSkillsEnglish(LanguageSkillsEnum languageSkillsEnglish) {
		if(languageSkillsEnglish != null)
		this.languageSkillsEnglish = languageSkillsEnglish.toString();
	}
	
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}

}
