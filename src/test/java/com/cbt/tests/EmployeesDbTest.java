package com.cbt.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cbt.utilities.DBType;
import com.cbt.utilities.DBUtility;

public class EmployeesDbTest {

		@BeforeClass
		public void setUp() throws SQLException {
			DBUtility.establishConnection(DBType.ORACLE);
		}
		
		@AfterClass
		public void tearDown() {
			DBUtility.closeConnections();

		}
		
	
	@Test
	public void countTest() throws SQLException {
		//Automation of queries
		/*Connect to oracle database
		 * run following sql query
		 * select * from employees where job_id='IT_PROG'
		 * more than 0 records should be returned
		 */	
		int rowsCount=DBUtility.getRowsCount("SELECT * FROM employees WHERE job_id='IT_PROG'");
		assertTrue(rowsCount>0);
			}
	
	@Test
	public void nameTestByID() throws SQLException {
		/* Connect to oracle database
		 * Employee's firstName and lastName with Employee id 105 should be, "David Austin"
		 */
		
		String sql="SELECT first_name,last_name FROM employees WHERE employee_id=105";
		List<Map<String,Object>> actual=DBUtility.runSQLQuery(sql);
		System.out.println(actual);
		assertEquals(actual.get(0).get("FIRST_NAME"), "David");
		assertEquals(actual.get(0).get("LAST_NAME"), "Austin");
			}
	
	


}
