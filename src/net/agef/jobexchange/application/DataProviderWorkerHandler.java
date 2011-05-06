/**
 * 
 */
package net.agef.jobexchange.application;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.slf4j.Logger;

import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.exceptions.DataProviderNotFoundException;
import net.agef.jobexchange.integration.DataProviderDAO;

/**
 * @author AGEF
 *
 */
public class DataProviderWorkerHandler implements DataProviderWorker{
	
	private Logger logger;
	private DataProviderDAO dataProviderDAO;
	private Collection<DataProvider> validDataProvider = new TreeSet<DataProvider>();
	
	public DataProviderWorkerHandler(DataProviderDAO dataProviderDAO, Logger logger){
		this.logger = logger;
		this.dataProviderDAO = dataProviderDAO;
		this.validDataProvider = dataProviderDAO.findAll();
	}
	
	@Override
	public DataProvider checkForValidDataProviderByIP(String dataProviderIP){
		Iterator<DataProvider> it = validDataProvider.iterator();
		while(it.hasNext()){
			DataProvider dp = it.next();
			for (int i = 0;i<dp.getProviderIP().length;i++){
				//logger.info("DataProvider: "+dp.getProviderName()+"---"+dp.getProviderIP()[i]);
				if(dataProviderIP.equals(dp.getProviderIP()[i])){
					return dp;
				}
			}
		}
		return null;
	}
	
	@Override
	public DataProvider getDataProviderByName(String dataProviderName) throws DataProviderNotFoundException{
		DataProvider dataProvider = dataProviderDAO.findDataProviderByName(dataProviderName);
		if(dataProvider!= null){
			return dataProvider;
		} else throw new DataProviderNotFoundException(dataProviderName);
	}

}
