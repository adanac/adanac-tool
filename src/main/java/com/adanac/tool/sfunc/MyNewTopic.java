package com.adanac.tool.sfunc;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 自动在 discuz 7.2 的论坛上发帖子 Created by allen
 */
public class MyNewTopic {

	private static URL url;
	private static HttpURLConnection con;
	private static String temp;
	private static InputStream is;
	private static byte[] b;
	private static int pos;
	private static String cookie_sid;
	private static String cookie_auth;
	private static String my_cookie;
	private static String login_hash;
	private static String form_hash;
	private static String post_formhash;
	private static String user = "byedis";// 用户名
	private static String pass = "hi,byedis123";// 密码
	private static String new_fid = "142";// 版块 ID
	private static String subject = "新主题";// 标题
	private static String msg = "这里是新主题的内容";// 帖子内容

	public static void main(String[] args) {
		try {
			// 获取 cookie_sid 和 login_formhash --------------------
			url = new URL(
					"http://club.njrx.cc/member.php?mod=logging&action=login&loginsubmit=yes&handlekey=login&loginhash=Ll7wV&inajax=1");
			con = (HttpURLConnection) url.openConnection();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 获取服务器发给客户端的 Cookie
				temp = con.getHeaderField("Set-Cookie");
				System.out.println("Set-Cookie:" + temp);
				// 取 Cookie 前面的部分就可以了，后面是过期时间、路径等，不用管它
				cookie_sid = temp.substring(14, 20);
				System.out.println("cookie_sid:" + cookie_sid);

				is = con.getInputStream();
				b = new byte[is.available()];
				is.read(b);
				// 服务器会返回一个页面，此页面中包含 loginhash
				temp = new String(b);
				// 找出这个 formhash 的位置
				pos = temp.indexOf("loginhash=");
				// System.out.println(temp);
				// 找出这个 loginhash 的内容，这是登录用的 loginhash
				login_hash = temp.substring(pos + 10, pos + 10 + 5);
				System.out.println("login_formhash:" + login_hash);
				System.out
						.println("------------------------------------------------------------get login_formhash end");
				is.close();
			}

			// 获取formhash -----------------------------------------------
			url = new URL("http://club.njrx.cc/member.php?");
			con = (HttpURLConnection) url.openConnection();

			// 设定以 POST 发送
			con.setRequestMethod("POST");
			// 加入 Cookie 内容
			con.setRequestProperty("Cookie", cookie_sid);
			// 添加 POST 的内容
			con.setDoOutput(true);
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
			osw.write("mod=logging&action=login&loginsubmit=yes&loginhash=" + login_hash
					+ "&inajax=1&loginfield=username&username=" + user + "&password=" + pass + "&questionid=0");
			osw.flush();
			osw.close();

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// Map<String, List<String>> map = con.getHeaderFields();
				// List<String> list = map.get("Set-Cookie");
				// for (int i = 0; i < list.size(); i++) {
				// temp = list.get(i);
				// if (temp.contains("CsN_auth")) {
				// System.out.println(temp);
				// // 取 Cookie 前面的部分就可以了，后面是过期时间、路径等，不用管它
				// cookie_auth = temp.split(";")[0];
				// System.out.println("cookie_auth:" + cookie_auth);
				// }
				// }

				is = con.getInputStream();
				byte[] b = new byte[is.available()];
				is.read(b);
				temp = new String(b);
				// 找出formhash的位置
				pos = temp.indexOf("formhash=");
				form_hash = temp.substring(pos + 9, pos + 9 + 8);
				System.out.println("form_hash:" + form_hash);
				System.out.println("------------------------------------------------------------get formhash end");
				is.close();
			}

			// 正式登录
			url = new URL(
					"http://club.njrx.cc/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=" + login_hash
							+ "&inajax=1&loginfield=username&username=" + user + "&password=" + pass + "&questionid=0");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			my_cookie = cookie_sid;/* + ";" + cookie_auth; */
			System.out.println(my_cookie);
			// 加入 Cookie 内容
			con.setRequestProperty("Cookie", my_cookie);

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = con.getInputStream();
				byte[] bis = new byte[is.available()];
				is.read(bis);
				temp = new String(b);
				pos = temp.indexOf("id=\"formhash\" value=");
				System.out.println(temp);
				// 获得发帖用的 formhash
				post_formhash = temp.substring(pos + 21, pos + 21 + 8);
				System.out.println("post_formhash:" + post_formhash);
				System.out.println("------------------------------------------------------------login end");
				is.close();
			}

			// 发新帖子
			url = new URL("http://club.njrx.cc/forum.php?mod=post&action=newthread&fid=140");
			con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Cookie", my_cookie);
			con.setDoOutput(true);
			osw = new OutputStreamWriter(con.getOutputStream());
			temp = "http://bbs.njrx.cc/forum.php?mod=post&action=newthread&fid=" + new_fid + "&formhash=" + form_hash
					+ "&extra=&topicsubmit=yes&formhash=" + login_hash + "&subject=" + subject + "&message=" + msg;
			osw.write(temp);
			osw.flush();
			osw.close();

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = con.getInputStream();
				byte[] b = new byte[is.available()];
				is.read(b);
				System.out.println(new String(b));
				System.out.println("------------------------------------------------------------post title end");
				is.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 

	}
}
