/**
 * 
 */
package net.agef.jobexchange.application;

import java.util.Collection;

import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.WorkExperience;
import net.agef.jobexchange.domain.WorkUserType;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.ApplicantProfileAlreadyExistException;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.InwentUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.exceptions.UserNotFoundException;
import net.agef.jobexchange.webservice.entities.ApplicantsSearchResultDTO;

/**
 * @author Administrator
 *
 */
public interface ApplicantWorker {

	public Collection<Applicant> getAllApplicants();

	public Long addApplicantData(Applicant applicantData) throws ApplicantProfileAlreadyExistException;
	public Applicant getApplicantDataByAPDUserId(Long apdUserId) throws APDUserNotFoundException, ApplicantProfileNotFoundException ;
	public Applicant getApplicantDataByUserId(Long userId) throws ApplicantProfileNotFoundException, UserNotFoundException;
	public Applicant getApplicantDataByProfileId(Long applicantProfileId) throws ApplicantProfileNotFoundException;

	public Boolean getApplicantProfileOnlineState(Long applicantProfileId) throws ApplicantProfileNotFoundException;
	
	public void modifyApplicantData(Applicant applicant) throws ApplicantProfileNotFoundException, PassedAttributeIsNullException ;

	public void deleteApplicantDataByCobraId(Long cobraSuperId)throws CobraUserNotFoundException, ApplicantProfileNotFoundException;

	public void deleteApplicantData(Long userId)throws APDUserNotFoundException, ApplicantProfileNotFoundException;
	public void setApplicantProfileOnlineStatus(Applicant applicant, Boolean onlineStatus) throws ObjectNotSavedException, CantChangeOnlineStateException;
	public Collection<Applicant> getApplicantByCriteria(String criteria, Country country, Territory territory);
	public Collection<ApplicantsSearchResultDTO> getApplicantByCriteria(String criteria, Country country, Territory territory, Integer resultAmount, Integer pageIndexStart, Boolean filterGetjobsResults);

	void deleteApplicantDataByInwentUserId(Long inwentUserId) throws InwentUserNotFoundException, ApplicantProfileNotFoundException;

	public Collection<Applicant> getApplicantByExtendedCriteria(String criteria, Country domainObj, Territory domainObj2, String[] availability, WorkUserType[] workTypes,
			String[] occupationalField, String managementExperience, Integer resultsAmount, Integer pageIndexStart) throws EnumValueNotFoundException;

	public int getApplicantsSearchResultsAmountByExtendedCriteria(String criteria, Country domainObj, Territory domainObj2, String[] availability, WorkUserType[] workTypes,
			String[] occupationalField, String managementExperience);
	
}
