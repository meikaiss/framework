package com.android.framework.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by meikai on 16/3/8.
 */
public class TimeUtil {


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
}
