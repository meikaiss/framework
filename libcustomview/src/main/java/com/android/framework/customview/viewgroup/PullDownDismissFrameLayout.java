package com.android.framework.customview.viewgroup;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.android.framework.util.DimenUtil;

/**
 * Created by meikai on 16/10/13.
 */
public class PullDownDismissFrameLayout extends FrameLayout {

    private static final int INVALID_POINTER = -1;

    /**
     * 松手后自动下滑的最大时间， 单位:ms
     */
    private static final int MAX_SETTLE_DURATION = 600;
    /**
     * 160dpi设备上 滑动速度超过此值，则认为是 fling； 乘以density则等于mMinimumVelocity
     */
    private static final int MIN_FLING_VELOCITY = 400;

    private int motionEventDownY = 0;
    /**
     * 有效的下拉距离，超过此距离后释放会自动继续下滑并关闭
     */
    private int effectivePullDownY = 0;
    private int screenYdpi = 0;

    private int interceptDownY;
    private int mActivePointerId = INVALID_POINTER;
    /**
     * 超过此速度则认为是fling
     */
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mTouchSlop;
    private boolean startDrag;
    /**
     * 标识 scroller 是否正处于 滚动状态
     */
    boolean isScrollerScrolling = false;

    /**
     * 下拉时背景渐变的插值器
     */
    private Interpolator bgColorInterpolator;
    private ViewGroup contentViewGroup;
    private DragListener dragListener;
    private Scroller scroller;
    private VelocityTracker velocityTracker;


    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }


    public PullDownDismissFrameLayout(Context context) {
        super(context);
        init(context);
    }

    public PullDownDismissFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullDownDismissFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullDownDismissFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        bgColorInterpolator = new LinearInterpolator();
        effectivePullDownY = DimenUtil.dp2px(getContext(), 100);

        scroller = new Scroller(getContext(), sInterpolator);

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        final float density = context.getResources().getDisplayMetrics().density;
        screenYdpi = context.getResources().getDisplayMetrics().heightPixels;

        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mMinimumVelocity = (int) (MIN_FLING_VELOCITY * density);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        contentViewGroup = (ViewGroup) getChildAt(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("PullDown Layout", "dispatchTouchEvent _ ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("PullDown Layout", "dispatchTouchEvent _ ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("PullDown Layout", "dispatchTouchEvent _ ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("PullDown Layout", "onInterceptTouchEvent _ ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("PullDown Layout", "onInterceptTouchEvent _ ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("PullDown Layout", "onInterceptTouchEvent _ ACTION_UP");
                break;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                interceptDownY = (int) ev.getY();
                startDrag = true;

                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                    velocityTracker.addMovement(ev);
                    mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                int diffY = (int) (ev.getY() - interceptDownY);

                if (diffY > mTouchSlop) {
                    // 正数表示 向下滑动
                    motionEventDownY = (int) ev.getY();
                    startDrag = true;
                    //超过拖阈值


                    return true;
                } else {
                    //未超过拖阈值
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 到这一步事件还没有拦截，则表示 没有触发 有效的 下拉关闭动作
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("PullDown Layout", "onTouchEvent _ ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("PullDown Layout", "onTouchEvent _ ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("PullDown Layout", "onTouchEvent _ ACTION_UP");
                break;
        }

        if (!startDrag) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                motionEventDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                velocityTracker.addMovement(event);

                int deltaY = (int) (event.getY() - motionEventDownY);

                if (deltaY < 0) {
                    // 禁止 向上移出 屏幕
                    return true;
                }
                contentViewGroup.setTranslationY(deltaY);

                if (dragListener != null) {
                    dragListener.onDrag(deltaY);
                }

                float factor = Math.abs(deltaY) / (float) screenYdpi;
                factor = (float) Math.min(factor, 1.0);
                factor = (float) Math.max(factor, 0.0);
                factor = bgColorInterpolator.getInterpolation(factor);
                this.setBackgroundColor(blendColors(Color.BLACK, Color.TRANSPARENT, factor));

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int deltaY2 = Math.abs((int) (event.getY() - motionEventDownY));

                startDrag = false;

                if (velocityTracker == null) {
                    break;
                }

                velocityTracker.addMovement(event);

                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, mActivePointerId);

                if (deltaY2 >= effectivePullDownY) {

                    smoothScrollToFinish((int) (screenYdpi - contentViewGroup.getTranslationY()), initialVelocity);
                } else {
                    if (initialVelocity > mMinimumVelocity) {
                        // 当距离没有达到有效下拉关闭距离，但是下拉的速度超过fling速度值， 也需要自动滑动关闭
                        smoothScrollToFinish((int) (screenYdpi - contentViewGroup.getTranslationY()), initialVelocity);
                    } else {
                        ValueAnimator animator = new ValueAnimator();
                        animator.setDuration(200);
                        animator.setInterpolator(new AccelerateInterpolator());
                        animator.setFloatValues(contentViewGroup.getTranslationY(), 0.0f);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                contentViewGroup.setTranslationY((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        animator.start();
                    }
                }

                if (velocityTracker != null) {
                    velocityTracker.clear();
                }
                break;
            default:
                break;
        }

        return true;
    }


    /**
     * 沿Y轴以指定速度 滑动指定位移
     *
     * @param deltaY   Y轴滑动的位移
     * @param velocity 滑动速度
     */
    private void smoothScrollToFinish(int deltaY, int velocity) {

        velocity = Math.abs(velocity);
        int duration = 200;
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs((float) deltaY / velocity));
        }
        duration = Math.min(duration, MAX_SETTLE_DURATION);

        scroller.startScroll(0, (int) contentViewGroup.getTranslationY(), 0, deltaY, duration);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (!scroller.isFinished() && scroller.computeScrollOffset()) {
            int y = scroller.getCurrY();
            contentViewGroup.setTranslationY(y);

            float factor = Math.abs(contentViewGroup.getTranslationY()) / (float) screenYdpi;
            factor = (float) Math.min(factor, 1.0);
            factor = (float) Math.max(factor, 0.0);
            factor = bgColorInterpolator.getInterpolation(factor);
            this.setBackgroundColor(blendColors(Color.BLACK, Color.TRANSPARENT, factor));

            ViewCompat.postInvalidateOnAnimation(this);

            isScrollerScrolling = true;
        } else {
            if (isScrollerScrolling) {
                if (dragListener != null) {
                    dragListener.onDragFinish();
                }
            }
            isScrollerScrolling = false;
        }
    }

    public interface DragListener {
        void onDrag(int deltaY);

        void onDragFinish();
    }

    /**
     * 混合两个颜色，ratio 为混合因子（混合因子为距离color1的逻辑距离）
     *
     * @param ratio 0.0 返回 color1, 0.5 返回均匀混合色, 1.0 返回 color2
     */
    public static
    @ColorInt
    int blendColors(@ColorInt int color1,
                    @ColorInt int color2,
                    @FloatRange(from = 0f, to = 1f) float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

}

