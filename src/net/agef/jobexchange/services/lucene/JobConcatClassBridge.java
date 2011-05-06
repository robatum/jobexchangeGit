/**
 * 
 */
package net.agef.jobexchange.services.lucene;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LanguageSkill;
import net.agef.jobexchange.domain.Languages;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import org.hibernate.search.bridge.ParameterizedBridge;

/**
 * @author Administrator
 *
 */
public class JobConcatClassBridge implements FieldBridge, ParameterizedBridge {

    private String sepChar;

    @SuppressWarnings("unchecked")
	public void setParameterValues(Map parameters) {
        this.sepChar = (String) parameters.get( "sepChar" );
    }
   


    public void set(String name, 
                Object value, //the department instance (entity) in this case
                Document document, //the Lucene document 
                LuceneOptions options){
                
                //Field.Store store, Field.Index index, Float boost) { // H-Search v.3.0
        // In this particular class the name of the new field was passed
        // from the name field of the ClassBridge Annotation. This is not
        // a requirement. It just works that way in this instance. The
        // actual name could be supplied by hard coding it below.
        JobImpl job = (JobImpl) value;
        String fieldValue1 = job.getJobDescription();
        if ( fieldValue1 == null ) {
            fieldValue1 = "";
        }
        String fieldValue2 = job.getTaskDescription();
        if ( fieldValue2 == null ) {
            fieldValue2 = "";
        }
        String fieldValue3 = job.getOrganisationDescription();
        if ( fieldValue3 == null ) {
            fieldValue3 = "";
        }
        
        String fieldValue4 = job.getAlternativeProfession();
        if ( fieldValue4 == null ) {
            fieldValue4 = "";
        }
        
        String fieldValue5 = job.getDesiredProfession();
        if ( fieldValue5 == null ) {
            fieldValue5 = "";
        }
        
        String fieldValue6 = job.getComputerSkillsComments();
        if ( fieldValue6 == null ) {
            fieldValue6 = "";
        }
        
        String fieldValue7 = job.getSpecialKnowledge();
        if ( fieldValue7 == null ) {
            fieldValue7 = "";
        }
        
        String fieldValue8 = job.getFurtherComments();
        if ( fieldValue8 == null ) {
            fieldValue8 = "";
        }
        
        String fieldValue9 = "";
        if(job.getOccupationalField()!=null){
        	fieldValue9 = job.getOccupationalField().toString();
	        if ( fieldValue9 == null ) {
	            fieldValue9 = "";
	        }
        }
        
        String fieldValue10 = "";
        if(job.getLanguageSkillsOther()!=null){
	        List<LanguageSkill> lang = job.getLanguageSkillsOther();
	        Iterator<LanguageSkill> it = lang.iterator();
			while(it.hasNext()){
				if(fieldValue10.equals("")){
					Languages language = it.next().getName();
					if(language != null)
					fieldValue10 = language.getIsoNameLong();	
				} else {
					Languages language = it.next().getName();
					if(language != null)
					fieldValue10 = fieldValue10+" "+language.getIsoNameLong();	
				}
				if ( fieldValue10 == null ) {
		            fieldValue10 = "";
		        }
			}
	        if ( fieldValue10 == null ) {
	            fieldValue10 = "";
	        }
        }
        
        String fieldValue11 = "";
        if(job.getCountryOfEmployment()!=null){
        	fieldValue11 = job.getCountryOfEmployment().getShortEnglishName();
	        if ( fieldValue11 == null ) {
	            fieldValue11 = "";
	        }
        }
        
        String fieldValue12 = "";
        if(job.getCobraJobId()!=null){
        	fieldValue12 = job.getCobraJobId().toString();
	        if ( fieldValue12 == null ) {
	            fieldValue12 = "";
	        }
        }
        
        String fieldValue13 = "";
        if(job.getJobOfferId()!=null){
        	fieldValue13 = job.getJobOfferId().toString();
	        if ( fieldValue13 == null ) {
	            fieldValue13 = "";
	        } 
        }
        
        
        String fieldValue = fieldValue1 + sepChar + fieldValue2+ sepChar + fieldValue3
        + sepChar + fieldValue4+ sepChar + fieldValue5+ sepChar + fieldValue6+ sepChar + fieldValue7
        + sepChar + fieldValue8+ sepChar + fieldValue9+ sepChar + fieldValue10+ sepChar + fieldValue11
        + sepChar + fieldValue12+ sepChar + fieldValue13;
        Field field = new Field( name, fieldValue, options.getStore(), options.getIndex() );
        if ( options.getBoost() != null ) field.setBoost( options.getBoost() );
        document.add( field );
    }


}