package com.dianxin.imessage.common.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * ============================================================================== Copyright (c) 2015 by www.tencent.com,
 * All rights reserved. ============================================================================== This software is
 * the confidential and proprietary information of tencent.com, Inc. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 * you entered into with tencent.com, Inc.
 * ------------------------------------------------------------------------------
 * <p/>
 * Author: faberxu Date: 2015/12/18 Description: Nothing. Function List: 1. Nothing. History: 1. Nothing.
 * ==============================================================================
 */
public class DateUtil extends DateUtils {

    public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public final static String yyyyMMdd = "yyyyMMdd";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    public static final String BIRTHDAY_FORMAT = "MM-dd";

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 根据计算今天与传过来的String类型的字符串相隔天数
     *
     * @param dateStr
     * @return
     */
    public static int calculateNumberOfDays(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        try {
            Date date = sdf.parse(dateStr);
            Date now = new Date();
            long n1 = now.getTime();
            long n2 = date.getTime();
            if (n1 > n2) {
                long diffTime = n1 - n2;
                int numberOfDays = (int) (diffTime / (3600 * 1000 * 24));
                return numberOfDays;
            } else {
                return 0;
            }
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getLocalTimeFromUTC(Date UTCDate) {
        format.setTimeZone(TimeZone.getDefault());
        return format.format(UTCDate);
    }

    /**
     * 计算穿过来的字符串与今天相隔的天数
     *
     * @param dateStr
     * @return
     */
    public static int calculateNumberOfDaysToToday(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        try {
            Date date = sdf.parse(dateStr);
            Date now = new Date();
            long n1 = now.getTime();
            long n2 = date.getTime();
            if (n2 >= n1) {
                long diffTime = n2 - n1;
                int numberOfDays = (int) (diffTime / (3600 * 1000 * 24));
                return numberOfDays;
            } else {
                return -1;
            }
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * 计算每个月的天数
     *
     * @param year 年份
     * @param month 月份
     * @return days 每个月的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        int days = 0;

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 9 || month == 10 || month == 12) {
            days = 31;
        } else if (month == 4 || month == 6 || month == 8 || month == 11) {
            days = 30;
        } else { // 2月份，闰年29天、平年28天
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * 计算一年中的天数
     *
     * @return
     */
    public static int calculateDays() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    /**
     * 计算两个时间相差的天数 输入时间格式: yyyy-MM-dd
     *
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static int getDayDiff(String start, String end) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startTime = df.parse(start);
            Date endTime = df.parse(end);
            long interval = endTime.getTime() - startTime.getTime();
            if (interval < 0) {
                throw new Exception("开始时间 start > 终止时间 end");
            }
            int day = (int) (interval / (24 * 60 * 60 * 1000));
            return day;
        } catch (ParseException e) {
            throw new Exception("时间格式应为 yyyy-MM-dd");
        }
    }

    /**
     * 计算两个时间相差的秒数 输入时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static int getSecondsDiff(String start, String end) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = df.parse(start);
            Date endTime = df.parse(end);
            long interval = endTime.getTime() - startTime.getTime();
            if (interval < 0) {
                throw new Exception("开始时间 start > 终止时间 end");
            }
            int minute = (int) (interval / (1000));
            return minute;
        } catch (ParseException e) {
            throw new Exception("时间格式应为 yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 计算两个时间相差的分钟数 输入时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static int getMinuteDiff(String start, String end) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = df.parse(start);
            Date endTime = df.parse(end);
            long interval = endTime.getTime() - startTime.getTime();
            if (interval < 0) {
                throw new Exception("开始时间 start > 终止时间 end");
            }
            int minute = (int) (interval / (60 * 1000));
            return minute;
        } catch (ParseException e) {
            throw new Exception("时间格式应为 yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 根据给定的格式取当前时间。
     * <p/>
     * 如果给定的格式为空，则使用默认格式：yyyy-MM-dd HH:mm:ss。
     *
     * @param pattern 指定格式
     * @return 字符串表示的当前时间
     */
    public static String getDateTime(String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dt = sdf.format(new Date());
        return dt;
    }

    /**
     * 获取当前时间前几天的时间
     *
     * @param offsetDay
     * @param strFormat
     * @return
     */
    public static String getDateBefore(int offsetDay, String strFormat) {
        String strDay = "";
        long nNow = getTimestamp();
        long mydate = nNow - (long) offsetDay * 24 * 3600 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        strDay = sdf.format(new Date(mydate));
        return strDay;
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getTimestamp() {
        Date dateNow = new Date();
        return dateNow.getTime();
    }

    /**
     * 字符串转date类型
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(date);
    }

    /**
     * 检测日期是否是预期格式
     *
     * @param strTime
     * @param pattern
     * @return
     */
    public static boolean checkDateFormat(String strTime, String pattern) {
        if (strTime.length() != pattern.trim().length())
            return false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern.trim());
            sdf.parse(strTime);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断同年同月生日
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 判断同一天生日
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameDate = cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * 
     * @param date 日期
     * @param parttern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 获取SimpleDateFormat
     * 
     * @param parttern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    public static void main(String[] args) {

    }
}
