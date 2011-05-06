package net.agef.jobexchange.integration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;


import net.agef.jobexchange.domain.AbstractUserRole;
import net.agef.jobexchange.domain.User;

public class UserRoleUserType extends EnumUserType{

	public UserRoleUserType() {
		super(AbstractUserRole.class);
	}
	
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String type = rs.getString(names[0]);
		//long amount = rs.getLong(names[1]);
		UserRoleType roleType = UserRoleType.valueOf(type);
		AbstractUserRole specification = roleType.getNewInstance();
		specification.setOwner((User)owner);
		return specification;
		}
	
	
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			preparedStatement.setNull(index, Types.VARCHAR);
			preparedStatement.setNull(index + 1, Types.NUMERIC);
		} 	else {
				AbstractUserRole roleState = (AbstractUserRole) value;
				String className = roleState.getClass().getName();
				UserRoleType[] roleTypes = UserRoleType.values();
				for (int i = 0; i <roleTypes.length; i++) {
					UserRoleType roleType = roleTypes[i];
					if (roleType.getClassName().equals(className)) {
					preparedStatement.setString(index, roleType.name());
					
					break;
				}
			}
		
		}
	}
	

}
