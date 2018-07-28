package com.cbt.tests;

import static com.cbt.utilities.ConfigurationReader.getProperty;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class JDBCConnection {

	@Test(enabled = false)
	public void oracleJDBC() throws SQLException {
		Connection connection = DriverManager.getConnection(getProperty("oracledb.url"), getProperty("oracledb.user"),
				getProperty("oracledb.password"));
//		Statement statement = connection.createStatement(); // Statement is an interface
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // unsynchronized
		ResultSet resultSet = statement.executeQuery("SELECT * FROM countries"); // We dont semi-colon in query//
																					// ResultSet is an interface
//		resultSet.next(); //Goes to first row, from header.

//		while (resultSet.next()) {
//			System.out.print(resultSet.getString(1) + "--"); // We can use both index and name of column
//			System.out.print(resultSet.getObject("country_name") + "--");
//			System.out.print(resultSet.getInt("region_id") + "\n");
//		}

		resultSet.last();
		int rowsCount = resultSet.getRow();
		System.out.println("Number of rows:" + rowsCount);

		resultSet.beforeFirst();
		while (resultSet.next()) {
			System.out.print(resultSet.getString(1) + "--"); // We can use both index and name of column
			System.out.print(resultSet.getObject("country_name") + "--");
			System.out.print(resultSet.getInt("region_id") + "\n");
		}

		resultSet.close();
		statement.close();
		connection.close();
	}

	@Test(enabled = false)
	public void jdbcMetadata() throws SQLException {
		Connection connection = DriverManager.getConnection(getProperty("oracledb.url"), getProperty("oracledb.user"),
				getProperty("oracledb.password"));
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String sql = "SELECT employee_id, last_name, job_id, salary from employees";
//		String sql = "SELECT * from employees";
		ResultSet resultSet = statement.executeQuery(sql);

		// Database Metadata
		DatabaseMetaData dbMetadata = connection.getMetaData(); // DatabaseMetaData is an interface
		System.out.println("User:" + dbMetadata.getUserName());
		System.out.println("Product_Name:" + dbMetadata.getDatabaseProductName());

		// resultSet Metadata
		ResultSetMetaData rsMedata = resultSet.getMetaData(); // ResultSetMetaData is an interface
		System.out.println("Columns count: " + rsMedata.getColumnCount());
//		System.out.println(rsMedata.getColumnName(1));

		for (int i = 1; i <= rsMedata.getColumnCount(); i++) {
			System.out.println(i + " -> " + rsMedata.getColumnName(i));
		}

		resultSet.close();
		statement.close();
		connection.close();
	}

	@Test
	public void storingMetaData() throws SQLException {
		Connection connection = DriverManager.getConnection(getProperty("oracledb.url"), getProperty("oracledb.user"),
				getProperty("oracledb.password"));
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String sql = "SELECT employee_id, last_name, job_id, salary from employees";
		ResultSet resultSet = statement.executeQuery(sql);

		// Throw resultSet into a List of Maps
		List<Map<String, Object>> list = new ArrayList<>(); // Create a list of Maps

		// resultSet Metadata
		ResultSetMetaData rsMedata = resultSet.getMetaData(); // ResultSetMetaData is an interface
		int colCount = rsMedata.getColumnCount();

		
		while (resultSet.next()) {
			Map<String, Object> rowMap = new HashMap<>();

			for (int col = 1; col <= colCount; col++) {
				rowMap.put(rsMedata.getColumnName(col),resultSet.getObject(col));
			}
			list.add(rowMap);
		}

		for (Map<String, Object> row : list) {
			System.out.println(row.get("EMPLOYEE_ID")); 
		}
		resultSet.close();
		statement.close();
		connection.close();
	}

}
