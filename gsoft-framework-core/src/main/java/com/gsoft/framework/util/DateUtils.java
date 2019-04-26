package com.gsoft.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public class DateUtils {
	/**
	 * 返回格式：yyyy-MM-dd
	 * @return
	 */
	public static String getToday(){
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}
	/**
	 * 
	 * @param format 根据指定的格式时间类型返回当前时间
	 * @return
	 */
	public static String getToday(String format){
		return getDateStr(Calendar.getInstance().getTime(),format);
	}
	
	/**
	 * 日期转字符
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateStr(Date date,String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * @param millis
	 * @return
	 */
	public static Date parseMills(long millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	/**
	 * 考试年份编码
	 * @return
	 */
	public static String getYearCode(){
		Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	String yearStr = new Integer(year).toString();
    	//当前年份后两位
    	return yearStr.substring(2,4);
	}
	
	/**
	 * String日期转Date
	 * 
	 * @param dateStr
	 * @param format
	 * @return 转换失败返回null
	 */
	public static Date parseDate(String dateStr, String format) {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		try {
			return fmt.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static long diffTime(Date date,Date date2){
		long diffTime = date.getTime() - date2.getTime();
		return diffTime/1000;
	}
	/**
	 * 时间加减处理
	 * @param date
	 * @param field 
	 * @param amount
	 * @return
	 */
	public static Date add(Date date,int field,int amount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}
	/**
	 * 时间加减处理
	 * @param dateStr
	 * @param format
	 * @param field
	 * @param amount
	 * @return
	 */
	public static String add(String dateStr,String format,int field,int amount){
		Date date = parseDate(dateStr, format);
		date = add(date, field, amount);
		return getDateStr(date, format);
	}
	

	/**
	 * 格式化时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat tofmt = new SimpleDateFormat(format);
		return tofmt.format(date);
	}

	/**
	 * 日期格式化
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getDateStr(int year, int month, int day, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return formatDate(calendar.getTime(), format);
	}

	/**
	 * 日期格式化
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getDateStr(int year, int month, int day) {
		return getDateStr(year, month, day, "yyyyMMdd");
	}
	
	/**
	 * 时间格式化
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getTimeStr(int hour, int minute, int second, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return formatDate(calendar.getTime(), format);
	}

	/**
	 * 时间格式化
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getTimeStr(int hour, int minute, int second) {
		return getTimeStr(hour, minute, second, "HHmmss");
	}

	/**
	 * 格式化
	 * @param date
	 * @param format
	 * @param toFormat
	 * @return
	 */
	public static String formatDateStr(String date, String format, String toFormat){
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		SimpleDateFormat tofmt = new SimpleDateFormat(toFormat);
		Date d;
		try {
			d = fmt.parse(date);
			return tofmt.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
//		String dateStr = DateUtils.getDateStr(parseMills(1350994695000l), "yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateStr);
		
	}
}
