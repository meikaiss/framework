package com.android.framework.customview.viewgroup;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zhy on 15/6/3.
 */
public class VDHLayout extends LinearLayout {

    private ViewDragHelper dragHelper;
    private View firstChild;
    private Point initOriginPosition = new Point();

    private OnDragFinishListener onDragFinishListener;

    public void setOnDragFinishListener(OnDragFinishListener onDragFinishListener) {
        this.onDragFinishListener = onDragFinishListener;
    }

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == firstChild;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.e("VDHLayout", "top=" + top + ", child.y=" + child.getY());

                if (top > initOriginPosition.y) {
                    // 不允许向下拉
                    return initOriginPosition.y;
                }
                return top;
            }


            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.e("VDHLayout", "onViewReleased, y=" + releasedChild.getY());

                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == firstChild) {

                    if (releasedChild.getY() > initOriginPosition.y - firstChild.getMeasuredHeight() / 2) {
                        // 说明滚动 没有超过 一半的高度，则自动回弹
                        dragHelper.settleCapturedViewAt(initOriginPosition.x, initOriginPosition.y);
                        invalidate();
                    } else {
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(releasedChild, "translationY", 0,
                                -firstChild.getMeasuredHeight() / 2);
                        objectAnimator.setDuration(300);
                        objectAnimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (onDragFinishListener != null) {
                                    onDragFinishListener.onDragFinish();
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        objectAnimator.start();
                    }

                }
            }

        });
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        initOriginPosition.x = firstChild.getLeft();
        initOriginPosition.y = firstChild.getTop();

        Log.e("VDHLayout", "left=" + initOriginPosition.x + ", top=" + initOriginPosition.y);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        firstChild = getChildAt(0);
    }

    public interface OnDragFinishListener {

        void onDragFinish();

    }
}
