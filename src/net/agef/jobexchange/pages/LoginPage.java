/**
 * 
 */
package net.agef.jobexchange.pages;


import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.services.Request;

/**
 * @author Administrator
 *
 */
public class LoginPage {
    @Inject @Value("${acegi.check.url}")
    private String checkUrl;
    
    @Inject
    private Request request;
    
    private boolean failed = false; 

    public boolean isFailed() {
        return failed;
    }
    
    public String getLoginCheckUrl() {
        return request.getContextPath() + checkUrl;
    }
    
    void onActivate(String extra) {
        if (extra.equals("failed")) {
            failed = true;
        }
    }
}
