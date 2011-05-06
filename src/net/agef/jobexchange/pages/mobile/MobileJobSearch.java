package net.agef.jobexchange.pages.mobile;

import java.util.Collection;
import java.util.List;

import net.agef.jobexchange.annotation.InjectSelectionModel;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.Territory;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class MobileJobSearch {
	@Persist
	@Property
	private String term;

	@Persist
	@InjectSelectionModel(labelField = "nameEnglish", idField = "id")
    private List<Territory> territoryList;// = new ArrayList<Territory>();
	
	@Property
    private Territory territoryItem;
	
	@Inject
	private LocationWorker lw;
	
	@Inject
	private Logger logger;
	
	@InjectPage
	private MobileJobListing jobListing;
	
	public void onPrepare(){
    	logger.info("Bin in onPrepare");
    	this.territoryList = lw.getAllTerritories();
    }
	
	Object onSuccess(){
		System.out.println("in onSuccess");
		if(term != null) 
			jobListing.setSearchTerm(term);
		else jobListing.setSearchTerm("");
		jobListing.setTerritory(territoryItem);
		System.out.println("Felder in jobListing Seite gesetzt. \nSuchbegriff: " + term + "\nKontinent: " + territoryItem);
		return jobListing;
	}
}
