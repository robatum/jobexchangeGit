/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.Currency;

/**
 * @author AGEF
 *
 */
public interface CurrencyAssembler {
	
	public String createDTO(Currency currency); 
	public Currency getDomainObj(String dto);

}
