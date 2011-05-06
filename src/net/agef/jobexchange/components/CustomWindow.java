/**
 * 
 */
package net.agef.jobexchange.components;



import org.apache.tapestry5.json.JSONObject;
import org.chenillekit.tapestry.core.components.Window;

/**
 * @author Andreas Pursian based on work of Sven Homburg 
 *
 */
public class CustomWindow extends Window
{
    /**
     * Invoked to allow subclasses to further configure the parameters passed to this component's javascript
     * options. Subclasses may override this method to configure additional features of the Window.
     * <p/>
     * This implementation does nothing. For more information about window options look at
     * this <a href="http://prototype-window.xilinus.com/documentation.html#initialize">page</a>.
     *
     * @param options windows option object
     */
    protected void configure(JSONObject options)
    {   
        options.put("minimizable", false);
        //options.put("title", "Google Window");
        //options.put("url", "http://www.google.com");
    }
}