/**
 * 
 */
package net.agef.jobexchange.domain;

import javax.persistence.Entity;

import org.springframework.security.GrantedAuthority;

//import org.acegisecurity.GrantedAuthority;

/**
 * @author Administrator
 *
 */
@Entity
public class LoginUserRole extends AbstractEntity implements GrantedAuthority {
    static final long serialVersionUID = 1;
    
    private String authority;
    
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    @SuppressWarnings("null")
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final LoginUserRole that = (LoginUserRole) o;
        if (that != null ? !authority.equals(that.authority) : that.authority != null) {
            return false;
        }
        
        return true;
    }
    
    public int hashCode() {
        return (authority != null ? authority.hashCode() : 0);
    }
    
    public String toString() {
        return authority;
    }
}
