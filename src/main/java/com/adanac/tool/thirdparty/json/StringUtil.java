package com.adanac.tool.thirdparty.json;

public class StringUtil {

	public static void main(String[] args) {
		String paramListStr = "[{spuId=f4824d72e5744138abb0ba7daa08e584, skuId=221285912e294897a300f4874f109787, categoryId=305253,spuLocalCode=1605080030, fskuReferPrice=4, fspuMinBuyCount=1, discount=1, restriction=1}]";
		System.out.println(proListStr(paramListStr));
	}

	public static String proListStr(String paramListStr) {
		paramListStr = paramListStr.substring(1, paramListStr.length() - 1);
		return paramListStr;
	}

	public static String proStr(String jsonStr) {
		StringBuilder sb = new StringBuilder(jsonStr);
		String strRes = "";

		String str = sb.substring(2, sb.length() - 2).toString();
		// str = str.replace("}", "");
		// str = str.replace("{", "");
		String[] strArr = str.split(",");
		for (int i = 0; i < strArr.length; i++) {
			String[] sarr = strArr[i].split("=");
			sarr[1] = "'" + sarr[1] + "'";
			String[] strArrRes = new String[2];
			strArrRes[0] = sarr[0];
			strArrRes[1] = sarr[1];
			strRes += String.join("=", strArrRes) + ",";
		}
		String tem = strRes.substring(0, strRes.length() - 1);
		tem = tem.replace("}'", "'}");
		return "[{" + tem + "}]";
	}

}
