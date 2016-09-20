package com.adanac.tool.thirdparty.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.adanac.tool.constant.ToolConstants;
import com.adanac.tool.j2se.datetime.DateTimeUtils;

public class FileUtil {

	/**
	 * 生成excle文件
	 * 
	 * @author 陈荣祥
	 */
	public static Map<String, Object> createExcel(XSSFWorkbook workbook, String filePath, String fileName) {
		try {
			// 项目根目录
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String basePath = request.getSession().getServletContext().getRealPath("/");
			if ("".equals(filePath)) {
				filePath = ToolConstants.ExcleFile.FILEPATH;
			}
			if ("".equals(fileName)) {
				fileName = DateTimeUtils.getDateFormat("yyyyMMddHHmmss", "") + ".xlxs";
			}
			// 将文件存于
			File file = new File(basePath + filePath);
			// 判断路径是否存在
			if (!file.exists()) {
				file.mkdir();
			}
			filePath = basePath + filePath + fileName;
			FileOutputStream fout = new FileOutputStream(filePath);
			workbook.write(fout);

			// 关闭
			fout.flush();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("filePath", filePath);
		resultmap.put("fileName", fileName);
		return resultmap;
	}

	public static void main(String[] args) throws Exception {
		String filePath = "D:\\test";
		XSSFWorkbook workbook = new XSSFWorkbook(filePath);
		String fileName = "mytest";
		Map<String, Object> map = createExcel(workbook, filePath, fileName);
		System.out.println(map.toString());
	}
}
