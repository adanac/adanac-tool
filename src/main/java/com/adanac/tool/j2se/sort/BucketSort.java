package com.adanac.tool.j2se.sort;

import java.util.Random;

/**
 * 桶排序
 */
public class BucketSort {

	/**
	 * 从小到大排序
	 * 
	 * @param res
	 * @param sort
	 *            isAsc
	 * @return
	 */
	public static String _bucket_sort(int[] res, boolean sort) {
		StringBuilder sb = new StringBuilder();
		if (sort) {
			for (int i = 0; i < res.length; i++) {
				for (int j = 0; j < res[i]; j++) {
					sb.append(i + ",");
				}
			}
		} else {
			for (int i = res.length - 1; i > 0; i--) {
				for (int j = 0; j < res[i]; j++) {
					sb.append(i + ",");
				}
			}
		}
		String data = sb.substring(0, sb.lastIndexOf(","));
		return data;
	}

	/**
	 * 
	 * @param n
	 *            有几个数
	 * @param bounder
	 *            最大值
	 * @return
	 */
	public static int[] initData(int n, int bounder) {
		int res[] = new int[bounder];
		for (int i = 0; i < n; i++) {
			int num = Math.abs(new Random().nextInt(bounder));
			res[num]++;
		}
		return res;
	}
}
