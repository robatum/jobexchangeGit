package net.agef.jobexchange.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
public class Education extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2131214029488599809L;

	
	private Applicant relatedApplicant;
	private Applicant relatedHighestDegreeApplicant;
	private EducationInstituteTypeEnum educationalInstitute;
	@Boost(1.5f)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private DegreeEnum degree;
	@Boost(2.5f)
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String fieldSpecialization; 
	private Date dateOfGraduation;
	private Country country;
	private String location;
	private String instituteName;
	
	/* Bewerberprofil Sommer 2010 */
	private Date started;
	private DecisionYesNoEnum untilToday;
	@Boost(2.5f)
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String field; // Fachrichtung
	/* Ende Bewerberprofil Sommer 2010 */
	
	/**
	 * @return the relatedApplicant
	 */
	@ManyToOne
	@JoinColumn(updatable=false, insertable=false, name="applicantFurtherEdu_fk")
	public Applicant getRelatedApplicant() {
		return relatedApplicant;
	}
	/**
	 * @param relatedApplicant the relatedApplicant to set
	 */
	public void setRelatedApplicant(Applicant relatedApplicant) {
		this.relatedApplicant = relatedApplicant;
	}
	
	/**
	 * @return the relatedHighestDegreeApplicant
	 */
	@OneToOne(mappedBy = "highestDegree")
	public Applicant getRelatedHighestDegreeApplicant() {
		return relatedHighestDegreeApplicant;
	}
	/**
	 * @param relatedHighestDegreeApplicant the relatedHighestDegreeApplicant to set
	 */
	public void setRelatedHighestDegreeApplicant(
			Applicant relatedHighestDegreeApplicant) {
		this.relatedHighestDegreeApplicant = relatedHighestDegreeApplicant;
	}
	/**
	 * @return the educationalInstitute
	 */
	@Enumerated(EnumType.STRING)
	public EducationInstituteTypeEnum getEducationalInstitute() {
		return educationalInstitute;
	}
	/**
	 * @param educationalInstitute the educationalInstitute to set
	 */
	public void setEducationalInstitute(EducationInstituteTypeEnum educationalInstitute) {
		this.educationalInstitute = educationalInstitute;
	}
	/**
	 * @return the degree
	 */
	@Enumerated(EnumType.STRING)
	public DegreeEnum getDegree() {
		return degree;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(DegreeEnum degree) {
		this.degree = degree;
	}
	/**
	 * @return the fieldSpecialization
	 */
	public String getFieldSpecialization() {
		return fieldSpecialization;
	}
	/**
	 * @param fieldSpecialization the fieldSpecialization to set
	 */
	public void setFieldSpecialization(String fieldSpecialization) {
		this.fieldSpecialization = fieldSpecialization;
	}
	/**
	 * @return the dateOfGraduation
	 */
	public Date getDateOfGraduation() {
		return dateOfGraduation;
	}
	/**
	 * @param dateOfGraduation the dateOfGraduation to set
	 */
	public void setDateOfGraduation(Date dateOfGraduation) {
		this.dateOfGraduation = dateOfGraduation;
	}
	/**
	 * @return the country
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the instituteName
	 */
	public String getInstituteName() {
		return instituteName;
	}
	/**
	 * @param instituteName the instituteName to set
	 */
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	
	public int compareTo(Education o) {
		System.out.println("Compare Education Objekte");
        return String.valueOf(this.getId()).compareTo(String.valueOf(o.getId()));
    }
	
	@Override
	public boolean equals(Object obj) {
		System.out.println("Vergleiche Education Objekte");
		if (this == obj)
		//	return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Education other = (Education) obj;
		if (educationalInstitute != other.educationalInstitute)
			return false;
		if (instituteName != other.instituteName)
			return false;
		if (country != other.country)
			return false;
		if (degree != other.degree)
			return false;
		if (dateOfGraduation != other.dateOfGraduation)
			return false;
		if (fieldSpecialization != other.fieldSpecialization)
			return false;
		return true;
	}
	public void setUntilToday(DecisionYesNoEnum untilToday) {
		this.untilToday = untilToday;
	}
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getUntilToday() {
		return untilToday;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getField() {
		return field;
	}
	public void setStarted(Date started) {
		this.started = started;
	}
	public Date getStarted() {
		return started;
	}
	
}
