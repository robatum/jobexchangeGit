/**
 * 
 */
package net.agef.jobexchange.integration;

import org.chenillekit.hibernate.daos.GenericDAO;

import net.agef.jobexchange.domain.Territory;



/**
 * @author Administrator
 *
 */
public interface TerritoryDAO extends GenericDAO<Territory, Long>{

	public Territory findTerritoryByISONumber(Integer isoNumber);
	
	public Territory findTerritoryByName(String territoryName);
	
}
