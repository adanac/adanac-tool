package com.adanac.tool.sfunc.bdapi;

/**
* @ClassName: HttpClientUtil 
* @Description: html页面抓取素有字符串工具类
* @author: adanac
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger("HttpClient");

	private String readInputStream(InputStream instream, String charest) throws Exception {
		StringBuilder sb = new StringBuilder();
		try (InputStreamReader isr = new InputStreamReader(instream, charest);
				BufferedReader reader = new BufferedReader(isr);) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		}
		return sb.toString();

	}

	public String getWebcontent(String webUrl, String charest) {
		if (StringUtils.isEmpty(webUrl))
			return null;
		int response = -1;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(webUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(60 * 2000);
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			conn.setDoOutput(true);
			conn.connect();
			response = conn.getResponseCode();
			if (response == 200) {
				InputStream im = null;
				try {
					im = conn.getInputStream();
					return readInputStream(im, charest);
				} finally {
					IOUtils.closeQuietly(im);
				}
			}
			return null;
		} catch (Exception e) {
			logger.error(String.format("下载到文件出错[url=%s][%s][responsecode=%d]", webUrl, e.getMessage(), response));
			return null;
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
	}
}