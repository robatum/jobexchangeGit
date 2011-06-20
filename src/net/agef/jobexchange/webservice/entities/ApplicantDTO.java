/**
 * 
 */
package net.agef.jobexchange.webservice.entities;

import java.util.Calendar;

import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.PublicationTypeEnum;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * 
 *
 * Die Klasse ApplicantDTO ist eine Domain Klasse und haelt Getter und Setter-Methoden 
 * zum Abfragen/Setzen von bewerberprofilrelevanten Daten im Kontext der Jobboerse bereit.
 * 
 * 
 * @author Andreas Pursian
 */
public class ApplicantDTO{
	
	private Long applicantProfileId;
	private Long applicantProfileOwnerId;
    private String durationOfContract;
    
    /* Bewerberprofil Sommer 2010 */
    private String currentStatus;
	private String lookingFor;
	private String offering;
	private WorkUserTypeDTO[] workTypes;
	private String availability;
	
	private String referencesAndCertificates;
	private String referencesAndCertificatesComments;
	
	private String furtherOnlineActivities;
	private String furtherOnlineActivitiesComments;

	private String publications;
	private String publicationsComments;
	
	private String publicationType;
	private String profilePhoto;
	/* Ende Bewerberprofil Sommer 2010 */
    
	private EducationDTO highestDegree;
	private EducationDTO[] furtherEducation;
	private WorkExperienceDTO[] workExperience;
	
	private String managementExperience;
	private Long managementExperienceSector;
	private String managementExperienceDuration;
	private String managementExperienceTeamSize;
	private String managementExperienceRemarks;
	private String computerSkills;
	private String computerSkillsComments;
	private String drivingLicence;
	private String drivingLicenceComments;
	private String additionalSkills;
	
	private String languageSkillsGerman;
	private String languageSkillsEnglish;
	private LanguageSkillDTO[] languageSkillsOther;
	
	private String preferredFieldOfActivity;
	private CountryDTO preferredLocation;
	private String locationRemarks;
	private String additionalRemarks;
	private Calendar availableFromDate;
	
	public ApplicantDTO(){
		
	}
	
	
	/**
	 * @return the applicantProfileOwner
	 * 
	 * Liefert die eindeutige Bewerberprofil Id.
	 */
	public Long getApplicantProfileId() {
		return applicantProfileId;
	}
	/**
	 * @param applicantProfileOwner the applicantProfileOwner to set
	 * 
	 * Erwartet die eindeutige Bewerberprofil Id.
	 */
	public void setApplicantProfileId(Long applicantProfileId) {
		this.applicantProfileId = applicantProfileId;
	}
	/**
	 * @return the applicantProfileOwnerId
	 * 
	 * Liefert die Id des Besitzers des aktuellen Bewerberprofils also die Portal User ID. 
	 * Wenn diese Id null ist, dann wurde das Stellenangebot ueber das AGEF Backend eingestellt. 
	 */
	public Long getApplicantProfileOwnerId() {
		return applicantProfileOwnerId;
	}
	/**
	 * @param applicantProfileOwnerId the applicantProfileOwnerId to set
	 * 
	 * Setzt die Id des Besitzers des aktuellen Bewerberprofils also die Portal User ID
	 */
	public void setApplicantProfileOwnerId(Long applicantProfileOwnerId) {
		this.applicantProfileOwnerId = applicantProfileOwnerId;
	}
	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}


	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}


	/**
	 * @return the lookingFor
	 */
	public String getLookingFor() {
		return lookingFor;
	}


	/**
	 * @param lookingFor the lookingFor to set
	 */
	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}


	/**
	 * @return the offering
	 */
	public String getOffering() {
		return offering;
	}


	/**
	 * @param offering the offering to set
	 */
	public void setOffering(String offering) {
		this.offering = offering;
	}


	public WorkUserTypeDTO[] getWorkTypes() {
		return workTypes;
	}


	public void setWorkTypes(WorkUserTypeDTO[] workTypes) {
		this.workTypes = workTypes;
	}


	public String getAvailability() {
		return availability;
	}


	public void setAvailability(String availability) {
		this.availability = availability;
	}


	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * SHORTTERM_1_TO_3_MONTH, 
	 * LONGTERM_3_MONTH_TO_2_YEARS,
	 * PERMANENT
	 * 
	 * @return the durationOfContract
	 */
	public String getDurationOfContract() {
		return durationOfContract;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * SHORTTERM_1_TO_3_MONTH,
	 * LONGTERM_3_MONTH_TO_2_YEARS,
	 * PERMANENT
	 * 
	 * @param durationOfContract the durationOfContract to set
	 */
	public void setDurationOfContract(String durationOfContract) {
		this.durationOfContract = durationOfContract;
	}
	/**
	 * Liefert ein Objekt der Klasse EducationDTO welches alle Daten zur hoechsten Ausbildung
	 * des Bewerbers enthaelt. 
	 * 
	 * @return the highestDegree
	 */
	public EducationDTO getHighestDegree() {
		return highestDegree;
	}
	/**
	 * Erwartet ein Objekt der Klasse EducationDTO welches alle Daten zur hoechsten Ausbildung
	 * des Bewerbers enthaelt.
	 * 
	 * @param highestDegree the highestDegree to set
	 * 
	 */
	public void setHighestDegree(EducationDTO highestDegree) {
		this.highestDegree = highestDegree;
	}
	/**
	 * Liefert ein Array vom Typ EducationDTO und enthaelt eine unsortierte Liste
	 * von weiteren Ausbildungsdaten.
	 * 
	 * @return the furtherEducation
	 * 
	 */
	public EducationDTO[] getFurtherEducation() {
		return furtherEducation;
	}
	/**
	 * Erwartet ein Array vom Typ EducationDTO welches eine unsortierte Liste
	 * von weiteren Ausbildungsdaten enthaelt.
	 * 
	 * @param furtherEducation the furtherEducation to set
	 * 
	 */
	public void setFurtherEducation(EducationDTO[] furtherEducation) {
		this.furtherEducation = furtherEducation;
	}
	/**
	 * Liefert ein Array vom Typ WorkExperienceDTO und enthaelt eine unsortierte Liste
	 * von Arbeitserfahrungen.
	 * 
	 * @return the workExperience
	 * 
	 */
	public WorkExperienceDTO[] getWorkExperience() {
		return workExperience;
	}
	/**
	 * Erwartet ein Array vom Typ WorkExperienceDTO welches eine unsortierte Liste
	 * von Arbeitserfahrungen enthaelt.
	 * 
	 * @param workExperience the workExperience to set
	 * 
	 */
	public void setWorkExperience(WorkExperienceDTO[] workExperience) {
		this.workExperience = workExperience;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * YES, 
	 * NO
	 * 
	 * @return the managementExperience
	 * 
	 */
	public String getManagementExperience() {
		return managementExperience;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * YES, 
	 * NO
	 * 
	 * @param managementExperience the managementExperience to set
	 * 
	 */
	public void setManagementExperience(String managementExperience) {
		this.managementExperience = managementExperience;
	}
	/**
	 * Liefert die Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @return the managementExperienceSector
	 * 
	 */
	public Long getManagementExperienceSector() {
		return managementExperienceSector;
	}
	/**
	 * Erwartet die Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @param managementExperienceSector the managementExperienceSector to set
	 * 
	 */
	public void setManagementExperienceSector(Long managementExperienceSector) {
		this.managementExperienceSector = managementExperienceSector;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * ZERO_TO_ONE,
	 * ONE_TO_TWO,
	 * TWO_TO_FIVE,
	 * FIVE_TO_TEN,
	 * MORE_THEN_TEN,
	 * 
	 * @return the managementExperienceDuration
	 * 
	 */
	public String getManagementExperienceDuration() {
		return managementExperienceDuration;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * ZERO_TO_ONE,
	 * ONE_TO_TWO,
	 * TWO_TO_FIVE,
	 * FIVE_TO_TEN,
	 * MORE_THEN_TEN
	 * 
	 * @param managementExperienceDuration the managementExperienceDuration to set
	 * 
	 */
	public void setManagementExperienceDuration(String managementExperienceDuration) {
		this.managementExperienceDuration = managementExperienceDuration;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * ONE_TO_TEN,
	 * ELEVEN_TO_THIRTY,
	 * THIRTYONE_TO_HUNDRED,
	 * MORE_THEN_HUNDRED
	 * 
	 * @return the managementExperienceTeamSize
	 * 
	 */
	public String getManagementExperienceTeamSize() {
		return managementExperienceTeamSize;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * ONE_TO_TEN,
	 * ELEVEN_TO_THIRTY,
	 * THIRTYONE_TO_HUNDRED,
	 * MORE_THEN_HUNDRED
	 * 
	 * @param managementExperienceTeamSize the managementExperienceTeamSize to set
	 * 
	 */
	public void setManagementExperienceTeamSize(String managementExperienceTeamSize) {
		this.managementExperienceTeamSize = managementExperienceTeamSize;
	}
	/**
	 * @return the managementExperienceRemarks
	 */
	public String getManagementExperienceRemarks() {
		return managementExperienceRemarks;
	}
	/**
	 * @param managementExperienceRemarks the managementExperienceRemarks to set
	 */
	public void setManagementExperienceRemarks(String managementExperienceRemarks) {
		this.managementExperienceRemarks = managementExperienceRemarks;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * YES,
	 * NO
	 * 
	 * @return the computerSkills
	 * 
	 */
	public String getComputerSkills() {
		return computerSkills;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * YES, 
	 * NO
	 * 
	 * @param computerSkills the computerSkills to set
	 * 
	 */
	public void setComputerSkills(String computerSkills) {
		this.computerSkills = computerSkills;
	}
	/**
	 * @return the computerSkillsComments
	 */
	public String getComputerSkillsComments() {
		return computerSkillsComments;
	}
	/**
	 * @param computerSkillsComments the computerSkillsComments to set
	 */
	public void setComputerSkillsComments(String computerSkillsComments) {
		this.computerSkillsComments = computerSkillsComments;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * YES, 
	 * NO
	 * 
	 * @return the drivingLicence
	 * 
	 */
	public String getDrivingLicence() {
		return drivingLicence;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * YES, 
	 * NO
	 * 
	 * @param drivingLicence the drivingLicence to set
	 * 
	 */
	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}
	/**
	 * @return the drivingLicenceComments
	 */
	public String getDrivingLicenceComments() {
		return drivingLicenceComments;
	}
	/**
	 * @param drivingLicenceComments the drivingLicenceComments to set
	 */
	public void setDrivingLicenceComments(String drivingLicenceComments) {
		this.drivingLicenceComments = drivingLicenceComments;
	}
	/**
	 * @return the additionalSkills
	 */
	public String getAdditionalSkills() {
		return additionalSkills;
	}
	/**
	 * @param additionalSkills the additionalSkills to set
	 */
	public void setAdditionalSkills(String additionalSkills) {
		this.additionalSkills = additionalSkills;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * MOTHER_TONGUE,
	 * BUSINESS_FLUENT,
	 * FLUENT,
	 * BASIC_KNOWLEDGE
	 * 
	 * @return the languageSkillsGerman
	 * 
	 */
	public String getLanguageSkillsGerman() {
		return languageSkillsGerman;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * MOTHER_TONGUE,
	 * BUSINESS_FLUENT,
	 * FLUENT,
	 * BASIC_KNOWLEDGE
	 * 
	 * @param languageSkillsGerman the languageSkillsGerman to set
	 * 
	 */
	public void setLanguageSkillsGerman(String languageSkillsGerman) {
		this.languageSkillsGerman = languageSkillsGerman;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * MOTHER_TONGUE,
	 * BUSINESS_FLUENT,
	 * FLUENT,
	 * BASIC_KNOWLEDGE
	 * 
	 * @return the languageSkillsEnglish
	 * 
	 */
	public String getLanguageSkillsEnglish() {
		return languageSkillsEnglish;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * MOTHER_TONGUE,
	 * BUSINESS_FLUENT,
	 * FLUENT,
	 * BASIC_KNOWLEDGE
	 * 
	 * @param languageSkillsEnglish the languageSkillsEnglish to set
	 * 
	 */
	public void setLanguageSkillsEnglish(String languageSkillsEnglish) {
		this.languageSkillsEnglish = languageSkillsEnglish;
	}
	/**
	 * Liefert ein Array vom Typ LanguageSkillDTO und enthaelt eine unsortierte Liste
	 * von weiteren Sprachkenntnissen.
	 * 
	 * @return the languageSkillsOther
	 * 
	 */
	public LanguageSkillDTO[] getLanguageSkillsOther() {
		return languageSkillsOther;
	}
	/**
	 * Erwartet ein Array vom Typ LanguageSkillDTO und enthaelt eine unsortierte Liste
	 * von weiteren Sprachkenntnissen.
	 * 
	 * @param languageSkillsOther the languageSkillsOther to set
	 * 
	 */
	public void setLanguageSkillsOther(LanguageSkillDTO[] languageSkillsOther) {
		this.languageSkillsOther = languageSkillsOther;
	}
	/**
	 * Liefert die Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @return the preferredFieldOfActivity
	 * 
	 */
	public String getPreferredFieldOfActivity() {
		return preferredFieldOfActivity;
	}
	/**
	 * Erwartet die Bereichs Id der Berufsfeldertabelle.
	 * 
	 * @param preferredFieldOfActivity the preferredFieldOfActivity to set
	 * 
	 */
	public void setPreferredFieldOfActivity(String preferredFieldOfActivity) {
		this.preferredFieldOfActivity = preferredFieldOfActivity;
	}
	/**
	 * Liefert ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) des Landes 
	 * der Adresse enthaelt.
	 * 
	 * @return the preferredLocation
	 * 
	 */
	public CountryDTO getPreferredLocation() {
		return preferredLocation;
	}
	
	/**
	 * Erwartet ein Objekt der Klasse CountryDTO, welches den Namen (englische Kurzschreibweise) oder die ISO Nummer des Landes 
	 * der Adresse enthaelt.
	 * 
	 * @param preferredLocation the preferredLocation to set
	 * 
	 */
	public void setPreferredLocation(CountryDTO preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	/**
	 * @return the locationRemarks
	 */
	public String getLocationRemarks() {
		return locationRemarks;
	}
	/**
	 * @param locationRemarks the locationRemarks to set
	 */
	public void setLocationRemarks(String locationRemarks) {
		this.locationRemarks = locationRemarks;
	}
	/**
	 * @return the additionalRemarks
	 */
	public String getAdditionalRemarks() {
		return additionalRemarks;
	}
	/**
	 * @param additionalRemarks the additionalRemarks to set
	 */
	public void setAdditionalRemarks(String additionalRemarks) {
		this.additionalRemarks = additionalRemarks;
	}


	/**
	 * @return the availableFromDate
	 */
	public Calendar getAvailableFromDate() {
		return availableFromDate;
	}


	/**
	 * @param availableFromDate the availableFromDate to set
	 */
	public void setAvailableFromDate(Calendar availableFromDate) {
		this.availableFromDate = availableFromDate;
	}


	public String getReferencesAndCertificates() {
		return referencesAndCertificates;
	}


	public void setReferencesAndCertificates(String referencesAndCertificates) {
		this.referencesAndCertificates = referencesAndCertificates;
	}


	public String getReferencesAndCertificatesComments() {
		return referencesAndCertificatesComments;
	}


	public void setReferencesAndCertificatesComments(String referencesAndCertificatesComments) {
		this.referencesAndCertificatesComments = referencesAndCertificatesComments;
	}


	public String getFurtherOnlineActivities() {
		return furtherOnlineActivities;
	}


	public void setFurtherOnlineActivities(String furtherOnlineActivities) {
		this.furtherOnlineActivities = furtherOnlineActivities;
	}


	public String getFurtherOnlineActivitiesComments() {
		return furtherOnlineActivitiesComments;
	}


	public void setFurtherOnlineActivitiesComments(String furtherOnlineActivitiesComments) {
		this.furtherOnlineActivitiesComments = furtherOnlineActivitiesComments;
	}


	public String getPublications() {
		return publications;
	}


	public void setPublications(String publications) {
		this.publications = publications;
	}


	public String getPublicationsComments() {
		return publicationsComments;
	}


	public void setPublicationsComments(String publicationsComments) {
		this.publicationsComments = publicationsComments;
	}


	public String getPublicationType() {
		return publicationType;
	}


	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}


	public String getProfilePhoto() {
		return profilePhoto;
	}


	public void setProfilePhoto(String photo) {
		this.profilePhoto = photo;
	}

}
