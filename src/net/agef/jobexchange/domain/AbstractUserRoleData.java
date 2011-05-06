/**
 * 
 */
package net.agef.jobexchange.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author AGEF
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="userRole",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("super")
@Table(name="userroledata")
public abstract class AbstractUserRoleData extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9170099348356756754L;
	
	@Transient
	public abstract String getOrganisationName();
	public abstract void setOrganisationName(String organisationName);
	@Transient
	public abstract String getOrganisationDescription();
	public abstract void setOrganisationDescription(String organisationDescription);
	@Transient
	public abstract IndustrySector getIndustrySector();
	public abstract void setIndustrySector(IndustrySector industrySector);	
	
}
