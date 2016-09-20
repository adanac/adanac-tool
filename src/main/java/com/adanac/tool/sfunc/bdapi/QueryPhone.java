package com.adanac.tool.sfunc.bdapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 读取原文本文件并根据号码字段查询归属地后，将查询结果与原内容写入到新文件中 
 * Created by allen
 */
public class QueryPhone {

	private static final String apiKey = "931aa4d10a67bfe2bad401b83deb4e8c";

	public static void main(String[] args) {
		String src = "res/phone.txt";
		String dest = "res/phone_result.txt";
		try {
			rwTxtFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void rwTxtFile(String src, String dest) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(src));
		BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
		String line;
		while ((line = br.readLine()) != null) {
			String[] split = line.split("-");
			String phone = split[2];
			JSONObject jsonObj = proContent(phone);
			String content = jsonObj.getString("province") + jsonObj.getString("city");
			System.out.println(phone + ":" + content);
			bw.write(line + "--" + content);// 把原有内容和查询结果写入目标文件
			bw.newLine();
		}
		br.close();
		bw.close();
	}

	public static JSONObject proContent(String phone) {
		String httpUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
		String httpArg = "phone=" + phone;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		JSONObject paramMap = JSON.parseObject(jsonResult);
		JSONObject jsonObj = (JSONObject) paramMap.get("retData");
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
