package com.adanac.tool.j2se.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

public class MergeFileUtil {
	public static final int BUFSIZE = 1024 * 8;

	/**
	 * 合并文件，BUG上一文件最后一行与下一文件首行合并在了同一行
	 * @param outFile
	 * @param files
	 */
	@SuppressWarnings("resource")
	public static void mergeFiles(String outFile, String[] files) {
		FileChannel outChannel = null;
		System.out.println("Merge " + Arrays.toString(files) + " into " + outFile);
		try {
			outChannel = new FileOutputStream(outFile).getChannel();
			for (String f : files) {
				FileChannel fc = new FileInputStream(f).getChannel();
				ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
				while (fc.read(bb) != -1) {
					bb.flip();
					outChannel.write(bb);
					bb.clear();
				}
				fc.close();

			}
			System.out.println("Merged!! ");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
			}
		}
	}

	public static void main(String[] args) {
		String dirPath = "D:\\soft_allen_1\\字典大全\\";
		String suffix = ".txt";
		String resFileName = "D:/soft_allen/output.txt";
		ArrayList<File> allFiles;
		// allFiles = CnFileUtil.getAllFiles(dirPath);
		// allFiles = CnFileUtil.getDirFiles(dirPath);
		allFiles = CnFileUtil.getDirFiles(dirPath, suffix);
		String[] fileNames = new String[allFiles.size()];
		for (int i = 0; i < fileNames.length; i++) {
			File file = allFiles.get(i);
			fileNames[i] = file.getAbsolutePath();
		}
		mergeFiles(resFileName, fileNames);
	}
}
