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
public class ApplicantContact extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6331890130025321340L;
	
	private User relatedApplicant;
	private User applicantContactOwner;
	private String contactNote;
	
	
	public ApplicantContact(User relatedApplicant, User applicantContactOwner, String contactNote){
		this.relatedApplicant = relatedApplicant;
		this.applicantContactOwner = applicantContactOwner;
		this.contactNote = contactNote;
	}
	
	/**
	 * @return the relatedApplicant
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=true)
	public User getRelatedApplicant() {
		return relatedApplicant;
	}
	/**
	 * @param relatedApplicant the relatedApplicant to set
	 */
	public void setRelatedApplicant(User relatedApplicant) {
		this.relatedApplicant = relatedApplicant;
	}
	/**
	 * @return the applicantContactOwner
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=true)
	public User getApplicantContactOwner() {
		return applicantContactOwner;
	}
	/**
	 * @param applicantContactOwner the applicantContactOwner to set
	 */
	public void setApplicantContactOwner(User applicantContactOwner) {
		this.applicantContactOwner = applicantContactOwner;
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
