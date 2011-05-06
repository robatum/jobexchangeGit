/**
 * 
 */
package net.agef.jobexchange.webservice.entities;



/**
 * @author Andreas Pursian
 *
 */
public class JobApplicationDTO {
	
	private Long relatedJobId;
	private Long applicationOwnerId;
	private String applicationOwnerName;
	private String relatedJobDescription;
	private String contactNote;
	
	public JobApplicationDTO(){
		
	}
	
	/**
	 * @return the relatedJobId
	 */
	public Long getRelatedJobId() {
		return relatedJobId;
	}
	/**
	 * @param relatedJobId the relatedJobId to set
	 */
	public void setRelatedJobId(Long relatedJobId) {
		this.relatedJobId = relatedJobId;
	}
	/**
	 * @return the applicationOwnerId
	 */
	public Long getApplicationOwnerId() {
		return applicationOwnerId;
	}
	/**
	 * @param applicationOwnerId the applicationOwnerId to set
	 */
	public void setApplicationOwnerId(Long applicationOwnerId) {
		this.applicationOwnerId = applicationOwnerId;
	}
	/**
	 * @return the applicationOwnerName
	 */
	public String getApplicationOwnerName() {
		return applicationOwnerName;
	}
	/**
	 * @param applicationOwnerName the applicationOwnerName to set
	 */
	public void setApplicationOwnerName(String applicationOwnerName) {
		this.applicationOwnerName = applicationOwnerName;
	}
	/**
	 * @return the relatedJobDescription
	 */
	public String getRelatedJobDescription() {
		return relatedJobDescription;
	}
	/**
	 * @param relatedJobDescription the relatedJobDescription to set
	 */
	public void setRelatedJobDescription(String relatedJobDescription) {
		this.relatedJobDescription = relatedJobDescription;
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
