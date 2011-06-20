/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.List;

import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;


import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.chenillekit.hibernate.daos.GenericDAO;

/**
 * @author Administrator
 *
 */
public interface ApplicantDAO extends GenericDAO<Applicant, Long>{

	@CommitAfter
	public Applicant doSave(Applicant app);
	
	@CommitAfter
	public Applicant doRefresh(Applicant app);
	
	@CommitAfter
	public void doDelete(Applicant app);
	
	@CommitAfter
	public void doDelete(Long appId);
	
	public Applicant doRetrieve(Long applicantProfileId, boolean detached);
	
	public Applicant doRetrieveByDatabaseUid(Long applicantProfileId, boolean detached);
	
	public List<Applicant> findAll();
	
	public Applicant findApplicantDataByProfileId(Long applicantProfileId);
	
	public Applicant findApplicantProfileByCobraId(Long cobraUserId) throws ApplicantProfileNotFoundException;

//	public Applicant findApplicantProfileByInwentId(Long applicantProfileOwnerId) throws ApplicantProfileNotFoundException;
}
