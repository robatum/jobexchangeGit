/**
 * 
 */
package net.agef.jobexchange.pages.forms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.components.CustomGPlotter;
import net.agef.jobexchange.components.CustomWindow;
import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.PublicationTypeEnum;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.pages.Index;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.queryParser.ParseException;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanModelSource;
import org.slf4j.Logger;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("unused")
public class SearchJobPageBackup {

	@Inject
	private Logger logger;

	@Inject
	private JobWorker jw;
	
	@Inject
	private LocationWorker lw;
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;
	
	
	@Component(id = "beanForm")
    private Form form; 
	
	@Component
    private Grid jobGrid;
	
	@Component(parameters = {"height=600", "width=500", "show=false", "style=alphacube"})
    private CustomWindow detailWindow;
	

	@Inject
    private Block jobDetails;
	
	
	@Property
	@Persist("Flash")
	private String searchField;
	
	private JobImpl jobData;
	
	@Property
	@Persist("Flash")
    private Address selectedAddress;
	
//	@Persist("Flash")
//	private Boolean autoCompleteQuery;
	
	
	private Collection<JobImpl> jobSearchList;
	
	@Persist("Flash")
	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Territory> territoryList;// = new ArrayList<Territory>();
	
	
	
   // private List<Country> countryDummyList = new ArrayList<Country>();
	@Persist
    @InjectSelectionModel(labelField = "shortEnglishName", idField = "id")
	private List<Country> countryList;
	
	
	@Persist
    private Territory territoryItem;
	
	
//	@Property
//	private Country countryDummyItem;
	
	@Persist("Flash")
    private Country countryItem;
    

	
	@SuppressWarnings("unchecked")
	private final BeanModel jobGridModel;
    {
    	jobGridModel = beanModelSource.createDisplayModel(JobImpl.class, messages);
    	jobGridModel.include();
    	jobGridModel.add("results", null);
    	//jobGridModel.exclude("jobOfferId","cobraJobId","commentsRegardingApplicationProcedure","jobOfferExpireDate","organisationName","jobDescription","locationOfEmployment","countryOfEmployment","organisationDescription","numberOfJobs","taskDescription","minimumRequirementsForEducation","furtherCommentsRegardingEducation","desiredProfession","alternativeProfession","workExperience","languageSkillsGerman","languageSkillsEnglish","computerSkills","computerSkillsComments","drivingLicence","specialKnowledge","furtherRequirements","possibleCommencementDate","durationOfContract","weeklyHoursOfWork","salary","currency","miscellaneousServices","preferredPublication","attachmentLocation","furtherComments");
    }
    
    public void onPrepare(){
    	logger.info("Bin in onPrepare");
    	this.territoryList = lw.getAllTerritories();
    	if(this.countryList == null)this.countryList = new ArrayList<Country>();
    }
    
    Boolean onActivate(){
    	logger.info("onActivate1");
    	if(this.territoryItem != null) {
    		try {
				logger.error("Bin im onActivate1 TerritorySelect: "
						+ this.territoryItem.getNameEnglish());
			} catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	else logger.error("Bin im onActivate1 TerritorySelect: ist null");
    	
    	if((this.territoryItem != null && this.searchField == null) || (this.searchField != null && this.searchField.length() > 3) ) {
    		logger.info("CountryItem1 is: "+this.countryItem);
    		this.jobSearchList = jw.getJobOfferByCriteriaGetjobs(this.searchField,countryItem, this.territoryItem);
    		logger.error("Hole Joblist.");
    	} 
    	return true;
    }
    
    Boolean onActivate(String searchString) throws ParseException, IOException {
    	logger.info("onActivate2");
    	this.searchField = searchString;
    	if (this.searchField != null && this.searchField.length() > 3) {
    		logger.error("Suche Normal");
    		logger.info("CountryItem2 is: "+this.countryItem);
    		this.jobSearchList = jw.getJobOfferByCriteriaGetjobs(this.searchField,this.countryItem, this.territoryItem);
    	} 
    	//logger.info("on Activate: checke autocomplete");
    	if(this.territoryItem != null && this.territoryItem instanceof Territory) {
    		this.countryList = lw.getRelatedCountries(this.territoryItem);
    		logger.error("Bin im onActivate2 TerritorySelect: "+ this.territoryItem.getNameEnglish());
    	}
    	else logger.error("Bin im onActivate2 TerritorySelect: ist null");
    	return true;
    }
    

     String onPassivate() {
    	 logger.info("bin in onPassivate");
        return this.searchField;
     }
    
    
    Object onSuccess(){
    	logger.info("onSuccess");
    	//logger.error("Setze mlt false");
    	if(territoryItem!= null || searchField != null){
    		if(searchField != null && searchField.length()<3){
    			form.recordError("Bitte wählen Sie ein Suchkriterium mit mehr als 3 Zeichen.");
    	    	return null;
    		}
    		logger.info("bin in onSuccess und gebe Suchseite zurueck");
    		return SearchJobPageBackup.class;
    		
    	} else {
    		form.recordError("Bitte wählen Sie mindestens ein Suchkriterium aus.");
    		return null;
    	}
	}
    
    
    Block onActionFromDetails(long jobOfferId)
    {
    	logger.info("Looking for job details : jobOfferId");
    	try {
			this.jobData = jw.getJobOfferDetails(jobOfferId);
		} catch (JobOfferNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return jobDetails;
    }
    

    
    
    @OnEvent(component = "territorySelect", value = "change")
    JSONObject territoryElementChanged(String value) {
    	JSONObject json = new JSONObject(); 
    	if(value != null && !value.equals("")){
	    	this.territoryItem = lw.getTerritoryById(new Long(value));
	    	//logger.error("Territory : "+this.territoryItem.getNameEnglish()+"---- value: "+value );
	    	this.countryList = lw.getRelatedCountries(territoryItem);
	    	
	    	String queryString = new String();
	    	Iterator<Country> it = this.countryList.iterator();
			int counter = 0;
	    	while(it.hasNext()){
				Country tempCountry = it.next();
				if (counter == 0) {
					queryString = "[['', ''], ['"+tempCountry.getId()+"', '"+ StringEscapeUtils.escapeJavaScript(tempCountry.getShortEnglishName())+"']";
				} else queryString = queryString+", ['"+tempCountry.getId()+"', '"+StringEscapeUtils.escapeJavaScript(tempCountry.getShortEnglishName())+"']";
				
				//logger.error("QueryString : "+ queryString);
				counter++;
			}
	    	queryString = queryString+"]";
	    	String escapedQueryString = queryString; //StringEscapeUtils.escapeHtml(queryString);
	    	//logger.error("QueryStringGesamt : "+ queryString+ " ---- escaped: "+escapedQueryString);
			json.put("result", new JSONArray(escapedQueryString)); 
    	} else json.put("result", new JSONArray("[]")); 
		return json; 
    } 
    
    @OnEvent(component = "countrySelect", value = "change")
    void countryElementChanged(String value) {
    	if(value != null && !value.equals("")){
    		this.countryItem = lw.getCountryById(new Long(value));
    		System.err.println("Country : "+this.countryItem.getOfficialEnglishName()+"---- value: "+value );
    	}
    }
    
    
    void cleanupRender() {
    	logger.info("CleanUp");
		form.clearErrors();
		// Clear the flash-persisted fields to prevent anomalies in onActivate when we hit refresh on page or browser button
		this.searchField = null;
		
		
		
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
	 * @return the detailWindow
	 */
	public CustomWindow getDetailWindow() {
		return detailWindow;
	}

	/**
	 * @param detailWindow the detailWindow to set
	 */
	public void setDetailWindow(CustomWindow detailWindow) {
		this.detailWindow = detailWindow;
	}



//	/**
//	 * @return the autoCompleteQuery
//	 */
//	public Boolean getAutoCompleteQuery() {
//		return autoCompleteQuery;
//	}
//
//	/**
//	 * @param autoCompleteQuery the autoCompleteQuery to set
//	 */
//	public void setAutoCompleteQuery(Boolean autoCompleteQuery) {
//		this.autoCompleteQuery = autoCompleteQuery;
//	}
//


	/**
	 * @return the territoryItem
	 */
	public Territory getTerritoryItem() {
		return territoryItem;
	}

	/**
	 * @param territoryItem the territoryItem to set
	 */
	public void setTerritoryItem(Territory territoryItem) {
		this.territoryItem = territoryItem;
	}

	/**
	 * @return the countryItem
	 */
	public Country getCountryItem() {
		return countryItem;
	}

	/**
	 * @param countryItem the countryItem to set
	 */
	public void setCountryItem(Country countryItem) {
		this.countryItem = countryItem;
	}

	/**
	 * @return the jobDetails
	 */
	public Block getJobDetails() {
		return jobDetails;
	}

	/**
	 * @param jobDetails the jobDetails to set
	 */
	public void setJobDetails(Block jobDetails) {
		this.jobDetails = jobDetails;
	}

//	/**
//	 * @return the searchField
//	 */
//	public String getSearchField() {
//		return searchField;
//	}
//
//
//	/**
//	 * @param searchField the searchField to set
//	 */
//	public void setSearchField(String searchField) {
//		this.searchField = searchField;
//	}


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
	 * @return the jobSearchList
	 */
	public Collection<JobImpl> getJobSearchList() {
		return jobSearchList;
	}
	/**
	 * @param jobSearchList the jobSearchList to set
	 */
	public void setJobSearchList(Collection<JobImpl> jobSearchList) {
		this.jobSearchList = jobSearchList;
	}



	/**
	 * @return the jobGridModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getJobGridModel() {
		return jobGridModel;
	}
	
    
	public Boolean getJobOfferIsAnonym(){
		if(this.jobData!= null && jobData.getPreferredPublication() != null && jobData.getPreferredPublication().equals(PublicationTypeEnum.ANONYMOUS_WITH_SECTOR_AND_JOB_REFERENCE_NUMBER))
			{return true;}
			else return false;
	}
    
    
}
