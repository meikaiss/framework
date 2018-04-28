package com.android.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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


    /**
     * 通过view获取activity
     * @param view
     * @return
     */
    public static Activity getActivity(View view) {
        if (view == null) {
            return null;
        }
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
