/**
 * 
 */
package net.agef.jobexchange.services.internal;

import net.agef.jobexchange.domain.LoginUser;

//import org.acegisecurity.userdetails.UserDetails;
//import org.acegisecurity.userdetails.UserDetailsService;
//import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * @author Administrator
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Session session;
    
    public UserDetailsServiceImpl(Session session) {
        this.session = session;
    }
    
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        final Query query = session.createQuery("SELECT x FROM LoginUser x WHERE x.username = :username");
        query.setParameter("username", username);
        final LoginUser bean = (LoginUser) query.uniqueResult();
        if (bean == null) {
            throw new UsernameNotFoundException(username);
        }
        return bean;
    }
}
