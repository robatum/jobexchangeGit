/**
 * 
 */
package net.agef.jobexchange.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * @author Administrator
 * 
 */
@Entity
@Indexed
public class DataProvider extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9030099521676885536L;

	private Collection<JobImpl>	relatedJobOffers = new TreeSet<JobImpl>();
	private Collection<User> relatedUsers = new TreeSet<User>();
	
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String providerName;
	private String providerId;
	private String[] providerIP;
	
	@Inject
	public DataProvider(){
	}
	
	public DataProvider(String providerName){
		this.providerName = providerName;
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update((providerName+"_JobExchangeProvider").getBytes());
			providerId = sha.digest().toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	public DataProvider(String providerName, String[] providerIP){
		this.providerName = providerName;
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update((providerName+"_JobExchangeProvider").getBytes());
			providerId = sha.digest().toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.providerIP = providerIP;
	}
	
	
	/**
	 * @return the relatedJobOffers
	 */
	@OneToMany(mappedBy="dataProvider")
	public Collection<JobImpl> getRelatedJobOffers() {
		return relatedJobOffers;
	}

	/**
	 * @param relatedJobOffers the relatedJobOffers to set
	 */
	public void setRelatedJobOffers(Collection<JobImpl> relatedJobOffers) {
		this.relatedJobOffers = relatedJobOffers;
	}

	/**
	 * @return the relatedUsers
	 */
	@OneToMany(mappedBy="dataProvider")
	public Collection<User> getRelatedUsers() {
		return relatedUsers;
	}

	/**
	 * @param relatedUsers the relatedUsers to set
	 */
	public void setRelatedUsers(Collection<User> relatedUsers) {
		this.relatedUsers = relatedUsers;
	}

	/**
	 * @return the providerName
	 */
	public String getProviderName() {
		return providerName;
	}
	/**
	 * @param providerName the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	/**
	 * @return the providerId
	 */
	public String getProviderId() {
		return providerId;
	}
	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return the providerIP
	 */
	public String[] getProviderIP() {
		return providerIP;
	}

	/**
	 * @param providerIP the providerIP to set
	 */
	public void setProviderIP(String[] providerIP) {
		this.providerIP = providerIP;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataProvider other = (DataProvider) obj;
		if (providerName != other.providerName)
			return false;
		if (providerId != other.providerId)
			return false;
		return true;
	}
	
}
