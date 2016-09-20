package com.adanac.tool.java8.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheDemo1 {
	private static Map<Integer, Integer> cache = new HashMap<>();

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
			System.out.println("f(" + i + ") = " + fibonacci3(i));
	}

	static int fibonacci3(int i) {
		if (i == 0)
			return i;

		if (i == 1)
			return 1;

		return cache.computeIfAbsent(i, (key) -> fibonacci3(i - 2) + fibonacci3(i - 1));
	}

	static int fibonacci2(int i) {
		if (i == 0)
			return i;

		if (i == 1)
			return 1;

		return cache.computeIfAbsent(i, (key) -> fibonacci2(i - 2) + fibonacci2(i - 1));
	}

	static int fibonacci1(int i) {
		if (i == 0)
			return i;

		if (i == 1)
			return 1;

		System.out.println("Calculating f(" + i + ")");
		return fibonacci1(i - 2) + fibonacci1(i - 1);
	}
}
