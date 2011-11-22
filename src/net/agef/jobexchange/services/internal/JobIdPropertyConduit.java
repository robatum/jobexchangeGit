/**
 * 
 */
package net.agef.jobexchange.services.internal;

import java.lang.annotation.Annotation;
import java.util.Date;


import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.OrganisationRoleData;

import org.apache.tapestry5.PropertyConduit;

/**
 * Diese Klasse dient dazu, die Sortierung innerhlab einer Tapestry5 Grid-Komponente fuer das Attribut Job Id zu ermoeglichen.
 * 
 * @author Andreas Pursian
 *
 */
public class JobIdPropertyConduit implements PropertyConduit{

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return null;
    }

    /**
     * @see org.apache.tapestry5.PropertyConduit#get(java.lang.Object)
     */
    public Object get(Object instance) {
    	JobImpl job = (JobImpl) instance;
        return job.getId();
    }

    @SuppressWarnings("unchecked")
    public Class getPropertyType() {
        return String.class;
    }

    public void set(Object instance, Object value) {
    	//Wird nicht implementietrt, da die Id nicht aenderbar ist.	
    }

	
	
}
