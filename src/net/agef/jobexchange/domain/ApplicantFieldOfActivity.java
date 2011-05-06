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
public class ApplicantFieldOfActivity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7157474882191502149L;

	private String apdCode;
	private Long numericId;
	private String parentId;
	private String descriptionGerman;
	private String descriptionEnglish;
	
	private User relatedUser;
	
	/**
	 * @return the apdCode
	 */
	public String getApdCode() {
		return apdCode;
	}
	/**
	 * @param apdCode the apdCode to set
	 */
	public void setApdCode(String apdCode) {
		this.apdCode = apdCode;
	}
	/**
	 * @return the numericId
	 */
	public Long getNumericId() {
		return numericId;
	}
	/**
	 * @param numericId the numericId to set
	 */
	public void setNumericId(Long numericId) {
		this.numericId = numericId;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the descriptionGerman
	 */
	public String getDescriptionGerman() {
		return descriptionGerman;
	}
	/**
	 * @param descriptionGerman the descriptionGerman to set
	 */
	public void setDescriptionGerman(String descriptionGerman) {
		this.descriptionGerman = descriptionGerman;
	}
	/**
	 * @return the descriptionEnglish
	 */
	public String getDescriptionEnglish() {
		return descriptionEnglish;
	}
	/**
	 * @param descriptionEnglish the descriptionEnglish to set
	 */
	public void setDescriptionEnglish(String descriptionEnglish) {
		this.descriptionEnglish = descriptionEnglish;
	}
	/**
	 * @return the relatedUser
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=true)
	public User getRelatedUser() {
		return relatedUser;
	}
	/**
	 * @param relatedUser the relatedUser to set
	 */
	public void setRelatedUser(User relatedUser) {
		this.relatedUser = relatedUser;
	}
}
