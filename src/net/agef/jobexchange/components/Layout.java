/**
 * 
 */
package net.agef.jobexchange.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Andreas Pursian
 *
 */
public class Layout {
	
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=true, defaultPrefix="literal") 
	private String title = "";
	
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
	
	
}
