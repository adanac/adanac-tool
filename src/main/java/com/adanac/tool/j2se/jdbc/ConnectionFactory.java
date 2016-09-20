package com.adanac.tool.j2se.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

/**
 * Connect to DataBase
 */
public class ConnectionFactory {

	public static final String URL = "jdbc:mysql://192.168.1.173:3306/demo";
	public static final String USER = "root";
	public static final String PASS = "root";

	/**
	 * Get a connection to database
	 * 
	 * @return Connection object
	 */
	public static Connection getConnection() {
		try {
			DriverManager.registerDriver(new Driver());
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException ex) {
			throw new RuntimeException("Error connecting to the database", ex);
		}
	}

}
