package com.android.framework.util;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by meikai on 16/4/21.
 */
public class ViewHolderUtil {

    public ViewHolderUtil() {

    }

    public static <T extends View> T get(View view, int id) {
        SparseArray viewHolder = (SparseArray)view.getTag();
        if(viewHolder == null) {
            viewHolder = new SparseArray();
            view.setTag(viewHolder);
        }

        View childView = (View)viewHolder.get(id);
        if(childView == null) {

            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }

        return (T)childView;
    }
}
