package com.adanac.tool.thirdparty.http;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.adanac.tool.constant.ToolConstants;

public class DefaultUtil {

	/**
	 * @param request
	 * @return ip地址
	 */
	public static String ipAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 生产商户编码
	 */
	public static String getMerchantCode(Long merchantId) {
		String code = "";
		if (null != merchantId && merchantId != ToolConstants.intNum.NUM_0) {
			String merchantCode = merchantId.toString();
			while (merchantCode.length() < ToolConstants.MERCHANT_CODE_LENGTH) {
				merchantCode = ToolConstants.strNum.STR_0 + merchantCode;
			}
			code = ToolConstants.MERCHANT_CODE + merchantCode;
		}
		return code;
	}

}
