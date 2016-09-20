package com.adanac.tool.thirdparty.http.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingUtil {

	public static void test_fun1() {
		InetAddress address;
		try {
			address = InetAddress.getByName("www.weibo.com");
			System.out.println("Name: " + address.getHostName());
			System.out.println("Addr: " + address.getHostAddress());
			System.out.println("Reach: " + address.isReachable(3000)); // 是否能通信
																		// 返回true或false
			System.out.println(address.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test_fun2(String ip) {
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		Process process = null; // 声明处理类对象
		String line = null; // 返回行信息
		InputStream is = null; // 输入流
		InputStreamReader isr = null; // 字节流
		BufferedReader br = null;
		boolean res = false;// 结果
		try {
			process = runtime.exec("ping " + ip); // PING
			is = process.getInputStream(); // 实例化输入流
			isr = new InputStreamReader(is);// 把输入流转换成字节流
			br = new BufferedReader(isr);// 从字节中读取文本
			while ((line = br.readLine()) != null) {
				if (line.contains("TTL")) {
					res = true;
					break;
				}
			}
			is.close();
			isr.close();
			br.close();
			if (res) {
				System.out.println(ip + " ping 通 ...");
			} else {
				System.out.println(ip + " ping 不通...");
			}
		} catch (IOException e) {
			System.out.println(e);
			runtime.exit(1);
		}
	}

	public static void test_batch() {
		// initData
		String data = initData();
		System.out.println("初始化数据完成...");
		String[] ips = data.split(",");
		int count = 0;
		for (String ip : ips) {
			boolean flag = ping(ip, 1, 1000);
			if (!flag) {
				System.out.println(ip + " ping 不通...");
			}
			count++;
		}
		System.out.println("测试 " + count + " 条数据完成...");
	}

	private static String initData() {
		String res;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 256; i++) {
			String str = "192.168.1." + i;
			sb.append(str);
			sb.append(",");
		}
		res = sb.substring(0, sb.lastIndexOf(","));
		return res;
	}

	public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime(); // 将要执行的ping命令,此命令是windows格式的命令
		String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
		try { // 执行命令并获取输出
			// System.out.println(pingCommand);
			Process p = r.exec(pingCommand);
			if (p == null) {
				return false;
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream())); // 逐行检查输出,计算类似出现=23ms
																				// TTL=62字样的次数
			int connectedCount = 0;
			String line = null;
			while ((line = in.readLine()) != null) {
				connectedCount += getCheckResult(line);
			} // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
			return connectedCount == pingTimes;
		} catch (Exception ex) {
			ex.printStackTrace(); // 出现异常则返回假
			return false;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
	private static int getCheckResult(String line) { // System.out.println("控制台输出的结果为:"+line);
		Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) {
		// System.out.println(initData());
		test_batch();
	}
}
