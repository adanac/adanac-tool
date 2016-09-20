package com.adanac.tool.thirdparty.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.adanac.framework.web.controller.BaseResult;
import com.adanac.tool.entity.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonTools {

	/**
	 * 将java对象转换为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(obj);// 将java对象转换为json对象
		String str = json.toString();// 将json对象转换为字符串
		return str;
	}

	public static Object getValueFromJson(String jsonStr) {
		net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(jsonStr);
		return jsonObj.getJSONObject("states").getJSONObject("rect1").get("type");
	}

	/**
	 * 将json字符串转为对象
	 */
	public static net.sf.json.JSONObject jsonStr2JsonObj(String jsonStr) {
		net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(jsonStr);
		return jsonObj;
	}

	public static com.alibaba.fastjson.JSONObject jsonStr2JsonObj2(String phone) {
		String httpUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
		String httpArg = "phone=" + phone;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		com.alibaba.fastjson.JSONObject paramMap = JSON.parseObject(jsonResult);
		com.alibaba.fastjson.JSONObject jsonObj = (com.alibaba.fastjson.JSONObject) paramMap.get("retData");
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
			connection.setRequestProperty("apikey", "apiKey");
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

	public static void testGetValueFromJson() {
		String jsonStr = "{states:{rect1:{type:'start',text:{text:'开始'}, "
				+ "attr:{ x:448, y:105, width:50, height:50}, " + "props:{text:{value:'开始'},temp1:{value:''},"
				+ "temp2:{value:''}}},rect2:{type:'state',text:{text:'camel_element'}, "
				+ "attr:{ x:431, y:224, width:100, height:50}, props:{text:{value:'camel_element'},"
				+ "temp1:{value:'http://www/baidu.com'}}},rect3:{type:'end',text:{text:'结束'}, "
				+ "attr:{ x:454, y:365, width:50, height:50}, props:{text:{value:'结束'},"
				+ "temp1:{value:''},temp2:{value:''}}}},paths:{path4:{from:'rect1',to:'rect2', dots:[],"
				+ "text:{text:'TO camel_element'},textPos:{x:0,y:-10}, props:{text:{value:''}}},"
				+ "path5:{from:'rect2',to:'rect3', dots:[],text:{text:'TO 结束'},textPos:{x:0,y:-10}, "
				+ "props:{text:{value:''}}}},props:{props:{name:{value:'新建流程'},key:{value:''}," + "desc:{value:''}}}}";
		Object object = JsonTools.getValueFromJson(jsonStr);
		System.out.println(object.toString());
	}

	public static String queryCategoryHtml(Long companyId, String classPath) {
		BaseResult baseResult = new BaseResult();
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 3; i++) {
			User user = new User("allen" + i + 1, "psd" + i + 1);
			userList.add(user);
		}
		String jsonList = ParseJson.Objects2Json(userList);
		baseResult.setContent(jsonList);
		if (null == baseResult.getContent()) {
			return "";
		}
		JSONArray arrary = (JSONArray) baseResult.getContent();
		StringBuilder sb = new StringBuilder();
		if (classPath == null || classPath.trim().equals("")) {
			arrary.forEach(e -> {
				JSONObject jobj = JSONObject.parseObject(e.toString());
				String temp = jobj.getLong("navId") + ":" + jobj.getString("navName");
				String name = jobj.getString("navName");
				sb.append("<option  value=\"" + temp + "\">" + name + "</option>");
			});
		} else {
			arrary.forEach(e -> {
				JSONObject jobj = JSONObject.parseObject(e.toString());
				String temp = jobj.getLong("navId") + ":" + jobj.getString("navName");
				String name = jobj.getString("navName");
				if (classPath.indexOf(temp) > -1) {
					sb.append("<option  value=\"" + temp + "\" selected=\"selected\">" + name + "</option>");
				} else {
					sb.append("<option  value=\"" + temp + "\">" + name + "</option>");
				}
			});
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// testGetValueFromJson();
		String temp = queryCategoryHtml(1000L, "baidu.com");
		System.out.println(temp);
	}

}
