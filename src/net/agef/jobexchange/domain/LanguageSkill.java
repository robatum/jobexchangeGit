package net.agef.jobexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
public class LanguageSkill extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2860218680728190719L;
	
	private JobImpl relatedJob;
	private Applicant relatedApplicant;
	@IndexedEmbedded
	private Languages name;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private LanguageSkillsEnum level;
	
	/**
	 * @return the relatedJob
	 */
	@ManyToOne
	@JoinColumn(insertable=false, updatable=false, name="jobOfferLanguageSkillsOther_fk")
	public JobImpl getRelatedJob() {
		return relatedJob;
	}
	/**
	 * @param relatedJob the relatedJob to set
	 */
	public void setRelatedJob(JobImpl relatedJob) {
		this.relatedJob = relatedJob;
	}
	
	/**
	 * @return the relatedApplicant
	 */
	@ManyToOne
	@JoinColumn(insertable=true, updatable=true, name="applicantLanguageSkillsOther_fk")
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
	 * @return the name
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=true)
	public Languages getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(Languages name) {
		this.name = name;
	}
	/**
	 * @return the level
	 */
	@Enumerated(EnumType.STRING)
	public LanguageSkillsEnum getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(LanguageSkillsEnum level) {
		this.level = level;
	}
	
	public int compareTo(LanguageSkill o) {
		System.out.println("Compare LanguageSkill Objekte");
        return String.valueOf(this.getId()).compareTo(String.valueOf(o.getId()));
    }

}
