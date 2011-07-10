package net.agef.jobexchange.pages.administration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.FieldOfOccupationWorker;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.AbstractUserRoleData;
import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.ContactPerson;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Currency;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.LoginUserRole;
import net.agef.jobexchange.domain.OccupationalField;
import net.agef.jobexchange.domain.OrganisationRole;
import net.agef.jobexchange.domain.OrganisationRoleData;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.DataProviderNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.OccupationalFieldNotFoundException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.integration.CurrencyDAO;
import net.agef.jobexchange.integration.JobDAO;
import net.agef.jobexchange.integration.OccupationalFieldDAO;
import net.agef.jobexchange.services.recaptcha.ReCaptcha;
import net.agef.jobexchange.services.recaptcha.ReCaptchaResponse;

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
import org.apache.tapestry5.hibernate.HibernateGridDataSource;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;

/**
 * Start page of application app.
 */
@SuppressWarnings("unused")
public class AddJobOfferPage
{
	@Inject
	private Logger logger;
	
	@Inject 
	private Session session;
	
	@Inject
	private JobWorker jw;
	
	@Inject
	private UserWorker uw;
	
	@Inject
	private LocationWorker lw;
	
	@Inject
	private LoginUserWorker luw;
	
	@Inject
	private FieldOfOccupationWorker foOW;
	
	@Inject
	private CurrencyDAO currencyDAO;
	
	@Inject
	private JobDAO jobDAO;
	
	@Inject
	private DataProviderWorker dpw;
	
	/*@Inject
	private HttpServletRequest request;*/
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;
	
	@Property
	@Component(id = "beanForm")
    private Form form; 
	
	@Property
	@Component(id="beanEditor")
	private BeanEditor beanEditor;
	
	//@Property
	//@Persist("Flash")
	private LoginUser loginUser;
	
	
	@Property
	@Persist("Flash")
	private JobImpl jobOffer;
	
	
	@Persist("Flash")
	//@Property
	private User jobOwner;
	

	
	@Persist("Flash")
	//@Property
	private ContactPerson contactPerson;

	private Collection<JobImpl> jobSearchList;
	
	
	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Territory> territoryList = new ArrayList<Territory>();
	
	//@Persist("Flash")
	@InjectSelectionModel(labelField = "shortEnglishName", idField = "id")
    private List<Country> countryDummyList;
	
	@InjectSelectionModel(labelField = "organisationName", idField = "owner.id")
    private List<OrganisationRoleData> orgUserDataList = new ArrayList<OrganisationRoleData>();
	
	@InjectSelectionModel(labelField = "toString", idField = "intValue")
    private List<Integer> jobAmountList = new ArrayList<Integer>();

	@InjectSelectionModel(labelField = "fieldNameEnglish", idField = "id")
    private List<OccupationalField> occupationalFieldList = new ArrayList<OccupationalField>();

	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Currency> salaryCurrencyList = new ArrayList<Currency>();

	@Persist
	private List<Country> countryList;

	
	@Property
	@Persist("Flash")
    private Territory territoryItem;
	
	@Property
    //@Persist
	private Country countryDummyItem;
	
	//@Property
	@Persist
    private Country countryItem;
	
	@Property
    @Persist("Flash")
    private OrganisationRoleData orgUserDataItem;
	
	@Property
    @Persist("Flash")
	private Integer jobAmountItem;
	
	@Property
	@Persist("Flash")
	private OccupationalField occupationalFieldItem;
	
	@Property
	private Currency salaryCurrencyItem;
	
	
	@Property
	@Persist("Flash")
	private Boolean modified;
	
	@SuppressWarnings("unchecked")
	private final BeanModel jobOfferModel;
    {
    	jobOfferModel = beanModelSource.createEditModel(JobImpl.class, messages);
    	jobOfferModel.exclude("jobOfferId","organisationName","attachmentLocation");
    	jobOfferModel.add("salaryCurrency",null);
    	jobOfferModel.add("territoryOfEmployment",null);
    	jobOfferModel.add("experiencesAndKnowledge",null);
    	jobOfferModel.reorder("numberOfJobs","jobDescription","taskDescription","territoryOfEmployment","locationOfEmployment","minimumRequirementsForEducation","furtherCommentsRegardingEducation","desiredProfession","alternativeprofession","workExperience","experiencesAndKnowledge","languageSkillsGerman","languageSkillsEnglish","computerSkills","computerSkillsComments","drivingLicence","specialKnowledge","furtherRequirements","possibleCommencementDate","durationOfContract","weeklyHoursOfWork","salary","salaryCurrency","miscellaneousServices","commentsRegardingApplicationProcedure","applicationExpireDate","preferredPublication","jobOfferExpireDate","furtherComments");
    }
    

    /**
	 * setup page properties before after activation
	 */
	
    public void onPrepare(){
    	
    	this.jobAmountList = new ArrayList<Integer>();
    	for(int i = 1;i<=25;i++){
    		jobAmountList.add(new Integer(i));
    	}
    	try {
			this.occupationalFieldList = (List<OccupationalField>) foOW.getOccupationalMainFields();
		} catch (OccupationalFieldNotFoundException e) {
			form.recordError("Error while initializing Occupational field list. Please contact your administrator.");
			e.printStackTrace();
		}
    	this.salaryCurrencyList = currencyDAO.findAll();
    	//this.loginUser = null;
    	this.countryList = new ArrayList<Country>();
    	this.countryDummyList = new ArrayList<Country>();
    }

    
    Object onActivate(JobImpl jobOffer){
    	this.modified = true;
    	logger.info("OnActivate from JobOffer");
    	this.territoryList = lw.getAllTerritories();
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
    		Criteria criteria = session.createCriteria(OrganisationRoleData.class);
    		criteria.addOrder(Order.asc("organisationName"));
//    		ProjectionList proList = Projections.projectionList();
//            proList.add(Projections.property("organisationName"));
//            proList.add(Projections.property("owner"),"id");
//            criteria.setProjection(proList);
    		this.orgUserDataList = criteria.list();
    	} else try {
			this.orgUserDataList = uw.getOrganisationUserDataByLoginUser(this.loginUser);
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			luw.logoutUser();
		}
    	this.jobOffer = jobOffer;
		if(this.jobOffer == null){
			return ManageJobOffersPage.class;
		}
		
		this.orgUserDataItem = (OrganisationRoleData) jobOffer.getJobOfferOwner().getUserRoleData();
    	this.jobAmountItem = jobOffer.getNumberOfJobs();
    	this.salaryCurrencyItem = jobOffer.getCurrency();
    	this.occupationalFieldItem = jobOffer.getOccupationalField();
    	if(this.countryItem==null){
    		logger.info("Setze CountryItem auf "+jobOffer.getCountryOfEmployment().getShortEnglishName() );
	    	this.countryItem = jobOffer.getCountryOfEmployment();
	    }

    	if(this.countryItem!=null){
    		this.territoryItem = this.countryItem.getParentTerritory();
    		this.countryList = lw.getRelatedCountries(this.territoryItem);
    		this.countryDummyList = this.countryList;
    		this.countryDummyItem = this.countryItem;
    	}
    	return true;
    }
    
    
    
    void onActivate(){
    	logger.info("OnActivate");
    	this.territoryList = lw.getAllTerritories();
    	if(luw.isLoggedInUser()){
    		try {
				this.loginUser = luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				luw.logoutUser();
			}
    	} else luw.logoutUser();
    	try {
			this.orgUserDataList = uw.getOrganisationUserDataByLoginUser(this.loginUser);
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			luw.logoutUser();
		}
//		if(this.territoryItem!=null){
//    		this.countryList = lw.getRelatedCountries(this.territoryItem);
//    		this.countryDummyList = this.countryList;
//    	}
		if(this.loginUser!=null && this.loginUser.getLoginUserAddress()!=null){
			if(this.countryItem==null){
				this.countryItem=this.loginUser.getLoginUserAddress().getCountry();
			}
			logger.info("CountryItem: "+countryItem.getShortEnglishName());
    		this.territoryItem = this.countryItem.getParentTerritory();
    		this.countryList = lw.getRelatedCountries(this.territoryItem);
    		this.countryDummyList = this.countryList;
    		this.countryDummyItem = this.countryItem;
    	} else logger.info("CountryItem ist null");
    	
	}
	

	Object onSuccess(){
		logger.info("Bin im onSuccess");
			
			if(orgUserDataItem!=null){
				this.jobOwner = orgUserDataItem.getOwner();
			} else {
				form.recordError("Please provide the Organisation the job offer belongs to.");
	        	return null;
			}

			if (this.countryItem != null) logger.error("Current countryItem: "+this.countryItem.getOfficialEnglishName());
				else logger.error("Current countryItem ist null"); 
			if (this.territoryItem != null) logger.error("Current territoryItem: "+this.territoryItem.getNameEnglish());
			else logger.error("Current territoryItem ist null"); 
			
			
			if(this.loginUser!= null){
				jobOffer.setGetjobsLoginUser(this.loginUser);
			}
			jobOffer.setNumberOfJobs(this.jobAmountItem);
			jobOffer.setOccupationalField(this.occupationalFieldItem);
			if(this.countryItem!=null){
				jobOffer.setCountryOfEmployment(lw.getCountryById(this.countryItem.getId()));
			} else {
				form.recordError("Please select a value for 'Country of employment'.");
	        	return null;
			}
			jobOffer.setCurrency(this.salaryCurrencyItem);
			jobOffer.setJobOfferOwner(this.jobOwner);
			DataProvider dataProvider;
			try {
				dataProvider = dpw.getDataProviderByName("GETJOBS");
			} catch (DataProviderNotFoundException e1) {
				e1.printStackTrace();
				form.recordError("Error while retrieving DataProvider.");
	        	return null;
			}
			jobOffer.setDataProvider(dataProvider);
			try {
				jw.addJobOffer(jobOffer);
			} catch (ObjectNotSavedException e) {
				e.printStackTrace();
				form.recordError("Error while saving job offer.");
				return null;
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
				form.recordError("Error while saving job offer, no attribute passed.");
				return null;
			}
		
		
		
		return OverviewPage.class;	
	}
	
	public JobImpl onPassivate(){
		if(modified!=null && modified){
         return jobOffer;
		} else return null;
	}

       
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
					queryString = "[['"+tempCountry.getId()+"', '"+tempCountry.getShortEnglishName()+"']";
				} else queryString = queryString+", ['"+tempCountry.getId()+"', '"+tempCountry.getShortEnglishName()+"']";
				
				//logger.error("QueryString : "+ queryString);
				counter++;
			}
	    	queryString = queryString+"]";
	    	//logger.error("QueryStringGesamt : "+ queryString);
			json.put("result", new JSONArray(queryString)); 
    	} else json.put("result", new JSONArray("[]")); 
		return json; 
    } 
    
    @OnEvent(component = "countrySelect", value = "change")
    void countryElementChanged(String value) {
    	if(!value.equals("")){
    		this.countryItem = lw.getCountryById(new Long(value));
    		logger.error("Country : "+this.countryItem.getShortEnglishName()+"---- value: "+value );
    	}
    }

    
    void cleanupRender() {
		form.clearErrors();
		// Clear the flash-persisted fields to prevent anomalies in onActivate when we hit refresh on page or browser button
		logger.info("Bin Im cleanupRender.");
		this.jobOffer = null;
		this.orgUserDataItem = null;
		this.occupationalFieldItem = null;
		this.jobAmountItem = null;
		this.jobOwner = null;
		this.countryItem = null;
		this.territoryItem = null;
		this.salaryCurrencyItem = null;
	}
	
	public Date getCurrentTime() 
	{ 
		return new Date(); 
		
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
	 * @return the _model
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getJobOfferModel() {
		return jobOfferModel;
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
	

	
	
	public Address getCurrentContactAddress(){
		return this.jobOwner.getCurrentContactAddress();
	}


		
}