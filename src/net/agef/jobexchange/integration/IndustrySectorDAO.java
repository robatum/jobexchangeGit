/**
 * 
 */
package net.agef.jobexchange.integration;

import org.chenillekit.hibernate.daos.GenericDAO;

import net.agef.jobexchange.domain.IndustrySector;

/**
 * @author AGEF
 *
 */
public interface IndustrySectorDAO extends GenericDAO<IndustrySector, Long>{

	public IndustrySector findIndustrySectorById(Long sectorId);
	
	public IndustrySector findIndustrySectorByName(String sectorName);
	
}
