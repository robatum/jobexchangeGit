package net.agef.jobexchange.webservice.tests;

import java.rmi.RemoteException;

import net.agef.jobexchange.webservice.tests.util.UserWSCallbackHandler;
import net.agef.jobexchange.webservice.tests.util.UserWSStub;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.AddAlumniUser;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.AlumniRoleDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfUserExist;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CheckIfUserExistResponse;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.CountryDTO;
import net.agef.jobexchange.webservice.tests.util.UserWSStub.UserDTO;

import org.apache.axis2.AxisFault;

public class UserWSClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UserWSStub userStub = new UserWSStub();
			UserWSCallbackHandler callbackHandler = null;

			AddAlumniUser alumniUser = new AddAlumniUser();

			UserDTO user = new UserDTO();
			// CountryDTO nationality = new CountryDTO();
			// nationality.setCountry("Germany");
			// user.setNationality(nationality);
			// user.setFamilyName("Jon Doe");
			// user.setDataProvider("AGEF");
			alumniUser.setUser(user);
			AlumniRoleDTO alumniRole = new AlumniRoleDTO();
			// alumniRole.setOrganisationName("Test GmbH");
			alumniUser.setUserRole(alumniRole);
			// alumniUser.setApdUserId(56);
			try {
				userStub.addAlumniUser(alumniUser);

				CheckIfUserExist checkIfUserExist12 = new CheckIfUserExist();
				checkIfUserExist12.setApdUserId(56);

				CheckIfUserExistResponse checkUserExistsResponse = userStub.checkIfUserExist(checkIfUserExist12);

				userStub.checkIfUserExist(checkIfUserExist12);
				System.out.println("running the userStub against the servic.");
				System.out.println(checkUserExistsResponse.get_return());
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		} catch (AxisFault e) {
			e.printStackTrace();
		}

	}

}
