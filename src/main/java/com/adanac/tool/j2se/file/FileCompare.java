package com.adanac.tool.j2se.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FileCompare {
	public static void main(String[] args) throws IOException {
		FileCompare c = new FileCompare();
		String path1 = "res/1.txt";
		String path2 = "res/2.txt";
		c.compareFile(path1, path2);
	}

	public void compareFile(String path1, String path2) {
		File file = new File(path1);
		File file2 = new File(path2);
		Set<String> fileTextSet = new HashSet<String>();
		Set<String> file2TextSet = new HashSet<String>();
		try {
			getText(file, fileTextSet);
			getText(file2, file2TextSet);
			compareSet(fileTextSet, file2TextSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void compareSet(Set<String> textSet, Set<String> StextSet2) {
		int numOfCommon = 0, numOfBase = 0, numOfAdap = 0;
		for (Iterator<String> iterator = textSet.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			if (StextSet2.contains(name)) {
				System.out.println("common:" + name);
				numOfCommon++;
			} else {
				System.out.println("1:" + name);
			}

			numOfBase++;
		}
		for (Iterator<String> iterator = StextSet2.iterator(); iterator.hasNext();) {

			String name = (String) iterator.next();
			if (!textSet.contains(name)) {
				System.out.println("2" + name);
			}

			numOfAdap++;
		}
		System.out.println("Common: " + numOfCommon);
		System.out.println("1: " + numOfBase);
		System.out.println("2: " + numOfAdap);
	}

	private void getText(File file, Set<String> textSet) throws IOException {
		BufferedReader br = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String lineStr = null;
			while ((lineStr = br.readLine()) != null) {
				// String text = lineStr.substring(lineStr.indexOf(":"));//
				// 按照需求切分
				textSet.add(lineStr);
			}
			if (br != null) {
				br.close();
			}
			if (is != null) {
				is.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}
}