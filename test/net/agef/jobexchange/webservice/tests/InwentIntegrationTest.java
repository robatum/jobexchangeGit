package net.agef.jobexchange.webservice.tests;

import java.rmi.RemoteException;
import java.util.Calendar;

import net.agef.jobexchange.domain.AvailabilityEnum;
import net.agef.jobexchange.domain.CurrentStatusEnum;
import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.DegreeEnum;
import net.agef.jobexchange.domain.LanguageSkillsEnum;
import net.agef.jobexchange.domain.PublicationTypeEnum;
import net.agef.jobexchange.domain.WorkTypeEnum;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.AddInwentApplicantProfile;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.AddInwentApplicantProfileResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.ApplicantDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.DeleteInwentApplicantProfile;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.DeleteInwentApplicantProfileResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.EducationDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetAllApplicantsResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfile;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.GetApplicantProfileResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.LanguageSkillDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.ModifyApplicantProfile;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.ModifyApplicantProfileResponse;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.WorkExperienceDTO;
import net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.WorkUserTypeDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.AddInwentAlumniUser;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.AddInwentAlumniUserResponse;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.AlumniRoleDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfInwentUserExist;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfInwentUserExistResponse;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CountryDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.UserDTO;

import org.apache.axis2.AxisFault;

public class InwentIntegrationTest extends junit.framework.TestCase {
	
	private UserWSStub userStub;
	
	private ApplicantWSStub applicantStub;
	private AddInwentApplicantProfile addApplicantProfile;
	
	private Long applicantProfileId;

	// 1. test CREATING user
	// 2. test ADDING applicant Profile
	// 3. test MODIFYING applicant Profile
	// 4. test DELETING applicant Profile
	// 5. test DELETING user
	
	public void setUp(){
		try {
			super.setUp();
			userStub = new UserWSStub();
			applicantStub = new ApplicantWSStub();
			addApplicantProfile = new AddInwentApplicantProfile();
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testInwentUserCreation() {
		AddInwentAlumniUser alumniUser = new AddInwentAlumniUser();

		UserDTO user = new UserDTO();
		CountryDTO nationality = new CountryDTO();
		nationality.setCountry("Germany");
		user.setNationality(nationality);
		user.setFamilyName("Jon Doe");
		user.setInwentUserId(23);
		user.setElggUserId(13);
		alumniUser.setUser(user);
		AlumniRoleDTO alumniRole = new AlumniRoleDTO();
		alumniRole.setOrganisationName("Test GmbH");
		alumniUser.setUserRole(alumniRole);
		alumniUser.setInwentUserId(17);
		
		try {
			AddInwentAlumniUserResponse response = userStub.addInwentAlumniUser(alumniUser);
			System.out.println(response.get_return());
			
			CheckIfInwentUserExist checkIfInwentUserExist = new CheckIfInwentUserExist();
			checkIfInwentUserExist.setInwentUserId(17);
			
			/* check if new user exists */
			CheckIfInwentUserExistResponse checkUserExistsResponse = userStub.checkIfInwentUserExist(checkIfInwentUserExist);
//			userStub.checkIfInwentUserExist(checkIfInwentUserExist);
			assertTrue(checkUserExistsResponse.get_return());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testAddInwentApplicantProfile(){
		// in den Kommentaren der Typ des Feldes im Domainobjekt
		ApplicantDTO applicantDTO = new ApplicantDTO();
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
		
		addApplicantProfile.setApplicantProfile(applicantDTO);
		addApplicantProfile.setInwentUserId(17);
		
		try {
			AddInwentApplicantProfileResponse addApplicantProfileResponse = applicantStub.addInwentApplicantProfile(addApplicantProfile);
			applicantProfileId = addApplicantProfileResponse.get_return();
			System.out.println(applicantProfileId);
			assertTrue(applicantProfileId > 0); // 0 is response code for error
			
			GetAllApplicantsResponse allApplicantsResponse = applicantStub.getAllApplicants();
			assertTrue(allApplicantsResponse.get_return().length > 0);
			
		} catch (RemoteException e) {
			System.out.print(e.getMessage());
			assertFalse(true);
		}
	}
	
	public void testModifyInwentApplicantProfile(){
		// in den Kommentaren der Typ des Feldes im Domainobjekt
		ApplicantDTO applicantDTO = new ApplicantDTO();
		applicantDTO.setCurrentStatus(CurrentStatusEnum.FREELANCER.value());
		applicantDTO.setLookingFor("Ich suche Erfuellung. Geld ist Nebensache.");
		applicantDTO.setOffering("Ich biete 30 Jahre Berufserfahrung bei 15 Diplomen und Doktortiteln.");
		applicantDTO.setWorkTypes(createWorkUserTypes());
		applicantDTO.setAvailability(AvailabilityEnum.NEGOTIABLE.value());
		
		/* Berufserfahrung */
		applicantDTO.setWorkExperience(createWorkExperience());
		
		/* Ausbildung */
		applicantDTO.setHighestDegree(createHighestEducation());
		applicantDTO.setFurtherEducation(createFurtherEducation());
		
		/* weitere Kenntnisse */
		applicantDTO.setLanguageSkillsGerman(LanguageSkillsEnum.NO_SKILLS.value());
		applicantDTO.setLanguageSkillsEnglish(LanguageSkillsEnum.BUSINESS_FLUENT.value());
		applicantDTO.setLanguageSkillsOther(createLanguageSkillsOther());
		applicantDTO.setComputerSkills(DecisionYesNoEnum.YES.value());
		applicantDTO.setComputerSkillsComments("Die haben viele Schalter innendrin");
		applicantDTO.setReferencesAndCertificates(DecisionYesNoEnum.YES.value());
		applicantDTO.setReferencesAndCertificatesComments("Abitur an der Abendschule");
		applicantDTO.setPublications(DecisionYesNoEnum.YES.value());
		applicantDTO.setPublicationsComments("Zaehlen Fahndungsfotos?");
		applicantDTO.setFurtherOnlineActivities(DecisionYesNoEnum.YES.value());
		applicantDTO.setFurtherOnlineActivitiesComments("");
		applicantDTO.setProfilePhoto("user982348.jpg");
		applicantDTO.setPublicationType(PublicationTypeEnum.FULL_WITH_COMPLETE_ADDRESS.value());
		
		ModifyApplicantProfile modifyApplicantProfile = new ModifyApplicantProfile();
		modifyApplicantProfile.setApplicantProfile(applicantDTO);
		modifyApplicantProfile.setApplicantProfileId(30);
		
		try {
			ModifyApplicantProfileResponse modifyApplicantProfileResponse = applicantStub.modifyApplicantProfile(modifyApplicantProfile);
			assertTrue(modifyApplicantProfileResponse.get_return());
			
			GetApplicantProfile getApplicantProfile = new GetApplicantProfile();
			getApplicantProfile.setApplicantProfileId(30); 
			GetApplicantProfileResponse profileResponse = applicantStub.getApplicantProfile(getApplicantProfile);
			assertTrue(profileResponse.get_return().getWorkTypes().length > 0);
			for(WorkUserTypeDTO workUserTypeDTO : profileResponse.get_return().getWorkTypes()){
				System.out.println(workUserTypeDTO.getWorkType());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testDeleteInwentApplicantProfile(){
		/*
		 * delete Profile request
		 */
		DeleteInwentApplicantProfile deleteApplicantProfile = new DeleteInwentApplicantProfile();
		deleteApplicantProfile.setInwentUserId(17);
		try {
			DeleteInwentApplicantProfileResponse deleteResponse = applicantStub.deleteInwentApplicantProfile(deleteApplicantProfile);
			assertTrue(deleteResponse.get_return());
		}
		catch(Exception e) {};
	}

	/*
	 * Helper methods
	 */
	
	private WorkUserTypeDTO[] createWorkUserTypes() {
		WorkUserTypeDTO[] workTypes = new WorkUserTypeDTO[2];
		WorkUserTypeDTO workType = new WorkUserTypeDTO();
		workType.setWorkType(WorkTypeEnum.INTERNSHIP_TRAINEE.value());
		WorkUserTypeDTO workType2 = new WorkUserTypeDTO();
		workType2.setWorkType(WorkTypeEnum.TEMPORARY_WORK.value());
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
	
	private net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.CountryDTO createCountry() {
		net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.CountryDTO country = new net.agef.jobexchange.webservice.tests.util.ApplicantWSStub.CountryDTO();
		country.setCountry("Antigua and Barbuda"); // Country.shortEnglishName (DB: cn_short_en)
		country.setIsoNumber(28); // Country.isoNumber (DB: cn_iso_nr)
		return country;
	}
	
}

