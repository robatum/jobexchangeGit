package net.agef.jobexchange.pages.forms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.AbstractUserRoleData;
import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.ContactPerson;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.OrganisationRole;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.DataProviderNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.services.recaptcha.ReCaptcha;
import net.agef.jobexchange.services.recaptcha.ReCaptchaResponse;
import net.agef.jobexchange.webservice.entities.JobSearchResultDTO;

import org.apache.lucene.queryParser.ParseException;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.BeanEditor;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanModelSource;
import org.slf4j.Logger;





/**
 * Start page of application app.
 */
@SuppressWarnings("unused")
public class AddJobPage
{
	@Inject
	private Logger logger;
	
	@Inject
	private JobWorker jw;
	
	@Inject
	private UserWorker uw;
	
	@Inject
	private LocationWorker lw;
	
	@Inject
	private LoginUserWorker luw;
	
	@Inject
	private DataProviderWorker dpw;
	
	@Inject
	private ReCaptcha recap;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;

	
//	@Inject
//    private Block countryBlock;
	
	@Component(id = "beanForm")
    private Form form; 
	
	@Component(id="beanEditor")
	private BeanEditor beanEditor;
	
	@Component
    private Grid jobGrid;
	
	@Persist("Flash")
	private JobImpl jobOffer;
	
	private JobImpl jobData;
	
	@Persist("Flash")
	private User jobOwner;
	
	@Persist("Flash")
	private LoginUser loginUser;
	
	@Persist("Flash")
	@Property
	private ContactPerson contactPerson;
	
	private String reCaptchaHTML;
	
	@Persist
    private boolean addAddress;
	
	@Persist
	@Property
	private AbstractUserRoleData userRoleData;

	private Collection<JobImpl> jobSearchList;
	
	
	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Territory> territoryList = new ArrayList<Territory>();
	
	
	@InjectSelectionModel(labelField = "officialEnglishName", idField = "id")
    private List<Country> countryDummyList = new ArrayList<Country>();
	
	@Persist
	private List<Country> countryList;
	
	@Property
	@Persist("Flash")
    private Territory territoryItem;
	
	@Property
	private Country countryDummyItem;
	
	@Property
	@Persist
    private Country countryItem;
	
	@SuppressWarnings("unchecked")
	private final BeanModel model;
    {
        model = beanModelSource.createEditModel(JobImpl.class, messages);
        //_model.add("delete");
        //_model.exclude("organisationDescription","numberOfJobs","taskDescription","minimumRequirementsForEducation","furtherCommentsRegardingEducation","desiredProfession","alternativeProfession","workExperience","languageSkillsGerman","languageSkillsEnglish","computerSkills","computerSkillsComments","drivingLicence","specialKnowledge","furtherRequirements","possibleCommencementDate","durationOfContract","weeklyHoursOfWork","salary","currency","miscellaneousServices","preferredPublication","attachmentLocation","furtherComments");
        //_model.reorder("messageRecipient","messageSubject","messageText","attachmentLocation");
    }
    
    @SuppressWarnings("unchecked")
	private final BeanModel jobGridModel;
    {
    	jobGridModel = beanModelSource.createDisplayModel(JobSearchResultDTO.class, messages);
    	
    	//jobGridModel.exclude("organisationDescription","numberOfJobs","taskDescription","minimumRequirementsForEducation","furtherCommentsRegardingEducation","desiredProfession","alternativeProfession","workExperience","languageSkillsGerman","languageSkillsEnglish","computerSkills","computerSkillsComments","drivingLicence","specialKnowledge","furtherRequirements","possibleCommencementDate","durationOfContract","weeklyHoursOfWork","salary","currency","miscellaneousServices","preferredPublication","attachmentLocation","furtherComments");
    	jobGridModel.exclude("hSearch_Score");
    	jobGridModel.include("jobOfferId","jobDescription");
    	jobGridModel.add("delete", null);
    }
    
	@SuppressWarnings("unchecked")
	private BeanModel userRoleDataModel;
    
    
    public Collection<JobSearchResultDTO> getJobList() { 
		//return this.jobSearchList;//
		return jw.getJobOfferByCriteria("Manager", null, null, 5, null);
	}
    
//    private List<Country> getCountries() {
//    	if(brands == null) { brands = new ArrayList<Brand>(); brands.add(new Brand("1", "Brand 1")); 
//    }
//    
//    public SelectModel getCountryModel()
//    {
//        return new SelectModelImpl(); 
//    }

    
    /**
	 * setup page properties before after activation
	 */
	
    public void onPrepare(){
    	this.territoryList = lw.getAllTerritories();
    	this.countryList = new ArrayList<Country>();
    }

    
    void onActivate(){
    	logger.info("OnActivate");
    	if(luw.isLoggedInUser()){
    		try {
				this.loginUser = luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				luw.logoutUser();
			}
    	} 
		this.jobOwner = new User();
		this.jobOwner.setUserRole(new OrganisationRole(jobOwner));
		userRoleData = jobOwner.getUserRoleData();
		userRoleDataModel = beanModelSource.createDisplayModel(userRoleData.getClass(), messages);
    	
		
		
		Collection<JobImpl> jobOffers = jw.getJobOfferByCriteria("Entwick*", null, null);
		this.jobSearchList = jobOffers;
		logger.info("Search Results: "+jobOffers.size());
		
		
		
	}
	
    @CommitAfter
	Object onSuccess(){
		//ReCaptchaResponse response = recap.checkAnswer(this.request.getRemoteAddr(), this.request.getParameter("recaptcha_challenge_field"), this.request.getParameter("recaptcha_response_field"));
        logger.info("Bin im onValidate");
		//if (response.isValid()) {
			//jobOwner.setCurrentContactAddress(contactAddress);
			if (this.countryItem != null) logger.error("Current countryItem: "+this.countryItem.getOfficialEnglishName());
				else logger.error("Current countryItem ist null"); 
			if (this.territoryItem != null) logger.error("Current territoryItem: "+this.territoryItem.getNameEnglish());
			else logger.error("Current territoryItem ist null"); 
			
			
			//jobOffer.setContactPerson(this.contactPerson);
			//jobOffer.setCountryOfEmployment(this.countryItem);
			
			if(this.loginUser!= null){
				jobOffer.setGetjobsLoginUser(this.loginUser);
			}
			try {
				uw.addUser(jobOwner);
			} catch (ObjectNotSavedException e2) {
				e2.printStackTrace();
				form.recordError("Error while adding User.");
	        	return null;
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
				form.recordError("Error while adding User, passed attribute is null.");
	        	return null;
			}
			jobOffer.setJobOfferOwner(this.jobOwner);
			try {
				DataProvider dataProvider = dpw.getDataProviderByName("GETJOBS");
			} catch (DataProviderNotFoundException e1) {
				e1.printStackTrace();
				form.recordError("Error while retrieving DataProvider.");
	        	return null;
			}
			try {
				jw.addJobOffer(jobOffer);
			} catch (ObjectNotSavedException e) {
				e.printStackTrace();
				form.recordError("Error while saving job offer.");
				return null;
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
				form.recordError("Error while saving job offer, no argument passed.");
				return null;
			}
//		}
//        else {
//        	form.recordError("The captcha you entered was wrong, please try again.");
//        	return null;
//        }
		
		
		return AddJobPage.class;	
	}
    
    Object onActionFromDelete(long jobOfferId)
    {
        try {
			jw.deleteJobOffer(jobOfferId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return AddJobPage.class;	
    }
    
//    @OnEvent(component = "territoryItem", value = "change")
//    public Block onChange(String value)
//    {
//    	this.countryList = lw.getRelatedCountries(territoryItem);
//        return countryBlock;
//    }
    
    
    @OnEvent(component = "territorySelect", value = "change")
    JSONObject territoryElementChanged(String value) {
    	JSONObject json = new JSONObject(); 
    	if(!value.equals("")){
	    	this.territoryItem = lw.getTerritoryById(new Long(value));
	    	logger.error("Territory : "+this.territoryItem.getNameEnglish()+"---- value: "+value );
	    	this.countryList = lw.getRelatedCountries(territoryItem);
	    	
	    	String queryString = new String();
	    	Iterator<Country> it = this.countryList.iterator();
			int counter = 0;
	    	while(it.hasNext()){
				Country tempCountry = it.next();
				if (counter == 0) {
					queryString = "[['"+tempCountry.getId()+"', '"+tempCountry.getOfficialEnglishName()+"']";
				} else queryString = queryString+", ['"+tempCountry.getId()+"', '"+tempCountry.getOfficialEnglishName()+"']";
				
				logger.error("QueryString : "+ queryString);
				counter++;
			}
	    	queryString = queryString+"]";
	    	logger.error("QueryStringGesamt : "+ queryString);
			json.put("result", new JSONArray(queryString)); 
    	} else json.put("result", new JSONArray("[]")); 
		return json; 
    } 
    
    @OnEvent(component = "countrySelect", value = "change")
    void countryElementChanged(String value) {
    	if(!value.equals("")){
    		this.countryItem = lw.getCountryById(new Long(value));
    		logger.error("Country : "+this.countryItem.getOfficialEnglishName()+"---- value: "+value );
    	}
    }

    
    void cleanupRender() {
		form.clearErrors();
		// Clear the flash-persisted fields to prevent anomalies in onActivate when we hit refresh on page or browser button
		this.jobOffer = null;
	}
	
	public Date getCurrentTime() 
	{ 
		return new Date(); 
		
	}

	/**
	 * @return the form
	 */
	public Form getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(Form form) {
		this.form = form;
	}

	/**
	 * @return the beanEditor
	 */
	public BeanEditor getBeanEditor() {
		return beanEditor;
	}

	/**
	 * @param beanEditor the beanEditor to set
	 */
	public void setBeanEditor(BeanEditor beanEditor) {
		this.beanEditor = beanEditor;
	}

	/**
	 * @return the beanModelSource
	 */
	public BeanModelSource getBeanModelSource() {
		return beanModelSource;
	}

	/**
	 * @param beanModelSource the beanModelSource to set
	 */
	public void setBeanModelSource(BeanModelSource beanModelSource) {
		this.beanModelSource = beanModelSource;
	}

	/**
	 * @return the jobOffer
	 */
	public JobImpl getJobOffer() {
		return jobOffer;
	}

	/**
	 * @param jobOffer the jobOffer to set
	 */
	public void setJobOffer(JobImpl jobOffer) {
		this.jobOffer = jobOffer;
	}

	/**
	 * @return the _model
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getModel() {
		return model;
	}


	/**
	 * @return the jobGridModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getJobGridModel() {
		return jobGridModel;
	}
	
	/**
	 * @return the userRoleDataModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getUserRoleDataModel() {
		return userRoleDataModel;
	}

	/**
	 * @param userRoleDataModel the userRoleDataModel to set
	 */
	@SuppressWarnings("unchecked")
	public void setUserRoleDataModel(BeanModel userRoleDataModel) {
		this.userRoleDataModel = userRoleDataModel;
	}

	/**
	 * @return the jobGrid
	 */
	public Grid getJobGrid() {
		return jobGrid;
	}


	/**
	 * @param jobGrid the jobGrid to set
	 */
	public void setJobGrid(Grid jobGrid) {
		this.jobGrid = jobGrid;
	}


	/**
	 * @return the jobData
	 */
	public JobImpl getJobData() {
		return jobData;
	}


	/**
	 * @param jobData the jobData to set
	 */
	public void setJobData(JobImpl jobData) {
		this.jobData = jobData;
	}

	/**
	 * @return the jobOwner
	 */
	public User getJobOwner() {
		return jobOwner;
	}

	/**
	 * @param jobOwner the jobOwner to set
	 */
	public void setJobOwner(User jobOwner) {
		this.jobOwner = jobOwner;
	}

	/**
	 * @return the addAddress
	 */
	public boolean isAddAddress() {
		return addAddress;
	}

	/**
	 * @param addAddress the addAddress to set
	 */
	public void setAddAddress(boolean addAddress) {
		this.addAddress = addAddress;
	}
	
	
	/**
	 * @return the territoryList
	 */
	@Cached
	public List<Territory> getTerritoryList() {
		return territoryList;
	}

	/**
	 * @param territoryList the territoryList to set
	 */
	public void setTerritoryList(List<Territory> territoryList) {
		this.territoryList = territoryList;
	}

//	/**
//	 * @return the territoryItem
//	 */
//	public Territory getTerritoryItem() {
//		return territoryItem;
//	}
//
//	/**
//	 * @param territoryItem the territoryItem to set
//	 */
//	public void setTerritoryItem(Territory territoryItem) {
//		this.territoryItem = territoryItem;
//	}

	/**
	 * @return the addAddress
	 */
	public Address getContactAddress() {
		return jobOffer.getCurrentContactAddress();
	}

	/**
	 * @param addAddress the addAddress to set
	 */
	public void setContactAddress(Address contactAddress) {
		this.jobOffer.setCurrentContactAddress(contactAddress);
	}
	

	/**
	 * @return the reCaptchaHTML
	 */
	public String getReCaptchaHTML() {
		return recap.createRecaptchaHtml(null, "clean", null);
	}

	/**
	 * @param reCaptchaHTML the reCaptchaHTML to set
	 */
	public void setReCaptchaHTML(String reCaptchaHTML) {
		this.reCaptchaHTML = reCaptchaHTML;
	}
	
	public Address getCurrentContactAddress(){
		return this.jobOwner.getCurrentContactAddress();
	}


		
}