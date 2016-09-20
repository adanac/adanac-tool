package com.adanac.tool.java8.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//UnicastRemoteObject用于导出的远程对象和获得与该远程对象通信的存根
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

	private static final long serialVersionUID = -8447746462052949389L;
	@SuppressWarnings("unused")
	private String name;

	protected HelloServiceImpl(String name) throws RemoteException {
		this.name = name;
	}

	@Override
	public String hello(String content) throws RemoteException {

		return "server>> " + content;
	}

}
