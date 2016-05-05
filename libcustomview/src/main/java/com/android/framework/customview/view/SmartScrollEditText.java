package com.android.framework.customview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by meikai on 16/5/5.
 */
public class SmartScrollEditText extends EditText {

    private float originalY;

    public SmartScrollEditText(Context context) {
        super(context);
    }

    public SmartScrollEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartScrollEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SmartScrollEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            originalY = e.getY();
        } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            if(canScrollVertically((int)(e.getY() - originalY))) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }

        return super.onTouchEvent(e);
    }
}
