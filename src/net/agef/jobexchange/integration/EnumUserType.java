package net.agef.jobexchange.integration;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
@SuppressWarnings("unchecked")
public abstract class EnumUserType implements UserType{


	private Class enumClass;
	
	public EnumUserType(Class enumClass){
		this.enumClass = enumClass;
	}
	
	public Object assemble(Serializable cached, Object owner)
	   throws HibernateException
	 {
	   return cached;
	 }

	public Object deepCopy(Object value) throws HibernateException
	 {
	   return value;
	 }

	public Serializable disassemble(Object value)
	   throws HibernateException
	 {
	   return (Serializable) value;
	 }

	public boolean equals(Object x, Object y) throws HibernateException
	 {
	   if (x == y)
	   {
	     return true;
	   }

	   if (x == null || y == null)
	   {
	     return false;
	   }

	   return Hibernate.STRING.isEqual(x, y);
	 }

	public int hashCode(Object object) throws HibernateException
	 {
	   return object.hashCode();
	 }

	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object nullSafeGet(ResultSet arg0, String[] arg1, Object arg2)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		
	}

	public Object replace(Object original, Object target, Object owner)
	   throws HibernateException
	 {
	   return original;
	 }

	public Class returnedClass()
	 {
	   return enumClass;
	 }

	public int[] sqlTypes()
	 {
	   return new int[] {
	     Types.VARCHAR
	   };
	 }
	
	

}
