package com.android.framework.customview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by meikai on 16/10/31.
 */
public class FloatDragImageView extends ImageView {

    private static long DURATION_AUTO_BACK_ANIMATOR = 250;

    /**
     * 按下后的 alpha 值
     */
    private float alphaPress;
    private float scalePress;

    private float eventDownX;
    private float eventDownY;

    private float currentTranslationX;
    private float currentTranslationY;

    private int parentWidth;
    private int parentHeight;

    private ValueAnimator autoBackAnimator;

    private boolean userMoveAction;

    private int dragPaddingTop, dragPaddingBottom;
    private int touchSlop;
    private OnEventStaticsListener eventStaticsListener;

    public void setOnEventStaticsListener(OnEventStaticsListener eventStaticsListener) {
        this.eventStaticsListener = eventStaticsListener;
    }

    public FloatDragImageView(Context context) {
        this(context, null);
    }

    public FloatDragImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatDragImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FloatDragImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        alphaPress = 0.5f;
        scalePress = 1.1f;
        dragPaddingTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, Resources.getSystem()
                .getDisplayMetrics());
        dragPaddingBottom = 0;

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FloatDragImageView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                FloatDragImageView.this.resetPosition();
            }
        });
    }

    public void resetPosition() {
        if (parentWidth == 0 && parentHeight == 0) {
            ViewGroup parent = (ViewGroup) getParent();
            parentWidth = parent.getMeasuredWidth();
            parentHeight = parent.getMeasuredHeight();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (parentWidth <= 0 && parentHeight <= 0) {
                    ViewGroup parent = (ViewGroup) getParent();
                    parentWidth = parent.getMeasuredWidth();
                    parentHeight = parent.getMeasuredHeight();
                }

                eventDownX = event.getRawX();
                eventDownY = event.getRawY();

                currentTranslationX = this.getTranslationX();
                currentTranslationY = this.getTranslationY();

                this.setScaleX(scalePress);
                this.setScaleY(scalePress);

                this.setAlpha(alphaPress);

                userMoveAction = false;

                break;
            case MotionEvent.ACTION_MOVE:

                float newTranslationX, newTranslationY;

                newTranslationX = this.currentTranslationX + event.getRawX() - eventDownX;
                newTranslationY = this.currentTranslationY + event.getRawY() - eventDownY;

                newTranslationX = Math.max(newTranslationX, 0);
                newTranslationX = Math.min(newTranslationX, parentWidth - this.getMeasuredWidth());
                newTranslationY = Math.max(newTranslationY, dragPaddingTop);
                newTranslationY = Math.min(newTranslationY,
                        parentHeight - this.getMeasuredHeight() - dragPaddingBottom);

                this.setTranslationX(newTranslationX);
                this.setTranslationY(newTranslationY);

                userMoveAction = userMoveAction
                        || (Math.hypot(event.getRawX() - eventDownX, event.getRawY() - eventDownY) > touchSlop);
                break;
            case MotionEvent.ACTION_UP:

                if (this.getTranslationX() <= (parentWidth - this.getMeasuredWidth()) / 2) {
                    playAnimator(0);
                } else {
                    playAnimator(parentWidth - this.getMeasuredWidth());
                }

                if (userMoveAction) {
                    // 触发了一次拖动操作
                    if (eventStaticsListener != null) {
                        eventStaticsListener.onDragFinish();
                    }
                } else {
                    // 没有触发拖动，则认为是 点击
                    this.performClick();
                    if (eventStaticsListener != null) {
                        eventStaticsListener.onSearchClick();
                    }
                }

                break;
            default:
                break;
        }

        return true;
    }

    private void playAnimator(int destination) {
        autoBackAnimator = new ValueAnimator();
        autoBackAnimator.setDuration(DURATION_AUTO_BACK_ANIMATOR);
        autoBackAnimator.setFloatValues(this.getTranslationX(), destination);

        autoBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                FloatDragImageView.this.setTranslationX((Float) animation.getAnimatedValue());

            }
        });

        autoBackAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        FloatDragImageView.this.setScaleX(1.0f);
        FloatDragImageView.this.setScaleY(1.0f);
        FloatDragImageView.this.setAlpha(1.0f);

        autoBackAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (autoBackAnimator != null && autoBackAnimator.isRunning()) {
            autoBackAnimator.cancel();
        }
    }

    /**
     * 业务功能 事件统计接口
     */
    public interface OnEventStaticsListener {
        /**
         * 触发了一次拖动
         */
        void onDragFinish();

        /**
         * 触发了一次点击
         */
        void onSearchClick();
    }

}
