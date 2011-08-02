package net.agef.jobexchange.webservice.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.agef.jobexchange.domain.PortalIdentifier;
import net.agef.jobexchange.domain.PortalIdentifierEnum;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;



public class PortalIdAssemblerWorker implements PortalIdAssembler {

	@Override
	public Byte[] createDTO(List<PortalIdentifier> portalIdList)  {
		Iterator<PortalIdentifier> it = portalIdList.iterator();
		Integer counter = 0;
		Byte[] portalIdValueList = new Byte[portalIdList.size()];
		while(it.hasNext()){
			System.out.println("Applicant: portalIdListe.toArray : konvertiere PortalID in Array");
			portalIdValueList[counter] = it.next().getPortalIdAsByte();
			counter++;
		}
		return portalIdValueList;
	}

	@Override
	public List<PortalIdentifier> createDomainObj(Byte[] dto) throws EnumValueNotFoundException {
		List<PortalIdentifier> portalIdentifierList = new ArrayList<PortalIdentifier>();
		if (dto != null)
			for (int i = 0; i < dto.length; i++) {
				PortalIdentifier portalIdentifier = new PortalIdentifier(dto[i]);				
				portalIdentifierList.add(portalIdentifier);
			}	
		return portalIdentifierList;
	}

	@Override
	public List<PortalIdentifier> updateDomainObj(Byte[] dto, List<PortalIdentifier> existingPortalIds) throws EnumValueNotFoundException {
		if (dto!=null){
			while(existingPortalIds.size() > dto.length){
				existingPortalIds.remove(existingPortalIds.size() - 1);
			}
			
			for (int i = 0; i < dto.length; i++) {
				if (i < existingPortalIds.size()) {
					existingPortalIds.set(i, new PortalIdentifier(dto[i]));
				} else existingPortalIds.add(i, new PortalIdentifier(dto[i]));
			}	
		}
		return existingPortalIds;
	}
}
