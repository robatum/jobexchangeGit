/**
 * 
 */
package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.TreeSet;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * @author AGEF
 *
 */
@Entity
@Indexed
@Table(name="portalIdentifier")
public class PortalIdentifier extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2692225442670752260L;
	
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private PortalIdentifierEnum portal;
	
	private Collection<Applicant> relatedApplicants = new TreeSet<Applicant>();
	
	public PortalIdentifier(){
	}
	
	public PortalIdentifier(PortalIdentifierEnum portalIdentifierEnum){
		this.portal = portalIdentifierEnum;
	}
	
	public PortalIdentifier(Byte portalIdentifier) throws EnumValueNotFoundException{
		this.portal = PortalIdentifierEnum.fromPortalId(portalIdentifier.intValue());
	}
	
	/**
	 * @return the portal
	 */
	@Enumerated(EnumType.STRING)
	public PortalIdentifierEnum getPortal() {
		return portal;
	}
	/**
	 * @param portal the portal to set
	 */
	public void setPortal(PortalIdentifierEnum portal) {
		this.portal = portal;
	}
	/**
	 * @return the relatedApplicants
	 */
	@OneToMany(mappedBy="portalIdList")
	public Collection<Applicant> getRelatedApplicants() {
		return relatedApplicants;
	}
	/**
	 * @param relatedApplicants the relatedApplicants to set
	 */
	public void setRelatedApplicants(Collection<Applicant> relatedApplicants) {
		this.relatedApplicants = relatedApplicants;
	}	
	
	@Transient
	public String getPortalName(){
		return this.portal.toString();
	}
	
	@Transient
	public Byte getPortalIdAsByte(){
		return this.portal.portalId().byteValue();
	}
	
	@Transient
	public Integer getPortalIdAsInt(){
		return this.portal.portalId();
	}
}
