package com.adanac.tool.j2se.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class OperDB_Oracle {
	private Connection conn = null;

	public OperDB_Oracle(String url, String username, String password) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeDb() throws SQLException {
		conn.close();
	}

	public void executeStatement(String username, String pwd) throws SQLException {
		String sql = "SELECT * FROM TEST_USER where username='" + username + "' and pwd='" + pwd + "'";
		System.out.println("executeStatement-sql: " + sql);
		java.sql.Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		showResultSet(rs);
		stmt.close();
	}

	public void executePreparedStatement(String username, String pwd) throws SQLException {
		java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TEST_USER where username=? and pwd=?");
		stmt.setString(1, username);
		stmt.setString(2, pwd);
		ResultSet rs = stmt.executeQuery();
		showResultSet(rs);
		stmt.close();
	}

	public void showResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		StringBuffer sb = new StringBuffer();
		int colCount = meta.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			sb.append(meta.getColumnName(i)).append("[").append(meta.getColumnTypeName(i)).append("]").append("\t");
		}
		while (rs.next()) {
			sb.append("\r\n");

			for (int i = 1; i <= colCount; i++) {
				sb.append(rs.getString(i)).append("\t");
			}
		}
		// 关闭ResultSet
		rs.close();

		System.out.println(sb.toString());
	}

	public static void main(String[] args) throws SQLException {
		try {
			OperDB_Oracle db = new OperDB_Oracle("jdbc:oracle:thin:@192.xxx.xxx.xxx:1521:xxx", "xxx", "xxx");

			db.executeStatement("aaa", "bbb");
			db.executeStatement("aaa", "' or '2'='2");

			db.executePreparedStatement("aaa", "bbb");
			db.executePreparedStatement("aaa", "' or '2'='2");

			db.closeDb();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
