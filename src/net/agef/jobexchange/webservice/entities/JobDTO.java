/**
 * 
 */
package net.agef.jobexchange.webservice.entities;

import java.util.Calendar;
import java.util.Date;


/**
 * 
 * 
 * Die Klasse JobDTO ist eine Domain Klasse und haelt Getter und Setter-Methoden
 * zum Abfragen/ Setzen von stellenrelevanten Daten im Kontext der Jobboerse bereit.
 * 
 * @author Andreas Pursian
 * 
 */
public class JobDTO {
	
	private Long jobOfferId;
	private Long cobraJobId;
	private Long jobOfferOwner;
	private ContactPersonDTO contactPerson;
	private AddressDTO contactPersonAddress;
		
	private String organisationName;
	private String organisationDescription;
	private String organisationIndustrySector;

	private Integer numberOfJobs;
	private String jobDescription;
	private String taskDescription;
	private String locationOfEmployment;
	private CountryDTO countryOfEmployment;
	private String minimumRequirementsForEducation;
	private String furtherCommentsRegardingEducation;
	private String desiredProfession;
	private String alternativeProfession;
	private String workExperience;
	
	private String languageSkillsGerman;
	private String languageSkillsEnglish;
	private LanguageSkillDTO[] languageSkillsOther;
	private String computerSkills;
	private String computerSkillsComments;
	private String drivingLicence;
	
	private String specialKnowledge;
	private String furtherRequirements;
	private Calendar possibleCommencementDate;
	private String durationOfContract;
	private Integer weeklyHoursOfWork;
	private String salary;
	private String currency;
	private String miscellaneousServices;
	
	private String preferredPublication;
	private String occupationalField;
	private String occupationalSubField;
	private String commentsRegardingApplicationProcedure;
	private String applicationFormLink;
	private Calendar applicationExpireDate;
	private String attachmentLocation;
	private String furtherComments;
	private Calendar jobOfferExpireDate;
	
	public JobDTO(){
	}
	
	/**
	 * Liefert die JobOfferId des aktuellen Stellenabgebotes. 
	 * 
	 * @return the jobOfferId
	 * 
	 */
	public Long getJobOfferId() {
		return jobOfferId;
	}
	/**
	 * Setzt die JobOfferId des aktuellen Stellenabgebotes. 
	 * 
	 * @param jobOfferId the jobOfferId to set
	 * 
	 */
	public void setJobOfferId(Long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}
	/**
	 * @return the cobraJobId
	 */
	public Long getCobraJobId() {
		return cobraJobId;
	}
	/**
	 * @param cobraJobId the cobraJobId to set
	 */
	public void setCobraJobId(Long cobraJobId) {
		this.cobraJobId = cobraJobId;
	}

	/**
	 * Liefert die Id des Besitzers des aktuellen Stellenabgebotes also die APD User ID. 
	 * Wenn diese Id null ist, dann wurde das Stellenangebot ueber das AGEF Backend eingestellt.
	 * 
	 * @return the jobOfferOwner
	 *  
	 */
	public Long getJobOfferOwner() {
		return jobOfferOwner;
	}
	/**
	 * Setzt die Id des Besitzers des aktuellen Stellenabgebotes also die APD User ID.
	 * 
	 * @param jobOfferOwner the jobOfferOwner to set
	 * 
	 */
	public void setJobOfferOwner(Long jobOfferOwner) {
		this.jobOfferOwner = jobOfferOwner;
	}
	/**
	 * Liefert ein Objekt der Klasse ContactPersonDTO welches alle Daten zur Kontaktperson zu diesem konkreten
	 * Stellenangebot enthaelt. 
	 * 
	 * @return the contactPerson
	 * 
	 */
	public ContactPersonDTO getContactPerson() {
		return contactPerson;
	}
	/**
	 * Erwartet ein Objekt der Klasse ContactPersonDTO, welches alle Daten zur Kontaktperson zu diesem konkreten
	 * Stellenangebot enthaelt. 
	 * 
	 * @param contactPerson the contactPerson to set
	 * 
	 */
	public void setContactPerson(ContactPersonDTO contactPerson) {
		this.contactPerson = contactPerson;
	}
	/**
	 * Liefert ein Objekt der Klasse AddressDTO welches alle Adressdaten zur Kontaktperson zu diesem konkreten
	 * Stellenangebot enthaelt.
	 * 
	 * @return the contactPersonAddress
	 * 
	 */
	public AddressDTO getContactPersonAddress() {
		return contactPersonAddress;
	}
	/**
	 * Erwartet ein Objekt der Klasse AddressDTO welches alle Adressdaten zur Kontaktperson zu diesem konkreten
	 * Stellenangebot enthaelt.
	 * 
	 * @param contactPersonAddress the contactPersonAddress to set
	 * 
	 */
	public void setContactPersonAddress(AddressDTO contactPersonAddress) {
		this.contactPersonAddress = contactPersonAddress;
	}
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

	/**
	 * @return the numberOfJobs
	 */
	public Integer getNumberOfJobs() {
		return numberOfJobs;
	}
	/**
	 * @param numberOfJobs the numberOfJobs to set
	 */
	public void setNumberOfJobs(Integer numberOfJobs) {
		this.numberOfJobs = numberOfJobs;
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
	 * @return the taskDescription
	 */
	public String getTaskDescription() {
		return taskDescription;
	}
	/**
	 * @param taskDescription the taskDescription to set
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	/**
	 * @return the locationOfEmployment
	 */
	public String getLocationOfEmployment() {
		return locationOfEmployment;
	}
	/**
	 * @param locationOfEmployment the locationOfEmployment to set
	 */
	public void setLocationOfEmployment(String locationOfEmployment) {
		this.locationOfEmployment = locationOfEmployment;
	}
	/**
	 * Liefert ein Objekt der Klasse CountryDTO welches den Namen des Landes in dem das Stellenangebot
	 * ausgeschrieben wurde enthaelt.
	 * 
	 * @return the countryOfEmployment
	 * 
	 */
	public CountryDTO getCountryOfEmployment() {
		return countryOfEmployment;
	}
	/**
	 * Erwartet ein Objekt der Klasse CountryDTO, welches den Namen oder die ISO Nummer des Landes in dem das Stellenangebot
	 * ausgeschrieben wurde enthaelt.
	 * 
	 * @param countryOfEmployment the countryOfEmployment to set
	 * 
	 */
	public void setCountryOfEmployment(CountryDTO countryOfEmployment) {
		this.countryOfEmployment = countryOfEmployment;
	}
	/**
	 * Ist ein Enum Type und gibt einen der folgenden Werte zurück:
	 * 
	 * BACHELOR,
	 * MASTER,
	 * DIPLOMA,
	 * MAGISTER,
	 * VOCATIONAL_TRAINING,
	 * DR,
	 * PROF
	 * 
	 * @return the minimumRequirementsForEducation
	 */
	public String getMinimumRequirementsForEducation() {
		return minimumRequirementsForEducation;
	}
	/**
	 * Ist ein Enum Type und erwartet einen der folgenden Werte:
	 * 
	 * BACHELOR,
	 * MASTER,
	 * DIPLOMA,
	 * MAGISTER,
	 * VOCATIONAL_TRAINING,
	 * DR,
	 * PROF
	 * 
	 * 
	 * @param minimumRequirementsForEducation the minimumRequirementsForEducation to set
	 */
	public void setMinimumRequirementsForEducation(
			String minimumRequirementsForEducation) {
		this.minimumRequirementsForEducation = minimumRequirementsForEducation;
	}
	/**
	 * @return the furtherCommentsRegardingEducation
	 */
	public String getFurtherCommentsRegardingEducation() {
		return furtherCommentsRegardingEducation;
	}
	/**
	 * @param furtherCommentsRegardingEducation the furtherCommentsRegardingEducation to set
	 */
	public void setFurtherCommentsRegardingEducation(
			String furtherCommentsRegardingEducation) {
		this.furtherCommentsRegardingEducation = furtherCommentsRegardingEducation;
	}
	/**
	 * @return the desiredProfession
	 */
	public String getDesiredProfession() {
		return desiredProfession;
	}
	/**
	 * @param desiredProfession the desiredProfession to set
	 */
	public void setDesiredProfession(String desiredProfession) {
		this.desiredProfession = desiredProfession;
	}
	/**
	 * @return the alternativeProfession
	 */
	public String getAlternativeProfession() {
		return alternativeProfession;
	}
	/**
	 * @param alternativeProfession the alternativeProfession to set
	 */
	public void setAlternativeProfession(String alternativeProfession) {
		this.alternativeProfession = alternativeProfession;
	}
	/**
	 * @return the workExperience
	 */
	public String getWorkExperience() {
		return workExperience;
	}
	/**
	 * @param workExperience the workExperience to set
	 */
	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
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
	 * @return the specialKnowledge
	 */
	public String getSpecialKnowledge() {
		return specialKnowledge;
	}
	/**
	 * @param specialKnowledge the specialKnowledge to set
	 */
	public void setSpecialKnowledge(String specialKnowledge) {
		this.specialKnowledge = specialKnowledge;
	}
	/**
	 * @return the furtherRequirements
	 */
	public String getFurtherRequirements() {
		return furtherRequirements;
	}
	/**
	 * @param furtherRequirements the furtherRequirements to set
	 */
	public void setFurtherRequirements(String furtherRequirements) {
		this.furtherRequirements = furtherRequirements;
	}
	/**
	 * @return the possibleCommencementDate
	 */
	public Calendar getPossibleCommencementDate() {
		return possibleCommencementDate;
	}
	/**
	 * @param possibleCommencementDate the possibleCommencementDate to set
	 */
	public void setPossibleCommencementDate(Calendar possibleCommencementDate) {
		this.possibleCommencementDate = possibleCommencementDate;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * SHORTTERM_1_TO_3_MONTH,
	 * LONGTERM_3_MONTH_TO_2_YEARS,
	 * PERMANENT
	 * 
	 * @return the durationOfContract
	 * 
	 */
	public String getDurationOfContract() {
		return durationOfContract;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * SHORTTERM_1_TO_3_MONTH,
	 * LONGTERM_3_MONTH_TO_2_YEARS,
	 * PERMANENT
	 * 
	 * @param durationOfContract the durationOfContract to set
	 * 
	 */
	public void setDurationOfContract(String durationOfContract) {
		this.durationOfContract = durationOfContract;
	}
	/**
	 * Liefert die vorraussichtliche Wochenarbeitszeit in Stunden als Integer Wert.
	 * 
	 * @return the weeklyHoursOfWork
	 * 
	 */
	public Integer getWeeklyHoursOfWork() {
		return weeklyHoursOfWork;
	}
	/**
	 * Erwartet die vorraussichtliche Wochenarbeitszeit in Stunden als Integer Wert. 
	 * 
	 * @param weeklyHoursOfWork the weeklyHoursOfWork to set
	 * 
	 */
	public void setWeeklyHoursOfWork(Integer weeklyHoursOfWork) {
		this.weeklyHoursOfWork = weeklyHoursOfWork;
	}
	/**
	 * Liefert die vorraussichtliche Monatsgehalt als Integer Wert.
	 * 
	 * @return the salary
	 * 
	 */
	public String getSalary() {
		return salary;
	}
	/**
	 * Erwartet die vorraussichtliche Monatsgehalt als Integer Wert.
	 * 
	 * @param salary the salary to set
	 * 
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}
	/**
	 * Liefert die Abkuerzung des internationalen Waehrungsnames in dem das Monatsgehalt gezahlt wird.
	 * 
	 * @return the currency
	 *  
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * Erwartet die Abkuerzung des internationalen Waehrungsnames oder den entsprechenden ISO Wert.
	 * 
	 * @param currency the currency to set
	 * 
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the miscellaneousServices
	 */
	public String getMiscellaneousServices() {
		return miscellaneousServices;
	}
	/**
	 * @param miscellaneousServices the miscellaneousServices to set
	 */
	public void setMiscellaneousServices(String miscellaneousServices) {
		this.miscellaneousServices = miscellaneousServices;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * FULL_WITH_COMPLETE_ADDRESS,
	 * ANONYMOUS_WITH_SECTOR_AND_JOB_REFERENCE_NUMBER
	 * 
	 * @return the preferredPublication
	 * 
	 */
	public String getPreferredPublication() {
		return preferredPublication;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * FULL_WITH_COMPLETE_ADDRESS,
	 * ANONYMOUS_WITH_SECTOR_AND_JOB_REFERENCE_NUMBER
	 * 
	 * @param preferredPublication the preferredPublication to set
	 *  
	 */
	public void setPreferredPublication(String preferredPublication) {
		this.preferredPublication = preferredPublication;
	}


	/**
	 * @return the occupationalField
	 */
	public String getOccupationalField() {
		return occupationalField;
	}

	/**
	 * @param occupationalField the occupationalField to set
	 */
	public void setOccupationalField(String occupationalField) {
		this.occupationalField = occupationalField;
	}
	
	
	/**
	 * @return the occupationalSubField
	 */
	public String getOccupationalSubField() {
		return occupationalSubField;
	}

	/**
	 * @param occupationalSubField the occupationalSubField to set
	 */
	public void setOccupationalSubField(String occupationalSubField) {
		this.occupationalSubField = occupationalSubField;
	}

	/**
	 * @return the commentsRegardingApplicationProcedure
	 */
	public String getCommentsRegardingApplicationProcedure() {
		return commentsRegardingApplicationProcedure;
	}
	/**
	 * @param commentsRegardingApplicationProcedure the commentsRegardingApplicationProcedure to set
	 */
	public void setCommentsRegardingApplicationProcedure(
			String commentsRegardingApplicationProcedure) {
		this.commentsRegardingApplicationProcedure = commentsRegardingApplicationProcedure;
	}
	/**
	 * @return the applicationFormLink
	 */
	public String getApplicationFormLink() {
		return applicationFormLink;
	}

	/**
	 * @param applicationFormLink the applicationFormLink to set
	 */
	public void setApplicationFormLink(String applicationFormLink) {
		this.applicationFormLink = applicationFormLink;
	}

	/**
	 * @return the applicationExpireDate
	 */
	public Calendar getApplicationExpireDate() {
		return applicationExpireDate;
	}

	/**
	 * @param applicationExpireDate the applicationExpireDate to set
	 */
	public void setApplicationExpireDate(Calendar applicationExpireDate) {
		this.applicationExpireDate = applicationExpireDate;
	}

	/**
	 * @return the furtherComments
	 */
	public String getFurtherComments() {
		return furtherComments;
	}
	/**
	 * @param furtherComments the furtherComments to set
	 */
	public void setFurtherComments(String furtherComments) {
		this.furtherComments = furtherComments;
	}
	/**
	 * @return the attachmentLocation
	 */
	public String getAttachmentLocation() {
		return attachmentLocation;
	}

	/**
	 * @param attachmentLocation the attachmentLocation to set
	 */
	public void setAttachmentLocation(String attachmentLocation) {
		this.attachmentLocation = attachmentLocation;
	}

	/**
	 * @return the jobOfferExpireDate
	 */
	public Calendar getJobOfferExpireDate() {
		return jobOfferExpireDate;
	}
	/**
	 * @param jobOfferExpireDate the jobOfferExpireDate to set
	 */
	public void setJobOfferExpireDate(Calendar jobOfferExpireDate) {
		this.jobOfferExpireDate = jobOfferExpireDate;
	}
	
	
	

}
