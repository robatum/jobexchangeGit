package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

@Entity
public class ContactPerson extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3853524265512384644L;
	
	
	private AddressEnum Addresses;
	private TitleEnum title;
	private String familyName;
	private String givenName;
	private String fathersName;
	private String position;
	private String emailBusiness;
	private String internet;
	private Address contactPersonAddress;	
	
	private Collection<JobImpl> relatedJob = new TreeSet<JobImpl>();
	
	private User relatedOrganisation;
	
	/**
	 * @return the addresses
	 */
	@Enumerated(EnumType.STRING)
	public AddressEnum getAddresses() {
		return Addresses;
	}
	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(AddressEnum addresses) {
		Addresses = addresses;
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
	 * @return the emailBusiness
	 */
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
	 * @return the contactPersonAddress
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="addressContactPerson_fk")
	public Address getContactPersonAddress() {
		return contactPersonAddress;
	}

	/**
	 * @param contactPersonAddress the contactPersonAddress to set
	 */
	public void setContactPersonAddress(Address contactPersonAddress) {
		this.contactPersonAddress = contactPersonAddress;
	}
	
	/**
	 * @return the relatedJob
	 */
	@OneToMany(mappedBy="contactPerson")
	public Collection<JobImpl> getRelatedJob() {
		return relatedJob;
	}
	/**
	 * @param relatedJob the relatedJob to set
	 */
	public void setRelatedJob(Collection<JobImpl> relatedJob) {
		this.relatedJob = relatedJob;
	}
	/**
	 * @return the relatedOrganisation
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public User getRelatedOrganisation() {
		return relatedOrganisation;
	}
	/**
	 * @param relatedOrganisation the relatedOrganisation to set
	 */
	public void setRelatedOrganisation(User relatedOrganisation) {
		this.relatedOrganisation = relatedOrganisation;
	}

	
}
