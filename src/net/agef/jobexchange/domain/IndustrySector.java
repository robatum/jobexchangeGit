/**
 * 
 */
package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="industrySector")
@Indexed
public class IndustrySector extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2645789500020243178L;

	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private Long sectorId;
	private Long parentSectorId;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String sectorNameGerman;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String sectorNameEnglish;
	
	private Collection<Applicant> relatedApplicantsByPreferredSector = new TreeSet<Applicant>();
	private Collection<Applicant> relatedApplicantsByManagementExperienceSector = new TreeSet<Applicant>();
	private Collection<OrganisationRoleData> relatedOrganisationData = new TreeSet<OrganisationRoleData>();
	private Collection<WorkExperience> relatedWorkExperienceSectors = new TreeSet<WorkExperience>();
	/**
	 * @return the sectorId
	 */
	@Column(name = "sectorId")
	public Long getSectorId() {
		return sectorId;
	}
	/**
	 * @param sectorId the sectorId to set
	 */
	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}
	/**
	 * @return the parentSectorId
	 */
	@Column(name = "parentSectorId")
	public Long getParentSectorId() {
		return parentSectorId;
	}
	/**
	 * @param parentSectorId the parentSectorId to set
	 */
	public void setParentSectorId(Long parentSectorId) {
		this.parentSectorId = parentSectorId;
	}
	/**
	 * @return the sectorNameGerman
	 */
	@Column(name = "sectorNameGerman")
	public String getSectorNameGerman() {
		return sectorNameGerman;
	}
	/**
	 * @param sectorNameGerman the sectorNameGerman to set
	 */
	public void setSectorNameGerman(String sectorNameGerman) {
		this.sectorNameGerman = sectorNameGerman;
	}
	/**
	 * @return the sectorNameEnglish
	 */
	@Column(name = "sectorNameEnglish")
	public String getSectorNameEnglish() {
		return sectorNameEnglish;
	}
	/**
	 * @param sectorNameEnglish the sectorNameEnglish to set
	 */
	public void setSectorNameEnglish(String sectorNameEnglish) {
		this.sectorNameEnglish = sectorNameEnglish;
	}

	/**
	 * @return the relatedApplicantsByPreferredSector
	 */
	@OneToMany(mappedBy="preferredFieldOfActivity")
	public Collection<Applicant> getRelatedApplicantsByPreferredSector() {
		return relatedApplicantsByPreferredSector;
	}
	/**
	 * @param relatedApplicantsByPreferredSector the relatedApplicantsByPreferredSector to set
	 */
	public void setRelatedApplicantsByPreferredSector(
			Collection<Applicant> relatedApplicantsByPreferredSector) {
		this.relatedApplicantsByPreferredSector = relatedApplicantsByPreferredSector;
	}
	/**
	 * @return the relatedApplicantsByManagementExperienceSector
	 */
	@OneToMany(mappedBy="managementExperienceSector")
	public Collection<Applicant> getRelatedApplicantsByManagementExperienceSector() {
		return relatedApplicantsByManagementExperienceSector;
	}
	/**
	 * @param relatedApplicantsByManagementExperienceSector the relatedApplicantsByManagementExperienceSector to set
	 */
	public void setRelatedApplicantsByManagementExperienceSector(
			Collection<Applicant> relatedApplicantsByManagementExperienceSector) {
		this.relatedApplicantsByManagementExperienceSector = relatedApplicantsByManagementExperienceSector;
	}
	/**
	 * @return the relatedOrganisationData
	 */
	@OneToMany(mappedBy="industrySector")
	public Collection<OrganisationRoleData> getRelatedOrganisationData() {
		return relatedOrganisationData;
	}
	/**
	 * @param relatedOrganisationData the relatedOrganisationData to set
	 */
	public void setRelatedOrganisationData(
			Collection<OrganisationRoleData> relatedOrganisationData) {
		this.relatedOrganisationData = relatedOrganisationData;
	}
	/**
	 * @return the relatedWorkExperienceSectors
	 */
	@OneToMany(mappedBy="sector")
	public Collection<WorkExperience> getRelatedWorkExperienceSectors() {
		return relatedWorkExperienceSectors;
	}
	/**
	 * @param relatedWorkExperienceSectors the relatedWorkExperienceSectors to set
	 */
	public void setRelatedWorkExperienceSectors(
			Collection<WorkExperience> relatedWorkExperienceSectors) {
		this.relatedWorkExperienceSectors = relatedWorkExperienceSectors;
	}
	
}
