package com.android.framework.customview.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.framework.util.DensityUtil;
import com.android.framework.util.LogUtil;

/**
 * Created by meikai on 2018/02/05.
 */
public class CameraShootButton extends View {


    //#####  默认的参数值  #####
    //停止状态时的外圆颜色
    private static final int outerCircleColorStop = 0xB3FFFFFF;
    //停止状态时的内部圆角矩形的填充色
    private static final int innerRectColorStop = 0xFFFFFFFF;

    //拍摄状态时的外圆颜色
    private static final int outerCircleColorShoot = 0x88ff0000;
    //拍摄状态时的内部圆角矩形的填充色
    private static final int innerRectColorShoot = 0xffff0000;


    //#####  通用绘制参数  #####
    private Paint paint;
    private int circleX;
    private int circleY;
    // 标识当前是否正在拍摄
    private boolean isShooting;
    private RectF innerRectF = new RectF();

    //外圆半径
    private int outerCircleRadius;
    //外圆厚度
    private int outerCircleWidth;
    //外圆颜色
    private int outerCircleColor = outerCircleColorStop;
    //内部圆角矩形的宽度
    private int innerRectWidth;
    //内部圆角矩形的填充色
    private int innerRectColor = innerRectColorStop;
    //内部圆角矩形的圆角半径
    private int innerRectRadius;


    private AnimatorSet animatorSetStart;
    private ValueAnimator valueAnimator1Start;
    private ValueAnimator valueAnimator2Start;

    private ValueAnimator valueAnimator1End;
    private AnimatorSet animatorSetEnd;

    public boolean isShooting() {
        return isShooting;
    }

    public CameraShootButton(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CameraShootButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CameraShootButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);

        outerCircleRadius = dp2px(40);
        outerCircleWidth = dp2px(6);

        innerRectWidth = dp2px(32) << 1;
        innerRectRadius = dp2px(32);

        setOnClickListener(onClickListener);
    }


    private int dp2px(int dpValue) {
        return DensityUtil.dp2px(getContext(), dpValue);
    }

    private void log(String msg) {
        LogUtil.e(msg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        circleX = getMeasuredWidth() / 2;
        circleY = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        onDrawStateStop(canvas);
    }

    //绘制 静止 状态的界面
    private void onDrawStateStop(Canvas canvas) {
        // 外圆
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(outerCircleWidth);
        paint.setColor(outerCircleColor);
        canvas.drawCircle(circleX, circleY, outerCircleRadius - outerCircleWidth / 2, paint);

        //内部中心的圆角矩形
        int left = circleX - innerRectWidth / 2;
        int right = circleX + innerRectWidth / 2;
        int top = circleY - innerRectWidth / 2;
        int bottom = circleY + innerRectWidth / 2;

        innerRectF.set(left, top, right, bottom);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(innerRectColor);
        canvas.drawRoundRect(innerRectF, innerRectRadius, innerRectRadius, paint);

    }

    private void startShoot() {
        if (animatorSetEnd != null) {
            animatorSetEnd.cancel();
        }

        if (valueAnimator1Start == null) {
            valueAnimator1Start = ValueAnimator.ofFloat(0f, 1f);
            valueAnimator1Start.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float frac = animation.getAnimatedFraction();

                    log("valueAnimator1Start, onAnimationUpdate, frac=" + frac);

                    outerCircleRadius = (int) (dp2px(40) + (dp2px(60) - dp2px(40)) * frac + 0.5f);
//                    outerCircleWidth = (int) (dp2px(6) + (dp2px(12) - dp2px(6)) * frac + 0.5f);
                    innerRectWidth = (int) (dp2px(32 << 1) + (dp2px(21 << 1) - dp2px(32 << 1)) * frac + 0.5f);
                    innerRectRadius = (int) (dp2px(32) + (dp2px(6) - dp2px(32)) * frac + 0.5f);

                    outerCircleColor = getCurrentColor(frac, outerCircleColorStop, outerCircleColorShoot);
                    innerRectColor = getCurrentColor(frac, innerRectColorStop, innerRectColorShoot);
                    invalidate();
                }
            });
            valueAnimator1Start.setDuration(300);
        }

        if (valueAnimator2Start == null) {
            valueAnimator2Start = ValueAnimator.ofFloat(0f, 1f);
            valueAnimator2Start.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float frac = animation.getAnimatedFraction();

                    log("valueAnimator2Start, onAnimationUpdate, frac=" + frac);

                    outerCircleWidth = (int) (dp2px(6) + (dp2px(12) - dp2px(6)) * frac + 0.5f);
                    invalidate();
                }
            });
            valueAnimator2Start.setDuration(800);
            valueAnimator2Start.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator2Start.setRepeatCount(ValueAnimator.INFINITE);
        }

        animatorSetStart = new AnimatorSet();
        animatorSetStart.play(valueAnimator1Start).before(valueAnimator2Start);
        animatorSetStart.start();

    }

    private void stopShoot() {
        if (animatorSetStart != null) {
            animatorSetStart.cancel();
        }

        final int finalOuterCircleRadius = outerCircleRadius;
        final int finalOuterCircleWidth = outerCircleWidth;
        final int finalInnerRectWidth = innerRectWidth;
        final int finalInnerRectRadius = innerRectRadius;

        valueAnimator1End = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator1End.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float frac = animation.getAnimatedFraction();

                log("valueAnimator1End, onAnimationUpdate, frac=" + frac);

                outerCircleRadius = (int) (finalOuterCircleRadius + (dp2px(40) - finalOuterCircleRadius) * frac + 0.5f);
                outerCircleWidth = (int) (finalOuterCircleWidth + (dp2px(6) - finalOuterCircleWidth) * frac + 0.5f);
                innerRectWidth = (int) (finalInnerRectWidth + (dp2px(32 << 1) - finalInnerRectWidth) * frac + 0.5f);
                innerRectRadius = (int) (finalInnerRectRadius + (dp2px(32) - finalInnerRectRadius) * frac + 0.5f);

                outerCircleColor = getCurrentColor(frac, outerCircleColorShoot, outerCircleColorStop);
                innerRectColor = getCurrentColor(frac, innerRectColorShoot, innerRectColorStop);
                invalidate();
            }
        });
        valueAnimator1End.setDuration(300);

        animatorSetEnd = new AnimatorSet();
        animatorSetEnd.playSequentially(valueAnimator1End);
        animatorSetEnd.start();

    }

    private View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isShooting) {
                isShooting = false;
                stopShoot();
            } else {
                isShooting = true;
                startShoot();
            }
        }
    };

    /**
     * 根据fraction值来计算当前的颜色。
     */
    private int getCurrentColor(float fraction, int startColor, int endColor) {
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + fraction * redDifference);
        blueCurrent = (int) (blueStart + fraction * blueDifference);
        greenCurrent = (int) (greenStart + fraction * greenDifference);
        alphaCurrent = (int) (alphaStart + fraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }

}
