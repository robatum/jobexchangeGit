/**
 * 
 */
package net.agef.jobexchange.integration;

import org.chenillekit.hibernate.daos.GenericDAO;

import net.agef.jobexchange.domain.Currency;



/**
 * @author Administrator
 *
 */
public interface CurrencyDAO extends GenericDAO<Currency, Long>{

	public Currency findCurrencyByName(String currencyName);
	
	public Currency findCurrencyByIsoNumber(Long currencyIsoNumber);
	
}
