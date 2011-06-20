/**
 * 
 */
package net.agef.jobexchange.webservice;

import javax.servlet.ServletContext;

import net.agef.jobexchange.application.DataProviderWorker;
import net.agef.jobexchange.application.UserWorker;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.ClientIPNotAuthorizedException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.InwentUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotDeletedException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.webservice.adapter.UserAssembler;
import net.agef.jobexchange.webservice.entities.AlumniRoleDTO;
import net.agef.jobexchange.webservice.entities.OrganisationRoleDTO;
import net.agef.jobexchange.webservice.entities.UserDTO;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * Die Klasse UserWS ist eine Webservice Skeleton Klasse und haelt alle Methoden zur 
 * Interaktion mit Nutzerdaten im Kontext der Jobboerse bereit.
 *
 * @author Andreas Pursian
 */
public class UserWS {
	
	private Logger logger = Logger.getLogger(UserWS.class);
	private UserWorker uw;
	private UserAssembler ua;
	private MessageContext msgCtx;
	private ServletContext axisServletContext;
	private DataProviderWorker dw;
	private DataProvider dataProvider;
	
	@SuppressWarnings("static-access")
	public UserWS() throws AxisFault {
		try {
			
			this.msgCtx = MessageContext.getCurrentMessageContext(); 
			this.axisServletContext = (ServletContext)msgCtx.getProperty("transport.http.servletContext");
			this.uw = (UserWorker)axisServletContext.getAttribute("UserWorker.UserWorkerService");
			this.dw = (DataProviderWorker)axisServletContext.getAttribute("DataProviderWorker.DataProviderWorkerService");
			this.ua = (UserAssembler)axisServletContext.getAttribute("UserAssembler.UserAssemblerService");
		
			//Ueberpruefung auf korrekten und erlaubten Datenprovider anhand der zugreifenden Client IP
			String remoteClientAddress = (String) msgCtx.getProperty(msgCtx.REMOTE_ADDR);
			logger.info("UserWS - RemoteClientIP: "+remoteClientAddress);		
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
	 * Die Methode 'addAlumniUser' dient dazu einen neuen Nutzer mit Alumnirolle in der Jobboerse zu registrieren. Die Registrierung eines Nutzers ist Grundlage fuer alle 
	 * weiteren Operationen wie das Hinzufuegen eines Bewerberprofils oder das Veroeffentlichen eines Stellenangebotes.
	 * 
	 * Wichtig ist hierbei das die konkrete Rolle die der Nutzer innerhalb der APD einnimmt (also entweder Alumni oder Organisation) korrekt uebergeben wird.
	 * Dies geschieht indem dem Parameter 'userRole' eine konkrete Instanz der Klasse AlumniRoleDTO (fuer die Alumni Rolle)
	 * uebergeben wird.
	 * 
	 * @param Erwartet ein Objekt der Klasse UserDTO mit allen relevanten Nutzerdaten, die APD User Id sowie eine Instanz der konkreten Nutzerrolle.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean addAlumniUser(UserDTO user, Long portalUserId, Byte[] portalId, AlumniRoleDTO userRole){
		logger.info("Adding alumniUser by portalUserId: "+portalUserId);
		try {
			User us = uw.getUserByPortalId(portalUserId);
			logger.info(us);
		} catch (APDUserNotFoundException e) {
			user.setPortalUserId(portalUserId);
			user.setPortalId(portalId[0]);
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
		logger.info("Adding alumniUser by apdUserId failed, user already exist!");
		return false;
			
		
	}
	
//	/**
//	 * Die Methode 'addAlumniUser' dient dazu einen neuen Nutzer mit Alumnirolle in der Jobboerse zu registrieren. Die Registrierung eines Nutzers ist Grundlage fuer alle 
//	 * weiteren Operationen wie das Hinzufuegen eines Bewerberprofils oder das Veroeffentlichen eines Stellenangebotes.
//	 * 
//	 * Wichtig ist hierbei das die konkrete Rolle die der Nutzer innerhalb der APD einnimmt (also entweder Alumni oder Organisation) korrekt uebergeben wird.
//	 * Dies geschieht indem dem Parameter 'userRole' eine konkrete Instanz der Klasse AlumniRoleDTO (fuer die Alumni Rolle)
//	 * uebergeben wird.
//	 * 
//	 * @param Erwartet ein Objekt der Klasse UserDTO mit allen relevanten Nutzerdaten, die APD User Id sowie eine Instanz der konkreten Nutzerrolle.
//	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
//	 */
//	public Boolean addInwentAlumniUser(UserDTO user, Long inwentUserId, AlumniRoleDTO userRole){
//		logger.info("Adding alumniUser by inwentUserId: "+inwentUserId);
//		try {
//			User us = uw.getUserByInwentId(inwentUserId);
//			logger.info(us);
//		} catch (InwentUserNotFoundException e) {
//			user.setInwentUserId(inwentUserId);
//			logger.info("Inwent User mit ID " + user.getInwentUserId());
//			user.setUserRole(userRole);
//			try {
//				uw.addUser(ua.createDomainObj(user),this.dataProvider);
//			} catch (EnumValueNotFoundException e1) {
//				e1.printStackTrace();
//				return false;
//			} catch (CountryNotFoundException e2) {
//				e2.printStackTrace();
//				return false;
//			} catch (ObjectNotSavedException e3) {
//				e3.printStackTrace();
//				return false;
//			} catch (PassedAttributeIsNullException e4) {
//				e4.printStackTrace();
//				return false;
//			}
//			return true;
//		}
//		logger.info("Adding inwentAlumniUser by inwentUserId failed, user already exist!");
//		return false;
//			
//		
//	}
	
	/**
	 * Die Methode 'addOrganisationUser' dient dazu einen neuen Nutzer mit der Organisationsrolle in der Jobboerse zu registrieren. Die Registrierung eines Nutzers ist Grundlage fuer alle 
	 * weiteren Operationen wie das Hinzufuegen eines Bewerberprofils oder das Veroeffentlichen eines Stellenangebotes.
	 * 
	 * Wichtig ist hierbei das die konkrete Rolle die der Nutzer innerhalb der APD einnimmt (also entweder Alumni oder Organisation) korrekt uebergeben wird.
	 * Dies geschieht indem dem Parameter 'userRole' vom eine konkrete Instanz der Klasse
	 * OrganisationRoleDTO (fuer die Organisations Rolle) uebergeben wird.
	 * 
	 * @param Erwartet ein Objekt der Klasse UserDTO mit allen relevanten Nutzerdaten, die APD User Id sowie eine Instanz der konkreten Nutzerrolle.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean addOrganisationUser(UserDTO user, Long portalUserId, Byte[] portalId, OrganisationRoleDTO userRole){
		logger.info("Adding orgUser by portalUserId: "+portalUserId);
		try {
			uw.getUserByPortalId(portalUserId);
		} catch (APDUserNotFoundException e) {
			user.setPortalUserId(portalUserId);
			user.setPortalId(portalId[0]);
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
		logger.info("Adding orgUser by apdUserId failed, user already exist!");
		return false;

	}
	

	/** 
	 * Die Methode 'modifyUser' ermoeglicht es das bestehende Nutzerprofil eines bestehendem Nutzers zu modifizieren.
	 * 
	 * @param Erwartet ein Objekt der Klasse UserDTO mit allen relevanten Nutzerdaten sowie die APD User Id.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean modifyUser(UserDTO user, Long portalUserId, Byte[] portalId){
		logger.info("Modify user by portalUserId: "+portalUserId);
		user.setPortalUserId(portalUserId);
		user.setPortalId(portalId[0]);
		try {
//			uw.modifyUser(ua.updateDomainObj(user, user.getApdUserId()));
			uw.modifyUser(ua.updateDomainObjByApdId(user, user.getApdUserId()));
		} catch (APDUserNotFoundException e) {
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
	
	
	/**
	 * Die Methode 'getUserByUserId' liefert ein Objekt vom Typ UserDTO welches alle Relevanten Stammdaten eines Nutzers 
	 * enthält.
	 * 
	 * @param Erwartet die APD User Id als Parameter.
	 * @return Gibt ein Objekt vom Typ UserDTO zurueck. Sollte kein Nutzer mit der übergebenen NutzerId gefunden werden 
	 * koennen, wird null zuruckgegeben.
	 */
//	public UserDTO getUserByUserId(Long apdUserId){
//		try {
//			return ua.createDTO(uw.getUserByAPDId(apdUserId));
//		} catch (APDUserNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
//		 
//	}

	
	/**
	 * Die Methode 'deleteUser' ermoeglicht es einen bestehenden Nutzer inklusive aller von ihm eingestellten Stellenangebote sowie eines
	 * moeglicherweise vorhandenen Bewerber-/Expertenprofils zu loeschen.
	 * 
	 * @param Erwartet die APD User Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean deleteUser(Long portalUserId){
		logger.info("Delete user by portalUserId: "+portalUserId);
		User user;
		try {
			user = uw.getUserByPortalId(portalUserId);
			uw.deleteUser(user);
		} catch (APDUserNotFoundException e) {
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
	
	
	/**
	 * 
	 * Die Methode 'checkIfUserExist' überprüft ob eine konkreten Nutzer mit seinem Profil bereits in der Jobbörse
	 * existiert.
	 * 
	 * @param Erwartet die APD User Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean checkIfUserExist(Long portalUserId) {
		logger.info("Check if user exist by portalUserId: "+portalUserId);
		try {
			if(uw.getUserByPortalId(portalUserId) != null){
				return true;
			} else return false;
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	/**
//	 * 
//	 * Die Methode 'checkIfUserExist' überprüft ob eine konkreten Nutzer mit seinem Profil bereits in der Jobbörse
//	 * existiert.
//	 * 
//	 * @param Erwartet die APD User Id als Parameter.
//	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
//	 */
//	public Boolean checkIfInwentUserExist(Long inwentUserId) {
//		logger.info("Check if user exist by inwentUserId: "+inwentUserId);
//		try {
//			if(uw.getUserByInwentId(inwentUserId) != null){
//				return true;
//			} else return false;
//		} catch (InwentUserNotFoundException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	
	/**
	 * Die Methode 'switchContactAddress' ermoeglicht es die aktive Kontaktadresse eines bestehenden Nutzer umzuschalten. Bei
	 * jedem Aufruf wird die zur Zeit aktive Adresse zur sekundaeren Adresse und die bisher sekundaere Adresse zur aktiven Addresse.
	 * 
	 * @param Erwartet die APD User Id als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean switchContactAddress(Long portalUserId){
		logger.info("Switch Contact Address by portalUserId: "+portalUserId);
		try {
			uw.switchContactAddressByPortalUserId(portalUserId);
		} catch (APDUserNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (ObjectNotSavedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Die Methode 'changeUserRoleToAlumni' wechselt die Rolle eines Nutzers zur Alumni Rolle.
	 * 
	 * @param Erwartet die Portal User Id und eine Instanz der Klasse AlumniRoleDTO als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean changeUserRoleToAlumni(Long portalUserId, AlumniRoleDTO role){
		logger.info("Change user role to alumni by portalUserId: "+portalUserId);
		try {
			ua.updateDomainObjRole(role, portalUserId);
		} catch (APDUserNotFoundException e) {
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
	
	/**
	 * Die Methode 'changeUserRoleToOrganisation' wechselt die Rolle eines Nutzers zur Organisations Rolle.
	 * 
	 * @param Erwartet die Portal User Id und eine Instanz der Klasse OrganisationRoleDTO als Parameter.
	 * @return Gibt ein Objekt vom Typ Boolean zurueck. Im Erfolgsfall traegt dieses den Wert 'true' im Fehlerfall 'false'.
	 */
	public Boolean changeUserRoleToOrganisation(Long portalUserId, OrganisationRoleDTO role){
		logger.info("Change user role to org by apdUserId: "+portalUserId);	
		try {
			ua.updateDomainObjRole(role, portalUserId);
		} catch (APDUserNotFoundException e) {
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
	

}
