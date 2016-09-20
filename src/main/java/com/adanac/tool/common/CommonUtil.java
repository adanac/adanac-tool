package com.adanac.tool.common;

import java.io.IOException;
import java.util.Properties;

import com.adanac.framework.utils.MD5;
import com.adanac.framework.utils.StringUtils;
import com.adanac.tool.j2se.io.PropertiesUtils;

public class CommonUtil {

	public static boolean validate(String sign, String secretKey, String... args) {
		if (!StringUtils.isEmpty(sign) && null != args && args.length > 0) {
			StringBuilder sb = new StringBuilder(secretKey);
			for (String arg : args) {
				if (!StringUtils.isEmpty(arg)) {
					sb.append(arg);
				}
			}
			String md5 = MD5.encode(sb.toString());
			return sign.equalsIgnoreCase(md5);
		}
		return false;
	}

	public static String getPropertyValue(String filePath, String key) {
		String property = "";
		try {
			Properties properties = PropertiesUtils.readProperties(filePath);
			property = properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property;
	}
}
