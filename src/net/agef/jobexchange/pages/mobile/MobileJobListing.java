package net.agef.jobexchange.pages.mobile;

import java.util.Collection;

import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.Territory;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class MobileJobListing {
	@Inject
	private JobWorker jw;
	
	@Property
	private Collection<JobImpl> jobSearchList;
	
	@Property
	private JobImpl job;
	
	@Property
	private int resultSize;

	private String searchField;
	
	@Persist
    private Territory territoryItem;
	
	public void onActivate(){
		this.jobSearchList = jw.getJobOfferByCriteriaGetjobs(this.searchField,null, this.territoryItem);
		this.resultSize = this.jobSearchList.size();
		System.out.println(this.resultSize + " Ergebnisse gefunden.");
//		job.getJobDescription();
	}
	
	public void setTerritory(Territory territory){
		this.territoryItem = territory;
	}
	
	void setSearchTerm(String term){
		this.searchField = term;
	}
}
