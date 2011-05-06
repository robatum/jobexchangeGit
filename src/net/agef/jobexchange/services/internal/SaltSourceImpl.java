/**
 * 
 */
package net.agef.jobexchange.services.internal;

import nu.localhost.tapestry5.springsecurity.services.SaltSourceService;

import org.springframework.security.providers.dao.salt.SystemWideSaltSource;
import org.springframework.security.userdetails.UserDetails;

//import nu.localhost.tapestry.acegi.services.SaltSourceService;
//
//import org.acegisecurity.providers.dao.salt.SystemWideSaltSource;
//import org.acegisecurity.userdetails.UserDetails;

/**
 * @author Administrator
 *
 */
public class SaltSourceImpl extends SystemWideSaltSource implements SaltSourceService {
    public Object getSalt(UserDetails userDetails) {
        return super.getSalt(userDetails);
    }
    
    @Override
    public String toString() {
        return "SaltSourceImpl(" + getSystemWideSalt() + ")";
    }
}
