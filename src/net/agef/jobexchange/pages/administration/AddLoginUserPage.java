/**
 * 
 */
package net.agef.jobexchange.pages.administration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.LoginUserRole;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.exceptions.LoginUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.integration.LoginUserRoleDAO;
import nu.localhost.tapestry5.springsecurity.services.SaltSourceService;
//import nu.localhost.tapestry.acegi.services.SaltSourceService;
//
//import org.acegisecurity.annotation.Secured;
//import org.acegisecurity.providers.encoding.PasswordEncoder;
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
import org.springframework.security.annotation.Secured;
import org.springframework.security.providers.encoding.PasswordEncoder;

/**
 * @author AGEF
 *
 */
@SuppressWarnings("unused")
@Secured({"ROLE_ADMIN"})
public class AddLoginUserPage {
	@Inject
	private Logger logger;
	
	@Inject
	private LoginUserWorker luw;
	
	@Inject
	private LocationWorker lw;
	
	@Inject
	private LoginUserRoleDAO loginUserRoleDAO;
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;

	@Inject
	private PasswordEncoder passwordEncoder;
    
	@Inject
	private SaltSourceService saltSource;
	
	@Component(id = "beanForm")
	@Property
    private Form form; 
	

	@Component(id="beanEditor")
	@Property
	private BeanEditor beanEditor;
	
	@Component(id="beanEditorUserAddress")
	@Property
	private BeanEditor beanEditorUserAddress;
	
	
	@Persist("Flash")
	@Property
	private LoginUser loginUser;
	
	@Persist("Flash")
	@Property
	private Address loginUserAddress;

	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Territory> territoryList = new ArrayList<Territory>();
	
	
	@InjectSelectionModel(labelField = "shortEnglishName", idField = "id")
    private List<Country> countryDummyList = new ArrayList<Country>();

	@InjectSelectionModel(labelField = "authority", idField = "id")
    private List<LoginUserRole> loginUserRoleList = new ArrayList<LoginUserRole>();
	
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
    private LoginUserRole loginUserRoleItem;
	
	private Boolean isModified = false;
	
	@SuppressWarnings("unchecked")
	private final BeanModel loginUserModel;
    {
    	loginUserModel = beanModelSource.createEditModel(LoginUser.class, messages);
    }
    
    @SuppressWarnings("unchecked")
	private final BeanModel loginUserAddressModel;
    {
    	loginUserAddressModel = beanModelSource.createEditModel(Address.class, messages);
    	loginUserAddressModel.add("loginUserTerritory",null);
    	loginUserAddressModel.reorder("address1","address2","city","federalstate","zipCode","loginUserTerritory");
    }
    
    
    public void onPrepare(){
    	this.territoryList = lw.getAllTerritories(); 
    	this.loginUserRoleList = loginUserRoleDAO.findAll();
    	this.countryList = new ArrayList<Country>();
    }
    
    
    Object onActivate(LoginUser user){
    	this.isModified = true;
    	logger.info("OnActivate - modify");
    	if(luw.isLoggedInUser()){
    		try {
				luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				luw.logoutUser();
			}
    	} else luw.logoutUser();
    	this.loginUser = user;
		if(this.loginUser == null){
			return ManageLoginUserPage.class;
		}
		
		this.loginUserAddress = loginUser.getLoginUserAddress();
		if(this.countryItem==null){
    		this.countryItem = loginUser.getLoginUserAddress().getCountry();
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
    	if(luw.isLoggedInUser()){
    		try {
				luw.getLoggedInUser();
			} catch (LoginUserNotFoundException e) {
				logger.error("Login user Form -- Login User Name could not be found in DB.");
				luw.logoutUser();
			}
    	}
    	
    }
    
    public void onValidateForm() {
        LoginUser anotherUser = null;
		try {
			anotherUser = luw.getUserByName(this.loginUser.getUsername());
		} catch (LoginUserNotFoundException e) {}
        if (anotherUser != null ) {
            form.recordError("User with the name '" + this.loginUser.getUsername() + "' already exists");
        }
    }
	
    
    Object onSuccess(){

    	Collection<LoginUserRole> grantedAuthorities = new HashSet<LoginUserRole>();
    	grantedAuthorities.add(loginUserRoleItem);
		this.loginUser.setGrantedAuthorities(grantedAuthorities);  
		this.loginUser.setLoginUserAddress(this.loginUserAddress);
		if(this.countryItem!=null){
			this.loginUserAddress.setCountry(lw.getCountryById(this.countryItem.getId()));
		} else logger.info("CountryItem is null");
		this.loginUser.setPassword(passwordEncoder.encodePassword(this.loginUser.getPassword(), saltSource.getSalt(this.loginUser)));
		try {
			luw.addLoginUser(loginUser);
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
    
    
    public LoginUser onPassivate(){
		if(isModified){
         return loginUser;
		} else return null;
		
	}
    
    void cleanupRender() {
		form.clearErrors();
		// Clear the flash-persisted fields to prevent anomalies in onActivate when we hit refresh on page or browser button
		this.loginUser = null;
		this.loginUserAddress = null;
		this.territoryItem = null;
		this.countryItem = null;
		this.loginUserRoleItem = null;
    }
    
    
    @OnEvent(component = "territorySelect", value = "change")
    JSONObject territoryElementChanged(String value) {
    	JSONObject json = new JSONObject(); 
    	if(!value.equals("")){
	    	this.territoryItem = lw.getTerritoryById(new Long(value));
	    	this.countryList = lw.getRelatedCountries(territoryItem);
	    	
	    	String queryString = new String();
	    	Iterator<Country> it = this.countryList.iterator();
			int counter = 0;
	    	while(it.hasNext()){
				Country tempCountry = it.next();
				if (counter == 0) {
					queryString = "[['"+tempCountry.getId()+"', '"+tempCountry.getShortEnglishName()+"']";
				} else queryString = queryString+", ['"+tempCountry.getId()+"', '"+tempCountry.getShortEnglishName()+"']";
				
				counter++;
			}
	    	queryString = queryString+"]";
			json.put("result", new JSONArray(queryString)); 
    	} else json.put("result", new JSONArray("[]")); 
		return json; 
    } 
    
    @OnEvent(component = "countrySelect", value = "change")
    void countryElementChanged(String value) {
    	if(!value.equals("")){
    		this.countryItem = lw.getCountryById(new Long(value));
    	}
    }
    
	
	/**
	 * @return the orgUserModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getLoginUserModel() {
		return loginUserModel;
	}
	
	/**
	 * @return the orgUserModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getLoginUserAddressModel() {
		return loginUserAddressModel;
	}
	
}
