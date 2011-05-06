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
public class AccessHistoryJobs extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2131214029488729809L;
	
	
	private JobImpl accessedJobProfile;


	/**
	 * @param accessedJobProfile the accessedJobProfile to set
	 */
	public void setAccessedJobProfile(JobImpl accessedJobProfile) {
		this.accessedJobProfile = accessedJobProfile;
	}


	/**
	 * @return the accessedJobProfile
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public JobImpl getaccessedJobProfile() {
		return accessedJobProfile;
	}
}