package net.agef.jobexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.annotations.Cascade;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
public class Address extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5092806686680455974L;

	
	private String address1;
	private String address2;
	private String city;
	private String federalState;
	private String zipCode;
	@IndexedEmbedded
	private Country country;
	private String phoneNumber;
	private String mobileNumber;
	private String faxNumber;
	
	private User address1Owner;
	private User address2Owner;
	
	private ContactPerson addressContactPerson;
	
	private LoginUser addressLoginUser;
	
	@Inject
	public Address(){
		
	}
	
	public Address(User user){
		this.address1Owner = user;
	}
	
	/**
	 * @return the address1
	 */
	@Validate("required")
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the city
	 */
	@Validate("required")
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the federalState
	 */
	public String getFederalState() {
		return federalState;
	}
	/**
	 * @param federalState the federalState to set
	 */
	public void setFederalState(String federalState) {
		this.federalState = federalState;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the country
	 */
	@Validate("required")
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public Country getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}
	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	/**
	 * @return the owner
	 */
	
	@OneToOne(mappedBy = "address1")
	public User getAddress1Owner() {
		return address1Owner;
	}
	/**
	 * @param owner the owner to set
	 */
	@NonVisual
	public void setAddress1Owner(User owner) {
		this.address1Owner = owner;
	}
	
	/**
	 * @return the owner
	 */
	
	@OneToOne(mappedBy = "address2")
	public User getAddress2Owner() {
		return address2Owner;
	}
	/**
	 * @param owner the owner to set
	 */
	@NonVisual
	public void setAddress2Owner(User owner) {
		this.address2Owner = owner;
	}
	/**
	 * @return the addressContactPerson
	 */
	@OneToOne(mappedBy = "contactPersonAddress")
	public ContactPerson getAddressContactPerson() {
		return addressContactPerson;
	}
	/**
	 * @param addressContactPerson the addressContactPerson to set
	 */
	@NonVisual
	public void setAddressContactPerson(ContactPerson addressContactPerson) {
		this.addressContactPerson = addressContactPerson;
	}
	/**
	 * @return the addressLoginUser
	 */
	@OneToOne(mappedBy = "loginUserAddress")
	public LoginUser getAddressLoginUser() {
		return addressLoginUser;
	}
	/**
	 * @param addressLoginUser the addressLoginUser to set
	 */
	@NonVisual
	public void setAddressLoginUser(LoginUser addressLoginUser) {
		this.addressLoginUser = addressLoginUser;
	}
	
}
