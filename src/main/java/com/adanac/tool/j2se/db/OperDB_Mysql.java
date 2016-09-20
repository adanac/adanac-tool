package com.adanac.tool.j2se.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class OperDB_Mysql {
	private Connection conn = null;

	public OperDB_Mysql(String url, String username, String password) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeDb() throws SQLException {
		conn.close();
	}

	public void executeStatement(String username, String pwd) throws SQLException {
		String sql = "SELECT * FROM tab3 where name='" + username + "' and pass='" + pwd + "'";
		System.out.println("executeStatement-sql: " + sql);
		java.sql.Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		showResultSet(rs);
		stmt.close();
	}

	public void executePreparedStatement(String username, String pwd) throws SQLException {
		java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tab3 where name=? and pass=?");
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
			OperDB_Mysql db = new OperDB_Mysql(
					"jdbc:mysql://192.168.2.144:3306/demo?useUnicode=true&characterEncoding=utf8", "root", "");
			// db.executeStatement("aaa", "bbb");
			// db.executeStatement("aaa", "' or '2'='2");
			//
			// db.executePreparedStatement("u1", "pwd");
			db.executePreparedStatement("u1", "' or '2'='2");

			db.closeDb();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
