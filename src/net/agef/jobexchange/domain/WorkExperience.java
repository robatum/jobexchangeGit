package net.agef.jobexchange.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.apache.tapestry5.beaneditor.DataType;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
public class WorkExperience extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7854761362407782807L;
	
	private Applicant relatedApplicant;
	private Date started;
	private Date finished;
	@Boost(2.5f)
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String jobTitle;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String jobDescription;
	@IndexedEmbedded
	private IndustrySector sector;
	@IndexedEmbedded
	private OccupationalField occupationalField;
//	@IndexedEmbedded
//	private OccupationalField occupationalSubField;

	/* Bewerberprofil Sommer 2010 */
	private DecisionYesNoEnum untilToday;
	private String company;
	private String homepage;
	private DecisionYesNoEnum managementExperience;
	/* Ende Bewerberprofil Sommer 2010 */
	
	/**
	 * @return the relatedApplicant
	 */
	@ManyToOne
	@JoinColumn(updatable=false, insertable=false, name="applicantWorkExp_fk")
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
	 * @return the started
	 */
	public Date getStarted() {
		return started;
	}
	/**
	 * @param started the started to set
	 */
	public void setStarted(Date started) {
		this.started = started;
	}
	/**
	 * @return the finished
	 */
	public Date getFinished() {
		return finished;
	}
	/**
	 * @param finished the finished to set
	 */
	public void setFinished(Date finished) {
		this.finished = finished;
	}
	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * @return the sector
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public IndustrySector getSector() {
		return sector;
	}
	/**
	 * @param sector the sector to set
	 */
	public void setSector(IndustrySector sector) {
		this.sector = sector;
	}
	/**
	 * @return the jobDescription
	 */
	@Lob
	public String getJobDescription() {
		return jobDescription;
	}
	/**
	 * @param jobDescription the jobDescription to set
	 */
	@DataType(value="longtext")
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	
	/**
	 * @return the occupationalField
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public OccupationalField getOccupationalField() {
		return occupationalField;
	}
	/**
	 * @param occupationalField the occupationalField to set
	 */
	public void setOccupationalField(OccupationalField occupationalField) {
		this.occupationalField = occupationalField;
	}
	
	
	
	public int compareTo(WorkExperience o) {
		System.out.println("Compare WorkExperience Objekte");
        return String.valueOf(this.getId()).compareTo(String.valueOf(o.getId()));
    }
	public void setUntilToday(DecisionYesNoEnum untilToday) {
		this.untilToday = untilToday;
	}
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getUntilToday() {
		return untilToday;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany() {
		return company;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setManagementExperience(DecisionYesNoEnum managementExperience) {
		this.managementExperience = managementExperience;
	}
	@Enumerated(EnumType.STRING)
	public DecisionYesNoEnum getManagementExperience() {
		return managementExperience;
	}


}
