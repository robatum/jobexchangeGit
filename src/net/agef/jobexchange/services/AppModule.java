// Copyright 2007, 2008 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package net.agef.jobexchange.services;

import java.io.IOException;
import java.net.URL;


import net.agef.jobexchange.application.ApplicantWorker;
import net.agef.jobexchange.application.ApplicantWorkerHandler;
import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.DataProviderWorkerHandler;
import net.agef.jobexchange.application.FieldOfOccupationWorker;
import net.agef.jobexchange.application.FieldOfOccupationWorkerHandler;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.JobWorkerHandler;
import net.agef.jobexchange.application.LocationWorker;
import net.agef.jobexchange.application.LocationWorkerHandler;
import net.agef.jobexchange.application.LoginUserWorker;
import net.agef.jobexchange.application.LoginUserWorkerHandler;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.application.UserWorkerHandler;
import net.agef.jobexchange.integration.AccessHistoryApplicantDAO;
import net.agef.jobexchange.integration.AccessHistoryApplicantDAOHibernate;
import net.agef.jobexchange.integration.AccessHistoryJobsDAO;
import net.agef.jobexchange.integration.AccessHistoryJobsDAOHibernate;
import net.agef.jobexchange.integration.ApplicantDAO;
import net.agef.jobexchange.integration.ApplicantDAOHibernate;
import net.agef.jobexchange.integration.CountryDAO;
import net.agef.jobexchange.integration.CountryDAOHibernate;
import net.agef.jobexchange.integration.CurrencyDAO;
import net.agef.jobexchange.integration.CurrencyDAOHibernate;
import net.agef.jobexchange.integration.DataProviderDAO;
import net.agef.jobexchange.integration.DataProviderDAOHibernate;
import net.agef.jobexchange.integration.IndustrySectorDAO;
import net.agef.jobexchange.integration.IndustrySectorDAOHibernate;
import net.agef.jobexchange.integration.JobApplicationDAO;
import net.agef.jobexchange.integration.JobApplicationDAOHibernate;
import net.agef.jobexchange.integration.JobDAO;
import net.agef.jobexchange.integration.JobDAOHibernate;
import net.agef.jobexchange.integration.LanguagesDAO;
import net.agef.jobexchange.integration.LanguagesDAOHibernate;
import net.agef.jobexchange.integration.LoginUserDAO;
import net.agef.jobexchange.integration.LoginUserDAOHibernate;
import net.agef.jobexchange.integration.LoginUserRoleDAO;
import net.agef.jobexchange.integration.LoginUserRoleDAOHibernate;
import net.agef.jobexchange.integration.OccupationalFieldDAO;
import net.agef.jobexchange.integration.OccupationalFieldDAOHibernate;
import net.agef.jobexchange.integration.SearchHistoryApplicantDAO;
import net.agef.jobexchange.integration.SearchHistoryApplicantDAOHibernate;
import net.agef.jobexchange.integration.SearchHistoryJobsDAO;
import net.agef.jobexchange.integration.SearchHistoryJobsDAOHibernate;
import net.agef.jobexchange.integration.TerritoryDAO;
import net.agef.jobexchange.integration.TerritoryDAOHibernate;
import net.agef.jobexchange.integration.UserDAO;
import net.agef.jobexchange.integration.UserDAOHibernate;

import net.agef.jobexchange.services.internal.EntityInterceptor;
import net.agef.jobexchange.services.internal.InjectSelectionModelWorker;
import net.agef.jobexchange.services.internal.JobOfferOnlineStateCheck;
import net.agef.jobexchange.services.internal.JobOfferOnlineStateCheckImpl;
import net.agef.jobexchange.services.internal.JobOfferOnlineStateCheckBundle;
import net.agef.jobexchange.services.internal.JobexchangeInitializerImpl;
import net.agef.jobexchange.services.internal.SaltSourceImpl;
import net.agef.jobexchange.services.internal.UserDetailsServiceImpl;
import net.agef.jobexchange.services.recaptcha.ReCaptcha;
import net.agef.jobexchange.services.recaptcha.ReCaptchaImpl;
import net.agef.jobexchange.webservice.adapter.AddressAssembler;
import net.agef.jobexchange.webservice.adapter.AddressAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.ApplicantAssembler;
import net.agef.jobexchange.webservice.adapter.ApplicantAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.ContactPersonAssembler;
import net.agef.jobexchange.webservice.adapter.ContactPersonAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.CountryAssembler;
import net.agef.jobexchange.webservice.adapter.CountryAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.CurrencyAssembler;
import net.agef.jobexchange.webservice.adapter.CurrencyAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.EducationAssembler;
import net.agef.jobexchange.webservice.adapter.EducationAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.IndustrySectorAssembler;
import net.agef.jobexchange.webservice.adapter.IndustrySectorAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.JobApplicationAssembler;
import net.agef.jobexchange.webservice.adapter.JobApplicationAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.JobAssembler;
import net.agef.jobexchange.webservice.adapter.JobAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.JobSearchResultAssembler;
import net.agef.jobexchange.webservice.adapter.JobSearchResultAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.LanguageSkillAssembler;
import net.agef.jobexchange.webservice.adapter.LanguageSkillAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.OccupationalFieldAssembler;
import net.agef.jobexchange.webservice.adapter.OccupationalFieldAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.TerritoryAssembler;
import net.agef.jobexchange.webservice.adapter.TerritoryAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.UserAssembler;
import net.agef.jobexchange.webservice.adapter.UserAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.WorkExperienceAssembler;
import net.agef.jobexchange.webservice.adapter.WorkExperienceAssemblerWorker;
import net.agef.jobexchange.webservice.adapter.WorkUserTypeAssembler;
import net.agef.jobexchange.webservice.adapter.WorkUserTypeAssemblerWorker;
import nu.localhost.tapestry5.springsecurity.services.SaltSourceService;
import nu.localhost.tapestry5.springsecurity.services.SpringSecurityServices;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.AliasContribution;
import org.apache.tapestry5.hibernate.HibernateConfigurer;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateTransactionDecorator;
import org.apache.tapestry5.SymbolConstants;


import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.services.ApplicationInitializerFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.chenillekit.quartz.services.JobSchedulingBundle;
import org.hibernate.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.ui.rememberme.RememberMeServices;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.userdetails.UserDetailsService;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class AppModule
{
    public static void bind(ServiceBinder binder)
    {
        
    	binder.bind(JobWorker.class, JobWorkerHandler.class);	
    	binder.bind(ApplicantWorker.class,ApplicantWorkerHandler.class);	 
    	binder.bind(UserWorker.class, UserWorkerHandler.class);	
    	binder.bind(LocationWorker.class, LocationWorkerHandler.class);	
    	binder.bind(FieldOfOccupationWorker.class, FieldOfOccupationWorkerHandler.class);	
    	binder.bind(DataProviderWorker.class,DataProviderWorkerHandler.class);	
    	binder.bind(LoginUserWorker.class,LoginUserWorkerHandler.class);	
    	
    	binder.bind(ReCaptcha.class, ReCaptchaImpl.class);
    	binder.bind(JobOfferOnlineStateCheck.class, JobOfferOnlineStateCheckImpl.class).eagerLoad();
    	
    	binder.bind(JobAssembler.class, JobAssemblerWorker.class);
    	binder.bind(JobApplicationAssembler.class, JobApplicationAssemblerWorker.class);
    	binder.bind(ApplicantAssembler.class, ApplicantAssemblerWorker.class);
    	binder.bind(UserAssembler.class, UserAssemblerWorker.class);
    	binder.bind(AddressAssembler.class, AddressAssemblerWorker.class);
    	binder.bind(CountryAssembler.class, CountryAssemblerWorker.class);
    	binder.bind(TerritoryAssembler.class, TerritoryAssemblerWorker.class);
    	binder.bind(LanguageSkillAssembler.class, LanguageSkillAssemblerWorker.class);
    	binder.bind(ContactPersonAssembler.class, ContactPersonAssemblerWorker.class);
    	binder.bind(EducationAssembler.class, EducationAssemblerWorker.class);
    	binder.bind(WorkExperienceAssembler.class, WorkExperienceAssemblerWorker.class);
    	binder.bind(IndustrySectorAssembler.class, IndustrySectorAssemblerWorker.class);
    	binder.bind(OccupationalFieldAssembler.class, OccupationalFieldAssemblerWorker.class);
    	binder.bind(CurrencyAssembler.class, CurrencyAssemblerWorker.class);
    	binder.bind(JobSearchResultAssembler.class, JobSearchResultAssemblerWorker.class);
    	
    	binder.bind(WorkUserTypeAssembler.class, WorkUserTypeAssemblerWorker.class);
    	
    	binder.bind(ApplicantDAO.class, ApplicantDAOHibernate.class);
    	binder.bind(JobDAO.class, JobDAOHibernate.class);
    	binder.bind(CountryDAO.class, CountryDAOHibernate.class);
    	binder.bind(CurrencyDAO.class, CurrencyDAOHibernate.class);
     	binder.bind(TerritoryDAO.class, TerritoryDAOHibernate.class);
     	binder.bind(UserDAO.class, UserDAOHibernate.class);
     	binder.bind(IndustrySectorDAO.class, IndustrySectorDAOHibernate.class);
     	binder.bind(OccupationalFieldDAO.class, OccupationalFieldDAOHibernate.class);
     	binder.bind(DataProviderDAO.class, DataProviderDAOHibernate.class);
     	binder.bind(JobApplicationDAO.class, JobApplicationDAOHibernate.class);
     	binder.bind(LanguagesDAO.class, LanguagesDAOHibernate.class);
     	binder.bind(LoginUserDAO.class, LoginUserDAOHibernate.class);
     	binder.bind(LoginUserRoleDAO.class, LoginUserRoleDAOHibernate.class);
     	binder.bind(SearchHistoryJobsDAO.class, SearchHistoryJobsDAOHibernate.class);
     	binder.bind(SearchHistoryApplicantDAO.class, SearchHistoryApplicantDAOHibernate.class);
     	binder.bind(AccessHistoryJobsDAO.class, AccessHistoryJobsDAOHibernate.class);
     	binder.bind(AccessHistoryApplicantDAO.class, AccessHistoryApplicantDAOHibernate.class);
     	
     	//binder.bind(SaltSourceService.class, SaltSourceImpl.class).withId("MySaltSource");
  
    	//binder.bind(UserDetailsService.class, UserDetailsServiceImpl.class );
    	
   
        // Make bind() calls on the binder object to define most IoC services.
        // Use service builder methods (example below) when the implementation
        // is provided inline, or requires more initialization than simply
        // invoking the constructor.
    }
    
    // Data type analysis mapping for beaneditor component
//    public static void contributeDefaultDataTypeAnalyzer(MappedConfiguration<Class<?>, String> configuration) {
//    	configuration.add(Address.class, "address"); 
//    }
    
    
    
//    public static void contributeRegexAuthorizer(Configuration<String> configuration) {
//           	//use this rather than a blanket regex (^.*.jpg$, etc.); want to be sure that tests pass from the default
//           	//configuration setup, (eg: this way, I realized that the"virtual" assets folder
//    		//needed to be opened up in the tapestry-provided contributions)rather than from some blanket configuration in the appmodule
//    		//opening up all css, js, etc. files.
//    		//would contribute to whitelist except that the resource path between ctxt and the rest of the path can change.
//          
////            configuration.add("^ctx/[^/]+/css/menue_style.css$");
////            configuration.add("^ctx/[^/]+/css/component_style.css$");
//            configuration.add("^.*\\.png$");
//	        configuration.add("^.*\\.gif$");
//	        configuration.add("^.*\\.jpg$");
//	        configuration.add("^.*\\.jpeg$");
//	        configuration.add("^.*\\.js$");
//	        configuration.add("^.*\\.css$");
//	        configuration.add("^.*\\.html$");
//        }

    
    
    // Setting up UTF8 Filter
    public RequestFilter buildUtf8Filter(
            @InjectService("RequestGlobals") final RequestGlobals requestGlobals)
        {
            return new RequestFilter()
            {
                public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
                {
                    requestGlobals.getHTTPServletRequest().setCharacterEncoding("UTF-8");
                    return handler.service(request, response);
                }
            };
        }

//    /**
//     * @param context       servlet context
//     * @param configuration configuration map
//     */
//    public static void contributeGoogleMapService(Context context, MappedConfiguration<String, Object> configuration)
//    {
//        configuration.add("google.map.key", new ContextResource(context, "WEB-INF/conf/googlemap.key"));
//        configuration.add("google.service.timeout", 30000);
////        configuration.add("google.service.proxy", new ProxyConfig("proxy.depot120.dpd.de", 3128, null, null));
//    }

    
//    public static GenericDAOFactory buildAppDAOFactory(Logger serviceLog, HibernateSessionManager sessionManager)
//    {	
//        return new AppDAOFactory(serviceLog, sessionManager);
//    }
    
    // enables @CommitAfter for Service classes ... 
    //TODO Check correct function of @CommitAfter for DAO Interfaces 
    @Match("*DAO")
    public static <T> T decorateTransactionally(HibernateTransactionDecorator decorator, Class<T> serviceInterface,
                                                T delegate,
                                                String serviceId)
    {
        System.out.println("AppModule: Generating Decorator for DAO Interface");
    	return decorator.build(serviceInterface, delegate, serviceId);
    }
    
    /**
     * 
     * @param configuration
     * Contribution for hibernate domain package
     */
    public static void contributeHibernateEntityPackageManager(Configuration<String> configuration)
    {
        configuration.add("net.agef.jobexchange.domain");
    }
    
    /**
     * 
     * @param configuration
     * Contribution for excluding pathes which should not been handled by tapestry -> Axis WS
     */
    public static void contributeIgnoredPathsFilter(Configuration<String> configuration)
    {
      configuration.add("/services/.*");
    }
    
    /**
     * 
     * @param configuration
     * Contribution for tapestry-hibernate app-name symbol
     */
//    public static void contributeSymbolSource(OrderedConfiguration<SymbolProvider> conf){
//        //tapestry-hibernate fails without tapestry.app-name symbol defined 
//         conf.add("AppPackage", new SymbolProvider(){
//            public String valueForSymbol(String symbolName){
//                if(symbolName.equalsIgnoreCase(InternalConstants.TAPESTRY_APP_PACKAGE_PARAM))
//                    return "net.agef.jobexchange";
//                return null;
//           } 
//         },"");
//     }
    
    
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, String> configuration)
    {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma seperated series of locale names;
        // the first locale name is the default when there's no reasonable match).
        
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,de");

        // The factory default is true but during the early stages of an application
        // overriding to false is a good idea. In addition, this is often overridden
        // on the command line as -Dtapestry.production-mode=false
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(SymbolConstants.CHARSET, "UTF-8");
        configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
        
        configuration.add("acegi.failure.url", "/loginpage/failed");
        configuration.add("acegi.check.url", "/j_acegi_security_check");
        configuration.add("acegi.loginform.url", "/loginpage");
        configuration.add("acegi.accessDenied.url", "/loginpage");
        configuration.add("acegi.target.url", "/administration/overviewpage");
        //configuration.add("spring-security.password.encoder", "org.springframework.security.providers.encoding.Md5PasswordEncoder");
        configuration.add("spring-security.failure.url", "/loginpage/failed" );
        configuration.add("spring-security.accessDenied.url", "/loginpage" );


    }
    
    
    
    //Enables HTTPS for the specified folder
    public void contributeMetaDataLocator(MappedConfiguration<String,String> configuration)
    {
     //  configuration.add("forms:" + TapestryConstants.SECURE_PAGE, "true");
    }
    
    // *HTTP* in contribute HTTPSAlias ist hier falsch... musste umbenannt werden,
    // da Spring Security eine gleichlautende Methode zur Konfiguration benötigt ... muss 
    // geklärt werden
    
//    public void contributeHTTPSAlias(Configuration<AliasContribution> configuration)
//    {
//        BaseURLSource source = new BaseURLSource()
//        {
//            public String getBaseURL(boolean secure)
//            {
//                String protocol = secure ? "https" : "http";
//
//                int port = secure ? 8443 : 8080;
//
//                return String.format("%s://localhost:%d", protocol, port);
//            }
//        };
//
//        configuration.add(AliasContribution.create(BaseURLSource.class, source));
//    }
    
    

    /**
     * This is a service definition, the service will be named "TimingFilter". The interface,
     * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
     * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
     * appropriate Log instance. Requests for static resources are handled at a higher level, so
     * this filter will only be invoked for Tapestry related requests.
     * 
     * <p>
     * Service builder methods are useful when the implementation is inline as an inner class
     * (as here) or require some other kind of special initialization. In most cases,
     * use the static bind() method instead. 
     * 
     * <p>
     * If this method was named "build", then the service id would be taken from the 
     * service interface and would be "RequestFilter".  Since Tapestry already defines
     * a service named "RequestFilter" we use an explicit service id that we can reference
     * inside the contribution method.
     */    
    public RequestFilter buildTimingFilter(final Logger log)
    {
        return new RequestFilter()
        {
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
            {
                long startTime = System.currentTimeMillis();

                try
                {
                    // The reponsibility of a filter is to invoke the corresponding method
                    // in the handler. When you chain multiple filters together, each filter
                    // received a handler that is a bridge to the next filter.
                    
                    return handler.service(request, response);
                }
                finally
                {
                    long elapsed = System.currentTimeMillis() - startTime;

                    log.info(String.format("Request time: %d ms", elapsed));
                }
            }
        };
    }
    
    public static void contributeApplicationInitializer(OrderedConfiguration<ApplicationInitializerFilter> configuration, 
    										final PasswordEncoder passwordEncoder, 
    										final SaltSourceService saltSource, 
    										final Session session, 
    										final HibernateSessionManager hbm) {
        configuration.add("JobexchangeInitializer", new JobexchangeInitializerImpl(passwordEncoder, saltSource, session, hbm));
    }
    
    
    //Configuration for Select Model Annotation Worker
    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration, PropertyAccess propertyAccess)
    {
        configuration.add("InjectSelectionModel", new InjectSelectionModelWorker(propertyAccess), "after:Inject*");
    }

    /**
     * This is a contribution to the RequestHandler service configuration. This is how we extend
     * Tapestry using the timing filter. A common use for this kind of filter is transaction
     * management or security.
     */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,
            @InjectService("TimingFilter") final RequestFilter timingFilter
            //,@InjectService("Utf8Filter") final RequestFilter utf8Filter
            )
    {
    	// Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.
        //configuration.add("Utf8Filter", utf8Filter); // handle UTF-8
        configuration.add("Timing", timingFilter);
    }
    
    
    public static void contributeHibernateSessionSource(OrderedConfiguration<HibernateConfigurer> config, final Session session) {
 
            config.add("HibernateConfiguration", new HibernateConfigurer() {
               
                    @Override
					public void configure(org.hibernate.cfg.Configuration configuration) {
                            /*
                             * I'm having trouble getting a reference to an implementation at the moment,
                             * so we'll settle on this for the moment.
                             */
                            configuration.setInterceptor(new EntityInterceptor(session,
                                    LoggerFactory.getLogger(EntityInterceptor.class)));
                    }

            });
    }
    
    // Quartz related configuration
    //TODO Quartz Konfiguration wieder herstellen 
    public void contributeSchedulerFactory(OrderedConfiguration<URL> configuration) 
    {
        Resource configResource = new ClasspathResource("quartz.properties");
        configuration.add("configuration", configResource.toURL());
    }


    public static void contributeQuartzSchedulerManager(JobOfferOnlineStateCheck importer, JobWorker jobWorker,
            OrderedConfiguration<JobSchedulingBundle> configuration)
    {
    	configuration.add("jobOfferOnlineStateCheck", new JobOfferOnlineStateCheckBundle(importer,jobWorker));
    }


    //ACEGI- Spring Security Integration
    
    public static void contributeAlias(Configuration<AliasContribution<PasswordEncoder>> configuration ) {

    	      configuration.add( AliasContribution.create(
    	          PasswordEncoder.class,
    	          new Md5PasswordEncoder() ) );
    	  }
     
    public static UserDetailsService buildUserDetailsService(Session session) {
        return new UserDetailsServiceImpl(session);
    }
    
    public static void contributeProviderManager(OrderedConfiguration<AuthenticationProvider> configuration,@InjectService("DaoAuthenticationProvider")
            AuthenticationProvider daoAuthenticationProvider) {
        		configuration.add("daoAuthenticationProvider", daoAuthenticationProvider);
    }
    
    public static SaltSourceService buildMySaltSource() throws Exception {
        SaltSourceImpl saltSource = new SaltSourceImpl();
        saltSource.setSystemWideSalt("DEADBEEF");
        saltSource.afterPropertiesSet();
        return saltSource;
    }
    
    public static AuthenticationProcessingFilter buildMyAuthenticationProcessingFilter(
    			@SpringSecurityServices final AuthenticationManager manager,
                @SpringSecurityServices final RememberMeServices rememberMeServices,
                @Inject @Value("${acegi.check.url}") final String authUrl,
                @Inject @Value("${acegi.target.url}") final String targetUrl,
                @Inject @Value("${acegi.failure.url}") final String failureUrl)
    throws Exception {
        AuthenticationProcessingFilter filter = new AuthenticationProcessingFilter();
        filter.setAuthenticationManager(manager);
        filter.setAuthenticationFailureUrl(failureUrl);
        filter.setDefaultTargetUrl(targetUrl);
        filter.setFilterProcessesUrl(authUrl);
        filter.setRememberMeServices(rememberMeServices);
        filter.afterPropertiesSet();
        return filter;
    }
    
    @SuppressWarnings("unchecked")
	public static void contributeAliasOverrides(@InjectService("MySaltSource")SaltSourceService saltSource,
    			@InjectService("MyAuthenticationProcessingFilter")AuthenticationProcessingFilter authenticationProcessingFilter,
                Configuration<AliasContribution> configuration) {
        		configuration.add(AliasContribution.create(SaltSourceService.class,saltSource));
        		configuration.add(AliasContribution.create(AuthenticationProcessingFilter.class,authenticationProcessingFilter));
    }    
    
    
    
}
