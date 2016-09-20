package com.adanac.tool.j2se.db.table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/***
 * oracle数据库， 表结构查询 ，字段信息查询，字段注释查询 表字段查询 all_tab_columns 表字段注释查询
 * all_col_comments
 */
public class OracleTableUtil {

	private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String DATABASE_URL = "jdbc:oracle:thin:@192.168.1.10:1521:orcl";
	private static final String DATABASE_USER = "dev";
	private static final String DATABASE_PASSWORD = "dev";
	private static Connection con = null;

	public static Connection getConnection() {
		try {
			Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
			return con;
		} catch (Exception ex) {
			System.out.println("getConnection error---> " + ex.getMessage());
		}
		return con;
	}

	/***
	 * 
	 * 
	 */
	public static List<HashMap<String, String>> showTableCloumns(String Table) throws SQLException {
		getConnection();
		List<HashMap<String, String>> columns = new ArrayList<HashMap<String, String>>();
		try {
			Statement stmt = con.createStatement();
			String sql = "select id,deptCode,userName * from tab_common";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", rs.getString("id"));
				map.put("deptCode", rs.getString("deptCode"));
				map.put("userName", rs.getString("userName"));
				columns.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return columns;
	}

	/**
	 * 获取数据库表的名字
	 */
	public static List<Map<String, String>> getTableNameByCon(Connection con) {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			if (rs != null) {
				while (rs.next()) {
					Map<String, String> colmap = new LinkedHashMap<>();
					colmap.put("表所属用户名", rs.getString(2));
					colmap.put("表名", rs.getString(3));
					list.add(colmap);
				}
			}
			con.close();
		} catch (Exception e) {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return list;
	}

}
