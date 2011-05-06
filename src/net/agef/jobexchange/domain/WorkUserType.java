package net.agef.jobexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Indexed;

@Indexed
@Entity
public class WorkUserType extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -514344894200230366L;

	private Applicant applicant;
	private WorkTypeEnum workType;

	public void setWorkType(WorkTypeEnum workType) {
		this.workType = workType;
	}
	
	@Enumerated(EnumType.STRING)
	public WorkTypeEnum getWorkType() {
		return workType;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	@ManyToOne
	@JoinColumn(updatable=false, insertable=false, name = "applicant_id")
	public Applicant getApplicant() {
		return applicant;
	}

}
