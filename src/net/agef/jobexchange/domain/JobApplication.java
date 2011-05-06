/**
 * 
 */
package net.agef.jobexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author AGEF
 *
 */
@Entity
public class JobApplication extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2486001304616408789L;
	
	private JobImpl relatedJob;
	private User jobApplicationOwner;
	private String contactNote;
	
	public JobApplication(JobImpl relatedJob, User jobApplicationOwner, String contactNote){
		this.relatedJob = relatedJob;
		this.jobApplicationOwner = jobApplicationOwner;
		this.contactNote = contactNote;
	}
	
	
	public JobApplication(){
		this.relatedJob = new JobImpl();
		this.jobApplicationOwner = new User();
		this.contactNote = "";
	}
	
	/**
	 * @return the relatedJob
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=true)
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
	 * @return the jobApplicationOwner
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=true)
	public User getJobApplicationOwner() {
		return jobApplicationOwner;
	}
	/**
	 * @param jobApplicationOwner the jobApplicationOwner to set
	 */
	public void setJobApplicationOwner(User jobApplicationOwner) {
		this.jobApplicationOwner = jobApplicationOwner;
	}
	/**
	 * @return the contactNote
	 */
	public String getContactNote() {
		return contactNote;
	}
	/**
	 * @param contactNote the contactNote to set
	 */
	public void setContactNote(String contactNote) {
		this.contactNote = contactNote;
	}
	

}
