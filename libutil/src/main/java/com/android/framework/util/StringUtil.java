package com.android.framework.util;

/**
 * Created by meikai on 15/10/13.
 */
public final class StringUtil {

    private StringUtil(){
        throw new RuntimeException("can't init util class");
    }

    public static boolean isEmpty(String source){
        return source == null || source.isEmpty();
    }

    public static boolean isNotEmpty(String source){
        return !isEmpty(source);
    }

    public static boolean isEmptyOrLiterallyNull(String s){
        return isEmpty(s) || "null".equals(s);
    }

}
