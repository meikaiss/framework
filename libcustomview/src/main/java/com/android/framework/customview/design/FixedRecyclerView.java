package com.android.framework.customview.design;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 当垂直方向不能再滑动时,此RecyclerView不会 请求取消父布局的拦截,
 * 即会调用 requestDisallowInterceptTouchEvent(false);
 * 执行完上一句之后,此FixedRecyclerView的父布局就可以拦截事件，比如SwipeRefreshLayout下拉刷新
 * Created by meikai on 16/4/9.
 */
public class FixedRecyclerView extends RecyclerView {

    public FixedRecyclerView(Context context) {
        super(context);
    }

    public FixedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canScrollVertically(int direction) {

        // check if scrolling up
        if (direction < 1) {
            boolean original = super.canScrollVertically(direction);
            return !original && getChildAt(0) != null && getChildAt(0).getTop() < 0 || original;
        }
        return super.canScrollVertically(direction);

    }
}

