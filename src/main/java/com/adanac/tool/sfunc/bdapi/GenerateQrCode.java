package com.adanac.tool.sfunc.bdapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.adanac.tool.common.CommonUtil;
import com.adanac.tool.sfunc.captcha.ImageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 生成二维码图片，并下载图片 Created by allen
 */
public class GenerateQrCode {

	private static final String filePath = "conf/allenKey.properties";
	private static String key = "baiDU_apiKey";
	private static String apiKey = CommonUtil.getPropertyValue(filePath, key);

	public static void main(String[] args) {
		String content = "我的位置，中国!";
		JSONObject jsonObject = generateQr(content);
		String downPath = jsonObject.getString("url");
		String savePath = "res//";
		ImageUtils.downloadImage(savePath, downPath);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 * @return
	 */
	public static JSONObject generateQr(String content) {
		content = URLEncoder.encode(content);
		String httpUrl = "http://apis.baidu.com/3023/qr/qrcode";
		String httpArg = "qr=" + content;
		String jsonResult = request(httpUrl, httpArg);
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
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apiKey);
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
