package com.android.framework.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.framework.customview.R;

/**
 * Created by meikai on 2018/07/19.
 */
public class SingleSeekBar extends View {

    private static final int trackBgColor = 0xffeeeeee;
    private static final int trackHegith = 6; //单位:dp


    private Bitmap bitmapThumb; //滑块图片
    private Rect trackRect; //控制滑轨的 绘制范围
    private Paint paint;


    public SingleSeekBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SingleSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SingleSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SingleSeekBar, defStyleAttr, 0);
        @DrawableRes int thumbDrawableResId = arr.getResourceId(R.styleable.SingleSeekBar_singleThumbDrawableRes, 0);
        arr.recycle();

        bitmapThumb = BitmapFactory.decodeResource(getContext().getResources(), thumbDrawableResId);

        paint = new Paint();
        paint.setColor(trackBgColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measuredWidth = widthSize;
        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measuredWidth = bitmapThumb.getWidth();
        }

        int measuredHeight = heightSize;
        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measuredHeight = bitmapThumb.getHeight();
        }

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        trackRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(trackRect, paint);
    }
}
