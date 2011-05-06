/**
 * 
 */
package net.agef.jobexchange.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author AGEF
 *
 */
@Entity
public class OrganisationRole extends AbstractUserRole{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2277170975809937242L;
	
	User user;
	AbstractUserRoleData organisationRoleData;
	
	public OrganisationRole(){
		this.organisationRoleData = new OrganisationRoleData();
	}
	
	public OrganisationRole(User user){
		this.organisationRoleData = new OrganisationRoleData();
		this.user = user;
		this.setRoleData(organisationRoleData);
	}
	
	@Override
	@Transient
	public AbstractUserRoleData getRoleData() {
		return this.organisationRoleData;
	}
	
	public void setRoleData(AbstractUserRoleData userRoleData){
		this.user.setUserRoleData((OrganisationRoleData)userRoleData);
	}
	
	public User getOwner(){
		return this.user;
	}
	
	public void setOwner(User user) {
		this.user = user;
		this.setRoleData(organisationRoleData);
	}

}
