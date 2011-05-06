/**
 * 
 */
package net.agef.jobexchange.webservice;

import javax.servlet.ServletContext;

import net.agef.jobexchange.application.ApplicantWorker;
import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.ApplicantProfileAlreadyExistException;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.ClientIPNotAuthorizedException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotDeletedException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.OccupationalFieldNotFoundException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.webservice.adapter.ApplicantAssembler;
import net.agef.jobexchange.webservice.adapter.JobAssembler;
import net.agef.jobexchange.webservice.adapter.UserAssembler;
import net.agef.jobexchange.webservice.entities.AlumniRoleDTO;
import net.agef.jobexchange.webservice.entities.ApplicantDTO;
import net.agef.jobexchange.webservice.entities.JobDTO;
import net.agef.jobexchange.webservice.entities.OrganisationRoleDTO;
import net.agef.jobexchange.webservice.entities.UserDTO;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

/**
 * @author Andreas Pursian
 *
 */
public class BackendWS {
	private Logger logger = Logger.getLogger(BackendWS.class);
	private ApplicantWorker aw;
	private ApplicantAssembler aa;
	private UserWorker uw;
	private UserAssembler ua;
	private JobWorker jw;
	private JobAssembler ja;
	private MessageContext msgCtx;
	private ServletContext axisServletContext;
	private DataProviderWorker dw;
	private DataProvider dataProvider;
	
	@SuppressWarnings("static-access")
	public BackendWS() throws AxisFault {
		try {
			
			this.msgCtx = MessageContext.getCurrentMessageContext(); 
			this.axisServletContext = (ServletContext)msgCtx.getProperty("transport.http.servletContext");
			this.jw = (JobWorker)axisServletContext.getAttribute("JobWorker.JobWorkerService");
			this.dw = (DataProviderWorker)axisServletContext.getAttribute("DataProviderWorker.DataProviderWorkerService");
			this.ja = (JobAssembler)axisServletContext.getAttribute("JobAssembler.JobAssemblerService");
			this.uw = (UserWorker)axisServletContext.getAttribute("UserWorker.UserWorkerService");
			this.ua = (UserAssembler)axisServletContext.getAttribute("UserAssembler.UserAssemblerService");
			this.aw = (ApplicantWorker)axisServletContext.getAttribute("ApplicantWorker.ApplicantWorkerService");
			this.aa = (ApplicantAssembler)axisServletContext.getAttribute("ApplicantAssembler.ApplicantAssemblerService");

			//Ueberpruefung auf korrekten und erlaubten Datenprovider anhand der zugreifenden Client IP
			String remoteClientAddress = (String) msgCtx.getProperty(msgCtx.REMOTE_ADDR);
			logger.info("BackendWS - RemoteClientIP: "+remoteClientAddress);		
			dataProvider = dw.checkForValidDataProviderByIP(remoteClientAddress);
			if(dataProvider == null){
				try {
					throw new ClientIPNotAuthorizedException();
				} catch (ClientIPNotAuthorizedException e) {
					e.printStackTrace();
					throw new AxisFault("Client IP Not Autherized Exception");
				}
			}
		} catch (Exception e) {
			throw new AxisFault("General WebService Instantiation Exception");
		}
	}
	
	
	public Boolean addAlumniUser(UserDTO user, Long cobraSuperId, AlumniRoleDTO userRole){
		logger.info("Adding alumniUser to db - cobraUser: "+cobraSuperId);
		try {
			uw.getUserByCobraId(cobraSuperId,false);
		} catch (CobraUserNotFoundException e) {
			user.setCobraUserId(cobraSuperId);
			user.setUserRole(userRole);
			try {
				uw.addUser(ua.createDomainObj(user),this.dataProvider);
			} catch (EnumValueNotFoundException e1) {
				e1.printStackTrace();
				return false;
			} catch (CountryNotFoundException e2) {
				e2.printStackTrace();
				return false;
			} catch (ObjectNotSavedException e3) {
				e3.printStackTrace();
				return false;
			} catch (PassedAttributeIsNullException e4) {
				e4.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	public Boolean addOrganisationUser(UserDTO user, Long cobraSuperId, OrganisationRoleDTO userRole){
		logger.info("Adding orgUser to db - cobraUser: "+cobraSuperId);
		try {
			uw.getUserByCobraId(cobraSuperId,true);
		} catch (CobraUserNotFoundException e) {
			user.setCobraUserId(cobraSuperId);
			user.setUserRole(userRole);
			try {
				uw.addUser(ua.createDomainObj(user),this.dataProvider);
			} catch (EnumValueNotFoundException e1) {
				e1.printStackTrace();
				return false;
			} catch (CountryNotFoundException e2) {
				e2.printStackTrace();
				return false;
			} catch (ObjectNotSavedException e3) {
				e3.printStackTrace();
				return false;
			} catch (PassedAttributeIsNullException e4) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Die Methode 'setJobOfferOnlineStateByCobraId' setzt den aktuellen Online Status eines Stellenangebots.
	 * 
	 * @param Erwartet die JobOffer Id und den neuen OnlineStatus als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean setJobOfferOnlineStateByCobraId(Long cobraJobId, Boolean onlineState){
		logger.info("Set JobOffer onlineState by jobOfferId: "+cobraJobId+" --- "+onlineState);
		try {
			JobImpl job = jw.getJobOfferByCobraId(cobraJobId);
			jw.setJobOfferOnlineStatus(job, onlineState);
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		} catch (CantChangeOnlineStateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Die Methode 'getJobOfferOnlineState' gibt den aktuellen Online Status eines Stellenangebots zurück.
	 * 
	 * @param Erwartet die JobOffer Id .
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' oder 'false' im Fehlerfall 'null'.
	 */
	public Boolean getJobOfferOnlineState(Long jobOfferId){
		logger.info("Get joboffer onlineState by jobOfferId: "+jobOfferId );
		JobImpl job;
		try {
			job = jw.getJobOfferDetails(jobOfferId);
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return job.getOnlineStatus();
	}
	
	
	/**
	 * Die Methode 'setOrganisationUserOnlineStateByCobraId' setzt den aktuellen Online Status eines Nutzers.
	 * 
	 * @param Erwartet die Cobra User Id und den neuen OnlineStatus als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean setOrganisationUserOnlineStateByCobraId(Long cobraUserId, Boolean onlineState){
		logger.info("Set orgUser onlineState by cobraUserId: "+cobraUserId+" --- "+onlineState);
		try {
			User user = uw.getUserByCobraId(cobraUserId,true);
			uw.setUserOnlineStatus(user, onlineState);
		} catch (CobraUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		} catch (CantChangeOnlineStateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * Die Methode 'setAlumniUserOnlineStateByCobraId' setzt den aktuellen Online Status eines Nutzers.
	 * 
	 * @param Erwartet die Cobra User Id und den neuen OnlineStatus als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean setAlumniUserOnlineStateByCobraId(Long cobraUserId, Boolean onlineState){
		logger.info("Set alumniUser onlineState by cobraUserId: "+cobraUserId+" --- "+onlineState);
		try {
			User user = uw.getUserByCobraId(cobraUserId,false);
			uw.setUserOnlineStatus(user, onlineState);
		} catch (CobraUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		} catch (CantChangeOnlineStateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public Boolean addJobOfferByCobraId(JobDTO jobOffer, Long cobraUserId, Long cobraJobId){
		logger.info("Adding joboffer to db - cobraUser: "+cobraUserId);
		try {
			jw.getJobOfferByCobraId(cobraJobId);
		} catch (JobOfferNotFoundException e1) {
			if(cobraJobId != null) {
				jobOffer.setCobraJobId(cobraJobId);
			} else {
				logger.info("Abort adding job to db - cobraJobId is null.");
				return false;
			}
			if(cobraUserId != null) {
				jobOffer.setJobOfferOwner(cobraUserId);
			} else {
				logger.info("Abort adding job to db - cobraUserId is null.");
				return false;
			}
			try {
				jw.addJobOffer(ja.createDomainObjByCobraId(jobOffer),this.dataProvider);
			} catch (CobraUserNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (OccupationalFieldNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (ObjectNotSavedException e) {
				e.printStackTrace();
				return false;
			} catch (EnumValueNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (CountryNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
		
	}
	
	
	public Boolean modifyJobOfferByCobraId(JobDTO jobOffer, Long cobraJobId){
		logger.info("Modify jobOffer by cobraId: "+cobraJobId);
		
		if(cobraJobId != null) {
			jobOffer.setCobraJobId(cobraJobId);
		} else {
			logger.info("Abort modifying job to db - cobraJobId is null.");
			return false;
		}
		try {
			jw.modifyJobOffer(ja.updateDomainObjByCobraId(jobOffer));
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (OccupationalFieldNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (EnumValueNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean deleteJobOfferByCobraId(Long cobraJobId){
		logger.info("Delete jobOffer by cobraId: "+cobraJobId);
		try {
			jw.deleteJobOfferByCobraId(cobraJobId);
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public Boolean addApplicantProfileByCobraId(ApplicantDTO applicantProfile, Long cobraUserId){
		logger.info("Add applicantProfile by cobraUserId: "+cobraUserId);
		applicantProfile.setApplicantProfileOwnerId(cobraUserId);
		try {
			aw.addApplicantData(aa.createDomainObjByCobraId(applicantProfile));
		} catch (CobraUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IndustrySectorNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ApplicantProfileAlreadyExistException e) {
			e.printStackTrace();
			return false;
		} catch (EnumValueNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	
	public Boolean modifyApplicantProfileByCobraId(ApplicantDTO applicantProfile, Long cobraUserId){
		logger.info("Modify applicantProfile by cobraUserId: "+cobraUserId);
		if(cobraUserId != null && applicantProfile != null) {
			applicantProfile.setApplicantProfileOwnerId(cobraUserId);
		} else {
			logger.info("Abort modifying applicantProfile to db - cobraUserId is null.");
			return false;
		}
		try {
			aw.modifyApplicantData(aa.updateDomainObjByCobraId(applicantProfile));
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IndustrySectorNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (EnumValueNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean deleteApplicantProfileByCobraId(Long cobraUserId){
		logger.info("Delete applicantProfile by cobraUserId: "+cobraUserId);
		try {
			aw.deleteApplicantDataByCobraId(cobraUserId);
		} catch (CobraUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public Boolean modifyUserByCobraId(UserDTO user, Long cobraSuperId, OrganisationRoleDTO orgRole, AlumniRoleDTO alumniRole){
		logger.info("Modify user by cobraUserId: "+cobraSuperId);
		user.setCobraUserId(cobraSuperId);
		if(orgRole != null)	user.setUserRole(orgRole);
			else if (alumniRole != null) user.setUserRole(alumniRole);
		try {
			uw.modifyUser(ua.updateDomainObjByCobraId(user, user.getCobraUserId()));
		} catch (CobraUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (EnumValueNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean deleteUserByCobraId(Long cobraUserId, boolean isOrganiationUser){
		logger.info("Delete user by cobraUserId: "+cobraUserId+" --- user is OrganisationUser:"+isOrganiationUser);
		User user;
		try {
			user = uw.getUserByCobraId(cobraUserId, isOrganiationUser);
			uw.deleteUser(user);
		} catch (CobraUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ObjectNotDeletedException e) {
			e.printStackTrace();
			return false;
		} catch (PassedAttributeIsNullException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
