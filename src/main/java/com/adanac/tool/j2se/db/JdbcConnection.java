package com.adanac.tool.j2se.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 数据库连接工具类
 * 
 * @author adanac
 * @version 1.0
 */
public class JdbcConnection {

	// 定义线程本地变量，每个线程访问它都会获得不同的对象
	// 使用ThreadLocal使一个连接绑定到一个线程上
	private static ThreadLocal<Connection> currentConnection = new ThreadLocal<Connection>();
	private static String username = null; // 用户名
	private static String password = null; // 密码
	private static String dbType = null;// 数据库类型
	private static String dbName = null; // 数据库名称
	private static String ip = null; // 数据库服务器IP地址
	private static String resourceName = null; // 为null时不使用连接池，
												// jdbc/mysql或jdbc/oracle或jdbc/derby

	private static void initParams() {
		username = DbConfig.getInstance().getDb_username();
		password = DbConfig.getInstance().getDb_password();
		dbType = DbConfig.getInstance().getDb_type();
		dbName = DbConfig.getInstance().getDb_name();
		ip = DbConfig.getInstance().getIp();
	}

	/**
	 * 
	 * @return 得到一个数据库连接
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		initParams();// 加载配置文件获取属性值
		Connection conn = currentConnection.get();
		if (conn == null) {
			if (null == resourceName) {
				if ("mysql".equals(dbType.toLowerCase())) {
					conn = getMySqlConnection();
				} else if ("oracle".equals(dbType.toLowerCase())) {
					conn = getOracleConnection();
				} else if ("derby".equals(dbType.toLowerCase())) {
					conn = getDerbyConnection();
				} else {
					System.out.println("在 JdbcConnection.java 中数据库类型没有设置");
					throw new SQLException("数据库类型未设置");
				}
			} else {
				conn = getConnectionByPool();
			}
			currentConnection.set(conn);
		}
		return conn;
	}

	/**
	 * 关闭Oracle数据库连接
	 * 
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		Connection conn = currentConnection.get();
		conn.close();
		currentConnection.set(null);
	}

	// 获得Oracle数据库连接
	private static Connection getOracleConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); // 加载驱动
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":1521:" + dbName, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Oracle驱动没找到");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 获得MySql数据库连接
	private static Connection getMySqlConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // 加载驱动
			String url = "jdbc:mysql://" + ip + ":3306/" + dbName + "?useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("MySql驱动没找到");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 获取Derby数据库连接
	private static Connection getDerbyConnection() {
		Connection conn = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance(); // 加载驱动
			String url = "jdbc:derby://" + ip + ":1527/" + dbName + ";create=true";
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Derby驱动没找到");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 获取连接池连接
	private static Connection getConnectionByPool() {
		try {
			Context ctx = new InitialContext();
			Context subContext = (Context) ctx.lookup("java:comp/env");
			String dsName = "";
			dsName = resourceName;

			DataSource dataSource = (DataSource) subContext.lookup(dsName);
			// 上面两句可以合写成下边这句
			// ctx.lookup("java:comp/env/jdbc/oracle");// java:comp/env/
			// 规定：加前缀指定资源
			return dataSource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
