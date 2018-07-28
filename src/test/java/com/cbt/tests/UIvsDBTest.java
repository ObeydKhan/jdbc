package com.cbt.tests;

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.cbt.pages.HRAppDeptEmpPage;
import com.cbt.utilities.BrowserUtils;
import com.cbt.utilities.DBType;
import com.cbt.utilities.DBUtility;

public class UIvsDBTest extends TestBase {

	private HRAppDeptEmpPage deptEmpPage;
	private Map<String, String> UIDepartmentData;
	private Map<String, Object> DBDepartmentData;

	@Test
	public void deptData() throws SQLException {

		deptEmpPage = new HRAppDeptEmpPage();
		UIDepartmentData = new HashMap<>();
		deptEmpPage.i_search_for_department_id(10);
		// add UI data to hashmap
		UIDepartmentData.put("DEPARTMENT_NAME", deptEmpPage.departmentName.getText());
		UIDepartmentData.put("MANAGER_ID", deptEmpPage.managerID.getText());
		UIDepartmentData.put("LOCATION_ID", deptEmpPage.locationID.getText());

		DBUtility.establishConnection(DBType.ORACLE);
		String sql = "SELECT department_name,manager_id,location_id FROM departments WHERE department_id=10";
		List<Map<String, Object>> dBDataList = DBUtility.runSQLQuery(sql);
		DBDepartmentData = dBDataList.get(0);
		DBUtility.closeConnections();

		// assertEquals(UIDepartmentData, DBDepartmentData); //fails because number type
		// from DB is returned(to Java) as BigDecimal
		assertEquals(UIDepartmentData.get("DEPARTMENT_ID"), DBDepartmentData.get("DEPARTMENT_ID"));
		assertEquals(UIDepartmentData.get("MANAGER_ID"), String.valueOf(DBDepartmentData.get("MANAGER_ID")));
		assertEquals(UIDepartmentData.get("LOCATION_ID"), String.valueOf(DBDepartmentData.get("LOCATION_ID")));
	}

	@Test
	public void firstLastNameSearchByEmail() throws SQLException {

		DBUtility.establishConnection(DBType.ORACLE);
		deptEmpPage = new HRAppDeptEmpPage();
		String input = "JWHALEN";
		deptEmpPage.email.sendKeys(input);

		deptEmpPage.findDetails.click();
		UIDepartmentData = new HashMap<>();
		BrowserUtils.waitForVisibility(deptEmpPage.firstName, 5);
		UIDepartmentData.put("FIRST_NAME", deptEmpPage.firstName.getText());
		UIDepartmentData.put("LAST_NAME", deptEmpPage.lastName.getText());

		String sql = "SELECT first_name, last_name FROM employees WHERE email='JWHALEN'";
		List<Map<String, Object>> dBDataList = DBUtility.runSQLQuery(sql);
		DBDepartmentData = dBDataList.get(0);
		DBUtility.closeConnections();

		assertEquals(UIDepartmentData, DBDepartmentData);
	}

	/*
	 * This test fails because we are reading data from Murodil's Oracle ADF App's
	 * Front End-> which gets different data(From Murodil's database on EC2) than our database on EC2
	 */
	@Test
	public void numberOfEmployeesForDepts() throws SQLException {

		DBUtility.establishConnection(DBType.ORACLE);
		deptEmpPage = new HRAppDeptEmpPage();
		UIDepartmentData = new HashMap<>();
		deptEmpPage.i_search_for_department_id(50);
		deptEmpPage.detach.click();
		BrowserUtils.waitFor(2);
//		BrowserUtils.scrollDown();
		UIDepartmentData.put("EMPLOYEES_COUNT", String.valueOf(deptEmpPage.employeesCount.size()));

		String sql = "SELECT COUNT(*) AS EMPLOYEES_COUNT FROM employees WHERE department_id=50";
		List<Map<String, Object>> dBDataList = DBUtility.runSQLQuery(sql);
		DBDepartmentData = dBDataList.get(0);
		DBUtility.closeConnections();

		for (String key : DBDepartmentData.keySet()) {
			assertEquals(UIDepartmentData.get(key), String.valueOf(DBDepartmentData.get(key)));	
		}
		
	}

}
