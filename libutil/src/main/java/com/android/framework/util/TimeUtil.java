package com.android.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by meikai on 16/3/8.
 */
public class TimeUtil {


    // 如果是同一年，则不显示年份，否则显示年份
    private final static Calendar toTime = Calendar.getInstance();
    private final static Calendar curTime = Calendar.getInstance();


    public static final String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 转换毫秒到具体时间, 小时:分钟:秒
     *
     * @param millis 毫秒
     * @return 时间字符串
     */
    public static String convertMillis2Time(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MICROSECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.HOURS.toSeconds(TimeUnit.MICROSECONDS.toHours(millis)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }


    /**
     * 将 秒数 转换成  a'b" 的格式 ,例如 75s 转换成 1'15"
     *
     * @param iSecond 秒数
     */
    public static String formatSecond(int iSecond) {

        if (iSecond < 60)
            return iSecond + "\"";
        else if (iSecond < 60 * 60) {
            return iSecond / 60 + "'" + formatSecond(iSecond % 60);
        } else {
            return iSecond / (60 * 60) + "°" + formatSecond(iSecond % (60 * 60));
        }
    }


    /**
     * 将 秒数 转换在 小时:分:秒 的格式
     *
     * @param durationSeconds
     * @return
     */
    public static String getDuration(int durationSeconds) {
        int hours = durationSeconds / (60 * 60);
        int leftSeconds = durationSeconds % (60 * 60);
        int minutes = leftSeconds / 60;
        int seconds = leftSeconds % 60;

        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(addZeroPrefix(hours));
        sBuffer.append(":");
        sBuffer.append(addZeroPrefix(minutes));
        sBuffer.append(":");
        sBuffer.append(addZeroPrefix(seconds));

        return sBuffer.toString();
    }

    public static String addZeroPrefix(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return "" + number;
        }

    }


    /**
     * 将 时间戳 转换成  XX前 , 如 2分钟前, 1 小时前
     * @param time
     * @return
     */
    public static String trimNotifyTime(long time) {
        return formatTime(time, true);
    }

    private static final String formatTime(long time, boolean showChinese) {
        final long ct = System.currentTimeMillis();
        final long dt = ct - time;
        if (dt < 60 * 1000) {
            return "1分钟前";
        } else if (dt < 60 * 60 * 1000) {
            return (dt / (60 * 1000)) + "分钟前";
        } else if (dt < 24 * 60 * 60 * 1000) {
            return (dt / (60 * 60 * 1000)) + "小时前";
        }/* else if (dt <= 4 * 24 * 60 * 60 * 1000) {
            return (dt / (24 * 60 * 60 * 1000)) + "天前";
        } */ else {
            if (time <= 0) {
                if (showChinese) {
                    return "很久之前";
                } else {
                    return "";
                }
            } else {

                toTime.setTimeInMillis(time);
                curTime.setTimeInMillis(ct);
                final int year = toTime.get(Calendar.YEAR);
                // 如果同年月日，则显示时间和分钟就可以了
                if (year == curTime.get(Calendar.YEAR)) {
                    if (showChinese) {
                        final int month = toTime.get(Calendar.MONTH);
                        final int day = toTime.get(Calendar.DAY_OF_MONTH);
                        return (month + 1) + "月" + day + "日";
                    } else {
                        return format(toTime.getTime(), "MM-dd HH:mm");
                    }
                } else {
                    if (showChinese) {
                        final int month = toTime.get(Calendar.MONTH);
                        final int day = toTime.get(Calendar.DAY_OF_MONTH);
                        return year + "年" + (month + 1) + "月" + day + "日";
                    } else {
                        return format(toTime.getTime(), "yyyy-MM-dd");
                    }
                }
            }
        }
    }
}
