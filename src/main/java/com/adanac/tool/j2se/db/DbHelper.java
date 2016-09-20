package com.adanac.tool.j2se.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

/**
 * QueryRunner工具类 Created by allen
 */
public class DbHelper {
	/**
	 * getQueryRunner
	 * 
	 * @return
	 */
	public static QueryRunner getQueryRunner() {
		// 数据源对象可以理解为连接池的管理者，通过他可以获取数据库的连接
		DataSource ds = null;
		try {
			// 通过在context.xml文件，设定的数据源对象的名字，获取数据源对象
			Context context = new InitialContext();
			// ds = (DataSource) context.lookup("java:/comp/env/jdbc/mysqlds");
			ds = (DataSource) context.lookup("java:/jdbc/demo");
			System.out.println("mysql数据库连接池连接成功");
		} catch (Exception e) {
			System.out.println("获取数据源时出错");
		}

		QueryRunner qr = new QueryRunner(ds);

		return qr;
	}

	public static void main(String[] args) {
		getQueryRunner();
	}
}
