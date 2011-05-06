/**
 * 
 */
package net.agef.jobexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;

/**
 * @author mutabor
 *
 */
@Entity
public class SearchHistoryJobs extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2131214029488732809L;
	
	
	private String queryString;
	private Territory queryContinent;
	private Country queryCountry;
	private Integer queryResultAmount;
	
	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}
	/**
	 * @param queryString the queryString to set
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	/**
	 * @return the queryContinent
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	//@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public Territory getQueryContinent() {
		return queryContinent;
	}
	/**
	 * @param queryContinent the queryContinent to set
	 */
	public void setQueryContinent(Territory queryContinent) {
		this.queryContinent = queryContinent;
	}
	/**
	 * @return the queryCountry
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
	//@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(nullable=true)
	public Country getQueryCountry() {
		return queryCountry;
	}
	/**
	 * @param queryCountry the queryCountry to set
	 */
	public void setQueryCountry(Country queryCountry) {
		this.queryCountry = queryCountry;
	}
	/**
	 * @return the queryResultAmount
	 */
	public Integer getQueryResultAmount() {
		return queryResultAmount;
	}
	/**
	 * @param queryResultAmount the queryResultAmount to set
	 */
	public void setQueryResultAmount(Integer queryResultAmount) {
		this.queryResultAmount = queryResultAmount;
	}

}
