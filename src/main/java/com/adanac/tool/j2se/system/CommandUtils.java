package com.adanac.tool.j2se.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 执行命令行命令
 * @author <a href="http://www.xdemo.org/">http://www.xdemo.org/</a>
 * 252878950@qq.com
 */
public class CommandUtils {

	/**
	 * 执行命令
	 * @param commandLine
	 * @return String 执行结果
	 * @throws Exception
	 */
	public static String execute(String commandLine) {

		String[] cmd = new String[3];
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name").toLowerCase();
		String charset = null;
		String result = "";

		if (osName.startsWith("windows")) {
			cmd[0] = "cmd.exe";
			cmd[1] = "/C";
			cmd[2] = commandLine;
			charset = "GBK";
		} else if (osName.startsWith("linux")) {
			cmd[0] = "sh";
			charset = "UTF-8";
		}

		Process ps;
		try {
			ps = Runtime.getRuntime().exec(cmd);
			String line = null;
			BufferedReader input = new BufferedReader(new InputStreamReader(ps.getInputStream(), charset));
			while ((line = input.readLine()) != null) {
				result += line + "\n";
			}
			input.close();
			ps.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(execute("calc"));
	}
}
