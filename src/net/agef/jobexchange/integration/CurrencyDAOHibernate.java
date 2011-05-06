/**
 * 
 */
package net.agef.jobexchange.integration;

import java.util.List;

import net.agef.jobexchange.domain.Currency;

import org.chenillekit.hibernate.daos.AbstractHibernateDAO;
import org.chenillekit.hibernate.utils.QueryParameter;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * @author Administrator
 *
 */
public class CurrencyDAOHibernate extends AbstractHibernateDAO<Currency, Long> implements CurrencyDAO{
	
	public CurrencyDAOHibernate(Logger logger, Session session) {
		super(logger, session);
	}

	public Currency findCurrencyByName(String currencyName){
		List<Currency> currencyList = this.findByQuery("From Currency c WHERE c.nameEnglish = :currencyName ", new QueryParameter("currencyName", currencyName));
		if(!currencyList.isEmpty()){
			return currencyList.get(0);
		}
		return null;
	}
	
	public Currency findCurrencyByIsoNumber(Long currencyIsoNumber){
		List<Currency> currencyList = this.findByQuery("From Currency c WHERE c.isoNumber = :currencyIsoNumber ", new QueryParameter("currencyIsoNumber", currencyIsoNumber));
		if(!currencyList.isEmpty()){
			return currencyList.get(0);
		}
		return null;
	}
}
