/**
 * 
 */
package net.agef.jobexchange.webservice.entities;


/**
 * 
 * 
 * Die Klasse JobSearchResultDTO ist eine auf die Anforderungen von Suchanfragen reduzierte
 * Domain Klasse und haelt Getter und Setter-Methoden
 * zum Abfragen/ Setzen von stellenrelevanten Daten im Kontext der Jobboerse bereit.
 * 
 * @author Andreas Pursian
 * 
 */
public class JobSearchResultDTO {

	/**
	 * 
	 */
	public JobSearchResultDTO() {
	}
	
	private float hSearch_Score;
	private Long id;
	private Long jobOfferId;
	private Integer countryOfEmploymentId;
	private String organisationIndustrySectorId;
	private String jobDescription;
	private CountryDTO countryOfEmployment;


	
	
	/**
	 * Liefert die Id des aktuellen Stellenabgebotes. 
	 * 
	 * @return the Id
	 * 
	 */
	public long getId() {
		return id+23;
	}
	/**
	 * Setzt die Id des aktuellen Stellenabgebotes. 
	 * 
	 * @param Id the jobOfferId to set
	 * 
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Liefert die JobOfferId des aktuellen Stellenabgebotes. 
	 * 
	 * @return the jobOfferId
	 * 
	 */
	public long getJobOfferId() {
		return id+23;
	}
	/**
	 * Setzt die JobOfferId des aktuellen Stellenabgebotes. 
	 * 
	 * @param jobOfferId the jobOfferId to set
	 * 
	 */
	public void setJobOfferId(long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}



	/**
	 * @return the organisationIndustrySector
	 */
	public String getOrganisationIndustrySectorId() {
		return organisationIndustrySectorId;
	}

	
	/**
	 * @param organisationIndustrySector the organisationIndustrySector to set
	 */
	public void setOrganisationIndustrySectorId(String organisationIndustrySectorId) {
		this.organisationIndustrySectorId = organisationIndustrySectorId;
	}
	
	/**
	 * @return the countryOfEmploymentId
	 */
	public Integer getCountryOfEmploymentId() {
		return countryOfEmploymentId;
	}
	/**
	 * @param countryOfEmploymentId the countryOfEmploymentId to set
	 */
	public void setCountryOfEmploymentId(Integer countryOfEmploymentId) {
		this.countryOfEmploymentId = countryOfEmploymentId;
	}

	
	/**
	 * Liefert die Stellenbezeichnung (Kurztext) zurück.
	 * 
	 * @return the jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}
	/**
	 * @param jobDescription the jobDescription to set
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	
	


	/**
	 * Liefert ein Objekt der Klasse CountryDTO welches den Namen des Landes in dem das Stellenangebot
	 * ausgeschrieben wurde enthaelt.
	 * 
	 * @return the countryOfEmployment
	 * 
	 */
	public CountryDTO getCountryOfEmployment() {
		if (countryOfEmploymentId!=null)
		return new CountryDTO(countryOfEmploymentId.toString());
			else return null;
	}
	/**
	 * Erwartet ein Objekt der Klasse CountryDTO, welches den Namen oder die ISO Nummer des Landes in dem das Stellenangebot
	 * ausgeschrieben wurde enthaelt.
	 * 
	 * @param countryOfEmployment the countryOfEmployment to set
	 * 
	 */
	public void setCountryOfEmployment(CountryDTO countryOfEmployment) {
		this.countryOfEmployment = countryOfEmployment;
	}
	/**
	 * @param score the score to set
	 */
	public void setHSearch_Score(float _HSearch_Score) {
		this.hSearch_Score = _HSearch_Score;
	}
	// die gleiche Methode wie zuvor, wird in dieser Schreibweise von Lucene benoetigt.
	public void set__HSearch_Score(float _HSearch_Score){
		this.setHSearch_Score(_HSearch_Score);
	}
	/**
	 * @return the score
	 */
	public float getHSearch_Score() {
		return hSearch_Score;
	}
	// die gleiche Methode wie zuvor, wird in dieser Schreibweiseeli von Lucene benoetigt.
	public float get__HSearch_Score() {
		return this.getHSearch_Score();
	}





	
	
	
	

}
