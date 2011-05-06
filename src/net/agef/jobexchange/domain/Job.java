package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.Date;


public interface Job {

	/**
	 * @return the jobOfferId
	 */

	
	public long getJobOfferId();

	/**
	 * @param jobOfferId the jobOfferId to set
	 */
	public void setJobOfferId(long jobOfferId);

	/**
	 * @return the jobOfferOwner
	 */
	public User getJobOfferOwner();

	/**
	 * @param jobOfferOwner the jobOfferOwner to set
	 */
	public void setJobOfferOwner(User jobOfferOwner);

	/**
	 * @return the jobApplications
	 */
	public User getJobApplications();

	/**
	 * @param jobApplications the jobApplications to set
	 */
	public void setJobApplications(User jobApplications);

	/**
	 * @return the dataProvider
	 */
	public DataProvider getDataProvider();

	/**
	 * @param dataProvider the dataProvider to set
	 */
	public void setDataProvider(DataProvider dataProvider);

	/**
	 * @return the organisationName
	 */
	public String getOrganisationName();
//
//	/**
//	 * @param organisationName the organisationName to set
//	 */
//	public void setOrganisationName(String organisationName);
//
	/**
	 * @return the organisationDescription
	 */
	public String getOrganisationDescription();
//
//	/**
//	 * @param organisationDescription the organisationDescription to set
//	 */
//	public void setOrganisationDescription(String organisationDescription);

	/**
	 * @return the numberOfJobs
	 */
	public Integer getNumberOfJobs();

	/**
	 * @param numberOfJobs the numberOfJobs to set
	 */
	public void setNumberOfJobs(Integer numberOfJobs);

	/**
	 * @return the jobDescription
	 */
	public String getJobDescription();

	/**
	 * @param jobDescription the jobDescription to set
	 */
	public void setJobDescription(String jobDescription);

	/**
	 * @return the taskDescription
	 */
	public String getTaskDescription();

	/**
	 * @param taskDescription the taskDescription to set
	 */
	public void setTaskDescription(String taskDescription);

	/**
	 * @return the locationOfEmployment
	 */
	public String getLocationOfEmployment();

	/**
	 * @param locationOfEmployment the locationOfEmployment to set
	 */
	public void setLocationOfEmployment(String locationOfEmployment);

	/**
	 * @return the countryOfEmployment
	 */
	public Country getCountryOfEmployment();

	/**
	 * @param countryOfEmployment the countryOfEmployment to set
	 */
	public void setCountryOfEmployment(Country countryOfEmployment);

	/**
	 * @return the minimumRequirementsForEducation
	 */
	public String getMinimumRequirementsForEducation();

	/**
	 * @param minimumRequirementsForEducation the minimumRequirementsForEducation to set
	 */
	public void setMinimumRequirementsForEducation(
			String minimumRequirementsForEducation);

	/**
	 * @return the furtherCommentsRegardingEducation
	 */
	public String getFurtherCommentsRegardingEducation();

	/**
	 * @param furtherCommentsRegardingEducation the furtherCommentsRegardingEducation to set
	 */
	public void setFurtherCommentsRegardingEducation(
			String furtherCommentsRegardingEducation);

	/**
	 * @return the desiredProfession
	 */
	public String getDesiredProfession();

	/**
	 * @param desiredProfession the desiredProfession to set
	 */
	public void setDesiredProfession(String desiredProfession);

	/**
	 * @return the alternativeProfession
	 */
	public String getAlternativeProfession();

	/**
	 * @param alternativeProfession the alternativeProfession to set
	 */
	public void setAlternativeProfession(String alternativeProfession);

	/**
	 * @return the workExperience
	 */
	public Integer getWorkExperience();

	/**
	 * @param workExperience the workExperience to set
	 */
	public void setWorkExperience(Integer workExperience);

	/**
	 * @return the languageSkillsGerman
	 */
	public LanguageSkillsEnum getLanguageSkillsGerman();

	/**
	 * @param languageSkillsGerman the languageSkillsGerman to set
	 */
	public void setLanguageSkillsGerman(LanguageSkillsEnum languageSkillsGerman);

	/**
	 * @return the languageSkillsEnglish
	 */
	public LanguageSkillsEnum getLanguageSkillsEnglish();

	/**
	 * @param languageSkillsEnglish the languageSkillsEnglish to set
	 */
	public void setLanguageSkillsEnglish(
			LanguageSkillsEnum languageSkillsEnglish);

	/**
	 * @return the languageSkillsOther
	 */
	public Collection<LanguageSkill> getLanguageSkillsOther();

	/**
	 * @param languageSkillsOther the languageSkillsOther to set
	 */
	public void setLanguageSkillsOther(
			Collection<LanguageSkill> languageSkillsOther);

	/**
	 * @return the computerSkills
	 */
	public DecisionYesNoEnum getComputerSkills();

	/**
	 * @param computerSkills the computerSkills to set
	 */
	public void setComputerSkills(DecisionYesNoEnum computerSkills);

	/**
	 * @return the computerSkillsComments
	 */
	
	public String getComputerSkillsComments();

	/**
	 * @param computerSkillsComments the computerSkillsComments to set
	 */
	public void setComputerSkillsComments(String computerSkillsComments);

	/**
	 * @return the drivingLicence
	 */
	public DecisionYesNoEnum getDrivingLicence();

	/**
	 * @param drivingLicence the drivingLicence to set
	 */
	public void setDrivingLicence(DecisionYesNoEnum drivingLicence);

	/**
	 * @return the specialKnowledge
	 */
	public String getSpecialKnowledge();

	/**
	 * @param specialKnowledge the specialKnowledge to set
	 */
	public void setSpecialKnowledge(String specialKnowledge);

	/**
	 * @return the furtherRequirements
	 */
	public String getFurtherRequirements();

	/**
	 * @param furtherRequirements the furtherRequirements to set
	 */
	public void setFurtherRequirements(String furtherRequirements);

	/**
	 * @return the possibleCommencementDate
	 */
	public Date getPossibleCommencementDate();

	/**
	 * @param possibleCommencementDate the possibleCommencementDate to set
	 */
	public void setPossibleCommencementDate(Date possibleCommencementDate);

	/**
	 * @return the durationOfContract
	 */
	public ContractDurationEnum getDurationOfContract();

	/**
	 * @param durationOfContract the durationOfContract to set
	 */
	public void setDurationOfContract(ContractDurationEnum durationOfContract);

	/**
	 * @return the weeklyHoursOfWork
	 */
	public Integer getWeeklyHoursOfWork();

	/**
	 * @param weeklyHoursOfWork the weeklyHoursOfWork to set
	 */
	public void setWeeklyHoursOfWork(Integer weeklyHoursOfWork);

	/**
	 * @return the salary
	 */
	public Integer getSalary();

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(Integer salary);

	/**
	 * @return the currency
	 */
	public Currency getCurrency();

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency);

	/**
	 * @return the miscellaneousServices
	 */
	public String getMiscellaneousServices();

	/**
	 * @param miscellaneousServices the miscellaneousServices to set
	 */
	public void setMiscellaneousServices(String miscellaneousServices);

	/**
	 * @return the preferredPublication
	 */
	public PublicationTypeEnum getPreferredPublication();

	/**
	 * @param preferredPublication the preferredPublication to set
	 */
	public void setPreferredPublication(PublicationTypeEnum preferredPublication);

	/**
	 * @return the attachmentLocation
	 */
	public String getAttachmentLocation();

	/**
	 * @param attachmentLocation the attachmentLocation to set
	 */
	public void setAttachmentLocation(String attachmentLocation);

	/**
	 * @return the furtherComments
	 */
	public String getFurtherComments();

	/**
	 * @param furtherComments the furtherComments to set
	 */
	public void setFurtherComments(String furtherComments);

	/**
	 * @return the contactAdress
	 */
	public Address getCurrentContactAddress();

	/**
	 * @param jobOwner the jobOwner to set
	 */
	public void setCurrentContactAddress(Address contactAddress);

}