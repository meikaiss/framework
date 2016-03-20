package com.android.framework.libui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.android.framework.libui.R;

/**
 * Created by meikai on 16/3/17.
 */
public class ImageOuterTextView extends View {

    private static final int TEXT_SIZE_SLOP = 2;
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#E66060");

    private Bitmap outerBitmap;
    private int innerTextColor;
    private int outerDrawableId;
    private float innerFactor;
    private float innerTextRadian;
    private String maxLengthText;
    private String innerText;

    private Paint textPaint;

    private int baseline;

    public int getInnerTextColor() {
        return innerTextColor;
    }

    public void setInnerTextColor(int innerTextColor) {
        this.innerTextColor = innerTextColor;
        textPaint.setColor(innerTextColor);
        invalidate();
    }

    public int getOuterDrawableId() {
        return outerDrawableId;
    }

    public void setOuterDrawableId(int outerDrawableId) {
        this.outerDrawableId = outerDrawableId;
        outerBitmap = BitmapFactory.decodeResource(getResources(), outerDrawableId);
        invalidate();
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
        invalidate();
    }

    public ImageOuterTextView(Context context) {
        this(context, null);
    }

    public ImageOuterTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageOuterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageOuterTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ImageOuterTextView);
        outerDrawableId = a.getResourceId(R.styleable.ImageOuterTextView_outerDrawable, 0);
        innerTextColor = a.getColor(R.styleable.ImageOuterTextView_innerTextColor, DEFAULT_TEXT_COLOR);
        int innerRadius = a.getInt(R.styleable.ImageOuterTextView_innerRadius, 0);
        int outerRadius = a.getInt(R.styleable.ImageOuterTextView_outerRadius, 0);
        innerTextRadian = a.getFloat(R.styleable.ImageOuterTextView_innerTextRadian, 0);
        maxLengthText = a.getString(R.styleable.ImageOuterTextView_maxLengthText);
        innerText = a.getString(R.styleable.ImageOuterTextView_innerText);
        a.recycle();

        outerBitmap = BitmapFactory.decodeResource(getResources(), outerDrawableId);
        innerFactor = (float) innerRadius / outerRadius;

        textPaint = new Paint();
        textPaint.setColor(innerTextColor);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measuredWidth = widthSize;
        int measuredHeight = heightSize;
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                measuredWidth = outerBitmap.getWidth();
                break;
            case MeasureSpec.EXACTLY:
                measuredWidth = widthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                measuredHeight = outerBitmap.getHeight();
                break;
            case MeasureSpec.EXACTLY:
                measuredHeight = heightSize;
                break;
        }

        setMeasuredDimension(measuredWidth, measuredHeight);

        computeSuitableTextSize();
    }

    private void computeSuitableTextSize() {
        int minTextSizeDp = 1;
        int hitTextSizeDp = minTextSizeDp;
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hitTextSizeDp, Resources.getSystem().getDisplayMetrics()));

        Rect bounds = new Rect();
        textPaint.getTextBounds(maxLengthText, 0, maxLengthText.length(), bounds);

        while ((float) bounds.width() / (float) getMeasuredWidth() < innerFactor) {
            hitTextSizeDp++;
            textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hitTextSizeDp, Resources.getSystem().getDisplayMetrics()));
            textPaint.getTextBounds(maxLengthText, 0, maxLengthText.length(), bounds);
        }

        int realTextSizeDp = hitTextSizeDp - TEXT_SIZE_SLOP;
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, realTextSizeDp, Resources.getSystem().getDisplayMetrics()));

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(outerBitmap, null, new Rect(0, 0, getWidth(), getHeight()), null);


        canvas.rotate(-innerTextRadian, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        canvas.drawText(innerText, getMeasuredWidth() / 2, baseline, textPaint);

        canvas.save();
        canvas.restore();
    }
}
