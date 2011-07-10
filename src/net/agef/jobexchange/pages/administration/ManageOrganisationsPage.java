/**
 * 
 */
package net.agef.jobexchange.pages.administration;

import java.util.Collection;
import java.util.List;


import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.LoginUserRole;
import net.agef.jobexchange.domain.OrganisationRoleData;
import net.agef.jobexchange.domain.User;
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

/**
 * @author AGEF
 *
 */
@SuppressWarnings("unused")
public class ManageOrganisationsPage {
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
	private LoginUser loginUser;
	
	@Component
	@Property
    private Grid organisationUserGrid;
	
	@Persist("Flash")
	@Property
	private OrganisationRoleData organisationUserData;
	
	@SuppressWarnings("unchecked")
	private final BeanModel organisationUserGridModel;
    {
    	organisationUserGridModel = beanModelSource.createDisplayModel(OrganisationRoleData.class, messages);
    	
    	organisationUserGridModel.include("organisationName");
    	organisationUserGridModel.add("industrySector", null);
    	organisationUserGridModel.add("jobs", null);
    	organisationUserGridModel.add("jobsOnline", null);
    	organisationUserGridModel.add("modify", null);
    	organisationUserGridModel.add("delete", null);
    	
    }
    
    
    public GridDataSource getOrganisationUserDataList() {
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
    		return new HibernateGridDataSource(session, OrganisationRoleData.class);
    	}
    	List<OrganisationRoleData> userList = null;
    	try {
			userList = uw.getOrganisationUserDataByLoginUser(this.loginUser);
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			logger.error("ManageOrgForm -- Login User Name could not be found in DB.");
			luw.logoutUser();
		}
		return (GridDataSource)userList;
	}
    
    
    Object onActionFromDelete(long orgUserId)
    {
        
			try {
				uw.deleteUser(orgUserId);
			} catch (ObjectNotDeletedException e) {
				e.printStackTrace();
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
			}
		
		return ManageOrganisationsPage.class;
    }
    
    
    Object onActionFromModify(long orgUserId)
    {
		return ManageOrganisationsPage.class;
    }
    
    /**
	 * @return the _model
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getOrganisationUserGridModel() {
		return organisationUserGridModel;
	}
	
	public int getOrganisationUserJobOffers(){
		return organisationUserData.getOwner().getMyJobOffers().size();
	}

	public int getOrganisationUserOnlineJobOffers(){
		return jw.getOnlineJobOffersByUser(organisationUserData.getOwner().getId());
	}
}
