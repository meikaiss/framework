package com.android.framework.demo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.framework.demo.R;

/**
 * Created by meikai on 15/12/16.
 */
public class DependentBehavior extends CoordinatorLayout.Behavior<TextView> {

    public DependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency.getId()== R.id.depentent;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child, offset);
        return true;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, TextView child,
                                  int parentWidthMeasureSpec, int widthUsed,
                                  int parentHeightMeasureSpec, int heightUsed) {

        Log.e("onMeasureChild", "widthUsed="+widthUsed+",heightUsed="+heightUsed);
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, TextView child, int layoutDirection) {
        Log.e("onLayoutChild", "layoutDirection="+layoutDirection);
        return super.onLayoutChild(parent, child, layoutDirection);
    }
}