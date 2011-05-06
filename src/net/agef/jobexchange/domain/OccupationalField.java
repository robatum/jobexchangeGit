/**
 * 
 */
package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name="occupationalField")
public class OccupationalField extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2692218442670342260L;
	
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Long fieldId;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private Long parentFieldId;
	private String fieldNameGerman;
	private String fieldNameEnglish;
	
	private Collection<JobImpl> relatedJobOffers = new TreeSet<JobImpl>();
	private Collection<WorkExperience> relatedWorkExperiences = new TreeSet<WorkExperience>();
	
	
	/**
	 * @return Boolean ob aktuelles Berufsfeld eine Oberkategorie ist oder nicht
	 */
	@Transient
	public Boolean isParentField() {
		if (parentFieldId != null && parentFieldId!=0)
			return false;
		else return true;
	}
	
	
	/**
	 * @return the fieldId
	 */
	@Column(name = "fieldId")
	public Long getFieldId() {
		return fieldId;
	}
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	/**
	 * @return the parentFieldId
	 */
	@Column(name = "parentFieldId")
	public Long getParentFieldId() {
		return parentFieldId;
	}
	/**
	 * @param parentFieldId the parentFieldId to set
	 */
	public void setParentFieldId(Long parentFieldId) {
		this.parentFieldId = parentFieldId;
	}
	/**
	 * @return the fieldNameGerman
	 */
	@Column(name = "fieldNameGerman")
	public String getFieldNameGerman() {
		return fieldNameGerman;
	}
	/**
	 * @param fieldNameGerman the fieldNameGerman to set
	 */
	public void setFieldNameGerman(String fieldNameGerman) {
		this.fieldNameGerman = fieldNameGerman;
	}
	/**
	 * @return the fieldNameEnglish
	 */
	@Column(name = "fieldNameEnglish")
	public String getFieldNameEnglish() {
		return fieldNameEnglish;
	}
	/**
	 * @param fieldNameEnglish the fieldNameEnglish to set
	 */
	public void setFieldNameEnglish(String fieldNameEnglish) {
		this.fieldNameEnglish = fieldNameEnglish;
	}
	
	/**
	 * @return the getRelatedJobs
	 */
	@OneToMany(mappedBy="occupationalField")
	public Collection<JobImpl> getRelatedJobOffers() {
		return relatedJobOffers;
	}
	/**
	 * @param getRelatedJobs the getRelatedJobs to set
	 */
	
	public void setRelatedJobOffers(Collection<JobImpl> relatedJobOffers) {
		this.relatedJobOffers = relatedJobOffers;
	}
		
	
	/**
	 * @return the relatedWorkExperiences
	 */
	@OneToMany(mappedBy="occupationalField")
	public Collection<WorkExperience> getRelatedWorkExperiences() {
		return relatedWorkExperiences;
	}
	
	/**
	 * @param relatedWorkExperiences the relatedWorkExperiences to set
	 */
	public void setRelatedWorkExperiences(
			Collection<WorkExperience> relatedWorkExperiences) {
		this.relatedWorkExperiences = relatedWorkExperiences;
	}
	
	
}
