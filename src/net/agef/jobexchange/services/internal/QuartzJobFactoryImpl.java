/**
 * 
 */
package net.agef.jobexchange.services.internal;


import org.apache.tapestry5.ioc.ServiceResources;
import org.quartz.Job;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;

/**
 * @author agef
 *
 */
public class QuartzJobFactoryImpl implements JobFactory {

//	private JobOfferOnlineStateCheck josc;
//	private JobWorker jw;
//	
//	public void QuartzJobFactoryImpl(JobOfferOnlineStateCheck jobOfferOnlineStateCheck, JobWorker jobWorker){
//	  this.josc = jobOfferOnlineStateCheck;
//	  this.jw = jobWorker;
//	}
//	
//	@Override
//	public Job newJob(TriggerFiredBundle bundle) {
//
//	   //String jobBeanName = bundle.getJobDetail().getJobDataMap().getString("jobBeanName");
//	   return (Job) josc;
//	}
	
	ServiceResources _srvcRsrcs; 
	Logger _logger;
	
	public QuartzJobFactoryImpl( ServiceResources prmSrvcRsrcs, Logger prmLogger ) { 
		_srvcRsrcs = prmSrvcRsrcs; 
		_logger = prmLogger; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public Job newJob( TriggerFiredBundle prmTrgrFiredBndl ) { 
		System.out.println("Bin in Create Job");
		Class c = prmTrgrFiredBndl.getJobDetail().getJobClass(); 
		System.out.println("Class is: "+c.getCanonicalName());
		return( (Job)_srvcRsrcs.getService( c ) ); 
	} 

	
	
	}