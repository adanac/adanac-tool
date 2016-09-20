package com.adanac.tool.thirdparty.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonConvertUtil {
	/**
	 * 该字符串可能转为 JSONObject 或 JSONArray
	 * 
	 * @param string
	 * @return
	 */
	public static boolean mayBeJSON(String string) {
		return ((string != null) && ((("null".equals(string)) || ((string.startsWith("[")) && (string.endsWith("]")))
				|| ((string.startsWith("{")) && (string.endsWith("}"))))));
	}

	/**
	 * 该字符串可能转为JSONObject
	 * 
	 * @param string
	 * @return
	 */
	public static boolean mayBeJSONObject(String string) {
		return ((string != null)
				&& ((("null".equals(string)) || ((string.startsWith("{")) && (string.endsWith("}"))))));
	}

	/**
	 * 该字符串可能转为 JSONArray
	 * 
	 * @param string
	 * @return
	 */
	public static boolean mayBeJSONArray(String string) {
		return ((string != null)
				&& ((("null".equals(string)) || ((string.startsWith("[")) && (string.endsWith("]"))))));
	}

	/**
	 * 函数注释：parseJSON2Map()<br>
	 * 时间：2014-10-28-上午10:50:21<br>
	 * 用途：该方法用于json数据转换为<Map<String, Object>
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * 函数注释：parseJSON2MapString()<br>
	 * 用途：该方法用于json数据转换为<Map<String, String><br>
	 * 备注：***<br>
	 */
	public static Map<String, String> parseJSON2MapString(String jsonStr) {
		Map<String, String> map = new HashMap<String, String>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			if (null != v) {
				map.put(k.toString(), v.toString());
			}
		}
		return map;
	}

	/**
	 * 函数注释：parseJSON2List()<br>
	 * 用途：该方法用于json数据转换为List<Map<String, Object>><br>
	 */
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * 函数注释：parseJSON2ListString()<br>
	 * 用途：该方法用于json数据转换为List<Map<String, String>><br>
	 */
	public static List<Map<String, String>> parseJSON2ListString(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2MapString(json2.toString()));
		}
		return list;
	}

	public static void main(String[] args) {
	}
}
