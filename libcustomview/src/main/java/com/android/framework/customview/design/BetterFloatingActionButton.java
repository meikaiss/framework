package com.android.framework.customview.design;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 * Created by meikai on 16/4/9.
 */
public class BetterFloatingActionButton extends FloatingActionButton {

    private boolean forceHide = false;

    public BetterFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BetterFloatingActionButton(Context context) {
        super(context);
    }

    public boolean isForceHide() {
        return forceHide;
    }

    public void setForceHide(boolean forceHide) {
        this.forceHide = forceHide;
        if (!forceHide) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    //if hide，disable animation
    public boolean canAnimation() {
        return !isForceHide();
    }
}

