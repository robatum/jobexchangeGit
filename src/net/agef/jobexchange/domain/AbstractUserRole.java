/**
 * 
 */
package net.agef.jobexchange.domain;






/**
 * @author AGEF
 *
 */
public abstract class AbstractUserRole extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2372486034667167270L;

	
	public abstract AbstractUserRoleData getRoleData();
	public void setRoleData(AbstractUserRoleData roleData){};
	public void changeUserRole(AbstractUserRole userRole){};
	public void setOwner(User user){};
    
}
