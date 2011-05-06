/**
 * 
 */
package net.agef.jobexchange.domain;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="static_currency")
public class Currency extends AbstractEntity{

	
	private static final long serialVersionUID = 2402611442483073441L;

	private Collection<JobImpl> getRelatedJobs = new TreeSet<JobImpl>();
	
	private Long isoNumber;
	private String nameEnglish;

	
	
	/**
	 * @return the getRelatedJobs
	 */
	@OneToMany(mappedBy="currency")
	public Collection<JobImpl> getGetRelatedJobs() {
		return getRelatedJobs;
	}

	/**
	 * @param getRelatedJobs the getRelatedJobs to set
	 */
	public void setGetRelatedJobs(Collection<JobImpl> getRelatedJobs) {
		this.getRelatedJobs = getRelatedJobs;
	}

	

	

	/**
	 * @return the isoNumber
	 */
	@Column(name = "cn_currency_iso_nr")
	public Long getIsoNumber() {
		return isoNumber;
	}

	/**
	 * @param isoNumber the isoNumber to set
	 */
	public void setIsoNumber(Long isoNumber) {
		this.isoNumber = isoNumber;
	}

	/**
	 * @return the nameEnglish
	 */
	@Column(name = "cn_currency_iso_3")
	public String getNameEnglish() {
		return nameEnglish;
	}
	/**
	 * @param nameEnglish the nameEnglish to set
	 */
	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}
		
}
