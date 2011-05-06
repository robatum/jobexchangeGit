/**
 * 
 */
package net.agef.jobexchange.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import net.agef.jobexchange.domain.AccessHistoryApplicant;
import net.agef.jobexchange.domain.AccessHistoryJobs;
import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.Currency;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.JobActiveEnum;
import net.agef.jobexchange.domain.JobApplication;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.SearchHistoryJobs;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.JobOfferNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.integration.AccessHistoryJobsDAO;
import net.agef.jobexchange.integration.CountryDAO;
import net.agef.jobexchange.integration.CurrencyDAO;
import net.agef.jobexchange.integration.DataProviderDAO;
import net.agef.jobexchange.integration.JobApplicationDAO;
import net.agef.jobexchange.integration.JobDAO;
import net.agef.jobexchange.integration.SearchHistoryJobsDAO;
import net.agef.jobexchange.integration.TerritoryDAO;
import net.agef.jobexchange.integration.UserDAO;
import net.agef.jobexchange.webservice.entities.JobSearchResultDTO;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.WildcardTermEnum;
import org.apache.lucene.search.similar.MoreLikeThis;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.Invokable;
import org.apache.tapestry5.ioc.services.ParallelExecutor;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.reader.ReaderProvider;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;



/**
 * @author Administrator
 *
 */
public class JobWorkerHandler implements JobWorker{
	
	private Logger logger;
	private HibernateSessionManager session;
	private JobDAO jobDAO;
	private UserDAO userDAO;
	private CurrencyDAO currencyDAO;
	private JobApplicationDAO jobApplicationDAO;
	private DataProviderDAO dataProviderDAO;
	private ParallelExecutor parallelExecutor;
	private SearchHistoryJobsDAO searchHistoryJobsDAO;
	private CountryDAO countryDAO;
	private TerritoryDAO territoryDAO;
	private AccessHistoryJobsDAO accessHistoryJobsDAO;
	
	public JobWorkerHandler(HibernateSessionManager session,AccessHistoryJobsDAO accessHistoryJobsDAO, CountryDAO countryDAO, TerritoryDAO territoryDAO, ParallelExecutor parallelExecutor, UserDAO userDAO, JobDAO jobDAO, CurrencyDAO currencyDAO,JobApplicationDAO jobApplicationDAO, DataProviderDAO dataProviderDAO, SearchHistoryJobsDAO searchHistoryJobsDAO, Logger logger){
		this.logger = logger;
		this.session = session;
		this.jobDAO = jobDAO;
		this.userDAO = userDAO;
		this.currencyDAO = currencyDAO;
		this.jobApplicationDAO = jobApplicationDAO;
		this.dataProviderDAO = dataProviderDAO;
		this.parallelExecutor = parallelExecutor;
		this.searchHistoryJobsDAO = searchHistoryJobsDAO;
		this.countryDAO = countryDAO;
		this.territoryDAO = territoryDAO;
		this.accessHistoryJobsDAO = accessHistoryJobsDAO;

	}
	
	public Collection<JobImpl> getAllJobOffers(){
		return jobDAO.findAll();
	}
	
	
	@Override
	public Collection<JobImpl> getJobOffersByAPDUser(Long apdUserId) throws APDUserNotFoundException {
		User user = userDAO.findAPDUserByID(apdUserId);
		if(user!=null){
			return jobDAO.findJobOffersByUser(user.getId());
		} else throw new APDUserNotFoundException(apdUserId.toString());
	}

	public Long addJobOffer(JobImpl jobOffer) throws ObjectNotSavedException, PassedAttributeIsNullException{
		if (jobOffer != null) {
			try {
				JobImpl savedJob = jobDAO.doSave(jobOffer);
				return (savedJob.getId()+23);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException();
			}
		} else throw new PassedAttributeIsNullException();
	}
	
	
	public Long addJobOffer(JobImpl jobOffer, DataProvider dataProvider) throws ObjectNotSavedException, PassedAttributeIsNullException{
		if (jobOffer != null && dataProvider != null) {
			try {
				jobOffer.setDataProvider(dataProviderDAO.doRetrieve(dataProvider.getId(), false));
				JobImpl savedJob = jobDAO.doSave(jobOffer);
				return (savedJob.getId()+23);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException(jobOffer.getId().toString());
			}
		}else throw new PassedAttributeIsNullException();
	}
	
//	public String[][] getAvgJobsPerMonthOnline(){
//		Collection<JobImpl> allJobOffers = this.getAllJobOffers();
//		Iterator<JobImpl> it = allJobOffers.iterator();
//		while(it.hasNext()){
//			JobImpl job = it.next();
//			for (int i = 0;i<job.get.length;i++){
//				//logger.info("DataProvider: "+dp.getProviderName()+"---"+dp.getProviderIP()[i]);
//				if(dataProviderIP.equals(dp.getProviderIP()[i])){
//					return dp;
//				}
//			}
//		}
//		
//		
//	}
	
	
	public Long increaseJobApplicationLinkCounter(Long jobOfferId) throws ObjectNotSavedException, JobOfferNotFoundException{
		JobImpl jobOffer;
		try {
			jobOffer = this.getJobOfferDetails(jobOfferId);
		} catch (JobOfferNotFoundException e) {
			e.printStackTrace();
			throw new JobOfferNotFoundException();
		}
		if(jobOffer.getClickCounter()!=null){
			jobOffer.setClickCounter(jobOffer.getClickCounter()+1);
		} else jobOffer.setClickCounter(new Long(1));
		
		try{
			jobDAO.doSave(jobOffer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ObjectNotSavedException();
		}
		return jobOffer.getClickCounter();
	}
	
	@Override
	public void setJobOfferOnlineStatus(JobImpl jobOffer, Boolean onlineStatus) throws ObjectNotSavedException, CantChangeOnlineStateException{
		if(jobOffer != null && onlineStatus != null ){ 	//&& jobOffer.getOnlineStatus()!= onlineStatus -- 
			jobOffer.setOnlineStatus(onlineStatus);		//Für Interaktion mit der Backend DB 	
			try{										//muss die Prüfung auch bei keiner Statusänderung true zurück geben
					jobDAO.doSave(jobOffer);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException();
			}
		} else throw new CantChangeOnlineStateException();
	}
	
	@Override
	public JobImpl getJobOfferByCobraId(Long cobraJobId) throws JobOfferNotFoundException{
		JobImpl job = jobDAO.findJobOfferByCobraId(cobraJobId);
		if(job != null){
			return job;
		} else throw new JobOfferNotFoundException();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAutoCompleteResults(String partialString){
		List<String> resultList = new ArrayList<String>();
		FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());  
		SearchFactory searchFactory = fullTextSession.getSearchFactory();
		DirectoryProvider jobProvider = searchFactory.getDirectoryProviders(JobImpl.class)[0];
		ReaderProvider readerProvider = searchFactory.getReaderProvider();
		IndexReader reader = readerProvider.openReader(jobProvider);
		
		try {
			WildcardTermEnum we = new WildcardTermEnum(reader, new Term("jobconcatsearchfield", partialString.toLowerCase()+"*")); 
			logger.info("Begin Terms:");
			int iterator = 0;
			do { 									 
				logger.info("Terms: "+we.term().text());
				resultList.add(we.term().text());
				iterator++;
			} while (we.next() && iterator<=10);
			logger.info("End Terms:");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    readerProvider.closeReader(reader);
		}
		return resultList;
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Collection<JobImpl> getMoreLikeThis(Long jobOfferId){
		List<JobImpl> mltJobOffers = new ArrayList<JobImpl>();
		
		FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());  
		SearchFactory searchFactory = fullTextSession.getSearchFactory();
		DirectoryProvider jobProvider = searchFactory.getDirectoryProviders(JobImpl.class)[0];
		ReaderProvider readerProvider = searchFactory.getReaderProvider();
		
		IndexSearcher is = null;
		try {
			is = new IndexSearcher(jobProvider.getDirectory());
		} catch (CorruptIndexException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IndexReader reader = readerProvider.openReader(jobProvider);
		QueryParser parser = new QueryParser("id", new StandardAnalyzer());
		Query query;
		
		// TODO Klasse "Hits" in getMoreLoikeThis Methode is deprecated
		Hits hits;
		String mltQueryString = new String();
		logger.info("JobOfferId:"+ jobOfferId+ "--- jobOfferId String: "+jobOfferId.toString());
		
		try {
			query = parser.parse(jobOfferId.toString());
			hits = is.search(query);
			for (int i = 0; i < hits.length();i++){
				logger.info("ID: "+ i +"-- Hit ID:"+hits.id(i)+" --- Entity ID:"+hits.doc(i).get("id"));
			}
			if (hits.length()>0) {
				logger.info("Hits : "+hits.length());
				MoreLikeThis mlt = new MoreLikeThis(reader);
				mlt.setFieldNames(new String[] {"jobDescription", "taskDescription", "desiredProfession"});
				Integer parentDocumentId = hits.length()-1;
				Query mltQuery = mlt.like(hits.id(parentDocumentId));
				Hits mltHits = is.search(mltQuery);
				logger.info("Habe gesucht und "+mltHits.length()+" Resultate erhalten");
				Integer counter = 0;
				for (int i=0;i < mltHits.length();i++ ){
					// TODO nur nach lucene document id checken ... hibernate id muss nicht immer gleich bleiben
					if (!hits.doc(parentDocumentId).get("id").equals(mltHits.doc(i).get("id"))){
						if (counter>0){
							logger.info("Bin im "+ i +" make Querystring");
							mltQueryString = mltQueryString + " OR " + mltHits.doc(i).get("id"); 
						} else {
							logger.info("Bin im "+ i+" else");
							mltQueryString = mltHits.doc(i).get("id"); 
						}
						counter++;
					}
				}
				logger.info("Querystring lautet"+ mltQueryString);
			} else {
				logger.info("Hits gleich null");
				return mltJobOffers;
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		QueryParser mltParser = new QueryParser("id", new StandardAnalyzer() );
		Query luceneQuery;
		
		
		try {
			if (!mltQueryString.equals("")){
				luceneQuery = mltParser.parse(mltQueryString);
				FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, JobImpl.class );  
				mltJobOffers = fullTextQuery.list(); 
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
		    readerProvider.closeReader(reader);
		    try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mltJobOffers;
	}

	
	private String createDefaultJobOfferQueryString(String criteria, Country country, Territory territory){
				
		String escapedCriteria;
		if(criteria!=null){
			escapedCriteria = MultiFieldQueryParser.escape(criteria);
		} else escapedCriteria = null;
		//parser.setAllowLeadingWildcard(true);
		String fullQueryString = "";
		if(escapedCriteria != null && !escapedCriteria.trim().equals("")){
			escapedCriteria = escapedCriteria.trim();
			fullQueryString = "("+escapedCriteria+"~0.8 OR "+"*"+escapedCriteria+"*)";
		}
		
		if(country != null) {
			if(fullQueryString!=""){
				fullQueryString =	fullQueryString+" AND (countryOfEmployment.isoNumber:"+country.getIsoNumber()+")";
			} else fullQueryString = "countryOfEmployment.isoNumber:"+country.getIsoNumber()+"";
		} else if (territory != null) {
				if(fullQueryString!=""){
					fullQueryString  = fullQueryString+" AND (countryOfEmployment.parentTerritory.isoNumber:"+territory.getIsoNumber()+")";
				} else fullQueryString  = "countryOfEmployment.parentTerritory.isoNumber:"+territory.getIsoNumber()+"";
			}  
		
		if(fullQueryString!=""){
			fullQueryString = fullQueryString+" AND (onlineStatus:true AND jobOfferOwner.onlineStatus:true)";
		} else fullQueryString = "onlineStatus:true AND jobOfferOwner.onlineStatus:true";
		
		
		logger.error("fullQueryString: "+fullQueryString);
		
		return fullQueryString;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<JobImpl> getJobOfferByCriteria(String criteria, Country country, Territory territory) {
		
		
			FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());  
			MultiFieldQueryParser parser = new MultiFieldQueryParser( new String[]{"jobconcatsearchfield"}, new StandardAnalyzer());
			//,"organisationDescription", "jobDescription", "taskDescription", "organisationName" --- "jobconcatsearchfield"
			parser.setAllowLeadingWildcard(true);
//			String escapedCriteria;
//			if(criteria!=null){
//				escapedCriteria = parser.escape(criteria);
//			} else escapedCriteria = null;
//			
//			String fullQueryString = "";
//			
//			if(escapedCriteria != null && !escapedCriteria.trim().equals("")){
//				escapedCriteria = escapedCriteria.trim();
//				fullQueryString = "("+escapedCriteria+"~0.8 OR "+"*"+escapedCriteria+"*)";
//			}
//			
//			if(country != null) {
//				if(fullQueryString!=""){
//					fullQueryString =	fullQueryString+" AND (countryOfEmployment.isoNumber:"+country.getIsoNumber()+")";
//				} else fullQueryString = "countryOfEmployment.isoNumber:"+country.getIsoNumber()+"";
//			} else if (territory != null) {
//					if(fullQueryString!=""){
//						fullQueryString  = fullQueryString+" AND (countryOfEmployment.parentTerritory.isoNumber:"+territory.getIsoNumber()+")";
//					} else fullQueryString  = "countryOfEmployment.parentTerritory.isoNumber:"+territory.getIsoNumber()+"";
//				}  
//			
//			if(fullQueryString!=""){
//				fullQueryString = fullQueryString+" AND (onlineStatus:true AND jobOfferOwner.onlineStatus:true)";
//			} else fullQueryString = "onlineStatus:true AND jobOfferOwner.onlineStatus:true";
//			
//			
//			logger.error("fullQueryString: "+fullQueryString);
//			
			String fullQueryString = this.createDefaultJobOfferQueryString(criteria, country, territory);
			
			if(fullQueryString != ""){
				Query luceneQuery = null;
				try {
					
					luceneQuery = parser.parse(fullQueryString);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("Parsed Query: "+luceneQuery.toString());
				FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( luceneQuery, JobImpl.class );  
				org.apache.lucene.search.Sort sort = new Sort(new SortField("countryOfEmployment.shortEnglishName"));
				fullTextQuery.setSort(sort);
				List<JobImpl> jobOffers = fullTextQuery.list(); 
				if(jobOffers!=null){
					logger.info(jobOffers.size()+ " results found.");
				} else logger.info("Null results found.");
				return jobOffers;
			} else return null;
		
//		SearchFactory searchFactory = fullTextSession.getSearchFactory();
//		DirectoryProvider jobProvider = searchFactory.getDirectoryProviders(Job.class)[0];
//
//		IndexSearcher is = new IndexSearcher(jobProvider.getDirectory());
//		
//		ReaderProvider readerProvider = searchFactory.getReaderProvider();
//		IndexReader reader = readerProvider.openReader(jobProvider);
//		
//		
//		TermEnum te = reader.terms(new Term("organisationDescription", "Entw"));
//		while (te.next()) {
//			logger.info("termEnum: "+te.term().toString());
//		}
//		
//		Hits hits = is.search(luceneQuery);
//		ArrayList list = new ArrayList();
//		logger.info(hits.length() + " Treffer insgesamt ohne Schwellwert-Filterung gefunden!");
//		for (int i = 0; i < hits.length(); i++) {
//			 if (hits.score(i)>0.1){
//
//			 Document doc = hits.doc(i);
//			 list.add(criteria+"\t"+doc.get("id")+"\t"+hits.score(i)+"\t"+"MEDLINE");
//			 logger.info(i+"  "+list.get(i));
//			 }
//		}
//		
		

//		try {
//			WildcardTermEnum we = new WildcardTermEnum(reader, new Term("organisationDescription", "entwicklungszusammena*")); 
//			logger.info("Begin Terms:");
//			do { 									 
//				logger.info("Terms: "+we.term().text());
//				//td.seek(we); 
////				while (td.next()) { 
////					logger.info();
////					} 
//				} while (we.next());
//			logger.info("End Terms:");
//		}
//		finally {
//		    readerProvider.closeReader(reader);
//		}
//		
		
		//TermDocs td = ir.termDocs(); 
		//IndexReader reader=IndexReader.open(fsDir); 
	
//		RAMDirectory directory = new RAMDirectory();
//		IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(), true);
//		Document doc = new Document();
//		doc.add(new Field("field", "blub Cat Cot Cit Cet Cam Ecet Zum", Field.Store.NO, Field.Index.TOKENIZED));
//		writer.addDocument(doc);
//		writer.optimize();
//		writer.close(); 
//	
//
//        IndexReader reader2 = IndexReader.open(directory);
//
//        WildcardTermEnum enums = new WildcardTermEnum(reader2, new Term("field", "c*"));
//
//        do {
//            logger.info(enums.term().text());
//        } while ( enums.next() );
//
//        WildcardQuery wq = new WildcardQuery(new Term("field", "c?t"));
//
//        Query q = wq.rewrite(reader2);
//
//        logger.info("Queryout: "+q.toString());
//
//        reader2.close();

}	
	
	
	
	@SuppressWarnings("unchecked")
	public Integer getJobOfferResultAmountByCriteria(final String criteria,final Country country,final Territory territory) {
		
		FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());  
		MultiFieldQueryParser parser = new MultiFieldQueryParser( new String[]{"jobconcatsearchfield"}, new StandardAnalyzer());
		//,"organisationDescription", "jobDescription", "taskDescription", "organisationName" --- "jobconcatsearchfield"
		parser.setAllowLeadingWildcard(true);
		
		
		String fullQueryString = this.createDefaultJobOfferQueryString(criteria, country, territory);
		Integer resultSize = null;
		
		if(fullQueryString != ""){
			Query luceneQuery = null;
			try {
				
				luceneQuery = parser.parse(fullQueryString);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Parsed Query: "+luceneQuery.toString());
			FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, JobImpl.class );  
			resultSize = fullTextQuery.getResultSize();
			logger.info("Jobs Result Amount mit getResultSize: "+resultSize ); 
		}
		
		return resultSize;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public Collection<JobSearchResultDTO> getJobOfferByCriteria(final String criteria,final Country country,final Territory territory, Integer resultAmount, Integer pageIndexStart) {
		
		
			FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());  
			MultiFieldQueryParser parser = new MultiFieldQueryParser( new String[]{"jobconcatsearchfield"}, new StandardAnalyzer());
			//,"organisationDescription", "jobDescription", "taskDescription", "organisationName" --- "jobconcatsearchfield"
			parser.setAllowLeadingWildcard(true);
			
			
			String fullQueryString = this.createDefaultJobOfferQueryString(criteria, country, territory);
			
			
			if(fullQueryString != ""){
				Query luceneQuery = null;
				try {
					
					luceneQuery = parser.parse(fullQueryString);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("Parsed Query: "+luceneQuery.toString());
				FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, JobImpl.class );  
				if (resultAmount != null && resultAmount > 0 && pageIndexStart == null){
					fullTextQuery.setMaxResults(resultAmount);
					
					logger.info("Job Query max results is: "+resultAmount+" --- pageIndexStart: "+pageIndexStart);
				}
				
				fullTextQuery.setProjection(FullTextQuery.SCORE, "id", "countryOfEmploymentId", "organisationIndustrySectorId", "jobDescription");
				fullTextQuery.setResultTransformer(Transformers.aliasToBean(JobSearchResultDTO.class));
				org.apache.lucene.search.Sort sort = new Sort(new SortField("countryOfEmploymentId"));
				fullTextQuery.setSort(sort);
				//fullTextQuery.setSort(Sort.RELEVANCE);
				List<JobSearchResultDTO> jobOffers = fullTextQuery.list(); 
				


				final Integer resultSize = fullTextQuery.getResultSize();
				logger.info(resultSize+ " overall results found.");
				
				// BEGINN - Parallel Execution: Speicherung der Suchanfrageparameter
				
				Date date1 = new Date();
			 	logger.info("Parallel Execution: timestamp before execution "+date1.toString());
				Future<String> future = parallelExecutor.invoke(new Invokable<String>() {

					@Override
					public String invoke() {
						
						try {
							SearchHistoryJobs searchHistory = new SearchHistoryJobs();
							searchHistory.setQueryString(criteria);
							if(country !=null){
								logger.info("Parallel Execution: country name: "+ country.getShortEnglishName());
								searchHistory.setQueryCountry(countryDAO.findCountryByName(country.getShortEnglishName()));
								
							}
							if(territory!=null){
								logger.info("Parallel Execution: territory name: "+ territory.getNameEnglish());
								searchHistory.setQueryContinent(territoryDAO.findTerritoryByName(territory.getNameEnglish()));
							}
							searchHistory.setQueryResultAmount(resultSize);
							searchHistoryJobsDAO.doSave(searchHistory);
							
						} catch (Exception e) {
							e.printStackTrace();
							try {
								throw new ObjectNotSavedException();
							} catch (ObjectNotSavedException e1) {
								e1.printStackTrace();
							}
						}
						
						Date date = new Date();
						logger.info("Parallel Execution: timestamp "+date.toString());
						return null;
					} 
					
				});

				Date date2 = new Date();
				logger.info("Parallel Execution: timestamp after execution "+date2.toString()+" parallel task is completed: "+future.isDone());
				
				// ENDE - Parallel Execution: Speicherung der Suchanfrageparameter
				
				
				
				if(jobOffers!=null){
					//logger.info(jobOffers.size()+ " results found.");
				
					if(pageIndexStart != null){
						logger.info("Bin in PageIndexStart "+ pageIndexStart);
						if (resultAmount!= null && pageIndexStart>0){
							logger.info("Bin in resultAmount "+ resultAmount);
							List<JobSearchResultDTO> jobOffersPaged = new ArrayList();
							if(jobOffers.size()>=pageIndexStart+resultAmount-1){
								logger.info("Bin in results > pageIndexStart+resultAmount ");
								for (int i = pageIndexStart-1;i<pageIndexStart+resultAmount-1;i++ ){
									jobOffersPaged.add(jobOffers.get(i));
								}
								return jobOffersPaged;
							} else if(jobOffers.size()>=pageIndexStart){
								logger.info("Bin in results < pageIndexStart+resultAmount ");
								for (int i = pageIndexStart-1;i<jobOffers.size();i++ ){
									jobOffersPaged.add(jobOffers.get(i));
								}
								return jobOffersPaged;
							}
							return jobOffersPaged;
						}
					}
					
				} else logger.info("Null results found.");
				
				
				
				return jobOffers;
			} else return null;		

}	
	
	
	
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public Collection<JobImpl> getJobOfferByCriteriaGetjobs(String criteria, Country country, Territory territory) {
		
		
			FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());  
			MultiFieldQueryParser parser = new MultiFieldQueryParser( new String[]{"jobconcatsearchfield"}, new StandardAnalyzer());
			//,"organisationDescription", "jobDescription", "taskDescription", "organisationName" --- "jobconcatsearchfield"
			parser.setAllowLeadingWildcard(true);
			
//			String escapedCriteria;
//			if(criteria!=null){
//				escapedCriteria = parser.escape(criteria);
//			} else escapedCriteria = null;
//			parser.setAllowLeadingWildcard(true);
//			String fullQueryString = "";
//			
//			if(escapedCriteria != null && !escapedCriteria.trim().equals("")){
//				escapedCriteria = escapedCriteria.trim();
//				fullQueryString = "("+escapedCriteria+"~0.8 OR "+"*"+escapedCriteria+"*)";
//			}
//			
//			if(country != null) {
//				if(fullQueryString!=""){
//					fullQueryString =	fullQueryString+" AND (countryOfEmployment.isoNumber:"+country.getIsoNumber()+")";
//				} else fullQueryString = "countryOfEmployment.isoNumber:"+country.getIsoNumber()+"";
//			} else if (territory != null) {
//					if(fullQueryString!=""){
//						fullQueryString  = fullQueryString+" AND (countryOfEmployment.parentTerritory.isoNumber:"+territory.getIsoNumber()+")";
//					} else fullQueryString  = "countryOfEmployment.parentTerritory.isoNumber:"+territory.getIsoNumber()+"";
//				}  
//			
//			if(fullQueryString!=""){
//				fullQueryString = fullQueryString+" AND (onlineStatus:true AND jobOfferOwner.onlineStatus:true)";
//			} else fullQueryString = "onlineStatus:true AND jobOfferOwner.onlineStatus:true";
			
			String fullQueryString = this.createDefaultJobOfferQueryString(criteria, country, territory);
			
			
			//TODO Workaround zum exkludieren von ZAV Stellen aus der getjobs.net Stellensuche
			String zavExclude = "Dies ist ein Stellenangebot aus dem entwicklungspolitischen Förderprogramm Rückkehrende Fachkräfte.";
			String escapedZavExclude = parser.escape(zavExclude);
			
			if(fullQueryString!=""){
				fullQueryString = fullQueryString+" AND -(miscellaneousServices:\""+escapedZavExclude+"\")";
			} else fullQueryString = "-(miscellaneousServices:\""+escapedZavExclude+"\")";
			
			
			logger.error("fullQueryString: "+fullQueryString);
			
			
			
			if(fullQueryString != ""){
				Query luceneQuery = null;
				try {
					
					luceneQuery = parser.parse(fullQueryString);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("Parsed Query: "+luceneQuery.toString());
				FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( luceneQuery, JobImpl.class );  
				org.apache.lucene.search.Sort sort = new Sort(new SortField("countryOfEmployment.shortEnglishName"));
				fullTextQuery.setSort(sort);
				List<JobImpl> jobOffers = fullTextQuery.list(); 
				if(jobOffers!=null){
					logger.info(jobOffers.size()+ " results found.");
				} else logger.info("Null results found.");
				return jobOffers;
			} else return null;
	
	
	
	}



	public int getOnlineJobOffersByUser(Long userId){
		Collection<JobImpl> onlineJobs = jobDAO.findJobOffersByUserAndOnlineState(userId, true);
		if(onlineJobs!=null){
			return onlineJobs.size();
		}
		return 0;
	}
	
//	public Collection<JobImpl> getJobOffersByLoginUser(LoginUser loginUser){
//		
//	}
	

	public JobImpl getJobOfferDetails(final Long jobOfferId) throws JobOfferNotFoundException{		
			JobImpl job = null;
			job = jobDAO.doRetrieve(jobOfferId, true);	
			
			if(job != null){
				
				// BEGINN - Parallel Execution: Speicherung der Anfrageparameter
				
				Date date1 = new Date();
			 	logger.info("Parallel Execution: timestamp before execution "+date1.toString());
				Future<String> future = parallelExecutor.invoke(new Invokable<String>() {

					@Override
					public String invoke() {
						
						try {
							AccessHistoryJobs accessHistory = new AccessHistoryJobs();
							accessHistory.setAccessedJobProfile(jobDAO.doRetrieve(jobOfferId, true));
							
							accessHistoryJobsDAO.doSave(accessHistory);
							
						} catch (Exception e) {
							e.printStackTrace();
							try {
								throw new ObjectNotSavedException();
							} catch (ObjectNotSavedException e1) {
								e1.printStackTrace();
							}
						}
						
						Date date = new Date();
						logger.info("Parallel Execution: timestamp "+date.toString());
						return null;
					} 
					
				});

				Date date2 = new Date();
				logger.info("Parallel Execution: timestamp after execution "+date2.toString()+" parallel task is completed: "+future.isDone());
				
				// ENDE - Parallel Execution: Speicherung der Suchanfrageparameter

				
				
				return job;
			} else throw new JobOfferNotFoundException();
	}
	
	// TODO Implement apply to Job
	public void applyToJobOffer(Long jobOfferId, Long userGuid, String contactNote) throws JobOfferNotFoundException, APDUserNotFoundException, ObjectNotSavedException{
			JobImpl job;
			try {
				job = jobDAO.doRetrieve(jobOfferId, true);
			} catch (Exception e) {
				e.printStackTrace();
				throw new JobOfferNotFoundException();
			}
			User user;
			try {
				user = userDAO.findAPDUserByID(userGuid);
			} catch (Exception e) {
				e.printStackTrace();
				throw new APDUserNotFoundException();
			}
			JobApplication jobApp = new JobApplication(job,user,contactNote);
			try {
				jobApplicationDAO.doSave(jobApp);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException();
			}
	}
	
	
	public Collection<JobApplication> getReceivedJobOfferApplications(Long apdUserId) throws APDUserNotFoundException{
		List<JobApplication> jobApplications = new ArrayList<JobApplication>();
		Collection<JobImpl> jobOffers = getJobOffersByAPDUser(apdUserId);
		Iterator<JobImpl> it = jobOffers.iterator();
		while(it.hasNext()){
			JobImpl job = it.next();
			jobApplications.addAll(job.getJobOfferApplications());
		}
		return jobApplications;
	}
	
	public void modifyJobOffer(JobImpl jobOffer) throws JobOfferNotFoundException, PassedAttributeIsNullException{
		if (jobOffer != null) {
			try {
				jobDAO.doSave(jobOffer);
			} catch (Exception e) {
				throw new JobOfferNotFoundException();
			}
		} else throw new PassedAttributeIsNullException();
	}
	
	public void deleteJobOffer(Long jobOfferId) throws JobOfferNotFoundException, PassedAttributeIsNullException{
			logger.info("Job Worker Delete JobOfferId :"+jobOfferId);
			if (jobOfferId != null) {
				try {
					jobDAO.doDelete(jobOfferId);
				} catch (Exception e) {
					throw new JobOfferNotFoundException();
				}
			}else throw new PassedAttributeIsNullException();
		
	}
	
	public void deleteJobOfferByCobraId(Long cobraJobId) throws JobOfferNotFoundException, PassedAttributeIsNullException{
		logger.info("Job Worker Delete JobOfferId :"+cobraJobId);
		if (cobraJobId !=null) {
			try {
				jobDAO.doDeleteByCobraId(cobraJobId);
			} catch (Exception e) {
				throw new JobOfferNotFoundException();
			}
		} else throw new PassedAttributeIsNullException();
	}
		
	public Currency getCurrencyByNameOrIsoNumber(String currencyValue){
		if(currencyValue!=null){
			//if currency is provided by iso number
			if(org.apache.commons.lang.StringUtils.isNumeric(currencyValue)){
				return currencyDAO.findCurrencyByIsoNumber(new Long(currencyValue));
			} else // else if country is provided by name 
				{
					return currencyDAO.findCurrencyByName(currencyValue);
			}
		}
		return null;	
	}

	
	// TODO Implement Sector ID Search
	public Collection<JobImpl> getJobOfferBySector(Long[] sectorIds){
		return null;
	}
	
	public Address getJobOwnerAddress(Long jobOfferId) throws JobOfferNotFoundException{		
		JobImpl jobOffer =  jobDAO.doRetrieve(jobOfferId, true);
		if(jobOffer!= null){
			return jobOffer.getCurrentContactAddress();			
		}else throw new JobOfferNotFoundException();
	}

	public Collection<JobImpl> getOutdatedJobOffers(){
		Collection<JobImpl> outdatedJobs = jobDAO.findOutdatedJobOffers();
		return outdatedJobs;
	}
	
	public Collection<JobImpl> getUpdatedJobOffers(){
		Collection<JobImpl> updatedJobs = jobDAO.findUpdatedJobOffers();
		return updatedJobs;
	}

	@Override
	public Collection<JobImpl> getJobOffersByAPDUserAndCriteria(Long apdUserId, JobActiveEnum jobActive, Country country, Territory territory, int numberOfResults, int indexStart) throws APDUserNotFoundException {
		User user = userDAO.findAPDUserByID(apdUserId);
		if(user!=null){
			return jobDAO.findJobOffersByUserAndCriteria(user.getId(), jobActive, country, territory, numberOfResults, indexStart);
		} else throw new APDUserNotFoundException(apdUserId.toString());
	}
}
