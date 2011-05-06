/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.Collection;
import java.util.List;

import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;


import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author Administrator
 *
 */
public class ApplicantDAOHibernate extends AbstractHibernateDAO<Applicant, Long> implements ApplicantDAO{

	private static final Integer jobOfferIdCorrection = 23;
	
	public ApplicantDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}
	
	public Applicant findApplicantDataByProfileId(Long applicantProfileId) {
		List<Applicant> applicant = this.findByQuery("From Applicant a WHERE  a.applicantProfileId = :profileId", new QueryParameter("profileId", applicantProfileId));
		if (!applicant.isEmpty()) {
			return applicant.remove(0);
		} else return null;
	}
	
	
	@Override
	public List<Applicant> findAll(){
		Collection<Applicant> user = this.findByQuery("From Applicant");
		if (!user.isEmpty()) {
			return (List<Applicant>) user;
		} else return null;
	}
	
	@Override
	public Applicant doRetrieve(Long applicantProfileId, boolean detached) {
		try {
			return super.doRetrieve(applicantProfileId- jobOfferIdCorrection, detached);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Applicant doRetrieveByDatabaseUid(Long applicantProfileId, boolean detached) {
		try {
			return super.doRetrieve(applicantProfileId, detached);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	public Applicant findApplicantProfileByCobraId(Long cobraUserId) throws ApplicantProfileNotFoundException {
		List<Applicant> applicant = null;
		try{
			applicant = this.findByQuery("From Applicant a WHERE  a.applicantProfileOwner.cobraSuperId = :cobraUserId", new QueryParameter("cobraUserId", cobraUserId));
		}catch (Exception e){
			throw new ApplicantProfileNotFoundException();
		}
		if (applicant !=null && !applicant.isEmpty()) {
			return applicant.remove(0);
		} else return null;
	}

	@Override
	public Applicant findApplicantProfileByInwentId(Long inwentUserId) throws ApplicantProfileNotFoundException {
		List<Applicant> applicant = null;
		try{
			applicant = this.findByQuery("From Applicant a WHERE  a.applicantProfileOwner.inwentUserId = :inwentUserId", new QueryParameter("inwentUserId", inwentUserId));
		}catch (Exception e){
			throw new ApplicantProfileNotFoundException();
		}
		if (applicant !=null && !applicant.isEmpty()) {
			return applicant.remove(0);
		} else return null;
	}
	
//	@Override
//	public Applicant doRefresh(Applicant app){
//		System.out.println("Fuehre Update durch fuer Applicant"+app.getApplicantProfileId());
//		//return super.doSave(app);
//		return this.doRefresh(app);
//	}

}
