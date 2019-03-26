package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * 高度自适应 ViewPagerChild 的高度
 * Created by meikai on 2019/03/26.
 */
public class WrapHeightViewPager extends ViewPager {

    private List<Integer> childViewHeightList = new ArrayList<>();

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (getChildCount() == 0 || position + 1 >= getChildCount()) {
                return;
            }

            ViewGroup leftChildViewGroup = (ViewGroup) getChildAt(position);
            ViewGroup RightChildViewGroup = (ViewGroup) getChildAt(position + 1);

            int leftHeight = leftChildViewGroup.getChildAt(0).getHeight();
            int rightHeight = RightChildViewGroup.getChildAt(0).getHeight();

            leftHeight = childViewHeightList.get(position);
            rightHeight = childViewHeightList.get(position+1);

            int diff = rightHeight - leftHeight;
            int viewPagerHeight = (int) ((leftHeight + diff * positionOffset) + 0.5f);

            getLayoutParams().height = viewPagerHeight;
            setLayoutParams(getLayoutParams());
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public WrapHeightViewPager(@NonNull Context context) {
        this(context, null);
    }

    public WrapHeightViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void initOnGlobalListener() {

        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (getChildCount() > 0) {
                    childViewHeightList.clear();
                    for (int i = 0; i < getChildCount(); i++) {
                        int childHeight = ((ViewGroup) (getChildAt(i))).getChildAt(0).getHeight();
                        childViewHeightList.add(childHeight);
                    }

                    int firstChildHeight = ((ViewGroup) (getChildAt(0))).getChildAt(0).getHeight();
                    if (firstChildHeight > 0) {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        getLayoutParams().height = firstChildHeight;
                        setLayoutParams(getLayoutParams());
                        initScrollListener();
                    }
                }

            }
        };

        this.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        this.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

    }

    public void initScrollListener() {
        if (isInEditMode()) {
            return;
        }

        this.removeOnPageChangeListener(onPageChangeListener);
        this.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode() || getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 每次滑动修改ViewPager的高度后，会相应影响每一个child的高度，所以这里需要重新计算child的高度
        try {
            for (int i = 0; i < getChildCount(); i++) {
                if (childViewHeightList.get(i) > 0) {
                    ((ViewGroup) getChildAt(i)).getChildAt(0).getLayoutParams().height =
                            childViewHeightList.get(i);
                    ((ViewGroup) getChildAt(i)).getChildAt(0)
                            .setLayoutParams(((ViewGroup) getChildAt(i)).getChildAt(0).getLayoutParams());
                }
            }
        } catch (Exception e) {

        }

    }


}
