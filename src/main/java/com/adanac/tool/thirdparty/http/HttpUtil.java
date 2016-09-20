package com.adanac.tool.thirdparty.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;

import net.sf.json.JSONObject;

/**
 * Http 连接处理工具
 * @author jwang
 * @date 2016年3月18日
 */
public final class HttpUtil {
	private static final MyLogger logger = MyLoggerFactory.getLogger(HttpUtil.class);

	public static final int BODYTYPE_PARAM = 0;
	public static final int BODYTYPE_JSON = 1;

	public static final String PARAM_CONNECT_TIMEOUT = "_connectTimeout"; // 连接超时参数
	public static final String PARAM_READ_TIMEOUT = "_readTimeout"; // 响应超时参数
	public static final int CONNECT_TIMEOUT = 3000;// （单位：毫秒)
	public static final int READ_TIMEOUT = 6000;// （单位：毫秒)
	public static final int TIMEOUT_WARN = 1000; // 接口请求超时报警

	public static final String DEFAULT_ENCODING = "UTF-8";

	private static final int BUFFER_SIZE = 4096;

	private HttpUtil() {
	}

	/**
	 * HTTP GET 请求
	 * 
	 * @param reqUrl
	 *            请求URL
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doGet(String restUrl) throws HttpStatusCodeException, Exception {
		return doGet(restUrl, null, null);
	}

	/**
	 * HTTP GET 请求
	 * 
	 * @param reqUrl
	 *            请求URL
	 * @param connectTimeout
	 *               连接超时时间（单位：毫秒)
	 * @param readTimeout
	 *              响应超时时间（单位：毫秒)
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doGet(String restUrl, Integer connectTimeout, Integer readTimeout)
			throws HttpStatusCodeException, Exception {
		if (logger.isInfoEnabled()) {
			logger.info("GET: URL={}.", restUrl);
		}
		HttpURLConnection urlConn = null;
		try {
			URL url = new URL(restUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setRequestProperty("User-Agent", "O2O/Client");
			if (connectTimeout == null) {
				urlConn.setConnectTimeout(CONNECT_TIMEOUT);
			} else {
				urlConn.setConnectTimeout(connectTimeout);
			}
			if (readTimeout == null) {
				urlConn.setReadTimeout(READ_TIMEOUT);
			} else {
				urlConn.setReadTimeout(readTimeout);
			}

			long time = System.currentTimeMillis();
			String ret = getBody(urlConn);
			long overtime = System.currentTimeMillis() - time;
			if (logger.isWarnEnabled() && overtime > TIMEOUT_WARN) {
				logger.warn("over time:" + overtime + " url:" + restUrl);
			}
			return ret;
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
				urlConn = null;
			}
		}
	}

	/**
	 * Http POST 请求
	 * @param restUrl       请求URL
	 * @param parameters    请求参数
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doPost(String restUrl, Map<String, String> parameters)
			throws HttpStatusCodeException, Exception {
		return doMethod(restUrl, parameters, "POST");
	};

	/**
	 * Http POST 请求
	 * @param restUrl       请求URL
	 * @param parameters    请求参数
	 * @param bodyType      0-普通参数提交,1-json格式提交
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doPost(String restUrl, Map<String, String> parameters, int bodyType)
			throws HttpStatusCodeException, Exception {
		return doMethod(restUrl, parameters, "POST", bodyType);
	}

	/**
	 * Http PUT 请求
	 * @param restUrl       请求URL
	 * @param parameters    请求参数
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doPut(String restUrl, Map<String, String> parameters)
			throws HttpStatusCodeException, Exception {
		return doMethod(restUrl, parameters, "PUT");
	}

	/**
	 * Http PUT 请求
	 * @param restUrl       请求URL
	 * @param parameters    请求参数
	 * @param bodyType      0-普通参数提交,1-json格式提交
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doPut(String restUrl, Map<String, String> parameters, int bodyType)
			throws HttpStatusCodeException, Exception {
		return doMethod(restUrl, parameters, "PUT", bodyType);
	}

	/**
	 * Http DELETE 请求
	 * @param restUrl       请求URL
	 * @param parameters    请求参数
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doDelete(String restUrl, Map<String, String> parameters)
			throws HttpStatusCodeException, Exception {
		return doMethod(restUrl, parameters, "DELETE");
	}

	/**
	 * 上传文件
	 * @param uploadUrl     上传URL
	 * @param parameters    参数
	 * @param fileParamName 文件参数名
	 * @param filename      文件名称
	 * @param contentType   类型
	 * @param data          文件内容
	 * @return
	 * @throws HttpStatusCodeException, Exception
	 * 返回正文信息
	 */
	public static String doUploadFile(String uploadUrl, Map<String, String> parameters, String fileParamName,
			String filename, String contentType, byte[] data) throws HttpStatusCodeException, Exception {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendFormdata(uploadUrl, parameters, fileParamName, filename, contentType, data);
			return getBody(urlConn);
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
	}

	/*
	 * 请求方法
	 */
	private static String doMethod(String restUrl, Map<String, String> parameters, String method)
			throws HttpStatusCodeException, Exception {
		return doMethod(restUrl, parameters, method, BODYTYPE_PARAM);
	}

	/*
	 * 请求方法
	 */
	private static String doMethod(String restUrl, Map<String, String> parameters, String method, int bodyType)
			throws HttpStatusCodeException, Exception {
		HttpURLConnection urlConn = null;
		try {
			long time = System.currentTimeMillis();
			urlConn = send(restUrl, parameters, method, bodyType);
			String ret = getBody(urlConn);
			long overtime = System.currentTimeMillis() - time;
			if (logger.isWarnEnabled() && overtime > TIMEOUT_WARN) {
				logger.warn("over time:" + overtime + " url:" + restUrl);
			}
			return ret;
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
				urlConn = null;
			}
		}
	}

	/**
	 * POST数据表单
	 * @param restUrl
	 * @param parameters
	 * @param connectTimeout
	 *               连接超时时间
	 * @return
	 * @throws Exception 
	 */
	private static HttpURLConnection send(String restUrl, Map<String, String> parameters, String method, int bodyType)
			throws Exception {
		HttpURLConnection urlConn = null;
		try {
			String connectTimeout = parameters.remove(PARAM_CONNECT_TIMEOUT);
			String readTimeout = parameters.remove(PARAM_READ_TIMEOUT);
			String params = "";
			URL url = new URL(restUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("User-Agent", "O2O/Client");
			urlConn.setConnectTimeout(valueOf(connectTimeout, CONNECT_TIMEOUT));
			urlConn.setReadTimeout(valueOf(readTimeout, READ_TIMEOUT));
			if (bodyType == BODYTYPE_JSON) {
				params = JSONObject.fromObject(parameters).toString();
				urlConn.setRequestProperty("Content-Type", "application/json");
			} else {
				params = generatorParamString(parameters);
			}
			if (logger.isInfoEnabled()) {
				logger.info("{}: URL={}, PARAM={}.", new Object[] { method, restUrl, params });
			}
			urlConn.setDoOutput(true);
			byte[] b = params.getBytes();
			urlConn.getOutputStream().write(b, 0, b.length);
			urlConn.getOutputStream().flush();

		} finally {
			if (urlConn != null) {
				urlConn.getOutputStream().close();
			}
		}
		return urlConn;
	}

	/**
	 * 上传文件表单
	 * @param uploadUrl
	 * @param parameters
	 * @param fileParamName
	 * @param filename
	 * @param contentType
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	private static HttpURLConnection sendFormdata(String uploadUrl, Map<String, String> parameters,
			String fileParamName, String filename, String contentType, byte[] data) throws Exception {
		HttpURLConnection urlConn = null;
		OutputStream os = null;
		try {
			URL url = new URL(uploadUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			String timeout = parameters.remove(PARAM_CONNECT_TIMEOUT);
			urlConn.setConnectTimeout(valueOf(timeout, CONNECT_TIMEOUT));
			urlConn.setDoOutput(true);

			urlConn.setRequestProperty("connection", "keep-alive");

			String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
			urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			boundary = "--" + boundary;
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
					String name = iter.next();
					String value = parameters.get(name);
					params.append(boundary + "\r\n");
					params.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
					params.append(URLEncoder.encode(value, DEFAULT_ENCODING));
					// params.append(value);
					params.append("\r\n");
				}
			}

			StringBuilder sb = new StringBuilder();
			// sb.append("\r\n");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\"" + filename
					+ "\"\r\n");
			sb.append("Content-Type: " + contentType + "\r\n\r\n");
			byte[] fileDiv = sb.toString().getBytes();
			byte[] endData = ("\r\n" + boundary + "--\r\n").getBytes();
			byte[] ps = params.toString().getBytes();

			os = urlConn.getOutputStream();
			os.write(ps);
			os.write(fileDiv);
			os.write(data);
			os.write(endData);

			os.flush();

		} finally {
			if (os != null) {
				os.close();
			}
		}
		return urlConn;
	}

	/**
	 * Return the body of the message as an input stream.
	 * @return the input stream body
	 * @throws IOException in case of I/O Errors
	 */
	private static String getBody(HttpURLConnection urlConn) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		if (urlConn.getResponseCode() != 200) {
			copy(urlConn.getErrorStream(), out);
			throw new HttpClientErrorException(HttpStatus.valueOf(urlConn.getResponseCode()),
					urlConn.getResponseMessage(), out.toByteArray(), Charset.forName(DEFAULT_ENCODING));
		} else {
			copy(urlConn.getInputStream(), out);
			return new String(out.toByteArray(), DEFAULT_ENCODING);
		}
	}

	/**
	 * 将parameters中数据转换成用"&"链接的http请求参数形式
	 * 
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String generatorParamString(Map<String, String> parameters) throws UnsupportedEncodingException {
		StringBuffer params = new StringBuffer();
		if (parameters != null) {
			for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String value = parameters.get(name);
				if (value != null) {
					params.append(name).append("=");
					params.append(URLEncoder.encode(value, DEFAULT_ENCODING));
				}
				if (iter.hasNext()) {
					params.append("&");
				}
			}
		}
		return params.toString();
	}

	/**
	 * Copy the contents of the given InputStream to the given OutputStream.
	 * Closes both streams when done.
	 * @param in the stream to copy from
	 * @param out the stream to copy to
	 * @return the number of bytes copied
	 * @throws IOException in case of I/O errors
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		try {
			int byteCount = 0;
			if (in == null) {
				return byteCount;
			}
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
			}
			try {
				out.close();
			} catch (IOException ex) {
			}
		}
	}

	private static Integer valueOf(String val, Integer defaultVal) {
		if (null != val) {
			try {
				defaultVal = Integer.valueOf(val);
			} catch (NumberFormatException e) {
			}
		}
		return defaultVal;
	}
}
