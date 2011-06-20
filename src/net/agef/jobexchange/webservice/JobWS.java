/**
 * 
 */
package net.agef.jobexchange.webservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.ServletContext;

import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.JobActiveEnum;
import net.agef.jobexchange.domain.JobApplication;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.ClientIPNotAuthorizedException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.OccupationalFieldNotFoundException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.exceptions.TerritoryNotFoundException;
import net.agef.jobexchange.webservice.adapter.CountryAssembler;
import net.agef.jobexchange.webservice.adapter.JobApplicationAssembler;
import net.agef.jobexchange.webservice.adapter.JobAssembler;
import net.agef.jobexchange.webservice.adapter.JobSearchResultAssembler;
import net.agef.jobexchange.webservice.adapter.TerritoryAssembler;
import net.agef.jobexchange.webservice.entities.CountryDTO;
import net.agef.jobexchange.webservice.entities.JobApplicationDTO;
import net.agef.jobexchange.webservice.entities.JobDTO;
import net.agef.jobexchange.webservice.entities.JobSearchResultDTO;
import net.agef.jobexchange.webservice.entities.TerritoryDTO;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;


/**
 * 
 * 
 * Die Klasse JobWS ist eine Webservice Skeleton Klasse und haelt alle Methoden zur 
 * Interaktion mit Stellenangeboten im Kontext der Jobboerse bereit.
 *
 * @author Andreas Pursian
 */
public class JobWS{

	private Logger logger = Logger.getLogger(JobWS.class);
	private JobWorker jw;
	private JobAssembler ja;
	private JobApplicationAssembler jaa;
	private JobSearchResultAssembler jsra;
	private CountryAssembler ca;
	private TerritoryAssembler ta;
	private MessageContext msgCtx;
	private ServletContext axisServletContext;
	private DataProviderWorker dw;
	private DataProvider dataProvider;
	
	@SuppressWarnings("static-access")
	public JobWS() throws AxisFault {
		try {
			
			this.msgCtx = MessageContext.getCurrentMessageContext(); 
			
			this.axisServletContext = (ServletContext)msgCtx.getProperty("transport.http.servletContext");
			this.jw = (JobWorker)axisServletContext.getAttribute("JobWorker.JobWorkerService");
			this.dw = (DataProviderWorker)axisServletContext.getAttribute("DataProviderWorker.DataProviderWorkerService");
			this.ja = (JobAssembler)axisServletContext.getAttribute("JobAssembler.JobAssemblerService");
			this.jaa = (JobApplicationAssembler)axisServletContext.getAttribute("JobApplicationAssembler.JobApplicationAssemblerService");		
			this.jsra = (JobSearchResultAssembler)axisServletContext.getAttribute("JobSearchResultAssembler.JobSearchResultAssemblerService");			
			this.ca = (CountryAssembler)axisServletContext.getAttribute("CountryAssembler.CountryAssemblerService");
			this.ta = (TerritoryAssembler)axisServletContext.getAttribute("TerritoryAssembler.TerritoryAssemblerService");
			
			//Ueberpruefung auf korrekten und erlaubten Datenprovider anhand der zugreifenden Client IP
			String remoteClientAddress = (String) msgCtx.getProperty(msgCtx.REMOTE_ADDR);
			logger.info("JobWS - RemoteClientIP: "+remoteClientAddress);		
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
	 * Die Methode 'getAllJobOffers' gibt alle in der Jobboerse hinterlegten Stellenangebote zurueck.
	 * 
	 * @return Gibt ein Array von Objekten der Klasse JobDTO zurueck.
	 */
	public JobDTO[] getAllJobOffers(){
		logger.info("Get all jobOffers");
		Collection<JobImpl> allJobOffers = jw.getAllJobOffers();
		if(allJobOffers!=null){
			List<JobDTO> jobOffersDTO = new ArrayList<JobDTO>();
			Iterator<JobImpl> it = allJobOffers.iterator();
			while(it.hasNext()){
				jobOffersDTO.add(ja.createDTO(it.next()));
			}
			return jobOffersDTO.toArray(new JobDTO[0]); 
		}
			
		return null;
	}
	
	
	/**
	 * Die Methode 'setJobOfferOnlineState' setzt den aktuellen Online Status eines Stellenangebots.
	 * 
	 * @param Erwartet die JobOffer Id und den neuen OnlineStatus als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean setJobOfferOnlineState(Long jobOfferId, Boolean onlineState){
		logger.info("Set joboffer onlineState by jobOfferId: "+jobOfferId +"---"+onlineState);
		try {
			JobImpl job = jw.getJobOfferDetails(jobOfferId);
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
	 * 
	 * Die Methode 'increaseJobApplicationLinkCounter' erhöht für ein konkretes Jobangebot den Zähler für Klicks auf den Link
	 * zu einer weiterführenden Bewerbungsseite des Arbeitgebers.
	 * 
	 * 
	 * @param Erwartet die JobOffer Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean increaseJobApplicationLinkCounter(Long jobOfferId){
		logger.info("Increase joboffer Application link click counter by jobOfferId: "+jobOfferId );
		Long clickCounter;
		try {
			clickCounter = jw.increaseJobApplicationLinkCounter(jobOfferId);
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		logger.info("Increased link click counter to: "+clickCounter );
		return true;
	}
	
	
	/**
	 * 
	 * Die Methode 'checkIfJobOffersExist' überprüft ob für einem konkreten Nutzer bereits Stellenangebote hinterlegt wurden
	 * oder nicht.
	 * 
	 * @param Erwartet die Portal User Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean checkIfJobOffersExist(Long portalUserId) {
		logger.info("Check if user has jobOffers by portalUserId: "+portalUserId);
		try {
			Collection<JobImpl> jobList = jw.getJobOffersByPortalUser(portalUserId);
			if(jobList != null && jobList.size()>0){
				return true;
			} else return false;
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 
	 * Die Methode 'checkIfUserIsJobOfferOwner' überprüft ob für einem konkreten Nutzer ob ihm ein konkretes Stellenangebote zuzuschreiben
	 * ist oder nicht.
	 * 
	 * @param Erwartet die Portal User Id und die JobOffer Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean checkIfUserIsJobOfferOwner(Long portalUserId, Long jobOfferId) {
		logger.info("Check if user ("+ portalUserId  +") is owner of jobOffer: "+jobOfferId);
		try {
			Collection<JobImpl> jobList = jw.getJobOffersByPortalUser(portalUserId);
			if(jobList != null && jobList.size()>0){
				
				Iterator<JobImpl> it = jobList.iterator();
				while(it.hasNext()){
					JobImpl job = it.next();
					if (job.getJobOfferOwner() != null && job.getJobOfferOwner().getApdUserId() != null &&  job.getJobOfferOwner().getApdUserId().equals(portalUserId)){
						return true;
					}
				}
				return false;
			} else return false;
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * 
	 * Die Methode 'checkIfJobexchangeIsOnline' überprüft ob die Jobbörse ordnungsgemäß arbeitet oder nicht-> Prüfung findet anhand von Aufruf aller Stellendaten statt
	 * 
	 * @param Erwartet keinen Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean checkIfJobexchangeIsOnline() {
		System.out.print("Check if jobDB is online");
//		try {
//			Collection<JobImpl> jobList = jw.getAllJobOffers();
//			if(jobList != null && jobList.size()>0){
//				System.out.print(" ... true");
//				return true;
//			} else {
//				System.out.print(" ... false");
//				return false;
//			}
//		} catch (Exception e) {
//			System.out.print(" ... false");
//			e.printStackTrace();
//			return false;
//		}
		
		
		return true;
	}
	
	
	/**
	 * Die Methode 'getJobOffersByUser' gibt alle in der Jobboerse hinterlegten Stellenangebote eines spezifischen 
	 * Nutzers zurueck.
	 * 
	 * @param Erwartet die Portal User Id als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse JobDTO zurueck.
	 */
	public JobDTO[] getJobOffersByUser(Long portalUserId){	
		logger.info("Get JobOffer By User portalUserId :"+portalUserId);
		Collection<JobImpl> jobOffers = new TreeSet<JobImpl>();
		try {
			jobOffers = jw.getJobOffersByPortalUser(portalUserId);
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
		if(jobOffers!=null){
			//TODO Performance Engpass -> Lösung mit hibernate Transformer oder Ähnlich finden !!! 
			List<JobDTO> jobOffersDTO = new ArrayList<JobDTO>();
			Iterator<JobImpl> it = jobOffers.iterator();
			while(it.hasNext()){
				jobOffersDTO.add(ja.createDTO(it.next()));
			}
			return jobOffersDTO.toArray(new JobDTO[0]);
		} else return null;
	}

	/**
	 * Die Methode 'getJobOffersByUserAndCriteria' gibt alle in der Jobboerse hinterlegten Stellenangebote eines spezifischen 
	 * Nutzers und der angegebenen Kriterien zurueck.
	 * 
	 * @param Erwartet die Portal User Id als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse JobDTO zurueck.
	 */
	public JobDTO[] getJobOffersByUserAndCriteria(Long portalUserId, String jobActive, CountryDTO country, TerritoryDTO territory, int numberOfResults, int indexStart){	
		logger.info("Get JobOffer By User portalUserId :"+portalUserId);
		Collection<JobImpl> jobOffers = new TreeSet<JobImpl>();
		try {
			logger.info(" in country: "+country.getCountry());
			logger.info(" in territory: "+territory.getTerritory());
			jobOffers = jw.getJobOffersByPortalUserAndCriteria(portalUserId, JobActiveEnum.fromValue(jobActive), ca.getDomainObj(country), ta.getDomainObj(territory), numberOfResults, indexStart);
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return null;
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
		if(jobOffers!=null){
			//TODO Performance Engpass -> Lösung mit hibernate Transformer oder Ähnlich finden !!! 
			List<JobDTO> jobOffersDTO = new ArrayList<JobDTO>();
			Iterator<JobImpl> it = jobOffers.iterator();
			while(it.hasNext()){
				jobOffersDTO.add(ja.createDTO(it.next()));
			}
			return jobOffersDTO.toArray(new JobDTO[0]);
		} else return null;
	}
	
	/**
	 * Die Methode 'getJobOfferDetails' gibt das spezifische Stellenangebot, welches durch die eindeutige JobOffer Id
	 * abgefragt wurde zurueck. Kann das Stellenangebot nicht gefunden werden, wird NULL zurückgegen.
	 * 
	 * @param Erwartet die JobOffer Id als Parameter.
	 * @return Gibt ein Objekt der Klasse JobDTO zurueck.
	 * 
	 */
	public JobDTO getJobOfferDetails(Long jobOfferId){
		logger.info("Get joboffer details - jobOffer: "+jobOfferId);
		JobImpl jobDetails = null;
		try {
			
			jobDetails = jw.getJobOfferDetails(jobOfferId);
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return ja.createDTO(jobDetails);
		
	}
	
	
	/**
	 * Die Methode 'getJobOfferByCriteria' gibt alle in der Jobboerse hinterlegten Stellenangebote die 
	 * auf die Suchanfrage passen zurueck.
	 * 
	 * @param Erwartet den Such-String sowie ein ewentuell ausgewähltes Land oder einen Kontinent als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse JobDTO zurueck.
	 */
	public JobDTO[] getJobOfferByCriteria(String criteria, CountryDTO country, TerritoryDTO territory){//, CountryDTO country, TerritoryDTO territory
		logger.info("Get joboffer by criteria: "+criteria);
		Collection<JobImpl> jobOffers;
		try {
			jobOffers = jw.getJobOfferByCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory));
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
		List<JobDTO> jobOffersDTO = new ArrayList<JobDTO>();
		if(jobOffers!= null){
			//TODO Performance Engpass -> Lösung mit hibernate Transformer oder Ähnlich finden !!! 
			Iterator<JobImpl> it = jobOffers.iterator();
			while(it.hasNext()){
				jobOffersDTO.add(ja.createDTO(it.next()));
			}
		}
		return jobOffersDTO.toArray(new JobDTO[0]);
	}
	
	
	
	/**
	 * Die Methode 'getJobOfferSearchResultsByCriteria' gibt alle in der Jobboerse hinterlegten Stellenangebote die 
	 * auf die Suchanfrage passen zurueck. Dabei wird im Gegensatz zu 'getJobOfferByCriteria' nicht das gesamte Stellenobjekt
	 * sondern nur eine auf die Anzeige in einer Suchergebnissübersicht verkürzte Variante zurückgegeben
	 * 
	 * @param Erwartet den Such-String sowie ein ewentuell ausgewähltes Land oder einen Kontinent als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse JobSearchResultDTO zurueck.
	 */
	public JobSearchResultDTO[] getJobOfferSearchResultsByCriteria(String criteria, CountryDTO country, TerritoryDTO territory, Integer resultsAmount, Integer pageIndexStart){//, CountryDTO country, TerritoryDTO territory
		logger.info("Get joboffer search result  by criteria: "+criteria+"--- resultsAmount: "+resultsAmount+" --- pageIndexStart: "+pageIndexStart);
		Collection<JobSearchResultDTO> jobOffers;
		try {
			jobOffers = jw.getJobOfferByCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory),resultsAmount, pageIndexStart);
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
//		List<JobSearchResultDTO> jobOffersDTO= new ArrayList<JobSearchResultDTO>();
//		if(jobOffers!= null){
//			//TODO Performance Engpass -> Lösung mit hibernate Transformer oder Ähnlich finden !!! 
//			Iterator<JobImpl> it = jobOffers.iterator();
//			while(it.hasNext()){
//				jobOffersDTO.add(jsra.createDTO(it.next()));
//			}
//		}
		return jobOffers.toArray(new JobSearchResultDTO[0]);//DTO.toArray(new JobDTO[0]);
	}
	
	/**
	 * Die Methode 'getJobOfferSearchResultsAmountByCriteria' gibt die Anzahl der in der Jobboerse hinterlegten Stellenangebote die 
	 * auf die Suchanfrage passen zurueck. 
	 * 
	 * @param Erwartet den Such-String sowie ein ewentuell ausgewähltes Land oder einen Kontinent als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse JobSearchResultDTO zurueck.
	 */
	public Integer getJobOfferSearchResultsAmountByCriteria(String criteria, CountryDTO country, TerritoryDTO territory){//, CountryDTO country, TerritoryDTO territory
		logger.info("Get joboffer by search amount criteria: "+criteria);
		try {
			return jw.getJobOfferResultAmountByCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory));
		} catch (CountryNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (TerritoryNotFoundException e) {
			e.printStackTrace();
			return null;
		} 

	
	}
	
	
//	/**
//	 * Die Methode 'getJobOfferSearchResultsByCriteria' gibt alle in der Jobboerse hinterlegten Stellenangebote die 
//	 * auf die Suchanfrage passen zurueck. Dabei wird im Gegensatz zu 'getJobOfferByCriteria' nicht das gesamte Stellenobjekt
//	 * sondern nur eine auf die Anzeige in einer Suchergebnissübersicht verkürzte Variante zurückgegeben
//	 * 
//	 * @param Erwartet den Such-String sowie ein ewentuell ausgewähltes Land oder einen Kontinent als Parameter.
//	 * @return Gibt ein Array von Objekten der Klasse JobSearchResultDTO zurueck.
//	 */
//	public Object[][] getJobOfferSearchResultsByCriteria(String criteria, CountryDTO country, TerritoryDTO territory, Integer resultsAmount){//, CountryDTO country, TerritoryDTO territory
//		logger.info("Get joboffer search result  by criteria: "+criteria);
//		Collection<JobSearchResultDTO> jobOffers;
//		try {
//			jobOffers = jw.getJobOfferByCriteria(criteria, ca.getDomainObj(country), ta.getDomainObj(territory),resultsAmount);
//		} catch (CountryNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		} catch (TerritoryNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		} 
////		List<JobSearchResultDTO> jobOffersDTO= new ArrayList<JobSearchResultDTO>();
////		if(jobOffers!= null){
////			//TODO Performance Engpass -> Lösung mit hibernate Transformer oder Ähnlich finden !!! 
////			Iterator<JobImpl> it = jobOffers.iterator();
////			while(it.hasNext()){
////				jobOffersDTO.add(jsra.createDTO(it.next()));
////			}
////		}
//		Object result[][] =  new Array()[][];
//		
//		return jobOffers.toArray(new JobSearchResultDTO[0]);//DTO.toArray(new JobDTO[0]);
//	}
	
	
	/**
	 * Die Methode 'addJobOffer' ermoeglicht es fuer einen bestehenden Nutzer ein neues Stellenangebot zu veroeffentlichen.
	 * 
	 * @param Erwartet ein Objekt der Klasse JobDTO mit allen relevanten Stellendaten sowie die Portal User Id.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 * 
	 */
	public Long addJobOffer(JobDTO jobOffer, Long portalUserId){
		logger.info("Adding joboffer by portalUser: "+portalUserId);
		jobOffer.setJobOfferOwner(portalUserId);
		Long savedJobId = new Long(0);
		try {
			savedJobId = jw.addJobOffer(ja.createDomainObjByApdId(jobOffer),this.dataProvider);	
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return new Long(0);
		} catch (OccupationalFieldNotFoundException e) {
			e.printStackTrace();
			return new Long(0);
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return new Long(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new Long(0);
		}
		logger.info("Joboffer was successfully saved with JobOfferId: "+savedJobId);
		return savedJobId;
	}

	
	
	/**
	 * Die Methode 'applyToJobOffer' ermoeglicht es einem Nutzer der APD Plattform sich auf ein Stellenangebot zu bewerben.
	 * 
	 * @param Erwartet die Stellenangebots Id der Stelle auf die sich der Nutzer Bewerben moechte, die Portal User Id des Bewerbers sowie eine optionale Kontaktmitteilung als Parameter.
	 */
	public Boolean applyToJobOffer(Long jobOfferId, Long portalUserId, String contactNote){
		logger.info("Apply to joboffer - jobOfferId: "+jobOfferId+" portalUserId: "+portalUserId);
		try {
			jw.applyToJobOffer(jobOfferId,portalUserId,contactNote);
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
//	/**
//	 * Die Methode 'getJobOfferApplications' liefert alle Bewerbungsanfragen die ein Nutzer an einen/mehrere Stellenangebote gestellt hat.
//	 * 
//	 * @param Erwartet die APD User Id als Parameter.
//	 * @return Gibt ein Array von Objekten der Klasse JobApplicationDTO zurueck.
//	 */
//	public JobApplicationDTO[] getJobOfferApplications(Long apdUserId){
//		return null;
//	}
	
	/**
	 * Die Methode 'getReceivedJobOfferApplications' liefert alle Kontaktanfragen die ein Nutzer (etwa eine Organisation/Unternehmen) zu seinen 
	 * veroeffentlichten Stellenangebote erhalten hat.
	 * 
	 * @param Erwartet die Portal User Id als Parameter.
	 * @return Gibt ein Array von Objekten der Klasse JobApplicationDTO zurueck.
	 */
	public JobApplicationDTO[] getReceivedJobOfferApplications(Long portalUserId){
		logger.info("Get Received JobOffer Applications for user portalUserId: "+portalUserId);
		
		Collection<JobApplication> jobApplications;
		try {
			jobApplications = jw.getReceivedJobOfferApplications(portalUserId);
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		List<JobApplicationDTO> jobApplicationDTO = new ArrayList<JobApplicationDTO>();
		if(jobApplications!= null){
			//TODO Performance Engpass -> Lösung mit hibernate Transformer oder Ähnlich finden !!! 
			Iterator<JobApplication> it = jobApplications.iterator();
			while(it.hasNext()){
				jobApplicationDTO.add(jaa.createDTO(it.next()));
			}
		}
		
		return jobApplicationDTO.toArray(new JobApplicationDTO[0]);
		
	}
	
	
	/** 
	 * Die Methode 'modifyApplicantProfile' ermoeglicht es das bestehende Stellenangebot eines bestehendem Nutzers zu modifizieren.
	 * 
	 * @param Erwartet ein Objekt der Klasse JobDTO mit allen relevanten Stellendaten sowie die JobOffer Id.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean modifyJobOffer(JobDTO jobOffer, Long jobOfferId){
		logger.info("Modify JobOffer by JobOfferId :"+jobOfferId);
		jobOffer.setJobOfferId(jobOfferId);
		try {
			jw.modifyJobOffer(ja.updateDomainObjByApdId(jobOffer));
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
	
	
	/**
	 * Die Methode 'deleteJobOffer' ermoeglicht es ein bestehende Stellenangebot eines bestehendem Nutzers zu loeschen.
	 * 
	 * @param Erwartet die JobOffer Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 * @throws JobOfferNotFoundException 
	 */
	public Boolean deleteJobOffer(Long jobOfferId){
			logger.info("Delete JobOffer by JobOfferId :"+jobOfferId);
			try {
				jw.deleteJobOffer(jobOfferId);
			} catch (JobOfferNotFoundException e) {
				return false;
			} catch (PassedAttributeIsNullException e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	
//	/**
//	 * 
//	 * Die Methode 'getAutoCompleteList' gibt vollstaendige Such-Strings die auf Stellenangebote in der Jobboerse und 
//	 * auf die unvollstaendige Suchanfrage passen zurueck.
//	 * 
//	 * @param Erwartet den Such-String als Parameter.
//	 * @return Gibt ein Array von Strings zurueck.
//	 */
//	public String[] getAutoCompleteList(String expression){
//		logger.info("Get autocompletelist for expression: "+expression);
//		return jw.getAutoCompleteResults(expression).toArray(new String[0]);
//	}
//	
	
//	/**
//	 * Die Methode 'getSimilarJobOffers' gibt zu dem uebergebenen Stellenangebot inhaltlich aehnliche Stellenangebote zurueck.
//	 * 
//	 * @param Erwartet eine JobOffer Id als Parameter.
//	 * @return Gibt ein Array von Objekten der Klasse JobDTO zurueck.
//	 */
//	public JobDTO[] getSimilarJobOffers(Long jobOfferId){
//		logger.info("Get similar jobOffers by JobOfferId: "+jobOfferId);
//		Collection<JobImpl> jobOffers = jw.getMoreLikeThis(jobOfferId);
//		Collection<JobDTO> jobOffersDTO = new TreeSet<JobDTO>();
//		if(jobOffers!=null){
//			//TODO Performance Engpass -> Lösung mit hibernate Transformer oder Ähnlich finden !!! 
//			Iterator<JobImpl> it = jobOffers.iterator();
//			while(it.hasNext()){
//				jobOffersDTO.add(ja.createDTO(it.next()));
//			}
//		}
//		return jobOffersDTO.toArray(new JobDTO[0]);
//	}
	
//	public JobImpl[] getJobOfferBySectorAndCountry(Long[] sectorIds, Long[] CountryIds){
//		return jw.getJobOfferBySector(sectorIds).toArray(new JobImpl[0]);
//	}
}
