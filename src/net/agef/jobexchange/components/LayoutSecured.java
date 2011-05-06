/**
 * 
 */
package net.agef.jobexchange.components;

import java.util.Locale;

import net.agef.jobexchange.application.LoginUserWorker;
import nu.localhost.tapestry5.springsecurity.services.LogoutService;
//import nu.localhost.tapestry.acegi.services.LogoutService;
//
//import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.springframework.security.annotation.Secured;

/**
 * @author Andreas Pursian
 *
 */
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class LayoutSecured {
	
	private static final String ENGLISH_LANG = "en";
    private static final String GERMAN_LANG = "de";
    private static final Locale ENGLISH_LOCAL = new Locale(ENGLISH_LANG);
    private static final Locale GERMAN_LOCAL = new Locale(GERMAN_LANG);
	
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=true, defaultPrefix="literal") 
	private String title = "";
	
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=false, defaultPrefix="literal") 
	private String subTitle = "";
	
	
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=false, defaultPrefix="literal") 
	private String subTitleLink = "";
	@SuppressWarnings("unused")
	@Property
	@Inject
	@Path("context:css/getjobs_style.css")
	private Asset stylesheetLayout;
	
	@SuppressWarnings("unused")
	@Property
	@Inject
	@Path("context:css/menue_style.css")
	private Asset stylesheetMenu;
	
	@SuppressWarnings("unused")
	@Property
	@Inject
	@Path("context:css/component_style.css")
	private Asset stylesheetComponent;

	
	@Inject
    private LogoutService logoutService;
	
	@Inject
	private LoginUserWorker luw;
	
	@Inject
    private PersistentLocale persistentLocale;
	
	@Inject
	private ComponentResources resources_;
    
    @Inject
    private Locale currentLocale;
	
    @Persist("Flash")
	private String localeLabel;
    
	@OnEvent(component = "logout")
    public void doLogout() {
		
        logoutService.logout();
    }
	
	public String getLoginUserName(){
		String loginUser = luw.getLoggedInUserName();
		if(loginUser!=null){
			return loginUser;
		} else doLogout();
		return null;
	}
	
	@OnEvent(component="switchLocale")
	void changeLocale(){
		String lang = persistentLocale.get() != null ? persistentLocale.get().getLanguage() : ENGLISH_LANG;
		if (GERMAN_LANG.equalsIgnoreCase(lang)){
			persistentLocale.set(ENGLISH_LOCAL);
			String nameRaw = resources_.getPageName(); 
			System.out.println("page name: "+nameRaw+ "--"+ resources_.getCompleteId());
		}
		else {
			persistentLocale.set(GERMAN_LOCAL);
			String nameRaw = resources_.getPageName(); 
			System.out.println("page name: "+nameRaw+ "--"+ resources_.getCompleteId());
		}
	}

	/**
	 * @return the localeLabel
	 */
	public String getLocaleLabel() {
		if (localeLabel == null)
		{
			if (currentLocale.equals(GERMAN_LOCAL)){
				localeLabel=new Locale("en").getDisplayName(ENGLISH_LOCAL);
			}
			else {
				localeLabel=new Locale("de").getDisplayName(GERMAN_LOCAL);
			}
		}
		return localeLabel;
	}


}
