/**
 * 
 */
package net.agef.jobexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author mutabor
 *
 */
@Entity
public class AccessHistoryApplicant extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2131214023588732809L;
	
	
	private Applicant accessedApplicantProfile;


	/**
	 * @param accessedApplicantProfile the accessedApplicantProfile to set
	 */
	public void setAccessedApplicantProfile(Applicant accessedApplicantProfile) {
		this.accessedApplicantProfile = accessedApplicantProfile;
	}


	/**
	 * @return the accessedApplicantProfile
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public Applicant getAccessedApplicantProfile() {
		return accessedApplicantProfile;
	}
	
	
}
