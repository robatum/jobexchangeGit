/**
 * 
 */
package net.agef.jobexchange.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import net.agef.jobexchange.services.lucene.JobConcatClassBridge;

import org.apache.tapestry5.beaneditor.DataType;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
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
@Indexed
@ClassBridge(name="jobconcatsearchfield",
        index=Index.TOKENIZED,
        store=Store.YES,
        impl = JobConcatClassBridge.class,
        params = @Parameter( name="sepChar", value=" " ) )
@Table(name="jobs")
//@Analyzer(impl = net.agef.jobexchange.services.lucene.EnglishAnalyzer.class)
public class JobImpl extends AbstractEntity {

	/**
	 * Persistence serial UID
	 */
	private static final long serialVersionUID = 4474971020654332528L;
	
	private Collection<AccessHistoryJobs> relatedAccessHistoryJob = new TreeSet<AccessHistoryJobs>();
	
	
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private Long jobOfferId;
	
	@IndexedEmbedded
	private User jobOfferOwner;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Long cobraJobId;
	private Long clickCounter;
	private ContactPerson contactPerson;
	@IndexedEmbedded
	private DataProvider dataProvider;
	private LoginUser getjobsLoginUser;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private Boolean onlineStatus;
		
	private OccupationalField occupationalField;
//	private OccupationalField occupationalSubField;
	private Integer numberOfJobs;
	@Boost(5f)
	@Field(index=Index.TOKENIZED, store=Store.YES)
	private String jobDescription;
	
	@Boost(2.5f)
	@Field(index=Index.TOKENIZED, store=Store.YES)
	private String taskDescription;
	private String locationOfEmployment;
	@IndexedEmbedded
	private Country countryOfEmployment;
	private DegreeEnum minimumRequirementsForEducation;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String furtherCommentsRegardingEducation;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String desiredProfession;
	private String alternativeProfession;
	private ExperienceDurationEnum workExperience;
	
	private LanguageSkillsEnum languageSkillsGerman;
	private LanguageSkillsEnum languageSkillsEnglish;
	private List<LanguageSkill> languageSkillsOther = new ArrayList<LanguageSkill>();
	private DecisionYesNoEnum computerSkills;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String computerSkillsComments;
	private DecisionYesNoEnum drivingLicence;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String specialKnowledge;
	private String furtherRequirements;
	private Date possibleCommencementDate;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private ContractDurationEnum durationOfContract;
	private Integer weeklyHoursOfWork;
	private String salary;
	private Currency currency;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String miscellaneousServices;
	
	private PublicationTypeEnum preferredPublication;
	private String commentsRegardingApplicationProcedure;
	private String applicationFormLink;
	private Date applicationExpireDate;
	private String attachmentLocation;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String furtherComments;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	@DateBridge(resolution = Resolution.HOUR)
	private Date jobOfferExpireDate;
	
	private Collection<JobApplication> jobOfferApplications = new TreeSet<JobApplication>();
	
	
	
	/**
	 * @return the getRelatedJobs
	 */
	@OneToMany(mappedBy="accessedJobProfile")
	public Collection<AccessHistoryJobs> getRelatedAccessHistoryJob() {
		return relatedAccessHistoryJob;
	}

	/**
	 * @param getRelatedJobs the getRelatedJobs to set
	 */
	public void setRelatedAccessHistoryJob(Collection<AccessHistoryJobs> relatedAccessHistoryJob) {
		this.relatedAccessHistoryJob = relatedAccessHistoryJob;
	}
	
	
	/**
	 * @return the jobOfferApplications
	 */
	@OneToMany(mappedBy="relatedJob")
	public Collection<JobApplication> getJobOfferApplications() {
		return jobOfferApplications;
	}

	/**
	 * @param jobOfferApplications the jobOfferApplications to set
	 */
	public void setJobOfferApplications(Collection<JobApplication> jobOfferApplications) {
		this.jobOfferApplications = jobOfferApplications;
	}

	@Inject // tells tapestry which constructor to use for auto-instantiating
	public JobImpl(){
		if (this.jobOfferOwner == null){
			this.jobOfferOwner = new User();
			this.dataProvider = this.jobOfferOwner.getDataProvider();
		}
		this.onlineStatus = true;
	}
	
	public JobImpl(User user){
		this.jobOfferOwner = user;
		this.dataProvider = this.jobOfferOwner.getDataProvider();
		this.onlineStatus = true;
	}
	
	public JobImpl(User user, DataProvider dataProvider){
		this.jobOfferOwner = user;
		this.dataProvider = dataProvider;
		this.onlineStatus = true;
	}
	
	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getJobOfferId()
	 */

	@Formula("id + 23")
	public Long getJobOfferId() {
		return jobOfferId;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setJobOfferId(Long)
	 */
	//@NonVisual
	public void setJobOfferId(Long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}
	
	/**
	 * @return the clickCounter
	 */
	public Long getClickCounter() {
		return clickCounter;
	}

	/**
	 * @param clickCounter the clickCounter to set
	 */
	@NonVisual
	public void setClickCounter(Long clickCounter) {
		this.clickCounter = clickCounter;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getJobOfferOwner()
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=false)
	public User getJobOfferOwner() {
		return jobOfferOwner;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setJobOfferOwner(net.agef.jobexchange.domain.User)
	 */
	public void setJobOfferOwner(User jobOfferOwner) {
		this.jobOfferOwner = jobOfferOwner;
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
	@NonVisual
	public void setCobraJobId(Long cobraJobId) {
		this.cobraJobId = cobraJobId;
	}

	/**
	 * @return the contactPerson
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name="contactPerson_fk",nullable=true)
	public ContactPerson getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(ContactPerson contactPerson) {
		this.contactPerson = contactPerson;
	}

//	/* (non-Javadoc)
//	 * @see net.agef.jobexchange.domain.s#getJobApplications()
//	 */
//	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
//	@JoinColumn(nullable=true)
//	public User getJobApplications() {
//		return jobApplications;
//	}
//
//	/* (non-Javadoc)
//	 * @see net.agef.jobexchange.domain.s#setJobApplications(net.agef.jobexchange.domain.User)
//	 */
//	public void setJobApplications(User jobApplications) {
//		this.jobApplications = jobApplications;
//	}

	

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getDataProvider()
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=false)
	public DataProvider getDataProvider() {
		return dataProvider;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setDataProvider(net.agef.jobexchange.domain.DataProvider)
	 */
	@NonVisual
	public void setDataProvider(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	/**
	 * @return the getjobsLoginUser
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public LoginUser getGetjobsLoginUser() {
		return getjobsLoginUser;
	}

	/**
	 * @param getjobsLoginUser the getjobsLoginUser to set
	 */
	public void setGetjobsLoginUser(LoginUser getjobsLoginUser) {
		this.getjobsLoginUser = getjobsLoginUser;
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
	@NonVisual
	public void setOnlineStatus(Boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	/**
	 * @return the occupationalField
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=true)
	@Validate("required")
	public OccupationalField getOccupationalField() {
		return occupationalField;
	}

	/**
	 * @param occupationalField the occupationalField to set
	 */
	public void setOccupationalField(OccupationalField occupationalField) {
		this.occupationalField = occupationalField;
	}


	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getOrganisationName()
	 */
	public String organisationName;
	
	@Transient
	public String getOrganisationName() {
		return this.jobOfferOwner.getUserRoleData().getOrganisationName();
	}

	
	public void setOrganisationName(String name) {	
	}
	
	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getOrganisationDescription()
	 */
	@Transient
	@NonVisual
	public String getOrganisationDescription() {
		return this.jobOfferOwner.getUserRoleData().getOrganisationDescription();
	}
	
	@NonVisual
	public void setOrganisationDescription() {	
	}
	
	@Transient
	@NonVisual
	public IndustrySector getOrganisationIndustrySector() {
		return this.jobOfferOwner.getUserRoleData().getIndustrySector();
	}
	
	@Transient
	@NonVisual
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	public String getOrganisationIndustrySectorId() {
			if(this.jobOfferOwner.getUserRoleData().getIndustrySector()!=null){
				return this.jobOfferOwner.getUserRoleData().getIndustrySector().getSectorId().toString();
			} return null;
	}
	
	
	@Transient
	@NonVisual
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	public Integer getCountryOfEmploymentId() {
			if(this.countryOfEmployment !=null){
				return this.countryOfEmployment.getIsoNumber();
			} return null;
	}
	
	@NonVisual
	public void setOrganisationIndustrySector() {
	}


	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getNumberOfJobs()
	 */
	@Validate("required")
	public Integer getNumberOfJobs() {
		return numberOfJobs;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setNumberOfJobs(java.lang.Integer)
	 */
	public void setNumberOfJobs(Integer numberOfJobs) {
		this.numberOfJobs = numberOfJobs;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getJobDescription()
	 */
	@Validate("required")
	@Lob
	public String getJobDescription() {
		return jobDescription;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setJobDescription(java.lang.String)
	 */
	//@DataType(value="longtext")
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getTaskDescription()
	 */
	@Validate("required")
	@Lob
	public String getTaskDescription() {
		return taskDescription;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setTaskDescription(java.lang.String)
	 */
	@DataType(value="longtext")
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getLocationOfEmployment()
	 */
	@Validate("required")
	public String getLocationOfEmployment() {
		return locationOfEmployment;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setLocationOfEmployment(java.lang.String)
	 */
	public void setLocationOfEmployment(String locationOfEmployment) {
		this.locationOfEmployment = locationOfEmployment;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getCountryOfEmployment()
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	//@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public Country getCountryOfEmployment() {
		return countryOfEmployment;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setCountryOfEmployment(net.agef.jobexchange.domain.Country)
	 */
	public void setCountryOfEmployment(Country countryOfEmployment) {
		this.countryOfEmployment = countryOfEmployment;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getMinimumRequirementsForEducation()
	 */
	@Enumerated(EnumType.STRING)
	@Validate("required")
	public DegreeEnum getMinimumRequirementsForEducation() {
		return minimumRequirementsForEducation;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setMinimumRequirementsForEducation(java.lang.String)
	 */
	public void setMinimumRequirementsForEducation(DegreeEnum minimumRequirementsForEducation) {
		this.minimumRequirementsForEducation = minimumRequirementsForEducation;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getFurtherCommentsRegardingEducation()
	 */
	@Lob
	public String getFurtherCommentsRegardingEducation() {
		return furtherCommentsRegardingEducation;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setFurtherCommentsRegardingEducation(java.lang.String)
	 */
	@DataType(value="longtext")
	public void setFurtherCommentsRegardingEducation(
			String furtherCommentsRegardingEducation) {
		this.furtherCommentsRegardingEducation = furtherCommentsRegardingEducation;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getDesiredProfession()
	 */
	@Validate("required")
	public String getDesiredProfession() {
		return desiredProfession;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setDesiredProfession(java.lang.String)
	 */
	public void setDesiredProfession(String desiredProfession) {
		this.desiredProfession = desiredProfession;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getAlternativeProfession()
	 */
	public String getAlternativeProfession() {
		return alternativeProfession;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setAlternativeProfession(java.lang.String)
	 */
	public void setAlternativeProfession(String alternativeProfession) {
		this.alternativeProfession = alternativeProfession;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getWorkExperience()
	 */
	@Enumerated(EnumType.STRING)
	public ExperienceDurationEnum getWorkExperience() {
		return workExperience;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setWorkExperience(java.lang.Integer)
	 */
	public void setWorkExperience(ExperienceDurationEnum workExperience) {
		this.workExperience = workExperience;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getLanguageSkillsGerman()
	 */
	@Enumerated(EnumType.STRING)
	public LanguageSkillsEnum getLanguageSkillsGerman() {
		return languageSkillsGerman;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setLanguageSkillsGerman(net.agef.jobexchange.domain.LanguageSkillsEnum)
	 */
	public void setLanguageSkillsGerman(LanguageSkillsEnum languageSkillsGerman) {
		this.languageSkillsGerman = languageSkillsGerman;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getLanguageSkillsEnglish()
	 */
	@Enumerated(EnumType.STRING)
	public LanguageSkillsEnum getLanguageSkillsEnglish() {
		return languageSkillsEnglish;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setLanguageSkillsEnglish(net.agef.jobexchange.domain.LanguageSkillsEnum)
	 */
	public void setLanguageSkillsEnglish(LanguageSkillsEnum languageSkillsEnglish) {
		this.languageSkillsEnglish = languageSkillsEnglish;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getLanguageSkillsOther()
	 */
	@OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@JoinColumn(name="jobOfferLanguageSkillsOther_fk")
	@IndexColumn(name="INDEX_COL")
	public List<LanguageSkill> getLanguageSkillsOther() {
		return languageSkillsOther;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setLanguageSkillsOther(java.util.Collection)
	 */
	public void setLanguageSkillsOther(List<LanguageSkill> languageSkillsOther) {
		this.languageSkillsOther = languageSkillsOther;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getComputerSkills()
	 */
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getComputerSkills() {
		return computerSkills;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setComputerSkills(net.agef.jobexchange.domain.DecisionYesNo)
	 */
	public void setComputerSkills(DecisionYesNoEnum computerSkills) {
		this.computerSkills = computerSkills;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getComputerSkillsComments()
	 */
	@Lob
	public String getComputerSkillsComments() {
		return computerSkillsComments;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setComputerSkillsComments(java.lang.String)
	 */
	@DataType(value="longtext")
	public void setComputerSkillsComments(String computerSkillsComments) {
		this.computerSkillsComments = computerSkillsComments;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getDrivingLicence()
	 */
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getDrivingLicence() {
		return drivingLicence;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setDrivingLicence(net.agef.jobexchange.domain.DecisionYesNo)
	 */
	public void setDrivingLicence(DecisionYesNoEnum drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getSpecialKnowledge()
	 */
	@Lob
	public String getSpecialKnowledge() {
		return specialKnowledge;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setSpecialKnowledge(java.lang.String)
	 */
	@DataType(value="longtext")
	public void setSpecialKnowledge(String specialKnowledge) {
		this.specialKnowledge = specialKnowledge;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getFurtherRequirements()
	 */
	@Lob
	public String getFurtherRequirements() {
		return furtherRequirements;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setFurtherRequirements(java.lang.String)
	 */
	@DataType(value="longtext")
	public void setFurtherRequirements(String furtherRequirements) {
		this.furtherRequirements = furtherRequirements;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getPossibleCommencementDate()
	 */
	public Date getPossibleCommencementDate() {
		return possibleCommencementDate;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setPossibleCommencementDate(java.util.Date)
	 */
	public void setPossibleCommencementDate(Date possibleCommencementDate) {
		this.possibleCommencementDate = possibleCommencementDate;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getDurationOfContract()
	 */
	@Validate("required")
	@Enumerated(EnumType.STRING)
	public ContractDurationEnum getDurationOfContract() {
		return durationOfContract;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setDurationOfContract(net.agef.jobexchange.domain.ContractDurationEnum)
	 */
	public void setDurationOfContract(ContractDurationEnum durationOfContract) {
		this.durationOfContract = durationOfContract;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getWeeklyHoursOfWork()
	 */
	@Validate("regexp=^0*[1-9][0-9]*$")
	public Integer getWeeklyHoursOfWork() {
		return weeklyHoursOfWork;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setWeeklyHoursOfWork(java.lang.Integer)
	 */
	public void setWeeklyHoursOfWork(Integer weeklyHoursOfWork) {
		this.weeklyHoursOfWork = weeklyHoursOfWork;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getSalary()
	 */
	public String getSalary() {
		return salary;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setSalary(java.lang.Integer)
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getCurrency()
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public Currency getCurrency() {
		return currency;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setCurrency(net.agef.jobexchange.domain.Currency)
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getMiscellaneousServices()
	 */
	@Lob
	public String getMiscellaneousServices() {
		return miscellaneousServices;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setMiscellaneousServices(java.lang.String)
	 */
	@DataType(value="longtext")
	public void setMiscellaneousServices(String miscellaneousServices) {
		this.miscellaneousServices = miscellaneousServices;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getPreferredPublication()
	 */
	@Validate("required")
	@Enumerated(EnumType.STRING)
	public PublicationTypeEnum getPreferredPublication() {
		return preferredPublication;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setPreferredPublication(net.agef.jobexchange.domain.PublicationTypeEnum)
	 */
	public void setPreferredPublication(PublicationTypeEnum preferredPublication) {
		this.preferredPublication = preferredPublication;
	}
	

	/**
	 * @return the applicationExpireDate
	 */
	@Validate("required")
	public Date getApplicationExpireDate() {
		return applicationExpireDate;
	}

	/**
	 * @param applicationExpireDate the applicationExpireDate to set
	 */
	public void setApplicationExpireDate(Date applicationExpireDate) {
		this.applicationExpireDate = applicationExpireDate;
	}
	
	/**
	 * @return the applicationFormLink
	 *
	 */
	@Validate("regexp=(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(https?://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?")
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
	 * @return the commentsRegardingApplicationProcedure
	 */
	@Lob
	@Validate("required")
	public String getCommentsRegardingApplicationProcedure() {
		return commentsRegardingApplicationProcedure;
	}

	/**
	 * @param commentsRegardingApplicationProcedure the commentsRegardingApplicationProcedure to set
	 */
	@DataType(value="longtext")
	public void setCommentsRegardingApplicationProcedure(
			String commentsRegardingApplicationProcedure) {
		this.commentsRegardingApplicationProcedure = commentsRegardingApplicationProcedure;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getAttachmentLocation()
	 */
	public String getAttachmentLocation() {
		return attachmentLocation;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setAttachmentLocation(java.lang.String)
	 */
	public void setAttachmentLocation(String attachmentLocation) {
		this.attachmentLocation = attachmentLocation;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getFurtherComments()
	 */
	@Lob
	public String getFurtherComments() {
		return furtherComments;
	}

	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setFurtherComments(java.lang.String)
	 */
	@DataType(value="longtext")
	public void setFurtherComments(String furtherComments) {
		this.furtherComments = furtherComments;
	}
	
	
	/**
	 * @return the jobOfferExpireDate
	 */
	@Validate("required")
	public Date getJobOfferExpireDate() {
		return jobOfferExpireDate;
	}

	/**
	 * @param jobOfferExpireDate the jobOfferExpireDate to set
	 */
	public void setJobOfferExpireDate(Date jobOfferExpireDate) {
		this.jobOfferExpireDate = jobOfferExpireDate;
	}


	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	
	
	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#getCurrentContactAddress()
	 */
	@Transient
	@NonVisual
	public Address getCurrentContactAddress() {
		if (this.jobOfferOwner == null){
			this.jobOfferOwner = new User();
		}
		if (this.jobOfferOwner.getCurrentAddress()){
			return this.jobOfferOwner.getAddress1();
		} else return this.jobOfferOwner.getAddress2();
	}
	
	/* (non-Javadoc)
	 * @see net.agef.jobexchange.domain.s#setCurrentContactAddress(net.agef.jobexchange.domain.Address)
	 */
	public void setCurrentContactAddress(Address contactAddress) {
		if (this.jobOfferOwner == null){
			this.jobOfferOwner = new User();
		}
		if (this.jobOfferOwner.getCurrentAddress()){
			this.jobOfferOwner.setAddress1(contactAddress);
		} else this.jobOfferOwner.setAddress2(contactAddress);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (jobOfferId ^ (jobOfferId >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobImpl other = (JobImpl) obj;
		if (jobOfferId != other.jobOfferId)
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	@Override
	public int compareTo(Object obj) {
		JobImpl other = (JobImpl) obj;
		return (this.id < other.getId() ) ? -1: (this.id >other.getId()) ? 1:0;
	}

}
