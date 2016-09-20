package com.adanac.tool.j2se.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;

import com.adanac.tool.j2se.db.MySql;

public class ReadFile2DB {

	public static void update2table(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String temp = null;
			int count = 0;
			// open connection
			MySql mysql = new MySql();
			Connection conn = mysql.getConn();
			while ((temp = br.readLine()) != null) {
				count++;
				temp = temp.trim();
				String insertSQl = "update tab2 set psd = '" + temp + "' where id=" + count;
				// write into db
				mysql.datatoMySql(conn, insertSQl);
			}
			System.out.println("成功更新" + count + "条数据...");
			mysql.closeConn(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insert2table(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String temp = null;
			int count = 0;
			// open connection
			MySql mysql = new MySql();
			Connection conn = mysql.getConn();
			while ((temp = br.readLine()) != null) {
				count++;
				temp = temp.trim();
				String insertSQl = "insert into tab2(email) values(\"" + temp + "\")";
				// write into db
				mysql.datatoMySql(conn, insertSQl);
			}
			System.out.println("成功插入" + count + "条数据...");
			mysql.closeConn(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String filename = "res/mail.txt";
		// insert2table(filename);
		filename = "res/passwd.txt";
		update2table(filename);
	}
}
