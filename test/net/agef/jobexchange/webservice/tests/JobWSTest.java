/**
 * UserWSTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
package net.agef.jobexchange.webservice.tests;

import java.rmi.RemoteException;

import net.agef.jobexchange.webservice.tests.util.JobWSStub.CountryDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOffersByUserAndCriteria;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.GetJobOffersByUserAndCriteriaResponse;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.JobDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub.TerritoryDTO;
import net.agef.jobexchange.webservice.tests.util.JobWSStub;

import org.apache.axis2.AxisFault;

/*
 *  UserWSTest Junit test case
 */

public class JobWSTest extends junit.framework.TestCase {

	private JobWSStub jobStub;
	private GetJobOffersByUserAndCriteria getJobsCriteria;

	public void setUp() {
		try {
			super.setUp();
			jobStub = new JobWSStub();
			getJobsCriteria = new GetJobOffersByUserAndCriteria();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testGetJobOfferByUserAndCriteria_MIMIMAL() {
		getJobsCriteria.setApdUserId(1);
		getJobsCriteria.setIndexStart(0);
		getJobsCriteria.setNumberOfResults(10);
		getJobsCriteria.setJobActive("ALL");
		getJobsCriteria.setCountry(new CountryDTO());
		getJobsCriteria.setTerritory(new TerritoryDTO());
		try {
			GetJobOffersByUserAndCriteriaResponse getJobsCriteriaResponse = jobStub.getJobOffersByUserAndCriteria(getJobsCriteria);
			JobDTO[] jobs = getJobsCriteriaResponse.get_return();
			assertTrue(jobs.length > 0);
			System.out.println(jobs.length);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testGetJobOfferByUserAndCriteria_RANGE() {
		getJobsCriteria.setApdUserId(1);
		getJobsCriteria.setIndexStart(50);
		getJobsCriteria.setNumberOfResults(0);
		getJobsCriteria.setJobActive("ALL");
		getJobsCriteria.setCountry(new CountryDTO());
		getJobsCriteria.setTerritory(new TerritoryDTO());
		try {
			GetJobOffersByUserAndCriteriaResponse getJobsCriteriaResponse = jobStub.getJobOffersByUserAndCriteria(getJobsCriteria);
			JobDTO[] jobs = getJobsCriteriaResponse.get_return();
			assertNull(jobs);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testGetJobOfferByUserAndCriteria_TERRITORY() {
		getJobsCriteria.setApdUserId(1);
		getJobsCriteria.setIndexStart(0);
		getJobsCriteria.setNumberOfResults(10);
		getJobsCriteria.setJobActive("ALL");
		CountryDTO country = new CountryDTO();
		getJobsCriteria.setCountry(country);
		TerritoryDTO territory = new TerritoryDTO();
		territory.setTerritory("21");
		getJobsCriteria.setTerritory(territory);
		try {
			GetJobOffersByUserAndCriteriaResponse getJobsCriteriaResponse = jobStub.getJobOffersByUserAndCriteria(getJobsCriteria);
			JobDTO[] jobs = getJobsCriteriaResponse.get_return();
			assertTrue(jobs.length > 0);
			assertTrue(jobs[0].getAlternativeProfession().contains("Systemadministrator Unix"));
			System.out.println(jobs.length);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testGetJobOfferByUserAndCriteria_COUNTRY() {
		getJobsCriteria.setApdUserId(1);
		getJobsCriteria.setIndexStart(0);
		getJobsCriteria.setNumberOfResults(10);
		getJobsCriteria.setJobActive("ALL");
		CountryDTO country = new CountryDTO();
		country.setIsoNumber(28);
		country.setCountry("Antigua and Barbuda");
		getJobsCriteria.setCountry(country);
		TerritoryDTO territory = new TerritoryDTO();
		getJobsCriteria.setTerritory(territory);
		try {
			GetJobOffersByUserAndCriteriaResponse getJobsCriteriaResponse = jobStub.getJobOffersByUserAndCriteria(getJobsCriteria);
			JobDTO[] jobs = getJobsCriteriaResponse.get_return();
			assertTrue(jobs.length > 0);
			assertTrue(jobs[0].getAlternativeProfession().contains("Systemadministrator Unix"));
			System.out.println(jobs.length);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testGetJobOfferByUserAndCriteria_NOT_IN_COUNTRY() {
		getJobsCriteria.setApdUserId(1);
		getJobsCriteria.setIndexStart(0);
		getJobsCriteria.setNumberOfResults(10);
		getJobsCriteria.setJobActive("ALL");
		CountryDTO country = new CountryDTO();
		country.setIsoNumber(28);
		country.setCountry("Germany");
		getJobsCriteria.setCountry(country);
		TerritoryDTO territory = new TerritoryDTO();
		getJobsCriteria.setTerritory(territory);
		try {
			GetJobOffersByUserAndCriteriaResponse getJobsCriteriaResponse = jobStub.getJobOffersByUserAndCriteria(getJobsCriteria);
			JobDTO[] jobs = getJobsCriteriaResponse.get_return();
			assertNull(jobs);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
