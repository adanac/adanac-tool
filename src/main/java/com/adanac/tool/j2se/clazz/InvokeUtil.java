package com.adanac.tool.j2se.clazz;

/**
 * 解决 if-else比较多的情况
 * 
 * @author adanac
 */
public class InvokeUtil {

	// 用于测试
	public void func1(String s) {
		System.out.println("====func1====" + s);
	}

	public void func2(String s) {
		System.out.println("====func2====" + s);
	}

	public void func3(String s) {
		System.out.println("====func3====" + s);
	}

	public static void invoke(Object o, String method, String param) throws Exception {
		o.getClass().getMethod(method, new Class[] { String.class }).invoke(o, new Object[] { param });
	}

}
