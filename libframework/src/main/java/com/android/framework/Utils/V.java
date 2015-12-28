package com.android.framework.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by meikai on 15/12/28.
 */
public final class V {

    public static <T extends View> T f(Activity activity, @IdRes int viewId) {
        return (T) activity.findViewById(viewId);
    }

    public static <T extends View> T f(View view, @IdRes int viewId) {
        return (T) view.findViewById(viewId);
    }

}
