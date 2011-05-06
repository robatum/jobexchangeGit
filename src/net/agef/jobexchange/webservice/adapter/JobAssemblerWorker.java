/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import java.util.Iterator;
import java.util.List;

import net.agef.jobexchange.application.JobWorker;
import net.agef.jobexchange.domain.ContractDurationEnum;
import net.agef.jobexchange.domain.Currency;
import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.DegreeEnum;
import net.agef.jobexchange.domain.ExperienceDurationEnum;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LanguageSkill;
import net.agef.jobexchange.domain.LanguageSkillsEnum;
import net.agef.jobexchange.domain.PublicationTypeEnum;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.LanguageNotFoundException;
import net.agef.jobexchange.exceptions.OccupationalFieldNotFoundException;
import net.agef.jobexchange.integration.JobDAO;
import net.agef.jobexchange.integration.UserDAO;
import net.agef.jobexchange.webservice.adapter.util.DateUtil;
import net.agef.jobexchange.webservice.entities.JobDTO;
import net.agef.jobexchange.webservice.entities.LanguageSkillDTO;


/**
 * @author Administrator
 *
 */
public class JobAssemblerWorker implements JobAssembler {
	
	private CountryAssembler ca;
	private CurrencyAssembler cua;
	private AddressAssembler ada;
	private LanguageSkillAssembler la;
	private ContactPersonAssembler cpa;
	private OccupationalFieldAssembler oa;
	private JobWorker jw;
	private UserDAO userDAO;
	private JobDAO jobDAO;
	
	public JobAssemblerWorker(JobDAO jobDAO, UserDAO userDAO, JobWorker jobWorker, CurrencyAssembler currencyAssembler, CountryAssembler countryAssembler, AddressAssembler addressAssembler, LanguageSkillAssembler languageAssembler, ContactPersonAssembler contactPersonAssembler, OccupationalFieldAssembler occupationalFieldAssembler) {
		this.ca = countryAssembler;
		this.ada = addressAssembler;
		this.la = languageAssembler;
		this.cpa = contactPersonAssembler;
		this.oa = occupationalFieldAssembler;
		this.jw = jobWorker;
		this.userDAO = userDAO;
		this.cua = currencyAssembler;
		this.jobDAO = jobDAO;
	}

	@Override
	public JobDTO createDTO(JobImpl job) {
		JobDTO dto = new JobDTO();
		
		if(job!=null){
			
			try {
				dto.setAlternativeProfession(job.getAlternativeProfession());
			} catch (Exception e) {
				
				return dto;
			}
			
			dto.setCobraJobId(job.getCobraJobId());
			dto.setCommentsRegardingApplicationProcedure(job.getCommentsRegardingApplicationProcedure());
			if(job.getComputerSkills()!=null){
				dto.setComputerSkills(job.getComputerSkills().toString());
			}
			dto.setComputerSkillsComments(job.getComputerSkillsComments());
			
			if(job.getContactPerson()!=null){
				
				dto.setContactPerson(cpa.createDTO(job.getContactPerson()));
				dto.setContactPersonAddress(ada.createDTO(job.getContactPerson().getContactPersonAddress()));			
			}else {
					dto.setContactPerson(cpa.createDTOByUser(job.getJobOfferOwner()));
					dto.setContactPersonAddress(ada.createDTO(job.getCurrentContactAddress()));
			}
			
			if(job.getCountryOfEmployment()!=null){
				dto.setCountryOfEmployment(ca.createDTO(job.getCountryOfEmployment()));
			}
			if(job.getCurrency()!= null){
				dto.setCurrency(cua.createDTO(job.getCurrency()));
			}
			dto.setDesiredProfession(job.getDesiredProfession());
			if(job.getDrivingLicence()!=null){
				dto.setDrivingLicence(job.getDrivingLicence().value());
			}
			if(job.getDurationOfContract()!=null){
				dto.setDurationOfContract(job.getDurationOfContract().toString());
			}
			dto.setFurtherComments(job.getFurtherComments());
			dto.setFurtherCommentsRegardingEducation(job.getFurtherCommentsRegardingEducation());
			dto.setApplicationExpireDate( DateUtil.date2Calendar(job.getApplicationExpireDate()) );
			dto.setFurtherRequirements(job.getFurtherRequirements());
			if(dto.getJobDescription() != null && !dto.getJobDescription().contains("(m/f)")){
				dto.setJobDescription(job.getJobDescription()+" (m/f)");
			}else dto.setJobDescription(job.getJobDescription());
			dto.setJobOfferExpireDate(DateUtil.date2Calendar(job.getJobOfferExpireDate()));
			dto.setJobOfferId(job.getJobOfferId());
			
			dto.setJobOfferOwner(job.getJobOfferOwner().getApdUserId());
			if(job.getLanguageSkillsEnglish()!=null){
				dto.setLanguageSkillsEnglish(job.getLanguageSkillsEnglish().toString());
			}
			if(job.getLanguageSkillsGerman()!=null){
				dto.setLanguageSkillsGerman(job.getLanguageSkillsGerman().toString());
			}
			
			if(job.getLanguageSkillsOther()!=null){		
				
				LanguageSkillDTO[] languageSkillOther = new LanguageSkillDTO[job.getLanguageSkillsOther().size()];
				int counter = 0;
				for (Iterator<LanguageSkill> it = job.getLanguageSkillsOther().iterator(); it.hasNext();){
					languageSkillOther[counter] = la.createDTO(it.next());
					counter++;
                }
				dto.setLanguageSkillsOther(languageSkillOther);
			}
			
			dto.setLocationOfEmployment(job.getLocationOfEmployment());
			if(job.getMinimumRequirementsForEducation()!=null){
				dto.setMinimumRequirementsForEducation(job.getMinimumRequirementsForEducation().toString());
			}
			dto.setMiscellaneousServices(job.getMiscellaneousServices());
			dto.setNumberOfJobs(job.getNumberOfJobs());
			dto.setOrganisationDescription(job.getOrganisationDescription());
			dto.setOrganisationName(job.getOrganisationName());
			if(job.getOrganisationIndustrySector()!=null){
				dto.setOrganisationIndustrySector(job.getOrganisationIndustrySector().getId().toString());
			}
			dto.setPossibleCommencementDate(DateUtil.date2Calendar(job.getPossibleCommencementDate()));
			if(job.getPreferredPublication()!=null){
				dto.setPreferredPublication(job.getPreferredPublication().toString());
			}
			dto.setSalary(job.getSalary());
			
//			if(job.getOccupationalField()!=null){
//				if(job.getOccupationalField().isParentField()) {
//					dto.setOccupationalSubField(job.getOccupationalField().getFieldId().toString());
//					dto.setOccupationalField(job.getOccupationalField().getParentFieldId().toString());
//				} else {
//					dto.setOccupationalField(job.getOccupationalField().getFieldId().toString());
//				}
//				
//			}
			
			
			if(job.getOccupationalField()!=null){
				if(!job.getOccupationalField().isParentField()) {
					dto.setOccupationalSubField(oa.createDTO(job.getOccupationalField()));
					dto.setOccupationalField(oa.createMainFromSubFieldDTO(job.getOccupationalField()));
				} else {
					dto.setOccupationalField(oa.createDTO(job.getOccupationalField()));
				}
				
			}	
			
			dto.setSpecialKnowledge(job.getSpecialKnowledge());
			dto.setTaskDescription(job.getTaskDescription());
			dto.setWeeklyHoursOfWork(job.getWeeklyHoursOfWork());
			if(job.getWorkExperience()!=null){
				dto.setWorkExperience(job.getWorkExperience().toString());
			}
			dto.setAttachmentLocation(job.getAttachmentLocation());
			dto.setApplicationFormLink(job.getApplicationFormLink());
		}
		return dto;
	}
	

	@Override
	public JobImpl createDomainObjByApdId(JobDTO dto) throws APDUserNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		JobImpl job = new JobImpl();

		if (dto != null) {
			if (dto.getJobOfferOwner() != null) {
				job.setJobOfferOwner(userDAO.findAPDUserByID(dto.getJobOfferOwner()));
			}
			if (dto.getCurrency() != null) {
				Currency currency = jw.getCurrencyByNameOrIsoNumber(dto.getCurrency());
				job.setCurrency(currency);
			}
			createDomainObj(dto, job);
		}

		return job;
	}
	
	@Override
	public JobImpl createDomainObjByCobraId(JobDTO dto) throws CobraUserNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		JobImpl job = new JobImpl();

		if (dto != null) {
			if (dto.getJobOfferOwner() != null) {
				job.setJobOfferOwner(userDAO.findCobraUserByID(dto.getJobOfferOwner(), true));
			}
			if (dto.getCurrency() != null) {
				job.setCurrency(cua.getDomainObj(dto.getCurrency()));
			}
			createDomainObj(dto, job);
		}

		return job;
	}

	private void createDomainObj(JobDTO dto, JobImpl job) throws EnumValueNotFoundException, CountryNotFoundException {
		job.setAlternativeProfession(dto.getAlternativeProfession());
		job.setCobraJobId(dto.getCobraJobId());
		job.setCommentsRegardingApplicationProcedure(dto.getCommentsRegardingApplicationProcedure());
		job.setApplicationExpireDate(DateUtil.calendar2Date(dto.getApplicationExpireDate()));
		if (dto.getComputerSkills() != null) {
			job.setComputerSkills(DecisionYesNoEnum.fromValue(dto.getComputerSkills()));
		}
		job.setComputerSkillsComments(dto.getComputerSkillsComments());
		if (dto.getContactPerson() != null) {
			job.setContactPerson(cpa.createDomainObj(dto.getContactPerson(), dto.getContactPersonAddress()));
		}

		if (dto.getCountryOfEmployment() != null) {
			job.setCountryOfEmployment(ca.getDomainObj(dto.getCountryOfEmployment()));
		}

		job.setDesiredProfession(dto.getDesiredProfession());
		if (dto.getDrivingLicence() != null) {
			job.setDrivingLicence(DecisionYesNoEnum.fromValue(dto.getDrivingLicence()));
		}
		if (dto.getDurationOfContract() != null) {
			job.setDurationOfContract(ContractDurationEnum.fromValue(dto.getDurationOfContract()));
		}
		job.setFurtherComments(dto.getFurtherComments());
		job.setFurtherCommentsRegardingEducation(dto.getFurtherCommentsRegardingEducation());
		job.setFurtherRequirements(dto.getFurtherRequirements());
		job.setJobDescription(dto.getJobDescription());
		job.setJobOfferExpireDate(DateUtil.calendar2Date(dto.getJobOfferExpireDate()));

		if (dto.getLanguageSkillsEnglish() != null) {
			job.setLanguageSkillsEnglish(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsEnglish()));
		}
		if (dto.getLanguageSkillsGerman() != null) {
			job.setLanguageSkillsGerman(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsGerman()));
		}

		if (dto.getLanguageSkillsOther() != null && dto.getLanguageSkillsOther().length > 0) {
			List<LanguageSkill> languageSkillsOther = job.getLanguageSkillsOther();
			for (int i = 0; i < dto.getLanguageSkillsOther().length; i++) {
				try {
					LanguageSkill langSkill = la.createDomainObj(dto.getLanguageSkillsOther()[i]);
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
			job.setLanguageSkillsOther(languageSkillsOther);
		}

		job.setLocationOfEmployment(dto.getLocationOfEmployment());
		if (dto.getMinimumRequirementsForEducation() != null) {
			job.setMinimumRequirementsForEducation(DegreeEnum.fromValue(dto.getMinimumRequirementsForEducation()));
		}
		job.setMiscellaneousServices(dto.getMiscellaneousServices());
		job.setNumberOfJobs(dto.getNumberOfJobs());
		job.setPossibleCommencementDate(DateUtil.calendar2Date(dto.getPossibleCommencementDate()));
		if (dto.getPreferredPublication() != null) {
			job.setPreferredPublication(PublicationTypeEnum.fromValue(dto.getPreferredPublication()));
		}
		job.setSalary(dto.getSalary());

		// Es wird jeweils nur die Unterkategorie gespeichert, da sich die
		// Oberkategorie hieraus ableiten laesst
		if (dto.getOccupationalField() != null) {
			if (dto.getOccupationalSubField() != null) {
				job.setOccupationalField(oa.getDomainObj(dto.getOccupationalSubField()));
			} else
				job.setOccupationalField(oa.getDomainObj(dto.getOccupationalField()));
		}
		job.setSpecialKnowledge(dto.getSpecialKnowledge());
		job.setTaskDescription(dto.getTaskDescription());
		job.setWeeklyHoursOfWork(dto.getWeeklyHoursOfWork());
		if (dto.getWorkExperience() != null) {
			job.setWorkExperience(ExperienceDurationEnum.fromValue(dto.getWorkExperience()));
		}
		job.setAttachmentLocation(dto.getAttachmentLocation());
		job.setApplicationFormLink(dto.getApplicationFormLink());
	}
	
	@Override
	public JobImpl updateDomainObjByApdId(JobDTO dto) throws JobOfferNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		JobImpl job;
		try {
			job = jobDAO.doRetrieve(dto.getJobOfferId(), true);

		} catch (Exception e) {
			throw new JobOfferNotFoundException();
		}

		updateDomainObj(dto, job);
		return job;
	}
	
	@Override
	public JobImpl updateDomainObjByCobraId(JobDTO dto) throws JobOfferNotFoundException, OccupationalFieldNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		JobImpl job;
		try {
			job = jobDAO.findJobOfferByCobraId(dto.getCobraJobId());

		} catch (Exception e) {
			throw new JobOfferNotFoundException();
		}

		updateDomainObj(dto, job);
		return job;
	}

	private void updateDomainObj(JobDTO dto, JobImpl job) throws JobOfferNotFoundException, EnumValueNotFoundException, CountryNotFoundException {
		if (job != null) {
		} else
			throw new JobOfferNotFoundException();

		if (dto != null) {
			job.setAlternativeProfession(dto.getAlternativeProfession());
			job.setCobraJobId(dto.getCobraJobId());
			job.setCommentsRegardingApplicationProcedure(dto.getCommentsRegardingApplicationProcedure());
			job.setApplicationExpireDate(DateUtil.calendar2Date(dto.getApplicationExpireDate()));
			if (dto.getComputerSkills() != null) {
				job.setComputerSkills(DecisionYesNoEnum.fromValue(dto.getComputerSkills()));
			}
			job.setComputerSkillsComments(dto.getComputerSkillsComments());
			if (dto.getContactPerson() != null) {
				job.setContactPerson(cpa.updateDomainObj(job.getContactPerson(), dto.getContactPerson(), dto.getContactPersonAddress()));
			}

			if (dto.getCountryOfEmployment() != null) {
				job.setCountryOfEmployment(ca.getDomainObj(dto.getCountryOfEmployment()));
			}

			if (dto.getCurrency() != null) {
				job.setCurrency(cua.getDomainObj(dto.getCurrency()));
			}
			job.setDesiredProfession(dto.getDesiredProfession());
			if (dto.getDrivingLicence() != null) {
				job.setDrivingLicence(DecisionYesNoEnum.fromValue(dto.getDrivingLicence()));
			}
			if (dto.getDurationOfContract() != null) {
				job.setDurationOfContract(ContractDurationEnum.fromValue(dto.getDurationOfContract()));
			}
			job.setFurtherComments(dto.getFurtherComments());
			job.setFurtherCommentsRegardingEducation(dto.getFurtherCommentsRegardingEducation());
			job.setFurtherRequirements(dto.getFurtherRequirements());
			job.setJobDescription(dto.getJobDescription());
			job.setJobOfferExpireDate(DateUtil.calendar2Date(dto.getJobOfferExpireDate()));

			if (dto.getLanguageSkillsEnglish() != null) {
				job.setLanguageSkillsEnglish(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsEnglish()));
			}
			if (dto.getLanguageSkillsGerman() != null) {
				job.setLanguageSkillsGerman(LanguageSkillsEnum.fromValue(dto.getLanguageSkillsGerman()));
			}

			if (dto.getLanguageSkillsOther() != null && dto.getLanguageSkillsOther().length > 0) {

				List<LanguageSkill> languageSkillsOther = job.getLanguageSkillsOther();
				if (languageSkillsOther.size() > dto.getLanguageSkillsOther().length) {

//					for (int i = dto.getLanguageSkillsOther().length; i < languageSkillsOther.size(); i++) {
//						languageSkillsOther.remove(i);
//					}
					while(languageSkillsOther.size() > dto.getLanguageSkillsOther().length){
						languageSkillsOther.remove(languageSkillsOther.size() - 1);
					}
				}

				for (int i = 0; i < dto.getLanguageSkillsOther().length; i++) {
					try {
						if (i < languageSkillsOther.size()) {
							languageSkillsOther.set(i, la.updateDomainObj(dto.getLanguageSkillsOther()[i], languageSkillsOther.get(i)));
						} else
							languageSkillsOther.add(i, la.createDomainObj(dto.getLanguageSkillsOther()[i]));
					} catch (LanguageNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				job.setLanguageSkillsOther(languageSkillsOther);
			} else
				job.getLanguageSkillsOther().clear();

			job.setLocationOfEmployment(dto.getLocationOfEmployment());
			if (dto.getMinimumRequirementsForEducation() != null) {
				job.setMinimumRequirementsForEducation(DegreeEnum.fromValue(dto.getMinimumRequirementsForEducation()));
			}
			job.setMiscellaneousServices(dto.getMiscellaneousServices());
			job.setNumberOfJobs(dto.getNumberOfJobs());
			job.setPossibleCommencementDate(DateUtil.calendar2Date(dto.getPossibleCommencementDate()));
			if (dto.getPreferredPublication() != null) {
				job.setPreferredPublication(PublicationTypeEnum.fromValue(dto.getPreferredPublication()));
			}
			job.setSalary(dto.getSalary());
			// Es wird jeweils nur die Unterkategorie gespeichert, da sich die
			// Oberkategorie hieraus ableiten laesst
			if (dto.getOccupationalField() != null) {
				if (dto.getOccupationalSubField() != null) {
					job.setOccupationalField(oa.getDomainObj(dto.getOccupationalSubField()));
				} else
					job.setOccupationalField(oa.getDomainObj(dto.getOccupationalField()));
			}
			job.setSpecialKnowledge(dto.getSpecialKnowledge());
			job.setTaskDescription(dto.getTaskDescription());
			job.setWeeklyHoursOfWork(dto.getWeeklyHoursOfWork());
			if (dto.getWorkExperience() != null) {
				job.setWorkExperience(ExperienceDurationEnum.fromValue(dto.getWorkExperience()));
			}
			job.setAttachmentLocation(dto.getAttachmentLocation());
			job.setApplicationFormLink(dto.getApplicationFormLink());
		}
	}
}
