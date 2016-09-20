package com.adanac.tool.thirdparty.json;

import java.util.ArrayList;
import java.util.List;

import com.adanac.tool.entity.PersonDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ParseJson {
	private String jsonStr;

	public ParseJson() {

	}

	public ParseJson(String str) {
		this.jsonStr = str;
	}

	/**
	 * 解析json字符串
	 */
	public void parse() {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String name = jsonObject.getString("name");
		int num = jsonObject.getInt("num");
		String sex = jsonObject.getString("sex");
		int age = jsonObject.getInt("age");
		System.out.println(name + " " + num + " " + sex + " " + age);
	}

	// 将java对象转换为json字符串
	public String Object2Json(Object obj) {
		JSONObject json = JSONObject.fromObject(obj);// 将java对象转换为json对象
		String str = json.toString();// 将json对象转换为字符串
		return str;
	}

	// 將java對象數組轉換為字符串
	public static String Objects2Json(List<?> objList) {
		JSONArray json = JSONArray.fromObject(objList);// 将java对象转换为json对象
		String str = json.toString();// 将json对象转换为字符串
		return str;
	}

	// 将json字符串转换为java对象
	public PersonDto JSON2Object() {
		// 接收{}对象，此处接收数组对象会有异常
		if (jsonStr.indexOf("[") != -1) {
			jsonStr = jsonStr.replace("[", "");
		}
		if (jsonStr.indexOf("]") != -1) {
			jsonStr = jsonStr.replace("]", "");
		}
		JSONObject obj = new JSONObject().fromObject(jsonStr);// 将json字符串转换为json对象
		PersonDto jb = (PersonDto) JSONObject.toBean(obj, PersonDto.class);// 将建json对象转换为Person对象
		return jb;// 返回一个Person对象
	}

	// 将json字符串转换为java數組对象

	// 从json中取值
	public static void testGetFromJson() {
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
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		System.out.println(jsonObj.getJSONObject("states").getJSONObject("rect1").get("type"));
	}

	// 将字符串转换为json对象，然后根据建得到相应的值
	public static void testParse() {
		ParseJson pj = new ParseJson("{\"name\":\"adanac\",\"num\":123456,\"sex\":\"male\",\"age\":24}");
		pj.parse();
	}

	// 将一个java对象转换为Json字符串
	public static void testObject2JSON() {
		PersonDto p1 = new PersonDto("adanac1", 123, "male", 23);
		ParseJson parseJson = new ParseJson();
		String str1 = parseJson.Object2Json(p1);
		System.out.println(str1);

	}

	// 将一个java对象數組转换为Json字符串
	public static void testObjects2Json() {
		List<PersonDto> pList = new ArrayList<PersonDto>();
		for (int i = 0; i < 3; i++) {
			PersonDto p = new PersonDto("adanac" + i, i, "male", 27);
			pList.add(p);
		}
		ParseJson parseJson = new ParseJson();
		String str1 = parseJson.Objects2Json(pList);
		System.out.println(str1);
	}

	// 将json字符串转换为java对象
	public static void testJSON2Object() {
		ParseJson pj = new ParseJson("{\"name\":\"adanac2\",\"num\":123456,\"sex\":\"male\",\"age\":25}");
		PersonDto p = pj.JSON2Object();
		System.out.println(p.toString());
	}

	public static void main(String[] args) {
		testGetFromJson();
	}

}
