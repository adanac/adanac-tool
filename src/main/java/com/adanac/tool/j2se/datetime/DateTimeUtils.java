package com.adanac.tool.j2se.datetime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.adanac.tool.constant.ToolConstants;

/**
 * 日期工具类
 * 
 * @author <a href="http://www.xdemo.org/">http://www.xdemo.org/</a>
 *         252878950@qq.com
 */
public class DateTimeUtils {

	public static String utc2Gmt8(String utcStr) {

		SimpleDateFormat utcDateFormat = getUTCDateFormat();
		SimpleDateFormat localDateFormat = getLocalDateFormat();
		try {
			Date date = utcDateFormat.parse(utcStr);

			return localDateFormat.format(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	public static String gmt82utc(String gmtStr) {

		SimpleDateFormat localDateFormat = getLocalDateFormat();
		SimpleDateFormat utcDateFormat = getUTCDateFormat();

		try {
			Date date = localDateFormat.parse(gmtStr);

			return utcDateFormat.format(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * gmt8
	 *
	 * @return
	 */
	private static SimpleDateFormat getLocalDateFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return dateFormat;
	}

	/**
	 * utc
	 *
	 * @return
	 */
	private static SimpleDateFormat getUTCDateFormat() {
		SimpleDateFormat utcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		utcDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return utcDateFormat;
	}

	/**
	 * 获取制定日期的格式化字符串
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return String
	 */
	public static String getFormatedDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 判断哪个日期在前 日过日期一在日期二之前，返回true,否则返回false
	 * 
	 * @param date1
	 *            日期一
	 * @param date2
	 *            日期二
	 * @return boolean
	 */
	public static boolean isBefore(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		if (c1.before(c2))
			return true;

		return false;
	}

	/**
	 * 将字符串转换成日期
	 * 
	 * @param date
	 *            String 日期字符串
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDateFromString(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}

	/**
	 * 解析时间字符串<br>
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date parse(String source, String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return simpleDateFormat.parse(source);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取指定日期当月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取指定日期当月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date firstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 是否是闰年
	 * 
	 * @param year
	 *            年份
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		GregorianCalendar calendar = new GregorianCalendar();
		return calendar.isLeapYear(year);
	}

	/**
	 * 获取指定日期之前或者之后多少天的日期
	 * 
	 * @param day
	 *            指定的时间
	 * @param offset
	 *            日期偏移量，正数表示延后，负数表示天前
	 * @return Date
	 */
	public static Date getDateByOffset(Date day, int offset) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.add(Calendar.DAY_OF_MONTH, offset);
		return c.getTime();
	}

	/**
	 * 获取一天开始时间 如 2014-12-12 00:00:00
	 * 
	 * @return
	 */
	public static Date getDayStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取一天结束时间 如 2014-12-12 23:59:59
	 * 
	 * @return
	 */
	public static Date getDayEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 时间分段 比如：2014-12-12 10:00:00 ～ 2014-12-12 14:00:00 分成两段就是 2014-12-12
	 * 10：00：00 ～ 2014-12-12 12：00：00 和2014-12-12 12：00：00 ～ 2014-12-12 14：00：00
	 * 
	 * @param start
	 *            起始日期
	 * @param end
	 *            结束日期
	 * @param pieces
	 *            分成几段
	 */
	public static Date[] getDatePieces(Date start, Date end, int pieces) {

		Long sl = start.getTime();
		Long el = end.getTime();

		Long diff = el - sl;

		Long segment = diff / pieces;

		Date[] dateArray = new Date[pieces + 1];

		for (int i = 1; i <= pieces + 1; i++) {
			dateArray[i - 1] = new Date(sl + (i - 1) * segment);
		}

		// 校正最后结束日期的误差，可能会出现偏差，比如14:00:00 ,会变成13:59:59之类的
		dateArray[pieces] = end;

		return dateArray;
	}

	/**
	 * 获取某个日期的当月第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取某个日期的当月最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return cal.getTime();
	}

	/**
	 * 获取某个日期的当月第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取某个日期的当月最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return cal.getTime();
	}

	/**
	 * 获取当前日期
	 * 
	 * @return YYYYMMDD
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currDate = sdf.format(new Date().getTime());
		return currDate;
	}

	/**
	 * 获取当前日期
	 * 
	 * @param dateFormat
	 *            日期格式
	 * @return
	 */
	public static String getCurrentDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat("" + dateFormat + "");
		return sdf.format(new Date().getTime());
	}

	/**
	 * 获取指定格式类型的时间
	 * 
	 * @param dateFormat（时间格式：yyyy-MM-dd
	 *            HH:mm:ss(默认)）
	 * @param datetime
	 *            毫秒数
	 * @return
	 */
	public static String getDateFormat(String dateFormat, String datetime) {
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		long timeMillis = 0l;
		if (datetime == null || "".equals(datetime)) {
			timeMillis = new Date().getTime();
		} else {
			timeMillis = Long.parseLong(datetime);
		}

		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(timeMillis);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTimestamp() {
		return new Timestamp(new Date().getTime()).toString();
	}

	/**
	 * 获取当前时间+1年
	 * 
	 * @return
	 */
	public static Timestamp getOneYearLaterTs() {
		Calendar currCalendar = Calendar.getInstance();
		currCalendar.set(Calendar.YEAR, currCalendar.get(Calendar.YEAR) + 1);
		return new Timestamp(currCalendar.getTimeInMillis());

	}

	/**
	 * 判断是否已过期
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isExpire(String time) {
		// 需要比对失效时间，失效的话则返回空
		Date endDate = DateTimeUtils.parse(time, ToolConstants.PATTREN_2);
		Date now = new Date();
		// 该券已过期了
		if (endDate.before(now)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws ParseException {

		/*
		 * Date d=new Date(); System.out.println(getDateByOffset(d,-1));
		 */
		System.out.println(getFormatedDate(getFirstDayOfMonth(new Date()), "yyyy-MM-dd"));
		System.out.println(getFormatedDate(getLastDayOfMonth(new Date()), "yyyy-MM-dd"));
		System.out.println(getFormatedDate(getFirstDayOfMonth(2013, 2), "yyyy-MM-dd"));
		System.out.println(getFormatedDate(getLastDayOfMonth(2000, 2), "yyyy-MM-dd"));
		System.out.println(getFormatedDate(getLastDayOfMonth(2001, 2), "yyyy-MM-dd"));
	}
}
