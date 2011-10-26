/**
 * 
 */
package net.agef.jobexchange.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

//import org.acegisecurity.GrantedAuthority;
//import org.acegisecurity.userdetails.UserDetails;
import org.apache.tapestry5.beaneditor.DataType;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;


/**
 * @author Administrator
 *
 */
@Entity
public class LoginUser extends AbstractEntity implements UserDetails {
    static final long serialVersionUID = 1;
    
    private String password;
    private String username;
    private AddressEnum addresses;
	private TitleEnum title;
	private String familyName;
	private String givenName;
	private String fathersName;
    private String emailBusiness;
    private String internet;
    private Address loginUserAddress;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private Collection<LoginUserRole> grantedAuthorities = new HashSet<LoginUserRole>();
    
    private List<JobImpl> providedJobOffers = new ArrayList<JobImpl>();
    private List<User> providedOrganisations = new ArrayList<User>();
    
    @Transient
    public GrantedAuthority[] getAuthorities() {
        final Collection<LoginUserRole> grantedAuthorities = getGrantedAuthorities();
        return (GrantedAuthority[]) grantedAuthorities.toArray(
                new GrantedAuthority[grantedAuthorities.size()]);
    }
    
    @Validate("required")
    @NaturalId
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Validate("required")
    public String getPassword() {
        return password;
    }
    
    @DataType(value="password")
    public void setPassword(String password) {
        this.password = password;
    }

    
    /**
	 * @return the addresses
	 */
    @Validate("required")
    //@Enumerated(EnumType.STRING)
	public AddressEnum getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(AddressEnum addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return the title
	 */
	public TitleEnum getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(TitleEnum title) {
		this.title = title;
	}

	/**
	 * @return the familyName
	 */
	@Validate("required")
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * @return the givenName
	 */
	@Validate("required")
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the fathersName
	 */
	public String getFathersName() {
		return fathersName;
	}

	/**
	 * @param fathersName the fathersName to set
	 */
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	/**
	 * @return the emailBusiness
	 */
	@Validate("required,email")
	public String getEmailBusiness() {
		return emailBusiness;
	}

	/**
	 * @param emailBusiness the emailBusiness to set
	 */
	public void setEmailBusiness(String emailBusiness) {
		this.emailBusiness = emailBusiness;
	}

	/**
	 * @return the internet
	 */
	public String getInternet() {
		return internet;
	}

	/**
	 * @param internet the internet to set
	 */
	public void setInternet(String internet) {
		this.internet = internet;
	}

	/**
	 * @return the loginUserAddress
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="addressLoginUser_fk")
	public Address getLoginUserAddress() {
		return loginUserAddress;
	}

	/**
	 * @param loginUserAddress the loginUserAddress to set
	 */
	public void setLoginUserAddress(Address loginUserAddress) {
		this.loginUserAddress = loginUserAddress;
	}

	public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String toString() {
        return username;
    }
    
//    public void addRole(String role) {
//        final LoginUserRole authority = new LoginUserRole();
//        authority.setAuthority(role);
//        getGrantedAuthorities().add(authority);
//    }
    
    public void addRole(LoginUserRole role) {
        getGrantedAuthorities().add(role);
    }

    @NonVisual
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    @NonVisual
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @NonVisual
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}) 
    public Collection<LoginUserRole> getGrantedAuthorities() {
        return grantedAuthorities;
    }
    
    public void setGrantedAuthorities(Collection<LoginUserRole> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    /**
	 * @return the providedJobOffers
	 */
    @OneToMany(mappedBy="getjobsLoginUser", cascade={CascadeType.REMOVE}) 
	public List<JobImpl> getProvidedJobOffers() {
		return providedJobOffers;
	}

	/**
	 * @param providedJobOffers the providedJobOffers to set
	 */
	public void setProvidedJobOffers(List<JobImpl> providedJobOffers) {
		this.providedJobOffers = providedJobOffers;
	}

	/**
	 * @return the providesOrganisations
	 */
	@OneToMany(mappedBy="relatedLoginUser", cascade={CascadeType.REMOVE})
	public List<User> getProvidedOrganisations() {
		return providedOrganisations;
	}

	/**
	 * @param providesOrganisations the providesOrganisations to set
	 */
	public void setProvidedOrganisations(List<User> providedOrganisations) {
		this.providedOrganisations = providedOrganisations;
	}

	
}
