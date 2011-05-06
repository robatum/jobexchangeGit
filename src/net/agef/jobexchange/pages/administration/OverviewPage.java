/**
 * 
 */
package net.agef.jobexchange.pages.administration;

//import nu.localhost.tapestry.acegi.services.LogoutService;

import nu.localhost.tapestry5.springsecurity.services.LogoutService;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.OnEvent;

/**
 * @author Administrator
 *
 */
//@Secured({"ROLE_ADMIN","ROLE_USER"})
public class OverviewPage {

	@Inject
    private LogoutService logoutService;
	
	@OnEvent(component = "logout")
    public void doLogout() {
        logoutService.logout();
    }
	
}
