package com.android.framework.libui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * 用于 适配器 getView 中生成 控件， 避免 渲染xml文件、以及findViewById耗时 引用滑动卡顿
 * Created by meikai on 16/3/17.
 */
public class DoubleTextView extends View {

    private int textSizePx;
    private int textColor;

    private String leftText;
    private String rightText;

    public void setText(String leftText, String rightText) {
        this.leftText = leftText == null ? "" : leftText;
        this.rightText = rightText == null ? "" : rightText;

        bounds = new Rect();
        textPaint.getTextBounds(this.rightText, 0, this.rightText.length(), bounds);

        invalidate();
    }

    public void setTextSizePx(int textSizePx) {
        this.textSizePx = textSizePx;
        this.textPaint.setTextSize(this.textSizePx);

        invalidate();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.textPaint.setColor(this.textColor);

        invalidate();
    }

    private Paint textPaint;
    private Rect bounds;

    public DoubleTextView(Context context) {
        super(context);
        init();
    }

    public DoubleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DoubleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        leftText = rightText = "";

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(36);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);


        bounds = new Rect();
        textPaint.getTextBounds(rightText, 0, rightText.length(), bounds);
    }

    int baseline = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(leftText, 0, baseline, textPaint);

        canvas.drawText(rightText, getMeasuredWidth() - bounds.right, baseline, textPaint);

    }
}

