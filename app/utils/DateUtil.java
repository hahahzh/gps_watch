package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * 
 * 时间日期处理工具包,从String到Date的转换以及从String到Date的转换<br>
 * 
 * 等格式化处理
 * 
 * original author(William) all rights reserved
 * 
 * @author William E-Mail:feng_dreaming@163.com
 * 
 * @since 2010-4-15
 * 
 * @see java.util.Date<br>
 * 
 *      java.text.SimpleDateFormat<br>
 * 
 *      java.util.Calendar
 * 
 * **/
public class DateUtil {

	private static final String FORMAT_0 = "yyyy-MM-dd HH:mm:ss";

	private static final String FORMAT_1 = "yyyy-MM-dd";

	private static final String FORMAT_2 = "HH:mm:ss";
	
	private static final String FORMAT_3 = "yyyy/MM/dd HH:mm:ss";
	
	private static final String FORMAT_4 = "yyyy-MM-dd HH:mm";

	/**
	 * 
	 * 如果参数长度不为为0,则取第一个参数进行格式化，<br>
	 * 
	 * 否则取当前日期时间，精确到秒 如:2010-4-15 9:36:38
	 * 
	 * 
	 * 
	 * @param Date
	 * 
	 *            ... 可变参数
	 * 
	 * @return java.lang.String
	 * 
	 * **/

	public static String reverseDate(Date date, int num) {

		SimpleDateFormat simple = null;
		switch(num){
			case 0: simple = new SimpleDateFormat(FORMAT_0);
			break;
			case 1: simple = new SimpleDateFormat(FORMAT_1);
			break;
			case 2: simple = new SimpleDateFormat(FORMAT_2);
			break;
			case 3: simple = new SimpleDateFormat(FORMAT_3);
			break;
			case 4: simple = new SimpleDateFormat(FORMAT_4);
			break;
			default:simple = new SimpleDateFormat(FORMAT_0);
			break;
		}		

		if (date != null) {
			return simple.format(date);
		}
		return simple.format(new Date());

	}

	/**
	 * 
	 * 如果参数长度不为为0,则取第一个参数进行格式化，<br>
	 * 
	 * 否则取当前日期 如:2010-4-15
	 * 
	 * 
	 * 
	 * @param Date
	 * 
	 *            ... 可变参数
	 * 
	 * @return java.lang.String
	 * 
	 * **/

	public static String toDate(Date... date) {

		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);

		if (date.length > 0) {

			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 
	 * 如果参数长度不为为0,则取第一个参数进行格式化，<br>
	 * 
	 * 否则取当前日期时间，精确到秒<br>
	 * 
	 * 如:9:36:38
	 * 
	 * 
	 * 
	 * @param Date
	 * 
	 *            ... 可变参数
	 * 
	 * @return java.lang.String
	 * 
	 * **/

	public static String toTime(Date... date) {

		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_2);

		if (date.length > 0) {

			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 
	 * 根据字符串格式去转换相应格式的日期和时间
	 * 
	 * 
	 * 
	 * @param java
	 *            .lang.String 必要参数
	 * 
	 * @return java.util.Date
	 * 
	 * @exception java.text.ParseException
	 * 
	 *                如果参数格式不正确会抛出此异常
	 * 
	 * **/

	public static Date reverse2Date(String date) {

		SimpleDateFormat simple = null;

		switch (date.trim().length()) {

		case 19:// 日期+时间

			simple = new SimpleDateFormat(FORMAT_0);

			break;
			
		case 18:// 日期+时间

			simple = new SimpleDateFormat(FORMAT_0);

			break;

		case 10:// 仅日期

			simple = new SimpleDateFormat(FORMAT_1);

			break;

		case 8:// 仅时间

			simple = new SimpleDateFormat(FORMAT_2);

			break;

		default:

			break;

		}

		try {

			return simple.parse(date);

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 
	 * 将带有时、分、秒格式的日期转化为00:00:00<br>
	 * 
	 * 方便日期推算,格式化后的是yyyy-MM-dd 00:00:00
	 * 
	 * @param java
	 *            .util.Date... date的长度可以为0,即不用给参数
	 * 
	 * @return java.util.Date
	 * 
	 * **/

	public static Date startOfADay(Date... date) {

		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);

		Date date_ = date.length == 0 ? new Date() : date[0];// 如果date为null则取当前时间

		String d = simple.format(date_);

		try {

			return simple.parse(d);

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 
	 * 推算一个月内向前或向后偏移多少天,当然推算年也可以使用
	 * 
	 * @param date
	 *            要被偏移的日期,<br>
	 * 
	 *            amout 偏移量<br>
	 * 
	 *            b 是否先将date格式化为yyyy-MM-dd 00:00:00 即:
	 * 
	 *            是否严格按整天计算
	 * 
	 * @return java.util.Date
	 * 
	 * **/

	public static Date addDayOfMonth(Date date, Integer amount, Boolean b) {

		date = date == null ? new Date() : date;// 如果date为null则取当前日期

		if (b) {

			date = startOfADay(date);

		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, amount);

		return cal.getTime();

	}

	/**
	 * 
	 * 推算一个小时内向前或向后偏移多少分钟,除了秒、毫秒不可以以外,其他都可以
	 * 
	 * @param date
	 *            要被偏移的日期,<br>
	 * 
	 *            amout 偏移量<br>
	 * 
	 *            b 是否先将date格式化为yyyy-MM-dd HH:mm:00 即:
	 * 
	 *            是否严格按整分钟计算
	 * 
	 * @return java.util.Date
	 * 
	 * **/

	public static Date addMinuteOfHour(Date date, Integer amount, Boolean b) {

		date = date == null ? new Date() : date;// 如果date为null则取当前日期

		if (b) {

			SimpleDateFormat simple = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:00");

			try {

				date = simple.parse(simple.format(date));

			} catch (ParseException e) {

				e.printStackTrace();

			}

		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.MINUTE, amount);

		return cal.getTime();

	}
	/**
	 * 返回两时间间隔(天)
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws ParseException
	 */
	public static long intervalOfDay(Date from, Date to) throws ParseException{
 
        GregorianCalendar cal1 = new GregorianCalendar(); 
        GregorianCalendar cal2 = new GregorianCalendar(); 
        cal1.setTime(from); 
        cal2.setTime(to); 
        long gap = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24); 
        return gap; 
    } 
	/**
	 * 返回两时间间隔(小时)
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws ParseException
	 */
	public static double intervalOfHour(Date from, Date to) throws ParseException{
		double gap = to.getTime() - from.getTime(); 
        return (((gap/1000)/60))/60; 
    } 
	
	public static void main(String[] args) {
		
			Date a=DateUtil.addDayOfMonth(new Date(),30,false);
			System.out.println(a);
	
	}

}
