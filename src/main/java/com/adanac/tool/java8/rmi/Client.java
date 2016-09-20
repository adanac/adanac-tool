package com.adanac.tool.java8.rmi;

import java.rmi.Naming;

public class Client {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		String url = "rmi://localhost:8888/";
		try {
			// 在RMI服务注册表中查找名称为service02的对象，并调用其上的方法
			HelloService service02 = (HelloService) Naming.lookup(url + "service02");

			Class stubClass = service02.getClass();
			System.out.println(service02 + " 是 " + stubClass.getName() + " 的实例！");
			// 获得本底存根已实现的接口类型
			Class[] interfaces = stubClass.getInterfaces();
			for (Class c : interfaces) {
				System.out.println("存根类实现了 " + c.getName() + " 接口！");
			}
			System.out.println(service02.hello("你好！"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
