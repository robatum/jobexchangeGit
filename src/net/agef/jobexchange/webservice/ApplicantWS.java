package net.agef.jobexchange.webservice;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import net.agef.jobexchange.application.ApplicantWorker;
import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.PortalIdentifierEnum;
import net.agef.jobexchange.domain.WorkUserType;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.ApplicantProfileAlreadyExistException;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.ClientIPNotAuthorizedException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.exceptions.InwentUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.exceptions.TerritoryNotFoundException;
import net.agef.jobexchange.webservice.adapter.ApplicantAssembler;
import net.agef.jobexchange.webservice.adapter.CountryAssembler;
import net.agef.jobexchange.webservice.adapter.TerritoryAssembler;
import net.agef.jobexchange.webservice.adapter.WorkExperienceAssembler;
import net.agef.jobexchange.webservice.adapter.WorkUserTypeAssembler;
import net.agef.jobexchange.webservice.entities.ApplicantDTO;
import net.agef.jobexchange.webservice.entities.ApplicantsSearchResultDTO;
import net.agef.jobexchange.webservice.entities.CountryDTO;
import net.agef.jobexchange.webservice.entities.TerritoryDTO;
import net.agef.jobexchange.webservice.entities.WorkUserTypeDTO;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;


/**
 * 
 * 
 * Die Klasse ApplicantWS ist eine Webservice Skeleton Klasse und haelt alle Methoden zur 
 * Interaktion mit Bewerber-/Expertenprofilen im Kontext der Jobboerse bereit.
 *
 * @author Andreas Pursian
 */
public class ApplicantWS {
	Logger logger = Logger.getLogger(ApplicantWS.class);
	private ApplicantWorker aw;
	private ApplicantAssembler aa;
	private CountryAssembler ca;
	private TerritoryAssembler ta;
	private MessageContext msgCtx;
	private ServletContext axisServletContext;
	private DataProviderWorker dw;
	private DataProvider dataProvider;
	private WorkUserTypeAssembler workUserTypeAssembler;
	//private WorkExperienceAssembler workExperienceAssembler;
	
	@SuppressWarnings("static-access")
	public ApplicantWS() throws AxisFault {
		try {
			this.msgCtx = MessageContext.getCurrentMessageContext(); 
			this.axisServletContext = (ServletContext)msgCtx.getProperty("transport.http.servletContext");
			this.aw = (ApplicantWorker)axisServletContext.getAttribute("ApplicantWorker.ApplicantWorkerService");
			this.dw = (DataProviderWorker)axisServletContext.getAttribute("DataProviderWorker.DataProviderWorkerService");
			this.aa = (ApplicantAssembler)axisServletContext.getAttribute("ApplicantAssembler.ApplicantAssemblerService");
			this.ca = (CountryAssembler)axisServletContext.getAttribute("CountryAssembler.CountryAssemblerService");
			this.ta = (TerritoryAssembler)axisServletContext.getAttribute("TerritoryAssembler.TerritoryAssemblerService");
			this.workUserTypeAssembler = (WorkUserTypeAssembler)axisServletContext.getAttribute("WorkUserTypeAssembler.WorkUserTypeAssemblerService");
			//this.workExperienceAssembler = (WorkExperienceAssembler)axisServletContext.getAttribute("WorkExperienceAssembler.WorkExperienceAssemblerService");
			
			//Ueberpruefung auf korrekten und erlaubten Datenprovider anhand der zugreifenden Client IP
			String remoteClientAddress = (String) msgCtx.getProperty(msgCtx.REMOTE_ADDR);
			logger.info("ApplicantWS - RemoteClientIP: "+remoteClientAddress);		
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
	
	/**
	 * 
	 * Die Methode 'getAllApplicants' gibt alle in der Jobboerse hinterlegten Bewerber-/Expertenprofile zurueck.
	 *
	 * @return Gibt ein Array von Objekten der Klasse ApplicantDTO zurueck.
	 */
	@Deprecated
	public ApplicantDTO[] getAllApplicants(){
		logger.info("Get all Applicants");
		Collection<Applicant> applicants = aw.getAllApplicants();	
		List<ApplicantDTO> applicantsDTO = new ArrayList<ApplicantDTO>();
		if(applicants!= null){
			Iterator<Applicant> it = applicants.iterator();
			while(it.hasNext()){
				applicantsDTO.add(aa.createDTOWithPortalId(it.next()));
			}
		}
		return applicantsDTO.toArray(new ApplicantDTO[0]);
	}
	
	/**
	 * Die Methode 'setApplicantProfileOnlineState' setzt den aktuellen Online Status eines Bewerberprofils.
	 * 
	 * @param Erwartet die Portal User Id und den neuen OnlineStatus als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean setApplicantProfileOnlineState(Long portalUserId, Boolean onlineState){
		logger.info("Set applicantProfile onlineState by portalUserId: "+portalUserId +"---"+onlineState);
		try {
			Applicant applicant = aw.getApplicantDataByPortalUserId(portalUserId);
			aw.setApplicantProfileOnlineStatus(applicant, onlineState);
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		} catch (CantChangeOnlineStateException e) {
			e.printStackTrace();
			return false;
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * Die Methode 'getApplicantProfileOnlineState' gibt den aktuellen Online Status eines Bewerberprofils zurück.
	 * 
	 * @param Erwartet die Portal User Id .
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' oder 'false' im Fehlerfall 'null'.
	 */
	public Boolean getApplicantProfileOnlineState(Long portalUserId){
		System.out.println("Get applicantProfile onlineState by portalUserId: "+portalUserId );
		Applicant applicant;
		
		try {
			applicant = aw.getApplicantDataByPortalUserId(portalUserId);
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return applicant.getOnlineStatus();
	}
	
	
	
	/**
	 * Die Methode 'getApplicantProfileOnlineStateByApplicantProfileId' gibt den aktuellen Online Status eines Bewerberprofils zurück.
	 * 
	 * @param Erwartet die Applicant Profil Id .
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' oder 'false' im Fehlerfall 'null'.
	 */
	public Boolean getApplicantProfileOnlineStateByApplicantProfileId(Long applicantProfileId){
		logger.info("Get applicantProfile onlineState by applicantProfileId: "+applicantProfileId );
		
		try {
			return aw.getApplicantProfileOnlineState(applicantProfileId);
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * Die Methode 'getApplicantProfile' gibt das Bewerber/Experten Profil eines spezifischen 
	 * Nutzers zurueck.
	 * 
	 * @param Erwartet die Applicant Profile Id als Parameter.
	 * @return Gibt ein Objekt der Klasse ApplicantDTO zurueck.
	 */
	public ApplicantDTO getApplicantProfile(Long applicantProfileId) {
		logger.info("Get applicantProfile by ProfileId: "+applicantProfileId);
		Applicant applicant = null;
		try {
			applicant = aw.getApplicantDataByProfileId(applicantProfileId);
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return aa.createDTOWithPortalId(applicant);
		
	}
	
	/**
	 * 
	 * Die Methode 'getApplicantProfileByUserId' gibt das Bewerber/Experten Profil eines spezifischen 
	 * Nutzers zurueck.
	 * 
	 * @param Erwartet die Portal User Id als Parameter.
	 * @return Gibt ein Objekt der Klasse ApplicantDTO zurueck.
	 */
	public ApplicantDTO getApplicantProfileByUserId(Long portalUserId) {
		logger.info("Get applicantProfile by portalUser: "+portalUserId);
		try {
			return aa.createDTOWithPortalId(aw.getApplicantDataByPortalUserId(portalUserId));
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	/**
	 * 
	 * Die Methode 'checkForApplicantProfile' überprüft ob für einem konkreten Nutzer bereits ein Bewerber-/Experten-
	 * profil angelegt wurde oder nicht.
	 * 
	 * @param Erwartet die Portal User Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean checkForApplicantProfile(Long portalUserId) {
		logger.info("Check for applicantProfile by portalUser: "+portalUserId);
		try {
			if(aw.getApplicantDataByPortalUserId(portalUserId) != null){
				return true;
			} else return false;
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Die Methode 'addApplicantProfile' ermoeglicht es zu einem bestehendem Nutzer ein neues Bewerber-/Expertenprofil hinzuzufuegen.
	 * 
	 * @param Erwartet ein Objekt der Klasse ApplicantDTO mit allen relevanten Profildaten sowie die APD User Id
	 * @return Gibt ein Objekt vom Typ Long zurueck. Im Erfolgsfall traegt dieses den Wert des ApplicantProfiles im Fehlerfall '0'.
	 * 
	 */
	public Long addApplicantProfile(ApplicantDTO applicantProfile, Long portalUserId, Byte[] portalId){
		logger.info("Adding applicantProfile to db - portalUserId: " + portalUserId);
		applicantProfile.setApplicantProfileOwnerId(portalUserId);
		applicantProfile.setPortalId(portalId);
		Long applicantProfileId = new Long(0);
		try {
			applicantProfileId = aw.addApplicantData(aa.createDomainObj(applicantProfile));
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return new Long(0);
		} catch (IndustrySectorNotFoundException e) {
			e.printStackTrace();
			return new Long(0);
		} catch (ApplicantProfileAlreadyExistException e) {
			e.printStackTrace();
			return new Long(0);
		} catch (EnumValueNotFoundException e) {
			e.printStackTrace();
			return new Long(0);
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return new Long(0);
		} 
		logger.info("Applicant profile was successfully saved with ProfileId: "+applicantProfileId);
		return applicantProfileId;
	}
	

	
	
	/**	 
	 * Die Methode 'modifyApplicantProfile' ermoeglicht es das bestehende Bewerber-/Expertenprofil eines bestehendem Nutzers zu modifizieren.
	 * 
	 * @param Erwartet ein Objekt der Klasse ApplicantDTO mit allen relevanten Profildaten sowie die Bewerberprofil Id.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean modifyApplicantProfile(ApplicantDTO applicantProfile, Long applicantProfileId, Byte[] portalId){
		logger.info("Modify ApplicantProfile ProfileId: "+applicantProfileId);
		applicantProfile.setApplicantProfileId(applicantProfileId);
		applicantProfile.setPortalId(portalId);
		try {
			aw.modifyApplicantData(aa.updateDomainObj(applicantProfile));
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
	
	
	/**
	 * Die Methode 'deleteApplicantProfile' ermoeglicht es das bestehende Bewerber-/Expertenprofil eines bestehendem Nutzers zu loeschen.
	 * 
	 * @param Erwartet die Portal User Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 * 
	 */
	public Boolean deleteApplicantProfile(Long portalUserId){
		logger.info("Delete applicantProfile for apdUser: "+portalUserId);
		try {
			aw.deleteApplicantData(portalUserId);
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ApplicantProfileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * Die Methode 'getApplicantsByCriteria' liefert alle Bewerber-/Expertenprofile die auf die Suchanfrage passen zurueck.
	 * 
	 * @param Erwartet den Such-String, Angaben zu Kontinent und Land sowie ein Byte Array mit Angaben zur Portal Id als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse ApplicantDTO zurueck.
	 */
	@Deprecated
	public ApplicantDTO[] getApplicantsByCriteria(String criteria, CountryDTO country, TerritoryDTO territory, Byte portalId){
		logger.info("Get applicantProfile By Criteria: "+criteria);
		Collection<Applicant> applicants;
		List<ApplicantDTO> applicantsDTO = new ArrayList<ApplicantDTO>();
		try {
			applicants = aw.getApplicantByCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory));
			
			if(applicants!= null){
				Iterator<Applicant> it = applicants.iterator();
				while(it.hasNext()){
					Applicant tempApplicant = it.next();
					if(portalId!=null){
					 if (tempApplicant.getPortalIdList()!=null && tempApplicant.getPortalIdList().contains(PortalIdentifierEnum.fromPortalId(portalId.intValue()))) {
						 applicantsDTO.add(aa.createDTOWithPortalId(tempApplicant));
					 }	 
					} else applicantsDTO.add(aa.createDTOWithPortalId(tempApplicant));
				}
			}
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (EnumValueNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
// Alte Methode .....
//		if(applicants!= null){
//			Iterator<Applicant> it = applicants.iterator();
//			while(it.hasNext()){
//				applicantsDTO.add(aa.createDTOWithPortalId(it.next()));
//			}
//		}
		
		return applicantsDTO.toArray(new ApplicantDTO[0]);
	}
	
	
	
	
	/**
	 * 
	 * Die Methode 'getApplicantsSearchResultsByCriteria' liefert alle Bewerber-/Expertenprofile die auf die Suchanfrage passen zurueck.
	 * 
	 *  @param Erwartet den Such-String, das Land bzw. den Kontinent sowie die Angaben für die Paginierung - Anzahl der zurückzugebenen Ergebnisse und den Startwert der Ergebnisse sowie ein Byte Array mit Angaben zur Portal Id als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse ApplicantDTO zurueck.
	 */
	public ApplicantsSearchResultDTO[] getApplicantsSearchResultsByCriteria(String criteria, CountryDTO country, TerritoryDTO territory, Integer resultsAmount, Integer pageIndexStart, Byte portalId){
		logger.info("getApplicantsSearchResultsByCriteria():Get applicantProfile By Criteria: "+criteria);
		Collection<ApplicantsSearchResultDTO> applicants;
		try {
			applicants = aw.getApplicantByCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory), resultsAmount, pageIndexStart,false);
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		return applicants.toArray(new ApplicantsSearchResultDTO[0]);
	}
	
	/**
	 * 
	 * Die Methode 'getApplicantsSearchResultsByCriteria' liefert alle Bewerber-/Expertenprofile die auf die Suchanfrage passen zurueck.
	 * 
	 *  @param Erwartet den Such-String, das Land bzw. den Kontinent sowie die Angaben für die Paginierung - Anzahl der zurückzugebenen Ergebnisse und den Startwert der Ergebnisse sowie ein Byte Array mit Angaben zur Portal Id als als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse ApplicantDTO zurueck.
	 */
	public ApplicantDTO[] getApplicantsByExtendedCriteria(String criteria, CountryDTO country, TerritoryDTO territory, String[] availability, WorkUserTypeDTO[] workUserTypeDTO, String[] occupationalField, String managementExperience, Integer resultsAmount, Integer pageIndexStart, Byte portalId){
		logger.info("getApplicantsByExtendedCriteria(): Get applicantProfile By ExtendedCriteria: "+criteria);
		Collection<Applicant> applicants;
		List<ApplicantDTO> applicantsDTO = new ArrayList<ApplicantDTO>();
	
		WorkUserType[] workTypes;
		if(workUserTypeDTO.length>0 && workUserTypeDTO != null){
			workTypes = new WorkUserType[workUserTypeDTO.length];
			int counter = 0;
			for(WorkUserTypeDTO workTypeDTO : workUserTypeDTO){
				try {
					workTypes[counter] = workUserTypeAssembler.createDomainObj(workTypeDTO);
					counter++;
				} catch (EnumValueNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		else {
			workTypes = null;
		}
		
		try {
			applicants = aw.getApplicantByExtendedCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory), availability, workTypes, occupationalField, managementExperience, resultsAmount, pageIndexStart);
			
			if(applicants!= null){
				Iterator<Applicant> it = applicants.iterator();
				while(it.hasNext()){
					Applicant tempApplicant = it.next();
					if(portalId!=null){
					 if (tempApplicant.getPortalIdList()!=null && tempApplicant.getPortalIdList().contains(PortalIdentifierEnum.fromPortalId(portalId.intValue()))) {
						 applicantsDTO.add(aa.createDTOWithPortalId(tempApplicant));
					 }	 
					} else applicantsDTO.add(aa.createDTOWithPortalId(tempApplicant));
				}
			}	
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (EnumValueNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
// Alte Methode .....
//		if(applicants!= null){
//			Iterator<Applicant> it = applicants.iterator();
//			while(it.hasNext()){
//				applicantsDTO.add(aa.createDTOWithPortalId(it.next()));
//			}
//		}
		
		return applicantsDTO.toArray(new ApplicantDTO[0]);
	}
	
	
	
	/**
	 * 
	 * Die Methode 'getApplicantsSearchResultsAmountByExtendedCriteria' liefert die Anzahl alle Bewerber-/Expertenprofile die auf die erweiterte Suchanfrage passen zurueck.
	 * 
	 * @param Erwartet den Such-String, das Land bzw. den Kontinent sowie die erweiterten Suchkriterien inkl. eines Byte Array mit Angaben zur Portal Id als Parameter.
	 * @return Gibt die Anzahl der gefundenen Bewerber-/Expertenprofileeinen als Integer Wert zurueck.
	 */
	
	public Integer getApplicantsSearchResultsAmountByExtendedCriteria(String criteria, CountryDTO country, TerritoryDTO territory, String[] availability, WorkUserTypeDTO[] workUserTypeDTO, String[] occupationalField, String managementExperience, Byte portalId){
		logger.info("Get applicantProfile Amount By Extended Criteria: "+criteria);
		int resultSize;
		
		WorkUserType[] workTypes;
		if(workUserTypeDTO.length>0 && workUserTypeDTO != null){
			workTypes = new WorkUserType[workUserTypeDTO.length];
			int counter = 0;
			for(WorkUserTypeDTO workTypeDTO : workUserTypeDTO){
				try {
					workTypes[counter] = workUserTypeAssembler.createDomainObj(workTypeDTO);
					counter++;
				} catch (EnumValueNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		else {
			workTypes = null;
		}
		
		try {
			resultSize = aw.getApplicantsSearchResultsAmountByExtendedCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory), availability, workTypes, occupationalField, managementExperience);
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return resultSize;
	}
	
	
	
	/**
	 * 
	 * Die Methode 'getApplicantsSearchResultsAmountByCriteria' liefert die Anzahl alle Bewerber-/Expertenprofile die auf die Suchanfrage passen zurueck.
	 * 
	 * @param Erwartet den Such-String sowie das Land bzw. den Kontinent sowie ein Byte Array mit Angaben zur Portal Id als Parameter.
	 * @return Gibt die Anzahl der gefundenen Bewerber-/Expertenprofileeinen als Integer Wert zurueck.
	 */
	
	public Integer getApplicantsSearchResultsAmountByCriteria(String criteria, CountryDTO country, TerritoryDTO territory, Byte portalId){
		logger.info("Get applicantProfile Amount By Criteria: "+criteria);
		Collection<ApplicantsSearchResultDTO> applicants;
		try {
			applicants = aw.getApplicantByCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory), null, null,false);
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return applicants.size();
	}

// Noch NICHT implementierte Methoden	
	
//	/**
//	 * 
//	 * Die Methode 'getAutoCompleteList' gibt vollstaendige Such-Strings die auf Bewerber-/Expertenprofile in der Jobboerse und 
//	 * auf die unvollstaendige Suchanfrage passen zurueck.
//	 * 
//	 * @param Erwartet den Such-String als Parameter.
//	 * @return Gibt ein Array von Strings zurueck.
//	 */
//	public String[] getAutoCompleteList(String expression){
//		return null;
//	}	
//	/**
//	 * Die Methode 'contactApplicant' ermoeglicht es einem Nutzer der APD Plattform (etwa eine Organisation/Unternehmen) einen anderen Nutzer, 
//	 * welcher sein Bewerber-/Expertenprofil in der Jobboerse veroeffentlicht hat zu kontaktieren.
//	 * 
//	 * @param Erwartet die Bewerberprofil Id des zu kontaktierenden Bewerbers, die APD User Id des Kontaktsuchenden sowie eine optionale Kontaktmitteilung als Parameter.
//	 * 
//	 */
//	public void contactApplicant(Long applicantProfileId, Long apdUserId, String contactNote){
//		
//	}
//	
//	/**
//	 * 
//	 * Die Methode 'getApplicantContacts' liefert alle Kontaktanfragen die ein Nutzer (etwa eine Organisation/Unternehmen) an einen/mehrere Bewerber/Experten gestellt hat.
//	 * 
//	 * @param Erwartet die APD User Id als Parameter.
//	 * @return Gibt ein Array von Objekten der Klasse ApplicantContactDTO zurueck.
//	 */
//	public ApplicantContactDTO[] getApplicantContacts(Long apdUserId){
//		return null;
//	}
//	
//	/**
//	 * 	 
//	 * Die Methode 'getReceivedApplicantContacts' liefert alle Kontaktanfragen die ein Nutzer zu seinem Bewerber-/Expertenprofil erhalten hat.
//	 * 
//	 * @param Erwartet die APD User Id als Parameter.
//	 * @return Gibt ein Array von Objekten der Klasse ApplicantContactDTO zurueck.
//	 */
//	public ApplicantContactDTO[] getReceivedApplicantContacts(Long apdUserId){
//		return null;
//	}	
	
	
}
