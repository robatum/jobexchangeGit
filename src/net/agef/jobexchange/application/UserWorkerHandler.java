/**
 * 
 */
package net.agef.jobexchange.application;

import java.util.ArrayList;
import java.util.List;

import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.OrganisationRoleData;

import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.InwentUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotDeletedException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.integration.DataProviderDAO;
import net.agef.jobexchange.integration.UserDAO;

/**
 * @author Administrator
 *
 */
public class UserWorkerHandler implements UserWorker{
	
	private UserDAO userDAO;
	private DataProviderDAO dataProviderDAO;
	
	public UserWorkerHandler(UserDAO userDAO, DataProviderDAO dataProviderDAO){
		this.userDAO = userDAO;
		this.dataProviderDAO = dataProviderDAO;
	}

	@Override
	public void addUser(User user) throws ObjectNotSavedException, PassedAttributeIsNullException {
		if (user != null) {
			try {
				userDAO.doSave(user);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException(user.getId().toString());
			}
		}else throw new PassedAttributeIsNullException();
	}
	
	@Override
	public void addUser(User user, DataProvider dataProvider) throws ObjectNotSavedException, PassedAttributeIsNullException{
		if (user != null && dataProvider != null) {
			user.setDataProvider(dataProviderDAO.doRetrieve(dataProvider
					.getId(), false));
			try {
				userDAO.doSave(user);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException(user.getId().toString());
			}
		} else throw new PassedAttributeIsNullException();
	}

	@Override
	public void deleteUser(User user) throws ObjectNotDeletedException, PassedAttributeIsNullException {
		if (user != null) {
			try {
				userDAO.doDelete(user);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotDeletedException();
			}
		} else throw new PassedAttributeIsNullException();
	}
	
	@Override
	public void deleteUser(Long userId) throws ObjectNotDeletedException, PassedAttributeIsNullException{
		if (userId != null) {
			try {
				userDAO.doDelete(userId);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotDeletedException(userId.toString());
			}
		} else throw new PassedAttributeIsNullException();
	}

	@Override
	public void modifyUser(User user) throws ObjectNotSavedException, PassedAttributeIsNullException {
		if (user != null) {
			try {
				userDAO.doSave(user);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException(user.getId().toString());
			}
		} else throw new PassedAttributeIsNullException();
		
	}

	@Override
	public User getUserByAPDId(Long userId) throws APDUserNotFoundException {
		User user = userDAO.findAPDUserByID(userId);
		if(user !=null){
			return user;
		} else throw new APDUserNotFoundException(String.valueOf(userId)); 
	}
	
	@Override
	public User getUserByCobraId(Long userId, boolean isOrganisationUser) throws CobraUserNotFoundException {
		User user = userDAO.findCobraUserByID(userId, isOrganisationUser);
		if(user !=null){
			return user;
		} else throw new CobraUserNotFoundException(userId.toString()); 
	}

	@Override
	public User getUserByInwentId(Long userId) throws InwentUserNotFoundException {
		User user = userDAO.findInwentUserByID(userId);
		if(user !=null){
			return user;
		} else throw new InwentUserNotFoundException(String.valueOf(userId)); 
	}
	
	@Override
	public void switchContactAddressByAPDUserId(Long apdUserId) throws APDUserNotFoundException, ObjectNotSavedException {
		User user = userDAO.findAPDUserByID(apdUserId);
		if(user !=null){
			user.setCurrentAddress(!user.getCurrentAddress());
		} else throw new APDUserNotFoundException(apdUserId.toString()); 
		
		try {
			userDAO.doSave(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ObjectNotSavedException();
		}
	}

	@Override
	public void setUserOnlineStatus(User user, Boolean onlineStatus) throws ObjectNotSavedException, CantChangeOnlineStateException {
		if(user != null && onlineStatus != null ){ //&& user.getOnlineStatus()!= onlineStatus -> auskommentieren um true als Rückgabewert für CobraDB zu haben
			user.setOnlineStatus(onlineStatus);
			try {
				userDAO.doSave(user);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException();
			}	
		} else throw new CantChangeOnlineStateException();
	}
	
	public List<User> getOrganisationUserByLoginUser(LoginUser user) throws PassedAttributeIsNullException{
		if(user !=null){
			return user.getProvidedOrganisations();
		} throw new PassedAttributeIsNullException();
	}
	
	public List<OrganisationRoleData> getOrganisationUserDataByLoginUser(LoginUser user) throws PassedAttributeIsNullException{
		if (user != null) {
			List<User> orgUser = user.getProvidedOrganisations();
			List<OrganisationRoleData> orgRoleData;
			if (orgUser != null) {
				orgRoleData = new ArrayList<OrganisationRoleData>();
				for (int i = 0; i < orgUser.size(); i++) {
					orgRoleData.add((OrganisationRoleData) orgUser.get(i)
							.getUserRoleData());
				}
			} else
				return null;
			return orgRoleData;
		} throw new PassedAttributeIsNullException();
	}
	
}
