/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.webservice.entities.JobSearchResultDTO;

/**
 * @author agef
 *
 */
public interface JobSearchResultAssembler {

	public JobSearchResultDTO createDTO(JobImpl job); 
}
