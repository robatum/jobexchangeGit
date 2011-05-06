/**
 * 
 */
package net.agef.jobexchange.webservice.entities;



/**
 * @author Andreas Pursian
 *
 */
public class LanguageSkillDTO {
	
	private String name;
	private String level;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Liefert die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * MOTHER_TONGUE,
	 * BUSINESS_FLUENT,
	 * FLUENT,
	 * BASIC_KNOWLEDGE
	 * 
	 * @return the level
	 * 
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * Erwartet die String Repraesentation eines der folgenden Enumwerte: 
	 * 
	 * MOTHER_TONGUE,
	 * BUSINESS_FLUENT,
	 * FLUENT,
	 * BASIC_KNOWLEDGE
	 * 
	 * @param level the level to set
	 * 
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	
}
