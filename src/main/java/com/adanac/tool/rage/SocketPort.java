package com.adanac.tool.rage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketPort {
	static String ip = "121.199.42.197";
	static String hostname = new String();

	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getByName(ip);
			System.out.println(address);
			hostname = address.getHostName();
			System.out.println(hostname);
		} catch (UnknownHostException e) {
			System.out.println("could not find " + ip);
		}

		try {
			PrintWriter pw = new PrintWriter(new FileWriter("portInfo.txt"));
			pw.println("Infomation of the Port on " + hostname + " computer...");
			pw.println();
			// do port scan
			for (int nport = 25; nport < 30; ++nport) {
				try {
					Socket s = new Socket(hostname, nport);
					pw.println("The port " + nport + " is open!");
					pw.println("Connect to " + s.getInetAddress() + " on port " + s.getPort() + " from port"
							+ s.getLocalPort() + " of " + s.getLocalAddress());

				} catch (Exception e) {
					pw.println("The port " + nport + " is closed!");
				}
			}
		} catch (IOException e) {
		}
	}
}
