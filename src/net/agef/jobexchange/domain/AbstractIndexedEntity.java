
package net.agef.jobexchange.domain;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.hibernate.search.annotations.DocumentId;

@MappedSuperclass
public class AbstractIndexedEntity extends AbstractEntity{
    private static final long serialVersionUID = -5269831219220124237L;
//    private long id;
//    private long version;
//    private Date created; 
//	private Date modified; 
//    
//    public int compareTo(Object o) {
//        return String.valueOf(this).compareTo(String.valueOf(o));
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    @NonVisual
    @Override
	public Long getId() {
        return id;
    }
    
//    @NonVisual
//    public void setId(long id) {
//        this.id = id;
//    }
//    
//    @Version
//    @Column(name="OPTLOCK")
//    public Long getVersion()  {  	
//			return version;
//	}
//
//	/**
//	 * @param version the version to set
//	 */
//    @NonVisual
//	public void setVersion(Long version) { 
//    	this.version = version;
//	}
//	
//	/**
//	 * @return the created
//	 */
//	
//	public Date getCreated() {
//		return created;
//	}
//
//	/**
//	 * @param created the created to set
//	 */
//	@NonVisual
//	public void setCreated(Date created) {
//		this.created = created;
//	}
//	/**
//	 * @return the modified
//	 */
//	public Date getModified() {
//		return modified;
//	}
//
//	/**
//	 * @param modified the modified to set
//	 */
//	@NonVisual
//	public void setModified(Date modified) {
//		this.modified = modified;
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + (int) (id ^ (id >>> 32));
//		return result;
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		AbstractIndexedEntity other = (AbstractIndexedEntity) obj;
//		if (id != other.id)
//			return false;
//		return true;
//	}
    
}