 package com.cbt.utilities;

import static com.cbt.utilities.ConfigurationReader.getProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;

	public static boolean establishConnection(DBType dbType) throws SQLException {

		switch (dbType) {
		case ORACLE:
			connection = DriverManager.getConnection(getProperty("oracledb.url"), getProperty("oracledb.user"),
					getProperty("oracledb.password"));
			return true;

		default:
			connection = null;
			return false;

		}
	}

	public static List<Map<String, Object>> runSQLQuery(String sql) throws SQLException {

		if (connection == null) {
			return null;
		}

		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sql);
		List<Map<String, Object>> list = new ArrayList<>();
		ResultSetMetaData rsMetadata = resultSet.getMetaData();

		while (resultSet.next()) {
			Map<String, Object> row = new HashMap<>();

			for (int col = 1; col <= rsMetadata.getColumnCount(); col++) {
				row.put(rsMetadata.getColumnName(col), resultSet.getObject(col));
			}
			list.add(row);
		}
		return list;
	}

	public static boolean closeConnections() {

		try {
			if (resultSet != null && statement != null && connection != null) {
				resultSet.close();
				statement.close();
				connection.close();
				return true;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static int getRowsCount(String sql) throws SQLException {
		if (connection == null) {
			return -1;
		}

		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sql);
		resultSet.last();
		return resultSet.getRow();
	}
	
	

}