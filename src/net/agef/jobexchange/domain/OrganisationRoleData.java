/**
 * 
 */
package net.agef.jobexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.tapestry5.beaneditor.DataType;
import org.apache.tapestry5.beaneditor.Validate;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

/**
 * @author AGEF
 *
 */
@Indexed
@Entity
@DiscriminatorValue("org")
public class OrganisationRoleData extends AbstractUserRoleData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4161708438303471951L;
	
	private User owner;
	
	@Field(index=Index.TOKENIZED, store=Store.YES)
	private String organisationName;
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String organisationDescription;
	@IndexedEmbedded
	private IndustrySector industrySector;
	private EmployeeAmountEnum organisationEmployeeAmount;
	private String humanRessourceDevelopment;
	
	@OneToOne(mappedBy = "userRoleData")
    public User getOwner() {
		return this.owner;
	}
	
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	/**
	 * @return the organisationName
	 */
	@Override
	@Validate("required")
	public String getOrganisationName() {
		return organisationName;
	}
	/**
	 * @param organisationName the organisationName to set
	 */
	@Override
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	/**
	 * @return the organisationDescription
	 */
	@Override
	@Validate("required")
	@Lob
	public String getOrganisationDescription() {
		return organisationDescription;
	}
	/**
	 * @param organisationDescription the organisationDescription to set
	 */
	@Override
	@DataType(value="longtext")
	public void setOrganisationDescription(String organisationDescription) {
		this.organisationDescription = organisationDescription;
	}

	/**
	 * @return the industrySector
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinColumn(nullable=true)
	public IndustrySector getIndustrySector() {
		return industrySector;
	}

	/**
	 * @param industrySector the industrySector to set
	 */
	public void setIndustrySector(IndustrySector industrySector) {
		this.industrySector = industrySector;
	}

	/**
	 * @return the organisationEmployeeAmount
	 */
	@Enumerated(EnumType.STRING)
	public EmployeeAmountEnum getOrganisationEmployeeAmount() {
		return organisationEmployeeAmount;
	}

	/**
	 * @param organisationEmployeeAmount the organisationEmployeeAmount to set
	 */
	public void setOrganisationEmployeeAmount(
			EmployeeAmountEnum organisationEmployeeAmount) {
		this.organisationEmployeeAmount = organisationEmployeeAmount;
	}

	/**
	 * @return the humanRessourceDevelopment
	 */
	@Lob	
	public String getHumanRessourceDevelopment() {
		return humanRessourceDevelopment;
	}

	/**
	 * @param humanRessourceDevelopment the humanRessourceDevelopment to set
	 */
	@DataType(value="longtext")
	public void setHumanRessourceDevelopment(String humanRessourceDevelopment) {
		this.humanRessourceDevelopment = humanRessourceDevelopment;
	}
	
	

}
