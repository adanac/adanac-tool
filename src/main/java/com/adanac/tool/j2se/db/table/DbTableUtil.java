package com.adanac.tool.j2se.db.table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.adanac.tool.j2se.db.MySql;

public class DbTableUtil {
	/**
	 * 根据数据库表名获取表各字段的详细信息
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<Map<String, Object>> getTableInfoByName(String tableName) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new LinkedHashMap<>();
		// open connection
		MySql mysql = new MySql();
		Connection conn = mysql.getConn();
		String sql = "select * from " + tableName;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			// 获得所有列的数目及实际列数
			int columnCount = data.getColumnCount();
			map.put("columnCount", columnCount);
			if (rs.next()) {
				for (int i = 1; i <= data.getColumnCount(); i++) {
					Map<String, Object> colmap = new LinkedHashMap<String, Object>();
					// 获得指定列的列名
					String columnName = data.getColumnName(i);
					// 获得指定列的注释
					String columnRemark = data.getColumnLabel(i);
					// 获得指定列的列值
					String columnValue = rs.getString(i);
					// 获得指定列的数据类型
					int columnType = data.getColumnType(i);
					// 获得指定列的数据类型名
					String columnTypeName = data.getColumnTypeName(i);
					// 所在的Catalog名字
					String catalogName = data.getCatalogName(i);
					// 对应数据类型的类
					String columnClassName = data.getColumnClassName(i);
					// 在数据库中类型的最大字符个数
					int columnDisplaySize = data.getColumnDisplaySize(i);
					// 默认的列的标题
					String columnLabel = data.getColumnLabel(i);
					// 获得列的模式
					String schemaName = data.getSchemaName(i);
					// 某列类型的精确度(类型的长度)
					int precision = data.getPrecision(i);
					// 小数点后的位数
					int scale = data.getScale(i);
					// 获取某列对应的表名
					String tableName2 = data.getTableName(i);
					// 是否自动递增
					boolean isAutoInctement = data.isAutoIncrement(i);
					// 在数据库中是否为货币型
					boolean isCurrency = data.isCurrency(i);
					// 是否为空
					int isNullable = data.isNullable(i);
					// 是否为只读
					boolean isReadOnly = data.isReadOnly(i);
					// 能否出现在where中
					boolean isSearchable = data.isSearchable(i);

					colmap.put("名称", columnName);
					colmap.put("注释", columnRemark);
					colmap.put("值", columnValue);
					colmap.put("类型，返回SqlType的编号", columnType);
					colmap.put("数据类型名", columnTypeName);
					colmap.put("所在的Catalog名字", catalogName);
					colmap.put("对应数据类型的类", columnClassName);
					colmap.put("在数据库中类型的最大字符个数", columnDisplaySize);
					colmap.put("默认的列的标题", columnLabel);
					colmap.put("模式", schemaName);
					colmap.put("类型的精确度(类型的长度)", precision);
					colmap.put("小数点后的位数", scale);
					colmap.put("对应的表名", tableName2);
					colmap.put("是否自动递增", isAutoInctement);
					colmap.put("在数据库中是否为货币型", isCurrency);
					colmap.put("是否为空", isNullable);
					colmap.put("是否只读", isReadOnly);
					colmap.put("能否出现在where中", isSearchable);
					map.put("第" + i + "列【" + columnName + "】的信息", colmap);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("数据库连接失败:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 根据数据库表名获取字段名的集合
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<String> getColNamesByTableName(String tableName) {
		List<String> res = new ArrayList<String>();
		// open connection
		MySql mysql = new MySql();
		Connection conn = mysql.getConn();
		String sql = "select * from " + tableName;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			if (rs.next()) {
				for (int i = 1; i <= data.getColumnCount(); i++) {
					String columnName = data.getColumnName(i);
					res.add(columnName);
				}
			}
		} catch (Exception e) {
			System.err.println("执行错误");
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 根据数据库名获取表名的集合
	 * 
	 * @param dbName
	 * @return
	 */
	public static List<String> getTabNamesBydbName(Connection conn) {
		List<String> res = new ArrayList<String>();
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				System.out.println("表名：" + rs.getString(3));
				System.out.println("表所属用户名：" + rs.getString(2));
				System.out.println("------------------------------");
				res.add(rs.getString(3));
			}
		} catch (Exception e) {
			System.err.println("执行错误");
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 获得某表中所有字段的注释
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getColumnCommentByTableName(List<String> tableNameList) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		MySql mysql = new MySql();
		Connection conn = mysql.getConn();
		Statement stmt = conn.createStatement();
		for (int i = 0; i < tableNameList.size(); i++) {
			String table = (String) tableNameList.get(i);
			ResultSet rs = stmt.executeQuery("show full columns from " + table);
			System.out.println("【" + table + "】");
			if (rs != null) {
				while (rs.next()) {
					System.out.println("字段名称：" + rs.getString("Field") + "\t" + "字段注释：" + rs.getString("Comment"));
					System.out.println(rs.getString("Field") + "\t:\t" + rs.getString("Comment"));
					map.put(rs.getString("Field"), rs.getString("Comment"));
				}
			}
			rs.close();
		}
		stmt.close();
		conn.close();
		return map;
	}

}
