package com.android.framework.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.android.framework.util.DimenUtil;


/**
 * Created by meikai on 2019/12/23
 */
public class RadarView extends View {

    private static final boolean enableCoor = false; //是否绘制坐标系

    private Paint mPaint;
    private int mCircleColor;
    private int mSweepColor[];
    private float mCenterY;
    private float mCenterX;
    private float mRadius;
    private int mPading;
    private Shader mSweepShader;
    private ValueAnimator mValueAnimator;
    private Matrix mRoateMatrix;
    private float mDegressProgress = 0.2f;

    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private static final String TAG = "RadarView";

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoateMatrix = new Matrix();
        mCircleColor = Color.parseColor("#000000");
        mSweepColor = new int[]{Color.parseColor("#FF00D1FF"),
                Color.parseColor("#FFFFFFFF")};
        mPaint.setStrokeWidth(DimenUtil.dp2px(getContext(), 2));
        mRadius = -1;
        mPading = (int) DimenUtil.dp2px(getContext(), 10);
        mValueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setDuration(5000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegressProgress = (float) animation.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate: " + mDegressProgress);
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRadius < 0) {
            int minDistance = Math.min(getMeasuredHeight(), getMeasuredWidth());
            mCenterX = minDistance / 2;
            mCenterY = mCenterX;
            mRadius = mCenterX - mPading;
            mSweepShader = new SweepGradient(mCenterX, mCenterY, mSweepColor[0], mSweepColor[1]);
        }
        //绘制扫描线
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(mSweepShader);
        mRoateMatrix.setRotate(mDegressProgress * 360, mCenterX, mCenterY);
        mSweepShader.setLocalMatrix(mRoateMatrix);
        // canvas.rotate(mDegressProgress * 360, mCenterX, mCenterY);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        canvas.restore();

        //绘制圆形和直角坐标
        if (enableCoor) {
            drawCoordinateSystem(canvas);
        }

        if (!mValueAnimator.isStarted()) {
            mValueAnimator.start();
        }

    }

    private void drawCoordinateSystem(Canvas canvas) {
        mPaint.setColor(mCircleColor);
        mPaint.setShader(null);
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        canvas.drawCircle(mCenterX, mCenterY, mRadius / 2, mPaint);
        canvas.drawLine(mPading, mCenterY, mCenterX + mRadius, mCenterY, mPaint);
        canvas.drawLine(mCenterX, mPading, mCenterX, mCenterY + mRadius, mPaint);
        canvas.restore();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: ");
        if (mValueAnimator.isStarted()) {
            mValueAnimator.cancel();
        }
    }
}

