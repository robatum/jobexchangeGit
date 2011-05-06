/**
 * 
 */
package net.agef.jobexchange.services.lucene;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.domain.Education;
import net.agef.jobexchange.domain.LanguageSkill;
import net.agef.jobexchange.domain.Languages;
import net.agef.jobexchange.domain.WorkExperience;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.ParameterizedBridge;

/**
 * @author AGEF
 *
 */
public class ApplicantConcatClassBridge implements FieldBridge, ParameterizedBridge {

	private String sepChar;

    @SuppressWarnings("unchecked")
	public void setParameterValues(Map parameters) {
        this.sepChar = (String) parameters.get( "sepChar" );
    }

    public void set(String name, 
                Object value, //the department instance (entity) in this case
                Document document, //the Lucene document 
                LuceneOptions options){
                //Field.Store store, Field.Index index, Float boost) { H-Search v. 3.0
        // In this particular class the name of the new field was passed
        // from the name field of the ClassBridge Annotation. This is not
        // a requirement. It just works that way in this instance. The
        // actual name could be supplied by hard coding it below.
        Applicant applicant = (Applicant) value;
        
        String fieldValue1 = "";
        if(applicant.getPreferredFieldOfActivity()!=null){
	        fieldValue1 = applicant.getPreferredFieldOfActivity().getSectorNameEnglish();
	        if ( fieldValue1 == null ) {
	            fieldValue1 = "";
	        }
        }
        
        String fieldValue2 = applicant.getComputerSkillsComments();
        if ( fieldValue2 == null ) {
            fieldValue2 = "";
        }
        
        String fieldValue3 = applicant.getAdditionalSkills();
        if ( fieldValue3 == null ) {
            fieldValue3 = "";
        }
        
        String fieldValue4 = "";
        if(applicant.getManagementExperienceSector()!=null){
	        fieldValue4 = applicant.getManagementExperienceSector().getSectorNameEnglish();
	        if ( fieldValue4 == null ) {
	            fieldValue4 = "";
	        }
        }
        
        String fieldValue5 = "";
        if(applicant.getManagementExperienceSector()!=null){
	        fieldValue5 = applicant.getManagementExperienceSector().getSectorNameGerman();
	        if ( fieldValue5 == null ) {
	            fieldValue5 = "";
	        }
        }
        
//		 Fällt mit den Änderungen des Karriereprofils im Sommer 2010 weg, da 
//		 Managementerfahrung jetzt pro Arbeitserfahrung erfasst wird
//        String fieldValue6 = applicant.getManagementExperienceRemarks();
//        if ( fieldValue6 == null ) {
//            fieldValue6 = "";
//        }
        
        
        String fieldValue7 = "";
        if(applicant.getHighestDegree()!=null){
	        fieldValue7 = applicant.getHighestDegree().getFieldSpecialization();
	        if ( fieldValue7 == null ) {
	            fieldValue7 = "";
	        }
        }
        
        String fieldValue8 = "";
        if(applicant.getWorkExperience()!=null){
	        List<WorkExperience> workExpSet = applicant.getWorkExperience();
	        Iterator<WorkExperience> it = workExpSet.iterator();
			while(it.hasNext()){
				WorkExperience workExp = it.next();
				if(fieldValue8.equals("")){
					fieldValue8 = workExp.getJobDescription();
					fieldValue8 = fieldValue8+" "+workExp.getJobTitle();
				} else {
					fieldValue8 = fieldValue8+" "+workExp.getJobDescription();
					fieldValue8 = fieldValue8+" "+workExp.getJobTitle();
				}
				 if ( fieldValue8 == null ) {
			            fieldValue8 = "";
			        }
			}
	        if ( fieldValue8 == null ) {
	            fieldValue8 = "";
	        }
        }
        
        String fieldValue9 = "";
        if(applicant.getLanguageSkillsOther()!=null){
	        List<LanguageSkill> lang = applicant.getLanguageSkillsOther();
	        Iterator<LanguageSkill> it = lang.iterator();
			while(it.hasNext()){
				if(fieldValue9.equals("")){
					Languages language = it.next().getName();
					if(language != null)
					fieldValue9 = language.getIsoNameLong();	
				} else {
					Languages language = it.next().getName();
					if(language != null)
					fieldValue9 = fieldValue9+" "+language.getIsoNameLong();	
				}
				if ( fieldValue9 == null ) {
		            fieldValue9 = "";
		        }
			}
	        if ( fieldValue9 == null ) {
	            fieldValue9 = "";
	        }
        }
        
        String fieldValue10 = applicant.getAdditionalRemarks();
        if ( fieldValue10 == null ) {
            fieldValue10 = "";
        }
        
        String fieldValue11 = "";
        if(applicant.getFurtherEducation()!=null){
	        List<Education> furtherEdu = applicant.getFurtherEducation();
	        Iterator<Education> it = furtherEdu.iterator();
			while(it.hasNext()){
				if(fieldValue11.equals("")){
					fieldValue11 = it.next().getFieldSpecialization();
				} else {
					fieldValue11 = fieldValue11+" "+it.next().getFieldSpecialization();
				}
				if ( fieldValue11 == null ) {
		            fieldValue11 = "";
		        }
			}
	        if ( fieldValue11 == null ) {
	            fieldValue11 = "";
	        }
        }
        
        
        
        String fieldValue12 = "";
        if(applicant.getApplicantProfileId()!=null){
        	fieldValue12 = applicant.getApplicantProfileId().toString();
	        if ( fieldValue12 == null ) {
	            fieldValue12 = "";
	        }
        }
        
        String fieldValue13 = "";
        if(applicant.getApplicantProfileOwner()!=null && applicant.getApplicantProfileOwner().getCobraSuperId()!=null){
        	fieldValue13 = applicant.getApplicantProfileOwner().getCobraSuperId().toString();
	        if ( fieldValue13 == null ) {
	            fieldValue13 = "";
	        } 
        }
        
        String fieldValue14 = "";
        if(applicant.getApplicantProfileOwner() != null 
        	&& applicant.getApplicantProfileOwner().getCurrentContactAddress() != null
        	&& applicant.getApplicantProfileOwner().getCurrentContactAddress().getCountry() != null	){
        	fieldValue14 = applicant.getApplicantProfileOwner().getCurrentContactAddress().getCountry().getShortEnglishName() ;
	        if ( fieldValue14 == null ) {
	            fieldValue14 = "";
	        }
        }
        
        String fieldValue15 = "";
        if(applicant.getAvailability() != null){
        	fieldValue15 = applicant.getAvailability().name();
        	if(fieldValue15 == null){
        		fieldValue15 = "";
        	}
        }
        
        /* Bewerberprofil Sommer 2010 */
        
        String fieldValue16 = "";
	    fieldValue16 = applicant.getLookingFor();
	    if ( fieldValue16 == null ) {
	            fieldValue16 = "";
	    }
	    
	    String fieldValue17 = "";
	    fieldValue17 = applicant.getOffering();
	    if ( fieldValue17 == null ) {
	            fieldValue17 = "";
	    }
        
	    String fieldValue18 = "";
	    fieldValue18 = applicant.getReferencesAndCertificatesComments();
	    if ( fieldValue18 == null ) {
	            fieldValue18 = "";
	    }
	    
	    String fieldValue19 = "";
        if(applicant.getHighestDegree()!=null){
	        fieldValue19 = applicant.getHighestDegree().getField();
	        if ( fieldValue19 == null ) {
	            fieldValue19 = "";
	        }
        }
        
        String fieldValue20 = "";
	    fieldValue20 = applicant.getPublicationsComments();
	    if ( fieldValue20 == null ) {
	            fieldValue20 = "";
	    }
	    
	    String fieldValue21 = "";
	    fieldValue21 = applicant.getFurtherOnlineActivitiesComments();
	    if ( fieldValue21 == null ) {
	            fieldValue21 = "";
	    }
        
        /* Ende Bewerberprofil Sommer 2010 */
	    
        String fieldValue = fieldValue1 + sepChar + fieldValue2+ sepChar + fieldValue3+ sepChar +fieldValue4 
        + sepChar + fieldValue5+ sepChar +fieldValue7 + sepChar + fieldValue8+ sepChar + fieldValue9
        + sepChar + fieldValue10+ sepChar + fieldValue11+ sepChar + fieldValue12+ sepChar + fieldValue13+ sepChar + fieldValue14
        + sepChar + fieldValue15 + sepChar + fieldValue16 + sepChar + fieldValue17 + sepChar + fieldValue18 + sepChar + fieldValue19
        + sepChar + fieldValue20 + sepChar + fieldValue21;
        Field field = new Field( name, fieldValue, options.getStore(), options.getIndex() );
        if ( options.getBoost() != null ) field.setBoost( options.getBoost() );
        document.add( field );
    }

}
