/**
 * 
 */
package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.JobApplication;


import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author AGEF
 *
 */
public interface JobApplicationDAO extends GenericDAO<JobApplication, Long>{

	
	@CommitAfter
	public JobApplication doSave(JobApplication jobApplication);
}
