/**
 * 
 */
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
import org.hibernate.annotations.Cascade;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * @author AGEF
 *
 */
@Entity
@Indexed
@Table(name="static_territories")
public class Territory extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -650029133493539098L;
	
	private Collection<SearchHistoryJobs> relatedSearchHistoryJobs = new TreeSet<SearchHistoryJobs>();
	private Collection<SearchHistoryApplicant> relatedSearchHistoryApplicant = new TreeSet<SearchHistoryApplicant>();
	

	private Collection<Country> relatedCountries;
	private Collection<Territory> relatedTerritories;
	private Territory parentTerritory;
	
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private Integer isoNumber;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String nameEnglish;
	
	@Inject
	public Territory(){
		
	}
	
	public Territory(String nameEnglish, Integer isoNumber){
		this.nameEnglish = nameEnglish;
		this.isoNumber = isoNumber;
		
	}
	
	public Territory(Collection<Country> relatedCountries, String nameEnglish, Integer isoNumber){
		this.relatedCountries = relatedCountries;
		this.nameEnglish = nameEnglish;
		this.isoNumber = isoNumber;
		
	}
	
//	public Territory(Collection<Territory> relatedTerritories, String nameEnglish, Integer isoNumber){
//		this.relatedTerritories = relatedTerritories;
//		this.nameEnglish = nameEnglish;
//		this.isoNumber = isoNumber;	
//	}
	
	
	/**
	 * @return the getRelatedJobs
	 */
	@OneToMany(mappedBy="queryContinent")
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
	@OneToMany(mappedBy="queryContinent")
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
	 * @return the getRelatedCountries
	 */
	@OneToMany(mappedBy="parentTerritory", cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
        org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	public Collection<Country> getRelatedCountries() {
		return relatedCountries;
	}
	/**
	 * @param getRelatedCountries the getRelatedCountries to set
	 */
	public void setRelatedCountries(Collection<Country> relatedCountries) {
		this.relatedCountries = relatedCountries;
	}
	
	/**
	 * @return the relatedTerritories
	 */
	@OneToMany(mappedBy="parentTerritory", cascade={CascadeType.REMOVE})
	public Collection<Territory> getRelatedTerritories() {
		return relatedTerritories;
	}
	/**
	 * @param relatedTerritories the relatedTerritories to set
	 */
	public void setRelatedTerritories(Collection<Territory> relatedTerritories) {
		this.relatedTerritories = relatedTerritories;
	}
	/**
	 * @return the isoNumber
	 */
	@Column(name = "tr_iso_nr")
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
	 * @return the parentIsoNumber
	 */
	@ManyToOne
	@JoinColumn(nullable=true, name = "tr_parent_fk")
	//@Column(name = "tr_parent_iso_nr")
	public Territory getParentTerritory() {
		return parentTerritory;
	}
	/**
	 * @param parentIsoNumber the parentIsoNumber to set
	 */
	public void setParentTerritory(Territory parentTerritory) {
		this.parentTerritory = parentTerritory;
	}
	/**
	 * @return the nameEnglish
	 */
	@Column(name = "tr_name_en")
	public String getNameEnglish() {
		return nameEnglish;
	}
	/**
	 * @param nameEnglish the nameEnglish to set
	 */
	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}
	
	
	
	
}
