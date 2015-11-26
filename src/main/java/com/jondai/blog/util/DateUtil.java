package com.jondai.blog.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static String datePattern = "yyyy-MM-dd";
    private static String timePattern = datePattern + " HH:MM a";
    public final static int WeekSpan = 7;
    public static int month[] = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 判断是否闰年
     *
     * @param year
     * @return
     */
    private static boolean LeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对个位数的月份之前补零
     *
     * @param month
     * @return
     */
    private static String impleMonth(int month) {
        String monthStr = new Integer(month).toString();
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        return monthStr;
    }

    /**
     * 对个位数的日子之前补零
     *
     * @param day
     * @return
     */
    private static String impleDay(int day) {
        String dayStr = new Integer(day).toString();
        if (dayStr.length() == 1) {
            dayStr = "0" + dayStr;
        }
        return dayStr;
    }

    /**
     * 得到当前周的第一天（周一）的日期
     *
     * @return
     */
    public static String getWeekFirstDate() {
        Date da = new Date();
        int dayOfWeek = da.getDay(); // 当前日期是星期几
        if (dayOfWeek != 0) {
            long fromWeekFirstInMillis = (dayOfWeek - 1) * 24 * 60 * 60 * 1000; // 与该周第一天相隔的毫秒数
            da.setTime(da.getTime() - fromWeekFirstInMillis);

        } else {
            //当前日期为一周的最后一天（周日）
            long fromWeekFirstInMillis = 6 * 24 * 60 * 60 * 1000; // 与该周第一天相隔的毫秒数
            da.setTime(da.getTime() - fromWeekFirstInMillis);

        }
        String weekFirstDay = new Integer(da.getYear() + 1900).toString();
        weekFirstDay = weekFirstDay + "-" + impleMonth(da.getMonth() + 1);
        weekFirstDay = weekFirstDay + "-" + impleDay(da.getDate());
        return weekFirstDay;
    }

    /**
     * 得到当前周的最后一天（周日）的日期
     * @return
     */
    public static String getWeekLastDate() {
        Date da = new Date();
        int dayOfWeek = da.getDay(); // 当前日期是星期几
        //如果当前天是星期日，则当前天为最后一天
        if (dayOfWeek!=0) {
            long toWeekLastInMillis = (WeekSpan - dayOfWeek) * 24 * 60 * 60 * 1000; // 与该周最后一天相隔的毫秒数
            da.setTime(da.getTime() + toWeekLastInMillis);
        }
        String weekLastDay = new Integer(da.getYear() + 1900).toString();
        weekLastDay = weekLastDay + "-" + impleMonth(da.getMonth() + 1);
        weekLastDay = weekLastDay + "-" + impleDay(da.getDate());
        return weekLastDay;
    }

    /**
     * 得到当前月的第一天的日期
     * @return
     */
    public static String getMonthFirstDate() {
        Date da = new Date();
        long dayOfMonth = da.getDate(); // 当前日期是本月第几天
        long fromMonthFirstInMillis = (dayOfMonth - 1) * 24 * 60 * 60 * 1000; // 与该月第一天相隔的毫秒数
        da.setTime(da.getTime() - fromMonthFirstInMillis);
        String MonthFirstDay = new Integer(da.getYear() + 1900).toString();
        MonthFirstDay = MonthFirstDay + "-" + impleMonth(da.getMonth() + 1);
        MonthFirstDay = MonthFirstDay + "-" + impleDay(da.getDate());
        return MonthFirstDay;
    }

    /**
     * 得到当前月的最后一天的日期
     * @return
     */
    public static String getMonthLastDate() {
        Date da = new Date();
        long dayOfMonth = da.getDate(); // 当前日期是本月第几天
        int monthSpan = 0;
        if ((da.getMonth() + 1) == 2) {
            if (LeapYear(da.getYear() + 1900)) {
                monthSpan = 29;
            } else {
                monthSpan = month[da.getMonth()];
            }
        } else {
            monthSpan = month[da.getMonth()];

        }
        // System.out.println("the montheSpan: "+monthSpan);

        long toMonthLastInMillis = (monthSpan - dayOfMonth) * 24 * 60 * 60 * 1000; // 与该月最后一天相隔的毫秒数
        da.setTime(da.getTime() + toMonthLastInMillis);
        String MonthLastDay = new Integer(da.getYear() + 1900).toString();
        MonthLastDay = MonthLastDay + "-" + impleMonth(da.getMonth() + 1);
        MonthLastDay = MonthLastDay + "-" + impleDay(da.getDate());
        return MonthLastDay;
    }

    /**
     * 得到当前的日期字符串，日期格式为 YYYY-MM-DD
     *
     * @return
     */
    public static String getCurrentDate() {
        Date da = new Date();
        String currentDay = new Integer(da.getYear() + 1900).toString();
        currentDay = currentDay + "-" + impleMonth(da.getMonth() + 1);
        currentDay = currentDay + "-" + impleDay(da.getDate());
        return currentDay;
    }


    //按长度把字符串前补0
    private static String strLen1(String s, int len) {
        if (isNullStr(s)) {
            s = "";

        }
        int strLen = s.length();
        for (int i = 0; i < len - strLen; i++) {
            s = "0" + s;
        }
        return s;
    }

    private static String strLen(String s, int len) {
        if (isNullStr(s)) {
            s = "";
        }
        if (s.length() == 8) {
            return s;
        }
        for (int i = 0; i < len - s.length(); i++) {
            s = "0" + s;
            if (s.length() == 8) {
                break;
            }
        }
        return s;
    }

    /**
     * 判断字符串是否为空
     * @param s
     * @return
     */
    private static boolean isNullStr(String s) {
        if (s == null || s.trim().length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串数组是否为空
     * @param s
     * @return
     */
    private static boolean isNullStr(String[] s) {
        if ((s == null) || (s.length <= 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回日历的年字符串
     * @param cal
     * @return
     */
    public static String getYear(Calendar cal) {
        return String.valueOf(cal.get(cal.YEAR));
    }

    /**
     * 返回日历的月字符串(两位)
     * @param cal
     * @return
     */
    public static String getMonth(Calendar cal) {
        return strLen(String.valueOf(cal.get(cal.MONTH) + 1), 2);
    }

    /**
     * 返回日历的日字符串(两位)
     * @param cal
     * @return
     */
    public static String getDay(Calendar cal) {
        return strLen(String.valueOf(cal.get(cal.DAY_OF_MONTH)), 2);
    }

    /**
     * 返回日历的时字符串(两位)
     * @param cal
     * @return
     */
    public static String getHour(Calendar cal) {
        return strLen(String.valueOf(cal.get(cal.HOUR_OF_DAY)), 2);
    }

    /**
     * 返回日历的分字符串(两位)
     * @param cal
     * @return
     */
    public static String getMinute(Calendar cal) {
        return strLen(String.valueOf(cal.get(cal.MINUTE)), 2);
    }

    /**
     * 返回日历的秒字符串(两位)
     * @param cal
     * @return
     */
    public static String getSecond(Calendar cal) {
        return strLen(String.valueOf(cal.get(cal.SECOND)), 2);
    }

    /**
     * 返回日历的日期字符串（格式："yyyy-mm-dd"）
     * @param cal
     * @return
     */
    public static String getDateStr(Calendar cal) {
        return getYear(cal) + "-" + getMonth(cal) + "-" + getDay(cal);
    }

    /**
     * 返回日历的时间字符串（格式："hh:ss"）
     * @param cal
     * @return
     */
    public static String getTimeStr(Calendar cal) {
        return getHour(cal) + ":" + getMinute(cal);
    }

    /**
     * 返回日历的日期时间字符串（格式："yyyy-mm-dd hh:ss"）
     * @param cal
     * @return
     */
    public static String getDate(Calendar cal) {
        return getDateStr(cal) + " " + getTimeStr(cal);
    }

    /**
     * 返回日期字符串("yyyy-mm-dd hh:ss:mm")的年
     * @param s
     * @return
     */
    public static int getYear(String s) {
        if (s == null || s.length() < 10) {
            return 1970;
        }
        return Integer.parseInt(s.substring(0, 4));
    }

    /**
     * 返回日期字符串("yyyy-mm-dd hh:ss:mm")的月
     * @param s
     * @return
     */
    public static int getMonth(String s) {
        if (s == null || s.length() < 10) {
            return 1;
        }
        return Integer.parseInt(s.substring(5, 7));
    }

    /**
     * 返回日期字符串("yyyy-mm-dd hh:ss:mm")的日
     * @param s
     * @return
     */
    public static int getDay(String s) {
        if (s == null || s.length() < 10) {
            return 1;
        }
        return Integer.parseInt(s.substring(8, 10));
    }
    /**
     * 返回日期字符串("yyyy-mm-dd hh:ss:mm")的时
     * @param s
     * @return
     */
    public static int getHour(String s) {
        if (s == null || s.length() < 16) {
            return 0;
        }
        return Integer.parseInt(s.substring(11, 13));
    }

    /**
     * 返回日期字符串("yyyy-mm-dd hh:ss:mm")的分
     * @param s
     * @return
     */
    public static int getMinute(String s) {
        if (s == null || s.length() < 16) {
            return 0;
        }
        return Integer.parseInt(s.substring(14, 16));
    }

    /**
     * 返回日期字符串("yyyy-mm-dd hh:ss:mm")的秒
     * @param s
     * @return
     */
    public static int getSecond(String s) {
        if (s == null || s.length() < 18) {
            return 0;
        }
        return Integer.parseInt(s.substring(17, 19));
    }

    /**
     * 返回日期时间字符串对应的日历（格式："yyyy-mm-dd hh:ss"）
     * @param s
     * @return
     */
    public static Calendar getCal(String s) {
        Calendar cal = Calendar.getInstance();
        cal.set(getYear(s), getMonth(s), getDay(s), getHour(s), getMinute(s),
                getSecond(s));
        return cal;
    }

    /**
     * 返回日期时间字符串对应的SQL日期（格式："yyyy-mm-dd hh:ss"）
     * @param s
     * @return
     */
    public static java.sql.Date getSqlDate(String s) {
        return java.sql.Date.valueOf(s);
    }

    /**
     * 返回当天日期对应的SQL日期（）
     * @return
     */
    public static java.sql.Date getSqlDate() {
        return java.sql.Date.valueOf(getNowDate());
    }

    /**
     * 取当前日期时间的字符串，格式为"yyyy-mm-dd hh:ss"
     * @return
     */
    public static String getNow() {
        Calendar now = Calendar.getInstance();
        return getDateStr(now) + " " + getTimeStr(now);
    }

    /**
     * 取当前日期的字符串，格式为"yyyy-mm-dd"
     * @return
     */
    public static String getNowDate() {
        Calendar now = Calendar.getInstance();
        return getDateStr(now);
    }

    /**
     * 取当前时间的字符串，格式为"hh:ss"
     * @return
     */
    public static String getNowTime() {
        Calendar now = Calendar.getInstance();
        return getTimeStr(now);
    }

    /**
     * 取当前时间的字符串
     * @return
     */
    public static String getCurrentTimeMillisStr() {

        return (new Long(System.currentTimeMillis()).toString());
    }

    /**
     * 根据当前时间的毫秒数（相对于January 1, 1970 00:00:00），取当前时间的字符串
     * @param time
     * @return
     */
    public static String changTimeMillisToStr(String time) {
        long t = 0l;
        try {
            t = Long.parseLong(time);
        } catch (Exception e) {
            return "";
        }
        Date date = new Date();
        date.setTime(t);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return getDateStr(cal) + " " + getTimeStr(cal);
    }
    /**
     * 根据当前时间的毫秒数（相对于January 1, 1970 00:00:00），取当前时间的字符串
     * @param time
     * @return
     */
    public static String changTimeMillisToStr(long time) {
        String str = "";
        try {
            str = Long.toString(time);
        } catch (Exception e) {
        }
        return changTimeMillisToStr(str);
    }

    /**
     * 格式化字符串为日期的函数.
     *
     * @param strDate 字符串.
     * @param format  转换格式如:"yyyy-MM-dd mm:ss"
     * @return 字符串包含的日期.
     */
    public static Date parseDate(String strDate, String format) {
        try {
            if (strDate == null || strDate.equals(""))
                return null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(strDate);
        } catch (Exception e) {
        }
        return new Date();
    }

    /**
     * 格式化日期为字符串函数.
     *
     * @param date   日期.
     * @param format 转换格式."yyyy-MM-dd mm:ss"
     * @return 日期转化来的字符串.
     */
    public static String formatDate(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 返回缺省的时间模式 (yyyy-MM-dd)
     *
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /**
     * 根据日期返回yyyy-MM-dd格式的日期
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 根据指定的格式，将一个日期字符串转换为 Date对象。
     *
     * @param aMask   the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws ParseException
     * @see java.text.SimpleDateFormat
     */
    public static final Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * 用yyyy-MM-dd HH:MM a日期格式返回当前的时间
     *
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * 返回当前的日期，格式yyyy-MM-dd
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * 根据指定的日前格式，返回日期对象对应的字符串。
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 根据Date 对象返回 其日期格式的字符串，日期格式：yyyy-MM-dd
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }

    /**
     * 使用日期格式（yyyy-MM-dd）转换一个字符串为Date对象
     * @param strDate the date to convert (in format yyyy-MM-dd)
     * @return a date object
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + datePattern);
            }

            aDate = convertStringToDate(datePattern, strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                    + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(),
                    pe.getErrorOffset());

        }

        return aDate;
    }

    /**
     * 得到当前月的最后一天的日期
     * @param da
     * @return
     */
    public static String getLastDate(Date da) {
        int monthSpan = 0;
        int monthValue = da.getMonth();
        if ((monthValue) == 2) {
            if (LeapYear(da.getYear() + 1900)) {
                monthSpan = 29;
            } else {
                monthSpan = month[monthValue - 1];
            }
        } else {
            if (monthValue == 0) {//因为以前都设置12月，但是data方法中只有0-11月
                monthSpan = month[11];
                monthValue = 12;
            } else {
                monthSpan = month[monthValue - 1];
            }

        }
        String MonthLastDay = da.getYear() + "";
        MonthLastDay += "-" + impleMonth(monthValue);
        MonthLastDay += "-" + monthSpan;
        return MonthLastDay;
    }

    /**
     * 得到一个月的最后一天的日期
     *
     * @param year
     * @param month
     * @return
     */
    public static String getEndDayOfMonth(int year, int month) {
        String str = null;
        java.util.Date date = new java.util.Date();
        date.setYear(year);
        date.setMonth(month);
        str = DateUtil.getLastDate(date);
        return str;
    }
    
    /**
	 * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of December in the year 2002
	 */
	public static final String ISO_DATE_FORMAT = "yyyyMMdd";

	/**
	 * Expanded ISO 8601 Date format yyyy-MM-dd i.e., 2002-12-25 for the 25th day of December in the year 2002
	 */
	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * yyyy-MM-dd hh:mm:ss
	 */
	public static final String DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";

	/**
	 * Default lenient setting for getDate.
	 */
	private static boolean LENIENT_DATE = false;

	/**
	 * 暂时不用
	 * @param JD
	 * @return
	 */
	protected static final float normalizedJulian(float JD)
	{

		float f = Math.round(JD + 0.5f) - 0.5f;

		return f;
	}

	/**
	 * 浮点值转换成日期格式<br>
	 * 暂时不用
	 * Returns the Date from a julian. The Julian date will be converted to noon GMT,
	 * such that it matches the nearest half-integer (i.e., a julian date of 1.4 gets
	 * changed to 1.5, and 0.9 gets changed to 0.5.)
	 *
	 * @param JD the Julian date
	 * @return the Gregorian date
	 */
	public static final Date toDate(float JD)
	{

		/* To convert a Julian Day Number to a Gregorian date, assume that it is for 0 hours, Greenwich time (so
		 * that it ends in 0.5). Do the following calculations, again dropping the fractional part of all
		 * multiplicatons and divisions. Note: This method will not give dates accurately on the
		 * Gregorian Proleptic Calendar, i.e., the calendar you get by extending the Gregorian
		 * calendar backwards to years earlier than 1582. using the Gregorian leap year
		 * rules. In particular, the method fails if Y<400. */
		float Z = (normalizedJulian(JD)) + 0.5f;
		float W = (int) ( (Z - 1867216.25f) / 36524.25f);
		float X = (int) (W / 4f);
		float A = Z + 1 + W - X;
		float B = A + 1524;
		float C = (int) ( (B - 122.1) / 365.25);
		float D = (int) (365.25f * C);
		float E = (int) ( (B - D) / 30.6001);
		float F = (int) (30.6001f * E);
		int day = (int) (B - D - F);
		int month = (int) (E - 1);

		if (month > 12)
		{
			month = month - 12;
		}

		int year = (int) (C - 4715); //(if Month is January or February) or C-4716 (otherwise)

		if (month > 2)
		{
			year--;
		}

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1); // damn 0 offsets
		c.set(Calendar.DATE, day);

		return c.getTime();
	}

	/**
	 * Returns the days between two dates. Positive values indicate that
	 * the second date is after the first, and negative values indicate, well,
	 * the opposite. Relying on specific times is problematic.
	 *
	 * @param early the "first date"
	 * @param late the "second date"
	 * @return the days between the two dates
	 */
	public static final int daysBetween(Date early, Date late)
	{

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(early);
		c2.setTime(late);

		return daysBetween(c1, c2);
	}

	/**
	 * Returns the days between two dates. Positive values indicate that
	 * the second date is after the first, and negative values indicate, well,
	 * the opposite.
	 *
	 * @param early
	 * @param late
	 * @return the days between two dates.
	 */
	public static final int daysBetween(Calendar early, Calendar late)
	{

		return (int) (toJulian(late) - toJulian(early));
	}

	/**
	 * Return a Julian date based on the input parameter. This is
	 * based from calculations found at
	 * <a href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day Calculations
	 * (Gregorian Calendar)</a>, provided by Bill Jeffrys.
	 * @param c a calendar instance
	 * @return the julian day number
	 */
	public static final float toJulian(Calendar c)
	{

		int Y = c.get(Calendar.YEAR);
		int M = c.get(Calendar.MONTH);
		int D = c.get(Calendar.DATE);
		int A = Y / 100;
		int B = A / 4;
		int C = 2 - A + B;
		float E = (int) (365.25f * (Y + 4716));
		float F = (int) (30.6001f * (M + 1));
		float JD = C + D + E + F - 1524.5f;

		return JD;
	}

	/**
	 * 暂时不用
	 * Return a Julian date based on the input parameter. This is
	 * based from calculations found at
	 * <a href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day Calculations
	 * (Gregorian Calendar)</a>, provided by Bill Jeffrys.
	 * @param date
	 * @return the julian day number
	 */
	public static final float toJulian(Date date)
	{

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return toJulian(c);
	}

	/**
	 * 日期增加
	 * @param isoString 日期字符串
	 * @param fmt 格式
	 * @param field 年/月/日 Calendar.YEAR/Calendar.MONTH/Calendar.DATE
	 * @param amount 增加数量
	 * @return
	 * @throws ParseException
	 */
	public static final String dateIncrease(String isoString, String fmt, int field, int amount)
	{

		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(stringToDate(isoString, fmt, true));
			cal.add(field, amount);

			return dateToString(cal.getTime(), fmt);

		}
		catch (Exception ex)
		{
			return null;
		}
	}

	/**
	 * Time Field Rolling function.
	 * Rolls (up/down) a single unit of time on the given time field.
	 *
	 * @param isoString
	 * @param field the time field.
	 * @param up Indicates if rolling up or rolling down the field value.
	 * @param expanded use formating char's
	 * @exception ParseException if an unknown field value is given.
	 */
	public static final String roll(String isoString, String fmt, int field, boolean up)
		throws ParseException
	{

		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(
			"GMT"));
		cal.setTime(stringToDate(isoString, fmt));
		cal.roll(field, up);

		return dateToString(cal.getTime(), fmt);
	}

	/**
	 * Time Field Rolling function.
	 * Rolls (up/down) a single unit of time on the given time field.
	 *
	 * @param isoString
	 * @param field the time field.
	 * @param up Indicates if rolling up or rolling down the field value.
	 * @exception ParseException if an unknown field value is given.
	 */
	public static final String roll(String isoString, int field, boolean up)
		throws ParseException
	{

		return roll(isoString, DATETIME_PATTERN, field, up);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * @param dateText 字符串
	 * @param format 日期格式
	 * @param lenient 日期越界标志
	 * @return
	 */
	public static Date stringToDate(String dateText, String format, boolean lenient)
	{

		if (dateText == null)
		{

			return null;
		}

		DateFormat df = null;

		try
		{

			if (format == null)
			{
				df = new SimpleDateFormat();
			}
			else
			{
				df = new SimpleDateFormat(format);
			}

			// setLenient avoids allowing dates like 9/32/2001
			// which would otherwise parse to 10/2/2001
			df.setLenient(false);

			return df.parse(dateText);
		}
		catch (ParseException e)
		{

			return null;
		}
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * @param dateText 字符串
	 * @param format 日期格式
	 * @return
	 */
	public static Date stringToDate(String dateString, String format)
	{

		return stringToDate(dateString, format, LENIENT_DATE);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * @param dateText 字符串
	 */
	public static Date stringToDate(String dateString)
	{

		return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
	}

	/** 根据时间变量返回时间字符串
	 * @return 返回时间字符串
	 * @param pattern 时间字符串样式
	 * @param date 时间变量
	 */
	public static String dateToString(Date date, String pattern)
	{

		if (date == null)
		{

			return null;
		}

		try
		{

			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);

			return sfDate.format(date);
		}
		catch (Exception e)
		{

			return null;
		}
	}

	/**
	 * 根据时间变量返回时间字符串 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date)
	{
		return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
	}

	/** 返回当前时间
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime()
	{
		java.util.Calendar calNow = java.util.Calendar.getInstance();
		java.util.Date dtNow = calNow.getTime();

		return dtNow;
	}

	/**
	 * 返回当前日期字符串
	 * @param pattern 日期字符串样式
	 * @return
	 */
	public static String getCurrentDateString(String pattern)
	{
		return dateToString(getCurrentDateTime(), pattern);
	}

	/**
	 * 返回当前日期字符串 yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDateString()
	{
		return dateToString(getCurrentDateTime(), ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 返回当前日期+时间字符串 yyyy-MM-dd hh:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateToStringWithTime(Date date)
	{

		return dateToString(date, DATETIME_PATTERN);
	}
	
	/**
	 * 日期增加-按时增加
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByHour(Date date, int hours)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);

		return cal.getTime();
	}	

	/**
	 * 日期增加-按日增加
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByDay(Date date, int days)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	/**
	 * 日期增加-按月增加
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByMonth(Date date, int mnt)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, mnt);

		return cal.getTime();
	}
	
	/**
	 * 日期增加-按年增加
	 * @param date
	 * @param mnt
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByYear(Date date, int mnt)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, mnt);

		return cal.getTime();
	}
	/**
	 * 日期增加
	 * @param date 日期字符串 yyyy-MM-dd
	 * @param days
	 * @return 日期字符串 yyyy-MM-dd
	 */
	public static String dateIncreaseByDay(String date, int days)
	{
		return dateIncreaseByDay(date, ISO_DATE_FORMAT, days);
	}

	/**
	 * 日期增加
	 * @param date 日期字符串
	 * @param fmt 日期格式
	 * @param days
	 * @return
	 */
	public static String dateIncreaseByDay(String date, String fmt, int days)
	{
		return dateIncrease(date, fmt, Calendar.DATE, days);
	}

	/**
	 * 日期字符串格式转换
	 * @param src 日期字符串
	 * @param srcfmt 源日期格式
	 * @param desfmt 目标日期格式
	 * @return
	 */
	public static String stringToString(String src, String srcfmt, String desfmt)
	{
		return dateToString(stringToDate(src, srcfmt), desfmt);
	}

        public static final String getCurrentTime() {
                long time = Calendar.getInstance().getTimeInMillis();
                return DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss");
        }
        
        public static void main(String[] a) {
        	System.out.println(DateUtil.getCurrentTime());
        }

        public static final String getCurrentTime(Date d) {
                return DateFormatUtils.format(d, "yyyy-MM-dd HH:mm:ss");
        }

//        public static final String getCurrentDate() {
//                long time = Calendar.getInstance().getTimeInMillis();
//                return DateFormatUtils.format(time, "yyyy-MM-dd 00:00:00");
//        }

        public static final String getCurrentDate(Date d) {
                return DateFormatUtils.format(d, "yyyy-MM-dd 00:00:00");
        }

        public static final String getCurrentDateShortStyle() {
                long time = Calendar.getInstance().getTimeInMillis();
                return DateFormatUtils.format(time, "yyyy-MM-dd");
        }

        public static final String getCurrentDateShortStyle(Date d) {
                return DateFormatUtils.format(d, "yyyy-MM-dd");
        }

        public static final String shortStyle(String longStyleDate) {
                if (longStyleDate == null || "".equals(longStyleDate))
                        return "0000-00-00";
                return longStyleDate.substring(0, 10);
        }

        public static final String longStyle(String shortStyleDate) {
                if (shortStyleDate == null || "".equals(shortStyleDate))
                        return "0000-00-00 00:00:00";
                return shortStyleDate + " 00:00:00";
        }

        /**
         * 日期增加-按日增加
         * @param date
         * @param days
         * @return java.util.Date:返回与当天间隔指定日期的日期,返回日期格式"yyyy-MM-dd 00:00:00");
         */
        public static String dateIncreaseByDay(int days)
        {
          Date d = dateIncreaseByDay(new Date(),days);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd 00:00:00");
        }

        /**
         * 日期增加-按日增加
         * @param date
         * @param days
         * @return java.util.Date:返回与当天间隔指定日期的时间,日期格式"yyyy-MM-dd HH:mm:ss");
         */
        public static String timeIncreaseByHour(int hours)
        {
          Date d = dateIncreaseByHour(new Date(),hours);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd HH:mm:ss");
        }        
        
        /**
         * 日期增加-按日增加
         * @param date
         * @param days
         * @return java.util.Date:返回与当天间隔指定日期的时间,日期格式"yyyy-MM-dd HH:mm:ss");
         */
        public static String timeIncreaseByDay(int days)
        {
          Date d = dateIncreaseByDay(new Date(),days);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd HH:mm:ss");
        }
        
        /**
         * 日期增加-按日增加
         * @param date
         * @param days
         * @return java.util.Date:返回与当天间隔指定日期的日期,返回日期格式"yyyy-MM-dd 00:00:00");
         */
        public static String dateIncreaseByWeek(int weeks)
        {
          int days = 7*weeks;
          Date d = dateIncreaseByDay(new Date(),days);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd 00:00:00");
        }

        /**
         * 日期增加-按日增加
         * @param date
         * @param days
         * @return java.util.Date:返回与当天间隔指定日期的时间,日期格式"yyyy-MM-dd HH:mm:ss");
         */
        public static String timeIncreaseByWeek(int weeks)
        {
        	int days = 7*weeks;
           Date d = dateIncreaseByDay(new Date(),days);
           return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd HH:mm:ss");
        }        

        /**
         * 日期增加-按月增加
         * @param date
         * @param days
         * @return java.util.Date:返回与当天间隔指定月的日期,
         */
        public static String dateIncreaseByMonth(int mnt)
        {
          Date d = dateIncreaseByMonth(new Date(),mnt);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd 00:00:00");
        }

        /**
         * 日期增加-按月增加
         * @param date
         * @param days
         * @return java.util.Date:返回与当天间隔指定月的时间,
         */
        public static String timeIncreaseByMonth(int mnt)
        {
          Date d = dateIncreaseByMonth(new Date(),mnt);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd HH:mm:ss");
        }

        /**
         * 日期增加-按年增加
         * @param date
         * @param mnt
         * @return java.util.Date:返回与当天间隔指定年的时间
         */
        public static String dateIncreaseByYear(int mnt)
        {

          Date d = dateIncreaseByYear(new Date(),mnt);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd 00:00:00");
        }


        /**
         * 日期增加-按年增加
         * @param date
         * @param mnt
         * @return java.util.Date:返回与当天间隔指定年的时间
         */
        public static String timeIncreaseByYear(int mnt)
        {

          Date d = dateIncreaseByYear(new Date(),mnt);
          return DateFormatUtils.format(d.getTime(), "yyyy-MM-dd HH:mm:ss");
        }

        /**
         * 获取本周的第一天
         * @return
         */
        public static Date weekBegin(){
            Calendar mth = Calendar.getInstance();
            mth.set(Calendar.DAY_OF_WEEK, mth.getActualMinimum(Calendar.DAY_OF_WEEK));
            mth.set(Calendar.HOUR_OF_DAY, 0);
            mth.set(Calendar.MINUTE, 0);
            mth.set(Calendar.SECOND, 0);
            return mth.getTime();
        }
        
        /**
         * 获取本周最后一天
         * @return
         */
        public static Date weekEnd(){
            Calendar mth = Calendar.getInstance();
            mth.set(Calendar.DAY_OF_WEEK,  mth.getActualMaximum(Calendar.DAY_OF_WEEK));
            mth.set(Calendar.HOUR_OF_DAY,  23);
            mth.set(Calendar.MINUTE, 59);
            mth.set(Calendar.SECOND, 59);
            return mth.getTime();
        } 
        
        
        /**
         * 获取当月的第一天
         * @return
         */
        public static Date monthBegin(){
            Calendar mth = Calendar.getInstance();         
            mth.set(Calendar.DAY_OF_MONTH, mth.getActualMinimum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY, 0);
            mth.set(Calendar.MINUTE, 0);
            mth.set(Calendar.SECOND, 0);
            return mth.getTime();
        }
        
        /**
         * 获取当月最后一天
         * @return
         */
        public static Date monthEnd(){
            Calendar mth = Calendar.getInstance();
            mth.set(Calendar.DAY_OF_MONTH,  mth.getActualMaximum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY,  23);
            mth.set(Calendar.MINUTE, 59);
            mth.set(Calendar.SECOND, 59);
            return mth.getTime();
        }
        
        /**
         * 获取指定日期当月的第一天
         * @mnt:偏离量，负数为当月前月份
         * @return
         */
        public static Date monthBeginByDate(Date date){
            Calendar mth = Calendar.getInstance();
            mth.setTime(date);
            mth.set(Calendar.DAY_OF_MONTH, mth.getActualMinimum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY, 0);
            mth.set(Calendar.MINUTE, 0);
            mth.set(Calendar.SECOND, 0);
            return mth.getTime();
        }
        
        /**
         * 获取指定日期当月的最后一天
         * @return
         */
        public static Date monthEndByDate(Date date){
            Calendar mth = Calendar.getInstance();
            mth.setTime(date);
            mth.set(Calendar.DAY_OF_MONTH,  mth.getActualMaximum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY,  23);
            mth.set(Calendar.MINUTE, 59);
            mth.set(Calendar.SECOND, 59);
            return mth.getTime();
        }
        
        /**
         * 获取指定日期当月的第一天
         * @mnt:偏离量，负数为当月前月份
         * @return
         */
        public static Date yearBeginByDate(Date date){
            Calendar mth = Calendar.getInstance();
            mth.setTime(date);
            mth.set(Calendar.DAY_OF_YEAR, mth.getActualMinimum(Calendar.DAY_OF_YEAR));
            mth.set(Calendar.HOUR_OF_DAY, 0);
            mth.set(Calendar.MINUTE, 0);
            mth.set(Calendar.SECOND, 0);
            return mth.getTime();
        }
        
        /**
         * 获取指定日期当月的最后一天
         * @return
         */
        public static Date yearEndByDate(Date date){
            Calendar mth = Calendar.getInstance();
            mth.setTime(date);
            mth.set(Calendar.DAY_OF_YEAR,  mth.getActualMaximum(Calendar.DAY_OF_YEAR));
            mth.set(Calendar.HOUR_OF_DAY,  23);
            mth.set(Calendar.MINUTE, 59);
            mth.set(Calendar.SECOND, 59);
            return mth.getTime();
        }         
        
        /**
         * 获取当季的第一天
         * @return
         */
        public static Date seasonBegin(){
            Calendar mth = Calendar.getInstance();
            int season = ((mth.get(Calendar.MONTH))/3)+1;//当前季度值(month从0开始计数)
            int beginMonth = 3*season -3; //季度的第一个月
            
            mth.set(Calendar.MONTH, beginMonth);
            mth.set(Calendar.DAY_OF_MONTH, mth.getActualMinimum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY, 0);
            mth.set(Calendar.MINUTE, 0);
            mth.set(Calendar.SECOND, 0);
            return mth.getTime();
        }
        
        /**
         * 获取当季最后一天
         * @return
         */
        public static Date seasonEnd(){
            Calendar mth = Calendar.getInstance();
            int season = ((mth.get(Calendar.MONTH))/3)+1;//当前季度值
            int endMonth = 3*season -1; //季度的最后一个月 
            mth.set(Calendar.MONTH, endMonth);
            mth.set(Calendar.DAY_OF_MONTH,  mth.getActualMaximum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY,  23);
            mth.set(Calendar.MINUTE, 59);
            mth.set(Calendar.SECOND, 59);
            Date date =  mth.getTime();
            return date;
        }   
        
        /**
         * 获取指定时间所在季节的第一天
         * @return
         */
        public static Date seasonBegin(Date date){
            Calendar mth = Calendar.getInstance();
            mth.setTime(date);
            
            int season = ((mth.get(Calendar.MONTH))/3)+1;//当前季度值(month从0开始计数)
            int beginMonth = 3*season -3; //季度的第一个月
            mth.set(Calendar.MONTH, beginMonth);
            mth.set(Calendar.DAY_OF_MONTH, mth.getActualMinimum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY, 0);
            mth.set(Calendar.MINUTE, 0);
            mth.set(Calendar.SECOND, 0);
            return mth.getTime();
        }
        
        /**
         * 获取指定时间所在季度的最后一天
         * @return
         */
        public static Date seasonEnd(Date date){
            Calendar mth = Calendar.getInstance();
            mth.setTime(date);
            
            int season = ((mth.get(Calendar.MONTH))/3)+1;//当前季度值
            int endMonth = 3*season -1; //季度的最后一个月 
            mth.set(Calendar.MONTH, endMonth);
            mth.set(Calendar.DAY_OF_MONTH,  mth.getActualMaximum(Calendar.DAY_OF_MONTH));
            mth.set(Calendar.HOUR_OF_DAY,  23);
            mth.set(Calendar.MINUTE, 59);
            mth.set(Calendar.SECOND, 59);
            return  mth.getTime();
        }            
        
        /**
         * 获取当年的第一天
         * @return
         */
        public static Date yearBegin(){
            Calendar mth = Calendar.getInstance();
            mth.set(Calendar.DAY_OF_YEAR, mth.getActualMinimum(Calendar.DAY_OF_YEAR));
            mth.set(Calendar.HOUR_OF_DAY, 0);
            mth.set(Calendar.MINUTE, 0);
            mth.set(Calendar.SECOND, 0);
            return mth.getTime();
        }
        
        /**
         * 获取当年最后一天
         * @return
         */
        public static Date yearEnd(){
            Calendar mth = Calendar.getInstance();
            mth.set(Calendar.DAY_OF_YEAR,  mth.getActualMaximum(Calendar.DAY_OF_YEAR));
            mth.set(Calendar.HOUR_OF_DAY,  23);
            mth.set(Calendar.MINUTE, 59);
            mth.set(Calendar.SECOND, 59);
            return mth.getTime();
        }   
        
        public static int getWeekIndex(Date date){
        	Calendar mth = Calendar.getInstance();
        	mth.setTime(date);
        	return mth.get(Calendar.WEEK_OF_YEAR);
        }
        
        /**
    	 * 计算两个String类型的日期时间值之间的时间差,不比较大小，只返回绝对差值
    	 * @param startTime
    	 * @param endTime
    	 * @return long[]={小时，分，秒,总毫秒数},输入参数为空时，返回{0,0,0,0}
    	 */
    	static public long[] getOdds(String startTime,String endTime){
    		long [] rtn = {0,0,0,0};
    		if (null == startTime || null==endTime) return rtn;

    		String[] df = {DateUtil.DATETIME_PATTERN};
    		java.util.Date dateStart;
    		java.util.Date dateEnd;

    		try {
    			dateStart = org.apache.commons.lang.time.DateUtils.parseDate(startTime,df);
    			dateEnd = org.apache.commons.lang.time.DateUtils.parseDate(endTime,df);
    			
    			
    			rtn[3] = (getCal(endTime).getTimeInMillis() - getCal(startTime).getTimeInMillis())/1000;
    			//java.lang.Math.abs(dateEnd.getTime() - dateStart.getTime())/1000;

    			rtn[0] = rtn[3] / 3600;
    			long  left = rtn[3] % 3600;
    			rtn[1] = left / 60;
    			rtn[2] = left % 60;

    		} catch (ParseException e) {
    			throw new RuntimeException("传入参数格式不正确：" + startTime, e);
    		}

    		return rtn;
    	}
}