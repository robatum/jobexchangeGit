/**
 * UserWSTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
package net.agef.jobexchange.webservice.tests;

import java.rmi.RemoteException;

import net.agef.jobexchange.webservice.tests.util.UserWSStub.AddAlumniUser;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.AddInwentAlumniUser;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.AlumniRoleDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfUserExist;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfUserExistResponse;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CountryDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.DeleteUser;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.DeleteUserResponse;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.UserDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub;

import org.apache.axis2.AxisFault;

/*
 *  UserWSTest Junit test case
 */

public class UserWSTest extends junit.framework.TestCase {
	
	private UserWSStub userStub;

	public void setUp(){
		try {
			super.setUp();
			userStub = new UserWSStub();
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testUserCreationAndDeletion() {
		AddAlumniUser alumniUser = new AddAlumniUser();

		UserDTO user = new UserDTO();
		CountryDTO nationality = new CountryDTO();
		nationality.setCountry("Germany");
		user.setNationality(nationality);
		user.setFamilyName("Jon Doe");
		// user.setDataProvider("AGEF");
		alumniUser.setUser(user);
		AlumniRoleDTO alumniRole = new AlumniRoleDTO();
		alumniRole.setOrganisationName("Test GmbH");
		alumniUser.setUserRole(alumniRole);
		alumniUser.setApdUserId(12);
		try {
			userStub.addAlumniUser(alumniUser);
			
			CheckIfUserExist checkIfUserExist12 = new CheckIfUserExist();
			checkIfUserExist12.setApdUserId(12);
			
			/* check if new user exists */
			CheckIfUserExistResponse checkUserExistsResponse = userStub.checkIfUserExist(checkIfUserExist12);
			userStub.checkIfUserExist(checkIfUserExist12);
			assertTrue(checkUserExistsResponse.get_return());
			
			/* delete newly created user */
//			DeleteUser deleteUser12 = new DeleteUser();
//			deleteUser12.setApdUserId(12);
//			DeleteUserResponse deleteResponse = userStub.deleteUser(deleteUser12);
//			assertTrue(deleteResponse.get_return());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
