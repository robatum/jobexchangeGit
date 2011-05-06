package net.agef.jobexchange.application;

import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.exceptions.DataProviderNotFoundException;

public interface DataProviderWorker {
	
	public DataProvider checkForValidDataProviderByIP(String dataProviderIP);
	public DataProvider getDataProviderByName(String dataProviderName) throws DataProviderNotFoundException;

}
