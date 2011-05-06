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

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * @author AGEF
 *
 */
@Entity
@Indexed
@Table(name="static_languages")
public class Languages extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3141336515727604630L;

	private Collection<LanguageSkill> relatedLanguageSkills = new TreeSet<LanguageSkill>();
	
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private String isoNameShort;
	@Field(index=Index.UN_TOKENIZED, store=Store.NO)
	private String isoNameLong;
	
	
	/**
	 * @return the relatedLanguageSkills
	 */
	@OneToMany(mappedBy="name")
	public Collection<LanguageSkill> getRelatedLanguageSkills() {
		return relatedLanguageSkills;
	}
	/**
	 * @param relatedLanguageSkills the relatedLanguageSkills to set
	 */
	public void setRelatedLanguageSkills(
			Collection<LanguageSkill> relatedLanguageSkills) {
		this.relatedLanguageSkills = relatedLanguageSkills;
	}
	/**
	 * @return the isoNameShort
	 */
	@Column(name = "lg_iso_2")
	public String getIsoNameShort() {
		return isoNameShort;
	}
	/**
	 * @param isoNameShort the isoNameShort to set
	 */
	public void setIsoNameShort(String isoNameShort) {
		this.isoNameShort = isoNameShort;
	}
	/**
	 * @return the isoNameLong
	 */
	@Column(name = "lg_name_en")
	public String getIsoNameLong() {
		return isoNameLong;
	}
	/**
	 * @param isoNameLong the isoNameLong to set
	 */
	public void setIsoNameLong(String isoNameLong) {
		this.isoNameLong = isoNameLong;
	}
	
	
	

	
	

}
