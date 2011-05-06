/**
 * 
 */
package net.agef.jobexchange.components;

/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.chenillekit.google.services.GoogleGeoCoder;
import org.chenillekit.google.services.GoogleService;


/**
 * Google Map component.
 *
 * @author <a href="mailto:homburgs@gmail.com">S.Homburg</a>
 * @version $Id$
 */
@SupportsInformalParameters
@IncludeJavaScriptLibrary(value = {"${commons.scripts}/GPlotter.js"})
public class CustomGPlotter implements ClientElement
{
    /**
     * For blocks, messages, crete actionlink, trigger event.
     */
    @Inject
    private ComponentResources resources;

    /**
     * Request object for information on current request.
     */
    @Inject
    private Request request;

    /**
     * RenderSupport to get unique client side id.
     */
    @Inject
    private RenderSupport renderSupport;

    /**
     * inject our google map service.
     */
    @Inject
    private GoogleService googleMapService;

    /**
     * The id used to generate a page-unique client-side identifier for the component. If a component renders multiple
     * times, a suffix will be appended to the to id to ensure uniqueness.
     */
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String _clientId;

    /**
     * google map search argument: street
     */
    @SuppressWarnings("unused")
	@Parameter(defaultPrefix = BindingConstants.PROP, value = "")
    private String street;

    /**
     * google map search argument: country
     */
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.PROP, value = "")
    private String country;

    /**
     * google map search argument: state
     */
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.PROP, value = "")
    private String state;

    /**
     * google map search argument: zipCode
     */
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.PROP, value = "")
    private String zipCode;

    /**
     * google map search argument: city
     */
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.PROP, value = "")
    private String city;

    /**
     * google map search argument: city
     */
    @Parameter(defaultPrefix = BindingConstants.PROP, value = "")
    private String errorCallbackFunction;

    private List<GoogleGeoCoder> _targetList;

    private String _assignedClientId;

    void setupRender()
    {
        _assignedClientId = renderSupport.allocateClientId(_clientId);
//        _targetList = googleMapService..getGeoCode(street, country, state, zipCode, city);
    }

    public String getPlotterId()
    {
        return getClientId();
    }

    /**
     * Tapestry render phase method.
     * Start a tag here, end it in afterRender
     *
     * @param writer the markup writer
     */
    void beginRender(MarkupWriter writer)
    {
        Element root = writer.getDocument().getRootElement();
        Element head = root.find("head");

        head.element("script",
                     "src", "http://maps.google.com/maps?file=api&v=2&key=" + googleMapService.getKey() + "&hl=" +
                request.getLocale().getLanguage(),
                     "type", "text/javascript",
                     "id", "gmap");

        writer.element("div", "id", getClientId() + "_map");
        resources.renderInformalParameters(writer);
        writer.end();
    }

    /**
     * Tapestry render phase method. End a tag here.
     */
    void afterRender()
    {
        JSONObject configuration = new JSONObject();

        configuration.put("zoomLevel", 20);
        configuration.put("smallControl", true);
        configuration.put("largeControl", false);
        configuration.put("typeControl", true);
        configuration.put("label", "location");

        configure(configuration);

        renderSupport.addScript("var %s = new GPlotter('%s_map', '%s', '%s', %s);",
                                getClientId(), getClientId(), googleMapService.getKey(), errorCallbackFunction, configuration.toString());

        if (_targetList.size() > 0)
//            renderSupport.addScript("%s.setMarker('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
//                                    getClientId(),
//                                    _targetList.get(0).getLatLng().getLatitude(),
//                                    _targetList.get(0).getLatLng().getLongitude(),
//                                    street, country, state, zipCode, city);
//        else
            renderSupport.addScript("%s.callException()", getClientId());
        
        renderSupport.addScript("showMapWindow();");
    }

    /**
     * for external configuration do override.
     *
     * @param jsonObject config object
     */
    protected void configure(JSONObject jsonObject)
    {
    }

    /**
     * Returns a unique id for the element. This value will be unique for any given rendering of a
     * page. This value is intended for use as the id attribute of the client-side element, and will
     * be used with any DHTML/Ajax related JavaScript.
     */
    public String getClientId()
    {
        return _assignedClientId;
    }
}