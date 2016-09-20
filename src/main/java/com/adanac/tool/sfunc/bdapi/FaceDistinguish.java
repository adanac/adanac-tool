package com.adanac.tool.sfunc.bdapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.adanac.tool.common.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 人脸识别，待研究
 */
public class FaceDistinguish {

	private static final String filePath = "conf/allenKey.properties";
	private static String key = "baiDU_apiKey";
	private static String apiKey = CommonUtil.getPropertyValue(filePath, key);

	public static void main(String[] args) {
		queryFace();
	}

	private static void queryFace() {
		String ip = "121.237.180.117";
		proContent(ip);
	}

	public static JSONObject proContent(String ip) {
		String httpUrl = "http://apis.baidu.com/idl_baidu/faceverifyservice/face_deleteuser";
		String httpArg = "username=test&cmdid=1000&logid=12345&appid=" + apiKey + "&clientip=" + ip
				+ "&type=st_groupverify&groupid=0&versionnum=1.0.0.1";
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		JSONObject jsonObj = JSON.parseObject(jsonResult);
		return jsonObj;
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			// set params
			connection.setRequestProperty("jsonrpc", "2.0");
			connection.setRequestProperty("method", "Delete");
			connection.setRequestProperty("username", "lucy2me");
			// connection.setRequestProperty("", "");
			// connection.setRequestProperty("", "");
			// connection.setRequestProperty("", "");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apiKey);
			connection.setDoOutput(true);
			connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
