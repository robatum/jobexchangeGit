/**
 * 
 */
package net.agef.jobexchange.services.internal;


import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;
import org.hibernate.type.Type;
import org.slf4j.Logger;



/**
 * @author Administrator
 *
 */
public class EntityInterceptor extends EmptyInterceptor {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7685282102017574521L;
	@SuppressWarnings("unused")
	private Logger log;
    private Session session;
    
    public EntityInterceptor(Session session, Logger log) {
            this.session = session;
            this.log = log;
    }
    
	
	public boolean onFlushDirty(
						Object entity, 
						Serializable id, 
						Object[] currentState, 
						Object[] previousState, 
						String[] propertyNames, 
						Type[] types){
		Date dateModified = new Date();
		return modifyProperty("modified", dateModified, currentState, propertyNames);
	}

    
    public boolean onSave(
                    Object entity, 
                    Serializable id, 
                    Object[] state, 
                    String[] propertyNames, 
                    Type[] types) {
            
            boolean modified = false;
            Date dateModified = new Date();
            if(isTransient(entity)) {
                    modified = modifyProperty("created", dateModified, state, propertyNames);
            }
            
            return modifyProperty("modified", dateModified, state, propertyNames) || modified;
    }
    
    public Boolean isTransient(Object entity) {
            /*
             * Our algorithm for transience is extremely general. If the entity identifier
             * is null, then we assume it is transient. 
             */ 
            try {
                    return this.session.getIdentifier(entity) == null;
            } catch (TransientObjectException e) {
                    return true;
            }
            
    }
    
    /**
     * Modify a property in an entity state array.
     * @param prop the property name to modify
     * @param value the value to assign
     * @param state the current entity state array
     * @param propertyNames the current entity property array
     * @return <code>true</code> if a modification was made, <code>false</code> if not
     */
    protected boolean modifyProperty(String prop, Object value, Object[] state, String[] propertyNames) {   
    	boolean modified = false;
            for(int i = 0; i < propertyNames.length; i++) {
                    if(propertyNames[i].equals(prop)) {
                            if(state[i] != value) {
                                    state[i] = value;
                                    modified = true;
                            }
                    }
            }
            return modified;
    }
    
}
