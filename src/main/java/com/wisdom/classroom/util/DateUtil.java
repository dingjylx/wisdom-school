package com.wisdom.classroom.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author yuzhibing
 */
public class DateUtil {
    public static String[] MONTHS = new String[]{"一月", "二月", "三月", "四月",
            "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    private static int[] DOMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
            30, 31};
    private static int[] lDOMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31,
            30, 31};
    /**
     * 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
    public static final String PATTERN_MM_dd = "MM-dd";
    public static final String PATTERN_MMdd = "MMdd";
    public static final String PATTERN_MMdd2 = "MM/dd";
    public static final String PATTERN_yyMMdd = "yy/MM/dd";
    public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String PATTERN_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_yyyMMdd_HH_mm_ss = "yyyyMMdd HH:mm:ss";

    public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_HH_mm_ss = "HH:mm:ss";
    public static final String PATTERN_HHmmss = "HHmmss";
    public static final String PATTERN_yyyyMMdd_HHmmss = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_yyyyMMdd2 = "yyyy/MM/dd";

    public static final DecimalFormat SECOND_DF = new DecimalFormat("0.000");

    public static final DateTimeFormatter LD_FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter LD_FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter LD_FORMATTER_DATE_TIME_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 取日期是一个月中的几号.
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Object day = cal.get(Calendar.DATE);
        if (day != null) {
            return Integer.valueOf(day.toString());
        } else {
            return 0;
        }
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {

        }
        return date;
    }

    public static Date add(Date now, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        String dateNowStr = sdf.format(calendar.getTime());
        Date today = null;
        try {
            today = sdf.parse(dateNowStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }


    /**
     * 取日期是哪一年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Object year = cal.get(Calendar.YEAR);
        if (year != null) {
            return Integer.valueOf(year.toString());
        } else {
            return 0;
        }
    }

    /**
     * 判断两个时间的大小.
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isCompareTime(Date startTime, Date endTime) {
        if (endTime.getTime() > startTime.getTime()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 取日期是月份.
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //cal.add(Calendar.MONTH, 1);
        Object day = cal.get(Calendar.MONTH);
        if (day != null) {
            return Integer.valueOf(day.toString()) + 1;
        } else {
            return 0;
        }
    }

    public static int getDaysOfmonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        if ((cal.get(Calendar.YEAR) % 4) == 0) {
            if ((cal.get(Calendar.YEAR) % 100) == 0
                    && (cal.get(Calendar.YEAR) % 400) != 0)
                return DOMonth[cal.get(Calendar.MONTH)];
            return lDOMonth[cal.get(Calendar.MONTH)];
        } else
            return DOMonth[cal.get(Calendar.MONTH)];
    }

    public static Calendar getClearCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Date getDateAfterDays(Date date, int duration) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, duration);
        return cal.getTime();
    }

    public static Date getDateAfter0000Date(Date date, int duration) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, duration);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        return cal.getTime();
    }

    public static Date getDateAfter2359Date(Date date, int duration) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, duration);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date getDateBeforeHours(Date date, int duration) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -duration);
        return cal.getTime();
    }

    public static Date getDateAfterMinutes(long duration) {
        long curr = System.currentTimeMillis();
        curr = curr + duration * 60 * 1000;
        return new Date(curr);
    }

    public static String formatDate(Date date, String format) {
        return getFormatDate(date, format);
    }

    /**
     * 格式时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFormatDate(Date date, String format) {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(date);
        } else {
            return null;
        }
    }

    public static String getZHDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";
            default:
                return "";
        }
    }

    /**
     * 当前日期小时相加或相减所得日期（+,-）操作,输入一个日期得到天数加减后的日期。
     *
     * @return
     */
    public static Date DsDay_Hour(Date date, Integer hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);

        return cal.getTime();
    }

    /**
     * 把参数时间精确到天
     *
     * @return
     */

    public static Date accurateToDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return clearDateAfterDay(cal);
    }

    public static int getDayOfWeek(Calendar cal) {// 得到每月1号是星期几
        cal.set(Calendar.DATE, 1);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date getTheMiddle(Date date, int plus) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, plus);
        return cal.getTime();
    }

    public static Map<String, Object> getBeginAndEndDateByDate(Date date) {

        Calendar calClearDate = Calendar.getInstance();
        calClearDate.setTime(date);
        calClearDate.set(Calendar.DATE, 1);
        date = calClearDate.getTime();
        Map<String, Object> map = new HashMap<String, Object>();
        // SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int dayOfWeek = getDayOfWeek(cal);
        cal.set(Calendar.DATE, -(dayOfWeek - 2));
        map.put("beginDate", cal.getTime());
        cal.add(Calendar.DATE, 21);
        map.put("currPageDate", cal.getTime());
        cal.add(Calendar.DATE, 20);
        map.put("endDate", cal.getTime());
        return map;
    }

    /**
     * 根据格式获取日期字符串.
     *
     * @param format
     * @param aDate
     * @return
     */
    public static String getDateTime(String format, Date aDate) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        String returnValue = df.format(aDate);
        return returnValue;
    }

    /**
     * 当前日期分钟相加或相减所得日期（+,-）操作,输入一个日期得到分钟加减后的日期。
     *
     * @param months
     * @return
     */
    public static Date DsDay_Minute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minutes = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, minutes + minute);
        return calendar.getTime();
    }

    /**
     * 当前日期秒钟相加或相减所得日期（+,-）操作,输入一个日期得到分钟加减后的日期。
     *
     * @return
     */
    public static Date DsDay_Second(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int seconds = calendar.get(Calendar.SECOND);
        calendar.set(Calendar.SECOND, seconds + second);
        return calendar.getTime();
    }

    /**
     * 清理所有天后面的日期时间
     *
     * @param c
     * @return
     */
    public static Date clearDateAfterDay(Calendar c) {
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        return c.getTime();
    }

    /**
     * String转Date
     *
     * @param sdate    日期字符串
     * @param fmString 指定日期格式
     * @return
     */
    public static Date toDate(String sdate, String fmString) {
        DateFormat df = new SimpleDateFormat(fmString);
        try {
            return df.parse(sdate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式不正确 ");
        }
    }

    /**
     * 根据指定的格式格式化时间
     */
    public static Date toDate(Date date, SimpleDateFormat sdf) {
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据出生日期得到年龄
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow < monthBirth
                || (monthNow == monthBirth && dayOfMonthNow < dayOfMonthBirth)) {
            age--;
        }
        return age;
    }

    // 计算两个日期之间有多少天
    public static int getDaysBetween(Date startDate, Date endDate) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 当前日期相加或相减所得日期（+,-）操作
     *
     * @return Date
     */
    public static Date dsDay_Date(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int days = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, days + day);
        Date cc = calendar.getTime();
        return cc;
    }

    /**
     * 取年.
     *
     * @param date
     * @return
     */
    public static String getFormatYear(Date date) {
        String str = null;
        if (date != null) {
            str = DateFormatUtils.format(date, "yyyy");
        }
        return str;
    }

    public static List<Date> getDateList(Date beginDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        List<Date> dateList = new ArrayList<Date>();

        dateList.add(beginDate);

        while (true) {
            if (clean(beginDate).equals(endDate)) {
                break;
            }
            calendar.add(Calendar.DATE, 1);
            Date currentDate = calendar.getTime();
            dateList.add(currentDate);
            if (currentDate.after(endDate)
                    || clean(currentDate).equals(clean(endDate))) {
                break;
            }

        }
        return dateList;
    }

    /**
     * 得到前一天晚上6点钟.
     *
     * @return
     * @throws ParseException
     */
    public static Date getYestoday18Hour() throws ParseException {
        Date d = new Date();
        SimpleDateFormat simpleOldDate = new SimpleDateFormat(
                "yyyy-MM-dd 00:00:00");
        Calendar ca = Calendar.getInstance();
        ca.setTime(simpleOldDate.parse(simpleOldDate.format(d)));
        ca.add(Calendar.DATE, -1);
        ca.add(Calendar.HOUR, 18);
        return ca.getTime();
    }

    private static Date clean(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getAfterDay(Date date) {
        SimpleDateFormat simpleOldDate = new SimpleDateFormat(
                "yyyy-MM-dd 00:00:00");
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
        } catch (ParseException e) {
            ca.setTime(getDayStart(new Date()));
        }
        ca.add(Calendar.DATE, 1);
        return ca.getTime();

    }

    public static Date getBeforeDay(Date date) {
        SimpleDateFormat simpleOldDate = new SimpleDateFormat(
                "yyyy-MM-dd 00:00:00");
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
        } catch (ParseException e) {
            ca.setTime(getDayStart(new Date()));
        }
        ca.add(Calendar.DATE, -1);
        return ca.getTime();

    }

    public static Date getToDay(Date date) {
        SimpleDateFormat simpleOldDate = new SimpleDateFormat(
                "yyyy-MM-dd 00:00:00");
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
        } catch (ParseException e) {
            ca.setTime(getDayStart(new Date()));
        }
        return ca.getTime();

    }

    /**
     * 返回 该日期的开始处
     *
     * @param date
     * @return
     */
    public static Date getDayStart(Date date) {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getDayEnd(Date date) {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 得到当前时间的23点.
     *
     * @param date
     * @return
     */
    public static Date getDayEndBeforeOneH(Date date) {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);
        return c.getTime();
    }

    /**
     * date1是否早于date2
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean inAdvance(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.getTime() < date2.getTime();
    }

    /**
     * 将日期和时分的两个时间合并到一起
     *
     * @param date
     * @param time
     * @return
     */
    public static Date mergeDateTime(Date date, Date time) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        dateCalendar.set(Calendar.HOUR, timeCalendar.get(Calendar.HOUR));
        dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
        dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
        return dateCalendar.getTime();
    }

    public static Date getDateByStr(String dateStr, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换到时间格式
     *
     * @param dateStr   需要转换的字符串
     * @param formatStr 需要格式的目标字符串 举例 yyyy-MM-dd
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date stringToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 比较2个日期 前一个日期至少比后一个日期大一天以上
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDateLessOneDayMore(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        if (c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)) {
            return true;
        } else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) > c2.get(Calendar.MONTH)) {
            return true;
        } else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DATE) > c2.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }

    /**
     * 将日期加年.
     *
     * @param date
     * @param time
     * @return
     */
    public static Date mergeDateTimeAddYear(Date date, Integer years) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        dateCalendar.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR)
                + years);
        return dateCalendar.getTime();
    }

    /**
     * 将日期加月.
     *
     * @param date
     * @param time
     * @return
     */
    public static Date mergeDateTimeAddMonth(Date date, Integer Months) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        dateCalendar.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH)
                + Months);
        return dateCalendar.getTime();
    }

    public static Long getMinBetween(Date startDate, Date endDate) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        Long min = 0L;
        if (d1.getTimeInMillis() > d2.getTimeInMillis()) {
            min = (d1.getTimeInMillis() - d2.getTimeInMillis()) / (1000 * 60);
        } else {
            min = (d2.getTimeInMillis() - d1.getTimeInMillis()) / (1000 * 60);
        }
        return min;
    }

    /**
     * 相差秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getMillisBetween(Date startDate, Date endDate) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        Long min = 0L;
        if (d1.getTimeInMillis() > d2.getTimeInMillis()) {
            min = (d1.getTimeInMillis() - d2.getTimeInMillis()) / 1000;
        } else {
            min = (d2.getTimeInMillis() - d1.getTimeInMillis()) / 1000;
        }
        return min;
    }

    /**
     * 获取今天的日期，去掉时、分、秒
     */
    public static Date getTodayYMDDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 得到今天几点
     *
     * @return
     */
    public static int getTodayHourDate() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 获取今天的日期yyyy-MM-dd格式
     *
     * @return
     */
    public static Date getTodayDate() {
        return stringToDate(formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
    }

    /**
     * 将分钟转成小时数,有小误差
     */
    public static float convertToHours(Long minutes) {
        if (minutes == null) {
            return 0f;
        }
        String fStr = (new Float(minutes + "") / 60) + "";
        String res = fStr.substring(0, fStr.lastIndexOf(".") + 2);
        return Float.parseFloat(res);
    }

    public static void main(String[] args) {
        System.out.println(minutesToDate(1L));
    }

    /**
     * 将小时数转为分钟,有小误差
     */
    public static long convertToMinutes(Float hours) {
        if (hours == null) {
            return 0L;
        }
        long h = hours.intValue() * 60;
        Float f = (hours - new Float(hours.intValue())) * 60;
        String fStr = f.toString();
        return h + Long.parseLong(fStr.substring(0, fStr.indexOf(".")));
    }

    /**
     * 年月日时分秒的日期转只有年月日的日期
     */
    public static Date toYMDDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 本月第一天
     */
    public static Date getFirstdayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 本月最后一天
     */
    public static Date getLastdayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        date = cal.getTime();
        return date;
    }

    /**
     * date 减去当前日期 . 剩余0天0时0分
     *
     * @return str
     */
    public static String getRemainTimeByCurrentDate(Date date) {
        String str = "剩余0天0时0分";
        if (null != date) {
            Date d = new Date();
            long seconds = (date.getTime() - d.getTime()) / 1000;
            if (seconds > 0) { // 秒
                long day = seconds / (3600 * 24); // 天数
                long house = (seconds % (3600 * 24)) / 3600; // 小时
                long min = (seconds % (3600)) / 60;// 分
                return "剩余" + day + "天" + house + "时" + min + "分";
            }

        }
        return str;
    }

    /**
     * 得到一个时间的具体日期 例如：两个月后的前一个星期 month = 3 ; day = -7
     *
     * @param Month
     * @param day
     * @return
     */
    public static Date getTimesByTimes(int Month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, Month);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 返回当前时间 毫秒
     *
     * @param date
     * @return
     */
    public static long getDateTime(String date) {
        if (StringUtils.isEmpty(date)) {
            return 0L;
        }
        Date d = toDate(date, "yyyy-MM-dd");
        return d.getTime();
    }

    /**
     * 比较2个日期 前一个日期至少比后一个日期大一天以上
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String compareDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return null;
        }
        if (date1.getTime() - date2.getTime() < 0) {
            return null;
        }
        long seconds = (date1.getTime() - date2.getTime()) / 1000;
        Long day = seconds / (3600 * 24); // 天数
        Long hour = (seconds % (3600 * 24)) / 3600; // 小时
        Long min = (seconds % (3600)) / 60;// 分
        String str = "";
        if (day > 0) {
            str = str + day + "天";
        }
        if (hour > 0) {
            str = str + hour + "小时";
        }
        if (min > 0) {
            str = str + min + "分";
        }
        return str;
    }

    /**
     * 在特定的日期上，加入特定的时间点，返回新的日期
     *
     * @param date 特定日期
     * @param time 给入的时间点
     *             time 的格式举例：09:00
     * @return
     */
    public static Date defineDate(Date date, String time) throws Exception {
        try {
            String[] strs = time.split(":");
            int hour = Integer.parseInt(strs[0]);
            int minute = Integer.parseInt(strs[1]);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        } catch (Exception e) {
            throw new Exception("给入的时间点参数格式错误！");
        }
    }

    public static List<Date> converFromString(String addition) {
        String[] ds = addition.split(",");
        List<Date> dateList = new ArrayList<Date>();
        for (String d : ds) {
            d = StringUtils.trimToNull(d);
            if (d == null) {
                continue;
            }
            try {
                dateList.add(new SimpleDateFormat(PATTERN_yyyyMMdd).parse(d));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(dateList);
        return dateList;
    }

    public static String convertDate(Date specDate) {
        return new SimpleDateFormat(PATTERN_yyyyMMdd).format(specDate);
    }

    public static Date converDateFromStr(String str) {
        try {
            return new SimpleDateFormat(PATTERN_yyyyMMdd).parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Date converDateFromStr2(String str) {
        try {

            return new SimpleDateFormat(PATTERN_yyyyMMdd2).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date converDateFromStr4(String str) {
        try {

            return new SimpleDateFormat(PATTERN_yyyMMdd_HH_mm_ss).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date converDateFromStr5(String str) {
        try {

            return new SimpleDateFormat(PATTERN_yyyy_MM_dd_HH_mm_ss).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date converDateFromStr3(String str) {

        if (StringUtils.isEmpty(str)) {
            return new Date();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }


    public static String convertDate3(Date specDate) {
        String str = "";
        try {
            str = new SimpleDateFormat(PATTERN_yyyy_MM_dd).format(specDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }

    public static String convertDate4(Date specDate) {
        String str = "";
        try {
            str = new SimpleDateFormat(PATTERN_yyyyMMddHHmmss).format(specDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }

    public static String convertDate5(Date specDate) {
        String str = "";
        try {
            str = new SimpleDateFormat(PATTERN_yyyyMMdd_HHmmss).format(specDate);
        } catch (Exception ex) {
            return null;
        }
        return str;
    }


    public static Date getBeforeDayBySecond(int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - second);
        return calendar.getTime();
    }

    public static List<String> getLimitDateByWeek(String beginDate, String endDate, String weeks) {
        List<String> dates = new ArrayList<String>();
        Date begin = DateUtil.getDateByStr(beginDate, "yyyy-MM-dd");
        Date end = DateUtil.getDateByStr(endDate, "yyyy-MM-dd");
        while (!begin.after(end)) {
            Calendar c = Calendar.getInstance();
            c.setTime(begin);
            int day = c.get(Calendar.DAY_OF_WEEK) - 1;
            if (weeks.contains("" + day)) {
                dates.add(DateUtil.formatDate(begin, "yyyy-MM-dd"));
            }
            begin = DateUtil.dsDay_Date(begin, 1);
        }
        return dates;
    }

    /**
     * 计算两个日期的天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) {
        long between_days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 当前日期按月份相加之后所得日期
     *
     * @param date
     * @param month
     * @return
     */
    public static Date getDate_Month(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthTest = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, monthTest + month);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date cc = calendar.getTime();
        return cc;
    }

    public static String minutesToDate(Long minutes) {
        if (minutes == null) {
            return "";
        }
        double time = (double) minutes;
        int day = 0;
        int hour = 0;
        int minute = 0;
        if (time > 0) {
            day = (int) Math.ceil(time / 1440);
            if (time % 1440 == 0) {
                hour = 0;
                minute = 0;
            } else {
                hour = (int) (1440 - time % 1440) / 60;
                minute = (int) (1440 - time % 1440) % 60;
            }
        } else if (time < 0) {
            time = -time;
            hour = (int) time / 60;
            minute = (int) time % 60;
        }
        String hourStr = "" + hour, minuteStr = "" + minute;
        if (hour < 10)
            hourStr = "0" + hour;
        if (minute < 10)
            minuteStr = "0" + minute;
        return day + "天" + hourStr + "点" + minuteStr + "分";
    }


    public static String getLogTime(Date date) {
        if (date != null) {
            SimpleDateFormat logFormat = new SimpleDateFormat("MM月dd日 HH时mm分ss秒");
            return logFormat.format(date);
        }
        return null;
    }

    // 计算两个日期之间有多少分钟
    public static long getMinutesBetween(Date startDate, Date endDate) {
        return (startDate.getTime() - endDate.getTime()) / 1000 / 60;
    }

    //日期加上三个月
    public static Date addThreeMonthDate(Date visitTime) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(visitTime);
        //日期加3个月
        rightNow.add(Calendar.MONTH, 3);
        return rightNow.getTime();
    }

    //第二天的Date
    public static Date getNextDate(Date visitTime) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(visitTime);
        //日期加3个月
        rightNow.add(Calendar.DAY_OF_YEAR, 1);
        return rightNow.getTime();
    }

    public static Date addHour(Date time, int n) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        //日期加3个月
        rightNow.add(Calendar.HOUR, n);
        return rightNow.getTime();
    }

    public static Long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    public static String getDurationMillis(Long lastMillis) {
        return SECOND_DF.format(Double.parseDouble(String.valueOf(System.currentTimeMillis() - lastMillis)) / 1000.0);
    }


    /**
     * 将 expireTime 和当前时间比较，判断是否过期
     *
     * @param expireTime yyyyMMddHHmmss
     * @return true：过期
     */
    public static boolean dateExpire(String expireTime) {
        LocalDateTime expire = LocalDateTime.parse(expireTime, LD_FORMATTER_DATE_TIME);
        return expire.isBefore(LocalDateTime.now());
    }

    /**
     * 将 expireTime 和当前时间比较，判断是否未过期
     *
     * @param expireTime yyyyMMddHHmmss
     * @return return true：未过期 false:已过期
     */
    public static boolean dateNoExpire(String expireTime) {
        return !dateExpire(expireTime);
    }

    /**
     * 判断当前日期yyyyMMdd是否和指定日期处于同一天
     *
     * @param dateTime
     * @return
     */
    public static boolean withinSameDate(Date dateTime) {
        if (Objects.isNull(dateTime)) {
            return false;
        }
        String currentDate = LocalDate.now().format(LD_FORMATTER_DATE);
        String date = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(LD_FORMATTER_DATE);
        if (StringUtils.equals(currentDate, date)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(LD_FORMATTER_DATE_TIME_2);
    }


    /**
     * 返回当前时间和指定时间相差多少分  ： 30m
     *
     * @param dateTime
     * @return
     */
    public static String getIntervalMinutes(String dateTime) {
        if (dateExpire(dateTime)) {
            return "0m";
        }
        LocalDateTime expire = LocalDateTime.parse(dateTime, LD_FORMATTER_DATE_TIME);
        return Duration.between(LocalDateTime.now(), expire).toMinutes() + "m";
    }

    /**
     * yyyyMMddHHmmss 转换为Date
     *
     * @param dateTime
     * @return
     */
    public static Date strToDate(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, LD_FORMATTER_DATE_TIME);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 计算指定前多少（d:天，h:小时，m：分钟）的时间
     *
     * @param param
     * @return
     */
    public static Date getDate(String param) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        if (param.contains("d")) {
            date.set(Calendar.DATE, date.get(Calendar.DATE) - Integer.valueOf(param.substring(0, param.indexOf("d"))));
        } else if (param.contains("h")) {
            date.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) - Integer.valueOf(param.substring(0, param.indexOf("h"))));
        } else if (param.contains("m")) {
            date.set(Calendar.MINUTE, date.get(Calendar.MINUTE) - Integer.valueOf(param.substring(0, param.indexOf("m"))));
        }
        return date.getTime();
    }

}
