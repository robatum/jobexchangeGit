/**
 * 
 */
package net.agef.jobexchange.pages.administration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.IndustrySector;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.OrganisationRole;
import net.agef.jobexchange.domain.OrganisationRoleData;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.DataProviderNotFoundException;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.integration.IndustrySectorDAO;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.BeanEditor;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanModelSource;
import org.slf4j.Logger;

/**
 * @author AGEF
 *
 */
@SuppressWarnings("unused")
public class AddOrganisationPage {
	@Inject
	private Logger logger;
	
	@Inject
	private UserWorker uw;
	
	@Inject
	private LoginUserWorker luw;
	
	@Inject
	private LocationWorker lw;
	
	@Inject
	private DataProviderWorker dpw;
	
	@Inject
	private IndustrySectorDAO industrySectorDAO;
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;

	
	@Component(id = "beanForm")
	@Property
    private Form form; 
	
	@Component(id="beanEditor")
	@Property
	private BeanEditor beanEditor;
	
	@Component(id="beanEditorUser")
	@Property
	private BeanEditor beanEditorUser;
	
	@Component(id="beanEditorUserAddress")
	@Property
	private BeanEditor beanEditorUserAddress;
	
	@Persist("Flash")
	private LoginUser loginUser;
	
	@Persist("Flash")
	@Property
	private User orgUser;
	
	@Persist("Flash")
	@Property
	private Address orgUserAddress;
	
	@Persist("Flash")
	@Property
	private OrganisationRoleData orgRoleData;
	
	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Territory> territoryList = new ArrayList<Territory>();
	
	
	@InjectSelectionModel(labelField = "shortEnglishName", idField = "id")
    private List<Country> countryDummyList = new ArrayList<Country>();

	
	@InjectSelectionModel(labelField = "sectorNameEnglish", idField = "id")
    private List<IndustrySector> orgIndustrySectorList = new ArrayList<IndustrySector>();
	
	
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
	
	@Property
	@Persist
    private IndustrySector orgIndustrySectorItem;
	
	@Property
	@Persist("Flash")
	private Boolean isModified; // = false
	
	@SuppressWarnings("unchecked")
	private final BeanModel orgDataModel;
    {
    	orgDataModel = beanModelSource.createEditModel(OrganisationRoleData.class, messages);
        orgDataModel.exclude("humanRessourceDevelopment");
        //orgDataModel.reorder("messageRecipient","messageSubject","messageText","attachmentLocation");
    }
    
    @SuppressWarnings("unchecked")
	private final BeanModel orgUserModel;
    {
    	orgUserModel = beanModelSource.createEditModel(User.class, messages);
    	orgUserModel.exclude("dateOfBirth","eMailPrivate","citizenship1","citizenship2","currentAddress");
        //orgDataModel.reorder("messageRecipient","messageSubject","messageText","attachmentLocation");
    }
    
    @SuppressWarnings("unchecked")
	private final BeanModel orgUserAddressModel;
    {
    	orgUserAddressModel = beanModelSource.createEditModel(Address.class, messages);
    	orgUserAddressModel.add("orgUserTerritory",null);
    	//orgUserAddressModel.exclude("dateOfBirth","eMailPrivate","citizenship1","citizenship2","currentAddress");
    	orgUserAddressModel.reorder("address1","address2","city","federalstate","zipCode","orgUserTerritory");
    }
    
    /**
	 * setup page properties before after activation
	 */
    public void onPrepare(){
    	this.territoryList = lw.getAllTerritories();
    	this.orgIndustrySectorList = industrySectorDAO.findAll(); 
    	this.countryList = new ArrayList<Country>();
    }
    
    
    Object onActivate(User user){
    	this.isModified = true;
    	logger.info("OnActivate - modify");
    	if(luw.isLoggedInUser()){
    		try {
				this.loginUser = luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				luw.logoutUser();
			}
    	} else luw.logoutUser();
    	this.orgUser = user;
		if(this.orgUser == null){
			return ManageOrganisationsPage.class;
		}
		
		this.orgRoleData = (OrganisationRoleData) orgUser.getUserRoleData();
		this.orgUserAddress = orgUser.getAddress1();
		this.orgIndustrySectorItem = this.orgRoleData.getIndustrySector();
		if(this.countryItem==null){
    		this.countryItem = orgUser.getAddress1().getCountry();
    		logger.info("Ersetze countryItm:"+this.countryItem+" durch: "+orgUser.getAddress1().getCountry() );
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
    	logger.info("OnActivate - normal ");
    	if(luw.isLoggedInUser()){
    		try {
				this.loginUser = luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				logger.error("AddOrgForm -- Login User Name could not be found in DB.");
				luw.logoutUser();
			}
    	}
    	
		//this.orgUserAddress = new Address(this.orgUser);
		
    }
    
    Object onSuccess(){
    	logger.info("Bin im onSuccess .... ");
    	DataProvider dataProvider;
    	try {
			dataProvider = dpw.getDataProviderByName("GETJOBS");
		} catch (DataProviderNotFoundException e1) {
			e1.printStackTrace();
			form.recordError("Error while retrieving DataProvider.");
        	return null;
		}
		this.orgUser.setRelatedLoginUser(this.loginUser);
		this.orgUser.setUserRole(new OrganisationRole(orgUser));  
		this.orgRoleData.setIndustrySector(orgIndustrySectorItem);
		this.orgUser.setUserRoleData(orgRoleData);
		this.orgUser.setDataProvider(dataProvider);
		this.orgUser.setAddress1(this.orgUserAddress);
		if(this.countryItem!=null){
			logger.error("Schreibe Country : "+this.countryItem.getShortEnglishName());
			this.orgUserAddress.setCountry(lw.getCountryById(this.countryItem.getId()));
		} else logger.info("UUUPSSSSS");
		
		try {
			uw.addUser(orgUser);
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			form.recordError("Error while saving job offer.");
			return null;
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			form.recordError("Error while saving job offer, no Argument passed.");
			return null;
		}
    	return OverviewPage.class;	
    }
    
    
    public User onPassivate(){
		if(isModified!= null && isModified){
         return orgUser;
		} else return null;
		
	}
    
    void cleanupRender() {
		form.clearErrors();
		// Clear the flash-persisted fields to prevent anomalies in onActivate when we hit refresh on page or browser button
		this.orgUser = null;
		this.orgRoleData = null;
		this.orgUserAddress = null;
		this.orgIndustrySectorItem = null;
		this.territoryItem = null;
		this.countryItem = null;
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
    
    /**
	 * @return the orgDataModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getOrgDataModel() {
		return orgDataModel;
	}
	
	/**
	 * @return the orgUserModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getOrgUserModel() {
		return orgUserModel;
	}
	
	/**
	 * @return the orgUserModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getOrgUserAddressModel() {
		return orgUserAddressModel;
	}
}
