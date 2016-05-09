package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by meikai on 16/5/9.
 */
public class NestGridView extends GridView {


    float downX;

    public NestGridView(Context context) {
        super(context);
    }

    public NestGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

        setMeasuredDimension(getMeasuredWidth(), 450);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("onTouchEvent", "ACTION_DOWN");

                downX = e.getX();
                getParent().requestDisallowInterceptTouchEvent(true);

                break;
            case MotionEvent.ACTION_MOVE:

                if (canScrollVertically((int) (e.getY() - downX))) {
                    Log.e("canScrollVertically", "true");
                } else {
                    Log.e("canScrollVertically", "false");
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("onTouchEvent", "ACTION_UP");

                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }

        return super.onTouchEvent(e);
    }

}