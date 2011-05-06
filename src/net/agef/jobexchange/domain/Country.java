package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name="static_countries")
public class Country extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2121654735208163311L;

	private Collection<JobImpl> getRelatedJobs = new TreeSet<JobImpl>();
	private Collection<Education> getRelatedEducation = new TreeSet<Education>();
	private Collection<Applicant> relatedPreferredCountries = new TreeSet<Applicant>();
	private Collection<Address> getRelatedAddresses = new TreeSet<Address>();
	private Collection<User> getRelatedUserNationalities = new TreeSet<User>();
	private Collection<SearchHistoryJobs> relatedSearchHistoryJobs = new TreeSet<SearchHistoryJobs>();
	private Collection<SearchHistoryApplicant> relatedSearchHistoryApplicant = new TreeSet<SearchHistoryApplicant>();
	
	
	private String isoNameLong;
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private Integer isoNumber;
	private Integer parentTerritoryIsoNumber;
	@IndexedEmbedded
	private Territory parentTerritory;
	private String officialLocalName;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String officialEnglishName;
	private String shortLocalName;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private String shortEnglishName;
	private String capital;
	private String topLevelDomain;
	private String currencyIsoNameShort;
	private Integer currencyIsoNumber;
	private Integer phonePrefix;
	private Boolean euMember;
	private Boolean unoMember;
	
	
	@Inject
	public Country(){
		
	}	
	
	public Country(String isoNameLong, Integer isoNumber, String officialEnglishName){
		this.isoNameLong = isoNameLong;
		this.isoNumber = isoNumber;	
		this.officialEnglishName = officialEnglishName;
	}
	
	public Country(Territory parentTerritory, String isoNameLong, Integer isoNumber, String officialEnglishName){
		this.parentTerritory = parentTerritory;
		this.isoNameLong = isoNameLong;
		this.isoNumber = isoNumber;	
		this.officialEnglishName = officialEnglishName;
	}
	
	/**
	 * @return the getRelatedJobs
	 */
	@OneToMany(mappedBy="countryOfEmployment")
	public Collection<JobImpl> getGetRelatedJobs() {
		return getRelatedJobs;
	}

	/**
	 * @param getRelatedJobs the getRelatedJobs to set
	 */
	public void setGetRelatedJobs(Collection<JobImpl> getRelatedJobs) {
		this.getRelatedJobs = getRelatedJobs;
	}

	
	
	/**
	 * @return the getRelatedJobs
	 */
	@OneToMany(mappedBy="queryCountry")
	public Collection<SearchHistoryJobs> getRelatedSearchHistoryJobs() {
		return relatedSearchHistoryJobs;
	}

	/**
	 * @param getRelatedJobs the getRelatedJobs to set
	 */
	public void setRelatedSearchHistoryJobs(Collection<SearchHistoryJobs> relatedSearchHistoryJobs) {
		this.relatedSearchHistoryJobs = relatedSearchHistoryJobs;
	}
	
	
	/**
	 * @return the getRelatedApplicant
	 */
	@OneToMany(mappedBy="queryCountry")
	public Collection<SearchHistoryApplicant> getRelatedSearchHistoryApplicant() {
		return relatedSearchHistoryApplicant;
	}

	/**
	 * @param getRelatedApplicant the getRelatedApplicant to set
	 */
	public void setRelatedSearchHistoryApplicant(Collection<SearchHistoryApplicant> relatedSearchHistoryApplicant) {
		this.relatedSearchHistoryApplicant = relatedSearchHistoryApplicant;
	}

	
	
	/**
	 * @return the getRelatedEducation
	 */
	@OneToMany(mappedBy="country")
	public Collection<Education> getGetRelatedEducation() {
		return getRelatedEducation;
	}

	/**
	 * @param getRelatedEducation the getRelatedEducation to set
	 */
	public void setGetRelatedEducation(Collection<Education> getRelatedEducation) {
		this.getRelatedEducation = getRelatedEducation;
	}

	/**
	 * @return the relatedPreferredCountries
	 */
	@OneToMany(mappedBy="preferredLocation")
	public Collection<Applicant> getRelatedPreferredCountries() {
		return relatedPreferredCountries;
	}

	/**
	 * @param relatedPreferredCountries the relatedPreferredCountries to set
	 */
	public void setRelatedPreferredCountries(
			Collection<Applicant> relatedPreferredCountries) {
		this.relatedPreferredCountries = relatedPreferredCountries;
	}

	/**
	 * @return the getRelatedAddresses
	 */
	@OneToMany(mappedBy="country")
	public Collection<Address> getGetRelatedAddresses() {
		return getRelatedAddresses;
	}

	/**
	 * @param getRelatedAddresses the getRelatedAddresses to set
	 */
	public void setGetRelatedAddresses(Collection<Address> getRelatedAddresses) {
		this.getRelatedAddresses = getRelatedAddresses;
	}

	/**
	 * @return the getRelatedUserNationalities
	 */
	@OneToMany(mappedBy="nationality")
	public Collection<User> getGetRelatedUserNationalities() {
		return getRelatedUserNationalities;
	}

	/**
	 * @param getRelatedUserNationalities the getRelatedUserNationalities to set
	 */
	public void setGetRelatedUserNationalities(
			Collection<User> getRelatedUserNationalities) {
		this.getRelatedUserNationalities = getRelatedUserNationalities;
	}

	/**
	 * @return the isoNameLong
	 */
	@Column(name = "cn_iso_3")
	public String getIsoNameLong() {
		return isoNameLong;
	}
	/**
	 * @param isoNameLong the isoNameLong to set
	 */
	public void setIsoNameLong(String isoNameLong) {
		this.isoNameLong = isoNameLong;
	}
	/**
	 * @return the isoNumber
	 */
	@Column(name = "cn_iso_nr")
	public Integer getIsoNumber() {
		return isoNumber;
	}
	/**
	 * @param isoNumber the isoNumber to set
	 */
	public void setIsoNumber(Integer isoNumber) {
		this.isoNumber = isoNumber;
	}
	/**
	 * @return the parentTerritoryIsoNumber
	 */
	@Column(name = "cn_parent_tr_iso_nr")
	public Integer getParentTerritoryIsoNumber() {
		return parentTerritoryIsoNumber;
	}

	/**
	 * @param parentTerritoryIsoNumber the parentTerritoryIsoNumber to set
	 */
	public void setParentTerritoryIsoNumber(Integer parentTerritoryIsoNumber) {
		this.parentTerritoryIsoNumber = parentTerritoryIsoNumber;
	}

	/**
	 * @return the parentTerritory
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false, name = "territory_fk")
	public Territory getParentTerritory() {
		return parentTerritory;
	}
	/**
	 * @param parentTerritory the parentTerritory to set
	 */
	public void setParentTerritory(Territory parentTerritory) {
		this.parentTerritory = parentTerritory;
	}
	/**
	 * @return the officialLocalName
	 */
	@Column(name = "cn_official_name_local")
	public String getOfficialLocalName() {
		return officialLocalName;
	}
	/**
	 * @param officialLocalName the officialLocalName to set
	 */
	public void setOfficialLocalName(String officialLocalName) {
		this.officialLocalName = officialLocalName;
	}
	/**
	 * @return the officialEnglishName
	 */
	@Column(name = "cn_official_name_en")
	public String getOfficialEnglishName() {
		return officialEnglishName;
	}
	/**
	 * @param officialEnglishName the officialEnglishName to set
	 */
	public void setOfficialEnglishName(String officialEnglishName) {
		this.officialEnglishName = officialEnglishName;
	}
	/**
	 * @return the shortLocalName
	 */
	@Column(name = "cn_short_local")
	public String getShortLocalName() {
		return shortLocalName;
	}
	/**
	 * @param shortLocalName the shortLocalName to set
	 */
	public void setShortLocalName(String shortLocalName) {
		this.shortLocalName = shortLocalName;
	}
	/**
	 * @return the shortEnglishName
	 */
	@Column(name = "cn_short_en")
	public String getShortEnglishName() {
		return shortEnglishName;
	}
	/**
	 * @param shortEnglishName the shortEnglishName to set
	 */
	public void setShortEnglishName(String shortEnglishName) {
		this.shortEnglishName = shortEnglishName;
	}
	/**
	 * @return the capital
	 */
	@Column(name = "cn_capital")
	public String getCapital() {
		return capital;
	}
	/**
	 * @param capital the capital to set
	 */
	public void setCapital(String capital) {
		this.capital = capital;
	}
	/**
	 * @return the topLevelDomain
	 */
	@Column(name = "cn_tldomain")
	public String getTopLevelDomain() {
		return topLevelDomain;
	}
	/**
	 * @param topLevelDomain the topLevelDomain to set
	 */
	public void setTopLevelDomain(String topLevelDomain) {
		this.topLevelDomain = topLevelDomain;
	}
	/**
	 * @return the currencyNameShort
	 */
	@Column(name = "cn_currency_iso_3")
	public String getCurrencyIsoNameShort() {
		return currencyIsoNameShort;
	}
	/**
	 * @param currencyNameShort the currencyNameShort to set
	 */
	public void setCurrencyIsoNameShort(String currencyIsoNameShort) {
		this.currencyIsoNameShort = currencyIsoNameShort;
	}
	/**
	 * @return the currencyIsoNumber
	 */
	@Column(name = "cn_currency_iso_nr")
	public Integer getCurrencyIsoNumber() {
		return currencyIsoNumber;
	}
	/**
	 * @param currencyIsoNumber the currencyIsoNumber to set
	 */
	public void setCurrencyIsoNumber(Integer currencyIsoNumber) {
		this.currencyIsoNumber = currencyIsoNumber;
	}
	/**
	 * @return the phonePrefix
	 */
	@Column(name = "cn_phone")
	public Integer getPhonePrefix() {
		return phonePrefix;
	}
	/**
	 * @param phonePrefix the phonePrefix to set
	 */
	public void setPhonePrefix(Integer phonePrefix) {
		this.phonePrefix = phonePrefix;
	}
	/**
	 * @return the euMember
	 */
	@Column(name = "cn_eu_member")
	public Boolean getEuMember() {
		return euMember;
	}
	/**
	 * @param euMember the euMember to set
	 */
	public void setEuMember(Boolean euMember) {
		this.euMember = euMember;
	}
	/**
	 * @return the unoMember
	 */
	@Column(name = "cn_uno_member")
	public Boolean getUnoMember() {
		return unoMember;
	}
	/**
	 * @param unoMember the unoMember to set
	 */
	public void setUnoMember(Boolean unoMember) {
		this.unoMember = unoMember;
	}
	
	
	
}
