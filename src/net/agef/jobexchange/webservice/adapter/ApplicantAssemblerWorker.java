/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import net.agef.jobexchange.application.FieldOfOccupationWorker;
import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.domain.AvailabilityEnum;
import net.agef.jobexchange.domain.ContractDurationEnum;
import net.agef.jobexchange.domain.CurrentStatusEnum;
import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.Education;
import net.agef.jobexchange.domain.ExperienceDurationEnum;
import net.agef.jobexchange.domain.LanguageSkill;
import net.agef.jobexchange.domain.LanguageSkillsEnum;
import net.agef.jobexchange.domain.PortalIdentifierEnum;
import net.agef.jobexchange.domain.PublicationTypeEnum;
import net.agef.jobexchange.domain.TeamSizeEnum;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.domain.WorkExperience;
import net.agef.jobexchange.domain.WorkUserType;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.exceptions.InwentUserNotFoundException;
import net.agef.jobexchange.exceptions.LanguageNotFoundException;
import net.agef.jobexchange.integration.ApplicantDAO;
import net.agef.jobexchange.integration.UserDAO;
import net.agef.jobexchange.webservice.adapter.util.DateUtil;
import net.agef.jobexchange.webservice.entities.ApplicantDTO;
import net.agef.jobexchange.webservice.entities.EducationDTO;
import net.agef.jobexchange.webservice.entities.LanguageSkillDTO;
import net.agef.jobexchange.webservice.entities.WorkExperienceDTO;
import net.agef.jobexchange.webservice.entities.WorkUserTypeDTO;

/**
 * @author Andreas Pursian
 * 
 */
public class ApplicantAssemblerWorker implements ApplicantAssembler {

	private CountryAssembler countryAssembler;
	private LanguageSkillAssembler languageSkillAssembler;
	private EducationAssembler educationAssembler;
	private WorkExperienceAssembler workExperienceAssembler;
	private FieldOfOccupationWorker fieldOfOccupationWorker;
	private IndustrySectorAssembler industrySectorAssembler;
	private WorkUserTypeAssembler workTypeAssembler;
	private PortalIdAssembler portalIdAssembler;
	private UserDAO userDAO;
	private ApplicantDAO applicantDAO;
	private Logger logger;

	public ApplicantAssemblerWorker(Logger logger, FieldOfOccupationWorker fieldOfOccupation, UserDAO userDAO, ApplicantDAO applicantDAO, CountryAssembler countryAssembler, LanguageSkillAssembler languageAssembler,
			ContactPersonAssembler contactPersonAssembler, EducationAssembler educationAssembler, WorkExperienceAssembler workExperienceAssembler, IndustrySectorAssembler industryAssembler,
			WorkUserTypeAssembler workTypeAssembler, PortalIdAssembler portalIdAssembler) {
		this.logger = logger;
		this.countryAssembler = countryAssembler;
		this.languageSkillAssembler = languageAssembler;
		this.educationAssembler = educationAssembler;
		this.workExperienceAssembler = workExperienceAssembler;
		this.fieldOfOccupationWorker = fieldOfOccupation;
		this.industrySectorAssembler = industryAssembler;
		this.workTypeAssembler = workTypeAssembler;
		this.portalIdAssembler = portalIdAssembler;
		// this.aw = applicantWorker;
		this.applicantDAO = applicantDAO;
		this.userDAO = userDAO;
	}

	@Override
	public ApplicantDTO createDTOWithPortalId(Applicant applicant) {
		return createDTO(applicant, applicant.getApplicantProfileOwner().getPortalUserId());
	}

	@Override
	public ApplicantDTO createDTOWithCobraId(Applicant applicant) {
		return createDTO(applicant, applicant.getApplicantProfileOwner().getCobraSuperId());
	}

	/*
	 * Hilfsfunktion, die Codeduplizierung vermeidet und OwnerId anhand der in
	 * der Signatur uebergebenen ID setzt.
	 */
	private ApplicantDTO createDTO(Applicant applicant, Long portalUserId) {
		ApplicantDTO dto = new ApplicantDTO();

		if (applicant != null) {
			if (applicant.getPortalIdList() != null) {
				dto.setPortalId(portalIdAssembler.createDTO(applicant.getPortalIdList()));
			}
			dto.setAdditionalRemarks(applicant.getAdditionalRemarks());
			dto.setAdditionalSkills(applicant.getAdditionalSkills());
			dto.setApplicantProfileId(applicant.getApplicantProfileId());
			dto.setApplicantProfileOwnerId(portalUserId);
			if (applicant.getComputerSkills() != null) {
				dto.setComputerSkills(applicant.getComputerSkills().toString());
			}
			dto.setComputerSkillsComments(applicant.getComputerSkillsComments());
			if (applicant.getDrivingLicence() != null) {
				dto.setDrivingLicence(applicant.getDrivingLicence().toString());
			}
			dto.setDrivingLicenceComments(applicant.getDrivingLicenceComments());
			if (applicant.getDurationOfContract() != null) {
				dto.setDurationOfContract(applicant.getDurationOfContract().toString());
			}

			if (applicant.getFurtherEducation() != null) {
				EducationDTO[] furtherEducationArray = new EducationDTO[applicant.getFurtherEducation().size()];
				int counter = 0;
				for (Iterator<Education> it = applicant.getFurtherEducation().iterator(); it.hasNext();) {
					furtherEducationArray[counter] = educationAssembler.createDTO(it.next());
					counter++;
				}
				dto.setFurtherEducation(furtherEducationArray);
			}

			if (applicant.getCurrentStatus() != null) {
				dto.setCurrentStatus(applicant.getCurrentStatus().toString());
			}

			if(applicant.getFurtherOnlineActivities() != null){
				dto.setFurtherOnlineActivities(applicant.getFurtherOnlineActivities().value());
			}
			dto.setFurtherOnlineActivitiesComments(applicant.getFurtherOnlineActivitiesComments());
			
			if(applicant.getPublications() != null){
				dto.setPublications(applicant.getPublications().value());
			}
			dto.setPublicationsComments(applicant.getPublicationsComments());
			
			if(applicant.getReferencesAndCertificates() != null){
				dto.setReferencesAndCertificates(applicant.getReferencesAndCertificates().value());
			}
			dto.setReferencesAndCertificatesComments(applicant.getReferencesAndCertificatesComments());

			dto.setLookingFor(applicant.getLookingFor());

			dto.setOffering(applicant.getOffering());

			if (applicant.getWorkUserTypes() != null) {
				WorkUserTypeDTO[] workTypeArray = new WorkUserTypeDTO[applicant.getWorkUserTypes().size()];
				//logger.info("# Anzahl WorkUserTypes: " + applicant.getWorkUserTypes().size());
				int counter = 0;
				for(WorkUserType workUserType : applicant.getWorkUserTypes()){	
					workTypeArray[counter] = workTypeAssembler.createDTO(workUserType);
					counter++;
				}
				dto.setWorkTypes(workTypeArray);
			}

			if (applicant.getAvailability() != null) {
				dto.setAvailability(applicant.getAvailability().toString());
			}

			if (applicant.getHighestDegree() != null) {
				dto.setHighestDegree(educationAssembler.createDTO(applicant.getHighestDegree()));
			}
			if (applicant.getLanguageSkillsEnglish() != null) {
				dto.setLanguageSkillsEnglish(applicant.getLanguageSkillsEnglish().toString());
			}
			if (applicant.getLanguageSkillsGerman() != null) {
				dto.setLanguageSkillsGerman(applicant.getLanguageSkillsGerman().toString());
			}

			if (applicant.getLanguageSkillsOther() != null) {
				LanguageSkillDTO[] languageSkillsOtherArray = new LanguageSkillDTO[applicant.getLanguageSkillsOther().size()];
				int counter = 0;
				for (Iterator<LanguageSkill> it = applicant.getLanguageSkillsOther().iterator(); it.hasNext();) {
					languageSkillsOtherArray[counter] = languageSkillAssembler.createDTO(it.next());
					counter++;
				}
				dto.setLanguageSkillsOther(languageSkillsOtherArray);
			}

			dto.setLocationRemarks(applicant.getLocationRemarks());
			if (applicant.getManagementExperience() != null) {
				dto.setManagementExperience(applicant.getManagementExperience().toString());
			}
			if (applicant.getManagementExperienceDuration() != null) {
				dto.setManagementExperienceDuration(applicant.getManagementExperienceDuration().toString());
			}
			dto.setManagementExperienceRemarks(applicant.getManagementExperienceRemarks());

			if (applicant.getManagementExperienceSector() != null) {
				dto.setManagementExperienceSector(applicant.getManagementExperienceSector().getSectorId());
			}
			if (applicant.getManagementExperienceTeamSize() != null) {
				dto.setManagementExperienceTeamSize(applicant.getManagementExperienceTeamSize().toString());
			}
			if (applicant.getPreferredFieldOfActivity() != null) {
				dto.setPreferredFieldOfActivity(applicant.getPreferredFieldOfActivity().getSectorId().toString());
			}
			if (applicant.getPreferredLocation() != null) {
				dto.setPreferredLocation(countryAssembler.createDTO(applicant.getPreferredLocation()));
			}

			if (applicant.getWorkExperience() != null) {
				WorkExperienceDTO[] workExperiencArray = new WorkExperienceDTO[applicant.getWorkExperience().size()];
				int counter = 0;
				for (Iterator<WorkExperience> it = applicant.getWorkExperience().iterator(); it.hasNext();) {
					workExperiencArray[counter] = workExperienceAssembler.createDTO(it.next());
					counter++;
				}
				dto.setWorkExperience(workExperiencArray);
			}
			
			dto.setAvailableFromDate(DateUtil.date2Calendar(applicant.getAvailableFromDate()));
			
			if(applicant.getPublicationType() != null){
				dto.setPublicationType(applicant.getPublicationType().value());
			}
			dto.setProfilePhoto(applicant.getProfilePhoto());

		}

		return dto;
	}

	@Override
	public Applicant createDomainObj(ApplicantDTO dto) throws APDUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		User user;
		try {
			user = userDAO.findPortalUserByID(dto.getApplicantProfileOwnerId());
		} catch (Exception e) {
			throw new APDUserNotFoundException();
		}
		Applicant applicant;
		if (user != null) {
			applicant = new Applicant(user);
		} else
			throw new APDUserNotFoundException();

		return createDomainObjectFromApplicant(dto, applicant);
	}

	@Override
	public Applicant createDomainObjByCobraId(ApplicantDTO dto) throws CobraUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		User user;
		try {
			user = userDAO.findCobraUserByID(dto.getApplicantProfileOwnerId(), false);
		} catch (Exception e) {
			throw new CobraUserNotFoundException();
		}
		Applicant applicant;
		if (user != null) {
			applicant = new Applicant(user);
		} else
			throw new CobraUserNotFoundException();

		return createDomainObjectFromApplicant(dto, applicant);
	}
	

//	@Override
//	public Applicant createDomainObjByInwentId(ApplicantDTO dto) throws InwentUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
//		User user;
//		try {
//			user = userDAO.findInwentUserByID(dto.getApplicantProfileOwnerId());
//		} catch (Exception e) {
//			throw new InwentUserNotFoundException();
//		}
//		Applicant applicant;
//		if(user != null){
//			applicant = new Applicant(user);
//		} else throw new InwentUserNotFoundException();
//		
//		
//		return createDomainObjectFromApplicant(dto, applicant);
//	}

	/*
	 * Hilfsmethode die duplizierten Code vermeidet und Domain Objekt erstellt
	 * aus DTO
	 */
	private Applicant createDomainObjectFromApplicant(ApplicantDTO dto, Applicant applicant) throws EnumValueNotFoundException, IndustrySectorNotFoundException, CountryNotFoundException {
		if (dto != null) {
//			applicant.setAdditionalRemarks(dto.getAdditionalRemarks());
//			applicant.setAdditionalSkills(dto.getAdditionalSkills());
			applicant.setApplicantProfileId(dto.getApplicantProfileId());
			if (dto.getPortalId()!=null && dto.getPortalId().length > 0){
				applicant.setPortalIdList(portalIdAssembler.createDomainObj(dto.getPortalId()));
			}

			if (dto.getComputerSkills() != null) {
				applicant.setComputerSkills(DecisionYesNoEnum.fromValue(dto.getComputerSkills()));
			}
			applicant.setComputerSkillsComments(dto.getComputerSkillsComments());
			
//			if (dto.getDrivingLicence() != null) {
//				applicant.setDrivingLicence(DecisionYesNoEnum.fromValue(dto.getDrivingLicence()));
//			}
//			applicant.setDrivingLicenceComments(dto.getDrivingLicenceComments());
//			if (dto.getDurationOfContract() != null) {
//				applicant.setDurationOfContract(ContractDurationEnum.fromValue(dto.getDurationOfContract()));
//			}

			if (dto.getFurtherEducation() != null) {
				List<Education> furtherEducation = new ArrayList<Education>();
				for (int i = 0; i < dto.getFurtherEducation().length; i++) {
					Education edu = educationAssembler.createDomainObj(dto.getFurtherEducation()[i]);
					/*
					 * an dieser Stelle wird ein eindeutiger Bezeichner gesetzt
					 * über den die compareTo Methode des Objektes die
					 * Reihenfolge der Objekte in einer Collection bestimmen
					 * kann
					 */
					edu.setId(i);
					furtherEducation.add(edu);
				}
				applicant.setFurtherEducation(furtherEducation);
			}

			if (dto.getCurrentStatus() != null) {
				applicant.setCurrentStatus(CurrentStatusEnum.fromValue(dto.getCurrentStatus()));
			}

			applicant.setLookingFor(dto.getLookingFor());

			applicant.setOffering(dto.getOffering());

			if (dto.getWorkTypes() != null) {
				List<WorkUserType> workTypes = new ArrayList<WorkUserType>();
				for (int i = 0; i < dto.getWorkTypes().length; i++) {
					try{
						WorkUserType workUserType = workTypeAssembler.createDomainObj(dto.getWorkTypes()[i]);
						logger.info(workUserType.getWorkType().toString());
						/*
						 * an dieser Stelle wird ein eindeutiger Bezeichner gesetzt
						 * über den die compareTo Methode des Objektes die
						 * Reihenfolge der Objekte in einer Collection bestimmen
						 * kann
						 */
						workUserType.setId(i);
//						workUserType.setApplicant(applicant);
						workTypes.add(workUserType);
					}
					catch(EnumValueNotFoundException e){
						e.printStackTrace();
					}

				}
				applicant.setWorkUserTypes(workTypes);
			}

			if (dto.getAvailability() != null) {
				applicant.setAvailability(AvailabilityEnum.valueOf(dto.getAvailability()));
			}

			if (dto.getHighestDegree() != null) {
				applicant.setHighestDegree(educationAssembler.createDomainObj(dto.getHighestDegree()));
			}
			if (dto.getLanguageSkillsEnglish() != null) {
				applicant.setLanguageSkillsEnglish(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsEnglish()));
			}
			if (dto.getLanguageSkillsGerman() != null) {
				applicant.setLanguageSkillsGerman(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsGerman()));
			}

			if (dto.getLanguageSkillsOther() != null) {
				List<LanguageSkill> languageSkillsOther = new ArrayList<LanguageSkill>();
				for (int i = 0; i < dto.getLanguageSkillsOther().length; i++) {
					try {
						LanguageSkill langSkill = languageSkillAssembler.createDomainObj(dto.getLanguageSkillsOther()[i]);
						/*
						 * an dieser Stelle wird ein eindeutiger Bezeichner
						 * gesetzt über den die compareTo Methode des Objektes
						 * die Reihenfolge der Objekte in einer Collection
						 * bestimmen kann
						 */
						langSkill.setId(i);
						languageSkillsOther.add(langSkill);
					} catch (LanguageNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				applicant.setLanguageSkillsOther(languageSkillsOther);
			}
			
			if (dto.getReferencesAndCertificates() != null) {
				applicant.setReferencesAndCertificates(DecisionYesNoEnum.fromValue(dto.getReferencesAndCertificates()));
			}
			applicant.setReferencesAndCertificatesComments(dto.getReferencesAndCertificatesComments());
			
			if (dto.getPublications() != null) {
				applicant.setPublications(DecisionYesNoEnum.fromValue(dto.getPublications()));
			}
			applicant.setPublicationsComments(dto.getPublicationsComments());
			
			if (dto.getFurtherOnlineActivities() != null) {
				applicant.setFurtherOnlineActivities(DecisionYesNoEnum.fromValue(dto.getFurtherOnlineActivities()));
			}
			applicant.setFurtherOnlineActivitiesComments(dto.getFurtherOnlineActivitiesComments());
//			applicant.setLocationRemarks(dto.getLocationRemarks());
//			if (dto.getManagementExperience() != null) {
//				applicant.setManagementExperience(DecisionYesNoEnum.fromValue(dto.getManagementExperience()));
//			}
//			if (dto.getManagementExperienceDuration() != null) {
//				applicant.setManagementExperienceDuration(ExperienceDurationEnum.fromValue(dto.getManagementExperienceDuration()));
//			}
//			applicant.setManagementExperienceRemarks(dto.getManagementExperienceRemarks());
//
//			if (dto.getManagementExperienceSector() != null) {
//				applicant.setManagementExperienceSector(fieldOfOccupationWorker.getIndustrySectorById(dto.getManagementExperienceSector()));
//			}
//			if (dto.getManagementExperienceTeamSize() != null) {
//				applicant.setManagementExperienceTeamSize(TeamSizeEnum.fromValue(dto.getManagementExperienceTeamSize()));
//			}
//			if (dto.getPreferredFieldOfActivity() != null) {
//				// applicant.setPreferredFieldOfActivity(fw.getIndustrySectorById(dto.getPreferredFieldOfActivity()));
//				applicant.setPreferredFieldOfActivity(industrySectorAssembler.getDomainObj(dto.getPreferredFieldOfActivity()));
//			}
//			if (dto.getPreferredLocation() != null) {
//				applicant.setPreferredLocation(countryAssembler.getDomainObj(dto.getPreferredLocation()));
//			}

			if (dto.getWorkExperience() != null) {
				List<WorkExperience> workExperience = new ArrayList<WorkExperience>();
				for (int i = 0; i < dto.getWorkExperience().length; i++) {
					WorkExperience workExp = workExperienceAssembler.createDomainObj(dto.getWorkExperience()[i]);
					/*
					 * an dieser Stelle wird ein eindeutiger Bezeichner gesetzt
					 * über den die compareTo Methode des Objektes die
					 * Reihenfolge der Objekte in einer Collection bestimmen
					 * kann
					 */
					workExp.setId(i);
					workExperience.add(workExp);
				}
				applicant.setWorkExperience(workExperience);
			}
		}
		if(dto.getAvailableFromDate() != null){
			applicant.setAvailableFromDate(DateUtil.calendar2Date(dto.getAvailableFromDate()));
		}
		
		if(dto.getPublicationType()!=null){
			applicant.setPublicationType(PublicationTypeEnum.valueOf(dto.getPublicationType()));
		}
		
		if(dto.getProfilePhoto() != null) {
			applicant.setProfilePhoto(dto.getProfilePhoto());
		}
		return applicant;
	}

	@Override
	public Applicant updateDomainObj(ApplicantDTO dto) throws ApplicantProfileNotFoundException, APDUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException,
			CountryNotFoundException {
		Applicant applicant;
		try {
			// applicant =
			// applicantDAO.findApplicantDataByProfileId(dto.getApplicantProfileId());
			applicant = applicantDAO.doRetrieve(dto.getApplicantProfileId(), true);
		} catch (Exception e) {
			throw new APDUserNotFoundException();
		}
		
		return updateDomainObj(dto, applicant); 
	}

	@Override
	public Applicant updateDomainObjByCobraId(ApplicantDTO dto) throws ApplicantProfileNotFoundException, APDUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException,
			CountryNotFoundException {
		Applicant applicant;
		try {
			// applicant =
			// applicantDAO.findApplicantDataByProfileId(dto.getApplicantProfileId());
			applicant = applicantDAO.findApplicantProfileByCobraId(dto.getApplicantProfileOwnerId());
		} catch (ApplicantProfileNotFoundException e) {
			applicant = null;
		}
		
		return updateDomainObj(dto, applicant);
	}
	
//	@Override
//	public Applicant updateDomainObjByInwentId(ApplicantDTO dto) throws ApplicantProfileNotFoundException, EnumValueNotFoundException, IndustrySectorNotFoundException, CountryNotFoundException{
//		Applicant applicant;
//		try {
//			// applicant =
//			// applicantDAO.findApplicantDataByProfileId(dto.getApplicantProfileId());
//			applicant = applicantDAO.findApplicantProfileByInwentId(dto.getApplicantProfileOwnerId());
//		} catch (ApplicantProfileNotFoundException e) {
//			applicant = null;
//		}
//		
//		return updateDomainObj(dto, applicant);
//	}

	
	private Applicant updateDomainObj(ApplicantDTO dto, Applicant applicant) throws ApplicantProfileNotFoundException, EnumValueNotFoundException, IndustrySectorNotFoundException,
			CountryNotFoundException {
		if (applicant != null) {
		} else
			throw new ApplicantProfileNotFoundException();

		if (dto != null) {
			if (dto.getPortalId()!=null){
				applicant.setPortalIdList(portalIdAssembler.updateDomainObj(dto.getPortalId(), applicant.getPortalIdList()));
			}
			
			applicant.setAdditionalRemarks(dto.getAdditionalRemarks());
			applicant.setAdditionalSkills(dto.getAdditionalSkills());
			applicant.setApplicantProfileId(dto.getApplicantProfileId());
			
			if (dto.getComputerSkills() != null) {
				applicant.setComputerSkills(DecisionYesNoEnum.fromValue(dto.getComputerSkills()));
			}
			applicant.setComputerSkillsComments(dto.getComputerSkillsComments());
			
			if (dto.getReferencesAndCertificates() != null) {
				applicant.setReferencesAndCertificates(DecisionYesNoEnum.fromValue(dto.getReferencesAndCertificates()));
			}
			applicant.setReferencesAndCertificatesComments(dto.getReferencesAndCertificatesComments());
			
			if (dto.getPublications() != null) {
				applicant.setPublications(DecisionYesNoEnum.fromValue(dto.getPublications()));
			}
			applicant.setPublicationsComments(dto.getPublicationsComments());
			
			if (dto.getFurtherOnlineActivities() != null) {
				applicant.setFurtherOnlineActivities(DecisionYesNoEnum.fromValue(dto.getFurtherOnlineActivities()));
			}
			applicant.setFurtherOnlineActivitiesComments(dto.getFurtherOnlineActivitiesComments());
			
			if (dto.getDrivingLicence() != null) {
				applicant.setDrivingLicence(DecisionYesNoEnum.fromValue(dto.getDrivingLicence()));
			}
			applicant.setDrivingLicenceComments(dto.getDrivingLicenceComments());
			if (dto.getDurationOfContract() != null) {
				applicant.setDurationOfContract(ContractDurationEnum.fromValue(dto.getDurationOfContract()));
			}

			if (dto.getFurtherEducation() != null && dto.getFurtherEducation().length > 0) {

				List<Education> furtherEducation = applicant.getFurtherEducation();
				if (furtherEducation.size() > dto.getFurtherEducation().length) {

//					for (int i = dto.getFurtherEducation().length; i < furtherEducation.size(); i++ ) {
//						furtherEducation.remove(i);
//					}
					while(furtherEducation.size() > dto.getFurtherEducation().length){
						furtherEducation.remove(furtherEducation.size() - 1);
					}
				}

				for (int i = 0; i < dto.getFurtherEducation().length; i++) {
					if (i < furtherEducation.size()) {
						furtherEducation.set(i, educationAssembler.updateDomainObj(dto.getFurtherEducation()[i], furtherEducation.get(i)));
					} else
						furtherEducation.add(i, educationAssembler.createDomainObj(dto.getFurtherEducation()[i]));
				}
				applicant.setFurtherEducation(furtherEducation);
			} else
				applicant.getFurtherEducation().clear();

			if (dto.getHighestDegree() != null) {
				applicant.setHighestDegree(educationAssembler.updateDomainObj(dto.getHighestDegree(), applicant.getHighestDegree()));
			}
			
			if (dto.getCurrentStatus() != null) {
				applicant.setCurrentStatus(CurrentStatusEnum.fromValue(dto.getCurrentStatus()));
			}
			
			applicant.setLookingFor(dto.getLookingFor());

			applicant.setOffering(dto.getOffering());

			if (dto.getWorkTypes() != null && dto.getWorkTypes().length > 0) {
				List<WorkUserType> workTypes = applicant.getWorkUserTypes();
				if(workTypes.size() > dto.getWorkTypes().length){
					logger.info("DB: " + workTypes.size() + "; DTO: " + dto.getWorkTypes().length);
//					for(int i = dto.getWorkTypes().length; i < workTypes.size(); ){
//						workTypes.remove(i);
//					}
					while(workTypes.size() > dto.getWorkTypes().length){
						workTypes.remove(workTypes.size() - 1);
					}
				}
				logger.info("nach dem entfernen. DB: " + workTypes.size() + "; DTO: " + dto.getWorkTypes().length);
				for (int i = 0; i < dto.getWorkTypes().length; i++) {
					if(i < workTypes.size()){
						workTypes.set(i, workTypeAssembler.updateDomainObj(dto.getWorkTypes()[i], workTypes.get(i)));
					}
					else {
//						WorkUserType workUserType = workTypeAssembler.createDomainObj(dto.getWorkTypes()[i]);
//						workUserType.setApplicant(applicant);
						workTypes.add(i, workTypeAssembler.createDomainObj(dto.getWorkTypes()[i]));
					}
				}
				applicant.setWorkUserTypes(workTypes);
			}
			else {
				applicant.getWorkUserTypes().clear();
			}

			if (dto.getAvailability() != null) {
				applicant.setAvailability(AvailabilityEnum.valueOf(dto.getAvailability()));
			}

			if (dto.getLanguageSkillsEnglish() != null) {
				applicant.setLanguageSkillsEnglish(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsEnglish()));
			}
			if (dto.getLanguageSkillsGerman() != null) {
				applicant.setLanguageSkillsGerman(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsGerman()));
			}

			if (dto.getLanguageSkillsOther() != null && dto.getLanguageSkillsOther().length > 0) {

				List<LanguageSkill> languageSkillsOther = applicant.getLanguageSkillsOther();
				if (languageSkillsOther.size() > dto.getLanguageSkillsOther().length) {

//					for (int i = dto.getLanguageSkillsOther().length; i < languageSkillsOther.size(); i++ ) {
//						languageSkillsOther.remove(i);
//					}
					while (languageSkillsOther.size() > dto.getLanguageSkillsOther().length){
						languageSkillsOther.remove(languageSkillsOther.size()-1);
					}
				}

				for (int i = 0; i < dto.getLanguageSkillsOther().length; i++) {
					try {
						if (i < languageSkillsOther.size()) {
							languageSkillsOther.set(i, languageSkillAssembler.updateDomainObj(dto.getLanguageSkillsOther()[i], languageSkillsOther.get(i)));
						} else
							languageSkillsOther.add(i, languageSkillAssembler.createDomainObj(dto.getLanguageSkillsOther()[i]));
					} catch (LanguageNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				applicant.setLanguageSkillsOther(languageSkillsOther);
			} else
				applicant.getLanguageSkillsOther().clear();

			applicant.setLocationRemarks(dto.getLocationRemarks());
			if (dto.getManagementExperience() != null) {
				applicant.setManagementExperience(DecisionYesNoEnum.fromValue(dto.getManagementExperience()));
			}
			if (dto.getManagementExperienceDuration() != null) {
				applicant.setManagementExperienceDuration(ExperienceDurationEnum.fromValue(dto.getManagementExperienceDuration()));
			}
			applicant.setManagementExperienceRemarks(dto.getManagementExperienceRemarks());

			if (dto.getManagementExperienceSector() != null) {
				applicant.setManagementExperienceSector(fieldOfOccupationWorker.getIndustrySectorById(dto.getManagementExperienceSector()));
			}
			if (dto.getManagementExperienceTeamSize() != null) {
				applicant.setManagementExperienceTeamSize(TeamSizeEnum.fromValue(dto.getManagementExperienceTeamSize()));
			}
			if (dto.getPreferredFieldOfActivity() != null) {
				// applicant.setPreferredFieldOfActivity(fw.getIndustrySectorById(dto.getPreferredFieldOfActivity()));
				applicant.setPreferredFieldOfActivity(industrySectorAssembler.getDomainObj(dto.getPreferredFieldOfActivity()));
			}
			if (dto.getPreferredLocation() != null) {
				applicant.setPreferredLocation(countryAssembler.getDomainObj(dto.getPreferredLocation()));
			}

			if (dto.getWorkExperience() != null && dto.getWorkExperience().length > 0) {
				List<WorkExperience> workExperience = applicant.getWorkExperience();
				if (workExperience.size() > dto.getWorkExperience().length) {

					while (workExperience.size() > dto.getWorkExperience().length ) {
						workExperience.remove(workExperience.size()-1);
					}
				}

				for (int i = 0; i < dto.getWorkExperience().length; i++) {
					if (i < workExperience.size()) {
						workExperience.set(i, workExperienceAssembler.updateDomainObj(dto.getWorkExperience()[i], workExperience.get(i)));
					} else
						workExperience.add(i, workExperienceAssembler.createDomainObj(dto.getWorkExperience()[i]));
				}
				applicant.setWorkExperience(workExperience);
			} else
				applicant.getWorkExperience().clear();

		}
		applicant.setAvailableFromDate(DateUtil.calendar2Date(dto.getAvailableFromDate()));

		logger.info("Update des Publikationstyps auf " + dto.getPublicationType());
		if(dto.getPublicationType() != null){
			applicant.setPublicationType(PublicationTypeEnum.valueOf(dto.getPublicationType()));
		}
		
		if(dto.getProfilePhoto() != null) {
			applicant.setProfilePhoto(dto.getProfilePhoto());
		}

		return applicant;
	}

}
