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

import com.adanac.framework.utils.StringUtils;
import com.adanac.tool.common.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 获取图片
 */
public class QueryImg {

	private static final String filePath = "conf/allenKey.properties";
	private static String key = "baiDU_apiKey";
	private static String apiKey = CommonUtil.getPropertyValue(filePath, key);

	public static void main(String[] args) {
		String dest = "res/img_result.txt";
		try {
			rwTxtFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void rwTxtFile(String dest) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(dest));
		String line = br.readLine();
		String size = "";
		if (line == null || StringUtils.isEmpty(line)) {
			size = "10";
		} else {
			size = line.trim();
		}
		br.close();
		JSONObject jsonObj = proContent(size);
		BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
		bw.newLine();
		bw.write(jsonObj.toJSONString());// 把原有内容和查询结果写入目标文件
		bw.close();
	}

	public static JSONObject proContent(String size) {
		String httpUrl = "http://apis.baidu.com/txapi/mvtp/meinv";
		String httpArg = "num=" + size;
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
