package com.android.framework.util;

/**
 * Created by meikai on 16/3/8.
 */
public class TimeUtil {

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
