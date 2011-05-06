/**
 * 
 */
package net.agef.jobexchange.services.internal;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * @author AGEF
 * 
 * adding custom MySQL Dialect with UTF-8 as default charset and InnoDB table engine
 */
public class MysqlDialectUtf8 extends MySQL5InnoDBDialect{
	
	public String getTableTypeString(){
		return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}
}
