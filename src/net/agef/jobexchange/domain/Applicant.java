/**
 * 
 */
package net.agef.jobexchange.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.agef.jobexchange.services.lucene.ApplicantConcatClassBridge;

import org.apache.tapestry5.beaneditor.DataType;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

/**
 * @author Administrator
 *
 */

@Entity
@ClassBridge(name="applicantconcatsearchfield",
        index=Index.TOKENIZED,
        store=Store.YES,
        impl = ApplicantConcatClassBridge.class,
        params = @Parameter( name="sepChar", value=" " ) )
@Table(name="applicants")
@Indexed
public class Applicant extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -385952236779087163L;
	
	private Collection<AccessHistoryApplicant> relatedAccessHistoryApplicant = new TreeSet<AccessHistoryApplicant>();
	
	
	@IndexedEmbedded
	private User applicantProfileOwner;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private Long applicantProfileId;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Boolean onlineStatus;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	@DateBridge(resolution = Resolution.HOUR)
	private Date applicantProfileExpireDate;
	
	/* Bewerberprofil Sommer 2010 */
	private CurrentStatusEnum currentStatus;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String lookingFor;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String offering;
	
	@IndexedEmbedded
	private List<WorkUserType> workUserTypes = new ArrayList<WorkUserType>();
	
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private AvailabilityEnum availability;
	
	/* ---------- */
	private DecisionYesNoEnum referencesAndCertificates;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String referencesAndCertificatesComments;
	
	private DecisionYesNoEnum furtherOnlineActivities;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String furtherOnlineActivitiesComments;

	private DecisionYesNoEnum publications;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String publicationsComments;
	
	private PublicationTypeEnum publicationType;
	private String profilePhoto;
	/* Ende Bewerberprofil Sommer 2010 */
	
	@IndexedEmbedded
	private Education highestDegree;
	@IndexedEmbedded
	private List<Education> furtherEducation = new ArrayList<Education>();
	@IndexedEmbedded
	private List<WorkExperience> workExperience = new ArrayList<WorkExperience>();
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private DecisionYesNoEnum managementExperience;
	private IndustrySector managementExperienceSector;
	private ExperienceDurationEnum managementExperienceDuration;
	private TeamSizeEnum managementExperienceTeamSize;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String managementExperienceRemarks;
	private DecisionYesNoEnum computerSkills;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String computerSkillsComments;
	private DecisionYesNoEnum drivingLicence;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String drivingLicenceComments;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String additionalSkills;
	
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private LanguageSkillsEnum languageSkillsGerman;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private LanguageSkillsEnum languageSkillsEnglish;
	@IndexedEmbedded
	private List<LanguageSkill> languageSkillsOther = new ArrayList<LanguageSkill>();
	
	private IndustrySector preferredFieldOfActivity;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private ContractDurationEnum durationOfContract;
	private Country preferredLocation;
	private String locationRemarks;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String additionalRemarks;
	private Date availableFromDate;
	
	public Applicant(){	
		this.onlineStatus = true;
	}
	
	public Applicant(User applicantProfileOwner){
		this.applicantProfileOwner = applicantProfileOwner;
		System.out.println("ApplicantClass user: "+this.applicantProfileOwner.getApdUserId()+"--"+this.applicantProfileOwner.getFamilyName());
		this.onlineStatus = true;
	}
	
	
	
	
	/**
	 * @return the getRelatedApplicant
	 */
	@OneToMany(mappedBy="accessedApplicantProfile")
	public Collection<AccessHistoryApplicant> getRelatedAccessHistoryApplicant() {
		return relatedAccessHistoryApplicant;
	}

	/**
	 * @param getRelatedApplicant the getRelatedApplicant to set
	 */
	public void setRelatedAccessHistoryApplicant(Collection<AccessHistoryApplicant> relatedAccessHistoryApplicant) {
		this.relatedAccessHistoryApplicant = relatedAccessHistoryApplicant;
	}
	
	
	/**
	 * @return the currentStatus
	 */
	@Enumerated(EnumType.STRING)
	public CurrentStatusEnum getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(CurrentStatusEnum currentStatus) {
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

//	@OneToMany(mappedBy="applicant", cascade={CascadeType.ALL})
//	@Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	
	@OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@JoinColumn(name="applicant_id")
	@IndexColumn(name="INDEX_COL")
	public List<WorkUserType> getWorkUserTypes() {
		return workUserTypes;
	}

	public void setWorkUserTypes(List<WorkUserType> workTypes) {
		this.workUserTypes = workTypes;
	}
	
	@Enumerated(EnumType.STRING)
	public AvailabilityEnum getAvailability() {
		return availability;
	}

	public void setAvailability(AvailabilityEnum availability) {
		this.availability = availability;
	}

	@Transient
	@NonVisual
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	public String getFieldOfHighestDegree() {
			if(this.highestDegree !=null){
				return this.highestDegree.getFieldSpecialization();
			} return null;
	}
	
	
	@Transient
	@NonVisual
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	public String getCombinedWorkExperiences() {
			if(this.workExperience !=null){
				Set<String> workExperienceHash = new HashSet<String>();
				String combinedWorkExperienceString = "";
				for (Iterator<WorkExperience> it = this.getWorkExperience().iterator(); it.hasNext();){
					workExperienceHash.add(it.next().getJobTitle());
                }
				for (Iterator<String> it = workExperienceHash.iterator(); it.hasNext();){
					if(combinedWorkExperienceString != "")
					 combinedWorkExperienceString = combinedWorkExperienceString +"; "+it.next();
					else combinedWorkExperienceString = it.next();
				}
				return combinedWorkExperienceString;
			} return null;
	}

	
	@Transient
	@NonVisual
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	public String getCurrentCountryOfResidence() {
			if(this.applicantProfileOwner !=null 
				&& this.applicantProfileOwner.getCurrentContactAddress()!=null 
				&& this.applicantProfileOwner.getCurrentContactAddress().getCountry() !=null){
					String currentCountryOfResidence = ""; 
					currentCountryOfResidence = this.applicantProfileOwner.getCurrentContactAddress().getCountry().getShortEnglishName();
					if (this.applicantProfileOwner.getCurrentContactAddress().getCity()!=null)
						currentCountryOfResidence = currentCountryOfResidence + " - "+this.applicantProfileOwner.getCurrentContactAddress().getCity();
					return currentCountryOfResidence;
			} return null;
	}
	
	
	
	/**
	 * @return the applicantProfileOwner
	 */
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="applicantProfileOwner_fk")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	public User getApplicantProfileOwner() {
		return applicantProfileOwner;
	}
	/**
	 * @param applicantProfileOwner the applicantProfileOwner to set
	 */
	public void setApplicantProfileOwner(User applicantProfileOwner) {
		this.applicantProfileOwner = applicantProfileOwner;
	}
	
	/**
	 * @return the applicantProfileId
	 */
	@Formula("id + 23")
	public Long getApplicantProfileId() {
		return applicantProfileId;
	}
	/**
	 * @param applicantProfileId the applicantProfileId to set
	 */
	@NonVisual
	public void setApplicantProfileId(Long applicantProfileId) {
		this.applicantProfileId = applicantProfileId;
	}
	/**
	 * @return the onlineStatus
	 */
	public Boolean getOnlineStatus() {
		return onlineStatus;
	}

	/**
	 * @param onlineStatus the onlineStatus to set
	 */
	public void setOnlineStatus(Boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	/**
	 * @return the applicantProfileExpireDate
	 */
	public Date getApplicantProfileExpireDate() {
		return applicantProfileExpireDate;
	}

	/**
	 * @param applicantProfileExpireDate the applicantProfileExpireDate to set
	 */
	public void setApplicantProfileExpireDate(Date applicantProfileExpireDate) {
		this.applicantProfileExpireDate = applicantProfileExpireDate;
	}

	/**
	 * @return the durationOfContract
	 */
	@Enumerated(EnumType.STRING)
	public ContractDurationEnum getDurationOfContract() {
		return durationOfContract;
	}
	/**
	 * @param durationOfContract the durationOfContract to set
	 */
	public void setDurationOfContract(ContractDurationEnum durationOfContract) {
		this.durationOfContract = durationOfContract;
	}
	/**
	 * @return the highestDegree
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(name="highestDegree_fk")
	public Education getHighestDegree() {
		return highestDegree;
	}
	/**
	 * @param highestDegree the highestDegree to set
	 */
	public void setHighestDegree(Education highestDegree) {
		this.highestDegree = highestDegree;
	}
	/**
	 * @return the furtherEducation
	 */
	@OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@JoinColumn(name="applicantFurtherEdu_fk")
	@IndexColumn(name="INDEX_COL")
	public List<Education> getFurtherEducation() {
		return furtherEducation;
	}
	/**
	 * @param furtherEducation the furtherEducation to set
	 */
	public void setFurtherEducation(List<Education> furtherEducation) {
		this.furtherEducation = furtherEducation;
	}
	/**
	 * @return the workExperience
	 */
	@OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@JoinColumn(name="applicantWorkExp_fk")
	@IndexColumn(name="INDEX_COL")
	public List<WorkExperience> getWorkExperience() {
		return workExperience;
	}
	/**
	 * @param workExperience the workExperience to set
	 */
	public void setWorkExperience(List<WorkExperience> workExperience) {
		this.workExperience = workExperience;
	}
	/**
	 * @return the managementExperience
	 */
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getManagementExperience() {
		return managementExperience;
	}
	/**
	 * @param managementExperience the managementExperience to set
	 */
	@DataType(value="longtext")
	public void setManagementExperience(DecisionYesNoEnum managementExperience) {
		this.managementExperience = managementExperience;
	}
	/**
	 * @return the managementExperienceSector
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public IndustrySector getManagementExperienceSector() {
		return managementExperienceSector;
	}
	/**
	 * @param managementExperienceSector the managementExperienceSector to set
	 */
	public void setManagementExperienceSector(
			IndustrySector managementExperienceSector) {
		this.managementExperienceSector = managementExperienceSector;
	}
	/**
	 * @return the managementExperienceDuration
	 */
	@Enumerated(EnumType.STRING)
	public ExperienceDurationEnum getManagementExperienceDuration() {
		return managementExperienceDuration;
	}
	/**
	 * @param managementExperienceDuration the managementExperienceDuration to set
	 */
	public void setManagementExperienceDuration(ExperienceDurationEnum managementExperienceDuration) {
		this.managementExperienceDuration = managementExperienceDuration;
	}
	/**
	 * @return the managementExperienceTeamSize
	 */
	@Enumerated(EnumType.STRING)
	public TeamSizeEnum getManagementExperienceTeamSize() {
		return managementExperienceTeamSize;
	}
	/**
	 * @param managementExperienceTeamSize the managementExperienceTeamSize to set
	 */
	public void setManagementExperienceTeamSize(TeamSizeEnum managementExperienceTeamSize) {
		this.managementExperienceTeamSize = managementExperienceTeamSize;
	}
	/**
	 * @return the managementExperienceRemarks
	 */
	@Lob
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
	 * @return the computerSkills
	 */
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getComputerSkills() {
		return computerSkills;
	}
	/**
	 * @param computerSkills the computerSkills to set
	 */
	public void setComputerSkills(DecisionYesNoEnum computerSkills) {
		this.computerSkills = computerSkills;
	}
	/**
	 * @return the computerSkillsComments
	 */
	@Lob
	public String getComputerSkillsComments() {
		return computerSkillsComments;
	}
	/**
	 * @param computerSkillsComments the computerSkillsComments to set
	 */
	@DataType(value="longtext")
	public void setComputerSkillsComments(String computerSkillsComments) {
		this.computerSkillsComments = computerSkillsComments;
	}
	/**
	 * @return the drivingLicence
	 */
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getDrivingLicence() {
		return drivingLicence;
	}
	/**
	 * @param drivingLicence the drivingLicence to set
	 */
	public void setDrivingLicence(DecisionYesNoEnum drivingLicence) {
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
	@Lob
	public String getAdditionalSkills() {
		return additionalSkills;
	}
	/**
	 * @param additionalSkills the additionalSkills to set
	 */
	@DataType(value="longtext")
	public void setAdditionalSkills(String additionalSkills) {
		this.additionalSkills = additionalSkills;
	}
	/**
	 * @return the languageSkillsGerman
	 */
	@Enumerated(EnumType.STRING)
	public LanguageSkillsEnum getLanguageSkillsGerman() {
		return languageSkillsGerman;
	}
	/**
	 * @param languageSkillsGerman the languageSkillsGerman to set
	 */
	public void setLanguageSkillsGerman(LanguageSkillsEnum languageSkillsGerman) {
		this.languageSkillsGerman = languageSkillsGerman;
	}
	/**
	 * @return the languageSkillsEnglish
	 */
	@Enumerated(EnumType.STRING)
	public LanguageSkillsEnum getLanguageSkillsEnglish() {
		return languageSkillsEnglish;
	}
	/**
	 * @param languageSkillsEnglish the languageSkillsEnglish to set
	 */
	public void setLanguageSkillsEnglish(LanguageSkillsEnum languageSkillsEnglish) {
		this.languageSkillsEnglish = languageSkillsEnglish;
	}
	/**
	 * @return the languageSkillsOther
	 */
	@OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@JoinColumn(name="applicantLanguageSkillsOther_fk")
	@IndexColumn(name="INDEX_COL")
	public List<LanguageSkill> getLanguageSkillsOther() {
		return languageSkillsOther;
	}
	/**
	 * @param languageSkillsOther the languageSkillsOther to set
	 */
	public void setLanguageSkillsOther(List<LanguageSkill> languageSkillsOther) {
		this.languageSkillsOther = languageSkillsOther;
	}
	/**
	 * @return the preferredFieldOfActivity
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public IndustrySector getPreferredFieldOfActivity() {
		return preferredFieldOfActivity;
	}
	/**
	 * @param preferredFieldOfActivity the preferredFieldOfActivity to set
	 */
	public void setPreferredFieldOfActivity(IndustrySector preferredFieldOfActivity) {
		this.preferredFieldOfActivity = preferredFieldOfActivity;
	}
	/**
	 * @return the preferredLocation
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public Country getPreferredLocation() {
		return preferredLocation;
	}
	/**
	 * @param preferredLocation the preferredLocation to set
	 */
	public void setPreferredLocation(Country preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	/**
	 * @return the locationRemarks
	 */
	@Lob
	public String getLocationRemarks() {
		return locationRemarks;
	}
	/**
	 * @param locationRemarks the locationRemarks to set
	 */
	@DataType(value="longtext")
	public void setLocationRemarks(String locationRemarks) {
		this.locationRemarks = locationRemarks;
	}
	/**
	 * @return the additionalRemarks
	 */
	@Lob
	public String getAdditionalRemarks() {
		return additionalRemarks;
	}
	/**
	 * @param additionalRemarks the additionalRemarks to set
	 */
	@DataType(value="longtext")
	public void setAdditionalRemarks(String additionalRemarks) {
		this.additionalRemarks = additionalRemarks;
	}
	
	/**
	 * @return the availableFromDate
	 */
	public Date getAvailableFromDate() {
		return availableFromDate;
	}

	/**
	 * @param availableFromDate the availableFromDate to set
	 */
	public void setAvailableFromDate(Date availableFromDate) {
		this.availableFromDate = availableFromDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Applicant other = (Applicant) obj;
		if (applicantProfileId!= other.applicantProfileId)
			return false;
		return true;
	}
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getReferencesAndCertificates() {
		return referencesAndCertificates;
	}

	public void setReferencesAndCertificates(DecisionYesNoEnum referencesAndCertificates) {
		this.referencesAndCertificates = referencesAndCertificates;
	}

	public String getReferencesAndCertificatesComments() {
		return referencesAndCertificatesComments;
	}

	public void setReferencesAndCertificatesComments(String referencesAndCertificatesComments) {
		this.referencesAndCertificatesComments = referencesAndCertificatesComments;
	}
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getFurtherOnlineActivities() {
		return furtherOnlineActivities;
	}

	public void setFurtherOnlineActivities(DecisionYesNoEnum furtherOnlineActivities) {
		this.furtherOnlineActivities = furtherOnlineActivities;
	}

	public String getFurtherOnlineActivitiesComments() {
		return furtherOnlineActivitiesComments;
	}

	public void setFurtherOnlineActivitiesComments(String furtherOnlineActivitiesComments) {
		this.furtherOnlineActivitiesComments = furtherOnlineActivitiesComments;
	}
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getPublications() {
		return publications;
	}

	public void setPublications(DecisionYesNoEnum publications) {
		this.publications = publications;
	}

	public String getPublicationsComments() {
		return publicationsComments;
	}

	public void setPublicationsComments(String publicationsComments) {
		this.publicationsComments = publicationsComments;
	}

	public void setPublicationType(PublicationTypeEnum publicationType) {
		this.publicationType = publicationType;
	}
	@Enumerated(EnumType.STRING)
	public PublicationTypeEnum getPublicationType() {
		return publicationType;
	}

	public void setProfilePhoto(String photo) {
		this.profilePhoto = photo;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}
	
	
}
