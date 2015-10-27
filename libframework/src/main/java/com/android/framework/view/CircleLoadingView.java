package com.android.framework.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.android.framework.R;

/**
 * Created by meikai on 15/10/23.
 */
public class CircleLoadingView extends View {

    // 默认 环绕半径
    private static float DEFAULT_SURROUND_RADIUS = 40;
    // 默认 四个小圆的半径
    private static float DEFAULT_SMAAL_RADIUS = 40;

    private static int DEFAULT_ANIMATOR_DURATION = 2000;

    private int surroundType = 0;


    // 画笔
    private Paint mPaint;
    // 屏幕像素密度等级
    private float density = 1.0f;
    // 环绕半径
    private float surroundRadius = 20 * density;
    // 四个小圆的半径
    private float smallRadius = 20 * density;
    // 角度 因子
    private int angleAnimatorValue = 0;
    // 弧度 因子
    private double radianAnimatorValue = 0;
    // 椭圆环绕是 X方向位移量
    private float ovalFactorX;
    // 椭圆环绕是 Y方向位移量
    private float ovalFactorY;
    private int circleColor1;
    private int circleColor2;
    private int circleColor3;
    private int circleColor4;

    private ValueAnimator valueAnimator;
    private long animatorDuration;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        density = getResources().getDisplayMetrics().density;

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleLoadingView, defStyleAttr, 0);
        surroundRadius = a.getDimension(R.styleable.CircleLoadingView_surroundRadius, DEFAULT_SURROUND_RADIUS);
        smallRadius = a.getDimension(R.styleable.CircleLoadingView_smallRadius, DEFAULT_SMAAL_RADIUS);
        surroundType = a.getInt(R.styleable.CircleLoadingView_surround_type, 0);
        animatorDuration = a.getInt(R.styleable.CircleLoadingView_surround_duration, DEFAULT_ANIMATOR_DURATION);
        ovalFactorX = a.getDimension(R.styleable.CircleLoadingView_ovalFactorX, 0);
        ovalFactorY = a.getDimension(R.styleable.CircleLoadingView_ovalFactorY, 0);
        circleColor1 = a.getColor(R.styleable.CircleLoadingView_circleColor1, Color.RED);
        circleColor2 = a.getColor(R.styleable.CircleLoadingView_circleColor2, Color.GREEN);
        circleColor3 = a.getColor(R.styleable.CircleLoadingView_circleColor3, Color.MAGENTA);
        circleColor4 = a.getColor(R.styleable.CircleLoadingView_circleColor4, Color.DKGRAY);
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(Integer.MAX_VALUE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(animatorDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angleAnimatorValue = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(surroundType == 0){
            onDrawTypeCircle(canvas, surroundRadius);
        }else if(surroundType == 1){
            onDrawTypeOval(canvas, surroundRadius);
        }

    }

    /**  圆形轨迹环绕 */
    private void onDrawTypeCircle(Canvas canvas, float bigRadius) {

        float cx = getWidth() / 2;
        float cy = getHeight() / 2;

        radianAnimatorValue = Math.PI * angleAnimatorValue / 180;
        mPaint.setColor(circleColor1);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadius, cy + (float) Math.sin(radianAnimatorValue) * bigRadius, smallRadius, mPaint);

        radianAnimatorValue += Math.PI / 2;
        mPaint.setColor(circleColor2);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadius, cy + (float) Math.sin(radianAnimatorValue) * bigRadius, smallRadius, mPaint);

        radianAnimatorValue += Math.PI;
        mPaint.setColor(circleColor3);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadius, cy + (float) Math.sin(radianAnimatorValue) * bigRadius, smallRadius, mPaint);

        radianAnimatorValue += Math.PI * 3 / 2;
        mPaint.setColor(circleColor4);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadius, cy + (float) Math.sin(radianAnimatorValue) * bigRadius, smallRadius, mPaint);

    }

    /**  椭圆轨迹环绕 */
    private void onDrawTypeOval(Canvas canvas, float bigRadius) {

        float cx = getWidth() / 2;
        float cy = getHeight() / 2;

        float bigRadiusX = bigRadius + ovalFactorX;
        float bigRadiusY = bigRadius + ovalFactorY;

        radianAnimatorValue = Math.PI * angleAnimatorValue / 180;
        mPaint.setColor(circleColor1);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadiusX, cy + (float) Math.sin(radianAnimatorValue) * bigRadiusY, smallRadius, mPaint);

        radianAnimatorValue += Math.PI / 2;
        mPaint.setColor(circleColor2);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadiusX, cy + (float) Math.sin(radianAnimatorValue) * bigRadiusY, smallRadius, mPaint);

        radianAnimatorValue += Math.PI;
        mPaint.setColor(circleColor3);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadiusX, cy + (float) Math.sin(radianAnimatorValue) * bigRadiusY, smallRadius, mPaint);

        radianAnimatorValue += Math.PI * 3 / 2;
        mPaint.setColor(circleColor4);
        canvas.drawCircle(cx + (float) Math.cos(radianAnimatorValue) * bigRadiusX, cy + (float) Math.sin(radianAnimatorValue) * bigRadiusY, smallRadius, mPaint);
    }


    public void playAnimator() {
        if( !valueAnimator.isStarted() ){
            valueAnimator.start();
        }
    }

    public void cancleAnimator() {
        valueAnimator.cancel();
    }

}

