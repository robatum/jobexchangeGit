package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
public class User extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2284587022138077470L;
	
	@Deprecated
	private Long apdUserId;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Long cobraSuperId;
//	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
//	private Long inwentUserId;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Long portalUserId;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Long portalId;
	@Deprecated
	private Long agefApplicantNumber;
	@IndexedEmbedded
	private DataProvider dataProvider;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Boolean onlineStatus;
	
	private AlumniRole alumniRole;
	@SuppressWarnings("unused")
	private OrganisationRole organisationRole;
	private AbstractUserRole userRole;
	
	
	private AbstractUserRoleData userRoleData;
	
	
	private AddressEnum addresses;
	private TitleEnum title;
	private String familyName;
	private String givenName;
	private String fathersName;
	private String position;
	private Date dateOfBirth;
	private String emailPrivate;
	private String emailBusiness;
	private String internet;
	private Country nationality;
	private String citizenship1;
	private String citizenship2;
	
	@IndexedEmbedded
	private Address address1;
	@IndexedEmbedded
	private Address address2;
	private Boolean currentAddress;
	
	private LoginUser relatedLoginUser;
	
	private Collection<ContactPerson> contactPersons = new TreeSet<ContactPerson>();
	
	private Applicant applicantProfile;
	private Collection<JobImpl> myJobOffers = new TreeSet<JobImpl>();
	private Collection<JobApplication> myJobApplications = new TreeSet<JobApplication>();
	
	private Collection<ApplicantContact> myApplicantContacts = new TreeSet<ApplicantContact>();
	private Collection<ApplicantContact> myReceivedApplicantContacts = new TreeSet<ApplicantContact>();
	
	private Collection<ApplicantFieldOfActivity> fieldsOfActivity = new TreeSet<ApplicantFieldOfActivity>();
	
	@Inject
	public User(){
		this.address1 = new Address();
		this.address2 = new Address();
		this.currentAddress = true;		
		if (this.userRole==null){
			this.alumniRole = new AlumniRole(this);
			this.userRole = alumniRole;
		}
		this.onlineStatus = true;
	}
	
	public User(AbstractUserRole userRole){
		this.address1 = new Address();
		this.address2 = new Address();
		this.currentAddress = true;
		this.userRole = userRole;
		this.userRole.setOwner(this);
		this.onlineStatus = true;
	}
	
	public User(AbstractUserRole userRole, DataProvider dataProvider){
		this.address1 = new Address();
		this.address2 = new Address();
		this.currentAddress = true;
		this.dataProvider = dataProvider;
		this.userRole = userRole;
		this.userRole.setOwner(this);
		this.onlineStatus = true;
	}
	
	public User(AbstractUserRole userRole, DataProvider dataProvider, LoginUser loginUser){
		this.address1 = new Address();
		this.address2 = new Address();
		this.currentAddress = true;
		this.dataProvider = dataProvider;
		this.userRole = userRole;
		this.userRole.setOwner(this);
		this.relatedLoginUser = loginUser;
		this.onlineStatus = true;
	}

	/**
	 * @return the guId
	 */
	@Deprecated
	public Long getApdUserId() {
		return apdUserId;
	}

	/**
	 * @param guId the guId to set
	 */
	@NonVisual
	@Deprecated
	public void setApdUserId(Long userId) {
		this.apdUserId = userId;
	}
	
	/**
	 * @return the cobraSuperId
	 */
	public Long getCobraSuperId() {
		return cobraSuperId;
	}

	/**
	 * @param cobraSuperId the cobraSuperId to set
	 */
	@NonVisual
	public void setCobraSuperId(Long cobraSuperId) {
		this.cobraSuperId = cobraSuperId;
	}
	
//	@NonVisual
//	public void setInwentUserId(Long inwentUserId) {
//		this.inwentUserId = inwentUserId;
//	}
//
//	public Long getInwentUserId() {
//		return inwentUserId;
//	}

	@NonVisual
	public void setPortalUserId(Long portalUserId) {
		this.portalUserId = portalUserId;
	}

	public Long getPortalUserId() {
		return portalUserId;
	}

	/**
	 * @return the portalId
	 */
	public Long getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	@NonVisual
	public void setPortalId(Long portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the agefApplicantNumber
	 */
	@Deprecated
	public Long getAgefApplicantNumber() {
		return agefApplicantNumber;
	}

	/**
	 * @param agefApplicantNumber the agefApplicantNumber to set
	 */
	@NonVisual
	@Deprecated
	public void setAgefApplicantNumber(Long agefApplicantNumber) {
		this.agefApplicantNumber = agefApplicantNumber;
	}

	/**
	 * @return the dataProvider
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public DataProvider getDataProvider() {
		return dataProvider;
	}

	/**
	 * @param dataProvider the dataProvider to set
	 */
	@NonVisual
	public void setDataProvider(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
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

//	/**
//	 * @return the alumniRoleData
//	 */
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="alumniroledata_fk")
//	public AlumniRoleData getAlumniRoleData() {
//		return alumniRoleData;
//	}
//
//	/**
//	 * @param alumniRoleData the alumniRoleData to set
//	 */
//	public void setAlumniRoleData(AlumniRoleData alumniRoleData) {
//		this.alumniRoleData = alumniRoleData;
//	}
//
//	/**
//	 * @return the organisationRoleData
//	 */
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="orgroledata_fk")
//	public OrganisationRoleData getOrganisationRoleData() {
//		return organisationRoleData;
//	}
//
//	/**
//	 * @param organisationRoleData the organisationRoleData to set
//	 */
//	public void setOrganisationRoleData(OrganisationRoleData organisationRoleData) {
//		this.organisationRoleData = organisationRoleData;
//	}
//
	/**
	 * @return the userRole
	 */
	@Type(type="net.agef.jobexchange.integration.UserRoleUserType")
	public AbstractUserRole getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	@Type(type="net.agef.jobexchange.integration.UserRoleUserType")
	public void setUserRole(AbstractUserRole userRole) {
		this.userRole = userRole;
	}
	
	
	/**
	 * @return the userRoleData
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userroledata_fk")
	public AbstractUserRoleData getUserRoleData() {
		return userRoleData;
		//return this.getUserRole().getRoleData();
	}

	/**
	 * @param userRoleData the userRoleData to set
	 */
	public void setUserRoleData(AbstractUserRoleData userRoleData) {
		this.userRoleData = userRoleData;
	}
	
	/**
	 * @return the myJobApplications
	 */
	@OneToMany(mappedBy="jobApplicationOwner")
	public Collection<JobApplication> getMyJobApplications() {
		return myJobApplications;
	}

	/**
	 * @param myJobApplications the myJobApplications to set
	 */
	public void setMyJobApplications(Collection<JobApplication> myJobApplications) {
		this.myJobApplications = myJobApplications;
	}
	

	/**
	 * @return the myApplicantContacts
	 */
	@OneToMany(mappedBy="applicantContactOwner")
	public Collection<ApplicantContact> getMyApplicantContacts() {
		return myApplicantContacts;
	}

	/**
	 * @param myApplicantContacts the myApplicantContacts to set
	 */
	public void setMyApplicantContacts(
			Collection<ApplicantContact> myApplicantContacts) {
		this.myApplicantContacts = myApplicantContacts;
	}

	/**
	 * @return the myReceivedApplicantContacts
	 */
	@OneToMany(mappedBy="relatedApplicant")
	public Collection<ApplicantContact> getMyReceivedApplicantContacts() {
		return myReceivedApplicantContacts;
	}

	/**
	 * @param myReceivedApplicantContacts the myReceivedApplicantContacts to set
	 */
	public void setMyReceivedApplicantContacts(
			Collection<ApplicantContact> myReceivedApplicantContacts) {
		this.myReceivedApplicantContacts = myReceivedApplicantContacts;
	}

	/**
	 * @return the fieldsOfActivity
	 */
	@OneToMany(mappedBy="relatedUser")
	public Collection<ApplicantFieldOfActivity> getFieldsOfActivity() {
		return fieldsOfActivity;
	}

	/**
	 * @param fieldsOfActivity the fieldsOfActivity to set
	 */
	public void setFieldsOfActivity(
			Collection<ApplicantFieldOfActivity> fieldsOfActivity) {
		this.fieldsOfActivity = fieldsOfActivity;
	}

	/**
	 * @return the address
	 */
    @Enumerated(EnumType.STRING)
	public AddressEnum getAddresses() {
		return addresses;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddresses(AddressEnum addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return the title
	 */
	@Enumerated(EnumType.STRING)
	public TitleEnum getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(TitleEnum title) {
		this.title = title;
	}

	/**
	 * @return the familyName
	 */
	@Validate("required")
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * @return the givenName
	 */
	@Validate("required")
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the fathersName
	 */
	public String getFathersName() {
		return fathersName;
	}

	/**
	 * @param fathersName the fathersName to set
	 */
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the dateOfBirth
	 */
	@Validate("required")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the emailPrivate
	 */
	@Validate("required,email")
	//@Validate("required,regexp=([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)")
	public String getEmailPrivate() {
		return emailPrivate;
	}

	/**
	 * @param emailPrivate the emailPrivate to set
	 */
	public void setEmailPrivate(String emailPrivate) {
		this.emailPrivate = emailPrivate;
	}

	/**
	 * @return the emailBusiness
	 */
	//@Validate("required,email")
	//@Validate("required,regexp=([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)")
	public String getEmailBusiness() {
		return emailBusiness;
	}

	/**
	 * @param emailBusiness the emailBusiness to set
	 */
	public void setEmailBusiness(String emailBusiness) {
		this.emailBusiness = emailBusiness;
	}

	/**
	 * @return the internet
	 */
	@Validate("regexp=(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(https?://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?")
	public String getInternet() {
		return internet;
	}

	/**
	 * @param internet the internet to set
	 */
	public void setInternet(String internet) {
		this.internet = internet;
	}

	/**
	 * @return the nationality
	 */
	@Validate("required")
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	//@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public Country getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}
	
	/**
	 * @return the citizenship1
	 */
	public String getCitizenship1() {
		return citizenship1;
	}
	/**
	 * @param citizenship1 the citizenship1 to set
	 */
	public void setCitizenship1(String citizenship1) {
		this.citizenship1 = citizenship1;
	}
	/**
	 * @return the citizenship2
	 */
	public String getCitizenship2() {
		return citizenship2;
	}
	/**
	 * @param citizenship2 the citizenship2 to set
	 */
	public void setCitizenship2(String citizenship2) {
		this.citizenship2 = citizenship2;
	}

	/**
	 * @return the address1
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address1_fk")
	public Address getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(Address address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address2_fk")
	public Address getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(Address address2) {
		this.address2 = address2;
	}

	/**
	 * @return the currentAddress
	 */
	public Boolean getCurrentAddress() {
		return currentAddress;
	}

	/**
	 * @param currentAddress the currentAddress to set
	 */
	public void setCurrentAddress(Boolean currentAddress) {
		this.currentAddress = currentAddress;
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return the applicantProfile
	 */
	@OneToOne(mappedBy = "applicantProfileOwner", cascade = CascadeType.REMOVE)
	public Applicant getApplicantProfile() {
		return applicantProfile;
	}

	/**
	 * @param applicantProfile the applicantProfile to set
	 */
	public void setApplicantProfile(Applicant applicantProfile) {
		this.applicantProfile = applicantProfile;
	}

	/**
	 * @return the relatedLoginUser
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public LoginUser getRelatedLoginUser() {
		return relatedLoginUser;
	}

	/**
	 * @param relatedLoginUser the relatedLoginUser to set
	 */
	public void setRelatedLoginUser(LoginUser relatedLoginUser) {
		this.relatedLoginUser = relatedLoginUser;
	}

	/**
	 * @return the contactPersons
	 */
	@OneToMany(mappedBy="relatedOrganisation", cascade={CascadeType.REMOVE}) 
	public Collection<ContactPerson> getContactPersons() {
		return contactPersons;
	}

	/**
	 * @param contactPersons the contactPersons to set
	 */
	public void setContactPersons(Collection<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
	}

	/**
	 * @return the myJobOffers
	 */
	@OneToMany(mappedBy="jobOfferOwner", cascade={CascadeType.REMOVE}) 
	public Collection<JobImpl> getMyJobOffers() {
		return myJobOffers;
	}

	/**
	 * @param myJobOffers the myJobOffers to set
	 */
	public void setMyJobOffers(Collection<JobImpl> myJobOffers) {
		this.myJobOffers = myJobOffers;
	}
	
	
	
	
	/**
	 * @return the contactAdress
	 */
	@Transient
	public Address getAlternativeContactAddress() {
		if (!this.currentAddress){
			return this.getAddress1();
		} else return this.getAddress2();
	}
	
	/**
	 * @return the contactAdress
	 */
	@Transient
	public Address getCurrentContactAddress() {
		if (this.currentAddress){
			return this.getAddress1();
		} else return this.getAddress2();
	}
	
	/**
	 * @param jobOwner the jobOwner to set
	 */
	@Transient
	@NonVisual
	public void setCurrentContactAddress(Address contactAddress) {
		if (this.currentAddress){
			this.setAddress1(contactAddress);
		} else this.setAddress2(contactAddress);
	}
	
	@Transient
	public void addNewJobOffer(JobImpl jobOffer){
		this.myJobOffers.add(jobOffer);
	}
	@Transient
	public void addNewJobOffer(JobImpl jobOffer, DataProvider dataProvider){
		jobOffer.setDataProvider(dataProvider);
		this.myJobOffers.add(jobOffer);
	}
	@Transient
	public void updateExistingJobOffer(long jobOfferId){
		//this.myJobOffers.
	}
	@Transient
	public void deleteJobOffer(JobImpl jobOffer){
		this.myJobOffers.remove(jobOffer);
	}
	
	@Transient
	public String getFullUserName(){
		String userName = "";
		userName = getGivenName() + " "+getFamilyName();
		return userName;
	}
	

}
