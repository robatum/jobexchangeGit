package net.agef.jobexchange.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class LayoutMobile {
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=true, defaultPrefix="literal") 
	private String title = "";
}
