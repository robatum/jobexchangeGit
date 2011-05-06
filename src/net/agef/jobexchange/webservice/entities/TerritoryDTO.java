/**
 * 
 */
package net.agef.jobexchange.webservice.entities;

/**
 * @author AGEF
 *
 */
public class TerritoryDTO {
	
	public TerritoryDTO(){
		
	}
	
	public TerritoryDTO(String territory){
		this.territory = territory;
	}
	
	private String territory;
	

	/**
	 * Liefert den Namen des Landes in englischer Kurzschreibweise.
	 * 
	 * @return the territory
	 */
	public String getTerritory() {
		return territory;
	}

	/**
	 * Erwartet den Namen (englishe Kurzschreibweise) oder die ISO Nummer des Kontinents.
	 * 
	 * @param territory the territory to set
	 */
	public void setTerritory(String territory) {
		this.territory = territory;
	}

}
