package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QFrequentPathsViger;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.connectors.clix2010.HibernateUtil;
import de.lemo.dms.connectors.moodle.ConnectorMoodle;
import de.lemo.dms.core.ServerConfigurationHardCoded;

/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 *
 */
public class Test {
	
	/**
	 * Tests the Chemgapedia-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runChemConn()
	{
		String logs = "C:\\Users\\s.schwarzrock\\Desktop\\120614\\130104_lemojoin_10K_1.log";
		//int i = 0;
		//for(int i = 1 ; i < 6; i++)
		//{
			DBConfigObject sourceConf = new DBConfigObject();
			sourceConf.addProperty("path.log_file", logs);
			sourceConf.addProperty("path.resource_metadata", "C:\\Users\\s.schwarzrock\\Desktop\\vsc");
			sourceConf.addProperty("filter_log_file", "true");
			sourceConf.addProperty("process_metadata", "true");
			sourceConf.addProperty("process_log_file", "true");
			
			ConnectorChemgapedia cm = new ConnectorChemgapedia();
			cm.setSourceDBConfig(sourceConf);
			cm.updateData("Chemgapedia(FIZ)", 0L);
		//}
	}
	
	/**
	 * Tests the Moodle(1.9)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runMoodleConn()
	{
		ConnectorMoodle cm = new ConnectorMoodle();
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		cm.getData("Moodle(Beuth)");
		//cm.updateData("Moodle(Beuth)", 1338000000);
	}
	
	/**
	 * Tests the Moodle(1.9)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runMoodleNumericConn()
	{
		de.lemo.dms.connectors.moodleNumericId.ConnectorMoodle cm = new de.lemo.dms.connectors.moodleNumericId.ConnectorMoodle();
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		cm.getData("Moodle(BBW)");
		//cm.updateData("Moodle(Beuth)", 1338000000);
	}
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runMoodle23Conn()
	{
		de.lemo.dms.connectors.moodle_2_3.ConnectorMoodle cm = new de.lemo.dms.connectors.moodle_2_3.ConnectorMoodle();
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		cm.getData("Moodle 2.3(Beuth)");
		//cm.updateData("Moodle 2.3(Beuth)", 1338000000);
	}
	
	/**
	 * Tests the Clix(2010)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runClixConn()
	{
		ConnectorClix cc = new ConnectorClix();
		cc.getData("Clix(HTW)");
	}
	
	public void test()
	{
		Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
        session.clear();
		
		Query pers = session.createQuery("from Person x order by x.id asc");
        List<?> person = pers.list();	        
        System.out.println("Person tables: " + person.size()); 
	}
	
	public void calculateMeichsner()
	{
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		Session session = dbHandler.getMiningSession();
		
		ArrayList<Long> cids = new ArrayList<Long>();
		cids.add(100117945446L);
		cids.add(100113617310L);
		cids.add(100110921956L);
		cids.add(10018074949L);
		cids.add(10014667155L);
		
		Criteria crit = session.createCriteria(ResourceLogMining.class, "logs");
		crit.add(Restrictions.in("logs.course.id", cids));
		System.out.println("Reading DB");
		@SuppressWarnings("unchecked")
		List<ResourceLogMining> l = crit.list();
		System.out.println("Found "+l.size()+" courses.");
		
		HashSet<String> hSet = new HashSet<String>();
		for(ResourceLogMining resL : l)
		{
			if(resL.getResource() != null && resL.getResource().getTitle().contains("Grundlagen des Projektmanagements"))
			{
				String id = resL.getCourse().getTitle() + "-" + resL.getResource().getTitle();
				hSet.add(id);
			}
		}
		
		for(String s : hSet)
		{
			System.out.println(s);
		}
		
	}
	
	
//	public static void test2()
//	{
//		QCourseActivity qca = new QCourseActivity();
//		List<Long> courses = new ArrayList<Long>();
//		courses.add(112200L);
//		courses.add(11476L);
//		List<Long> roles = new ArrayList<Long>();
//		Long startTime = 0L;
//		Long endTime = 1500000000L;
//		int resolution = 100;
//		List<String> resourceTypes = new ArrayList<String>();
//		qca.compute(courses, roles, startTime, endTime, resolution, resourceTypes);
//	}
	
	public void testViger()
	{
		QFrequentPathsViger qfpv = new QFrequentPathsViger();
		ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		ArrayList<Long> users = new ArrayList<Long>();
		ArrayList<String> types = new ArrayList<String>();
		Long minLength = 0L;
		Long maxLength = 1000L;
		double minSup = 1;
		
		qfpv.compute(courses, users, types, minLength, maxLength, minSup, false, 0L, 1500000000L);
		
	}
	
	public void testBide()
	{
		QFrequentPathsBIDE qfpv = new QFrequentPathsBIDE();
		ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		ArrayList<Long> users = new ArrayList<Long>();
		ArrayList<String> types = new ArrayList<String>();
		Long minLength = 0L;
		Long maxLength = 1000L;
		double minSup = 0.5;
		
		qfpv.compute(courses, users, types, minLength, maxLength, minSup, false, 0L, 1500000000L);
		
	}
	
		
	public void testService()
	{
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(11476L);
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
	        
        Criteria criteria = session.createCriteria(ICourseRatedObjectAssociation.class, "aso");
        criteria.add(Restrictions.in("aso.course.id", courses));
        	        
        ArrayList<ICourseRatedObjectAssociation> list = (ArrayList<ICourseRatedObjectAssociation>) criteria.list();
        
        for(ICourseRatedObjectAssociation obj : list)
        {
        	res.add(obj.getRatedObject().getClass().getSimpleName());
        	res.add(obj.getRatedObject().getPrefix().toString());
        	res.add(obj.getRatedObject().getId() + "");
        	res.add(obj.getRatedObject().getTitle());
        }
        System.out.println("Starting test");
	}
	
	public void run()
	{
		System.out.println("Starting test");
		runChemConn();
		System.out.println("Test finished");
	}
	
	public static void main(String[] args)
	{
		Test t = new Test();
		t.runClixConn();
	}
	
}
