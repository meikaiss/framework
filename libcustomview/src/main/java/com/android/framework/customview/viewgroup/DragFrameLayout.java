package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.android.framework.customview.R;

/**
 * Created by meikai on 17/6/6.
 */
public class DragFrameLayout extends FrameLayout {

    private ViewDragHelper viewDragHelper;

    public DragFrameLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallBack());

        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child.getId() == R.id.drag_view_child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int getViewHorizontalDragRange(View child)
        {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child)
        {
            return 1;
        }
    }

}
