package com.android.framework.viewgroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by meikai on 15/12/6.
 */
public class SlideViewPager extends ViewGroup {

    /**
     * 手指Action_Up后当前item自动滑动到正确位置的毫秒数
     */
    private static final int PAGE_CHANGE_DURATION = 600;
    /**
     * 手指Action_Up后滑动速度超过此值，则认为是有效翻页
     */
    public static int X_VELOCITY_THRESHOLD = 600;

    /**
     * 左右相邻item 显示在当前屏的宽度
     */
    private int mSlideDimension;
    private int mGutterSize;

    private float mDownEventX;


    private int mSwitchSize = 0;
    private int mCurrentPosition = 0;

    private int mMaximumVelocity;

    private ScrollState mScrollState;
    private ScrollerCompat mScrollerCompat;
    private VelocityTracker mVelocityTracker;

    private OnPagerChangeListener onPagerChangeListener;

    private void setScrollState(ScrollState state) {
        mScrollState = state;
        if (onPagerChangeListener != null){
            onPagerChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void setOnPagerChangeListener(OnPagerChangeListener onPagerChangeListener) {
        this.onPagerChangeListener = onPagerChangeListener;
    }

    private final Runnable mEndScrollRunnable = new Runnable() {
        public void run() {
            mScrollState = ScrollState.SCROLL_STATE_IDLE;
        }
    };

    public SlideViewPager(Context context) {
        this(context, null);
    }

    public SlideViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlideViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mScrollerCompat = ScrollerCompat.create(context, sInterpolator);


        mSlideDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
        mGutterSize = 20;

        float density = context.getResources().getDisplayMetrics().density;

        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int childWidth = getMeasuredWidth() - mSlideDimension * 2;
        int childHeight = getMeasuredHeight();

        // 创建 子View的 测量 格式
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        mSwitchSize = childWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int left = mSlideDimension + child.getMeasuredWidth() * i;
            int right = left + child.getMeasuredWidth();
            int bottom = child.getMeasuredHeight();

            child.layout(left, 0, right, bottom);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownEventX = event.getRawX();
                if (mScrollerCompat != null && !mScrollerCompat.isFinished())
                    mScrollerCompat.abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:

                float moveDistence = -(event.getRawX() - mDownEventX);
                mDownEventX = event.getRawX();

                scrollBy((int) moveDistence, 0);

                setScrollState(ScrollState.SCROLL_STATE_DRAGGING);

                break;
            case MotionEvent.ACTION_UP:
                VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);

                float xVelocity = velocityTracker.getXVelocity();

                if (xVelocity > X_VELOCITY_THRESHOLD && mCurrentPosition > 0) {
                    smoothScrollToItemView(mCurrentPosition - 1);
                } else if (xVelocity < -X_VELOCITY_THRESHOLD && mCurrentPosition < getChildCount() - 1) {
                    smoothScrollToItemView(mCurrentPosition + 1);
                } else {
                    smoothScrollToDes();
                }

                setScrollState(ScrollState.SCROLL_STATE_SETTLING);

                break;

        }

        return true;
    }

    @Override
    public void computeScroll() {

        if (!mScrollerCompat.isFinished() && mScrollerCompat.computeScrollOffset()) {

            scrollTo(mScrollerCompat.getCurrX(), 0);

        }

        if(mScrollState == ScrollState.SCROLL_STATE_SETTLING){
            setScrollState(ScrollState.SCROLL_STATE_IDLE);
        }
    }

    private void smoothScrollToDes() {
        int scrollX = getScrollX();
        int position = (scrollX + mSwitchSize / 2) / mSwitchSize;

        if (position >= getChildCount())
            position = getChildCount() - 1;
        Log.e("", "smoothScrollToDes, position=" + position);
        smoothScrollToItemView(position);
    }

    private void smoothScrollToItemView(int position) {
        mCurrentPosition = position;

        int dx = position * (getMeasuredWidth() - mSlideDimension * 2) - getScrollX();
        Log.e("", "smoothScrollToItemView, dx=" + dx);
        mScrollerCompat.startScroll(getScrollX(), 0, dx, 0, PAGE_CHANGE_DURATION);

        invalidate();

        if(onPagerChangeListener != null){
            onPagerChangeListener.onPageSelected(position);
        }
    }


    public enum ScrollState {
        /**
         * 空闲状态
         */
        SCROLL_STATE_IDLE,
        /**
         * 正在被用户拖拽
         */
        SCROLL_STATE_DRAGGING,
        /**
         * 正在自动滑动正确位置的过程中
         */
        SCROLL_STATE_SETTLING
    }

    public interface OnPagerChangeListener {
        void onPageSelected(int position);

        void onPageScrollStateChanged(ScrollState scrollState);
    }


}
