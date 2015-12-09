package com.android.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by meikai on 15/12/8.
 */
public class InputLinearLayout extends LinearLayout {

    IOnKeyboardStateChangedListener iOnKeyboardStateChangedListener;

    public void setiOnKeyboardStateChangedListener(IOnKeyboardStateChangedListener iOnKeyboardStateChangedListener) {
        this.iOnKeyboardStateChangedListener = iOnKeyboardStateChangedListener;
    }

    public InputLinearLayout(Context context) {
        super(context);
    }

    public InputLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InputLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("InputLinearLayout", "onSizeChanged, w=" + w + ", h=" + h + ", oldw=" + oldw + ",oldh+" + oldh);

        if (h != oldh && iOnKeyboardStateChangedListener != null) {
            iOnKeyboardStateChangedListener.onKeyboardStateChanged(h > oldh);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("InputLinearLayout", "onLayout, l=" + l + ", t=" + t + ", r=" + r + ",b+" + b);
    }


    public interface IOnKeyboardStateChangedListener {
        void onKeyboardStateChanged(boolean state);
    }
}
