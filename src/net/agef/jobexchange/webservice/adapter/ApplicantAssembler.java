/**
 * 
 */
package net.agef.jobexchange.webservice.adapter;

import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.CountryNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.IndustrySectorNotFoundException;
import net.agef.jobexchange.exceptions.InwentUserNotFoundException;
import net.agef.jobexchange.webservice.entities.ApplicantDTO;


/**
 * @author Administrator
 *
 */
public interface ApplicantAssembler {
	
	public ApplicantDTO createDTOWithAPDId(Applicant applicant); 
	public ApplicantDTO createDTOWithCobraId(Applicant applicant);
	public Applicant createDomainObj(ApplicantDTO dto) throws APDUserNotFoundException, IndustrySectorNotFoundException,EnumValueNotFoundException, CountryNotFoundException;
	public Applicant createDomainObjByCobraId(ApplicantDTO dto) throws CobraUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
	public Applicant updateDomainObj(ApplicantDTO dto) throws ApplicantProfileNotFoundException ,APDUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
	public Applicant updateDomainObjByCobraId(ApplicantDTO dto) throws ApplicantProfileNotFoundException, APDUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
	public Applicant createDomainObjByInwentId(ApplicantDTO applicantProfile) throws InwentUserNotFoundException, IndustrySectorNotFoundException, EnumValueNotFoundException, CountryNotFoundException;
	public Applicant updateDomainObjByInwentId(ApplicantDTO dto) throws ApplicantProfileNotFoundException, EnumValueNotFoundException, IndustrySectorNotFoundException, CountryNotFoundException;

}
