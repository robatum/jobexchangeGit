/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.Currency;
import net.agef.jobexchange.integration.CurrencyDAO;

/**
 * @author AGEF
 *
 */
public class CurrencyAssemblerWorker implements CurrencyAssembler{
	
	CurrencyDAO currencyDAO;
	
	public CurrencyAssemblerWorker(CurrencyDAO currencyDAO){
		this.currencyDAO = currencyDAO;
	}

	@Override
	public String createDTO(Currency currency) {
		if (currency!=null) { 
			return currency.getNameEnglish();
		} else return null;
	}

	@Override
	public Currency getDomainObj(String dto) {
		if(dto!=null && !dto.equals("")){
			try {
				//if currency is provided by id
				if (org.apache.commons.lang.StringUtils.isNumeric(dto)) {
					return currencyDAO.findCurrencyByIsoNumber(new Long(dto));
				} else // else if currency is provided by name 
				{
					return currencyDAO.findCurrencyByName(dto);
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}
