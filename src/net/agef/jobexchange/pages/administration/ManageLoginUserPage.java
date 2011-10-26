/**
 * 
 */
package net.agef.jobexchange.pages.administration;

import java.util.List;

import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.LoginUserRole;
import net.agef.jobexchange.domain.OrganisationRoleData;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotDeletedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.hibernate.HibernateGridDataSource;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.security.annotation.Secured;

//import org.acegisecurity.annotation.Secured;

/**
 * @author AGEF
 *
 */
@Secured({"ROLE_ADMIN"})
public class ManageLoginUserPage {
	
	
	@Inject
	private Logger logger;
	
	@Inject 
	private Session session;
	
	@Inject
	private UserWorker uw;
	
	@Inject
	private JobWorker jw;
	
	@Inject
	private LoginUserWorker luw;
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;
	
	@Persist("Flash")
	@Property
	private LoginUser loginUser;
	
	@Component
	@Property
    private Grid loginUserGrid;
	
//	@Persist("Flash")
//	@Property
//	private OrganisationRoleData loginUserData;
	
	@SuppressWarnings("unchecked")
	private final BeanModel loginUserGridModel;
    {
    	loginUserGridModel = beanModelSource.createDisplayModel(LoginUser.class, messages);
    	
    	loginUserGridModel.include("username","familyName","givenName","enabled");
    	//loginUserGridModel.add("industrySector", null);
    	//loginUserGridModel.add("jobs", null);
    	//loginUserGridModel.add("jobsOnline", null);
    	loginUserGridModel.add("modify", null);
    	loginUserGridModel.add("delete", null);
    	
    }
    
    @Secured({"ROLE_ADMIN"})
    public GridDataSource getLoginUserDataList() {
    	if(luw.isLoggedInUser()){
    		try {
				this.loginUser = luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				logger.error("ManageOrgForm -- Login User Name could not be found in DB.");
				luw.logoutUser();
			}
    	}
    	LoginUserRole lur = new LoginUserRole();
    	lur.setAuthority("ROLE_ADMIN");
    	if (loginUser.getGrantedAuthorities().contains(lur)){
    		return new HibernateGridDataSource(session, LoginUser.class);
    	}    	
		return null;
	}
    
    
    Object onActionFromDelete(long loginUserId)
    {
        
			try {
				luw.deleteLoginUser(loginUserId);
			} catch (ObjectNotDeletedException e) {
				e.printStackTrace();
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
			}
		
		return ManageLoginUserPage.class;
    }
    
    
    Object onActionFromModify(long loginUserId)
    {
		return ManageLoginUserPage.class;
    }
    
    /**
	 * @return the _model
	 */

	@SuppressWarnings("rawtypes")
	public BeanModel getLoginUserGridModel() {
		return loginUserGridModel;
	}
//	
//	public int getOrganisationUserJobOffers(){
//		return organisationUserData.getOwner().getMyJobOffers().size();
//	}
//
//	public int getOrganisationUserOnlineJobOffers(){
//		return jw.getOnlineJobOffersByUser(organisationUserData.getOwner().getId());
//	}
//	

}
