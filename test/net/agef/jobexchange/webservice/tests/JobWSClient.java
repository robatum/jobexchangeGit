package net.agef.jobexchange.webservice.tests;

import java.rmi.RemoteException;
import java.util.Calendar;

import net.agef.jobexchange.webservice.tests.util.JobWSStub.AddJobOffer;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.AddJobOfferResponse;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.AddressDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.ContactPersonDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.CountryDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.DeleteJobOffer;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.GetAllJobOffersResponse;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferByCriteria;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferByCriteriaResponse;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferDetails;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOfferDetailsResponse;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.JobDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.LanguageSkillDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.ModifyJobOffer;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.ModifyJobOfferResponse;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.TerritoryDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub;

import org.apache.axis2.AxisFault;

public class JobWSClient {

	public static void main(String[] args) {
		try {
			JobWSStub stub = new JobWSStub();
			// GetJobOfferResponse gjo = new GetJobOfferResponse();
			// GetJobOfferResponse res = stub.getJobOffer();
			// System.out.println("JobOffer - Organisation : "+res.get_return().getOrganisationName());

			AddJobOffer sJO = new AddJobOffer();
			JobDTO jobOffer = new JobDTO();
			ContactPersonDTO cp = new ContactPersonDTO();
			LanguageSkillDTO ls = new LanguageSkillDTO();

			ls.setName("Brasilianisch");
			ls.setLevel("FLUENT");
			AddressDTO addr = new AddressDTO();
			addr.setAddress1("Berliner Straße 2");
			addr.setAddress2("12334 Köln");
			CountryDTO country = new CountryDTO();
			country.setCountry("54");
			addr.setCountry(country);
			jobOffer.setContactPersonAddress(addr);
			cp.setFamilyName("Mustermann");
			cp.setPosition("Geschäftsführer");
			cp.setAddresses("MR");
			jobOffer.setContactPerson(cp);
			jobOffer.setLanguageSkillsEnglish("FLUENT");
			jobOffer.setComputerSkills("YES");
			CountryDTO empCountry = new CountryDTO();
			empCountry.setCountry("40");
			jobOffer.setCountryOfEmployment(empCountry);
			jobOffer.setPossibleCommencementDate(Calendar.getInstance());
			jobOffer.setAlternativeProfession("BiologIn");
			jobOffer.setJobDescription("LebensmittelchemikerIn (w/m)");
			try {
				// DecisionYesNoEnumTransfer ff =
				// DecisionYesNoEnumTransfer..YES;
				// jobOffer.setDrivingLicence(ff);
				// jobOffer.setComputerSkills(DecisionYesNo.YES);

				// jobOffer.setComputerSkills(new DecisionYesNo());
				sJO.setJobOffer(jobOffer);
				sJO.setApdUserId(1);
				AddJobOfferResponse sJOR = stub.addJobOffer(sJO);

				DeleteJobOffer delJO = new DeleteJobOffer();
				delJO.setJobOfferId(30);
				System.out.println("Delete Job OfferId: " + delJO.getJobOfferId());
				// DeleteJobOfferResponse delJOR = stub.deleteJobOffer(delJO);
				// System.out.println("Delete Response: "+delJOR.get_return());
				// sJOR = stub.addJobOffer(sJO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Habe " + jobOffer.getAlternativeProfession() + " geschrieben");
			// System.out.println("JobOffer - Added : "+sJOR.get_return());

			GetJobOfferByCriteria gJBC = new GetJobOfferByCriteria();
			gJBC.setCriteria("");
			gJBC.setCountry(null);
			TerritoryDTO ter = new TerritoryDTO();
			ter.setTerritory("142");
			gJBC.setTerritory(ter);

			GetJobOfferByCriteriaResponse gJBCR = stub.getJobOfferByCriteria(gJBC);

			JobDTO[] criteriaJobOffers = gJBCR.get_return();
			System.out.println("AllOrgs criteria: " + criteriaJobOffers);
			if (criteriaJobOffers != null) {
				for (int i = 0; i < criteriaJobOffers.length; i++) {
					System.out.println("CriteriaOrgs: " + criteriaJobOffers[i].getJobOfferId() + "--" + criteriaJobOffers[i].getJobDescription() + "--"
							+ criteriaJobOffers[i].getCountryOfEmployment().getCountry());// +"---"+allJobOffers[i].getComputerSkills().toString());
				}
			}

			//						

			GetAllJobOffersResponse gajoRes = stub.getAllJobOffers();
			JobDTO[] allJobOffers = (JobDTO[]) gajoRes.get_return();
			System.out.println("AllOrgs: " + allJobOffers.length);
			for (int i = 0; i < allJobOffers.length; i++) {
				// System.out.println("AllOrgs: "+allJobOffers[i].getJobOfferId()+"--"+allJobOffers[i].getJobDescription()+"--"+allJobOffers[i].getCountryOfEmployment().getCountry()+"---"+allJobOffers[i].getOrganisationIndustrySector());
			}

			GetJobOfferDetails jod = new GetJobOfferDetails();
			jod.setJobOfferId(24);
			GetJobOfferDetailsResponse gjdRes = stub.getJobOfferDetails(jod);
			JobDTO job = gjdRes.get_return();

			// System.out.println("Job Details: "+job.getJobOfferId()+"---"+job.getJobDescription());
			//                        
			ModifyJobOffer modJO = new ModifyJobOffer();
			modJO.setJobOfferId(job.getJobOfferOwner());

			job.setDesiredProfession("LebensmittelchemikerIn");
			job.setWeeklyHoursOfWork(40);
			job.setDrivingLicence("YES");
			job.setDurationOfContract("LONGTERM_3_MONTH_TO_2_YEARS");
			job.setFurtherComments("n/a");
			job.setFurtherCommentsRegardingEducation("Wir erwarten ein abgeschlossenes Hochschulstudium sowie langjährige Berufserfahrung im Bereich Lebensmitteltechnik.");
			job.setJobOfferExpireDate(Calendar.getInstance());
			job.setLanguageSkillsEnglish("BUSINESS_FLUENT");
			job.setLanguageSkillsGerman("BUSINESS_FLUENT");
			job.setLocationOfEmployment("Abu Dhabi");
			job.setNumberOfJobs(1);
			job.setComputerSkillsComments("MS WORD/Excel/Access");
			job.setPossibleCommencementDate(Calendar.getInstance());
			job.setMinimumRequirementsForEducation("MASTER");
			job.setFurtherCommentsRegardingEducation("Fach-/Hochschulstudium im Bereich Lebensmitteltechnik");
			job.setSalary("3500");
			job.setWorkExperience("TWO_TO_FIVE");
			CountryDTO empCountry2 = new CountryDTO();
			empCountry2.setCountry("41");
			job.setCountryOfEmployment(empCountry2);

			modJO.setJobOffer(job);

			ModifyJobOfferResponse modJOR = stub.modifyJobOffer(modJO);
			System.out.println("Modify return Result for JobOffer " + job.getJobOfferId() + ": " + modJOR.get_return());

			// GetJobOffersByUser joBU = new GetJobOffersByUser();
			// joBU.setApdUserId(2);
			// GetJobOffersByUserResponse joBUR = stub.getJobOffersByUser(joBU);
			// JobDTO[] userJobOffers = joBUR.get_return();
			// for(int i=0;i<userJobOffers.length;i++){
			// System.out.println("UserJobOffer: "+userJobOffers[i].getJobOfferId()+"--"+userJobOffers[i].getJobDescription());//+"---"+allJobOffers[i].getComputerSkills().toString());
			// }
			//                        
			GetJobOfferByCriteria joBC = new GetJobOfferByCriteria();
			joBC.setCriteria("Suchmaschine");
			joBC.setCountry(null);
			joBC.setTerritory(null);
			GetJobOfferByCriteriaResponse joBCR = stub.getJobOfferByCriteria(joBC);
			JobDTO[] jobOfferByCriteria = joBCR.get_return();
			if (jobOfferByCriteria != null) {
				for (int i = 0; i < jobOfferByCriteria.length; i++) {
					System.out.println("CriteriaJobOffer: " + jobOfferByCriteria[i].getJobOfferId() + "--" + jobOfferByCriteria[i].getJobDescription());// +"---"+allJobOffers[i].getComputerSkills().toString());
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
