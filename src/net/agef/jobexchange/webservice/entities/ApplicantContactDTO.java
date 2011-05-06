/**
 * 
 */
package net.agef.jobexchange.webservice.entities;


/**
 * @author Andreas Pursian
 *
 */
public class ApplicantContactDTO{


	private Long relatedApplicantProfileId;
	private Long applicantContactOwnerId;
	private String applicantContactOwnerName;
	private String contactNote;
	
	public ApplicantContactDTO(){
	}
	
	/**
	 * @return the relatedApplicantProfileId
	 */
	public Long getRelatedApplicantProfileId() {
		return relatedApplicantProfileId;
	}
	/**
	 * @param relatedApplicantProfileId the relatedApplicantProfileId to set
	 */
	public void setRelatedApplicantProfileId(Long relatedApplicantProfileId) {
		this.relatedApplicantProfileId = relatedApplicantProfileId;
	}
	/**
	 * @return the applicantContactOwnerId
	 */
	public Long getApplicantContactOwnerId() {
		return applicantContactOwnerId;
	}
	/**
	 * @param applicantContactOwnerId the applicantContactOwnerId to set
	 */
	public void setApplicantContactOwnerId(Long applicantContactOwnerId) {
		this.applicantContactOwnerId = applicantContactOwnerId;
	}
	/**
	 * @return the applicantContactOwnerName
	 */
	public String getApplicantContactOwnerName() {
		return applicantContactOwnerName;
	}
	/**
	 * @param applicantContactOwnerName the applicantContactOwnerName to set
	 */
	public void setApplicantContactOwnerName(String applicantContactOwnerName) {
		this.applicantContactOwnerName = applicantContactOwnerName;
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
