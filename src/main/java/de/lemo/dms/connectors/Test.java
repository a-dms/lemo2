/**
 * File ./main/java/de/lemo/dms/connectors/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.questions.QActivityResourceType;
import de.lemo.dms.processing.questions.QCourseActivity;
import de.lemo.dms.processing.questions.QCumulativeUserAccess;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QLearningObjectUsage;
import de.lemo.dms.processing.questions.QPerformanceHistogram;
import de.lemo.dms.processing.questions.QPerformanceUserTest;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.service.ServiceCourseDetails;
import de.lemo.dms.service.ServiceCourseTitleSearch;


/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 */
public class Test {

	private static final Long ID_MOODLE23 = 4L;
	private static final Long ID_MOODLE_NUMERIC = 5L;
	private static final Long ID_MOODLE19 = 2L;
	private static final Long ID_CLIX = 6L;
	final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodle23Conn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE23);
		List<Long> courses = new ArrayList<Long>();
		courses.add(7L);
		
		//connector.setCourseIdFilter(courses);
		
		connector.getData();
		
	}
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodleConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE19);
		List<Long> courses = new ArrayList<Long>();
		courses.add(476L);
		courses.add(2200L);
		courses.add(3905L);
		
		connector.setCourseIdFilter(courses);
		
		connector.getData();
		
	}
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodleNumericConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE_NUMERIC);
		List<Long> courses = new ArrayList<Long>();
		courses.add(1197L);
		courses.add(1275L);
		courses.add(1485L);
		
		connector.setCourseIdFilter(courses);
		
		connector.getData();
		
	}
	
	/**
	 * Tests the Clix2010-connector. Configurations have to be altered accordingly.
	 */
	public void runClixConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_CLIX);
		List<Long> courses = new ArrayList<Long>();
		
		
		courses.add(18508964L);
		courses.add(21040950L);
		courses.add(21040967L);
		
		
		
		courses.add(4667155L);
		courses.add(8074949L);
		courses.add(10921956L);
		courses.add(13617310L);
		courses.add(17945446L);
		
		connector.setCourseIdFilter(courses);
		
		connector.getData();
		
	}

	
	public void test()
	{
		QPerformanceHistogram qput = new QPerformanceHistogram();
		QFrequentPathsBIDE bid = new QFrequentPathsBIDE();
		QCumulativeUserAccess cua = new QCumulativeUserAccess();
		QCourseActivity qlou = new QCourseActivity();
		QLearningObjectUsage lou = new QLearningObjectUsage();
		QActivityResourceType art = new QActivityResourceType();
		
		List<Long> gender = new ArrayList<Long>();
		gender.add(0L);
		
		List<Long> courses = new ArrayList<Long>();
		List<Long> users = new ArrayList<Long>();
	//	users.add(2L);
		courses.add(1513617310L);
		
		ArrayList<String> types = new ArrayList<String>();
		//types.add("resource");
		

		
		ServiceCourseDetails scd = new ServiceCourseDetails();
		
		CourseObject co = scd.getCourseDetails(1513617310L);
		
		//art.compute(courses, co.getFirstRequest(), co.getLastRequest(), types, gender);
		
		lou.compute(courses, users, types, co.getFirstRequest(), co.getLastRequest(), gender);
		
//		cua.compute(courses, types, co.getFirstRequest(), co.getLastRequest(), gender);
		
		//qlou.compute(courses, users, 0L, 1500000000L, 100L, new ArrayList<String>(), gender);
		
		//qput.compute(courses, users, new ArrayList<Long>(), 100L, 0L, 1500000000L, gender);
	
		ServiceCourseTitleSearch scts = new ServiceCourseTitleSearch();
		scts.getCoursesByText("Inform", null, null);
		
	}
	

	public void run()
	{
		logger.info("Starting test");
		ServerConfiguration.getInstance().loadConfig("/lemo");
		this.runMoodleNumericConn();
		logger.info("Test finished");
	}

	/**
	 * main method for test case 
	 * TODO move to unit test
	 * @param args
	 */
	public static void main(final String[] args)
	{

		final Test t = new Test();
		t.run();
	}

}
