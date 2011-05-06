package net.agef.jobexchange.application;

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

public interface UserWorker {

	public void addUser(User user) throws ObjectNotSavedException, PassedAttributeIsNullException;
	public void addUser(User user, DataProvider dataProvider) throws ObjectNotSavedException, PassedAttributeIsNullException;
	public void modifyUser(User user) throws ObjectNotSavedException, PassedAttributeIsNullException;
	public void deleteUser(User user) throws ObjectNotDeletedException, PassedAttributeIsNullException;
	public void deleteUser(Long userId) throws ObjectNotDeletedException, PassedAttributeIsNullException;
	public User getUserByAPDId(Long userId)throws APDUserNotFoundException;
	public User getUserByCobraId(Long userId, boolean isOrganisationUser)throws CobraUserNotFoundException;
	public User getUserByInwentId(Long inwentUserId) throws InwentUserNotFoundException;
	public void switchContactAddressByAPDUserId(Long userId)throws APDUserNotFoundException, ObjectNotSavedException;
	public void setUserOnlineStatus(User user, Boolean onlineStatus) throws ObjectNotSavedException, CantChangeOnlineStateException;
	public List<User> getOrganisationUserByLoginUser(LoginUser user) throws PassedAttributeIsNullException;
	public List<OrganisationRoleData> getOrganisationUserDataByLoginUser(LoginUser user) throws PassedAttributeIsNullException;
}
