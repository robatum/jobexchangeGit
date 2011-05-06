/**
 * 
 */
package net.agef.jobexchange.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import net.agef.jobexchange.domain.AccessHistoryApplicant;
import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.domain.AvailabilityEnum;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.OccupationalField;
import net.agef.jobexchange.domain.SearchHistoryApplicant;
import net.agef.jobexchange.domain.Territory;
import net.agef.jobexchange.domain.User;
import net.agef.jobexchange.domain.WorkUserType;
import net.agef.jobexchange.exceptions.APDUserNotFoundException;
import net.agef.jobexchange.exceptions.ApplicantProfileAlreadyExistException;
import net.agef.jobexchange.exceptions.ApplicantProfileNotFoundException;
import net.agef.jobexchange.exceptions.CantChangeOnlineStateException;
import net.agef.jobexchange.exceptions.CobraUserNotFoundException;
import net.agef.jobexchange.exceptions.EnumValueNotFoundException;
import net.agef.jobexchange.exceptions.InwentUserNotFoundException;
import net.agef.jobexchange.exceptions.ObjectNotSavedException;
import net.agef.jobexchange.exceptions.PassedAttributeIsNullException;
import net.agef.jobexchange.exceptions.UserNotFoundException;
import net.agef.jobexchange.integration.AccessHistoryApplicantDAO;
import net.agef.jobexchange.integration.ApplicantDAO;
import net.agef.jobexchange.integration.CountryDAO;
import net.agef.jobexchange.integration.SearchHistoryApplicantDAO;
import net.agef.jobexchange.integration.TerritoryDAO;
import net.agef.jobexchange.integration.UserDAO;
import net.agef.jobexchange.webservice.adapter.OccupationalFieldAssembler;
import net.agef.jobexchange.webservice.entities.ApplicantsSearchResultDTO;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.Invokable;
import org.apache.tapestry5.ioc.services.ParallelExecutor;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;

/**
 * @author Administrator
 * 
 */
public class ApplicantWorkerHandler implements ApplicantWorker {

	private Logger logger;
	private HibernateSessionManager session;
	private ApplicantDAO applicantDAO;
	private UserDAO userDAO;
	private OccupationalFieldAssembler occupationalFieldAssembler;
	private ParallelExecutor parallelExecutor;
	private SearchHistoryApplicantDAO searchHistoryApplicantDAO;
	private CountryDAO countryDAO;
	private TerritoryDAO territoryDAO;
	private AccessHistoryApplicantDAO accessHistoryApplicantDAO;

	public ApplicantWorkerHandler(AccessHistoryApplicantDAO accessHistoryApplicantDAO, SearchHistoryApplicantDAO searchHistoryApplicantDAO, CountryDAO countryDAO, TerritoryDAO territoryDAO, ParallelExecutor parallelExecutor,ApplicantDAO applicantDAO, UserDAO userDAO, HibernateSessionManager session, OccupationalFieldAssembler occupationalFieldAssembler, Logger logger) {
		this.logger = logger;
		this.applicantDAO = applicantDAO;
		this.userDAO = userDAO;
		this.session = session;
		this.occupationalFieldAssembler = occupationalFieldAssembler;
		this.parallelExecutor = parallelExecutor;
		this.searchHistoryApplicantDAO = searchHistoryApplicantDAO;
		this.countryDAO = countryDAO;
		this.territoryDAO = territoryDAO;
		this.accessHistoryApplicantDAO = accessHistoryApplicantDAO;
	}

	@Override
	public Collection<Applicant> getAllApplicants() {
		return applicantDAO.findAll();
	}

	@Override
	public Long addApplicantData(Applicant applicantData) throws ApplicantProfileAlreadyExistException {
		if (applicantData != null && applicantData.getApplicantProfileOwner().getApplicantProfile() == null) {
			try {
				Applicant applicantSaved = applicantDAO.doSave(applicantData);
				return (applicantSaved.getId() + 23);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicantProfileAlreadyExistException();
			}
		} else
			throw new ApplicantProfileAlreadyExistException();
	}
	
	
	@Override
	public Boolean getApplicantProfileOnlineState(Long applicantProfileId) throws ApplicantProfileNotFoundException{
		logger.info("Bestimme Online Status für Profil mit ID: " + applicantProfileId);
		Applicant applicant = applicantDAO.doRetrieve(applicantProfileId, false); 
		if(applicant != null){ 
			return applicant.getOnlineStatus();
		} else throw new ApplicantProfileNotFoundException();
	}
	
	

	@Override
	public Applicant getApplicantDataByAPDUserId(Long apdUserId) throws APDUserNotFoundException, ApplicantProfileNotFoundException {
		User user = userDAO.findAPDUserByID(apdUserId);
		if (user != null) {
			if (user.getApplicantProfile() != null) {
				return user.getApplicantProfile();
			} else
				throw new ApplicantProfileNotFoundException();
		} else
			throw new APDUserNotFoundException(apdUserId.toString());
	}

	@Override
	public Applicant getApplicantDataByProfileId(final Long applicantProfileId) throws ApplicantProfileNotFoundException {
		Applicant applicant = applicantDAO.findApplicantDataByProfileId(applicantProfileId);
		if (applicant != null) {
			
//			// BEGINN - Parallel Execution: Speicherung der Anfrageparameter
//			System.out.println("ApplicantWorker: ParallelExecution in getApplicantDataByProfileId mit Id = "+applicantProfileId);
//			Date date1 = new Date();
//		 	logger.info("Parallel Execution: timestamp before execution "+date1.toString());
//			Future<String> future = parallelExecutor.invoke(new Invokable<String>() {
//
//				@Override
//				public String invoke() {
//					
//					try {
//						AccessHistoryApplicant accessHistory = new AccessHistoryApplicant();
//						accessHistory.setAccessedApplicantProfile(applicantDAO.findApplicantDataByProfileId(applicantProfileId));
//						
//						accessHistoryApplicantDAO.doSave(accessHistory);
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//						try {
//							throw new ObjectNotSavedException();
//						} catch (ObjectNotSavedException e1) {
//							e1.printStackTrace();
//						}
//					}
//					
//					Date date = new Date();
//					logger.info("Parallel Execution: timestamp "+date.toString());
//					return null;
//				} 
//				
//			});
//
//			Date date2 = new Date();
//			logger.info("Parallel Execution: timestamp after execution "+date2.toString()+" parallel task is completed: "+future.isDone());
//			
//			// ENDE - Parallel Execution: Speicherung der Suchanfrageparameter
			
			
			
			
			return applicant;
		} else
			throw new ApplicantProfileNotFoundException(applicantProfileId.toString());
	}

	@Override
	public Applicant getApplicantDataByUserId(Long userId) throws ApplicantProfileNotFoundException, UserNotFoundException {
		User user = userDAO.doRetrieve(userId, true);
		if (user != null) {
			if (user.getApplicantProfile() != null) {
				return user.getApplicantProfile();
			} else
				throw new ApplicantProfileNotFoundException();
		} else
			throw new UserNotFoundException();
	}

	@Override
	public void modifyApplicantData(Applicant applicant) throws ApplicantProfileNotFoundException, PassedAttributeIsNullException {
		if (applicant != null) {
			try {
				applicantDAO.doSave(applicant);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicantProfileNotFoundException();
			}
		} else
			throw new PassedAttributeIsNullException();
	}

	@Override
	public void deleteApplicantDataByCobraId(Long cobraSuperId) throws CobraUserNotFoundException, ApplicantProfileNotFoundException {
		User user = userDAO.findCobraUserByID(cobraSuperId, false);
		if (user != null) {
			try {
				applicantDAO.doDelete(user.getApplicantProfile());
			} catch (Exception e) {
				throw new ApplicantProfileNotFoundException();
			}
		} else
			throw new CobraUserNotFoundException();

	}

	@Override
	public void deleteApplicantDataByInwentUserId(Long inwentUserId) throws InwentUserNotFoundException, ApplicantProfileNotFoundException {
		User user = userDAO.findInwentUserByID(inwentUserId);
		if (user != null) {
			try {
				applicantDAO.doDelete(user.getApplicantProfile());
			} catch (Exception e) {
				throw new ApplicantProfileNotFoundException();
			}
		} else
			throw new InwentUserNotFoundException();

	}

	@Override
	public void deleteApplicantData(Long userId) throws APDUserNotFoundException, ApplicantProfileNotFoundException {
		User user = userDAO.findAPDUserByID(userId);
		if (user != null) {
			try {
				applicantDAO.doDelete(user.getApplicantProfile());
			} catch (Exception e) {
				throw new ApplicantProfileNotFoundException();
			}
		} else
			throw new APDUserNotFoundException(userId.toString());
	}

	@Override
	public void setApplicantProfileOnlineStatus(Applicant applicant, Boolean onlineStatus) throws ObjectNotSavedException, CantChangeOnlineStateException {
		if (applicant != null && onlineStatus != null && applicant.getOnlineStatus() != onlineStatus) {
			applicant.setOnlineStatus(onlineStatus);
			try {
				System.out.println(applicant.getWorkUserTypes().size());
				applicantDAO.doSave(applicant);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ObjectNotSavedException();
			}
		} else
			throw new CantChangeOnlineStateException();
	}

	private String createDefaultApplicantQueryString(String criteria, Country country, Territory territory, Boolean filterGetjobsResults) {

		String escapedCriteria;
		if (criteria != null) {
			escapedCriteria = MultiFieldQueryParser.escape(criteria);
		} else
			escapedCriteria = null;
		// parser.setAllowLeadingWildcard(true);
		String fullQueryString = "";

		if (escapedCriteria != null && !escapedCriteria.trim().equals("")) {
			escapedCriteria = escapedCriteria.trim();
			fullQueryString = escapedCriteria + "~0.8 OR " + "*" + escapedCriteria + "*";
		}
		if (country != null) {
			if (fullQueryString != "") {
				fullQueryString = fullQueryString + " AND (applicantProfileOwner.address1.country.isoNumber:" + country.getIsoNumber() + ")";
			} else
				fullQueryString = "applicantProfileOwner.address1.country.isoNumber:" + country.getIsoNumber() + "";
		} else if (territory != null) {
			if (fullQueryString != "") {
				fullQueryString = fullQueryString + " AND (applicantProfileOwner.address1.country.parentTerritory.isoNumber:" + territory.getIsoNumber() + ")";
			} else
				fullQueryString = "applicantProfileOwner.address1.country.parentTerritory.isoNumber:" + territory.getIsoNumber() + "";
		}

		// if(fullQueryString!=""){
		// fullQueryString = fullQueryString+" AND (onlineStatus:true)";
		// } else fullQueryString = "onlineStatus:true";
		//

		if (fullQueryString != "") {
			fullQueryString = fullQueryString + " AND (onlineStatus:true AND applicantProfileOwner.onlineStatus:true)";
		} else
			fullQueryString = "onlineStatus:true AND applicantProfileOwner.onlineStatus:true";

		if (filterGetjobsResults != null && filterGetjobsResults) {
			if (fullQueryString != "") {
				fullQueryString = fullQueryString + " AND (applicantProfileOwner.dataProvider.providerName:AGEF)";
			} else
				fullQueryString = "applicantProfileOwner.dataProvider.providerName:AGEF";
		}

		logger.info("fullQueryString: " + fullQueryString);
		return fullQueryString;
	}

	// TODO Die Sortierung und Suche nach Laendern, sollte so angepasst werden,
	// das sie jeweils auf die aktuelle Addresse zugreift
	@SuppressWarnings("unchecked")
	@Deprecated
	public Collection<Applicant> getApplicantByCriteria(String criteria, Country country, Territory territory) {

		FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());
		MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "applicantconcatsearchfield" }, new StandardAnalyzer());

		// String escapedCriteria;
		// if(criteria!=null){
		// escapedCriteria = parser.escape(criteria);
		// } else escapedCriteria = null;
		parser.setAllowLeadingWildcard(true);
		// String fullQueryString = "";
		//
		// if(escapedCriteria != null && !escapedCriteria.trim().equals("")){
		// escapedCriteria = escapedCriteria.trim();
		// fullQueryString = escapedCriteria+"~0.8 OR "+"*"+escapedCriteria+"*";
		// }
		// if(country != null) {
		// if(fullQueryString!=""){
		// fullQueryString =
		// fullQueryString+" AND (applicantProfileOwner.address1.country.isoNumber:"+country.getIsoNumber()+")";
		// } else fullQueryString =
		// "applicantProfileOwner.address1.country.isoNumber:"+country.getIsoNumber()+"";
		// } else if (territory != null) {
		// if(fullQueryString!=""){
		// fullQueryString =
		// fullQueryString+" AND (applicantProfileOwner.address1.country.parentTerritory.isoNumber:"+territory.getIsoNumber()+")";
		// } else fullQueryString =
		// "applicantProfileOwner.address1.country.parentTerritory.isoNumber:"+territory.getIsoNumber()+"";
		// }
		//
		// // if(fullQueryString!=""){
		// // fullQueryString = fullQueryString+" AND (onlineStatus:true)";
		// // } else fullQueryString = "onlineStatus:true";
		// //
		//
		// if(fullQueryString!=""){
		// fullQueryString =
		// fullQueryString+" AND (onlineStatus:true AND applicantProfileOwner.onlineStatus:true)";
		// } else fullQueryString =
		// "onlineStatus:true AND applicantProfileOwner.onlineStatus:true";
		//
		//
		// logger.error("fullQueryString: "+fullQueryString);

		String fullQueryString = this.createDefaultApplicantQueryString(criteria, country, territory, false);

		if (fullQueryString != "") {
			Query luceneQuery = null;
			try {

				luceneQuery = parser.parse(fullQueryString);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Parsed Query: " + luceneQuery.toString());
			FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Applicant.class);
			// org.apache.lucene.search.Sort sort = new Sort(new
			// SortField("applicantProfileOwner.address1.country.shortEnglishName"));
			// fullTextQuery.setSort(sort);
			List<Applicant> applicants = fullTextQuery.list();
			return applicants;
		} else
			return null;

	}

	// TODO Die Sortierung und Suche nach Laendern, sollte so angepasst werden,
	// das sie jeweils auf die aktuelle Addresse zugreift
	@SuppressWarnings("unchecked")
	public Collection<ApplicantsSearchResultDTO> getApplicantByCriteria(String criteria, Country country, Territory territory, Integer resultAmount, Integer pageIndexStart,
			Boolean filterGetjobsResults) {
		logger.info("getApplicantByCriteria() Begin");

		FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());
		MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "applicantconcatsearchfield" }, new StandardAnalyzer());

		parser.setAllowLeadingWildcard(true);

		String fullQueryString = this.createDefaultApplicantQueryString(criteria, country, territory, filterGetjobsResults);

		if (fullQueryString != "") {
			Query luceneQuery = null;
			try {

				luceneQuery = parser.parse(fullQueryString);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Parsed Query: " + luceneQuery.toString());
			FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Applicant.class);
			if (resultAmount != null && resultAmount > 0 && pageIndexStart == null) {
				fullTextQuery.setMaxResults(resultAmount);

				logger.info("Applicant Query max results is: " + resultAmount + " --- pageIndexStart: " + pageIndexStart);
			}

			// org.apache.lucene.search.Sort sort = new Sort(new
			// SortField("applicantProfileOwner.address1.country.shortEnglishName"));
			// fullTextQuery.setSort(sort);
			// List<Applicant> applicants = fullTextQuery.list();

			// FullTextQuery.SCORE,
			fullTextQuery.setProjection("id", "fieldOfHighestDegree", "combinedWorkExperiences", "currentCountryOfResidence", "durationOfContract", "languageSkillsGerman", "languageSkillsEnglish");
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(ApplicantsSearchResultDTO.class));
			// org.apache.lucene.search.Sort sort = new Sort(new
			// SortField("id"));
			// fullTextQuery.setSort(sort);
			if(criteria == null || criteria.trim().equals("")){
				fullTextQuery.setSort(new Sort("id", true));
				logger.info("Sortiere nach id DESC");
			} else {
				fullTextQuery.setSort(Sort.RELEVANCE);
				logger.info("Sortiere nach Relevanz");
			}
			List<ApplicantsSearchResultDTO> applicants = fullTextQuery.list();

			Integer resultSize = fullTextQuery.getResultSize();
			logger.info(resultSize + " overall results found.");

			if (applicants != null) {
				// logger.info(jobOffers.size()+ " results found.");

				if (pageIndexStart != null) {
					logger.info("Bin in PageIndexStart " + pageIndexStart);
					if (resultAmount != null && pageIndexStart > 0) {
						logger.info("Bin in resultAmount " + resultAmount);
						List<ApplicantsSearchResultDTO> applicantsPaged = new ArrayList<ApplicantsSearchResultDTO>();
						if (applicants.size() >= pageIndexStart + resultAmount - 1) {
							logger.info("Bin in results > pageIndexStart+resultAmount ");
							for (int i = pageIndexStart - 1; i < pageIndexStart + resultAmount - 1; i++) {
								applicantsPaged.add(applicants.get(i));
							}
							return applicantsPaged;
						} else if (applicants.size() >= pageIndexStart) {
							logger.info("Bin in results < pageIndexStart+resultAmount ");
							for (int i = pageIndexStart - 1; i < applicants.size(); i++) {
								applicantsPaged.add(applicants.get(i));
							}
							return applicantsPaged;
						}
						return applicantsPaged;
					}
				}

			} else
				{
					logger.info("Null results found.");
				}
			
			logger.info("getApplicantByCriteria(): End");
			return applicants;
		} else
			{
				logger.info("getApplicantByCriteria() End");
				return null;
			}

	}

	@SuppressWarnings("unchecked")
	public Collection<Applicant> getApplicantByExtendedCriteria(final String criteria,final Country country,final Territory territory, String[] availability, WorkUserType[] workUserType, String[] occupationalField,
			String managementExperience, Integer resultAmount, Integer pageIndexStart) throws EnumValueNotFoundException {
		logger.info("getApplicantByExtendedCriteria() Begin");
		
		List<ApplicantsSearchResultDTO> applicants = new ArrayList<ApplicantsSearchResultDTO>();
		
		applicants = prepareAndExecuteFulltextQueryForExtendedCriteria(criteria, country, territory);

		List<Long> finalResultIds = new ArrayList<Long>();
		Integer finalLuceneResultAmount = null;
		if (applicants != null && applicants.size() > 0) {
			finalLuceneResultAmount = applicants.size();
			Criteria criteria1 = prepareCriteriaForExtendedSearch(availability, workUserType, occupationalField, managementExperience, resultAmount, pageIndexStart, applicants);
			if(criteria == null || criteria.trim().equals("")){
				criteria1.addOrder(Order.desc("id"));
			}
			try {
				finalResultIds = criteria1.list();
				
			} catch (Exception e) {
				logger.info("ApplicantWorkHandler: Hibernate exception occured during extended search.");
				e.printStackTrace();
			} 
		} else {
			logger.info("Null results found.");
		}
		logger.info("FinalResultIds: " + finalResultIds.size());

		List<Applicant> finalResult = new ArrayList<Applicant>();
		for (Long id : finalResultIds) {
			// id ist tatsaechlicher primary key aus der DB und nicht applicantProfileId (id + 23)
			logger.info("hole Profil mit ID: " + id);
			Applicant applicant = applicantDAO.doRetrieveByDatabaseUid(id, false); 
			if(applicant != null){ 
				finalResult.add(applicant); 
			}
		}
		
		final Integer resultSize = finalLuceneResultAmount;
		
		// BEGINN - Parallel Execution: Speicherung der Suchanfrageparameter
		
		Date date1 = new Date();
	 	logger.info("Parallel Execution: timestamp before execution "+date1.toString());
		Future<String> future = parallelExecutor.invoke(new Invokable<String>() {

			@Override
			public String invoke() {
				
				try {
					SearchHistoryApplicant searchHistory = new SearchHistoryApplicant();
					searchHistory.setQueryString(criteria);
					if(country !=null){
						logger.info("Parallel Execution: country name: "+ country.getShortEnglishName());
						searchHistory.setQueryCountry(countryDAO.findCountryByName(country.getShortEnglishName()));
						
					}
					if(territory!=null){
						logger.info("Parallel Execution: territory name: "+ territory.getNameEnglish());
						searchHistory.setQueryContinent(territoryDAO.findTerritoryByName(territory.getNameEnglish()));
					}
					if(resultSize !=null){
						searchHistory.setQueryResultAmount(resultSize);
					}
					
					searchHistoryApplicantDAO.doSave(searchHistory);
					
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
		logger.info("getApplicantByExtendedCriteria() Ende");
		return finalResult;

	}
	
	@Override
	public int getApplicantsSearchResultsAmountByExtendedCriteria(String criteria, Country country, Territory territory, String[] availability, WorkUserType[] workTypes,
			String[] occupationalField, String managementExperience) {
List<ApplicantsSearchResultDTO> applicants = new ArrayList<ApplicantsSearchResultDTO>();
		
		applicants = prepareAndExecuteFulltextQueryForExtendedCriteria(criteria, country, territory);

		int numberResults = 0;
		if (applicants != null && applicants.size() > 0) {
			try {
				Criteria criteria1 = prepareCriteriaForExtendedSearch(availability, workTypes, occupationalField, managementExperience, null, null, applicants);
				criteria1.setProjection(Projections.countDistinct("id"));
				numberResults = (Integer) criteria1.uniqueResult();
			} catch (Exception e) {
				logger.info("ApplicantWorkHandler: Hibernate exception occured during extended search.");
				e.printStackTrace();
			}
		} else {
			logger.info("Null results found.");
		}
		return numberResults;
	}

	private Criteria prepareCriteriaForExtendedSearch(String[] availability, WorkUserType[] workUserType, String[] occupationalField, String managementExperience, Integer resultAmount,
			Integer pageIndexStart, List<ApplicantsSearchResultDTO> applicants) throws EnumValueNotFoundException {
		List<Long> idList = new ArrayList<Long>();
		for (ApplicantsSearchResultDTO value : applicants) {
			idList.add((Long) value.getId());
		}
		// QUERY ERSTELLEN: Suche nach weiteren Kriterien in einer DB Query
		// unter Beruecksichtigung der bereits erhaltenen ids
		Criteria criteria1 = this.session.getSession().createCriteria(Applicant.class);
		// criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria1.setProjection(Projections.distinct(Projections.property("id")));

		if (availability != null && availability.length > 0) {
			Disjunction disjunctionAvailability = Restrictions.disjunction();
			for (String availabilityString : availability) {
				disjunctionAvailability.add(Restrictions.eq("availability", AvailabilityEnum.fromValue(availabilityString)));
			}
			criteria1.add(disjunctionAvailability);
		}

		if (managementExperience != null && managementExperience.trim().length() > 0) {
			/* nur wenn die Workexperience bei der Suche benoetigt wird, sollte der Alias erstellt werden, da ansonsten Profile ohne Workexpierence ignoriert werden. */
			criteria1.createAlias("workExperience", "workExperience"); 
			criteria1.add(Restrictions.eq("workExperience.managementExperience", DecisionYesNoEnum.fromValue(managementExperience)));
		}

		if (occupationalField != null && occupationalField.length > 0) {
			Disjunction disjunctionOccupationalField = Restrictions.disjunction();
			for (String occupation : occupationalField) {
				if (occupation != null && occupation.trim().length() > 0) {
					OccupationalField occuField = occupationalFieldAssembler.getDomainObj(occupation);
					if (occuField != null) {
						logger.info(occuField.getFieldNameEnglish());
						disjunctionOccupationalField.add(Restrictions.eq("workExperience.occupationalField", occuField));
					}
				}
			}
			criteria1.add(disjunctionOccupationalField);
		}

		criteria1.add(Restrictions.in("id", idList));
		if (workUserType != null && workUserType.length > 0) {
			Disjunction disjunction = Restrictions.disjunction();
			criteria1.createAlias("workUserTypes", "workUserTypes");
			for (WorkUserType workType : workUserType) {
				if (workType != null) {
					disjunction.add(Restrictions.eq("workUserTypes.workType", workType.getWorkType()));
				}
			}
			criteria1.add(disjunction);
		}
		if (pageIndexStart != null)
			criteria1.setFirstResult(pageIndexStart);
		logger.info("firstResult: " + pageIndexStart);
		if (resultAmount != null)
			criteria1.setMaxResults(resultAmount);
		logger.info("maxResults: " + resultAmount);
		//criteria1.setResultTransformer(Transformers.aliasToBean(ApplicantsSearchResultDTO.class));
		return criteria1;
	}

	@SuppressWarnings("unchecked")
	private List<ApplicantsSearchResultDTO> prepareAndExecuteFulltextQueryForExtendedCriteria(String criteria, Country country, Territory territory) {
		FullTextSession fullTextSession = Search.getFullTextSession(session.getSession());
		MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "applicantconcatsearchfield" }, new StandardAnalyzer());

		parser.setAllowLeadingWildcard(true);

		String fullQueryString = this.createDefaultApplicantQueryString(criteria, country, territory, false);
		// availability, workUserType, workExperience
		if (fullQueryString != "") {
			Query luceneQuery = null;
			try {
				luceneQuery = parser.parse(fullQueryString);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Parsed Query: " + luceneQuery.toString());
			FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Applicant.class);

			fullTextQuery.setProjection("id");
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(ApplicantsSearchResultDTO.class));
			fullTextQuery.setSort(Sort.RELEVANCE);
			return (List<ApplicantsSearchResultDTO>)fullTextQuery.list();
		}
		return new ArrayList<ApplicantsSearchResultDTO>();
	}


}
