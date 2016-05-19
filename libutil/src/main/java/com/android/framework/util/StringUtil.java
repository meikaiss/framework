package com.android.framework.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by meikai on 15/10/13.
 */
public final class StringUtil {

    private StringUtil() {
        throw new RuntimeException("can't init util class");
    }

    public static boolean isEmpty(String source) {
        return source == null || source.isEmpty();
    }

    public static boolean isNotEmpty(String source) {
        return !isEmpty(source);
    }

    public static boolean isEmptyOrLiterallyNull(String s) {
        return isEmpty(s) || "null".equals(s);
    }


    // 获取通知的ID, 防止重复, 可以用于通知的ID
    public static class NotificationID {
        // 随机生成一个数
        private final static AtomicInteger c = new AtomicInteger(0);

        // 获取一个不重复的数, 从0开始
        public static int getID() {
            return c.incrementAndGet();
        }
    }


}
