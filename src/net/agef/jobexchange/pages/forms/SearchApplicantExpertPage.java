/**
 * 
 */
package net.agef.jobexchange.pages.forms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.application.ApplicantWorker;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.components.CustomWindow;
import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Education;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.WorkExperience;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;
import net.agef.jobexchange.webservice.entities.ApplicantsSearchResultDTO;

import org.apache.lucene.queryParser.ParseException;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.slf4j.Logger;

/**
 * @author Administrator
 *
 */
public class SearchApplicantExpertPage {
	
	@Inject
	private Logger logger;
	
	@Inject
	private ApplicantWorker aw;
	
	@Inject
	private LocationWorker lw;
	
	@Inject 
	private BeanModelSource beanModelSource;
	
	@Inject
    private Messages messages;
	
	@Inject
	@Property
	private Block countryBlock;

	
	@Component(id = "beanForm")
    private Form form; 
	
	@Component
    private Grid applicantGrid;
	
	@Component(parameters = {"height=600", "width=500", "show=false", "style=alphacube"})
    private CustomWindow detailWindow;
	

	@Inject
    private Block applicantDetails;
	
	
	@Property
	@Persist("Flash")
	private String searchField;
	
	private Applicant applicantData;
	
	@SuppressWarnings("unused")
	@Property
	private ApplicantsSearchResultDTO applicantSearchData;
	
	@SuppressWarnings("unused")
	@Property
	private WorkExperience workExperienceItem;
	
	@SuppressWarnings("unused")
	@Property
	private Education educationItem;
	
//	@Property
//	@Persist("Flash")
//    private Address selectedAddress;
	
//	@Persist("Flash")
//	private Boolean autoCompleteQuery;
	
	
	private Collection<ApplicantsSearchResultDTO> applicantSearchList;
	
	@SuppressWarnings("unused")
	@Persist
	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Territory> territoryList;
	
	
	@Persist
    @InjectSelectionModel(labelField = "shortEnglishName", idField = "id")
	private List<Country> countryList;
	
	
	@Persist
    private Territory territoryItem;
	
	
	@Persist
    private Country countryItem;
    

	
	@SuppressWarnings("unchecked")
	private final BeanModel applicantGridModel;
    {
    	applicantGridModel = beanModelSource.createDisplayModel(ApplicantsSearchResultDTO.class, messages);
    	applicantGridModel.include();
    	applicantGridModel.add("Results", null);
    	//applicantGridModel.exclude("id","languageSkillsGerman", "languageSkillsEnglish");
    	//"__HSearch_Score",
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
				System.err.println("Bin im onActivate1 TerritorySelect: "
						+ this.territoryItem.getNameEnglish());
			} catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	else logger.error("Bin im onActivate1 TerritorySelect: ist null");
    	
    	if((this.territoryItem != null && this.searchField == null) || (this.searchField != null && this.searchField.length() > 3) ) {
    		logger.info("CountryItem1 is: "+this.countryItem);
    		this.applicantSearchList = aw.getApplicantByCriteria(this.searchField,countryItem, this.territoryItem,null,null,true);
    		logger.error("Hole Applicantlist.");
    	} 
    	return true;
    }
    
    Boolean onActivate(String searchString) throws ParseException, IOException {
    	logger.info("onActivate2");
    	this.searchField = searchString;
    	if (this.searchField != null && this.searchField.length() > 3) {
    		logger.error("Suche Normal");
    		logger.info("CountryItem2 is: "+this.countryItem);
    		this.applicantSearchList = aw.getApplicantByCriteria(this.searchField,this.countryItem, this.territoryItem,null,null,true);
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
    		return SearchApplicantExpertPage.class;
    		
    	} else {
    		form.recordError("Bitte wählen Sie mindestens ein Suchkriterium aus.");
    		return null;
    	}
	}
    
    
    Block onActionFromDetails(long applicantId)
    {
    	logger.info("Looking for applicant details :"+ applicantId);
    	
			try {
				this.applicantData = aw.getApplicantDataByProfileId(applicantId);
			} catch (ApplicantProfileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return applicantDetails;
    }
    
    public Object onValueChanged(final Territory territory) 
    {
    	if(territory != null){
    		this.territoryItem = lw.getTerritoryById(territory.getId());
        	logger.error("Territory : "+territory.getNameEnglish());
        	this.countryList = lw.getRelatedCountries(territoryItem);
    	}
       
       return this.countryBlock;
    }
    

    
    
//    @OnEvent(component = "territorySelect", value = "change")
//    JSONObject territoryElementChanged(String value) {
//    	JSONObject json = new JSONObject(); 
//    	if(value != null && !value.equals("")){
//	    	this.territoryItem = lw.getTerritoryById(new Long(value));
//	    	//logger.error("Territory : "+this.territoryItem.getNameEnglish()+"---- value: "+value );
//	    	this.countryList = lw.getRelatedCountries(territoryItem);
//	    	
//	    	String queryString = new String();
//	    	Iterator<Country> it = this.countryList.iterator();
//			int counter = 0;
//	    	while(it.hasNext()){
//				Country tempCountry = it.next();
//				if (counter == 0) {
//					queryString = "[['', ''], ['"+tempCountry.getId()+"', '"+ StringEscapeUtils.escapeJavaScript(tempCountry.getShortEnglishName())+"']";
//				} else queryString = queryString+", ['"+tempCountry.getId()+"', '"+StringEscapeUtils.escapeJavaScript(tempCountry.getShortEnglishName())+"']";
//				
//				//logger.error("QueryString : "+ queryString);
//				counter++;
//			}
//	    	queryString = queryString+"]";
//	    	String escapedQueryString = queryString; //StringEscapeUtils.escapeHtml(queryString);
//	    	//logger.error("QueryStringGesamt : "+ queryString+ " ---- escaped: "+escapedQueryString);
//			json.put("result", new JSONArray(escapedQueryString)); 
//    	} else json.put("result", new JSONArray("[]")); 
//		return json; 
//    } 
//    
//    @OnEvent(component = "countrySelect", value = "change")
//    void countryElementChanged(String value) {
//    	if(value != null && !value.equals("")){
//    		this.countryItem = lw.getCountryById(new Long(value));
//    		logger.error("Country : "+this.countryItem.getOfficialEnglishName()+"---- value: "+value );
//    	}
//    }
    
    
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
	 * @return the ApplicantGrid
	 */
	public Grid getApplicantGrid() {
		return applicantGrid;
	}
	/**
	 * @param ApplicantGrid the ApplicantGrid to set
	 */
	public void setApplicantGrid(Grid applicantGrid) {
		this.applicantGrid = applicantGrid;
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
	 * @return the applicantDetails
	 */
	public Block getApplicantDetails() {
		return applicantDetails;
	}

	/**
	 * @param applicantDetails the applicantDetails to set
	 */
	public void setApplicantDetails(Block applicantDetails) {
		this.applicantDetails = applicantDetails;
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
	public Applicant getApplicantData() {
		return applicantData;
	}
	/**
	 * @param jobData the jobData to set
	 */
	public void setApplicantData(Applicant applicantData) {
		this.applicantData = applicantData;
	}

	/**
	 * @return the jobSearchList
	 */
	public Collection<ApplicantsSearchResultDTO> getApplicantSearchList() {
		return applicantSearchList;
	}
	/**
	 * @param jobSearchList the jobSearchList to set
	 */
	public void setJobSearchList(Collection<ApplicantsSearchResultDTO> applicantSearchList) {
		this.applicantSearchList = applicantSearchList;
	}

	/**
	 * @return the jobGridModel
	 */
	@SuppressWarnings("unchecked")
	public BeanModel getApplicantGridModel() {
		return applicantGridModel;
	}
	
    
	@SuppressWarnings("unchecked")
	public String getEnumLabel(Enum enumValue){
		if(enumValue != null)
			return org.apache.tapestry5.internal.TapestryInternalUtils.getLabelForEnum(messages, enumValue); 
		return "";
	}
    
	public String getApplicantProfileId() {
		if(applicantData!= null){
			if(applicantData.getApplicantProfileOwner() !=null && applicantData.getApplicantProfileOwner().getCobraSuperId()!=null){
				return applicantData.getApplicantProfileOwner().getCobraSuperId().toString();
			} else return "APD".concat(applicantData.getApplicantProfileId().toString());
		} else return "0";
	}

}
