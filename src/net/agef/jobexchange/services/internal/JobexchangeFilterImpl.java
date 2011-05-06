package net.agef.jobexchange.services.internal;

import java.io.IOException;
import java.util.Formatter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.agef.jobexchange.application.ApplicantWorker;
import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.webservice.adapter.ApplicantAssembler;
import net.agef.jobexchange.webservice.adapter.CountryAssembler;
import net.agef.jobexchange.webservice.adapter.JobApplicationAssembler;
import net.agef.jobexchange.webservice.adapter.JobAssembler;
import net.agef.jobexchange.webservice.adapter.JobSearchResultAssembler;
import net.agef.jobexchange.webservice.adapter.TerritoryAssembler;
import net.agef.jobexchange.webservice.adapter.UserAssembler;
import net.agef.jobexchange.webservice.adapter.WorkExperienceAssembler;
import net.agef.jobexchange.webservice.adapter.WorkUserTypeAssembler;

import org.apache.tapestry5.internal.ServletContextSymbolProvider;
import org.apache.tapestry5.internal.TapestryAppInitializer;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.apache.tapestry5.ioc.services.ServiceActivity;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;
import org.apache.tapestry5.ioc.services.Status;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.apache.tapestry5.services.ServletApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobexchangeFilterImpl implements Filter
{

	
	/**
	 * The TapestryFilter is responsible for intercepting all requests into the web application. It identifies the requests
	 * that are relevant to Tapestry, and lets the servlet container handle the rest. It is also responsible for
	 * initializing Tapestry.
	 * <p/>
	 * <p/>
	 * The application is configured via context-level init parameters.
	 * <p/>
	 * <dl> <dt>  tapestry.app-package</dt> <dd> The application package (used to search for pages, components, etc.)</dd>
	 * </dl>
	 */

	
	    private final Logger logger = LoggerFactory.getLogger(JobexchangeFilterImpl.class);

	    private FilterConfig config;

	    private Registry registry;

	    private HttpServletRequestHandler handler;

	    /**
	     * Initializes the filter using the {@link TapestryAppInitializer}. The application name is the capitalization of
	     * the filter name (as specified in web.xml).
	     */
	    public final void init(FilterConfig filterConfig) throws ServletException
	    {
	        config = filterConfig;

	        ServletContext context = config.getServletContext();

	        String filterName = config.getFilterName();

	        SymbolProvider provider = new ServletContextSymbolProvider(context);

	        TapestryAppInitializer appInitializer = new TapestryAppInitializer(logger, provider, filterName, "servlet");

	        appInitializer.addModules(provideExtraModuleDefs(context));

	        registry = appInitializer.createRegistry();

	        //long start = appInitializer..getStartTime();

	        //long toRegistry = appInitializer.getRegistryCreatedTime();

	        ServletApplicationInitializer ai = registry.getService("ServletApplicationInitializer",
	                                                               ServletApplicationInitializer.class);

	        ai.initializeApplication(filterConfig.getServletContext());

	        registry.performRegistryStartup();

	        handler = registry.getService("HttpServletRequestHandler", HttpServletRequestHandler.class);

	        init(registry);

	        @SuppressWarnings("unused")
			long toFinish = System.currentTimeMillis();

	        StringBuilder buffer = new StringBuilder("Startup status:\n\n");
	        Formatter f = new Formatter(buffer);

	       // f.format("Startup time: %,d ms to build IoC Registry, %,d ms overall." + "\n\nStartup services status:\n",
	         //        toRegistry - start, toFinish - start);

	        int unrealized = 0;

	        ServiceActivityScoreboard scoreboard = registry
	                .getService(ServiceActivityScoreboard.class);

	        List<ServiceActivity> serviceActivity = scoreboard.getServiceActivity();

	        int longest = 0;

	        // One pass to find the longest name, and to count the unrealized services.

	        for (ServiceActivity activity : serviceActivity)
	        {
	            Status status = activity.getStatus();

	            longest = Math.max(longest, activity.getServiceId().length());

	            if (status == Status.DEFINED || status == Status.VIRTUAL) unrealized++;

	        }

	        String formatString = "%" + longest + "s: %s\n";

	        // A second pass to output all the services

	        for (ServiceActivity activity : serviceActivity)
	        {
	            f.format(formatString, activity.getServiceId(), activity.getStatus().name());
	        }

	        f.format("\n%4.2f%% unrealized services (%d/%d)\n", 100. * unrealized / serviceActivity.size(), unrealized,
	                 serviceActivity.size());
	        
	        
	        //ServletContext context = applicationGlobals.getServletContext();
	        context.setAttribute("JobWorker.JobWorkerService", registry.getService(JobWorker.class));
	        context.setAttribute("JobAssembler.JobAssemblerService", registry.getService(JobAssembler.class));
	        context.setAttribute("JobApplicationAssembler.JobApplicationAssemblerService", registry.getService(JobApplicationAssembler.class));
	        				
	        context.setAttribute("SearchResult.JobSearchResultAssemblerService", registry.getService(JobSearchResultAssembler.class));
	        
	        context.setAttribute("ApplicantWorker.ApplicantWorkerService", registry.getService(ApplicantWorker.class));
	        context.setAttribute("ApplicantAssembler.ApplicantAssemblerService", registry.getService(ApplicantAssembler.class));

	        context.setAttribute("UserWorker.UserWorkerService", registry.getService(UserWorker.class));
	        context.setAttribute("UserAssembler.UserAssemblerService", registry.getService(UserAssembler.class));

	        context.setAttribute("CountryAssembler.CountryAssemblerService", registry.getService(CountryAssembler.class));
	        context.setAttribute("TerritoryAssembler.TerritoryAssemblerService", registry.getService(TerritoryAssembler.class));
	        
	        context.setAttribute("DataProviderWorker.DataProviderWorkerService", registry.getService(DataProviderWorker.class));
	        
	        context.setAttribute("WorkExperienceAssembler.WorkExperienceAssemblerService", registry.getService(WorkExperienceAssembler.class));
	        context.setAttribute("WorkUserTypeAssembler.WorkUserTypeAssemblerService", registry.getService(WorkUserTypeAssembler.class));
	        
	        logger.info(buffer.toString());
	    }

	    protected final FilterConfig getFilterConfig()
	    {
	        return config;
	    }

	    /**
	     * Invoked from {@link #init(FilterConfig)} after the Registry has been created, to allow any additional
	     * initialization to occur. This implementation does nothing, and my be overriden in subclasses.
	     *
	     * @param registry from which services may be extracted
	     * @throws ServletException
	     */
	    protected void init(Registry registry) throws ServletException
	    {

	    }

	    /**
	     * Overridden in subclasses to provide additional module definitions beyond those normally located. This
	     * implementation returns an empty array.
	     */
	    protected ModuleDef[] provideExtraModuleDefs(ServletContext context)
	    {
	        return new ModuleDef[0];
	    }

	    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException
	    {
	        try
	        {
	            boolean handled = handler.service((HttpServletRequest) request, (HttpServletResponse) response);

	            if (!handled) chain.doFilter(request, response);
	        }
	        finally
	        {
	            registry.cleanupThread();
	        }
	    }

	    /**
	     * Shuts down and discards the registry.
	     */
	    public final void destroy()
	    {
	        destroy(registry);

	        registry.shutdown();

	        registry = null;
	        config = null;
	        handler = null;
	    }

	    /**
	     * Invoked from {@link #destroy()} to allow subclasses to add additional shutdown logic to the filter. The Registry
	     * will be shutdown after this call. This implementation does nothing, and may be overridden in subclasses.
	     *
	     * @param registry
	     */
	    protected void destroy(Registry registry)
	    {

	    }

	}
