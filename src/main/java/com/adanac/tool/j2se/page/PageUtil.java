package com.adanac.tool.j2se.page;

import java.util.HashMap;
import java.util.Map;

import com.adanac.tool.constant.ToolConstants;

public class PageUtil {

	/**
	 * 分页
	 * @param Map
	 * @return Map
	 */
	public static Map<String, Object> findPageSize(Map<String, Object> paramMap) {
		int pageSize = Double.valueOf(paramMap.get("pageSize").toString()).intValue();
		int pageNumber = Double.valueOf(paramMap.get("pageNumber").toString()).intValue();
		int beginIndex = (pageNumber - 1) * pageSize;
		int endIndex = pageNumber * pageSize;

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("beginIndex", beginIndex);
		resultMap.put("endIndex", endIndex);
		return resultMap;
	}

	/**
	 * 总页数
	 * @param total=总记录数
	 */
	public static int getTotalPage(int total) {
		int totalPage = 0;
		if (total != 0) {
			if (total % ToolConstants.threadConstants.PAGE_SIZE == 0) {
				totalPage = total / ToolConstants.threadConstants.PAGE_SIZE;
			} else {
				totalPage = (total / ToolConstants.threadConstants.PAGE_SIZE) + 1;
			}
		}
		return totalPage;
	}

}
