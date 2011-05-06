package net.agef.jobexchange.webservice.tests;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import junit.framework.TestCase;
import net.agef.jobexchange.domain.AvailabilityEnum;
import net.agef.jobexchange.domain.CurrentStatusEnum;
import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.DegreeEnum;
import net.agef.jobexchange.domain.LanguageSkillsEnum;
import net.agef.jobexchange.domain.PublicationTypeEnum;
import net.agef.jobexchange.domain.WorkTypeEnum;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.AddApplicantProfile;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.AddApplicantProfileResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.ApplicantDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.CountryDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.DeleteApplicantProfile;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.DeleteApplicantProfileResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.EducationDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetAllApplicantsResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfileByUserId;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfileByUserIdResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantsByExtendedCriteria;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantsByExtendedCriteriaResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.LanguageSkillDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.TerritoryDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.WorkExperienceDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.WorkUserTypeDTO;

import org.apache.axis2.AxisFault;

public class ApplicantWSTest extends TestCase {

	private ApplicantWSStub applicantStub;
	public void setUp() {
		try {
			super.setUp();
			applicantStub = new ApplicantWSStub();
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 1. Profil erstellen
	 * 2. Profile bearbeiten
	 * 3. Profile loeschen
	 */
	
	public void testCreateCompleteApplicantProfile(){
		ApplicantDTO applicantDTO = new ApplicantDTO();
		/*  Allgemein  */
		applicantDTO.setCurrentStatus(CurrentStatusEnum.FREELANCER.value());
		applicantDTO.setLookingFor("I am looking for ...");
		applicantDTO.setOffering("I am offering ...");
		applicantDTO.setWorkTypes(createWorkUserTypes());
		applicantDTO.setAvailability(AvailabilityEnum.TWO_MONTH.value());
		
		/* Berufserfahrung */
		applicantDTO.setWorkExperience(createWorkExperience());
		
		/* Ausbildung */
		applicantDTO.setHighestDegree(createHighestEducation());
		applicantDTO.setFurtherEducation(createFurtherEducation());
		
		/* weitere Kenntnisse */
		applicantDTO.setLanguageSkillsGerman(LanguageSkillsEnum.MOTHER_TONGUE.value());
		applicantDTO.setLanguageSkillsEnglish(LanguageSkillsEnum.BUSINESS_FLUENT.value());
		applicantDTO.setLanguageSkillsOther(createLanguageSkillsOther());
		applicantDTO.setComputerSkills(DecisionYesNoEnum.YES.value());
		applicantDTO.setComputerSkillsComments("Computer hab ick zu Hause.");
		applicantDTO.setReferencesAndCertificates(DecisionYesNoEnum.YES.value());
		applicantDTO.setReferencesAndCertificatesComments("Diplom, ITIL v3 Zertifizierung");
		applicantDTO.setPublications(DecisionYesNoEnum.YES.value());
		applicantDTO.setPublicationsComments("Diplomarbeit zum Thema PKI an der FHTW Berlin");
		applicantDTO.setFurtherOnlineActivities(DecisionYesNoEnum.YES.value());
		applicantDTO.setFurtherOnlineActivitiesComments("");
		applicantDTO.setProfilePhoto("user123123.jpg");
		applicantDTO.setPublicationType(PublicationTypeEnum.FULL_WITH_COMPLETE_ADDRESS.value());
		
		/* */
		AddApplicantProfile addApplicantProfile = new AddApplicantProfile(); 
		addApplicantProfile.setApplicantProfile(applicantDTO);
		addApplicantProfile.setApdUserId(1);
		
		try {
			AddApplicantProfileResponse addApplicantProfileResponse = applicantStub.addApplicantProfile(addApplicantProfile);
			Long id = addApplicantProfileResponse.get_return();
			assertTrue(id > 0); // 0 is response code for error
			
			GetAllApplicantsResponse allApplicantsResponse = applicantStub.getAllApplicants();
			assertTrue(allApplicantsResponse.get_return().length > 0);
			
		} catch (RemoteException e) {
			System.out.print(e.getMessage());
			assertFalse(true);
		}
	}
	
	public void testGetAllApplicantProfiles(){
		/*
		 * get profile request
		 */
		GetApplicantProfileByUserId getApplicantProfileByUserId = new GetApplicantProfileByUserId();
		getApplicantProfileByUserId.setApdUserId(1);
		
		GetApplicantProfileByUserIdResponse searchResponse;
		try {
			searchResponse = applicantStub.getApplicantProfileByUserId(getApplicantProfileByUserId);
			ApplicantDTO applicantDTOResult = searchResponse.get_return();
			assertEquals(applicantDTOResult.getHighestDegree().getInstituteName(), "HTW Berlin");
			assertEquals("YES", applicantDTOResult.getComputerSkills());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertFalse(true);
		}
		
	}
	
	public void testSearchforApplicantByExtendedCriteria(){
		GetApplicantsByExtendedCriteria getSearchResults = new GetApplicantsByExtendedCriteria();
		
//		getSearchResults.addAvailability(AvailabilityEnum.TWO_MONTH.value());
//		getSearchResults.addAvailability(AvailabilityEnum.ONE_MONTH.value());

		String[] param = new String[0];
		getSearchResults.setAvailability(null);
		
		CountryDTO country = new CountryDTO();
		country.setCountry("Germany"); // Country.shortEnglishName (DB: cn_short_en)
		country.setIsoNumber(276); // Country.isoNumber (DB: cn_iso_nr)
		getSearchResults.setCountry(null);
		getSearchResults.setCriteria("");
		getSearchResults.setPageIndexStart(0);
		getSearchResults.setResultsAmount(10);
		getSearchResults.setTerritory(null);
		
		getSearchResults.setManagementExperience("");
		
		getSearchResults.addOccupationalField("");
//		getSearchResults.addOccupationalField("2");
		
		getSearchResults.setWorkUserTypeDTO(null);
		
		GetApplicantsByExtendedCriteriaResponse searchResponse = new GetApplicantsByExtendedCriteriaResponse();
		
		try {
			searchResponse = applicantStub.getApplicantsByExtendedCriteria(getSearchResults);
			assertTrue(searchResponse.get_return().length>0);
			System.out.println(searchResponse.get_return()[0].getWorkTypes().length);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertFalse(true);
		}
	}

	public void testDeleteApplicantProfile(){
		/*
		 * delete Profile request
		 */
		DeleteApplicantProfile deleteApplicantProfile = new DeleteApplicantProfile();
		deleteApplicantProfile.setApdUserId(1);
		
		DeleteApplicantProfileResponse deleteResponse;
		try {
			deleteResponse = applicantStub.deleteApplicantProfile(deleteApplicantProfile);
			assertTrue(deleteResponse.get_return());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private WorkUserTypeDTO[] createWorkUserTypes() {
		WorkUserTypeDTO[] workTypes = new WorkUserTypeDTO[2];
		WorkUserTypeDTO workType = new WorkUserTypeDTO();
		workType.setWorkType(WorkTypeEnum.FREELANCE.value());
		WorkUserTypeDTO workType2 = new WorkUserTypeDTO();
		workType2.setWorkType(WorkTypeEnum.PHD.value());
		workTypes[0] = workType;
		workTypes[1] = workType2;
		return workTypes;
	}
	
	private WorkExperienceDTO[] createWorkExperience() {
		WorkExperienceDTO[] workExperienceArray = new WorkExperienceDTO[2];
		
		WorkExperienceDTO work1 = new WorkExperienceDTO();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1999);
		work1.setStarted( calendar ); // Date
		work1.setFinished(Calendar.getInstance()); // Date
		work1.setUntilToday(DecisionYesNoEnum.NO.value());
		work1.setJobTitle("Job Title"); // String
		work1.setOccupationalField("1"); // OccupationalField (1 - Administration/ Office Organisation)
		work1.setOccupationalSubField("101"); // 101 - bookkeeping and accounting
		work1.setCompany("The other Company");
		work1.setHomepage("www.theothercompany.org");
		work1.setJobDescription("Job Description"); // String
		work1.setManagementExperience(DecisionYesNoEnum.NO.value());
		
		WorkExperienceDTO work2 = new WorkExperienceDTO();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.YEAR, 2007);
		work2.setStarted( calendar2 ); // Date
		work2.setUntilToday(DecisionYesNoEnum.YES.value()); // DecisionYesNoEnum
		work2.setJobTitle("Job Title"); // String
		work2.setOccupationalField("1"); // OccupationalField (1 - Administration/ Office Organisation)
		work2.setOccupationalSubField("101"); // 101 - bookkeeping and accounting
		work2.setCompany("Company Name"); // String
		work2.setHomepage("http://www.yourcompanyname.com");
		work2.setJobDescription("Job Description"); // String
		work2.setManagementExperience(DecisionYesNoEnum.YES.value()); // DecisionYesNoEnum
		
		workExperienceArray[0] = work2;
		workExperienceArray[1] = work1;
		
		return workExperienceArray;
	}

	private EducationDTO createHighestEducation() {
		EducationDTO education = new EducationDTO();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1998);
		education.setStarted(calendar); // Date
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.YEAR, 2004);
		education.setDateOfGraduation(calendar2); // Date
		education.setUntilToday(DecisionYesNoEnum.NO.value()); // DecisionYesNoEnum
		education.setDegree(DegreeEnum.DIPLOMA.value()); // DegreeEnum
		education.setField("meine Fachrichtung");
		education.setFieldSpecialization("meine Spezialisierung");
		education.setCountry(createCountry());
		education.setInstituteName("HTW Berlin");
		return education;
	}

	private EducationDTO[] createFurtherEducation() {
		EducationDTO[] educations = new EducationDTO[1];
		
		EducationDTO education = new EducationDTO();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2009);
		education.setStarted(calendar); // Date
		education.setUntilToday(DecisionYesNoEnum.YES.value()); // DecisionYesNoEnum
		education.setDegree(DegreeEnum.DIPLOMA.value()); // DegreeEnum
		education.setField("meine Fachrichtung");
		education.setFieldSpecialization("meine Spezialisierung");
		education.setCountry(createCountry());
		education.setInstituteName("HTW Berlin");
		
		educations[0] = education;
		
		return educations;
	}
	
	private LanguageSkillDTO[] createLanguageSkillsOther() {
		LanguageSkillDTO[] languageSkills = new LanguageSkillDTO[1];
		
		LanguageSkillDTO languageSkillRussian = new LanguageSkillDTO(); 
		languageSkillRussian.setLevel(LanguageSkillsEnum.BASIC_KNOWLEDGE.value());
		languageSkillRussian.setName("RU"); // Languages - ISO Name Short (DB: lg_iso_2)
		
		languageSkills[0] = languageSkillRussian;
		
		return languageSkills;
	}
	
	private CountryDTO createCountry() {
		CountryDTO country = new CountryDTO();
		country.setCountry("Antigua and Barbuda"); // Country.shortEnglishName (DB: cn_short_en)
		country.setIsoNumber(28); // Country.isoNumber (DB: cn_iso_nr)
		return country;
	}
	
	private TerritoryDTO createTerritory(){
		TerritoryDTO territory = new TerritoryDTO();
		territory.setTerritory("South America");
		return territory;
	}
	
}
