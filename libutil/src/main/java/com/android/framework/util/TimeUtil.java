package com.android.framework.util;

/**
 * Created by meikai on 16/3/8.
 */
public class TimeUtil {


    /**
     * 将 秒数 转换成  a'b" 的格式 ,例如 75s 转换成 1'15"
     *
     * @param iSecond 秒数
     */
    public static String formatSecond(int iSecond) {

        if(iSecond < 60)
            return iSecond + "\"";
        else if (iSecond < 60 * 60) {
            return iSecond / 60 + "'" + formatSecond(iSecond % 60);
        } else {
            return iSecond / (60 * 60) + "°" + formatSecond(iSecond % (60 * 60));
        }
    }


    /**
     * 将 秒数 转换在 小时:分:秒 的格式
     * @param durationSeconds
     * @return
     */
    public static String getDuration(int durationSeconds){
        int hours = durationSeconds /(60*60);
        int leftSeconds = durationSeconds % (60*60);
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

    public static String addZeroPrefix(int number){
        if(number < 10){
            return "0"+number;
        }else{
            return ""+number;
        }

    }
}
