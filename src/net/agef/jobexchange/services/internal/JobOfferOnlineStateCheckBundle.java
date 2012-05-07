package net.agef.jobexchange.services.internal;


import net.agef.jobexchange.application.JobWorker;

import org.chenillekit.quartz.services.JobSchedulingBundle;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author agef
 *
 */
public class JobOfferOnlineStateCheckBundle implements JobSchedulingBundle
{
    private final Logger logger = LoggerFactory.getLogger(JobOfferOnlineStateCheckBundle.class);
    private Trigger trigger;
    private JobDetail jobDetail;
    private final JobOfferOnlineStateCheck importer;
    private JobWorker jw;

    public JobOfferOnlineStateCheckBundle(JobOfferOnlineStateCheck importer, JobWorker jw)
    {
        this.importer = importer;
        this.jw = jw;
        createBundle();
    }

    private void createBundle()
    {
        try
        {
        	System.out.println("Quartz Service creates job Bundle ....");
            trigger = new CronTrigger("JobOfferOnlineStateCheckTrigger", "CronTriggerGroup", "0 0 1 * * ?"); //30 * * * * ? //0 0 1 * * ?
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("importer", importer);
            jobDataMap.put("jobWorker", this.jw);
            trigger.setJobDataMap(jobDataMap);

            
            jobDetail = new JobDetail("JobOfferOnlineStateCheck", null, JobOfferOnlineStateCheckImpl.class);
            jobDetail.setJobDataMap(jobDataMap);
            logger.info("created JobOfferOnlineStateCheck for {}", ((CronTrigger) trigger).getCronExpression());
        }
        catch (java.text.ParseException e) {
        	
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }

    public String getSchedulerId()
    {
        return null;
    }

    public JobDetail getJobDetail()
    {
        return jobDetail;
    }

    public Trigger getTrigger()
    {
        return trigger;
    }
}

