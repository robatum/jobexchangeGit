package net.agef.jobexchange.services.internal;

import java.util.Date;
import java.util.List;

import net.agef.jobexchange.domain.Address;
import net.agef.jobexchange.domain.AlumniRole;
import net.agef.jobexchange.domain.Applicant;
import net.agef.jobexchange.domain.ContactPerson;
import net.agef.jobexchange.domain.ContractDurationEnum;
import net.agef.jobexchange.domain.Country;
import net.agef.jobexchange.domain.DataProvider;
import net.agef.jobexchange.domain.DecisionYesNoEnum;
import net.agef.jobexchange.domain.DegreeEnum;
import net.agef.jobexchange.domain.ExperienceDurationEnum;
import net.agef.jobexchange.domain.IndustrySector;
import net.agef.jobexchange.domain.JobImpl;
import net.agef.jobexchange.domain.LanguageSkillsEnum;
import net.agef.jobexchange.domain.LoginUser;
import net.agef.jobexchange.domain.LoginUserRole;
import net.agef.jobexchange.domain.OrganisationRole;
import net.agef.jobexchange.domain.TitleEnum;
import net.agef.jobexchange.domain.User;
import nu.localhost.tapestry5.springsecurity.services.SaltSourceService;
//import nu.localhost.tapestry.acegi.services.SaltSourceService;
//
//import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.services.ApplicationInitializer;
import org.apache.tapestry5.services.ApplicationInitializerFilter;
import org.apache.tapestry5.services.Context;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.security.providers.encoding.PasswordEncoder;

public class JobexchangeInitializerImpl implements ApplicationInitializerFilter {
	private Session session;
	private HibernateSessionManager hbm;
	private PasswordEncoder passwordEncoder;
	private SaltSourceService saltSource;

	private String[] dataProviderAPDIP = { "84.200.230.34", "84.200.230.35", "84.200.230.36", "84.201.42.193", "84.201.42.194", "84.201.42.195", "84.201.42.196", "84.201.42.197", "84.201.42.202",
			"84.201.42.200", "84.201.42.201", "84.200.227.132", "85.178.200.125" };
	private String[] dataProviderPPWIP = { "212.79.161.119", "217.7.251.147" };
	private String[] dataProviderAGEFIP = { "213.146.113.73", "87.193.197.15", "217.91.217.146" };
	private String[] dataProviderGETJOBSIP = { "127.0.0.1" };

	private boolean runFirst = false;
	private boolean readSearchData = true;
	private boolean insertTestData = false;
	private boolean contextStart = true;
	private boolean addDataProviderIP = true;

	private boolean runInitialization = runFirst || readSearchData || insertTestData || contextStart || addDataProviderIP;

	public JobexchangeInitializerImpl(PasswordEncoder passwordEncoder, SaltSourceService saltSource, Session session, HibernateSessionManager hbm) {
		this.session = session;
		this.hbm = hbm;
		this.passwordEncoder = passwordEncoder;
		this.saltSource = saltSource;

	}

	@SuppressWarnings("unchecked")
	public void initializeApplication(Context context, ApplicationInitializer applicationInitializer) {

		if (readSearchData) {

			Session hbmSession = hbm.getSession();
			FullTextSession fullTextSession = Search.getFullTextSession(hbmSession);
			Transaction tx = fullTextSession.beginTransaction();
			// tx.begin();
			List<JobImpl> jobs = hbmSession.createQuery("from JobImpl as job").list();
			for (JobImpl job : jobs) {
				fullTextSession.index(job);
			}
			fullTextSession.flushToIndexes();
			List<Applicant> applicants = hbmSession.createQuery("from Applicant as applicant").list();
			for (Applicant applicant : applicants) {
				fullTextSession.index(applicant);
			}
			tx.commit(); // index are written at commit time
		}

		if (addDataProviderIP) {
			Transaction t = session.getTransaction();
			t.begin();

			DataProvider apd = (DataProvider) session.load(DataProvider.class, new Long(1));
			DataProvider agef = (DataProvider) session.load(DataProvider.class, new Long(2));
			DataProvider ppw = (DataProvider) session.load(DataProvider.class, new Long(3));
			DataProvider getjobs = (DataProvider) session.load(DataProvider.class, new Long(4));

			apd.setProviderIP(dataProviderAPDIP);
			agef.setProviderIP(dataProviderAGEFIP);
			ppw.setProviderIP(dataProviderPPWIP);
			getjobs.setProviderIP(dataProviderGETJOBSIP);

			session.saveOrUpdate(apd);
			session.saveOrUpdate(agef);
			session.saveOrUpdate(ppw);
			session.saveOrUpdate(getjobs);
			t.commit();

		}

		if (runFirst) {

			Transaction t = session.getTransaction();
			t.begin();
			//
			DataProvider apd = new DataProvider("APD", dataProviderAPDIP);
			DataProvider agef = new DataProvider("AGEF", dataProviderAGEFIP);
			DataProvider ppw = new DataProvider("PPW", dataProviderPPWIP);
			DataProvider getjobs = new DataProvider("GETJOBS", dataProviderGETJOBSIP);

			session.save(apd);
			session.save(agef);
			session.save(ppw);
			session.save(getjobs);

			SQLQuery query = session.createSQLQuery("DROP TABLE IF EXISTS `static_countries`;");
			// query.executeUpdate();
			query = session
					.createSQLQuery("CREATE TABLE  `static_countries` (`id` int(11) unsigned NOT NULL auto_increment,`pid` int(11) unsigned default '0',`cn_iso_2` char(2) collate utf8_unicode_ci default '',`cn_iso_3` char(3) collate utf8_unicode_ci default '',`cn_iso_nr` int(11) unsigned default '0',`cn_parent_tr_iso_nr` int(11) unsigned default '0',`cn_official_name_local` varchar(128) collate utf8_unicode_ci default '',`cn_official_name_en` varchar(128) collate utf8_unicode_ci default '',`cn_capital` varchar(45) collate utf8_unicode_ci default '',`cn_tldomain` char(2) collate utf8_unicode_ci default '',`cn_currency_iso_3` char(3) collate utf8_unicode_ci default '',`cn_currency_iso_nr` int(10) unsigned default '0',`cn_phone` int(10) unsigned default '0',`cn_eu_member` tinyint(3) unsigned default '0',`cn_address_format` tinyint(3) unsigned default '0',`cn_zone_flag` tinyint(4) default '0',`cn_short_local` varchar(70) collate utf8_unicode_ci default '',`cn_short_en` varchar(50) collate utf8_unicode_ci default '',`cn_uno_member` tinyint(3) unsigned default '0',PRIMARY KEY  (`id`),UNIQUE KEY `id` (`id`)) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
			// query.executeUpdate();

			query = session.createSQLQuery("DROP TABLE IF EXISTS `occupationalField`;");
			// query.executeUpdate();
			query = session.createSQLQuery("CREATE TABLE `occupationalField` (" +
					" `id` int(11) unsigned NOT NULL auto_increment," +
					" `fieldId` int(11) unsigned default '0'," +
					" `parentFieldId` int(11) unsigned default '0'," +
					" `fieldNameGerman` varchar(150) collate utf8_unicode_ci default ''," +
					" `fieldNameEnglish` varchar(150) collate utf8_unicode_ci default ''," +
					" PRIMARY KEY  (`id`)," +
					" UNIQUE KEY `id` (`id`)" +
					"	) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
			// query.executeUpdate();

			String queryString = "INSERT INTO `occupationalField` (`OPTLOCK`,`id`,`fieldId`,`parentfieldId`,`fieldNameGerman`,`fieldNameEnglish`) VALUES"
					+
					"(0,1,1,0,'Administration/ Sachbearbeitung','Administration/ Office Organisation'),"
					+
					"(0,2,2,0,'Aus- und Weiterbildung','Training and further education'),"
					+
					"(0,3,3,0,'Bau und Architektur','Civil Engineering and Architecture'),"
					+
					"(0,4,4,0,'Baugewerbe und -industrie','Building Trade and Industry'),"
					+
					"(0,5,5,0,'Beratung','Consulting'),"
					+
					"(0,6,6,0,'Design, Fotografie und Gestaltung','Design, Photgraphy and Production'),"
					+
					"(0,7,7,0,'Einkauf, Logistik und Materialwirtschaft ','Purchase/  Logistics and Materials Management'),"
					+
					"(0,8,8,0,'Handwerk ','Crafts'),"
					+
					"(0,9,9,0,'Ingenieurwesen / Entwicklung und Konstruktion','Engineering/ Development and Design'),"
					+
					"(0,10,10,0,'IT, DV und Telekommunikation','IT, Data Processing and Telecommunications'),"
					+
					"(0,11,11,0,'Landwirtschaft, Natur und Umwelt','Agriculture, Nature and Environment'),"
					+
					"(0,12,12,0,'Management und Unternehmensführung ','Management and Corporate Management'),"
					+
					"(0,13,13,0,'Marketing und Öffentlichkeitsarbeit','Marketing and Public Relations'),"
					+
					"(0,14,14,0,'Medien','Media'),"
					+
					"(0,15,15,0,'Medizin und Gesundheit','Medical Science and Health'),"
					+
					"(0,16,16,0,'Metall, Maschinenbau und Konstruktion','Metal Engineering, Mechanical Engineering and Construction'),"
					+
					"(0,17,17,0,'Naturwissenschaftliche Berufe','Natural Science'),"
					+
					"(0,18,18,0,'Organisation, Verwaltung und Recht','Corporate Organisation, Administration and Law'),"
					+
					"(0,19,19,0,'Personalwesen','Human Resources'),"
					+
					"(0,20,20,0,'Produktion und Fertigung','Production and Manufacturing'),"
					+
					"(0,21,21,0,'Projektmanagement und -koordination ','Project Management and Project Coordination'),"
					+
					"(0,22,22,0,'Rechnungs-, Finanzwesen und Controlling','Accounting, Finance and Controlling'),"
					+
					"(0,23,23,0,'Verkehr und Logistik','Transport and Logistics'),"
					+
					"(0,24,24,0,'Vertrieb und Verkauf','Marketing and Sales'),"
					+
					"(0,25,25,0,'Sonstige Berufsfelder','Further Professions and Occupations'),"
					+
					"(0,26,26,0,'Entwicklungszusammenarbeit','Development Cooperation'),"
					+
					"(0,27,27,0,'Technikerberufe der angewandten Wissenschaften','Engineering Occupations in the field of Applied Sciences'),"
					+
					"(0,28,28,0,'Hotel, Gastronomie, Freizeit, Sport und Tourismus','Hotel and Catering Industry, Recreational, Sports and Tourism Industry'),"
					+
					"(0,29,29,0,'Kundendienst, Service und Inbetriebnahme','Customer Service, Service and Commissioning'),"
					+
					"(0,30,30,0,'Gesellschafts- und Geisteswissenschaften','Social Sciences and the Humanities'),"
					+
					"(0,31,31,0,'Sonstige soziale und pädagogische Berufe','Other Social and Educational Professions'),"
					+
					"(0,32,32,0,'Industrie-, Werkzeug- und KFZ-Mechaniker','Industrial Mechanics, Tool Mechanics and Motor Mechanics'),"
					+
					"(0,33,33,0,'Forschung und Lehre in wissenschaftlichen Institutionen','Research and Teaching in Scientific Institutions '),"
					+
					"(0,34,34,0,'Banken, Finanzdienstleistungen und Versicherungen ','Business support services and institutions'),"
					+
					"(0,35,35,0,'Biowissenschaften','Biosciences'),"
					+
					"(0,36,36,0,'Energieerzeugung und -versorgung','Generation of Energy and Energy Supply'),"
					+
					"(0,37,37,0,'Wasserversorgung und -entsorgung','Water supply and water disposal'),"
					+
					"(0,38,38,0,'Facility-Management','Facility Management'),"
					+
					"(0,39,39,0,'Sonstige akademische Berufe ','Other Academic Professions'),"
					+
					"(0,40,40,0,'Elektrik, Elektrotechnik und Mechatronik ','Electrical Engineering, Electronics and Mechatronics'),"
					+
					"(0,41,41,0,'Publizistische, Bibliotheks-, Übersetzungs- und verwandte Wissenschaftsberufe ','Media, Library, Translation and Interpretation and related fields'),"
					+
					"(0,42,42,0,'Kunst und Kultur','Arts and Culture'),"
					+
					"(0,43,43,0,'Sicherheitsorgane und Zivilschutz','Security Forces and Civil Defense'),"
					+
					"(0,44,101,1,'Buchhaltung und Rechnungswesen','bookkeeping and accounting'),"
					+
					"(0,45,102,1,'Büro und Sekretariat','office and secretariat'),"
					+
					"(0,46,103,1,'Kaufmännische Berufe','commercial occupations'),"
					+
					"(0,47,104,1,'Projektmitarbeit und Assistenz','working on projects and assistance with projects'),"
					+
					"(0,48,105,1,'Sachbearbeitung','processing/ office administration'),"
					+
					"(0,49,106,1,'Schul- und Universitätsverwaltung','school and university management'),"
					+
					"(0,50,107,1,'Verwaltung und öffentlicher Dienst','administration and public service'),"
					+
					"(0,51,108,1,'Sonstige Berufe im Bereich Administration / Sachbearbeitung','other occupations in the fields of administration/processing'),"
					+
					"(0,52,201,2,'Berufliche Bildung','vocational education'),"
					+
					"(0,53,202,2,'Bildungseinrichtungen und Fortbildung','educational institutions and continuing education and training'),"
					+
					"(0,54,203,2,'Bildungspolitik und Verwaltung im Bildungswesen','educational policy and educational management'),"
					+
					"(0,55,204,2,'Erwachsenenbildung','adult education'),"
					+
					"(0,56,205,2,'Forschung im Bereich Bildung','educational research'),"
					+
					"(0,57,206,2,'Fortbildung von Fach- und Führungskräften','continuing education of experts and senior executives'),"
					+
					"(0,58,207,2,'Grundschullehrer','primary school teachers'),"
					+
					"(0,59,208,2,'Dozenten','lecturers'),"
					+
					"(0,60,209,2,'Lehreraus- und -fortbildung ','initial teacher education and continuing teacher education'),"
					+
					"(0,61,210,2,'Sekundarstufenlehrer (inkl. Abitur)','secondary school teachers (incl. school leaving exam)'),"
					+
					"(0,62,211,2,'Sonderschullehrer','special education teacher'),"
					+
					"(0,63,212,2,'Vorschulunterricht, Kindergärtner','Pre-school and kindergarten  teacher'),"
					+
					"(0,64,213,2,'Sonstige Berufe im Bereich Aus-  und Weiterbildung','other occupations in the fields of training and continuing education'),"
					+
					"(0,65,301,3,'Architekten','architects'),"
					+
					"(0,66,302,3,'Baugutachter und Bauschätzer','building surveyor'),"
					+
					"(0,67,303,3,'Bauingenieure ','construction/civil engineer'),"
					+
					"(0,68,304,3,'Baupolitik und -verwaltung','building policy and building authorities/admininstration '),"
					+
					"(0,69,305,3,'Bauunternehmer und Bauleiter','builders and site managers'),"
					+
					"(0,70,306,3,'Bautechniker, Facharbeiter Bauwesen / Bauindustrie','site engineer, skilled engineering worker construction trade, building industry'),"
					+
					"(0,71,307,3,'Industriedesigner ','industrial designer'),"
					+
					"(0,72,308,3,'Landschaftsarchitekt','landscape architect'),"
					+
					"(0,73,309,3,'Niedrigkostenwohnungsbau','low-cost residental construction'),"
					+
					"(0,74,310,3,'Stadtentwicklungs- und Flächennutzungsplaner','town planner and land-use planner'),"
					+
					"(0,75,311,3,'Statiker','structural designer'),"
					+
					"(0,76,312,3,'Vermessungstechniker, Karthographen und verwandte Berufe','qualified land surveyors, cartographers and related professions'),"
					+
					"(0,77,313,3,'Sonstige Berufe im Bereich Bau und Architektur','other occupations in the fields of civil engineering and architecture'),"
					+
					"(0,78,401,4,'Bauhelfer','building labourer '),"
					+
					"(0,79,402,4,'Bauleiter','site manager'),"
					+
					"(0,80,403,4,'Bautechniker, Facharbeiter','site engineer, skilled construction worker'),"
					+
					"(0,81,404,4,'Sonstige Dienstleister ','other service providers'),"
					+
					"(0,82,405,4,'Sonstige Berufe im Bereich Baugewerbe und -industrie','other occupations in the fields of  building trade and industry'),"
					+
					"(0,83,501,5,'Beratung in den Bereichen Finanzen, Anlagen, Immobilien','consulting in the fields of finance, investment, real estate'),"
					+
					"(0,84,502,5,'Beratung in den Bereichen Soziales und Weiterbildung ','consulting in the fields of social affairs and continuing education'),"
					+
					"(0,85,503,5,'Beratung von Unternehmen, Organisationen, Investoren und Körperschaften','consulting of companies, organizations, investors  and corporations'),"
					+
					"(0,86,504,5,'Gutachtertätigkeiten','surveyor/appraiser'),"
					+
					"(0,87,505,5,'Weitere personenzentrierte Beratungsdienste ','further consulting services offered to individuals'),"
					+
					"(0,88,506,5,'Sonstige Berufe im Bereich Beratung','other occupations in the field of consulting'),"
					+
					"(0,89,601,6,'Computer Animation und Multimedia','computer animation and multimedia '),"
					+
					"(0,90,602,6,'Fotografie, Fotomedien und Videogestaltung','photography, photo media and videotaping'),"
					+
					"(0,91,603,6,'Grafik und Illustration','Graphics and Illustration'),"
					+
					"(0,92,604,6,'Textil-, Mode- und Produktdesign','textile design, fashion design and product design'),"
					+
					"(0,93,605,6,'Web- und User Interface Design','web design and user interface design'),"
					+
					"(0,94,606,6,'Sonstige Berufe im Bereich Design, Fotografie und Gestaltung','other occupations in the fields of design and production'),"
					+
					"(0,95,701,7,'Fachkräfte im Bereich Einkauf / Beschaffung','specialists in the field of purchasing and procurement'),"
					+
					"(0,96,702,7,'Fachkräfte im Bereich Lagerung / Aufbewahrung','specialists in the field of warehousing and storage'),"
					+
					"(0,97,703,7,'Fachkräfte im Bereich Logistik, Transport und Distribution','specialists in the field of logistics, transportation and distribution'),"
					+
					"(0,98,704,7,'Kaufmännische Berufe','commercial occupations'),"
					+
					"(0,99,705,7,'Sonstige Berufe im Bereich Einkauf, Logistik und Materialwirtschaft ','other occupations in the fields of purchase/logistics and materials management'),"
					+
					"(0,100,801,8,'Bauausführung und Innenausbau','construction and finishes'),"
					+
					"(0,101,802,8,'Bekleidung- und Textilherstellung','clothing and textile manufacture'),"
					+
					"(0,102,803,8,'Gesundheits-, Körperpflege- und Reinigungsgewerbe ','health care and body care industry, cleaning industry'),"
					+
					"(0,103,804,8,'Glas-, Holz, Papier-, Keramik- und sonstiges produzierendes Handwerk','glass industry, timber industry, paper industry, ceramic industry and other craft industries'),"
					+
					"(0,104,805,8,'Heizungs- und Klimaanlagen ','heating and air conditioning systems'),"
					+
					"(0,105,806,8,'Metallverarbeitung und Schmiede','metal processing and forge'),"
					+
					"(0,106,807,8,'Nahrungsmittelgewerbe ','food industry'),"
					+
					"(0,107,808,8,'Wasserinstallation und Sanitär','plumbing (installation of pipes and sanitary ware)'),"
					+
					"(0,108,809,8,'Sonstige Berufe im Bereich Handwerk','other occupations in the field of crafts'),"
					+
					"(0,109,901,9,'Bau- und Vermessungswesen','construction and surveying'),"
					+
					"(0,110,902,9,'Bergbauingenieure','mining engineers'),"
					+
					"(0,111,903,9,'Bio-, Chemie- und Verfahrensingenieurwesen','bioengineering, chemical engineering and process engineering'),"
					+
					"(0,112,904,9,'Elektrotechnik','electrical engineering'),"
					+
					"(0,113,905,9,'Energie- und Kerntechnik','power engineering, nuclear power engineering'),"
					+
					"(0,114,906,9,'Erdölingenieure','petrol engineers'),"
					+
					"(0,115,907,9,'Geologieingenieure','engineers specialized in geology'),"
					+
					"(0,116,908,9,'Interdisziplinäre Spezialisierungen','interdisciplinary specialization'),"
					+
					"(0,117,909,9,'Luft- und Raumfahrttechnik','aerospace engineering'),"
					+
					"(0,118,910,9,'Maschinenbau','mechanical engineering'),"
					+
					"(0,119,911,9,'Prozessteuerung und Prozessentwicklung','process engineering and process design'),"
					+
					"(0,120,912,9,'Raumfahrtingenieure','aerospace engineers'),"
					+
					"(0,121,913,9,'Schiffbau','shipbuilding'),"
					+
					"(0,122,914,9,'Solartechnik und erneuerbare Energien','solar engineering and renewable energies'),"
					+
					"(0,123,915,9,'Technisches Zeichnen und CAD','engineering drawing and CAD'),"
					+
					"(0,124,916,9,'Wirtschafts- und Fertigungsingenieure','industrial engineers and production engineers'),"
					+
					"(0,125,917,9,'Sonstige Berufe im Bereich Ingenieurwesen/ Entwicklung und Konstruktion','other occupations in the field of engineering/design and construction'),"
					+
					"(0,126,1001,10,'Computertechniker','IT specialists'),"
					+
					"(0,127,1002,10,'Datenbankentwicklung und -administration','design and administration of data bases'),"
					+
					"(0,128,1003,10,'Informatiker, Softwareentwickler, Systemadministratoren','computer scientists, software engineers, system administrators'),"
					+
					"(0,129,1004,10,'Interface Designer und Usability Experten','interface designers, usability experts'),"
					+
					"(0,130,1005,10,'IT-Berater, IT-Kaufleute und IT-Techniker','IT consultants, IT clerks, IT engineers'),"
					+
					"(0,131,1006,10,'Telekommunikationstechniker','telecommunications engineers'),"
					+
					"(0,132,1007,10,'Webdesigner und Content Manager','web designers and content managers'),"
					+
					"(0,133,1008,10,'Sonstige Berufe im Bereich IT, DV und Telekommunikation','other occupations in the fields of IT, data processing and telecommunications'),"
					+
					"(0,134,1101,11,'Alternative Landwirtschaftsentwicklung','alternative development of agriculture'),"
					+
					"(0,135,1102,11,'Aus- und Fortbildung im Bereich Fischereiwesen','basic and further education in the field of fishery'),"
					+
					"(0,136,1103,11,'Aus- und Fortbildung im Bereich Landwirtschaft','basic and further education in the field of agriculture'),"
					+
					"(0,137,1104,11,'Biodiversität','biodiversity'),"
					+
					"(0,138,1105,11,'Brennholz und Holzkohle','firewood and charcoal'),"
					+
					"(0,139,1106,11,'Dienstleistungen im Bereich Fischereiwesen','services in the field of fishery'),"
					+
					"(0,140,1107,11,'Fischereientwicklung','development of fishery'),"
					+
					"(0,141,1108,11,'Fischereiforschung','research in the field of fishery'),"
					+
					"(0,142,1109,11,'Fischereipolitik und -verwaltung','fishery policy and administration'),"
					+
					"(0,143,1110,11,'Forstentwicklung','development of forestry'),"
					+
					"(0,144,1111,11,'Forstliche Aus- und Fortbildung','basic and further education in the field of forestry'),"
					+
					"(0,145,1112,11,'Forstliche Dienstleistungen','services in the field of forestry'),"
					+
					"(0,146,1113,11,'Forstliche Forschung','research in the field of forestry'),"
					+
					"(0,147,1114,11,'Forstpolitik und -verwaltung','forestry policy and administration '),"
					+
					"(0,148,1115,11,'Gebietsschutz','territory protection'),"
					+
					"(0,149,1116,11,'Hochwasserschutz','flood protection'),"
					+
					"(0,150,1117,11,'Landwirte, Forstwirte, Fischer','agriculturists, foresters, fishermen'),"
					+
					"(0,151,1118,11,'Landwirtschaftliche Beratung','agricultural consultation'),"
					+
					"(0,152,1119,11,'Landwirtschaftliche Dienste','agricultural services'),"
					+
					"(0,153,1120,11,'Landwirtschaftliche Finanzdienste','agricultural financial services'),"
					+
					"(0,154,1121,11,'Landwirtschaftliche Genossenschaften','agricultural cooperatives'),"
					+
					"(0,155,1122,11,'Landwirtschaftliche Landressourcen','farmland resources'),"
					+
					"(0,156,1123,11,'Landwirtschaftsentwicklung','agricultural development'),"
					+
					"(0,157,1124,11,'Landwirtschaftspolitik und  -verwaltung','agricultural policy and administration'),"
					+
					"(0,158,1125,11,'Pflanzenschutz, Nachernteschutz und Schädlingsbekämpfung','plant protection, post-harvest protection, pest control'),"
					+
					"(0,159,1126,11,'Schutz der Biosphäre','protection of biosphere'),"
					+
					"(0,160,1127,11,'Umwelterziehung/ Fortbildung','environmental education/further education'),"
					+
					"(0,161,1128,11,'Umweltforschung','environmental research'),"
					+
					"(0,162,1129,11,'Umweltpolitik und -verwaltung','environmental policy and administration'),"
					+
					"(0,163,1130,11,'Sonstige Berufe im Bereich Landwirtschaft, Natur und Umwelt','other occupation in the fields of agriculture, environment and nature'),"
					+
					"(0,164,1201,12,'Abteilungs- und Bereichsleitung','management of department and of division'),"
					+
					"(0,165,1202,12,'Consulting/ Unternehmensberatung','consulting'),"
					+
					"(0,166,1203,12,'Filial- und Niederlassungsleitung','management of an affiliate and a branch office'),"
					+
					"(0,167,1204,12,'Projektmanagement und -leitung','project management'),"
					+
					"(0,168,1205,12,'Strategieentwicklung und Planung','strategery and planning'),"
					+
					"(0,169,1206,12,'Vorstand, Geschäftsführung und Betriebsleitung','board of directors, management, management committee'),"
					+
					"(0,170,1207,12,'Sonstige Berufe im Bereich Management und Unternehmensführung ','other occupations in the fields of management and administration'),"
					+
					"(0,171,1301,13,'Kommunikation und Presse','communication and press'),"
					+
					"(0,172,1302,13,'Markt- und Meinungsforschung','marketing research and opinion research'),"
					+
					"(0,173,1303,13,'Online-Marketing','online marketing'),"
					+
					"(0,174,1304,13,'Öffentlichkeitsarbeit und Marketing','public relations and marketing'),"
					+
					"(0,175,1305,13,'Veranstaltungsplanung und -durchführung','event planning and management'),"
					+
					"(0,176,1306,13,'Werbung und Mediengestaltung','advertisement, commercials and media design'),"
					+
					"(0,177,1307,13,'Sonstige Berufe im Bereich Marketing und Öffentlichkeitsarbeit','other occupations in the field of marketing and public relations'),"
					+
					"(0,178,1401,14,'Dienstleistungen in digitalen Medien','services in digital media'),"
					+
					"(0,179,1402,14,'Dienstleistungen in Radio-, Fernseh- oder Filmbranche  ','services in the radio, film and TV industry'),"
					+
					"(0,180,1403,14,'Gestaltung, Design, Grafik und Layout','creation, design, graphics and layout'),"
					+
					"(0,181,1404,14,'Journalismus, Redaktion und Publizistik','journalism, editorial services and communication science'),"
					+
					"(0,182,1405,14,'Kommunikation und Technik','communication and technics'),"
					+
					"(0,183,1406,14,'Sonstige Berufe im Bereich Medien','other occupations in the field of the media'),"
					+
					"(0,184,1501,15,'Allgemeine Basisvorsorge und Betreuung bestimmter Gruppen (Flüchtlinge, Gastarbeiter)','general basic precautions and care for certain groups (of refugees, migrant labourers)'),"
					+
					"(0,185,1502,15,'Apothekenwesen und Pharmazie','apothekary trade and pharmacy'),"
					+
					"(0,186,1503,15,'Aufklärung in Gesundheitsfragen','health education'),"
					+
					"(0,187,1504,15,'Ausbilder im Gesundheitsbereich','healt instructor'),"
					+
					"(0,188,1505,15,'Basisgesundheitswesen','basic health care system'),"
					+
					"(0,189,1506,15,'Epidemiologie, Statistiken und Schreiben von Gesundheitsberichten','epidemiology, statistics and health reporting '),"
					+
					"(0,190,1507,15,'Familienplanung','family planning'),"
					+
					"(0,191,1508,15,'Gesundheitspolitik und Verwaltung des Gesundheitswesens','health policy and health administration'),"
					+
					"(0,192,1509,15,'Gesundheitswesen allgemein','general health care system '),"
					+
					"(0,193,1510,15,'Kranken- und Altenpflege','nursing and geriatric care'),"
					+
					"(0,194,1511,15,'Mediziner (Ärzte allgemein, Zahnärzte), Apotheker','medical scientists (doctors in general, dentists), pharmacists'),"
					+
					"(0,195,1512,15,'Medizinische Aus- und Fortbildung','medical education and further education'),"
					+
					"(0,196,1513,15,'Medizinische Vorsorgebehandlung und -methoden','medical precautionary treatment and methods'),"
					+
					"(0,197,1514,15,'Nothilfe und Rettungsdienste','emergency assistance and medical rescue service'),"
					+
					"(0,198,1515,15,'Optik und Akustik','optics and acoustics'),"
					+
					"(0,199,1516,15,'Physiotherapie und Rehabilitationwesen','physiotherapy and rehabilitation systems'),"
					+
					"(0,200,1517,15,'Praktische Medizin und Zahnmedizin','practical medicine and dentistry'),"
					+
					"(0,201,1518,15,'Prüfer in öffentlicher Gesundheitspflege, Umweltmedizin und Arbeitsschutz','public health inspector, environmental medicine and occupational health and safety'),"
					+
					"(0,202,1519,15,'Psychologen und Psychiater','psychologists and psychiatrists'),"
					+
					"(0,203,1520,15,'Soziale Medizin and Präventive Medizin, Hygieneaufklärung','social medicine and preventive medicine, health education (hygiene)'),"
					+
					"(0,204,1521,15,'Tiermedizin und Tierpflege','veterinary medicine and animal keeping'),"
					+
					"(0,205,1522,15,'Vorbeugender Gesundheitsschutz, präventive Epidemiebekämpfung, Impfprogramme','preventive health protection, preventive control of epidemics, immunization schedules'),"
					+
					"(0,206,1523,15,'Sonstige Berufe im Bereich Medizin und Gesundheit','other occupations in the field of medicine and health'),"
					+
					"(0,207,1601,16,'Maschinenbauingenieure und -techniker','graduate mechanical engineers and mechanical engineers'),"
					+
					"(0,208,1602,16,'Metallurgie und Materialingenieure ','metallurgical and materials engineers '),"
					+
					"(0,209,1603,16,'Sonstige Berufe im Bereich Metall, Maschinenbau und Konstruktion','other occupations in the field of metal, mechanical engineering and design '),"
					+
					"(0,210,1701,17,'Chemiker','chemists'),"
					+
					"(0,211,1702,17,'Ernährungswissenschaftler','nutrionists'),"
					+
					"(0,212,1703,17,'Informatiker, Systemprogrammierer, Systemanalytiker','IT specialists, system programmers, system analysers'),"
					+
					"(0,213,1704,17,'Mathematiker, Statistiker, Aktuare','mathematicians, statisticians, actuaries'),"
					+
					"(0,214,1705,17,'Medizinische und pharmazeutische Forschung','medical and pharmaceutical research'),"
					+
					"(0,215,1706,17,'Meteorologen, Geologen, Geophysiker','meteorologists, geologists, geophysicists'),"
					+
					"(0,216,1707,17,'Physiker, Astrophysiker, Quantenphysiker, Astronomen','physicists, astrophysicists, quantum physicists, astronomers'),"
					+
					"(0,217,1708,17,'Sonstige Berufe im Bereich Naturwissenschaftliche Berufe','other occupations in the field of natural sciences'),"
					+
					"(0,218,1801,18,'Arbeitsrecht','employment law'),"
					+
					"(0,219,1802,18,'Beamte der Einwanderungsbehörde, Versicherungsbeamte und Steuerbeamte','public officers of the immigration authority, insurances and tax authority'),"
					+
					"(0,220,1803,18,'Beschäftigungspolitik und Arbeitsverwaltung','emploment policy and labour administration'),"
					+
					"(0,221,1804,18,'Entwicklung von Recht und Gerichtswesen','development of law and judicature'),"
					+
					"(0,222,1805,18,'Finanzverwaltung (öffentlicher Sektor)','financial administration (public sector)'),"
					+
					"(0,223,1806,18,'Gerichtsbeamte und Friedensrichter','court clerks, justices of the peace'),"
					+
					"(0,224,1807,18,'Krisenprävention und Konfliktlösung, Frieden und Sicherheit','crisis prevention and conflict resolution, peace and security'),"
					+
					"(0,225,1808,18,'Menschenrechtsaktivisten','human rights activists'),"
					+
					"(0,226,1809,18,'Patentrecht','patent law'),"
					+
					"(0,227,1810,18,'Regierungsverwaltung und Staatsbeamte','governmental administration and civil servants'),"
					+
					"(0,228,1811,18,'Steuer- und Wirtschaftsrecht','tax law, business law'),"
					+
					"(0,229,1812,18,'Straf- und Zivilrecht','criminal law, civil law'),"
					+
					"(0,230,1813,18,'Wahlbeobachter','election observers'),"
					+
					"(0,231,1814,18,'Wirtschafts- und Entwicklungspolitik und -planung','economic policy, development policy and planning'),"
					+
					"(0,232,1815,18,'Sonstige Berufe im Bereich Organisation, Verwaltung und Recht','other occupations in the field of organization, administration and law'),"
					+
					"(0,233,1901,19,'Arbeitsvermittlung','job placement'),"
					+
					"(0,234,1902,19,'Aus- und Weiterbildung ','basic and further education'),"
					+
					"(0,235,1903,19,'Lohn- und Gehaltsabrechnung','wage and salary administration/payroll accountintg'),"
					+
					"(0,236,1904,19,'Personalvermittlung','personell service'),"
					+
					"(0,237,1905,19,'Personalmanagement und -verwaltung','HR, personnel management and administration'),"
					+
					"(0,238,1906,19,'Sonstige Berufe im Bereich Personalwesen','other occupations in the field of HR'),"
					+
					"(0,239,2001,20,'Chemische- / Pharmazeutische Produktion','chemical and pharmaceutical production'),"
					+
					"(0,240,2002,20,'Druckerei','printing'),"
					+
					"(0,241,2003,20,'Elektronik und Telekommunikationstechnik','electronical and telecommunication technics'),"
					+
					"(0,242,2004,20,'Gießerei und Formgebung','foundry and shaping'),"
					+
					"(0,243,2005,20,'Metall- und Schweißtechnik','metal-working and welding technologies '),"
					+
					"(0,244,2006,20,'Montage und Verpackung','assembling and packing'),"
					+
					"(0,245,2007,20,'Näherei  und Schneiderei','sewing and tailoring'),"
					+
					"(0,246,2008,20,'Nahrungs- und Genussmittelproduktion','food and luxury food production'),"
					+
					"(0,247,2009,20,'Sonstige Berufe im Bereich Produktion und Fertigung','other occupations in the fields of production and manufacturing'),"
					+
					"(0,248,2101,21,'Beratung und Monitoring','consulting and monitoring'),"
					+
					"(0,249,2102,21,'Projektassistenz','project assistant'),"
					+
					"(0,250,2103,21,'Projektfinanzierung und -abrechnung','project finance and accounting'),"
					+
					"(0,251,2104,21,'Projektkoordination und -organisation','project coordination and organization'),"
					+
					"(0,252,2105,21,'Projektleitung','project management'),"
					+
					"(0,253,2106,21,'Sonstige Berufe im Bereich Projektmanagment und -koordination ','other occupations in the field of project management and coordination'),"
					+
					"(0,254,2201,22,'Buchhaltung ','bookkeeping and accounting'),"
					+
					"(0,255,2202,22,'Controlling','controlling'),"
					+
					"(0,256,2203,22,'Gutachtertätigkeit','acting as an expert'),"
					+
					"(0,257,2204,22,'Kreditprüfung und -bearbeitung','credit check and processing'),"
					+
					"(0,258,2205,22,'Risikoanalyst, Investmentbanker und Sonstige Finanzdienstleister','risk analyst, investment banker and other financial service providers'),"
					+
					"(0,259,2206,22,'Steuer-, Immobilien-, Finanzberater ','tax consultant, real estate agent/consultant, financial consultant'),"
					+
					"(0,260,2207,22,'Wertpapierhandel und Fondverwaltung','stock broking, fund management'),"
					+
					"(0,261,2208,22,'Wirtschaftsprüfung und Revision','auditing and bank examination'),"
					+
					"(0,262,2209,22,'Sonstige Berufe im Bereich Rechnungs-, Finanzwesen und Controlling','other occupations in the fields of accounting, finance and controlling'),"
					+
					"(0,263,2301,23,'Fachkräfte im Lagerwesen','specialists in storage and warehousing '),"
					+
					"(0,264,2302,23,'Fachkräfte im Transportwesen','specialists in transport'),"
					+
					"(0,265,2303,23,'Luftverkehrswesen','air traffic'),"
					+
					"(0,266,2304,23,'Schienenverkehrswesen','railway traffic'),"
					+
					"(0,267,2305,23,'Straßenverkehrswesen','road traffic'),"
					+
					"(0,268,2306,23,'Speditionskaufmann und Sachbearbeiter','shipping clerk and people dealing with logistics'),"
					+
					"(0,269,2307,23,'Wasserverkehrswesen','water traffic '),"
					+
					"(0,270,2308,23,'Verkehrspolitik und  -verwaltung','traffic policy and administration'),"
					+
					"(0,271,2309,23,'Sonstige Berufe im Bereich Verkehr und Logistik','other occupations in the fields of shipping and logistics'),"
					+
					"(0,272,2401,24,'Account Management','account management'),"
					+
					"(0,273,2402,24,'Außendienstmitarbeiter, Vertreter  und  Verkäufer','field service, representative and sales person'),"
					+
					"(0,274,2403,24,'Immobilienhandel','real estate bsuiness'),"
					+
					"(0,275,2404,24,'Kaufmännische Berufe','business careers'),"
					+
					"(0,276,2405,24,'Versicherungsvertretung','insurance agency'),"
					+
					"(0,277,2406,24,'Sonstige Berufe im Bereich Vertrieb und Verkauf','other occupations in the fiels of marketing and sales'),"
					+
					"(0,278,2501,25,'Aus- und Fortbildung im Bereich Handel','education and further education in the field of trade'),"
					+
					"(0,279,2502,25,'Aus- und Fortbildung im Bereich Transport und Lagerhaltung','education and further education in the field of transportation and storage'),"
					+
					"(0,280,2503,25,'Handelspolitik und -verwaltung','trade policy and trade administration'),"
					+
					"(0,281,2504,25,'Lagerhaltung','storage and warehousing'),"
					+
					"(0,282,2505,25,'Restaurant und Verpflegungsmanager','restaurant and catering manager'),"
					+
					"(0,283,2506,25,'Sonstige Berufsfelder','other professional fields '),"
					+
					"(0,284,2601,26,'Austausch und Kultur','exchange and culture'),"
					+
					"(0,285,2602,26,'Bildung, Gesundheit und Soziales','education, health, social affairs'),"
					+
					"(0,286,2603,26,'Entwicklung, Konstruktion und technische Zusammenarbeit','development, structure and technical cooperation'),"
					+
					"(0,287,2604,26,'Finanz- und Sozialwissenschaften ','financial and social sciences'),"
					+
					"(0,288,2605,26,'Projektmanagement','project management'),"
					+
					"(0,289,2606,26,'Sonstiges Berufe im Bereich der Entwicklungszusammenarbeit','other occupations in the field of development cooperation'),"
					+
					"(0,290,2701,27,'Bordinstrument-, Elektrik- und Luftfahrtmechaniker, -techniker und kontrolleure','mechanics, engineers and inspectors for airplane instruments, airplane electrics and aviation'),"
					+
					"(0,291,2702,27,'Chemietechnologen','chemical technologists'),"
					+
					"(0,292,2703,27,'Elektro-, ingenieurtechnische Elektronik- und Telekommunikationstechniker','electrical engineers, electronics engineers, telecommunication engineers. '),"
					+
					"(0,293,2704,27,'Geologie und Mineraltechniker','engineers specialised in mineralogy and geology '),"
					+
					"(0,294,2705,27,'Meteorologietechniker','engineers specialised in meteorology '),"
					+
					"(0,295,2706,27,'Techniker und Mechaniker im Produktmanagement','engineers and mechanics in the field of product management'),"
					+
					"(0,296,2707,27,'Sonstige Berufe im Bereich Technikerberufe der angewandten Wissenschaften','other occupations in the field of engineering in applied sciences'),"
					+
					"(0,297,2801,28,'Dienstleistungen in sportlichen Einrichtungen und Sportindustrie','services in sport facilities and in the sport industry'),"
					+
					"(0,298,2802,28,'Hotel-, Restaurant- bzw. Gaststättengewerbe','hotel, restaurant and catering industry '),"
					+
					"(0,299,2803,28,'Messen und Freizeitveranstaltungen / Freizeitindustrie','trade fairs, recreational events/leisure industry'),"
					+
					"(0,300,2804,28,'Profisport','professional sports'),"
					+
					"(0,301,2805,28,'Tourismusbranche und Fremdenverkehr','tourism industry and tourism'),"
					+
					"(0,302,2806,28,'Tourismuspolitik und  -verwaltung','tourism policy and administration'),"
					+
					"(0,303,2807,28,'Sonstige Berufe im Bereich Hotel, Gastronomie, Freizeit, Sport und Tourismus','other occupations in the fields of hotel, restaurant and catering industry, sports and tourism industry'),"
					+
					"(0,304,2901,29,'Außen- und Kundendienst (Montage / Wartung)','field service, customer service (assembly+installation/maintenance)'),"
					+
					"(0,305,2902,29,'Lieferservice, Mitarbeiterservice und ähnliche Serviceleistungen','delivery service, staff service and similar services'),"
					+
					"(0,306,2903,29,'Telefonischer Kundenservice und Call-Center','customer call center and call center'),"
					+
					"(0,307,2904,29,'Sonstige Berufe im Bereich Kundendienst, Service und Inbetriebnahme','other occupations in the fields of customer service, service and commissioning'),"
					+
					"(0,308,3001,30,'Anthropologen und Ethnologen','anthropologists and ethnologists'),"
					+
					"(0,309,3002,30,'Demografen','demographers'),"
					+
					"(0,310,3003,30,'Philosophen, Historiker, Kunsthistoriker, Kulturwissenschaftler','philosophers, historians, art historians, cultural scientists'),"
					+
					"(0,311,3004,30,'Psychologen, Diplomsozialarbeiter','psychologists, graduate social workers'),"
					+
					"(0,312,3005,30,'Rechtswissenschaftler (Anwälte) und Politikwissenschaftler','legal scholars (lawyers) and political scientists'),"
					+
					"(0,313,3006,30,'Soziologen, Sozialpsychologen, Kulturgeografen ','sociologists, social psychologists, cultural geographers'),"
					+
					"(0,314,3007,30,'Theologen','theologians'),"
					+
					"(0,315,3008,30,'Wirtschaftswissenschaftler','economists'),"
					+
					"(0,316,3009,30,'Sonstige Berufe im Bereich Gesellschafts- und Geisteswissenschaften','other occupations in the fields of social sciences and the humanities'),"
					+
					"(0,317,3101,31,'Abfederung der sozialen Folgen von HIV/AIDS','mitigation of the social consequences of HIV/AIDS'),"
					+
					"(0,318,3102,31,'Beschäftigungspolitik und Arbeitsverwaltung','employment policy and labour administration'),"
					+
					"(0,319,3103,31,'Drogenbekämpfung','drug enforcement'),"
					+
					"(0,320,3104,31,'Kultur und Freizeit','culture and leisure time'),"
					+
					"(0,321,3105,31,'Multisektorale Hilfe für soziale Grunddienste','multi-sectoral support for social basic services'),"
					+
					"(0,322,3106,31,'Sozialfürsorge / soziale Dienste','social welfare/ social services'),"
					+
					"(0,323,3107,31,'Statistische Kapazitätsbildung','statistical capacity building'),"
					+
					"(0,324,3108,31,'Wohnungsbaupolitik  und -verwaltung','house building policy and administration'),"
					+
					"(0,325,3109,31,'Sonstige soziale und pädagogische Berufe','other social and educational professions'),"
					+
					"(0,326,3201,32,'Fahrzeugbau und -instandhaltung','manufacturing and maintenance of vehicles'),"
					+
					"(0,327,3202,32,'Produktion und Fertigung von Maschinen und Technik','manufacturing and production of machinery and technics'),"
					+
					"(0,328,3203,32,'Spezialisierte Mechanikerberufe','specialized mechanics (different occupations)'),"
					+
					"(0,329,3204,32,'Wartung und Reparatur','maintenance and repair'),"
					+
					"(0,330,3205,32,'Sonstige Berufe im Bereich Industrie-, Werkzeug- und KFZ-Mechaniker','other occupations in the fields of industrial mechanics, tool mechanics and motor mechanics'),"
					+
					"(0,331,3301,33,'Gastdozenten ','guest lecturers')," +
					"(0,332,3302,33,'Hochschulprofessoren ','university professors')," +
					"(0,333,3303,33,'Wissenschaftliche Mitarbeiter','scientific staff')," +
					"(0,334,3304,33,'Sonstige Berufe in wissenschaftlichen Institutionen','other professions in scientific institutions')," +
					"(0,335,3401,34,'Finanzdienstleistungen mit Schwerpunkt Beratung ','financial services focussing on consultation ')," +
					"(0,336,3402,34,'Finanzmakler, Börsendienste und Rating-Agenturen','loan brokers, stock exchange services and rating agencies')," +
					"(0,337,3403,34,'Finanzsektorpolitik und -verwaltung','financial sector policies and administration')," +
					"(0,338,3404,34,'Privatisierung','privatization')," +
					"(0,339,3405,34,'Währungsinstitutionen','monetary institutes ')," +
					"(0,340,3406,34,'Versicherungskaufmann/frau','insurance clerk')," +
					"(0,341,3407,34,'Bankkaufmann/frau','banker')," +
					"(0,342,3408,34,'Sonstige Berufe im Bereich Banken, Finanzdienstleistungen und Versicherungen ','other occupations in the fields of business support services and institutions')," +
					"(0,343,3501,35,'Biologen, Biochemiker, Botaniker, Zoologen und verwandte Wissenschaftler','biologists, biochemists, botanists, zoologists and related scientists')," +
					"(0,344,3502,35,'Medizinsche und pharmazeutische Forschung','medical and pharmaceutical research')," +
					"(0,345,3503,35,'Sonstige Berufe im Bereich Biowissenschaften','other occupations in the field of biosciences ')," +
					"(0,346,3601,36,'Andere erneuerbare Energieformen','other renewable energies')," +
					"(0,347,3602,36,'Energiegewinnung und -verwendung aus Biomasse und Abfall','biomass-to-energy and waste-to-energy technologies')," +
					"(0,348,3603,36,'Energiespeichertechniken','energy storage techniques')," +
					"(0,349,3604,36,'Erdwärmeenergie, Wärmefkraftwerke','geothermal energy, thermal power stations')," +
					"(0,350,3605,36,'Meeresenergie','ocean energy')," +
					"(0,351,3606,36,'Solarenergie','solar energy')," +
					"(0,352,3607,36,'Windenergie','wind energy')," +
					"(0,353,3608,36,'Sonstige Berufe im Bereich Energieerzeugung und -versorgung','other occupations in the fields of energy production, energy supply ')," +
					"(0,354,3701,37,'Abfallwirtschaft und -entsorgung','waste industry and disposal')," +
					"(0,355,3702,37,'Abwasseranalyse','analysis of waste water ')," +
					"(0,356,3703,37,'Abwassersammlung und -aufbereitung','collection of waste water and treatment of waste water')," +
					"(0,357,3704,37,'Wasserressourcenpolitik und -verwaltung','policy of water resources and administration of water resources')," +
					"(0,358,3705,37,'Wasserressourcenschutz','protection of water resources')," +
					"(0,359,3706,37,'Sonstige Berufe im Bereich Wasserversorgung und -entsorgung','other occupations in the field of water supply and disposal')," +
					"(0,360,3801,38,'Andere Bauunternehmen, Installateure und Wiederinstandsetzer','other construction companies, fitters and repairers')," +
					"(0,361,3802,38,'Gebäudemanagment','facility management')," +
					"(0,362,3803,38,'Gebäudewartung / -reinigung','building maintenance/ commercial cleaning')," +
					"(0,363,3804,38,'Kontrolleure, Bahnbetrieb ','inspectors, railway system')," +
					"(0,364,3805,38,'Mechaniker, Maschinisten und Bauaufseher','mechanics, machine operators and site inspectors')," +
					"(0,365,3806,38,'Produktions- und Instandhaltungsmanager','production managers and maintenance managers')," +
					"(0,366,3807,38,'Transportmanager','transport managers')," +
					"(0,367,3808,38,'Wohnheimerbauer und -erneuerer','hostel builders and hostel renovation managers')," +
					"(0,368,3809,38,'Sonstige Berufe im Bereich Facility-Management','other occupations in the field of facility management')," +
					"(0,369,3901,39,'Konferenzorganisatoren, Veranstaltungsplaner','conference organizers, event managers, event planners')," +
					"(0,370,3902,39,'Sonstige akademische Berufe','other academic professions')," +
					"(0,371,4001,40,'Elektriker','electrician')," +
					"(0,372,4002,40,'Kabelfernseh- und Wartungstechniker','engineer for cable tv and maintenance ')," +
					"(0,373,4003,40,'Mechatroniker','mechatronic engineer')," +
					"(0,374,4004,40,'Starkstromleitungs- und Kabelarbeiter','labourer for high power current lines and other cables')," +
					"(0,375,4005,40,'Stromversorgungselektriker','power supply electrician')," +
					"(0,376,4006,40,'Telekommunikationsinstallateure und Instandsetzungsarbeiter','telecommunications fitters and maintenance labourers')," +
					"(0,377,4007,40,'Sonstige Berufe im Bereich Elektrik, Elektrotechnik und Mechatronik ','ohter occupations in the field of electrical engineering, electronics and mechatronics')," +
					"(0,378,4101,41,'Bibliothekswesen','librarianship')," +
					"(0,379,4102,41,'Kommunikationswissenschaftler, Sprachwissenschaftler, Dolmetscher, Übersetzer','communication scientists, linguists, interpreters, translators')," +
					"(0,380,4103,41,'Redaktion und Lektorat','editorial services, lectorship')," +
					"(0,381,4104,41,'Schriftsteller, Journalisten, Autoren (inkl. Drehbuchautoren)','writers, journalists, authors (including screenwriters)')," +
					"(0,382,4105,41,'Verlagswesen','publishing industry')," +
					"(0,383,4106,41,'Zeitungen, Zeitschriften und sonstige Printmedien ','newspapers, journals and other print media')," +
					"(0,384,4107,41,'Sonstige verwandte Wissenschaftsberufe','other related scientific professions')," +
					"(0,385,4108,41,'Qualitätsmanagment und -sicherung','Quality Management and Control')," +
					"(0,386,4109,41,'Qualitätskontrolle','quality control')," +
					"(0,387,4110,41,'Qualitätssicherung (Software, Produktion, etc.)','quality management (software, manufacturing etc)')," +
					"(0,388,4111,41,'Zertifizierungs- und Prüfwesen (ISO,TÜV, etc.)','certifying and testing (ISO, TUEV etc)')," +
					"(0,389,4112,41,'Sonstige Berufe im Bereich Qualitätsmanagment und -sicherung','other occupations in the fields of quality management and control')," +
					"(0,390,4201,42,'Archäologie, Denkmalpflege und Restauration','archeology, preservation of historical monuments, restauration')," +
					"(0,391,4202,42,'Dienstleistungen in Musik-, Radio-, Film- oder Fernsehindustrie ','services in the music, radio, film or TV industry')," +
					"(0,392,4203,42,'Dienstleistungen in Theatern, Museen oder sonstigen kulturellen Einrichtungen','services in theatres, museums or other cultural institutions')," +
					"(0,393,4204,42,'Komponisten, Musiker, Sänger','composers, musicians, singers')," +
					"(0,394,4205,42,'Kulturmanager','cultural managers')," +
					"(0,395,4206,42,'Maler, Bildhauer und verwandte Künstler','painters, sculptors and related artists')," +
					"(0,396,4207,42,'Regisseure und Schauspieler','stage directors and producers, actors')," +
					"(0,397,4208,42,'Tänzer, Choreographen, Dramaturgen ','dancers, choreographers, dramatic advisors')," +
					"(0,398,4209,42,'Sonstige Berufe im Bereich Kunst und Kultur','other occupations in the fields of arts and culture')," +
					"(0,399,4301,43,'Feuerwehr und Rettungsdienst','fire service and rescue service ')," +
					"(0,400,4302,43,'Polizei','police')," +
					"(0,401,4303,43,'Sicherheitsdienste','security services')," +
					"(0,402,4304,43,'Strafvollzug','penal system')," +
					"(0,403,4305,43,'Verteidigung/ Militär','military defense/armed forces')," +
					"(0,404,4306,43,'Sonstige Berufe im Bereich Sicherheitsorgane und Zivilschutz','other occupations in the fields of security forces and civil defense');";

			query = session.createSQLQuery(queryString);
			query.executeUpdate();

			query = session.createSQLQuery("DROP TABLE IF EXISTS `industrySector`;");
			query = session.createSQLQuery("CREATE TABLE `industrySector` (" +
					" `id` int(11) unsigned NOT NULL auto_increment," +
					" `sectorId` int(11) unsigned default '0'," +
					" `parentSectorId` int(11) unsigned default '0'," +
					" `sectorNameGerman` varchar(150) collate utf8_unicode_ci default ''," +
					" `sectorNameEnglish` varchar(150) collate utf8_unicode_ci default ''," +
					" PRIMARY KEY  (`id`)," +
						" UNIQUE KEY `id` (`id`)" +
						"	) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");

			String sectorQueryString = "INSERT INTO `industrySector` (`OPTLOCK`,`id`,`sectorId`,`parentSectorId`,`sectorNameGerman`,`sectorNameEnglish`) VALUES" +

			"(0,1,1,0,'Agentur, Werbung und Marketing','Agencies, Advertising and Marketing')," +
					"(0,2,2,0,'Architektur und Design','Architecture and Design')," +
					"(0,3,3,0,'Banken','Banking')," +
					"(0,4,4,0,'Baugewerbe und -industrie','Building Trade and Industry')," +
					"(0,5,5,0,'Bergbau','Mining Industry')," +
					"(0,6,6,0,'Biotechnik und Pharmaindustrie','Biotechnology and Pharmaceutical Industry')," +
					"(0,7,7,0,'Bildung, Weiterbildung und Training','Education, Further Education and Training')," +
					"(0,8,8,0,'Buchhaltung, Steuer- und Prüfungswesen','Accountancy, Fiscal System and Auditing')," +
					"(0,9,9,0,'Chemie- und erdölverarbeitende Industrie','Chemicals and Oil-Producing Industry')," +
					"(0,10,10,0,'Druck, Papier und Verpackungsindustrie','Printing, Paper and Packaging Industry')," +
					"(0,11,11,0,'Elektrotechnik, Feinmechanik und Optik','Electrical Engineering, Precision Mechanics and Optical Industry')," +
					"(0,12,12,0,'Energie-, Wasserversorgung und Entsorgung','Energy and Water Supply, Disposal')," +
					"(0,13,13,0,'Fahrzeugbau und -zulieferer','Car Manufacturing and Sub-Contractors')," +
					"(0,14,14,0,'Finanzdienstleistungen','Financial Services')," +
					"(0,15,15,0,'Freizeit, Touristik und Sport','Recreation, Tourism and Sports')," +
					"(0,16,16,0,'Gemeinnützig','Non-Profit Sector')," +
					"(0,17,17,0,'Gesundheit und soziale Dienste','Health and Social Services')," +
					"(0,18,18,0,'Glas/Keramik Herstellung und Verarbeitung','Glas/ Pottery Production and Manufacturing')," +
					"(0,19,19,0,'Groß- und Einzelhandel','Wholesale and Retail')," +
					"(0,20,20,0,'Handwerk','Crafts')," +
					"(0,21,21,0,'Holz- und Möbelindustrie','Timber and Furniture Industry')," +
					"(0,22,22,0,'Hotel, Gastronomie und Catering','Hotel Trade and Catering ')," +
					"(0,23,23,0,'Immobilien','Real Estate')," +
					"(0,24,24,0,'Internet-Dienstleistungen, Portale und Datenverarbeitung','Internet Services, Portals and Data Processing')," +
					"(0,25,25,0,'IT-Hardware','IT Hardware')," +
					"(0,26,26,0,'IT-Software ','IT Software')," +
					"(0,27,27,0,'Konsumgüter und Gebrauchsgüter','Consumer Goods and Hard Goods')," +
					"(0,28,28,0,'Kunst und Kultur','Arts and Culture')," +
					"(0,29,29,0,'Land-, Forst-, Fischwirtschaft und Gartenbau','Agriculture, Forestry, Fishing Industry, Horticulture')," +
					"(0,30,30,0,'Luft- und Raumfahrt','Aerospace Industry')," +
					"(0,31,31,0,'Maschinen- und Anlagenbau','Mechanical and Plant Engineering')," +
					"(0,32,32,0,'Medien (Film, Funk, TV, Verlage)','Media (Film, Radio, TV, Publishing)')," +
					"(0,33,33,0,'Medizintechnik','Medical Engineering')," +
					"(0,34,34,0,'Metallindustrie','Metal Industry')," +
					"(0,35,35,0,'Nahrungs- und Genußmittel','Foods and Fine Foods')," +
					"(0,36,36,0,'Öffentlicher Dienst und Verbände','Civil Service and Associations')," +
					"(0,37,37,0,'Personaldienstleistungen','Personnel Recruitment')," +
					"(0,38,38,0,'Schifffahrt','Shipping/ Navy')," +
					"(0,39,39,0,'Sicherheitsdienste','Security Agency')," +
					"(0,40,40,0,'Telekommunikation','Telecommunications')," +
					"(0,41,41,0,'Textilien, Bekleidung und Lederwaren','Textiles, Clothes and Leather Goods')," +
					"(0,42,42,0,'Transport und Logistik','Transport and Logistics')," +
					"(0,43,43,0,'Unternehmensberatung, Wirtschaftsprüfung und Recht','Consulting, Auditing and Law')," +
					"(0,44,44,0,'Vermietung und Leasing','Renting and Leasing')," +
					"(0,45,45,0,'Versicherungen','Insurance Industry')," +
					"(0,46,46,0,'Wissenschaft und Forschung','Science and Research')," +
					"(0,47,47,0,'Sonstige Branchen','Further Sectors')," +
					"(0,48,48,0,'Sonstige Dienstleistungen','Further Services')," +
					"(0,49,49,0,'Sonstiges produzierendes Gewerbe','Further Manufacturing Industries');";

			query = session.createSQLQuery(sectorQueryString);
			query.executeUpdate();

			query = session.createSQLQuery("CREATE TABLE `static_languages` (" +
					"`id` int(11) unsigned NOT NULL auto_increment," +
					"`lg_iso_2` char(2) collate utf8_unicode_ci default ''," +
					"`lg_name_en` varchar(50) collate utf8_unicode_ci default ''," +
					"PRIMARY KEY  (`id`)," +
					"UNIQUE KEY `uid` (`id`)," +
					") ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=191 ;");

			String languageQueryString = "INSERT INTO `static_languages` (`id`, `OPTLOCK`, `lg_iso_2`, `lg_name_en`) VALUES " +
					"(1, 0, 'AB', 'Abkhazian')," +
					"(2, 0, 'AA', 'Afar')," +
					"(3, 0, 'AF', 'Afrikaans')," +
					"(4, 0, 'SQ', 'Albanian')," +
					"(5, 0, 'AM', 'Amharic')," +
					"(6, 0, 'AR', 'Arabic')," +
					"(7, 0, 'HY', 'Armenian')," +
					"(8, 0, 'AS', 'Assamese')," +
					"(9, 0, 'AY', 'Aymara')," +
					"(10, 0, 'AZ', 'Azerbaijani')," +
					"(11, 0, 'BA', 'Bashkir')," +
					"(12, 0, 'EU', 'Basque')," +
					"(13, 0, 'BN', 'Bengali')," +
					"(14, 0, 'DZ', 'Dzongkha')," +
					"(15, 0, 'BH', 'Bihari')," +
					"(16, 0, 'BI', 'Bislama')," +
					"(17, 0, 'BR', 'Breton')," +
					"(18, 0, 'BG', 'Bulgarian')," +
					"(19, 0, 'MY', 'Burmese')," +
					"(20, 0, 'BE', 'Belarusian')," +
					"(21, 0, 'KM', 'Khmer')," +
					"(22, 0, 'CA', 'Catalan')," +
					"(23, 0, 'ZA', 'Zhuang')," +
					"(24, 0, 'ZH', 'Chinese (Traditional)')," +
					"(25, 0, 'CO', 'Corsican')," +
					"(26, 0, 'HR', 'Croatian')," +
					"(27, 0, 'CS', 'Czech')," +
					"(28, 0, 'DA', 'Danish')," +
					"(29, 0, 'NL', 'Dutch')," +
					"(30, 0, 'EN', 'English')," +
					"(31, 0, 'EO', 'Esperanto')," +
					"(32, 0, 'ET', 'Estonian')," +
					"(33, 0, 'FO', 'Faeroese')," +
					"(34, 0, 'FA', 'Persian')," +
					"(35, 0, 'FJ', 'Fijian')," +
					"(36, 0, 'FI', 'Finnish')," +
					"(37, 0, 'FR', 'French')," +
					"(38, 0, 'FY', 'Frisian')," +
					"(39, 0, 'GL', 'Galician')," +
					"(40, 0, 'GD', 'Scottish Gaelic')," +
					"(41, 0, 'GV', 'Manx')," +
					"(42, 0, 'KA', 'Georgian')," +
					"(43, 0, 'DE', 'German')," +
					"(44, 0, 'EL', 'Greek')," +
					"(45, 0, 'KL', 'Greenlandic')," +
					"(46, 0, 'GN', 'Guaraní')," +
					"(47, 0, 'GU', 'Gujarati')," +
					"(48, 0, 'HA', 'Hausa')," +
					"(49, 0, 'HE', 'Hebrew')," +
					"(50, 0, 'HI', 'Hindi')," +
					"(51, 0, 'HU', 'Hungarian')," +
					"(52, 0, 'IS', 'Icelandic')," +
					"(53, 0, 'ID', 'Indonesian')," +
					"(56, 0, 'IU', 'Inuktitut')," +
					"(57, 0, 'IK', 'Inupiaq')," +
					"(58, 0, 'GA', 'Irish')," +
					"(59, 0, 'IT', 'Italian')," +
					"(60, 0, 'JA', 'Japanese')," +
					"(62, 0, 'KN', 'Kannada')," +
					"(63, 0, 'KS', 'Kashmiri')," +
					"(64, 0, 'KK', 'Kazakh')," +
					"(65, 0, 'RW', 'Kinyarwanda')," +
					"(66, 0, 'KY', 'Kirghiz')," +
					"(67, 0, 'RN', 'Kirundi')," +
					"(68, 0, 'KO', 'Korean')," +
					"(69, 0, 'KU', 'Kurdish')," +
					"(70, 0, 'LO', 'Lao')," +
					"(72, 0, 'LV', 'Latvian')," +
					"(73, 0, 'LN', 'Lingala')," +
					"(74, 0, 'LT', 'Lithuanian')," +
					"(75, 0, 'MK', 'Macedonian')," +
					"(76, 0, 'MG', 'Malagasy')," +
					"(77, 0, 'MS', 'Malay')," +
					"(78, 0, 'ML', 'Malayalam')," +
					"(79, 0, 'MT', 'Maltese')," +
					"(80, 0, 'MI', 'Māori')," +
					"(81, 0, 'MR', 'Marathi')," +
					"(82, 0, 'MO', 'Moldavian')," +
					"(83, 0, 'MN', 'Mongolian')," +
					"(84, 0, 'NA', 'Nauru')," +
					"(85, 0, 'NE', 'Nepali')," +
					"(86, 0, 'NO', 'Norwegian')," +
					"(87, 0, 'OC', 'Occitan')," +
					"(88, 0, 'OR', 'Oriya')," +
					"(89, 0, 'OM', 'Oromo')," +
					"(90, 0, 'PS', 'Pashto')," +
					"(91, 0, 'PL', 'Polish')," +
					"(92, 0, 'PT', 'Portuguese')," +
					"(93, 0, 'PA', 'Punjabi')," +
					"(94, 0, 'QU', 'Quechua')," +
					"(95, 0, 'RM', 'Rhaeto-Romance')," +
					"(96, 0, 'RO', 'Romanian')," +
					"(97, 0, 'RU', 'Russian')," +
					"(98, 0, 'SM', 'Samoan')," +
					"(99, 0, 'SG', 'Sango')," +
					"(101, 0, 'SR', 'Serbian')," +
					"(103, 0, 'ST', 'Sesotho')," +
					"(104, 0, 'TN', 'Setswana')," +
					"(105, 0, 'SN', 'Shona')," +
					"(106, 0, 'SD', 'Sindhi')," +
					"(107, 0, 'SI', 'Sinhala')," +
					"(108, 0, 'SS', 'Swati')," +
					"(109, 0, 'SK', 'Slovak')," +
					"(110, 0, 'SL', 'Slovenian')," +
					"(111, 0, 'SO', 'Somali')," +
					"(112, 0, 'ES', 'Spanish')," +
					"(113, 0, 'SU', 'Sundanese')," +
					"(114, 0, 'SW', 'Swahili')," +
					"(115, 0, 'SV', 'Swedish')," +
					"(116, 0, 'TL', 'Tagalog')," +
					"(117, 0, 'TG', 'Tajik')," +
					"(118, 0, 'TA', 'Tamil')," +
					"(119, 0, 'TT', 'Tatar')," +
					"(120, 0, 'TE', 'Telugu')," +
					"(121, 0, 'TH', 'Thai')," +
					"(122, 0, 'BO', 'Tibetan')," +
					"(123, 0, 'TI', 'Tigrinya')," +
					"(124, 0, 'TO', 'Tongan')," +
					"(125, 0, 'TS', 'Tsonga')," +
					"(126, 0, 'TR', 'Turkish')," +
					"(127, 0, 'TK', 'Turkmen')," +
					"(128, 0, 'TW', 'Twi')," +
					"(129, 0, 'UG', 'Uyghur')," +
					"(130, 0, 'UK', 'Ukrainian')," +
					"(131, 0, 'UR', 'Urdu')," +
					"(132, 0, 'UZ', 'Uzbek')," +
					"(133, 0, 'VI', 'Vietnamese')," +
					"(135, 0, 'CY', 'Welsh')," +
					"(136, 0, 'WO', 'Wolof')," +
					"(137, 0, 'XH', 'Xhosa')," +
					"(139, 0, 'YO', 'Yoruba')," +
					"(140, 0, 'ZU', 'Zulu')," +
					"(141, 0, 'BS', 'Bosnian')," +
					"(143, 0, 'AK', 'Akan')," +
					"(144, 0, 'AN', 'Aragonese')," +
					"(145, 0, 'AV', 'Avar')," +
					"(146, 0, 'BM', 'Bambara')," +
					"(147, 0, 'CE', 'Chechen')," +
					"(148, 0, 'CH', 'Chamorro')," +
					"(149, 0, 'CR', 'Cree')," +
					"(151, 0, 'CV', 'Chuvash')," +
					"(152, 0, 'DV', 'Dhivehi')," +
					"(153, 0, 'EE', 'Ewe')," +
					"(154, 0, 'FF', 'Fula')," +
					"(155, 0, 'HO', 'Hiri motu')," +
					"(156, 0, 'HT', 'Haïtian Creole')," +
					"(157, 0, 'HZ', 'Herero')," +
					"(158, 0, 'IG', 'Igbo')," +
					"(159, 0, 'II', 'Yi')," +
					"(162, 0, 'KG', 'Kongo')," +
					"(163, 0, 'KI', 'Kikuyu')," +
					"(164, 0, 'KJ', 'Kuanyama')," +
					"(165, 0, 'KR', 'Kanuri')," +
					"(166, 0, 'KV', 'Komi')," +
					"(167, 0, 'KW', 'Cornish')," +
					"(168, 0, 'LB', 'Luxembourgish')," +
					"(169, 0, 'LG', 'Luganda')," +
					"(170, 0, 'LI', 'Limburgish')," +
					"(171, 0, 'LU', 'Luba-Katanga')," +
					"(172, 0, 'MH', 'Marshallese')," +
					"(173, 0, 'NB', 'Norwegian Bokmål')," +
					"(174, 0, 'ND', 'North Ndebele')," +
					"(175, 0, 'NG', 'Ndonga')," +
					"(176, 0, 'NN', 'Norwegian Nynorsk')," +
					"(177, 0, 'NR', 'South Ndebele')," +
					"(178, 0, 'NV', 'Navajo')," +
					"(179, 0, 'NY', 'Chichewa')," +
					"(180, 0, 'OJ', 'Ojibwa')," +
					"(181, 0, 'OS', 'Ossetic')," +
					"(183, 0, 'SC', 'Sardinian')," +
					"(184, 0, 'SE', 'Northern Sami')," +
					"(186, 0, 'TY', 'Tahitian')," +
					"(187, 0, 'VE', 'Venda')," +
					"(188, 0, 'WA', 'Walloon')," +
					"(161, 0, 'JV', 'Javanese')," +
					"(189, 0, 'PT', 'Brazilian Portuguese')," +
					"(190, 0, 'ZH', 'Chinese (Simplified)')," +
					"(54, 0, 'IA', 'Interlingua')," +
					"(55, 0, 'IE', 'Interlingue')," +
					"(71, 0, 'LA', 'Latin')," +
					"(100, 0, 'SA', 'Sanskrit')," +
					"(134, 0, 'VO', 'Volapük')," +
					"(138, 0, 'YI', 'Yiddish')," +
					"(142, 0, 'AE', 'Avestan')," +
					"(150, 0, 'CU', 'Church Slavonic')," +
					"(160, 0, 'IO', 'Ido')," +
					"(182, 0, 'PI', 'Pali');";

			query = session.createSQLQuery(languageQueryString);
			query.executeUpdate();

			query = session.createSQLQuery("DROP TABLE IF EXISTS `static_territories`;");
			query = session.createSQLQuery("CREATE TABLE `static_territories` (" +
												" `id` int(11) unsigned NOT NULL auto_increment," +
												" `pid` int(11) unsigned default '0'," +
												" `tr_iso_nr` int(11) unsigned default '0'," +
												" `tr_parent_iso_nr` int(11) unsigned default '0'," +
												" `tr_name_en` varchar(50) collate utf8_unicode_ci default ''," +
												" PRIMARY KEY  (`id`)," +
												" UNIQUE KEY `id` (`id`)" +
												"	) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");

			String territoryQueryString = "INSERT INTO `static_territories` (`OPTLOCK`,`id`,`tr_iso_nr`,`tr_parent_fk`,`tr_name_en`) VALUES" +

			"(0,1,2,NULL,'Africa')," +
							"(0,2,9,NULL,'Australia/Oceania')," +
							"(0,3,142,NULL,'Asia')," +
							"(0,4,150,NULL,'Europe')," +
							"(0,5,21,NULL,'Northern America')," +
							"(0,6,5,NULL,'South America');";

			query = session.createSQLQuery(territoryQueryString);
			query.executeUpdate();
			// query =
			// session.createSQLQuery("Delete From `static_countries`;");
			// query.executeUpdate();
			String countryQueryString = "INSERT INTO `static_countries` (`id`,`OPTLOCK`,`territory_fk`,`cn_iso_3`,`cn_iso_nr`,`cn_parent_tr_iso_nr`,`cn_official_name_local`,`cn_official_name_en`,`cn_capital`,`cn_tldomain`,`cn_currency_iso_3`,`cn_currency_iso_nr`,`cn_phone`,`cn_eu_member`,`cn_short_local`,`cn_short_en`,`cn_uno_member`) "
					+
					" VALUES"
					+
					"(1, 0, 4, 'AND', 20, 150, 'Principat d''Andorra', 'Principality of Andorra', 'Andorra la Vella', 'ad', 'EUR', 978, 376, 0, 'Andorra', 'Andorra', 1),"
					+
					"(2, 0, 3, 'ARE', 784, 142, 'الإمارات العربيّة المتّحدة', 'United Arab Emirates', 'Abu Dhabi', 'ae', 'AED', 784, 971, 0, 'الإمارات العربيّة المتّحدة', 'United Arab Emirates', 1),"
					+
					"(3, 0, 3, 'AFG', 4, 142, 'د افغانستان اسلامي دولت', 'Islamic Republic of Afghanistan', 'Kabul', 'af', 'AFN', 1, 93, 0, 'افغانستان', 'Afghanistan', 1),"
					+
					"(4, 0, 5, 'ATG', 28, 21, 'Antigua and Barbuda', 'Antigua and Barbuda', 'St John''s', 'ag', 'XCD', 951, 1268, 0, 'Antigua and Barbuda', 'Antigua and Barbuda', 1),"
					+
					"(5, 0, 5, 'AIA', 660, 21, 'Anguilla', 'Anguilla', 'The Valley', 'ai', 'XCD', 951, 1264, 0, 'Anguilla', 'Anguilla', 0),"
					+
					"(6, 0, 4, 'ALB', 8, 150, 'Republika e Shqipërisë', 'Republic of Albania', 'Tirana', 6, 'ALL', 8, 355, 0, 'Shqipëria', 'Albania', 1),"
					+
					"(7, 0, 4, 'ARM', 51, 150, 'Հայաստանի Հանրապետություն', 'Republic of Armenia', 'Yerevan', 'am', 'AMD', 51, 374, 0, 'Հայաստան', 'Armenia', 1),"
					+
					"(8, 0, 5, 'ANT', 530, 21, 'Nederlandse Antillen', 'Netherlands Antilles', 1, 2, 'ANG', 532, 599, 0, 'Nederlandse Antillen', 'Netherlands Antilles', 0),"
					+
					"(9, 0, 1, 'AGO', 24, 2, 'República de Angola', 'Republic of Angola', 'Luanda', 'ao', 'AOA', 973, 244, 0, 'Angola', 'Angola', 1),"
					+
					"(11, 0, 6, 'ARG', 32, 5, 'República Argentina', 'Argentine Republic', 'Buenos Aires', 'ar', 'ARS', 1, 54, 0, 'Argentina', 'Argentina', 1),"
					+
					"(12, 0, 2, 'ASM', 16, 9, 'Amerika Samoa', 'American Samoa', 'Pago Pago', 6, 'USD', 840, 685, 0, 'Amerika Samoa', 'American Samoa', 0),"
					+
					"(13, 0, 4, 'AUT', 40, 150, 'Republik Österreich', 'Republic of Austria', 'Vienna', 'at', 'EUR', 978, 43, 1, 'Österreich', 'Austria', 1),"
					+
					"(14, 0, 2, 'AUS', 36, 9, 'Commonwealth of Australia', 'Commonwealth of Australia', 'Canberra', 'au', 'AUD', 36, 61, 0, 'Australia', 'Australia', 1),"
					+
					"(15, 0, 5, 'ABW', 533, 21, 'Aruba', 'Aruba', 'Oranjestad', 'aw', 'AWG', 533, 297, 0, 'Aruba', 'Aruba', 0),"
					+
					"(16, 0, 4, 'AZE', 31, 150, 'Azərbaycan Respublikası', 'Republic of Azerbaijan', 'Baku', 'az', 'AZM', 31, 994, 0,'Azərbaycan', 'Azerbaijan', 1),"
					+
					"(17, 0, 4, 'BIH', 70, 150, 'Bosna i Hercegovina / Босна и Херцеговина', 'Bosnia and Herzegovina', 'Sarajevo', 'ba', 'BAM', 977, 387, 0, 'BiH/БиХ', 'Bosnia and Herzegovina', 1),"
					+
					"(18, 0, 5, 'BRB', 52, 21, 'Barbados', 'Barbados', 1, 'bb', 'BBD', 52, 1246, 0, 'Barbados', 'Barbados', 1),"
					+
					"(19, 0, 3, 'BGD', 50, 142, 'গনপ্রজাতন্ত্রী বাংলা', 'People’s Republic of Bangladesh', 'Dhaka', 'bd', 'BDT', 50, 880, 0,  'বাংলাদেশ', 'Bangladesh', 1),"
					+
					"(20, 0, 4, 'BEL', 56, 150, 'Koninkrijk België / Royaume de Belgique', 'Kingdom of Belgium', 'Brussels', 1, 'EUR', 978, 32, 1,'Belgique', 'Belgium', 1),"
					+
					"(21, 0, 1, 'BFA', 854, 2, 'Burkina Faso', 'Burkina Faso', 1, 'bf', 'XOF', 952, 226, 0,'Burkina', 'Burkina Faso', 1),"
					+
					"(22, 0, 4, 'BGR', 100, 150, 'Република България', 'Republic of Bulgaria', 'Sofia', 2, 'BGL', 100, 359, 0, 'България', 'Bulgaria', 1),"
					+
					"(23, 0, 3, 'BHR', 48, 142, 'مملكة البحرين', 'Kingdom of Bahrain', 'Manama', 'bh', 'BHD', 48, 973, 0, 'البحري', 'Bahrain', 1),"
					+
					"(24, 0, 1, 'BDI', 108, 2, 'Republika y''u Burundi', 'Republic of Burundi', 'Bujumbura', 'bi', 'BIF', 108, 257, 0, 'Burundi', 'Burundi', 1),"
					+
					"(25, 0, 1, 'BEN', 204, 2, 'République du Bénin', 'Republic of Benin', 1, 2, 'XOF', 952, 229, 0,'Bénin', 'Benin', 1),"
					+
					"(26, 0, 5, 'BMU', 60, 21, 'Bermuda', 'Bermuda', 'Hamilton', 'bm', 'BMD', 60, 1441, 0, 'Bermuda', 'Bermuda', 0),"
					+
					"(27, 0, 3, 'BRN', 96, 142, 'برني دارالسلام', 'Sultanate of Brunei', 'Bandar Seri Begawan', 'bn', 'BND', 96, 673, 0,  'دارالسلام', 'Brunei', 1),"
					+
					"(28, 0, 6, 'BOL', 68, 5, 'República de Bolivia', 'Republic of Bolivia', 'Sucre', 'bo', 'BOB', 68, 591, 0, 'Bolivia', 'Bolivia', 1),"
					+
					"(29, 0, 6, 'BRA', 76, 5, 'República Federativa do Brasil', 'Federative Republic of Brazil', 'Brasilia', 6, 'BRL', 2, 55, 0, 'Brasil', 'Brazil', 1),"
					+
					"(30, 0, 5, 'BHS', 44, 21, 'Commonwealth of The Bahamas', 'Commonwealth of The Bahamas', 'Nassau', 1, 'BSD', 44, 1242, 0,  'The Bahamas', 'The Bahamas', 1),"
					+
					"(31, 0, 3, 'BTN', 64, 142, 'Druk-Yul', 'Kingdom of Bhutan', 'Thimphu', 6, 'BTN', 64, 975, 0, 'Druk-Yul', 'Bhutan', 1),"
					+
					"(33, 0, 1, 'BWA', 72, 2, 'Republic of Botswana', 'Republic of Botswana', 1, 'bw', 'BWP', 72, 267, 0,'Botswana', 'Botswana', 1),"
					+
					"(34, 1, 4, 'BLR', 112, 150, 'Рэспубліка Беларусь', 'Republic of Belarus', 'Minsk', 6, 'BYR', 974, 375, 0, 'Беларусь', 'Belarus', 1),"
					+
					"(35, 0, 5, 'BLZ', 84, 21, 'Belize', 'Belize', 'Belmopan', 'bz', 'BZD', 84, 501, 0, 'Belize', 'Belize', 1),"
					+
					"(36, 0, 5, 'CAN', 124, 21, 'Canada', 'Canada', 'Ottawa', 'ca', 'CAD', 124, 1, 0, 'Canada', 'Canada', 1),"
					+
					"(37, 0, 2, 'CCK', 166, 9, 'Territory of Cocos (Keeling) Islands', 'Territory of Cocos (Keeling) Islands', 'Bantam', 1, 'AUD', 36, 6722, 0, 'Cocos (Keeling) Islands', 'Cocos (Keeling) Islands', 0),"
					+
					"(38, 0, 1, 'COD', 180, 2, 'République Démocratique du Congo', 'Democratic Republic of the Congo', 'Kinshasa', 'cd', 'CDF', 976, 0, 0,'Congo', 'Congo', 1),"
					+
					"(39, 0, 1, 'CAF', 140, 2, 'République Centrafricaine', 'Central African Republic', 'Bangui', 'cf', 'XAF', 950, 236, 0,'Centrafrique', 'Central African Republic', 1),"
					+
					"(40, 0, 1, 'COG', 178, 2, 'République du Congo', 'Republic of the Congo', 'Brazzaville', 1, 'XAF', 950, 242, 0, 'Congo-Brazzaville', 'Congo-Brazzaville', 1),"
					+
					"(41, 0, 4, 'CHE', 756, 150, 'Confédération suisse / Schweizerische Eidgenossenschaft', 'Swiss Confederation', 'Berne', 'ch', 'CHF', 756, 41, 0,  'Schweiz', 'Switzerland', 1),"
					+
					"(42, 0, 1, 'CIV', 384, 2, 'République de Côte d’Ivoire', 'Republic of Côte d''Ivoire', 'Yamoussoukro', 6, 'XOF', 1, 225, 0,'Côte d’Ivoire', 'Côte d’Ivoire', 1),"
					+
					"(43, 0, 2, 'COK', 184, 9, 'Cook Islands', 'Cook Islands', 'Avarua', 1, 'NZD', 554, 682, 0, 'Cook Islands', 'Cook Islands', 0),"
					+
					"(44, 0, 6, 'CHL', 152, 5, 'República de Chile', 'Republic of Chile', 1, 'cl', 'CLP', 152, 56, 0, 'Chile', 'Chile', 1),"
					+
					"(45, 0, 1, 'CMR', 120, 2, 'Republic of Cameroon / République du Cameroun', 'Republic of Cameroon', 'Yaoundé', 'cm', 'XAF', 950, 237, 0, 'Cameroun', 'Cameroon', 1),"
					+
					"(46, 0, 3, 'CHN', 156, 142, '中华人民共和国', 'People’s Republic of China', 'Beijing', 'cn', 'CNY', 156, 86, 0,'中华', 'China', 1),"
					+
					"(47, 0, 6, 'COL', 170, 5, 'República de Colombia', 'Republic of Colombia', 'Bogotá', 'co', 'COP', 170, 57, 0, 'Colombia', 'Colombia', 1),"
					+
					"(48, 0, 5, 'CRI', 188, 21, 'República de Costa Rica', 'Republic of Costa Rica', 'San José', 'cr', 'CRC', 188, 506, 0, 'Costa Rica', 'Costa Rica', 1),"
					+
					"(49, 0, 5, 'CUB', 192, 21, 'República de Cuba', 'Republic of Cuba', 1, 'cu', 'CUP', 192, 53, 0,'Cuba', 'Cuba', 1),"
					+
					"(50, 0, 1, 'CPV', 132, 2, 'República de Cabo Verde', 'Republic of Cape Verde', 1, 'cv', 'CVE', 132, 238, 0, 'Cabo Verde', 'Cape Verde', 1),"
					+
					"(51, 1, 2, 'CXR', 162, 9, 'Territory of Christmas Island', 'Territory of Christmas Island', 'Flying Fish Cove', 'cx', 'AUD', 36, 6724, 0, 'Christmas Island', 'Christmas Island', 0),"
					+
					"(52, 0, 3, 'CYP', 196, 142, 'Κυπριακή Δημοκρατία / Kıbrıs Cumhuriyeti', 'Republic of Cyprus', 'Nicosia', 'cy', 'CYP', 196, 357, 1,  'Κύπρος / Kıbrıs', 'Cyprus', 1),"
					+
					"(53, 0, 4, 'CZE', 203, 150, 'Česká republika', 'Czech Republic', 'Prague', 'cz', 'CZK', 203, 420, 1,  'Cesko', 'Czech Republic', 1),"
					+
					"(54, 0, 4, 'DEU', 276, 150, 'Bundesrepublik Deutschland', 'Federal Republic of Germany', 'Berlin', 2, 'EUR', 978, 49, 1, 'Deutschland', 'Germany', 1),"
					+
					"(55, 0, 2, 'DJI', 262, 2, 'جمهورية جيبوتي / République de Djibouti', 'Republic of Djibouti', 1, 'dj', 'DJF', 262, 253, 0,  'جيبوتي /Djibouti', 'Djibouti', 1),"
					+
					"(56, 0, 4, 'DNK', 208, 150, 'Kongeriget Danmark', 'Kingdom of Denmark', 'Copenhagen', 6, 'DKK', 208, 45, 1, 'Danmark', 'Denmark', 1),"
					+
					"(57, 1, 5, 'DMA', 212, 21, 'Commonwealth of Dominica', 'Commonwealth of Dominica', 'Roseau', 'dm', 'XCD', 951, 1767, 0,  'Dominica', 'Dominica', 1),"
					+
					"(58, 0, 5, 'DOM', 214, 21, 'República Dominicana', 'Dominican Republic', 'Santo Domingo', 2, 'DOP', 214, 1809, 0,  'Quisqueya', 'Dominican Republic', 1),"
					+
					"(59, 0, 1, 'DZA', 12, 2, 'الجمهورية الجزائرية الديمقراطية', 'People’s Democratic Republic of Algeria', 1, 'dz', 'DZD', 12, 213, 0, 'الجزائ', 'Algeria', 1),"
					+
					"(60, 0, 6, 'ECU', 218, 5, 'República del Ecuador', 'Republic of Ecuador', 'Quito', 'ec', 'USD', 840, 593, 0,  'Ecuador', 'Ecuador', 1),"
					+
					"(61, 0, 4, 'EST', 233, 150, 'Eesti Vabariik', 'Republic of Estonia', 'Tallinn', 1, 'EEK', 233, 372, 1, 'Eesti', 'Estonia', 1),"
					+
					"(62, 0, 1, 'EGY', 818, 2, 'جمهوريّة مصر العربيّة', 'Arab Republic of Egypt', 'Cairo', 'eg', 'EGP', 818, 20, 0, 'مصر', 'Egypt', 1),"
					+
					"(63, 0, 1, 'ESH', 732, 2, 'الصحراء الغربية', 'Western Sahara', 'El Aaiún', 'eh', 'MAD', 504, 0, 0,  'الصحراء الغربي', 'Western Sahara', 0),"
					+
					"(64, 0, 1, 'ERI', 232, 2, 'ሃግሬ ኤርትራ', 'State of Eritrea', 1, 'er', 'ERN', 232, 291, 0, 'ኤርትራ', 'Eritrea', 1),"
					+
					"(65, 0, 4, 'ESP', 724, 150, 'Reino de España', 'Kingdom of Spain', 'Madrid', 'es', 'EUR', 978, 34, 1,  'España', 'Spain', 1),"
					+
					"(66, 0, 1, 'ETH', 231, 2, 'የኢትዮጵያ ፌዴራላዊ', 'Federal Democratic Republic of Ethiopia', 'Addis Ababa', 'et', 'ETB', 230, 251, 0,  'ኢትዮጵያ', 'Ethiopia', 1),"
					+
					"(67, 0, 4, 'FIN', 246, 150, 'Suomen Tasavalta / Republiken Finland', 'Republic of Finland', 'Helsinki', 'fi', 'EUR', 978, 358, 1, 'Suomi', 'Finland', 1),"
					+
					"(68, 0, 2, 'FJI', 242, 9, 'Republic of the Fiji Islands / Matanitu Tu-Vaka-i-koya ko Vi', 'Republic of the Fiji Islands', 1, 2, 'FJD', 242, 679, 0, 'Viti', 'Fiji', 1),"
					+
					"(69, 0, 6, 'FLK', 238, 5, 'Falkland Islands', 'Falkland Islands', 'Stanley', 'fk', 'FKP', 238, 500, 0,  'Falkland Islands', 'Falkland Islands', 0),"
					+
					"(70, 0, 2, 'FSM', 583, 9, 'Federated States of Micronesia', 'Federated States of Micronesia', 'Palikir', 'fm', 'USD', 840, 691, 0, 'Micronesia', 'Micronesia', 1),"
					+
					"(71, 0, 4, 'FRO', 234, 150, 'Føroyar / Færøerne', 'Faroe Islands', 'Thorshavn', 'fo', 'DKK', 208, 298, 0,  'Føroyar / Færøerne', 'Faroes', 0),"
					+
					"(72, 0, 4, 'FRA', 250, 150, 'République française', 'French Republic', 'Paris', 'fr', 'EUR', 978, 33, 1,  'France', 'France', 1),"
					+
					"(73, 0, 1, 'GAB', 266, 2, 'République Gabonaise', 'Gabonese Republic', 'Libreville', 'ga', 'XAF', 950, 241, 0,  'Gabon', 'Gabon', 1),"
					+
					"(74, 0, 4, 'GBR', 826, 150, 'United Kingdom of Great Britain and Northern', 'United Kingdom of Great Britain and Northern', 'London', 'uk', 'GBP', 6, 44, 1,  'United Kingdom', 'United Kingdom', 1),"
					+
					"(75, 0, 5, 'GRD', 308, 21, 'Grenada', 'Grenada', 'St George''s', 'gd', 'XCD', 951, 1473, 0, 'Grenada', 'Grenada', 1),"
					+
					"(76, 0, 4, 'GEO', 268, 150, 'საქართველო', 'Georgia', 'Tbilisi', 6, 'GEL', 981, 995, 0,  'საქართველო', 'Georgia', 1),"
					+
					"(77, 0, 6, 'GUF', 254, 5, 'Guyane française', 'French Guiana', 'Cayenne', 'gf', 'EUR', 978, 594, 0,'Guyane française', 'French Guiana', 0),"
					+
					"(78, 0, 1, 'GHA', 288, 2, 'Republic of Ghana', 'Republic of Ghana', 'Accra', 'gh', 'GHC', 288, 233, 0,'Ghana', 'Ghana', 1),"
					+
					"(79, 0, 4, 'GIB', 292, 150, 'Gibraltar', 'Gibraltar', 1, 'gi', 'GIP', 292, 350, 0, 'Gibraltar', 'Gibraltar', 0),"
					+
					"(80, 0, 5, 'GRL', 304, 21, 'Kalaallit Nunaat / Grønland', 'Greenland', 'Nuuk', 2, 'DKK', 208, 299, 0, 'Grønland', 'Greenland', 0),"
					+
					"(81, 0, 1, 'GMB', 270, 2, 'Republic of The Gambia', 'Republic of The Gambia', 'Banjul', 'gm', 'GMD', 270, 220, 0,  'Gambia', 'Gambia', 1),"
					+
					"(82, 0, 1, 'GIN', 324, 2, 'République de Guinée', 'Republic of Guinea', 'Conakry', 'gn', 'GNF', 324, 224, 0,  'Guinée', 'Guinea', 1),"
					+
					"(83, 1, 5, 'GLP', 312, 21, 'Département de la Guadeloupe', 'Department of Guadeloupe', 'Basse Terre', 'gp', 'EUR', 978, 590, 0, 'Guadeloupe', 'Guadeloupe', 0),"
					+
					"(84, 0, 1, 'GNQ', 226, 2, 'República de Guinea Ecuatorial', 'Republic of Equatorial Guinea', 'Malabo', 'gq', 'XAF', 950, 240, 0,  'Guinea Ecuatorial', 'Equatorial Guinea', 1),"
					+
					"(85, 0, 4, 'GRC', 300, 150, 'Ελληνική Δημοκρατία', 'Hellenic Republic', 'Athens', 'gr', 'EUR', 978, 30, 1, 'Ελλάδα', 'Greece', 1),"
					+
					"(87, 0, 5, 'GTM', 320, 21, 'República de Guatemala', 'Republic of Guatemala', 'Guatemala City', 1, 'GTQ', 320, 502, 0,  'Guatemala', 'Guatemala', 1),"
					+
					"(88, 0, 2, 'GUM', 316, 9, 'The Territory of Guam / Guåhån', 'The Territory of Guam', 'Hagåtña', 'gu', 'USD', 840, 671, 0,'Guåhån', 'Guam', 0),"
					+
					"(89, 0, 1, 'GNB', 624, 2, 'República da Guiné-Bissau', 'Republic of Guinea-Bissau', 1, 6, 'XOF', 952, 245, 0,  'Guiné-Bissau', 'Guinea-Bissau', 1),"
					+
					"(90, 0, 6, 'GUY', 328, 5, 'Co-operative Republic of Guyana', 'Co-operative Republic of Guyana', 'Georgetown', 1, 'GYD', 328, 592, 0,  'Guyana', 'Guyana', 1),"
					+
					"(91, 0, 3, 'HKG', 344, 142, '香港特別行政區', 'Hong Kong SAR of the People’s Republic of China', '', 1, 'HKD', 344, 852, 0, '香港', 'Hong Kong SAR of China', 0),"
					+
					"(92, 0, 5, 'HND', 340, 21, 'República de Honduras', 'Republic of Honduras', 'Tegucigalpa', 'hn', 'HNL', 340, 504, 0, 'Honduras', 'Honduras', 1),"
					+
					"(93, 0, 4, 'HRV', 191, 150, 'Republika Hrvatska', 'Republic of Croatia', 'Zagreb', 6, 'HRK', 191, 385, 0,  'Hrvatska', 'Croatia', 1),"
					+
					"(94, 0, 5, 'HTI', 332, 21, 'Repiblik d Ayiti / République d''Haïti', 'Republic of Haiti', 1, 2, 'HTG', 332, 509, 0, 'Ayiti', 'Haiti', 1),"
					+
					"(95, 0, 4, 'HUN', 348, 150, 'Magyar Köztársaság', 'Republic of Hungary', 'Budapest', 'hu', 'HUF', 348, 36, 1, 'Magyarország', 'Hungary', 1),"
					+
					"(96, 0, 3, 'IDN', 360, 142, 'Republik Indonesia', 'Republic of Indonesia', 'Jakarta', 'id', 'IDR', 1, 62, 0, 'Indonesia', 'Indonesia', 1),"
					+
					"(97, 1, 4, 'IRL', 372, 150, 'Poblacht na hÉireann / Republic of Ireland', 'Republic of Ireland', 'Dublin', 'ie', 'EUR', 978, 353, 1, 'Éire', 'Ireland', 1),"
					+
					"(98, 0, 3, 'ISR', 376, 142, 'دولة إسرائيل / מדינת ישראלل', 'State of Israel', 'Tel Aviv', 'il', 'ILS', 1, 972, 0,  'ישראל', 'Israel', 1),"
					+
					"(99, 0, 3, 'IND', 356, 142, 'Bharat; Republic of India', 'Republic of India', 'New Delhi', 'in', 'INR', 1, 91, 0,  'India', 'India', 1),"
					+
					"(101, 0, 3, 'IRQ', 368, 142, 'الجمهورية العراقية', 'Republic of Iraq', 'Baghdad', 'iq', 'IQD', 368, 964, 0, 'العراق / عيَراق', 'Iraq', 1),"
					+
					"(102, 0, 3, 'IRN', 364, 142, 'جمهوری اسلامی ايران', 'Islamic Republic of Iran', 'Tehran', 'ir', 'IRR', 364, 98, 0, 'ايران', 'Iran', 1),"
					+
					"(103, 0, 4, 'ISL', 352, 150, 'Lýðveldið Ísland', 'Republic of Iceland', 1, 'is', 'ISK', 352, 354, 0, 'Ísland', 'Iceland', 1),"
					+
					"(104, 0, 4, 'ITA', 380, 150, 'Repubblica Italiana', 'Italian Republic', 'Rome', 2, 'EUR', 978, 39, 1,  'Italia', 'Italy', 1),"
					+
					"(105, 0, 5, 'JAM', 388, 21, 'Commonwealth of Jamaica', 'Commonwealth of Jamaica', 'Kingston', 'jm', 'JMD', 1, 1876, 0, 'Jamaica', 'Jamaica', 1),"
					+
					"(106, 0, 3, 'JOR', 400, 142, 'المملكة الأردنية الهاشمية', 'Hashemite Kingdom of Jordan', 'Amman', 1, 'JOD', 400, 962, 0, 'أردنّ', 'Jordan', 1),"
					+
					"(107, 0, 3, 'JPN', 392, 142, '日本国', 'Japan', 'Tokyo', 'jp', 'JPY', 1, 81, 0, '日本', 'Japan', 1),"
					+
					"(108, 0, 1, 'KEN', 404, 2, 'Jamhuri va Kenya', 'Republic of Kenia', 'Nairobi', 'ke', 'KES', 404, 254, 0,  'Kenya', 'Kenya', 1),"
					+
					"(109, 0, 3, 'KGZ', 417, 142, 'Кыргызстан', 'Kyrgyzstan', 'Bishkek', 'kg', 'KGS', 417, 7, 0,'Кыргызстан', 'Kyrgyzstan', 1),"
					+
					"(110, 0, 3, 'KHM', 116, 142, 'Preăh Réachéanachâkr Kâmpŭchea', 'Kingdom of Cambodia', 'Phnom Penh', 6, 'KHR', 116, 855, 0, 'Kâmpŭchea', 'Cambodia', 1),"
					+
					"(111, 0, 2, 'KIR', 296, 9, 'Republic of Kiribati', 'Republic of Kiribati', 'Bairiki', 'ki', 'AUD', 36, 686, 0, 'Kiribati', 'Kiribati', 1),"
					+
					"(112, 0, 1, 'COM', 174, 2, 'Udzima wa Komori /Union des Comores /اتحاد القمر', 'Union of the Comoros', 'Moroni', 2, 'KMF', 174, 269, 0, 'اتحاد القمر', 'Comoros', 1),"
					+
					"(113, 0, 5, 'KNA', 659, 21, 'Federation of Saint Kitts and Nevis', 'Federation of Saint Kitts and Nevis', 'Basseterre', 2, 'XCD', 951, 1869, 0,  'Saint Kitts and Nevis', 'Saint Kitts and Nevis', 1),"
					+
					"(114, 0, 3, 'PRK', 408, 142, '조선민주주의인민화국', 'Democratic People’s Republic of Korea', 'Pyongyang', 'kp', 'KPW', 408, 850, 0,  '북조선', 'North Korea', 1),"
					+
					"(115, 0, 3, 'KOR', 410, 142, '대한민국', 'Republic of Korea', 'Seoul', 1, 'KRW', 410, 82, 0,  '한국', 'South Korea', 1),"
					+
					"(116, 0, 3, 'KWT', 414, 142, 'دولة الكويت', 'State of Kuweit', 'Kuwait City', 6, 'KWD', 414, 965, 0,  'الكويت', 'Kuwait', 1),"
					+
					"(117, 0, 5, 'CYM', 136, 21, 'Cayman Islands', 'Cayman Islands', 'George Town', 6, 'KYD', 136, 1345, 0,  'Cayman Islands', 'Cayman Islands', 0),"
					+
					"(118, 0, 3, 'KAZ', 398, 142, 'Қазақстан Республикасы /Республика Казахстан', 'Republic of Kazakhstan', 'Astana', 'kz', 'KZT', 398, 7, 0,  'Қазақстан /Казахстан', 'Kazakhstan', 1),"
					+
					"(119, 0, 3, 'LAO', 418, 142, 'ສາທາລະນະລັດປະຊາທິປະໄຕປະຊາຊົນລາວ', 'Lao People’s Democratic Republic', 'Vientiane', 'la', 'LAK', 418, 856, 0, 'ເມືອງລາວ', 'Laos', 1),"
					+
					"(120, 0, 3, 'LBN', 422, 142, 'الجمهوريّة اللبنانيّة', 'Republic of Lebanon', 1, 'lb', 'LBP', 422, 961, 0,  'لبنان', 'Lebanon', 1),"
					+
					"(121, 1, 5, 'LCA', 662, 21, 'Saint Lucia', 'Saint Lucia', 'Castries', 'lc', 'XCD', 951, 1758, 0,  'Saint Lucia', 'Saint Lucia', 1),"
					+
					"(122, 0, 4, 'LIE', 438, 150, 'Fürstentum Liechtenstein', 'Principality of Liechtenstein', 'Vaduz', 'li', 'CHF', 756, 41, 0,  'Liechtenstein', 'Liechtenstein', 1),"
					+
					"(123, 0, 3, 'LKA', 144, 142, 'ශ්‍රී ලංකා / இலங்கை சனநாயக சோஷலிசக் குடியரசு', 'Democratic Socialist Republic of Sri Lanka', 'Colombo', 'lk', 'LKR', 1, 94, 0, 'ශ්‍රී ලංකා / இலங்கை', 'Sri Lanka', 1),"
					+
					"(124, 0, 1, 'LBR', 430, 2, 'Republic of Liberia', 'Republic of Liberia', 'Monrovia', 'lr', 'LRD', 430, 231, 0,  'Liberia', 'Liberia', 1),"
					+
					"(125, 0, 1, 'LSO', 426, 2, 'Muso oa Lesotho / Kingdom of Lesotho', 'Kingdon of Lesotho', 'Maseru', 'ls', 'LSL', 426, 266, 0, 'Lesotho', 'Lesotho', 1),"
					+
					"(126, 0, 4, 'LTU', 440, 150, 'Lietuvos Respublika', 'Republic of Lithuania', 'Vilnius', 'lt', 'LTL', 440, 370, 1, 'Lietuva', 'Lithuania', 1),"
					+
					"(127, 0, 4, 'LUX', 442, 150, 'Grand-Duché de Luxembourg / Großherzogtum Luxemburg / Groussherzogtum Lëtzebuerg', 'Grand Duchy of Luxembourg', 'Luxembourg', 1, 'EUR', 978, 352, 1, 'Luxemburg', 'Luxembourg', 1),"
					+
					"(128, 0, 4, 'LVA', 428, 150, 'Latvijas Republika', 'Republic of Latvia', 'Riga', 'lv', 'LVL', 428, 371, 1,  'Latvija', 'Latvia', 1),"
					+
					"(129, 0, 1, 'LBY', 434, 2, 'الجماهيرية العربية الليبية الشعبية الإشتراكية ﺍﻟﻌﻆﻤﻰ', 'Great Socialist People’s Libyan Arab Jamahiriya', 'Tripoli', 'ly', 'LYD', 434, 218, 0, 'الليبية', 'Libya', 1),"
					+
					"(130, 0, 1, 'MAR', 504, 2, 'المملكة المغربية', 'Kingdom of Morocco', 'Rabat', 1, 'MAD', 504, 212, 0, 'المغربية', 'Morocco', 1),"
					+
					"(131, 1, 4, 'MCO', 492, 150, 'Principauté de Monaco / Principatu de Munegu', 'Principality of Monaco', 'Monaco', 'mc', 'EUR', 978, 377, 0,  'Monaco', 'Monaco', 1),"
					+
					"(132, 0, 4, 'MDA', 498, 150, 'Republica Moldova', 'Republic of Moldova', 'Chisinau', 'md', 'MDL', 498, 373, 0,  'Moldova', 'Moldova', 1),"
					+
					"(133, 0, 1, 'MDG', 450, 2, 'Repoblikan''i Madagasikara / République de Madagascar', 'Republic of Madagascar', 2, 'mg', 'MGA', 969, 261, 0, 'Madagascar', 'Madagascar', 1),"
					+
					"(134, 0, 2, 'MHL', 584, 9, 'Aolepān Aorōkin M̧ajeļ / Republic of the Marshall Islands', 'Republic of the Marshall Islands', 'Dalap-Uliga-Darrit (DUD)', 1, 'USD', 840, 692, 0,  'Marshall Islands', 'Marshall Islands', 1),"
					+
					"(135, 0, 4, 'MKD', 807, 150, 'Република Македонија', 'Republic of Macedonia', 'Skopje', 2, 'MKD', 807, 389, 0,  'Македонија', 'Macedonia', 1),"
					+
					"(136, 0, 1, 'MLI', 466, 2, 'République du Mali', 'Republik Mali', 1, 'ml', 'XOF', 952, 223, 0, 'Mali', 'Mali', 1),"
					+
					"(137, 0, 3, 'MMR', 104, 142, 'Pyidaungzu Myanma Naingngandaw', 'Union of Myanmar', 'Yangon', 6, 'MMK', 104, 95, 0,  'Myanmar', 'Myanmar', 1),"
					+
					"(138, 0, 3, 'MNG', 496, 142, 'Монгол Улс', 'Mongolia', 'Ulan Bator', 'mn', 'MNT', 496, 976, 0, 'Монгол Улс', 'Mongolia', 1),"
					+
					"(139, 0, 3, 'MAC', 446, 142, '中華人民共和國澳門特別行政區 / Região Administrativa Especial de Macau da República Popular da China', 'Macao SAR of the People’s Republic of China', 'Macau', 'mo', 'MOP', 446, 853, 0,  '澳門 / Macau', 'Macao SAR of China', 0),"
					+
					"(140, 0, 2, 'MNP', 580, 9, 'Commonwealth of the Northern Mariana Islands', 'Commonwealth of the Northern Mariana Islands', 'Garapan', 'mp', 'USD', 840, 1670, 0,  'Northern Marianas', 'Northern Marianas', 0),"
					+
					"(141, 0, 5, 'MTQ', 474, 21, 'Département de la Martinique', 'Department of Martinique', 'Fort-de-France', 'mq', 'EUR', 978, 596, 0,  'Martinique', 'Martinique', 0),"
					+
					"(142, 0, 1, 'MRT', 478, 2, 'الجمهورية الإسلامية الموريتانية', 'Islamic Republic of Mauritania', 'Nouakchott', 1, 'MRO', 478, 222, 0,  'الموريتانية', 'Mauritania', 1),"
					+
					"(143, 0, 5, 'MSR', 500, 21, 'Montserrat', 'Montserrat', 'Plymouth', 'ms', 'XCD', 951, 1664, 0,  'Montserrat', 'Montserrat', 0),"
					+
					"(144, 0, 4, 'MLT', 470, 150, 'Repubblika ta'' Malta / Republic of Malta', 'Republic of Malta', 'Valletta', 'mt', 'MTL', 470, 356, 1,  'Malta', 'Malta', 1),"
					+
					"(145, 0, 1, 'MUS', 480, 2, 'Republic of Mauritius', 'Republic of Mauritius', 'Port Louis', 'mu', 'MUR', 480, 230, 0, 'Mauritius', 'Mauritius', 1),"
					+
					"(146, 0, 3, 'MDV', 462, 142, 'ދިވެހިރާއްޖޭގެ ޖުމުހޫރިއްޔާ', 'Republic of Maldives', 1, 'mv', 'MVR', 462, 960, 0,  'ޖުމުހޫރިއްޔ', 'Maldives', 1),"
					+
					"(147, 0, 1, 'MWI', 454, 2, 'Republic of Malawi / Dziko la Malaŵi', 'Republic of Malawi', 'Lilongwe', 6, 'MWK', 454, 265, 0,  'Malawi', 'Malawi', 1),"
					+
					"(148, 0, 5, 'MEX', 484, 21, 'Estados Unidos Mexicanos', 'United Mexican States', 'Mexico City', 1, 'MXN', 484, 52, 0,  'México', 'Mexico', 1),"
					+
					"(149, 0, 3, 'MYS', 458, 142, 'ڤرسكوتوان مليسيا', 'Malaysia', 'Kuala Lumpur', 'my', 'MYR', 458, 60, 0, 'مليسيا', 'Malaysia', 1),"
					+
					"(150, 0, 1, 'MOZ', 508, 2, 'República de Moçambique', 'Republic of Mozambique', 'Maputo', 'mz', 'MZM', 508, 258, 0, 'Moçambique', 'Mozambique', 1),"
					+
					"(151, 0, 1, 'NAM', 516, 2, 'Republic of Namibia', 'Republic of Namibia', 'Windhoek', 'na', 'NAD', 516, 264, 0,  'Namibia', 'Namibia', 1),"
					+
					"(152, 0, 2, 'NCL', 540, 9, 'Territoire de Nouvelle-Caledonie et Dépendances', 'Territory of New Caledonia', 'Nouméa', 'nc', 'XPF', 953, 687, 0, 'Nouvelle-Calédonie', 'New Caledonia', 0),"
					+
					"(153, 0, 1, 'NER', 562, 2, 'République du Niger', 'Republic of Niger', 1, 'ne', 'XOF', 952, 227, 0,  'Niger', 'Niger', 1),"
					+
					"(154, 0, 2, 'NFK', 574, 9, 'Territory of Norfolk Island', 'Territory of Norfolk Island', 'Kingston', 'nf', 'AUD', 36, 6723, 0, 'Norfolk Island', 'Norfolk Island', 0),"
					+
					"(155, 0, 1, 'NGA', 566, 2, 'Federal Republic of Nigeria', 'Federal Republic of Nigeria', 'Abuja', 'ng', 'NGN', 566, 234, 0, 'Nigeria', 'Nigeria', 1),"
					+
					"(156, 0, 5, 'NIC', 558, 21, 'República de Nicaragua', 'Republic of Nicaragua', 'Managua', 6, 'NIO', 558, 505, 0,  'Nicaragua', 'Nicaragua', 1),"
					+
					"(157, 0, 4, 'NLD', 528, 150, 'Koninkrijk der Nederlanden', 'Kingdom of the Netherlands', 'Amsterdam', 'nl', 'EUR', 978, 31, 1,  'Nederland', 'Netherlands', 1),"
					+
					"(158, 0, 4, 'NOR', 578, 150, 'Kongeriket Norge', 'Kingdom of Norway', 'Oslo', 'no', 'NOK', 578, 47, 0, 'Norge', 'Norway', 1),"
					+
					"(159, 0, 3, 'NPL', 524, 142, 'नेपाल अधिराज्य', 'Kingdom of Nepal', 'Kathmandu', 'np', 'NPR', 524, 977, 0, 'नेपाल', 'Nepal', 1),"
					+
					"(160, 0, 2, 'NRU', 520, 9, 'Ripublik Naoero', 'Republic of Nauru', 'Yaren', 'nr', 'AUD', 36, 674, 0, 'Naoero', 'Nauru', 1),"
					+
					"(161, 0, 2, 'NIU', 570, 9, 'Niue', 'Niue', 'Alofi', 'nu', 'NZD', 554, 683, 0,  'Niue', 'Niue', 0),"
					+
					"(162, 0, 2, 'NZL', 554, 9, 'New Zealand / Aotearoa', 'New Zealand', 'Wellington', 'nz', 'NZD', 1, 64, 0, 'New Zealand / Aotearoa', 'New Zealand', 1),"
					+
					"(163, 0, 3, 'OMN', 512, 142, 'سلطنة عُمان', 'Sultanate of Oman', 1, 'om', 'OMR', 512, 968, 0,  'عُمان', 'Oman', 1),"
					+
					"(164, 0, 5, 'PAN', 591, 21, 'República de Panamá', 'Repulic of Panama', 'Panama City', 'pa', 'PAB', 1, 507, 0, 'Panamá', 'Panama', 1),"
					+
					"(165, 0, 6, 'PER', 604, 5, 'República del Perú', 'Republic of Peru', 'Lima', 'pe', 'PEN', 1, 51, 0, 'Perú', 'Peru', 1),"
					+
					"(166, 0, 2, 'PYF', 258, 9, 'Polynésie française', 'French Polynesia', 'Papeete', 2, 'XPF', 953, 689, 0,  'Polynésie française', 'French Polynesia', 0),"
					+
					"(167, 0, 2, 'PNG', 598, 9, 'Independent State of Papua New Guinea / Papua Niugini', 'Independent State of Papua New Guinea', 'Port Moresby', 6, 'PGK', 598, 675, 0,  'Papua New Guinea  / Papua Niugini', 'Papua New Guinea', 1),"
					+
					"(168, 0, 3, 'PHL', 608, 142, 'Republika ng Pilipinas / Republic of the Philippines', 'Republic of the Philippines', 'Manila', 'ph', 'PHP', 1, 63, 0, 'Philippines', 'Philippines', 1),"
					+
					"(169, 0, 3, 'PAK', 586, 142, 'Islamic Republic of Pakistan / اسلامی جمہوریۂ پاکستان', 'Islamic Republic of Pakistan', 'Islamabad', 1, 'PKR', 586, 92, 0,  'پاکستان', 'Pakistan', 1),"
					+
					"(170, 0, 4, 'POL', 616, 150, 'Rzeczpospolita Polska', 'Republic of Poland', 6, 'pl', 'PLN', 985, 48, 1,  'Polska', 'Poland', 1),"
					+
					"(171, 0, 5, 'SPM', 666, 21, 'Saint-Pierre-et-Miquelon', 'Saint Pierre and Miquelon', 'Saint-Pierre', 'pm', 'EUR', 978, 508, 0,  'Saint-Pierre-et-Miquelon', 'Saint Pierre and Miquelon', 0),"
					+
					"(172, 1, 2, 'PCN', 612, 9, 'Pitcairn Islands', 'Pitcairn Islands', 'Adamstown', 'pn', 'NZD', 554, 0, 0, 'Pitcairn Islands', 'Pitcairn Islands', 0),"
					+
					"(173, 0, 5, 'PRI', 630, 21, 'Estado Libre Asociado de Puerto Rico / Commonwealth of Puerto Rico', 'Commonwealth of Puerto Rico', 'San Juan', 'pr', 'USD', 1, 1787, 0,  'Puerto Rico', 'Puerto Rico', 0),"
					+
					"(174, 0, 4, 'PRT', 620, 150, 'República Portuguesa', 'Portuguese Republic', 'Lisbon', 'pt', 'EUR', 978, 351, 1,  'Portugal', 'Portugal', 1),"
					+
					"(175, 6, 2, 'PLW', 585, 9, 'Belu''u era Belau / Republic of Palau', 'Republic of Palau', 'Koror', 'pw', 'USD', 840, 680, 0,  'Belau / Palau', 'Palau', 1),"
					+
					"(176, 0, 6, 'PRY', 600, 5, 'República del Paraguay / Tetä Paraguáype', 'Republic of Paraguay', 'Asunción', 6, 'PYG', 600, 595, 0,  'Paraguay', 'Paraguay', 1),"
					+
					"(177, 0, 3, 'QAT', 634, 142, 'دولة قطر', 'State of Qatar', 'Doha', 'qa', 'QAR', 634, 974, 0,  'قطر', 'Qatar', 1),"
					+
					"(178, 0, 1, 'REU', 638, 2, 'Département de la Réunion', 'Department of Réunion', 'Saint-Denis', 1, 'EUR', 978, 262, 0, 'Réunion', 'Reunion', 0),"
					+
					"(179, 0, 4, 'ROU', 642, 150, 'România', 'Romania', 1, 'ro', 'ROL', 642, 40, 0,  'România', 'Romania', 1),"
					+
					"(180, 0, 4, 'RUS', 643, 150, 'Российская Федерация', 'Russian Federation', 'Moscow', 'ru', 'RUB', 643, 7, 0, 'Росси́я', 'Russia', 1),"
					+
					"(181, 0, 1, 'RWA', 646, 2, 'Repubulika y''u Rwanda / République Rwandaise', 'Republic of Rwanda', 'Kigali', 'rw', 'RWF', 646, 250, 0,'Rwanda', 'Rwanda', 1),"
					+
					"(182, 0, 3, 'SAU', 682, 142, 'المملكة العربية السعودية', 'Kingdom of Saudi Arabia', 'Riyadh', 'sa', 'SAR', 1, 966, 0,  'السعودية', 'Saudi Arabia', 1),"
					+
					"(183, 0, 2, 'SLB', 90, 9, 'Solomon Islands', 'Solomon Islands', 'Honiara', 'sb', 'SBD', 90, 677, 0,  'Solomon Islands', 'Solomon Islands', 1),"
					+
					"(184, 0, 1, 'SYC', 690, 2, 'Repiblik Sesel / Republic of Seychelles / République des Seychelles', 'Republic of Seychelles', 'Victoria', 'sc', 'SCR', 690, 248, 0, 'Seychelles', 'Seychelles', 1),"
					+
					"(185, 0, 1, 'SDN', 736, 2, 'جمهورية السودان', 'Republic of the Sudan', 'Khartoum', 2, 'SDD', 736, 249, 0, 'السودان', 'Sudan', 1),"
					+
					"(186, 0, 4, 'SWE', 752, 150, 'Konungariket Sverige', 'Kingdom of Sweden', 1, 'se', 'SEK', 752, 46, 1, 'Sverige', 'Sweden', 1),"
					+
					"(187, 0, 3, 'SGP', 702, 142, 'Republic of Singapore / 新加坡共和国 / Republik Singapura / சிங்கப்பூர் குடியரசு', 'Republic of Singapore', 'Singapore', 6, 'SGD', 1, 65, 0,  'Singapore', 'Singapore', 1),"
					+
					"(188, 0, 1, 'SHN', 654, 2, 'Saint Helena', 'Saint Helena', 'Jamestown', 'sh', 'SHP', 654, 290, 0,  'Saint Helena', 'Saint Helena', 0),"
					+
					"(189, 0, 4, 'SVN', 705, 150, 'Republika Slovenija', 'Republic of Slovenia', 6, 'si', 'SIT', 705, 386, 1,  'Slovenija', 'Slovenia', 1),"
					+
					"(190, 0, 4, 'SJM', 744, 150, 'Svalbard', 'Svalbard', 'Longyearbyen', 'sj', 'NOK', 578, 47, 0,  'Svalbard', 'Svalbard', 0),"
					+
					"(191, 0, 4, 'SVK', 703, 150, 'Slovenská republika', 'Slovak Republic', 'Bratislava', 5, 'SKK', 703, 421, 1, 'Slovensko', 'Slovakia', 1),"
					+
					"(192, 0, 1, 'SLE', 694, 2, 'Republic of Sierra Leone', 'Republic of Sierra Leone', 'Freetown', 1, 'SLL', 694, 232, 0,  'Sierra Leone', 'Sierra Leone', 1),"
					+
					"(193, 0, 4, 'SMR', 674, 150, 'Serenissima Repubblica di San Marino', 'Most Serene Republic of San Marino', 'San Marino', 'sm', 'EUR', 978, 378, 0,  'San Marino', 'San Marino', 1),"
					+
					"(194, 0, 1, 'SEN', 686, 2, 'République de Sénégal', 'Republic of Senegal', 1, 5, 'XOF', 952, 221, 0, 'Sénégal', 'Senegal', 1),"
					+
					"(195, 0, 1, 'SOM', 706, 2, 'Soomaaliya', 'Somalia', 'Mogadishu', 1, 'SOS', 706, 252, 0,  'Soomaaliya', 'Somalia', 1),"
					+
					"(196, 0, 6, 'SUR', 740, 5, 'Republiek Suriname', 'Republic of Surinam', 'Paramaribo', 'sr', 'SRD', 968, 597, 0, 'Suriname', 'Suriname', 1),"
					+
					"(197, 0, 1, 'STP', 678, 2, 'República Democrática de São Tomé e Príncipe', 'Democratic Republic of São Tomé e Príncipe', 'São Tomé', 2, 'STD', 678, 239, 0,  'São Tomé e Príncipe', 'São Tomé e Príncipe', 1),"
					+
					"(198, 0, 5, 'SLV', 222, 21, 'República de El Salvador', 'Republic of El Salvador', 1, 'sv', 'SVC', 222, 503, 0,  'El Salvador', 'El Salvador', 1),"
					+
					"(199, 0, 3, 'SYR', 760, 142, 'الجمهوريّة العربيّة السّوريّة', 'Syrian Arab Republic', 'Damascus', 'sy', 'SYP', 760, 963, 0,  'سوري', 'Syria', 1),"
					+
					"(200, 0, 1, 'SWZ', 748, 2, 'Umboso weSwatini / Kingdom of Swaziland', 'Kingdom of Swaziland', 'Mbabane', 'sz', 'SZL', 748, 268, 0,'weSwatini', 'Swaziland', 1),"
					+
					"(201, 0, 5, 'TCA', 796, 21, 'Turks and Caicos Islands', 'Turks and Caicos Islands', 'Cockburn Town', 2, 'USD', 840, 1649, 0, 'Turks and Caicos Islands', 'Turks and Caicos Islands', 0),"
					+
					"(202, 0, 1, 'TCD', 148, 2, 'جمهورية تشاد / République du Tchad', 'Republic of Chad', 'N''Djamena', 6, 'XAF', 950, 235, 0,  'تشاد / Tchad', 'Chad', 1),"
					+
					"(10, 0, 2, 'ATA', 10, 29, 'Antarctica', 'Antarctica', '', 1, '', 0, 67212, 0, 'Antarctica', 'Antarctica', 0),"
					+
					"(204, 0, 1, 'TGO', 768, 2, 'République Togolaise', 'Republic of Togo', 1, 'tg', 'XOF', 952, 228, 0,  'Togo', 'Togo', 1),"
					+
					"(205, 0, 3, 'THA', 764, 142, 'ราชอาณาจักรไทย', 'Kingdom of Thailand', 'Bangkok', 'th', 'THB', 1, 66, 0, 'ไทย', 'Thailand', 1),"
					+
					"(206, 0, 3, 'TJK', 762, 142, 'Ҷумҳурии Тоҷикистон', 'Republic of Tajikistan', 1, 'tj', 'TJS', 972, 7, 0,  'Тоҷикистон', 'Tajikistan', 1),"
					+
					"(207, 1, 2, 'TKL', 772, 9, 'Tokelau', 'Tokelau', 'Fakaofo', 'tk', 'NZD', 554, 0, 0,  'Tokelau', 'Tokelau', 0),"
					+
					"(208, 0, 3, 'TKM', 795, 142, 'Türkmenistan Jumhuriyäti', 'Republic of Turkmenistan', 6, 'tm', 'TMM', 795, 7, 0, 'Türkmenistan', 'Turkmenistan', 1),"
					+
					"(209, 0, 1, 'TUN', 788, 2, 'الجمهورية التونسية', 'Republic of Tunisia', 'Tunis', 'tn', 'TND', 788, 216, 0,  'التونسية', 'Tunisia', 1),"
					+
					"(210, 0, 2, 'TON', 776, 9, 'Pule''anga Fakatu''i ''o Tonga / Kingdom of Tonga', 'Kingdom of Tonga', 'Nuku''alofa', 'to', 'TOP', 776, 676, 0,  'Tonga', 'Tonga', 1),"
					+
					"(211, 0, 3, 'TLS', 626, 142, 'Repúblika Demokrátika Timor Lorosa''e / República Democrática de Timor-Leste', 'Democratic Republic of Timor-Leste', 'Dili', 'tp', 'TPE', 626, 670, 0,  'Timor Lorosa''e', 'Timor-Leste', 1),"
					+
					"(212, 0, 3, 'TUR', 792, 142, 'Türkiye Cumhuriyeti', 'Republic of Turkey', 2, 'tr', 'TRY', 949, 90, 0,  'Türkiye', 'Turkey', 1),"
					+
					"(213, 0, 5, 'TTO', 780, 21, 'Republic of Trinidad and Tobago', 'Republic of Trinidad and Tobago', 'Port of Spain', 'tt', 'TTD', 780, 1868, 0, 'Trinidad and Tobago', 'Trinidad and Tobago', 1),"
					+
					"(214, 0, 2, 'TUV', 798, 9, 'Tuvalu', 'Tuvalu', 'Fongafale', 'tv', 'AUD', 36, 688, 0,  'Tuvalu', 'Tuvalu', 1),"
					+
					"(215, 0, 3, 'TWN', 158, 142, '中華民國', 'Republic of China', 'Taipei', 'tw', 'TWD', 901, 886, 0,  '中華', 'Taiwan', 0),"
					+
					"(216, 0, 1, 'TZA', 834, 2, 'Jamhuri ya Muungano wa Tanzania', 'United Republic of Tanzania', 'Dodoma', 6, 'TZS', 834, 255, 0,  'Tanzania', 'Tanzania', 1),"
					+
					"(217, 0, 4, 'UKR', 804, 150, 'Україна', 'Ukraine', 'Kiev', 'ua', 'UAH', 980, 380, 0,  'Україна', 'Ukraine', 1),"
					+
					"(218, 0, 1, 'UGA', 800, 2, 'Republic of Uganda', 'Republic of Uganda', 'Kampala', 'ug', 'UGX', 800, 256, 0, 'Uganda', 'Uganda', 1),"
					+
					"(242, 1, 4, 'TUR', 792, 150, 'Türkiye Cumhuriyeti', 'Republic of Turkey', 2, 'tr', 'TRY', 949, 90, 0, 'Türkiye', 'Turkey', 0),"
					+
					"(243, 0, 3, 'EGY', 818, 142, 'جمهوريّة مصر العربيّة', 'Arab Republic of Egypt', 'Cairo', 'eg', 'EGP', 818, 0, 1, 'مصر', 'Egypt', 1),"
					+
					"(220, 0, 5, 'USA', 840, 21, 'United States of America', 'United States of America', 'Washington DC', 'us', 'USD', 840, 1, 0,  'United States', 'United States', 1),"
					+
					"(221, 0, 6, 'URY', 858, 5, 'República Oriental del Uruguay', 'Eastern Republic of Uruguay', 'Montevideo', 'uy', 'UYU', 858, 598, 0,  'Uruguay', 'Uruguay', 1),"
					+
					"(222, 0, 3, 'UZB', 860, 142, 'O‘zbekiston Respublikasi', 'Republic of Uzbekistan', 'Tashkent', 'uz', 'UZS', 860, 7, 0, 'O‘zbekiston', 'Uzbekistan', 1),"
					+
					"(223, 0, 4, 'VAT', 336, 150, 'Status Civitatis Vaticanae / Città del Vaticano', 'Vatican City', 'Vatican City', 'va', 'EUR', 978, 396, 0,  'Vaticano', 'Vatican City', 0),"
					+
					"(224, 0, 5, 'VCT', 670, 21, 'Saint Vincent and the Grenadines', 'Saint Vincent and the Grenadines', 'Kingstown', 'vc', 'XCD', 951, 1784, 0, 'Saint Vincent and the Grenadines', 'Saint Vincent and the Grenadines', 1),"
					+
					"(225, 0, 6, 'VEN', 862, 5, 'República Bolivariana de Venezuela', 'Bolivarian Republic of Venezuela', 1, 've', 'VEB', 862, 58, 0, 'Venezuela', 'Venezuela', 1),"
					+
					"(226, 1, 5, 'VGB', 92, 21, 'British Virgin Islands', 'British Virgin Islands', 'Road Town', 'vg', 'USD', 840, 1284, 0, 'British Virgin Islands', 'British Virgin Islands', 0),"
					+
					"(227, 0, 5, 'VIR', 850, 21, 'United States Virgin Islands', 'United States Virgin Islands', 'Charlotte Amalie', 'vi', 'USD', 840, 1340, 0,  'US Virgin Islands', 'US Virgin Islands', 0),"
					+
					"(228, 0, 3, 'VNM', 704, 142, 'Cộng Hòa Xã Hội Chủ Nghĩa Việt Nam', 'Socialist Republic of Vietnam', 'Hanoi', 'vn', 'VND', 704, 84, 0, 'Việt Nam', 'Vietnam', 1),"
					+
					"(229, 0, 2, 'VUT', 548, 9, 'Ripablik blong Vanuatu / Republic of Vanuatu / République du Vanuatu', 'Republic of Vanuatu', 'Port Vila', 'vu', 'VUV', 548, 678, 0,  'Vanuatu', 'Vanuatu', 1),"
					+
					"(230, 0, 2, 'WLF', 876, 9, 'Territoire de Wallis et Futuna', 'Territory of Wallis and Futuna Islands', 'Mata-Utu', 'wf', 'XPF', 953, 681, 0,  'Wallis and Futuna', 'Wallis and Futuna', 0),"
					+
					"(231, 0, 2, 'WSM', 882, 9, 'Malo Sa''oloto Tuto''atasi o Samoa / Independent State of Samoa', 'Independent State of Samoa', 1, 6, 'WST', 882, 685, 0, 'Samoa', 'Samoa', 1),"
					+
					"(232, 0, 3, 'YEM', 887, 142, 'الجمهوريّة اليمنية', 'Republic of Yemen', 'San''a', 'ye', 'YER', 886, 967, 0,  'اليمنية', 'Yemen', 1),"
					+
					"(233, 6, 1, 'MYT', 175, 2, 'Mayotte', 'Mayotte', 'Mamoudzou', 2, 'EUR', 978, 269, 0,  'Mayotte', 'Mayotte', 0),"
					+
					"(235, 0, 1, 'ZAF', 710, 2, 'Republic of South Africa / Republiek van Suid-Afrika / Rephaboliki ya Afrika-Borwa', 'Republic of South Africa', 'Pretoria', 'za', 'ZAR', 1, 27, 0, 'Afrika-Borwa', 'South Africa', 1),"
					+
					"(236, 0, 1, 'ZMB', 894, 2, 'Republic of Zambia', 'Republic of Zambia', 'Lusaka', 'zm', 'ZMK', 894, 260, 0, 'Zambia', 'Zambia', 1),"
					+
					"(237, 0, 1, 'ZWE', 716, 2, 'Republic of Zimbabwe', 'Republic of Zimbabwe', 'Harare', 'zw', 'ZWD', 716, 263, 0,  'Zimbabwe', 'Zimbabwe', 1),"
					+
					"(238, 6, 3, 'PSE', 275, 142, 'Palestinian territories', 'Palestinian territories', '', 'ps', '0', 0, 0, 0,  'Palestine', 'Palestine', 0),"
					+
					"(239, 0, 4, 'CSG', 891, 150, 'Државна заједница Србија и Црна Гора', 'State Union of Serbia and Montenegro', 'Belgrade', 'cs', 'CSD', 891, 381, 0,  'Србија и Црна Гора', 'Serbia and Montenegro', 1),"
					+
					"(240, 0, 4, 'ALA', 248, 150, 'Åland Islands', 'Åland Islands', 'Mariehamn', 'fi', 'EUR', 978, 35818, 1,  'Åland Islands', 'Åland Islands', 0),"
					+
					"(241, 0, 2, 'HMD', 334, 9, 'Heard Island and McDonald Islands', 'Heard Island and McDonald Islands', '', '', 'AUD', 36, 0, 0, 'Heard Island and McDonald Islands', 'Heard Island and McDonald Islands', 0);";

			query = session.createSQLQuery(countryQueryString);
			query.executeUpdate();
			//
			// ""(0,1,5,'AD','AND',20,39,'Principat d\\'Andorra','Principality
			// of Andorra','Andorra la
			// Vella','ad','EUR',978,376,0,'Andorra','Andorra',1),"+"+

			// ""(0,2,4,'AE','ARE',784,0,'الإمارات العربيّة المتّحدة','United
			// Arab Emirates','Abu Dhabi','ae','AED',784,971,0,'الإمارات
			// العربيّة المتّحدة','United Arab Emirates',1),"+"+
			// ""(0,3,4,'AF','AFG',4,34,'د افغانستان اسلامي دولت','Islamic
			// Republic of
			// Afghanistan','Kabul','af','AFN',971,93,0,'افغانستان','Afghanistan',1),"+"+
			// ""(0,4,3,'AG','ATG',28,29,'Antigua and Barbuda','Antigua and
			// Barbuda','St John\\'s','ag','XCD',951,1268,0,'Antigua and
			// Barbuda','Antigua and Barbuda',1),"+"+
			// ""(0,5,3,'AI','AIA',660,29,'Anguilla','Anguilla','The
			// Valley','ai','XCD',951,1264,0,'Anguilla','Anguilla',0),"+"+
			// ""(0,6,5,'AL','ALB',8,39,'Republika e Shqipërisë','Republic of
			// Albania','Tirana','al','ALL',8,355,0,'Shqipëria','Albania',1),"+"+
			// ""(0,7,4,'AM','ARM',51,172,'Հայաստանի Հանրապետություն','Republic
			// of
			// Armenia','Yerevan','am','AMD',51,374,0,'Հայաստան','Armenia',1),"+"+
			// ""(0,8,3,'AN','ANT',530,29,'Nederlandse Antillen','Netherlands
			// Antilles','Willemstad','an','ANG',532,599,0,'Nederlandse
			// Antillen','Netherlands Antilles',0),"+"+
			// ""(0,9,1,'AO','AGO',24,17,'República de Angola','Republic of
			// Angola','Luanda','ao','AOA',973,244,0,'Angola','Angola',1),"+"+
			// ""(0,10,6,'AQ','ATA',10,0,'Antarctica','Antarctica','','aq','',0,67212,0,'Antarctica','Antarctica',0),"+"+
			// ""(0,11,3,'AR','ARG',32,5,'República Argentina','Argentine
			// Republic','Buenos
			// Aires','ar','ARS',32,54,0,'Argentina','Argentina',1),"+"+
			// ""(0,12,2,'AS','ASM',16,61,'Amerika Samoa','American Samoa','Pago
			// Pago','as','USD',840,685,0,'Amerika Samoa','American
			// Samoa',0),"+"+
			// ""(0,13,5,'AT','AUT',40,155,'Republik Österreich','Republic of
			// Austria','Vienna','at','EUR',978,43,1,'Österreich','Austria',1),"+"+
			// ""(0,14,2,'AU','AUS',36,53,'Commonwealth of
			// Australia','Commonwealth of
			// Australia','Canberra','au','AUD',36,61,0,'Australia','Australia',1),"+"+
			// ""(0,15,3,'AW','ABW',533,29,'Aruba','Aruba','Oranjestad','aw','AWG',533,297,0,'Aruba','Aruba',0),"+"+
			// ""(0,16,4,'AZ','AZE',31,172,'Azərbaycan Respublikası','Republic
			// of
			// Azerbaijan','Baku','az','AZM',31,994,0,'Azərbaycan','Azerbaijan',1),"+"+
			// ""(0,17,5,'BA','BIH',70,39,'Bosna i Hercegovina / Босна и
			// Херцеговина','Bosnia and
			// Herzegovina','Sarajevo','ba','BAM',977,387,0,'BiH/БиХ','Bosnia
			// and Herzegovina',1),"+"+
			// ""(0,18,3,'BB','BRB',52,29,'Barbados','Barbados','Bridgetown','bb','BBD',52,1246,0,'Barbados','Barbados',1),"+"+
			// ""(0,19,4,'BD','BGD',50,34,'গনপ্রজাতন্ত্রী বাংলা','People’s
			// Republic of
			// Bangladesh','Dhaka','bd','BDT',50,880,0,'বাংলাদেশ','Bangladesh',1),"+"+
			// ""(0,20,5,'BE','BEL',56,155,'Koninkrijk België / Royaume de
			// Belgique','Kingdom of
			// Belgium','Brussels','be','EUR',978,32,1,'Belgique','Belgium',1),"+"+
			// ""(0,21,1,'BF','BFA',854,11,'Burkina Faso','Burkina
			// Faso','Ouagadougou','bf','XOF',952,226,0,'Burkina','Burkina
			// Faso',1),"+"+
			// ""(0,22,5,'BG','BGR',100,151,'Република България','Republic of
			// Bulgaria','Sofia','bg','BGL',100,359,0,'България','Bulgaria',1),"+"+
			// ""(0,23,4,'BH','BHR',48,145,'مملكة البحرين','Kingdom of
			// Bahrain','Manama','bh','BHD',48,973,0,'البحري','Bahrain',1),"+"+
			// ""(0,24,1,'BI','BDI',108,14,'Republika y\\'u Burundi','Republic
			// of
			// Burundi','Bujumbura','bi','BIF',108,257,0,'Burundi','Burundi',1),"+"+
			// ""(0,25,1,'BJ','BEN',204,11,'République du Bénin','Republic of
			// Benin','Porto Novo','bj','XOF',952,229,0,'Bénin','Benin',1),"+"+
			// ""(0,26,3,'BM','BMU',60,21,'Bermuda','Bermuda','Hamilton','bm','BMD',60,1441,0,'Bermuda','Bermuda',0),"+"+
			// ""(0,27,4,'BN','BRN',96,35,'برني دارالسلام','Sultanate of
			// Brunei','Bandar Seri
			// Begawan','bn','BND',96,673,0,'دارالسلام','Brunei',1),"+"+
			// ""(0,28,3,'BO','BOL',68,5,'República de Bolivia','Republic of
			// Bolivia','Sucre','bo','BOB',68,591,0,'Bolivia','Bolivia',1),"+"+
			// ""(0,29,3,'BR','BRA',76,5,'República Federativa do
			// Brasil','Federative Republic of
			// Brazil','Brasilia','br','BRL',986,55,0,'Brasil','Brazil',1),"+"+
			// ""(0,30,3,'BS','BHS',44,29,'Commonwealth of The
			// Bahamas','Commonwealth of The
			// Bahamas','Nassau','bs','BSD',44,1242,0,'The Bahamas','The
			// Bahamas',1),"+"+
			// ""(0,31,4,'BT','BTN',64,34,'Druk-Yul','Kingdom of
			// Bhutan','Thimphu','bt','BTN',64,975,0,'Druk-Yul','Bhutan',1),"+"+
			// ""(0,32,6,'BV','BVT',74,0,'Bouvet Island','Bouvet
			// Island','','bv','NOK',578,0,0,'Bouvet Island','Bouvet
			// Island',0),"+"+
			// ""(0,33,1,'BW','BWA',72,18,'Republic of Botswana','Republic of
			// Botswana','Gaborone','bw','BWP',72,267,0,'Botswana','Botswana',1),"+"+
			// ""(0,34,5,'BY','BLR',112,172,'Рэспубліка Беларусь','Republic of
			// Belarus','Minsk','by','BYR',974,375,0,'Беларусь','Belarus',1),"+"+
			// ""(0,35,3,'BZ','BLZ',84,13,'Belize','Belize','Belmopan','bz','BZD',84,501,0,'Belize','Belize',1),"+"+
			// ""(0,36,3,'CA','CAN',124,21,'Canada','Canada','Ottawa','ca','CAD',124,1,0,'Canada','Canada',1),"+"+
			// ""(0,37,2,'CC','CCK',166,53,'Territory of Cocos (Keeling)
			// Islands','Territory of Cocos (Keeling)
			// Islands','Bantam','cc','AUD',36,6722,0,'Cocos (Keeling)
			// Islands','Cocos (Keeling) Islands',0),"+"+
			// ""(0,38,1,'CD','COD',180,17,'République Démocratique du
			// Congo','Democratic Republic of the
			// Congo','Kinshasa','cd','CDF',976,0,0,'Congo','Congo',1),"+"+
			// ""(0,39,1,'CF','CAF',140,17,'République Centrafricaine','Central
			// African
			// Republic','Bangui','cf','XAF',950,236,0,'Centrafrique','Central
			// African Republic',1),"+"+
			// ""(0,40,1,'CG','COG',178,17,'République du Congo','Republic of
			// the
			// Congo','Brazzaville','cg','XAF',950,242,0,'Congo-Brazzaville','Congo-Brazzaville',1),"+"+
			// ""(0,41,5,'CH','CHE',756,155,'Confédération suisse /
			// Schweizerische Eidgenossenschaft','Swiss
			// Confederation','Berne','ch','CHF',756,41,0,'Schweiz','Switzerland',1),"+"+
			// ""(0,42,1,'CI','CIV',384,11,'République de Côte
			// d’Ivoire','Republic of Côte
			// d\\'Ivoire','Yamoussoukro','ci','XOF',952,225,0,'Côte
			// d’Ivoire','Côte d’Ivoire',1),"+"+
			// ""(0,43,2,'CK','COK',184,61,'Cook Islands','Cook
			// Islands','Avarua','ck','NZD',554,682,0,'Cook Islands','Cook
			// Islands',0),"+"+
			// ""(0,44,3,'CL','CHL',152,5,'República de Chile','Republic of
			// Chile','Santiago','cl','CLP',152,56,0,'Chile','Chile',1),"+"+
			// ""(0,45,1,'CM','CMR',120,17,'Republic of Cameroon / République du
			// Cameroun','Republic of
			// Cameroon','Yaoundé','cm','XAF',950,237,0,'Cameroun','Cameroon',1),"+"+
			// ""(0,46,4,'CN','CHN',156,30,'中华人民共和国','People’s Republic of
			// China','Beijing','cn','CNY',156,86,0,'中华','China',1),"+"+
			// ""(0,47,3,'CO','COL',170,5,'República de Colombia','Republic of
			// Colombia','Bogotá','co','COP',170,57,0,'Colombia','Colombia',1),"+"+
			// ""(0,48,3,'CR','CRI',188,13,'República de Costa Rica','Republic
			// of Costa Rica','San José','cr','CRC',188,506,0,'Costa
			// Rica','Costa Rica',1),"+"+
			// ""(0,49,3,'CU','CUB',192,29,'República de Cuba','Republic of
			// Cuba','Havana','cu','CUP',192,53,0,'Cuba','Cuba',1),"+"+
			// ""(0,50,1,'CV','CPV',132,11,'República de Cabo Verde','Republic
			// of Cape Verde','Praia','cv','CVE',132,238,0,'Cabo Verde','Cape
			// Verde',1),"+"+
			// ""(0,51,2,'CX','CXR',162,0,'Territory of Christmas
			// Island','Territory of Christmas Island','Flying Fish
			// Cove','cx','AUD',36,6724,0,'Christmas Island','Christmas
			// Island',0),"+"+
			// ""(0,52,4,'CY','CYP',196,145,'Κυπριακή Δημοκρατία / Kıbrıs
			// Cumhuriyeti','Republic of
			// Cyprus','Nicosia','cy','CYP',196,357,1,'Κύπρος /
			// Kıbrıs','Cyprus',1),"+"+
			// ""(0,53,5,'CZ','CZE',203,151,'Česká republika','Czech
			// Republic','Prague','cz','CZK',203,420,1,'Cesko','Czech
			// Republic',1),"+"+
			// ""(0,54,5,'DE','DEU',276,155,'Bundesrepublik
			// Deutschland','Federal Republic of
			// Germany','Berlin','de','EUR',978,49,1,'Deutschland','Germany',1),"+"+
			// ""(0,55,1,'DJ','DJI',262,14,'جمهورية جيبوتي / République de
			// Djibouti','Republic of
			// Djibouti','Djibouti','dj','DJF',262,253,0,'جيبوتي
			// /Djibouti','Djibouti',1),"+"+
			// ""(0,56,5,'DK','DNK',208,154,'Kongeriget Danmark','Kingdom of
			// Denmark','Copenhagen','dk','DKK',208,45,1,'Danmark','Denmark',1),"+"+
			// ""(0,57,3,'DM','DMA',212,29,'Commonwealth of
			// Dominica','Commonwealth of
			// Dominica','Roseau','dm','XCD',951,1767,0,'Dominica','Dominica',1),"+"+
			// ""(0,58,3,'DO','DOM',214,29,'República Dominicana','Dominican
			// Republic','Santo
			// Domingo','do','DOP',214,1809,0,'Quisqueya','Dominican
			// Republic',1),"+"+
			// ""(0,59,1,'DZ','DZA',12,15,'الجمهورية الجزائرية
			// الديمقراطية','People’s Democratic Republic of
			// Algeria','Algiers','dz','DZD',12,213,0,'الجزائ','Algeria',1),"+"+
			// ""(0,60,3,'EC','ECU',218,5,'República del Ecuador','Republic of
			// Ecuador','Quito','ec','USD',840,593,0,'Ecuador','Ecuador',1),"+"+
			// ""(0,61,5,'EE','EST',233,154,'Eesti Vabariik','Republic of
			// Estonia','Tallinn','ee','EEK',233,372,1,'Eesti','Estonia',1),"+"+
			// ""(0,62,1,'EG','EGY',818,15,'جمهوريّة مصر العربيّة','Arab
			// Republic of
			// Egypt','Cairo','eg','EGP',818,20,0,'مصر','Egypt',1),"+"+
			// ""(0,63,1,'EH','ESH',732,15,'الصحراء الغربية','Western
			// Sahara','El Aaiún','eh','MAD',504,0,0,'الصحراء الغربي','Western
			// Sahara',0),"+"+
			// ""(0,64,1,'ER','ERI',232,14,'ሃግሬ ኤርትራ','State of
			// Eritrea','Asmara','er','ERN',232,291,0,'ኤርትራ','Eritrea',1),"+"+
			// ""(0,65,5,'ES','ESP',724,39,'Reino de España','Kingdom of
			// Spain','Madrid','es','EUR',978,34,1,'España','Spain',1),"+"+
			// ""(0,66,1,'ET','ETH',231,14,'የኢትዮጵያ ፌዴራላዊ','Federal Democratic
			// Republic of Ethiopia','Addis
			// Ababa','et','ETB',230,251,0,'ኢትዮጵያ','Ethiopia',1),"+"+
			// ""(0,67,5,'FI','FIN',246,154,'Suomen Tasavalta / Republiken
			// Finland','Republic of
			// Finland','Helsinki','fi','EUR',978,358,1,'Suomi','Finland',1),"+"+
			// ""(0,68,2,'FJ','FJI',242,54,'Republic of the Fiji Islands /
			// Matanitu Tu-Vaka-i-koya ko Vi','Republic of the Fiji
			// Islands','Suva','fj','FJD',242,679,0,'Viti','Fiji',1),"+"+
			// ""(0,69,3,'FK','FLK',238,5,'Falkland Islands','Falkland
			// Islands','Stanley','fk','FKP',238,500,0,'Falkland
			// Islands','Falkland Islands',0),"+"+
			// ""(0,70,2,'FM','FSM',583,57,'Federated States of
			// Micronesia','Federated States of
			// Micronesia','Palikir','fm','USD',840,691,0,'Micronesia','Micronesia',1),"+"+
			// ""(0,71,5,'FO','FRO',234,154,'Føroyar / Færøerne','Faroe
			// Islands','Thorshavn','fo','DKK',208,298,0,'Føroyar /
			// Færøerne','Faroes',0),"+"+
			// ""(0,72,5,'FR','FRA',250,155,'République française','French
			// Republic','Paris','fr','EUR',978,33,1,'France','France',1),"+"+
			// ""(0,73,1,'GA','GAB',266,17,'République Gabonaise','Gabonese
			// Republic','Libreville','ga','XAF',950,241,0,'Gabon','Gabon',1),"+"+
			// ""(0,74,5,'GB','GBR',826,154,'United Kingdom of Great Britain and
			// Northern','United Kingdom of Great Britain and
			// Northern','London','uk','GBP',826,44,1,'United Kingdom','United
			// Kingdom',1),"+"+
			// ""(0,75,3,'GD','GRD',308,29,'Grenada','Grenada','St
			// George\\'s','gd','XCD',951,1473,0,'Grenada','Grenada',1),"+"+
			// ""(0,76,4,'GE','GEO',268,172,'საქართველო','Georgia','Tbilisi','ge','GEL',981,995,0,'საქართველო','Georgia',1),"+"+
			// ""(0,77,3,'GF','GUF',254,5,'Guyane française','French
			// Guiana','Cayenne','gf','EUR',978,594,0,'Guyane française','French
			// Guiana',0),"+"+
			// ""(0,78,1,'GH','GHA',288,11,'Republic of Ghana','Republic of
			// Ghana','Accra','gh','GHC',288,233,0,'Ghana','Ghana',1),"+"+
			// ""(0,79,5,'GI','GIB',292,39,'Gibraltar','Gibraltar','Gibraltar','gi','GIP',292,350,0,'Gibraltar','Gibraltar',0),"+"+
			// ""(0,80,3,'GL','GRL',304,21,'Kalaallit Nunaat /
			// Grønland','Greenland','Nuuk','gl','DKK',208,299,0,'Grønland','Greenland',0),"+"+
			// ""(0,81,1,'GM','GMB',270,11,'Republic of The Gambia','Republic of
			// The
			// Gambia','Banjul','gm','GMD',270,220,0,'Gambia','Gambia',1),"+"+
			// ""(0,82,1,'GN','GIN',324,11,'République de Guinée','Republic of
			// Guinea','Conakry','gn','GNF',324,224,0,'Guinée','Guinea',1),"+"+
			// ""(0,83,3,'GP','GLP',312,29,'Département de la
			// Guadeloupe','Department of Guadeloupe','Basse
			// Terre','gp','EUR',978,590,0,'Guadeloupe','Guadeloupe',0),"+"+
			// ""(0,84,1,'GQ','GNQ',226,17,'República de Guinea
			// Ecuatorial','Republic of Equatorial
			// Guinea','Malabo','gq','XAF',950,240,0,'Guinea
			// Ecuatorial','Equatorial Guinea',1),"+"+
			// ""(0,85,5,'GR','GRC',300,39,'Ελληνική Δημοκρατία','Hellenic
			// Republic','Athens','gr','EUR',978,30,1,'Ελλάδα','Greece',1),"+"+
			// ""(0,86,3,'GS','SGS',239,0,'South Georgia and the South Sandwich
			// Islands','South Georgia and the South Sandwich
			// Islands','','gs','',0,0,0,'South Georgia and the South Sandwich
			// Islands','South Georgia and the South Sandwich Islands',0),"+"+
			// ""(0,87,3,'GT','GTM',320,13,'República de Guatemala','Republic of
			// Guatemala','Guatemala
			// City','gt','GTQ',320,502,0,'Guatemala','Guatemala',1),"+"+
			// ""(0,88,2,'GU','GUM',316,57,'The Territory of Guam / Guåhån','The
			// Territory of
			// Guam','Hagåtña','gu','USD',840,671,0,'Guåhån','Guam',0),"+"+
			// ""(0,89,1,'GW','GNB',624,11,'República da Guiné-Bissau','Republic
			// of
			// Guinea-Bissau','Bissau','gw','XOF',952,245,0,'Guiné-Bissau','Guinea-Bissau',1),"+"+
			// ""(0,90,3,'GY','GUY',328,5,'Co-operative Republic of
			// Guyana','Co-operative Republic of
			// Guyana','Georgetown','gy','GYD',328,592,0,'Guyana','Guyana',1),"+"+
			// ""(0,91,4,'HK','HKG',344,30,'香港特別行政區','Hong Kong SAR of the
			// People’s Republic of China','','hk','HKD',344,852,0,'香港','Hong
			// Kong SAR of China',0),"+"+
			// ""(0,92,3,'HN','HND',340,13,'República de Honduras','Republic of
			// Honduras','Tegucigalpa','hn','HNL',340,504,0,'Honduras','Honduras',1),"+"+
			// ""(0,93,5,'HR','HRV',191,39,'Republika Hrvatska','Republic of
			// Croatia','Zagreb','hr','HRK',191,385,0,'Hrvatska','Croatia',1),"+"+
			// ""(0,94,3,'HT','HTI',332,29,'Repiblik d Ayiti / République
			// d\\'Haïti','Republic of
			// Haiti','Port-au-Prince','ht','HTG',332,509,0,'Ayiti','Haiti',1),"+"+
			// ""(0,95,5,'HU','HUN',348,151,'Magyar Köztársaság','Republic of
			// Hungary','Budapest','hu','HUF',348,36,1,'Magyarország','Hungary',1),"+"+
			// ""(0,96,4,'ID','IDN',360,35,'Republik Indonesia','Republic of
			// Indonesia','Jakarta','id','IDR',360,62,0,'Indonesia','Indonesia',1),"+"+
			// ""(0,97,5,'IE','IRL',372,154,'Poblacht na hÉireann / Republic of
			// Ireland','Republic of
			// Ireland','Dublin','ie','EUR',978,353,1,'Éire','Ireland',1),"+"+
			// ""(0,98,4,'IL','ISR',376,145,'دولة إسرائيل / מדינת ישראלل','State
			// of Israel','Tel
			// Aviv','il','ILS',376,972,0,'ישראל','Israel',1),"+"+
			// ""(0,99,4,'IN','IND',356,34,'Bharat; Republic of India','Republic
			// of India','New Delhi','in','INR',356,91,0,'India','India',1),"+"+
			// ""(0,100,4,'IO','IOT',86,0,'British Indian Ocean
			// Territory','British Indian Ocean
			// Territory','','io','',0,0,0,'British Indian Ocean
			// Territory','British Indian Ocean Territory',0),"+"+
			// ""(0,101,4,'IQ','IRQ',368,145,'الجمهورية العراقية','Republic of
			// Iraq','Baghdad','iq','IQD',368,964,0,'العراق /
			// عيَراق','Iraq',1),"+"+
			// ""(0,102,4,'IR','IRN',364,34,'جمهوری اسلامی ايران','Islamic
			// Republic of
			// Iran','Tehran','ir','IRR',364,98,0,'ايران','Iran',1),"+"+
			// ""(0,103,5,'IS','ISL',352,154,'Lýðveldið Ísland','Republic of
			// Iceland','Reykjavík','is','ISK',352,354,0,'Ísland','Iceland',1),"+"+
			// ""(0,104,5,'IT','ITA',380,39,'Repubblica Italiana','Italian
			// Republic','Rome','it','EUR',978,39,1,'Italia','Italy',1),"+"+
			// ""(0,105,3,'JM','JAM',388,29,'Commonwealth of
			// Jamaica','Commonwealth of
			// Jamaica','Kingston','jm','JMD',388,1876,0,'Jamaica','Jamaica',1),"+"+
			// ""(0,106,4,'JO','JOR',400,145,'المملكة الأردنية
			// الهاشمية','Hashemite Kingdom of
			// Jordan','Amman','jo','JOD',400,962,0,'أردنّ','Jordan',1),"+"+
			// ""(0,107,4,'JP','JPN',392,30,'日本国','Japan','Tokyo','jp','JPY',392,81,0,'日本','Japan',1),"+"+
			// ""(0,108,1,'KE','KEN',404,14,'Jamhuri va Kenya','Republic of
			// Kenia','Nairobi','ke','KES',404,254,0,'Kenya','Kenya',1),"+"+
			// ""(0,109,4,'KG','KGZ',417,143,'Кыргызстан','Kyrgyzstan','Bishkek','kg','KGS',417,7,0,'Кыргызстан','Kyrgyzstan',1),"+"+
			// ""(0,110,4,'KH','KHM',116,35,'Preăh Réachéanachâkr
			// Kâmpŭchea','Kingdom of Cambodia','Phnom
			// Penh','kh','KHR',116,855,0,'Kâmpŭchea','Cambodia',1),"+"+
			// ""(0,111,2,'KI','KIR',296,57,'Republic of Kiribati','Republic of
			// Kiribati','Bairiki','ki','AUD',36,686,0,'Kiribati','Kiribati',1),"+"+
			// ""(0,112,1,'KM','COM',174,14,'Udzima wa Komori /Union des Comores
			// /اتحاد القمر','Union of the
			// Comoros','Moroni','km','KMF',174,269,0,'اتحاد
			// القمر','Comoros',1),"+"+
			// ""(0,113,3,'KN','KNA',659,29,'Federation of Saint Kitts and
			// Nevis','Federation of Saint Kitts and
			// Nevis','Basseterre','kn','XCD',951,1869,0,'Saint Kitts and
			// Nevis','Saint Kitts and Nevis',1),"+"+
			// ""(0,114,4,'KP','PRK',408,30,'조선민주주의인민화국','Democratic People’s
			// Republic of Korea','Pyongyang','kp','KPW',408,850,0,'북조선','North
			// Korea',1),"+"+
			// ""(0,115,4,'KR','KOR',410,30,'대한민국','Republic of
			// Korea','Seoul','kr','KRW',410,82,0,'한국','South Korea',1),"+"+
			// ""(0,116,4,'KW','KWT',414,145,'دولة الكويت','State of
			// Kuweit','Kuwait
			// City','kw','KWD',414,965,0,'الكويت','Kuwait',1),"+"+
			// ""(0,117,3,'KY','CYM',136,29,'Cayman Islands','Cayman
			// Islands','George Town','ky','KYD',136,1345,0,'Cayman
			// Islands','Cayman Islands',0),"+"+
			// ""(0,118,4,'KZ','KAZ',398,143,'Қазақстан Республикасы /Республика
			// Казахстан','Republic of
			// Kazakhstan','Astana','kz','KZT',398,7,0,'Қазақстан
			// /Казахстан','Kazakhstan',1),"+"+
			// ""(0,119,4,'LA','LAO',418,35,'ສາທາລະນະລັດປະຊາທິປະໄຕປະຊາຊົນລາວ','Lao
			// People’s Democratic
			// Republic','Vientiane','la','LAK',418,856,0,'ເມືອງລາວ','Laos',1),"+"+
			// ""(0,120,4,'LB','LBN',422,145,'الجمهوريّة اللبنانيّة','Republic
			// of
			// Lebanon','Beirut','lb','LBP',422,961,0,'لبنان','Lebanon',1),"+"+
			// ""(0,121,3,'LC','LCA',662,29,'Saint Lucia','Saint
			// Lucia','Castries','lc','XCD',951,1758,0,'Saint Lucia','Saint
			// Lucia',1),"+"+
			// ""(0,122,5,'LI','LIE',438,155,'Fürstentum
			// Liechtenstein','Principality of
			// Liechtenstein','Vaduz','li','CHF',756,41,0,'Liechtenstein','Liechtenstein',1),"+"+
			// ""(0,123,4,'LK','LKA',144,34,'ශ්‍රී ලංකා / இலங்கை சனநாயக சோஷலிசக்
			// குடியரசு','Democratic Socialist Republic of Sri
			// Lanka','Colombo','lk','LKR',144,94,0,'ශ්‍රී ලංකා / இலங்கை','Sri
			// Lanka',1),"+"+
			// ""(0,124,1,'LR','LBR',430,11,'Republic of Liberia','Republic of
			// Liberia','Monrovia','lr','LRD',430,231,0,'Liberia','Liberia',1),"+"+
			// ""(0,125,1,'LS','LSO',426,18,'Muso oa Lesotho / Kingdom of
			// Lesotho','Kingdon of
			// Lesotho','Maseru','ls','LSL',426,266,0,'Lesotho','Lesotho',1),"+"+
			// ""(0,126,5,'LT','LTU',440,154,'Lietuvos Respublika','Republic of
			// Lithuania','Vilnius','lt','LTL',440,370,1,'Lietuva','Lithuania',1),"+"+
			// ""(0,127,5,'LU','LUX',442,155,'Grand-Duché de Luxembourg /
			// Großherzogtum Luxemburg / Groussherzogtum Lëtzebuerg','Grand
			// Duchy of
			// Luxembourg','Luxembourg','lu','EUR',978,352,1,'Luxemburg','Luxembourg',1),"+"+
			// ""(0,128,5,'LV','LVA',428,154,'Latvijas Republika','Republic of
			// Latvia','Riga','lv','LVL',428,371,1,'Latvija','Latvia',1),"+"+
			// ""(0,129,1,'LY','LBY',434,15,'الجماهيرية العربية الليبية الشعبية
			// الإشتراكية ﺍﻟﻌﻆﻤﻰ','Great Socialist People’s Libyan Arab
			// Jamahiriya','Tripoli','ly','LYD',434,218,0,'الليبية','Libya',1),"+"+
			// ""(0,130,1,'MA','MAR',504,15,'المملكة المغربية','Kingdom of
			// Morocco','Rabat','ma','MAD',504,212,0,'المغربية','Morocco',1),"+"+
			// ""(0,131,5,'MC','MCO',492,155,'Principauté de Monaco / Principatu
			// de Munegu','Principality of
			// Monaco','Monaco','mc','EUR',978,377,0,'Monaco','Monaco',1),"+"+
			// ""(0,132,5,'MD','MDA',498,172,'Republica Moldova','Republic of
			// Moldova','Chisinau','md','MDL',498,373,0,'Moldova','Moldova',1),"+"+
			// ""(0,133,1,'MG','MDG',450,14,'Repoblikan\\\'i Madagasikara /
			// République de Madagascar','Republic of
			// Madagascar','Antananarivo','mg','MGA',969,261,0,'Madagascar','Madagascar',1),"+"+
			// ""(0,134,2,'MH','MHL',584,57,'Aolepān Aorōkin M̧ajeļ / Republic
			// of the Marshall Islands','Republic of the Marshall
			// Islands','Dalap-Uliga-Darrit
			// (DUD)','mh','USD',840,692,0,'Marshall Islands','Marshall
			// Islands',1),"+"+
			// ""(0,135,5,'MK','MKD',807,39,'Република Македонија','Republic of
			// Macedonia','Skopje','mk','MKD',807,389,0,'Македонија','Macedonia',1),"+"+
			// ""(0,136,1,'ML','MLI',466,11,'République du Mali','Republik
			// Mali','Bamako','ml','XOF',952,223,0,'Mali','Mali',1),"+"+
			// ""(0,137,4,'MM','MMR',104,35,'Pyidaungzu Myanma
			// Naingngandaw','Union of
			// Myanmar','Yangon','mm','MMK',104,95,0,'Myanmar','Myanmar',1),"+"+
			// ""(0,138,4,'MN','MNG',496,30,'Монгол Улс','Mongolia','Ulan
			// Bator','mn','MNT',496,976,0,'Монгол Улс','Mongolia',1),"+"+
			// ""(0,139,4,'MO','MAC',446,30,'中華人民共和國澳門特別行政區 / Região
			// Administrativa Especial de Macau da República Popular da
			// China','Macao SAR of the People’s Republic of
			// China','Macau','mo','MOP',446,853,0,'澳門 / Macau','Macao SAR of
			// China',0),"+"+
			// ""(0,140,2,'MP','MNP',580,57,'Commonwealth of the Northern
			// Mariana Islands','Commonwealth of the Northern Mariana
			// Islands','Garapan','mp','USD',840,1670,0,'Northern
			// Marianas','Northern Marianas',0),"+"+
			// ""(0,141,3,'MQ','MTQ',474,29,'Département de la
			// Martinique','Department of
			// Martinique','Fort-de-France','mq','EUR',978,596,0,'Martinique','Martinique',0),"+"+
			// ""(0,142,1,'MR','MRT',478,11,'الجمهورية الإسلامية
			// الموريتانية','Islamic Republic of
			// Mauritania','Nouakchott','mr','MRO',478,222,0,'الموريتانية','Mauritania',1),"+"+
			// ""(0,143,3,'MS','MSR',500,29,'Montserrat','Montserrat','Plymouth','ms','XCD',951,1664,0,'Montserrat','Montserrat',0),"+"+
			// ""(0,144,5,'MT','MLT',470,39,'Repubblika ta\\' Malta / Republic
			// of Malta','Republic of
			// Malta','Valletta','mt','MTL',470,356,1,'Malta','Malta',1),"+"+
			// ""(0,145,1,'MU','MUS',480,14,'Republic of Mauritius','Republic of
			// Mauritius','Port
			// Louis','mu','MUR',480,230,0,'Mauritius','Mauritius',1),"+"+
			// ""(0,146,4,'MV','MDV',462,34,'ދިވެހިރާއްޖޭގެ
			// ޖުމުހޫރިއްޔާ','Republic of
			// Maldives','Malé','mv','MVR',462,960,0,'ޖުމުހޫރިއްޔ','Maldives',1),"+"+
			// ""(0,147,1,'MW','MWI',454,14,'Republic of Malawi / Dziko la
			// Malaŵi','Republic of
			// Malawi','Lilongwe','mw','MWK',454,265,0,'Malawi','Malawi',1),"+"+
			// ""(0,148,3,'MX','MEX',484,13,'Estados Unidos Mexicanos','United
			// Mexican States','Mexico
			// City','mx','MXN',484,52,0,'México','Mexico',1),"+"+
			// ""(0,149,4,'MY','MYS',458,35,'ڤرسكوتوان مليسيا','Malaysia','Kuala
			// Lumpur','my','MYR',458,60,0,'مليسيا','Malaysia',1),"+"+
			// ""(0,150,1,'MZ','MOZ',508,14,'República de Moçambique','Republic
			// of
			// Mozambique','Maputo','mz','MZM',508,258,0,'Moçambique','Mozambique',1),"+"+
			// ""(0,151,1,'NA','NAM',516,18,'Republic of Namibia','Republic of
			// Namibia','Windhoek','na','NAD',516,264,0,'Namibia','Namibia',1),"+"+
			// ""(0,152,2,'NC','NCL',540,54,'Territoire de Nouvelle-Caledonie et
			// Dépendances','Territory of New
			// Caledonia','Nouméa','nc','XPF',953,687,0,'Nouvelle-Calédonie','New
			// Caledonia',0),"+"+
			// ""(0,153,1,'NE','NER',562,11,'République du Niger','Republic of
			// Niger','Niamey','ne','XOF',952,227,0,'Niger','Niger',1),"+"+
			// ""(0,154,2,'NF','NFK',574,53,'Territory of Norfolk
			// Island','Territory of Norfolk
			// Island','Kingston','nf','AUD',36,6723,0,'Norfolk Island','Norfolk
			// Island',0),"+"+
			// ""(0,155,1,'NG','NGA',566,11,'Federal Republic of
			// Nigeria','Federal Republic of
			// Nigeria','Abuja','ng','NGN',566,234,0,'Nigeria','Nigeria',1),"+"+
			// ""(0,156,3,'NI','NIC',558,13,'República de Nicaragua','Republic
			// of
			// Nicaragua','Managua','ni','NIO',558,505,0,'Nicaragua','Nicaragua',1),"+"+
			// ""(0,157,5,'NL','NLD',528,155,'Koninkrijk der
			// Nederlanden','Kingdom of the
			// Netherlands','Amsterdam','nl','EUR',978,31,1,'Nederland','Netherlands',1),"+"+
			// ""(0,158,5,'NO','NOR',578,154,'Kongeriket Norge','Kingdom of
			// Norway','Oslo','no','NOK',578,47,0,'Norge','Norway',1),"+"+
			// ""(0,159,4,'NP','NPL',524,34,'नेपाल अधिराज्य','Kingdom of
			// Nepal','Kathmandu','np','NPR',524,977,0,'नेपाल','Nepal',1),"+"+
			// ""(0,160,2,'NR','NRU',520,57,'Ripublik Naoero','Republic of
			// Nauru','Yaren','nr','AUD',36,674,0,'Naoero','Nauru',1),"+"+
			// ""(0,161,2,'NU','NIU',570,61,'Niue','Niue','Alofi','nu','NZD',554,683,0,'Niue','Niue',0),"+"+
			// ""(0,162,2,'NZ','NZL',554,53,'New Zealand / Aotearoa','New
			// Zealand','Wellington','nz','NZD',554,64,0,'New Zealand /
			// Aotearoa','New Zealand',1),"+"+
			// ""(0,163,4,'OM','OMN',512,145,'سلطنة عُمان','Sultanate of
			// Oman','Muscat','om','OMR',512,968,0,'عُمان','Oman',1),"+"+
			// ""(0,164,3,'PA','PAN',591,13,'República de Panamá','Repulic of
			// Panama','Panama
			// City','pa','PAB',590,507,0,'Panamá','Panama',1),"+"+
			// ""(0,165,3,'PE','PER',604,5,'República del Perú','Republic of
			// Peru','Lima','pe','PEN',604,51,0,'Perú','Peru',1),"+"+
			// ""(0,166,2,'PF','PYF',258,61,'Polynésie française','French
			// Polynesia','Papeete','pf','XPF',953,689,0,'Polynésie
			// française','French Polynesia',0),"+"+
			// ""(0,167,2,'PG','PNG',598,54,'Independent State of Papua New
			// Guinea / Papua Niugini','Independent State of Papua New
			// Guinea','Port Moresby','pg','PGK',598,675,0,'Papua New Guinea /
			// Papua Niugini','Papua New Guinea',1),"+"+
			// ""(0,168,4,'PH','PHL',608,35,'Republika ng Pilipinas / Republic
			// of the Philippines','Republic of the
			// Philippines','Manila','ph','PHP',608,63,0,'Philippines','Philippines',1),"+"+
			// ""(0,169,4,'PK','PAK',586,34,'Islamic Republic of Pakistan /
			// اسلامی جمہوریۂ پاکستان','Islamic Republic of
			// Pakistan','Islamabad','pk','PKR',586,92,0,'پاکستان','Pakistan',1),"+"+
			// ""(0,170,5,'PL','POL',616,151,'Rzeczpospolita Polska','Republic
			// of Poland','Warsaw','pl','PLN',985,48,1,'Polska','Poland',1),"+"+
			// ""(0,171,3,'PM','SPM',666,21,'Saint-Pierre-et-Miquelon','Saint
			// Pierre and
			// Miquelon','Saint-Pierre','pm','EUR',978,508,0,'Saint-Pierre-et-Miquelon','Saint
			// Pierre and Miquelon',0),"+"+
			// ""(0,172,2,'PN','PCN',612,61,'Pitcairn Islands','Pitcairn
			// Islands','Adamstown','pn','NZD',554,0,0,'Pitcairn
			// Islands','Pitcairn Islands',0),"+"+
			// ""(0,173,3,'PR','PRI',630,29,'Estado Libre Asociado de Puerto
			// Rico / Commonwealth of Puerto Rico','Commonwealth of Puerto
			// Rico','San Juan','pr','USD',840,1787,0,'Puerto Rico','Puerto
			// Rico',0),"+"+
			// ""(0,174,5,'PT','PRT',620,39,'República Portuguesa','Portuguese
			// Republic','Lisbon','pt','EUR',978,351,1,'Portugal','Portugal',1),"+"+
			// ""(0,175,2,'PW','PLW',585,57,'Belu\\'u era Belau / Republic of
			// Palau','Republic of Palau','Koror','pw','USD',840,680,0,'Belau /
			// Palau','Palau',1),"+"+
			// ""(0,176,3,'PY','PRY',600,5,'República del Paraguay / Tetä
			// Paraguáype','Republic of
			// Paraguay','Asunción','py','PYG',600,595,0,'Paraguay','Paraguay',1),"+"+
			// ""(0,177,4,'QA','QAT',634,145,'دولة قطر','State of
			// Qatar','Doha','qa','QAR',634,974,0,'قطر','Qatar',1),"+"+
			// ""(0,178,1,'RE','REU',638,14,'Département de la
			// Réunion','Department of
			// Réunion','Saint-Denis','re','EUR',978,262,0,'Réunion','Reunion',0),"+"+
			// ""(0,179,5,'RO','ROU',642,151,'România','Romania','Bucharest','ro','ROL',642,40,0,'România','Romania',1),"+"+
			// ""(0,180,4,'RU','RUS',643,172,'Российская Федерация','Russian
			// Federation','Moscow','ru','RUB',643,7,0,'Росси́я','Russia',1),"+"+
			// ""(0,181,1,'RW','RWA',646,14,'Repubulika y\\'u Rwanda /
			// République Rwandaise','Republic of
			// Rwanda','Kigali','rw','RWF',646,250,0,'Rwanda','Rwanda',1),"+"+
			// ""(0,182,4,'SA','SAU',682,145,'المملكة العربية السعودية','Kingdom
			// of Saudi Arabia','Riyadh','sa','SAR',682,966,0,'السعودية','Saudi
			// Arabia',1),"+"+
			// ""(0,183,2,'SB','SLB',90,54,'Solomon Islands','Solomon
			// Islands','Honiara','sb','SBD',90,677,0,'Solomon Islands','Solomon
			// Islands',1),"+"+
			// ""(0,184,1,'SC','SYC',690,14,'Repiblik Sesel / Republic of
			// Seychelles / République des Seychelles','Republic of
			// Seychelles','Victoria','sc','SCR',690,248,0,'Seychelles','Seychelles',1),"+"+
			// ""(0,185,1,'SD','SDN',736,15,'جمهورية السودان','Republic of the
			// Sudan','Khartoum','sd','SDD',736,249,0,'السودان','Sudan',1),"+"+
			// ""(0,186,5,'SE','SWE',752,154,'Konungariket Sverige','Kingdom of
			// Sweden','Stockholm','se','SEK',752,46,1,'Sverige','Sweden',1),"+"+
			// ""(0,187,4,'SG','SGP',702,35,'Republic of Singapore / 新加坡共和国 /
			// Republik Singapura / சிங்கப்பூர் குடியரசு','Republic of
			// Singapore','Singapore','sg','SGD',702,65,0,'Singapore','Singapore',1),"+"+
			// ""(0,188,1,'SH','SHN',654,11,'Saint Helena','Saint
			// Helena','Jamestown','sh','SHP',654,290,0,'Saint Helena','Saint
			// Helena',0),"+"+
			// ""(0,189,5,'SI','SVN',705,39,'Republika Slovenija','Republic of
			// Slovenia','Ljubljana','si','SIT',705,386,1,'Slovenija','Slovenia',1),"+"+
			// ""(0,190,5,'SJ','SJM',744,154,'Svalbard','Svalbard','Longyearbyen','sj','NOK',578,47,0,'Svalbard','Svalbard',0),"+"+
			// ""(0,191,5,'SK','SVK',703,151,'Slovenská republika','Slovak
			// Republic','Bratislava','sk','SKK',703,421,1,'Slovensko','Slovakia',1),"+"+
			// ""(0,192,1,'SL','SLE',694,11,'Republic of Sierra Leone','Republic
			// of Sierra Leone','Freetown','sl','SLL',694,232,0,'Sierra
			// Leone','Sierra Leone',1),"+"+
			// ""(0,193,5,'SM','SMR',674,39,'Serenissima Repubblica di San
			// Marino','Most Serene Republic of San Marino','San
			// Marino','sm','EUR',978,378,0,'San Marino','San Marino',1),"+"+
			// ""(0,194,1,'SN','SEN',686,11,'République de Sénégal','Republic of
			// Senegal','Dakar','sn','XOF',952,221,0,'Sénégal','Senegal',1),"+"+
			// ""(0,195,1,'SO','SOM',706,14,'Soomaaliya','Somalia','Mogadishu','so','SOS',706,252,0,'Soomaaliya','Somalia',1),"+"+
			// ""(0,196,3,'SR','SUR',740,5,'Republiek Suriname','Republic of
			// Surinam','Paramaribo','sr','SRD',968,597,0,'Suriname','Suriname',1),"+"+
			// ""(0,197,1,'ST','STP',678,17,'República Democrática de São Tomé e
			// Príncipe','Democratic Republic of São Tomé e Príncipe','São
			// Tomé','st','STD',678,239,0,'São Tomé e Príncipe','São Tomé e
			// Príncipe',1),"+"+
			// ""(0,198,3,'SV','SLV',222,13,'República de El Salvador','Republic
			// of El Salvador','San Salvador','sv','SVC',222,503,0,'El
			// Salvador','El Salvador',1),"+"+
			// ""(0,199,4,'SY','SYR',760,145,'الجمهوريّة العربيّة
			// السّوريّة','Syrian Arab
			// Republic','Damascus','sy','SYP',760,963,0,'سوري','Syria',1),"+"+
			// ""(0,200,1,'SZ','SWZ',748,18,'Umboso weSwatini / Kingdom of
			// Swaziland','Kingdom of
			// Swaziland','Mbabane','sz','SZL',748,268,0,'weSwatini','Swaziland',1),"+"+
			// ""(0,201,3,'TC','TCA',796,29,'Turks and Caicos Islands','Turks
			// and Caicos Islands','Cockburn Town','tc','USD',840,1649,0,'Turks
			// and Caicos Islands','Turks and Caicos Islands',0),"+"+
			// ""(0,202,1,'TD','TCD',148,17,'جمهورية تشاد / République du
			// Tchad','Republic of
			// Chad','N\\'Djamena','td','XAF',950,235,0,'تشاد /
			// Tchad','Chad',1),"+"+
			// ""(0,203,6,'TF','ATF',260,0,'Terres australes françaises','French
			// Southern Territories','','tf','',0,0,0,'Terres australes
			// françaises','French Southern Territories',0),"+"+
			// ""(0,204,1,'TG','TGO',768,11,'République Togolaise','Republic of
			// Togo','Lomé','tg','XOF',952,228,0,'Togo','Togo',1),"+"+
			// ""(0,205,4,'TH','THA',764,35,'ราชอาณาจักรไทย','Kingdom of
			// Thailand','Bangkok','th','THB',764,66,0,'ไทย','Thailand',1),"+"+
			// ""(0,206,4,'TJ','TJK',762,143,'Ҷумҳурии Тоҷикистон','Republic of
			// Tajikistan','Dushanbe','tj','TJS',972,7,0,'Тоҷикистон','Tajikistan',1),"+"+
			// ""(0,207,2,'TK','TKL',772,61,'Tokelau','Tokelau','Fakaofo','tk','NZD',554,0,0,'Tokelau','Tokelau',0),"+"+
			// ""(0,208,4,'TM','TKM',795,143,'Türkmenistan
			// Jumhuriyäti','Republic of
			// Turkmenistan','Ashgabat','tm','TMM',795,7,0,'Türkmenistan','Turkmenistan',1),"+"+
			// ""(0,209,1,'TN','TUN',788,15,'الجمهورية التونسية','Republic of
			// Tunisia','Tunis','tn','TND',788,216,0,'التونسية','Tunisia',1),"+"+
			// ""(0,210,2,'TO','TON',776,61,'Pule\\'anga Fakatu\\'i \\'o Tonga /
			// Kingdom of Tonga','Kingdom of
			// Tonga','Nuku\\'alofa','to','TOP',776,676,0,'Tonga','Tonga',1),"+"+
			// ""(0,211,4,'TL','TLS',626,35,'Repúblika Demokrátika Timor
			// Lorosa\\'e / República Democrática de Timor-Leste','Democratic
			// Republic of Timor-Leste','Dili','tp','TPE',626,670,0,'Timor
			// Lorosa\\'e','Timor-Leste',1),"+"+
			// ""(0,212,4,'TR','TUR',792,145,'Türkiye Cumhuriyeti','Republic of
			// Turkey','Ankara','tr','TRY',949,90,0,'Türkiye','Turkey',1),"+"+
			// ""(0,213,3,'TT','TTO',780,29,'Republic of Trinidad and
			// Tobago','Republic of Trinidad and Tobago','Port of
			// Spain','tt','TTD',780,1868,0,'Trinidad and Tobago','Trinidad and
			// Tobago',1),"+"+
			// ""(0,214,2,'TV','TUV',798,61,'Tuvalu','Tuvalu','Fongafale','tv','AUD',36,688,0,'Tuvalu','Tuvalu',1),"+"+
			// ""(0,215,4,'TW','TWN',158,30,'中華民國','Republic of
			// China','Taipei','tw','TWD',901,886,0,'中華','Taiwan',0),"+"+
			// ""(0,216,1,'TZ','TZA',834,14,'Jamhuri ya Muungano wa
			// Tanzania','United Republic of
			// Tanzania','Dodoma','tz','TZS',834,255,0,'Tanzania','Tanzania',1),"+"+
			// ""(0,217,5,'UA','UKR',804,172,'Україна','Ukraine','Kiev','ua','UAH',980,380,0,'Україна','Ukraine',1),"+"+
			// ""(0,218,1,'UG','UGA',800,14,'Republic of Uganda','Republic of
			// Uganda','Kampala','ug','UGX',800,256,0,'Uganda','Uganda',1),"+"+
			// ""(0,219,3,'UM','UMI',581,0,'United States Minor Outlying
			// Islands','United States Minor Outlying
			// Islands','','um','USD',840,0,0,'United States Minor Outlying
			// Islands','United States Minor Outlying Islands',0),"+"+
			// ""(0,220,3,'US','USA',840,21,'United States of America','United
			// States of America','Washington DC','us','USD',840,1,0,'United
			// States','United States',1),"+"+
			// ""(0,221,3,'UY','URY',858,5,'República Oriental del
			// Uruguay','Eastern Republic of
			// Uruguay','Montevideo','uy','UYU',858,598,0,'Uruguay','Uruguay',1),"+"+
			// ""(0,222,4,'UZ','UZB',860,143,'O‘zbekiston
			// Respublikasi','Republic of
			// Uzbekistan','Tashkent','uz','UZS',860,7,0,'O‘zbekiston','Uzbekistan',1),"+"+
			// ""(0,223,5,'VA','VAT',336,39,'Status Civitatis Vaticanae / Città
			// del Vaticano','Vatican City','Vatican
			// City','va','EUR',978,396,0,'Vaticano','Vatican City',0),"+"+
			// ""(0,224,3,'VC','VCT',670,29,'Saint Vincent and the
			// Grenadines','Saint Vincent and the
			// Grenadines','Kingstown','vc','XCD',951,1784,0,'Saint Vincent and
			// the Grenadines','Saint Vincent and the Grenadines',1),"+"+
			// ""(0,225,3,'VE','VEN',862,5,'República Bolivariana de
			// Venezuela','Bolivarian Republic of
			// Venezuela','Caracas','ve','VEB',862,58,0,'Venezuela','Venezuela',1),"+"+
			// ""(0,226,3,'VG','VGB',92,29,'British Virgin Islands','British
			// Virgin Islands','Road Town','vg','USD',840,1284,0,'British Virgin
			// Islands','British Virgin Islands',0),"+"+
			// ""(0,227,3,'VI','VIR',850,29,'United States Virgin
			// Islands','United States Virgin Islands','Charlotte
			// Amalie','vi','USD',840,1340,0,'US Virgin Islands','US Virgin
			// Islands',0),"+"+
			// ""(0,228,4,'VN','VNM',704,35,'Cộng Hòa Xã Hội Chủ Nghĩa Việt
			// Nam','Socialist Republic of
			// Vietnam','Hanoi','vn','VND',704,84,0,'Việt Nam','Vietnam',1),"+"+
			// ""(0,229,2,'VU','VUT',548,54,'Ripablik blong Vanuatu / Republic
			// of Vanuatu / République du Vanuatu','Republic of Vanuatu','Port
			// Vila','vu','VUV',548,678,0,'Vanuatu','Vanuatu',1),"+"+
			// ""(0,230,2,'WF','WLF',876,61,'Territoire de Wallis et
			// Futuna','Territory of Wallis and Futuna
			// Islands','Mata-Utu','wf','XPF',953,681,0,'Wallis and
			// Futuna','Wallis and Futuna',0),"+"+
			// ""(0,231,2,'WS','WSM',882,61,'Malo Sa\\'oloto Tuto\\'atasi o
			// Samoa / Independent State of Samoa','Independent State of
			// Samoa','Apia','ws','WST',882,685,0,'Samoa','Samoa',1),"+"+
			// ""(0,232,4,'YE','YEM',887,145,'الجمهوريّة اليمنية','Republic of
			// Yemen','San\\'a','ye','YER',886,967,0,'اليمنية','Yemen',1),"+"+
			// ""(0,233,1,'YT','MYT',175,14,'Mayotte','Mayotte','Mamoudzou','yt','EUR',978,269,0,'Mayotte','Mayotte',0),"+"+
			// ""(0,235,1,'ZA','ZAF',710,18,'Republic of South Africa /
			// Republiek van Suid-Afrika / Rephaboliki ya
			// Afrika-Borwa','Republic of South
			// Africa','Pretoria','za','ZAR',710,27,0,'Afrika-Borwa','South
			// Africa',1),"+"+
			// ""(0,236,1,'ZM','ZMB',894,14,'Republic of Zambia','Republic of
			// Zambia','Lusaka','zm','ZMK',894,260,0,'Zambia','Zambia',1),"+"+
			// ""(0,237,1,'ZW','ZWE',716,14,'Republic of Zimbabwe','Republic of
			// Zimbabwe','Harare','zw','ZWD',716,263,0,'Zimbabwe','Zimbabwe',1),"+"+
			// ""(0,238,4,'PS','PSE',275,145,'Palestinian
			// territories','Palestinian
			// territories','','ps','0',0,0,0,'Palestine','Palestine',0),"+"+
			// ""(0,239,5,'CS','CSG',891,39,'Државна заједница Србија и Црна
			// Гора','State Union of Serbia and
			// Montenegro','Belgrade','cs','CSD',891,381,0,'Србија и Црна
			// Гора','Serbia and Montenegro',1),"+"+
			// ""(0,240,5,'AX','ALA',248,154,'Åland Islands','Åland
			// Islands','Mariehamn','fi','EUR',978,35818,1,'Åland
			// Islands','Åland Islands',0),"+"+
			// "(0,241,2,'HM','HMD',334,53,'Heard Island and McDonald Islands','Heard Island and McDonald Islands','','','AUD',36,0,0,'Heard Island and McDonald Islands','Heard Island and McDonald Islands',0);";
			//
			// query = session.createSQLQuery(queryString);
			// query.executeUpdate();

			String currencyQueryString = "INSERT INTO `static_currency` (`id`,`OPTLOCK`,`cn_currency_iso_nr`,`cn_currency_iso_3`) " +
					" VALUES" +
					"(1,0,784,'AED')," +
					"(2,0,971,'AFN')," +
					"(3,0,8,'ALL')," +
					"(4,0,51,'AMD')," +
					"(5,0,532,'ANG')," +
					"(6,0,973,'AOA')," +
					"(	7,0,32,'ARS')," +
					"(	8,0,36,'AUD')," +
					"(	9,0,533,'AWG')," +
					"(	10,0,31,'AZM')," +
					"(	11,0,977,'BAM')," +
					"(	12,0,52,'BBD')," +
					"(	13,0,50,'BDT')," +
					"(	14,0,100,'BGL')," +
					"(	15,0,48,'BHD')," +
					"(	16,0,108,'BIF')," +
					"(	17,0,60,'BMD')," +
					"(	18,0,96,'BND')," +
					"(	19,0,68,'BOB')," +
					"(	20,0,986,'BRL')," +
					"(	21,0,44,'BSD')," +
					"(	22,0,64,'BTN')," +
					"(	23,0,72,'BWP')," +
					"(	24,0,974,'BYR')," +
					"(	25,0,84,'BZD')," +
					"(	26,0,124,'CAD')," +
					"(	27,0,976,'CDF')," +
					"(	28,0,756,'CHF')," +
					"(	29,0,152,'CLP')," +
					"(	30,0,156,'CNY')," +
					"(	31,0,170,'COP')," +
					"(	32,0,188,'CRC')," +
					"(	33,0,891,'CSD')," +
					"(	34,0,192,'CUP')," +
					"(	35,0,132,'CVE')," +
					"(	36,0,196,'CYP')," +
					"(	37,0,203,'CZK')," +
					"(	38,0,262,'DJF')," +
					"(	39,0,208,'DKK')," +
					"(	40,0,214,'DOP')," +
					"(	41,0,12,'DZD')," +
					"(	42,0,233,'EEK')," +
					"(	43,0,818,'EGP')," +
					"(	44,0,232,'ERN')," +
					"(	45,0,230,'ETB')," +
					"(	46,0,978,'EUR')," +
					"(	47,0,242,'FJD')," +
					"(	48,0,238,'FKP')," +
					"(	49,0,826,'GBP')," +
					"(	50,0,981,'GEL')," +
					"(	51,0,288,'GHC')," +
					"(	52,0,292,'GIP')," +
					"(	53,0,270,'GMD')," +
					"(	54,0,324,'GNF')," +
					"(	55,0,320,'GTQ')," +
					"(	56,0,328,'GYD')," +
					"(	57,0,344,'HKD')," +
					"(	58,0,340,'HNL')," +
					"(	59,0,191,'HRK')," +
					"(	60,0,332,'HTG')," +
					"(	61,0,348,'HUF')," +
					"(	62,0,360,'IDR')," +
					"(	63,0,376,'ILS')," +
					"(	64,0,356,'INR')," +
					"(	65,0,368,'IQD')," +
					"(	66,0,364,'IRR')," +
					"(	67,0,352,'ISK')," +
					"(	68,0,388,'JMD')," +
					"(	69,0,400,'JOD')," +
					"(	70,0,392,'JPY')," +
					"(	71,0,404,'KES')," +
					"(	72,0,417,'KGS')," +
					"(	73,0,116,'KHR')," +
					"(	74,0,174,'KMF')," +
					"(	75,0,408,'KPW')," +
					"(	76,0,410,'KRW')," +
					"(	77,0,414,'KWD')," +
					"(	78,0,136,'KYD')," +
					"(	79,0,398,'KZT')," +
					"(	80,0,418,'LAK')," +
					"(	81,0,422,'LBP')," +
					"(	82,0,144,'LKR')," +
					"(	83,0,430,'LRD')," +
					"(	84,0,426,'LSL')," +
					"(	85,0,440,'LTL')," +
					"(	86,0,428,'LVL')," +
					"(	87,0,434,'LYD')," +
					"(	88,0,504,'MAD')," +
					"(	89,0,498,'MDL')," +
					"(	90,0,969,'MGA')," +
					"(	91,0,807,'MKD')," +
					"(	92,0,104,'MMK')," +
					"(	93,0,496,'MNT')," +
					"(	94,0,446,'MOP')," +
					"(	95,0,478,'MRO')," +
					"(	96,0,470,'MTL')," +
					"(	97,0,480,'MUR')," +
					"(	98,0,462,'MVR')," +
					"(	99,0,454,'MWK')," +
					"(	100,0,484,'MXN')," +
					"(	101,0,458,'MYR')," +
					"(	102,0,508,'MZM')," +
					"(	103,0,516,'NAD')," +
					"(	104,0,566,'NGN')," +
					"(	105,0,558,'NIO')," +
					"(	106,0,578,'NOK')," +
					"(	107,0,524,'NPR')," +
					"(	108,0,554,'NZD')," +
					"(	109,0,512,'OMR')," +
					"(	110,0,590,'PAB')," +
					"(	111,0,604,'PEN')," +
					"(	112,0,598,'PGK')," +
					"(	113,0,608,'PHP')," +
					"(	114,0,586,'PKR')," +
					"(	115,0,985,'PLN')," +
					"(	116,0,600,'PYG')," +
					"(	117,0,634,'QAR')," +
					"(	118,0,642,'ROL')," +
					"(	119,0,643,'RUB')," +
					"(	120,0,646,'RWF')," +
					"(	121,0,682,'SAR')," +
					"(	122,0,90,'SBD')," +
					"(	123,0,690,'SCR')," +
					"(	124,0,736,'SDD')," +
					"(	125,0,752,'SEK')," +
					"(	126,0,702,'SGD')," +
					"(	127,0,654,'SHP')," +
					"(	128,0,705,'SIT')," +
					"(	129,0,703,'SKK')," +
					"(	130,0,694,'SLL')," +
					"(	131,0,706,'SOS')," +
					"(	132,0,968,'SRD')," +
					"(	133,0,678,'STD')," +
					"(	134,0,222,'SVC')," +
					"(	135,0,760,'SYP')," +
					"(	136,0,748,'SZL')," +
					"(	137,0,764,'THB')," +
					"(	138,0,972,'TJS')," +
					"(	139,0,795,'TMM')," +
					"(	140,0,788,'TND')," +
					"(	141,0,776,'TOP')," +
					"(	142,0,626,'TPE')," +
					"(	143,0,949,'TRY')," +
					"(	144,0,780,'TTD')," +
					"(	145,0,901,'TWD')," +
					"(	146,0,834,'TZS')," +
					"(	147,0,980,'UAH')," +
					"(	148,0,800,'UGX')," +
					"(	149,0,840,'USD')," +
					"(	150,0,858,'UYU')," +
					"(	151,0,860,'UZS')," +
					"(	152,0,862,'VEB')," +
					"(	153,0,704,'VND')," +
					"(	154,0,548,'VUV')," +
					"(	155,0,882,'WST')," +
					"(	156,0,950,'XAF')," +
					"(	157,0,951,'XCD')," +
					"(	158,0,952,'XOF')," +
					"(	159,0,953,'XPF')," +
					"(	160,0,886,'YER')," +
					"(	161,0,710,'ZAR')," +
					"(	162,0,894,'ZMK')," +
					"(	163,0,716,'ZWD');";

			query = session.createSQLQuery(currencyQueryString);
			query.executeUpdate();

			final LoginUserRole ur = new LoginUserRole();
			ur.setAuthority("ROLE_ADMIN");
			session.save(ur);

			final LoginUserRole ur1 = new LoginUserRole();
			ur1.setAuthority("ROLE_USER");
			session.save(ur1);

			final LoginUser ud = new LoginUser();
			ud.setUsername("test");
			ud.setPassword(passwordEncoder.encodePassword("test", saltSource.getSalt(ud)));
			ud.setFamilyName("Mustermann");
			ud.setGivenName("Max");
			ud.setEmailBusiness("max@mustermann.de");
			ud.addRole(ur1);
			session.save(ud);

			final LoginUser ud1 = new LoginUser();
			ud1.setUsername("admin");
			ud1.setPassword(passwordEncoder.encodePassword("seKuRAm83", saltSource.getSalt(ud)));
			ud1.setFamilyName("Administrator");
			ud1.setGivenName("");
			ud1.setEmailBusiness("edv@agef.de");
			ud1.addRole(ur);
			ud1.addRole(ur1);
			session.save(ud1);

			t.commit();
		}

		if (insertTestData) {

			Transaction t = session.getTransaction();
			t.begin();

			// new address for user Max Mustermann
			final Address address = new Address();
			address.setCity("Berlin");
			address.setAddress1("Mainzer Str. 1");
			address.setAddress2("Friedrichhain");
			address.setZipCode("10247");
			address.setCountry((Country) session.load(Country.class, new Long(54)));

			// new address for user Maria Musterfrau
			final Address address2 = new Address();
			address2.setCity("Bern");
			address2.setAddress1("Berliner Str. 1");
			address2.setAddress2("");
			address2.setZipCode("3456");
			address2.setCountry((Country) session.load(Country.class, new Long(42)));

			final Address address3 = new Address();
			address3.setCity("Bern");
			address3.setAddress1("Berliner Str. 3");
			address3.setAddress2("");
			address3.setZipCode("3456");
			address3.setCountry((Country) session.load(Country.class, new Long(41)));

			final Address address4 = new Address();
			address4.setCity("Bern");
			address4.setAddress1("Berliner Str. 32");
			address4.setAddress2("");
			address4.setZipCode("34222256");
			address4.setCountry((Country) session.load(Country.class, new Long(44)));

			// new user Max Mustermann
			final User user = new User();
			user.setTitle(TitleEnum.PROF_DR);
			user.setGivenName("Max");
			user.setFamilyName("Mustermann");
			user.setEmailPrivate("max@daad.de");
			user.setInternet("www.daad.de");
			user.setDateOfBirth(new Date());
			user.setAddress1(address);
			// user.setCobraSuperId(new Long(2));
			user.setNationality((Country) session.load(Country.class, new Long(54)));
			user.setPortalUserId(new Long(2)); // old user id 23

			final IndustrySector industrySector = (IndustrySector) session.load(IndustrySector.class, new Long(2));

			// new user Maria Musterfrau
			final User user2 = new User();
			user2.setTitle(TitleEnum.DR);
			user2.setGivenName("Maria");
			user2.setFamilyName("Musterfrau");
			user2.setEmailPrivate("maria@daad.de");
			user2.setInternet("www.daad.de");
			user2.setDateOfBirth(new Date());
			user2.setAddress1(address2);
			// user2.setCobraSuperId(new Long(1));
			user2.setUserRole(new OrganisationRole(user2));
			user2.getUserRoleData().setOrganisationName("DAAD");
			user2.getUserRoleData().setOrganisationDescription("Organisation zur Förderung des Akademischen Austauschs im Rahmen der Entwicklungszusammenarbeit");
			user2.getUserRoleData().setIndustrySector(industrySector);
			user2.setPortalUserId(new Long(1));

			final User user3 = new User();
			user3.setTitle(TitleEnum.PROF_DR);
			user3.setGivenName("Max");
			user3.setFamilyName("Cobrauser");
			user3.setInternet("www.cobra.de");
			user3.setEmailPrivate("max@cobra.de");
			user3.setDateOfBirth(new Date());
			user3.setAddress1(address3);
			user3.setCobraSuperId(new Long(2));

			final AlumniRole orgRole = new AlumniRole(user3);
			orgRole.getRoleData().setIndustrySector((IndustrySector) session.load(IndustrySector.class, new Long(3)));
			user3.setUserRole(orgRole);

			final DataProvider agefProvider = (DataProvider) session.load(DataProvider.class, new Long(2));

			session.save(address);
			session.save(user);
			session.save(address2);
			session.save(user2);
			session.save(address3);
			session.save(user3);

			final ContactPerson cp = new ContactPerson();
			cp.setEmailBusiness("contact@daad.de");
			cp.setFamilyName("Mustermann");
			cp.setGivenName("Peter");
			cp.setInternet("www.daad.de");
			cp.setPosition("HR Manager");
			cp.setTitle(TitleEnum.DR);
			cp.setContactPersonAddress(address4);
			Country country6 = (Country) session.load(Country.class, new Long(4));
			Country country7 = (Country) session.load(Country.class, new Long(3));

			// new example job
			final JobImpl job = new JobImpl(user2, agefProvider);
			// job.setOrganisationDescription("Organisation zur Förderung des Akademischen Austauschs im Rahmen der Entwicklungszusammenarbeit");
			// job.setOrganisationName("DAAD");
			job.setJobDescription("DatenbankspezialistIn(w/m)");
			job.setTaskDescription("Administration der bestehenden sowie Konzeption und Aufbau neuer Datenbanksysteme.");
			job.setAlternativeProfession("Systemadministrator Unix");
			job.setCommentsRegardingApplicationProcedure("Bitte übersenden sie uns ihre vollständigen Bewerbungsunterlagen an die folgende eMailadresse: apply@daad.de");
			job.setComputerSkills(DecisionYesNoEnum.YES);
			job.setDesiredProfession("DatenbankadmistratorIn");
			job.setWeeklyHoursOfWork(40);
			job.setDrivingLicence(DecisionYesNoEnum.YES);
			job.setDurationOfContract(ContractDurationEnum.LONGTERM_2_YEARS_AND_LONGER);
			job.setFurtherComments("n/a");
			job.setFurtherCommentsRegardingEducation("Wir erwarten ein abgeschlossenes Hochschulstudium sowie langjährige Berufserfahrung im Aufbau und der Betreuung von Datenbanksystemen");
			job.setJobOfferExpireDate(new Date());
			job.setLanguageSkillsEnglish(LanguageSkillsEnum.BUSINESS_FLUENT);
			job.setLanguageSkillsGerman(LanguageSkillsEnum.BUSINESS_FLUENT);
			job.setLocationOfEmployment("Abu Dhabi");
			job.setNumberOfJobs(1);
			job.setComputerSkillsComments("MSSQL Server/ Oracle/ PostgrSQL");
			job.setPossibleCommencementDate(new Date());
			job.setMinimumRequirementsForEducation(DegreeEnum.MASTER);
			job.setSalary("3500");
			job.setWorkExperience(ExperienceDurationEnum.TWO_TO_FIVE);
			job.setContactPerson(cp);
			job.setCountryOfEmployment(country6);
			session.save(job);

			// another example job
			final JobImpl job1 = new JobImpl(user2, agefProvider);
			// job1.setOrganisationName("InWent");
			// job1.setOrganisationDescription("Organisation zur Durchführung von Weiterbildungsmaßnahmen im Rahmen der Entwicklungszusammenarbeit");
			job1.setJobDescription("SuchmaschinenspezialitIn (w/m)");
			job1.setTaskDescription("Administration der bestehenden sowie Konzeption und Aufbau neuer Suchmaschinensysteme.");
			job1.setAlternativeProfession("SystemadministratorIn Unix/Windows Server");
			job1.setCommentsRegardingApplicationProcedure("Bitte übersenden sie uns ihre vollständigen Bewerbungsunterlagen an die folgende eMailadresse: apply@daad.de");
			job1.setComputerSkills(DecisionYesNoEnum.YES);
			job1.setDesiredProfession("SuchmaschinenspezialitIn");
			job1.setWeeklyHoursOfWork(40);
			job1.setDrivingLicence(DecisionYesNoEnum.YES);
			job1.setDurationOfContract(ContractDurationEnum.PERMANENT);
			job1.setFurtherComments("n/a");
			job1.setFurtherCommentsRegardingEducation("Wir erwarten ein abgeschlossenes Hochschulstudium sowie langjährige Berufserfahrung im Aufbau und der Betreuung von Suchmaschinensystemen");
			job1.setJobOfferExpireDate(new Date());
			job1.setLanguageSkillsEnglish(LanguageSkillsEnum.BUSINESS_FLUENT);
			job1.setLanguageSkillsGerman(LanguageSkillsEnum.BUSINESS_FLUENT);
			job1.setLocationOfEmployment("Abu Dhabi");
			job1.setNumberOfJobs(2);
			job1.setComputerSkillsComments("Unix Administration, Kenntnisse zu Suchalgorithmen");
			job1.setPossibleCommencementDate(new Date());
			job1.setMinimumRequirementsForEducation(DegreeEnum.MASTER);
			job1.setSalary("4500");
			job1.setWorkExperience(ExperienceDurationEnum.TWO_TO_FIVE);
			job1.setContactPerson(cp);
			job1.setCountryOfEmployment(country7);
			// session.save(job1);

			t.commit();

			// JobImpl jobDetails = new JobImpl(user);
			// jobDetails.setAlternativeProfession("AGEF");
			// JobImplAdapter jobDetailsTransfer = new
			// JobImplAdapter(jobDetails);
			// JobTransfer transfer = (JobTransferImpl) jobDetailsTransfer;
			// System.out.println("Transferergebniss : "+jobDetailsTransfer.getAlternativeProfession());
			//
			// session.flush();
			// session.close();

		}

		if (runInitialization) {
			applicationInitializer.initializeApplication(context);
		}
	}

}
