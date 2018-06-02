package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by meikai on 16/5/9.
 */

public class NestGridView2 extends GridView {

    public NestGridView2(Context context) {
        super(context);
    }

    public NestGridView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestGridView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}