package com.adanac.tool.j2se.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

public class DownFileUtil {

	public static void downMedia(final String savePath, final String filename, final String downPath) {
		File fd = new File(savePath);
		if (!fd.exists()) {
			fd.mkdirs();
		}

		new Thread() {
			public void run() {
				try {
					down_file(downPath, savePath, filename);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void down_file(String downPath, String savePath, String filename) throws IOException {
		URL myURL = new URL(downPath);
		URLConnection conn = myURL.openConnection();
		conn.connect();
		InputStream is = conn.getInputStream();
		int fileSize = conn.getContentLength();
		if (fileSize <= 0)
			throw new RuntimeException("can not know the file's size");
		if (is == null)
			throw new RuntimeException("stream is null");
		FileOutputStream fos = new FileOutputStream(savePath + "/" + filename);
		byte buf[] = new byte[1024];
		int downLoadFileSize = 0;
		do {
			// ѭ����ȡ
			int numread = is.read(buf);
			if (numread == -1) {
				break;
			}
			fos.write(buf, 0, numread);
			downLoadFileSize += numread;
		} while (true);
		try {
			is.close();
			fos.close();
		} catch (Exception ex) {
		}
	}
}
