package com.adanac.tool.java8.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {
	// 声明服务器端必须提供的服务
	String hello(String content) throws RemoteException;
}
