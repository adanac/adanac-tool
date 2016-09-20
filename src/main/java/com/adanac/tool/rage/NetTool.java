package com.adanac.tool.rage;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetTool {

	static InetAddress myIpAddress = null;
	static InetAddress myServer = null;

	public static void main(String[] args) {
		System.out.println("localhost:" + getMyIp());
		System.out.println("serverIp:" + getServerIp("wooyun.com"));
	}

	// 获得localhost的ip
	public static InetAddress getMyIp() {
		try {
			myIpAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return myIpAddress;
	}

	// 获得 www.baidu.com 的ip
	public static InetAddress getServerIp(String host) {
		try {
			myServer = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return myServer;
	}
}
