package com.android.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by meikai on 16/9/9.
 */
public class CollectionUtil {


    public static <T> boolean isNotEmpty(Collection<T> list) {
        return !isEmpty(list);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <T> List<T> copy(List<T> list) {
        ArrayList copy = new ArrayList();
        if (isEmpty((Collection) list)) {
            return copy;
        } else {
            Iterator i$ = list.iterator();

            while (i$.hasNext()) {
                Object t = i$.next();
                copy.add(t);
            }

            return copy;
        }
    }
}
