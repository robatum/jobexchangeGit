package net.agef.jobexchange.components;

import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

//@IncludeJavaScriptLibrary(value = { "mobile/jquery.mobile-1.0a1.min.js", "mobile/jquery-1.4.4.min.js" })
//@IncludeStylesheet(value = { "mobile/jquery.mobile-1.0a2.min.css" })
public class LayoutMobile {
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=true, defaultPrefix="literal") 
	private String title = "";
}
