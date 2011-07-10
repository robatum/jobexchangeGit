/**
 * 
 */
package net.agef.jobexchange.pages.administration;

import java.util.Collection;

import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.LoginUserRole;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.hibernate.HibernateGridDataSource;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
public class ManageJobOffersPage {
	
	@Inject
	private JobWorker jw;
	
	@Inject 
	private Session session;
	
	@Inject
	private LoginUserWorker luw;
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;
	
	@Component
    private Grid jobOfferGrid;
	
	@Persist
	private Collection<JobImpl> jobOfferList;
	
	@Persist("Flash")
	private JobImpl jobOfferData;
	
	@Persist("Flash")
	private LoginUser loginUser;
	
	@SuppressWarnings("unchecked")
	private final BeanModel jobOfferGridModel;
    {
    	jobOfferGridModel = beanModelSource.createDisplayModel(JobImpl.class, messages);
    	jobOfferGridModel.include("jobDescription","organisationName","jobOfferExpireDate");
    	
    	jobOfferGridModel.add("Id", null);
    	//jobOfferGridModel.add("expireDate", null);
    	jobOfferGridModel.add("onlineState", null);
    	jobOfferGridModel.add("modify", null);
    	jobOfferGridModel.add("delete", null);
    	jobOfferGridModel.reorder("id");
    	    	
    }
    
    /**
	 * Die Methode 'getJobOfferList()' liefert in Abhängigkeit von der Rolle des jeweils aufrufenden Nutzer (Admin oder nicht)
	 * ein Objekt vom Typ GridDataSource (vormals Collection<JobImpl>). Hierdurch wird der Hibernate Criteria Aufruf von T5 
	 * direkt auf die jeweils eingestellte Pagination Größe mittels maxresults limitiert und ermöglicht damit ein schnelles 
	 * Blättern auch in großen Datenbeständen  
	 */
    public GridDataSource getJobOfferList() { 
    	if(luw.isLoggedInUser()){
    		try {
				this.loginUser = luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				luw.logoutUser();
			}
    	} else luw.logoutUser();
    	LoginUserRole lur = new LoginUserRole();
    	lur.setAuthority("ROLE_ADMIN");
    	if (loginUser.getGrantedAuthorities().contains(lur)){
    		return new HibernateGridDataSource(session, JobImpl.class);
    		//return jw.getAllJobOffers();
    	}else return (GridDataSource)loginUser.getProvidedJobOffers();
	}
    
    public void setJobOfferList(Collection<JobImpl> jobOfferList) { 
    	this.jobOfferList = jobOfferList;
    }
    
    
    Object onActionFromDelete(long jobOfferId)
    {
        
			try {
				jw.deleteJobOffer(jobOfferId);
			} catch (JobOfferNotFoundException e) {
				e.printStackTrace();
				
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
			}
		
		return ManageJobOffersPage.class;	
    }

    
    Object onActionFromOnlineState(long jobOfferId)
    {	JobImpl job;
		try {
			job = jw.getJobOfferDetails(jobOfferId);
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try {
			jw.setJobOfferOnlineStatus(job, !job.getOnlineStatus());
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return null;
		} catch (CantChangeOnlineStateException e) {
			e.printStackTrace();
			return null;
		}
		return ManageJobOffersPage.class;	
    }

	/**
	 * @return the jobOfferGrid
	 */
	public Grid getJobOfferGrid() {
		return jobOfferGrid;
	}


	/**
	 * @param jobOfferGrid the jobOfferGrid to set
	 */
	public void setJobOfferGrid(Grid jobOfferGrid) {
		this.jobOfferGrid = jobOfferGrid;
	}


	/**
	 * @return the jobOfferData
	 */
	public JobImpl getJobOfferData() {
		return jobOfferData;
	}


	/**
	 * @param jobOfferData the jobOfferData to set
	 */
	public void setJobOfferData(JobImpl jobOfferData) {
		this.jobOfferData = jobOfferData;
	}


	/**
	 * @return the jobOfferGridModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getJobOfferGridModel() {
		return jobOfferGridModel;
	}

}
