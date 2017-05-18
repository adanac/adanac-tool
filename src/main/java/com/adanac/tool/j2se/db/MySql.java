package com.adanac.tool.j2se.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 加載配置文件實現MySql数据库操作
 */
public class MySql {

	// 定义执行语句
	public Statement stmt = null;

	// 创建数据库连接
	public Connection getConn() {
		Connection conn = null;
		try {
			conn = JdbcConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 执行完关闭数据库连接
	public void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建一个插入数据的方法 executeUpdate()
	 * 
	 * @param insertSQl
	 */
	public void datatoMySql(Connection conn, String insertSQl) {

		try {

			// 创建一个 Statement 对象来将 SQL 语句发送到数据库
			stmt = conn.createStatement();
			// 执行SQL 插入语句
			stmt.executeUpdate(insertSQl);
			// 执行完 停止执行语句
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 创建一个用于select查看数据的方法 executeQuery();
	 * 
	 * @param strSelect
	 * @return ResultSet
	 */
	public ResultSet queryMySql(Connection conn, String strSelect) {
		// 创建一个数据集 用于获取查询到的行数据
		ResultSet rs = null;
		try {

			// 创建一个 Statement 对象来将 SQL 语句发送到数据库
			stmt = conn.createStatement();
			// 执行查询语句 获取ResultSet对象
			rs = stmt.executeQuery(strSelect);
			rs.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		// 返回结果集
		return rs;
	}

	public static void main(String[] args) {
		MySql mysql = new MySql();
		Connection conn = mysql.getConn();
		String insertSQl = "insert into tab1 (url,title,content) values('hzw.com','hzw163','test')";
		insertSQl = "INSERT INTO tab_common (id, username, passwd) VALUES(2,'Olivia' OR UPDATEXML(1,CONCAT(0x7e,(VERSION())),0) OR'','Nervo')";
		insertSQl = "INSERT INTO tab_common (id, username, passwd) VALUES(2,'r00tgrok1' or updatexml(0,concat(0x7e,(SELECT concat(table_name) FROM information_schema.tables WHERE table_schema=database() limit 0,1)),0) or '', 'ohmygod_is_r00tgrok')";
		mysql.datatoMySql(conn, insertSQl);
	}
}