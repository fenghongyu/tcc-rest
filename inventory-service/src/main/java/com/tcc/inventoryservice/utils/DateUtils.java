package com.tcc.inventoryservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Seconds;

public class DateUtils {
    // 标准日期时间格式
    /**
     * yyyy-MM
     */
    public static final String FORMAT_MONTH = "yyyy-MM";

    public static final String FORMAT_YEAR_MONTH = "yyyyMM";
    /**
     * yyyy
     */
    public static final String FORMAT_DATE_YEAR = "yyyy";
    /**
     * dd
     */
    public static final String FORMAT_DATE_DAY = "dd";

    /**
     * MM/dd
     */
    public static final String FORMAT_DATE_MONTH_DAY = "MM/dd";

    public static final String FORMAT_DATE_MONTH_DAY_2 = "MM-dd";

    public static final String FORMAT_DATE_MONTH_DAY_CH = "M月d日";

    public static final String FORMAT_DATE_MONTH_HOUR_MINUTE_CH = "MM月dd日 HH:mm";

    public static final String FORMAT_DATE_YEAR_MONTH_DAY_CH = "yyyy年MM月dd日";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * yyyy/MM/dd
     */
    public static final String FORMAT_DATE_1 = "yyyy/MM/dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_DATE_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * HH:mm
     */
    public static final String FORMAT_HOUR_MINUTE = "HH:mm";

    public static final String FORMAT_HOUR = "HH";
    /**
     * MM-dd HH:mm
     */
    public static final String FORMAT_MONTH_DAY_HOUR_MINUTE = "MM-dd HH:mm";
    /**
     * yyyy-MM-dd (星期一) HH:mm
     */
    public static final String FORMAT_DATE_TIME_WEEK = "yyyy-MM-dd (EEEE) HH:mm";

    /**
     * 星期一
     */
    public static final String FORMAT_DATE_WEEK = "EEEE";

    // 无符号格式
    /**
     * yyyyMMdd
     */
    public static final String FORMAT_DATE_UNSIGNED = "yyyyMMdd";
    /**
     * yyyyMMddHHmmss
     */
    public static final String FORMAT_DATE_TIME_UNSIGNED = "yyyyMMddHHmmss";
    /**
     * yyyyMMddHHmmssSSS
     */
    public static final String FORMAT_DATE_TIMEMILL_UNSIGNED = "yyyyMMddHHmmssSSS";

    /**
     * 将标准时间转成时间格式
     * @param date 标准时间
     * @return 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        return format(date, FORMAT_DATE_TIME);
    }

    /**
     * 按指定格式格式化时期时间
     * @param date   date
     * @param format format
     * @return string.
     */
    public static String format(Date date, String format) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * 将时间的字符串格式转成Date
     * @param str str
     * @return Date
     */
    public static Date parse(String str) {
        return parse(str, FORMAT_DATE_TIME);
    }

    /**
     * 按指定格式解析字符串，将字符串转为日期时间格式
     * @param str    string
     * @param format format
     * @return date
     */
    public static Date parse(String str, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断上下午
     */
    public static boolean isDateTimeBeforeNoon(Date date) {
        Date from = DateUtils.getStatisDayTime(date, 0);
        return (date.getTime() - from.getTime()) < 1000 * 60 * 60 * 12;
    }

    /**
     * 两个日期相隔秒数
     */
    public static int secondsBetween(Date start, Date end) {
        return Seconds.secondsBetween(new Instant(start.getTime()), new Instant(end.getTime())).getSeconds();
    }

    /**
     * 增加分钟数
     */
    public static Date plusMinutes(Date date, int minutes) {
        return new DateTime(date.getTime()).plusMinutes(minutes).toDate();
    }

    /**
     * 增加小时数
     */
    public static Date plusHours(Date date, int hours) {
        return new DateTime(date.getTime()).plusHours(hours).toDate();
    }

    /**
     * 增加天数
     */
    public static Date plusDays(Date date, int days) {
        return new DateTime(date.getTime()).plusDays(days).toDate();
    }

    /**
     * 增加月数
     */
    public static Date plusMonths(Date date, int months) {
        return new DateTime(date.getTime()).plusMonths(months).toDate();
    }

    /**
     * 增加年数
     */
    public static Date plusYears(Date date, int years) {
        return new DateTime(date.getTime()).plusYears(years).toDate();
    }

    public static Date getStatisDayTime(Date currentTime, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, value);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getStartOfTheDay(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getEndOfTheDay(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获得当前周--周一的日期
     */
    public static Date getCurrentMonday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        return monday;
    }

    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK); // 获得今天是一周的第几天，星期日是第一天
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 转化成容易阅读的日期格式（标准版）：
     * 去年以前 ----> 2016-09-10
     * 今年 ----> 09-10
     * 昨天 ----> 昨天 14:23
     * 今天 ----> 14:23
     */
    public static String toDisplayDatetime2(Date date) {
        if (date == null) {
            return null;
        }

        Calendar temp = Calendar.getInstance();
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);

        temp.add(Calendar.DATE, 1);

        String formatStr;
        if (date.getTime() >= temp.getTimeInMillis()) {
            // 明天或以后...
            formatStr = "yyyy-MM-dd HH:mm";
        } else {
            temp.add(Calendar.DATE, -1);
            if (date.getTime() >= temp.getTimeInMillis()) {
                // 今天
                formatStr = "HH:mm";
            } else {
                temp.add(Calendar.DATE, -1);
                if (date.getTime() >= temp.getTimeInMillis()) {
                    // 昨天
                    formatStr = "昨天 HH:mm";
                } else {
                    temp.set(Calendar.DAY_OF_YEAR, 1);
                    if (date.getTime() >= temp.getTimeInMillis()) {
                        // 今年
                        formatStr = "MM-dd";
                    } else {
                        // 很久以前...
                        formatStr = "去年 yyyy-MM-dd";
                    }
                }
            }
        }

        return format(date, formatStr);
    }

    /**
     * 转化成容易阅读的日期格式（标准版）：
     * 去年以前 ----> 2016-09-10 14:23
     * 今年 ----> 09-10 14:23
     * 昨天 ----> 昨天 14:23
     * 今天 ----> 14:23
     */
    public static String toDisplayDatetime(Date date) {
        if (date == null) {
            return null;
        }

        Calendar temp = Calendar.getInstance();
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);

        temp.add(Calendar.DATE, 1);

        String formatStr;
        if (date.getTime() >= temp.getTimeInMillis()) {
            // 明天或以后...
            formatStr = "yyyy-MM-dd HH:mm";
        } else {
            temp.add(Calendar.DATE, -1);
            if (date.getTime() >= temp.getTimeInMillis()) {
                // 今天
                formatStr = "HH:mm";
            } else {
                temp.add(Calendar.DATE, -1);
                if (date.getTime() >= temp.getTimeInMillis()) {
                    // 昨天
                    formatStr = "昨天 HH:mm";
                } else {
                    temp.set(Calendar.DAY_OF_YEAR, 1);
                    if (date.getTime() >= temp.getTimeInMillis()) {
                        // 今年
                        formatStr = "MM-dd HH:mm";
                    } else {
                        // 很久以前...
                        formatStr = "yyyy-MM-dd HH:mm";
                    }
                }
            }
        }

        return format(date, formatStr);
    }

    public static String toDisplayPreviewDatetime(Date date) {
        if (date == null) {
            return null;
        }

        Calendar temp = Calendar.getInstance();
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);

        temp.add(Calendar.DATE, 2);

        String formatStr = "MM-dd HH:mm";
        if (date.getTime() >= temp.getTimeInMillis()) {
            // 后天或以后...
            return format(date, formatStr);
        } else {
            String tag = "今天 ";
            temp.add(Calendar.DATE, -2);
            if (date.getTime() >= temp.getTimeInMillis()) {
                // 今天或明天
                formatStr = "HH:mm";
                temp.add(Calendar.DATE, 1);
                if (date.getTime() >= temp.getTimeInMillis()) {
                    tag = "明天 ";
                }
            }
            return tag + format(date, formatStr);
        }
    }

    /**
     * 转化成容易阅读的日期格式（中国表示版）：
     * 今天 ----> 今天9月2日
     * 昨天 ----> 昨天9月2日
     * 其他 ----> 9月2日
     */
    public static String toChineseDisplayDatetime(Date date) {
        if (date == null) {
            return null;
        }

        Calendar temp = Calendar.getInstance();
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);

        temp.add(Calendar.DATE, 1);

        String formatStr;
        if (date.getTime() >= temp.getTimeInMillis()) {
            // 明天或以后...
            formatStr = "M月d日";
        } else {
            temp.add(Calendar.DATE, -1);
            if (date.getTime() >= temp.getTimeInMillis()) {
                // 今天
                formatStr = "今天M月d日";
            } else {
                temp.add(Calendar.DATE, -1);
                if (date.getTime() >= temp.getTimeInMillis()) {
                    // 昨天
                    formatStr = "昨天M月d日";
                } else {
                    formatStr = "M月d日";
                }
            }
        }

        return format(date, formatStr);
    }

    /**
     * 刚刚
     * 5分钟前
     * 5小时前
     * 5天前
     * 5月前
     */
    public static String toFormatDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -1);

        String str = "";
        if (date.getTime() > now.getTimeInMillis()) {
            str = "刚刚";
        } else {
            int n = 2;
            while (n <= 60) {
                now.add(Calendar.MINUTE, -1);
                if (date.getTime() > now.getTimeInMillis()) {
                    str = n - 1 + "分钟前";
                    return str;
                }
                n++;
            }
            n = 2;
            while (n <= 24) {
                now.add(Calendar.HOUR_OF_DAY, -1);
                if (date.getTime() > now.getTimeInMillis()) {
                    str = n - 1 + "小时前";
                    return str;
                }
                n++;
            }
            n = 2;
            while (n <= 30) {
                now.add(Calendar.DAY_OF_MONTH, -1);
                if (date.getTime() > now.getTimeInMillis()) {
                    str = n - 1 + "天前";
                    return str;
                }
                n++;
            }
            n = 2;
            while (n <= 12) {
                now.add(Calendar.MONTH, -1);
                if (date.getTime() > now.getTimeInMillis()) {
                    str = n - 1 + "月前";
                    return str;
                }
                n++;
            }
            n = 2;
            while (n <= 100) {
                now.add(Calendar.YEAR, -1);
                if (date.getTime() > now.getTimeInMillis()) {
                    str = n - 1 + "年前";
                    return str;
                }
                n++;
            }
        }
        return str;
    }

    /**
     * 转化成 14:23
     */
    public static String toTimelineDatetime(Date date) {
        return format(date, "HH:mm");
    }

    /**
     * 是否在指定分钟内
     */
    public static boolean isWithinNMin(Date date1, Date date2, int n) {
        if (date1 != null && date2 != null) {
            return (date1.getTime() - date2.getTime()) < (1000 * 60 * n);
        }
        return false;
    }

    public static int getHourMinutes(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour * 100 + minute;
    }

    /**
     * 计算相差天数
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        Date beginDate = parse(beginDateStr, FORMAT_DATE);
        Date endDate = parse(endDateStr, FORMAT_DATE);
        return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static long getDaySub(Date beginDate, Date endDate) {
        return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算相差分钟数
     */
    public static long getMinuesSub(Date beginDate, Date endDate) {
        return (endDate.getTime() - beginDate.getTime()) / (60 * 1000);
    }

    /**
     * 判断指定时间是否是去年及以前的时间 false : 今年 ； true  去年及以前
     */
    public static boolean isBeforeCurrentYear(Date date) {
        // 创建 Calendar 对象
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        int dateYear = dateCal.get(Calendar.YEAR);
        if (currentYear == dateYear) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     */
    public static String secondsTimeStamp2DateFormat(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = FORMAT_DATE_TIME;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     */
    public static Date secondsTimeStamp2DateParse(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return new Date();
        }
        return new Date(Long.valueOf(seconds + "000"));
    }

    public static String getSellTime(Date date) {
        String tem;
        if (DateUtils.isBeforeCurrentYear(date)) {
            tem = DateUtils.format(date, DateUtils.FORMAT_DATE);
        } else {
            tem = DateUtils.toDisplayDatetime(date);
        }
        return tem;
    }

    /**
     * 相差天数，区分润非润年
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }
    }

}
