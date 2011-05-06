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
public class AlumniRole extends AbstractUserRole{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8391118942047740239L;
	
	User user;
	AbstractUserRoleData alumniRoleData;

	public AlumniRole(){
		this.alumniRoleData = new AlumniRoleData();
	}
	
	public AlumniRole(User user){
		this.alumniRoleData = new AlumniRoleData();
		this.user = user;
		this.setRoleData(alumniRoleData);
		
	}
	
	
	@Override
	@Transient
	public AbstractUserRoleData getRoleData() {
		return this.user.getUserRoleData();
	}
	
	public void setRoleData(AbstractUserRoleData userRoleData){
		this.user.setUserRoleData((AlumniRoleData)userRoleData);
	}
	
	public User getOwner(){
		return this.user;
	}
	
	public void setOwner(User user) {
		this.user = user;
		this.setRoleData(alumniRoleData);
	}

	

}
