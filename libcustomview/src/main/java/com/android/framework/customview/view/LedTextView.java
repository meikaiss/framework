package com.android.framework.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.framework.customview.R;
import com.android.framework.util.FontUtils;

/**
 * Created by meikai on 16/7/7.
 */
public class LedTextView extends TextView {

    private FontUtils utils;
    /*
     * 一个字用16*16的点阵表示
     */
    private int dots = 16;
    /*
     * 点阵之间的距离
     */
    private float spacing = 10;
    /*
     * 点阵中点的半径
     */
    private float radius;
    private Paint normalPaint;
    private Paint selectPaint;

    private String text;
    /*
     * 汉字对应的点阵矩阵
     */
    private boolean[][] matrix;
    /*
     * 是否开启滚动
     */
    private boolean scroll;
    /*
     * 默认颜色绿色
     */
    private int paintColor = Color.GREEN;

    /*
     * 滚动的text
     */
    private volatile boolean scrollText = true;
    /*
     * 用来调整滚动速度
     */
    private int sleepTime = 60;

    /*
     * 滚动方向，默认0向左
     */
    private int scrollDirection = 0;

    public LedTextView(Context context) {
        this(context, null, 0);
    }

    public LedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LedTextView);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.LedTextView_textColor) {
                paintColor = typedArray.getColor(R.styleable.LedTextView_textColor, Color.GREEN);
            } else if (attr == R.styleable.LedTextView_spacing) {
                spacing = typedArray.getDimension(R.styleable.LedTextView_spacing, 10);
            } else if (attr == R.styleable.LedTextView_scroll) {
                scroll = typedArray.getBoolean(R.styleable.LedTextView_scroll, true);
            } else if (attr == R.styleable.LedTextView_speed) {
                int speed = typedArray.getInt(R.styleable.LedTextView_speed, 0);
                if (0 == speed)
                    sleepTime = 100;
                else
                    sleepTime = 300;
            } else if (attr == R.styleable.LedTextView_scrollDirection) {
                scrollDirection = typedArray.getInt(R.styleable.LedTextView_scrollDirection, 0);
            }
        }
        typedArray.recycle();
        selectPaint = new Paint();
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(paintColor);

        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setColor(paintColor);

        text = getText().toString();
        if (1 == scrollDirection) {
            text = reverseString(text);
        }

        utils = new FontUtils(getContext());
        matrix = utils.getWordsInfo(text);
        if (scroll) {
            handler.sendEmptyMessage(1);
        }
    }

    /**
     * 翻转字符串，当设置享有滚动时调用
     *
     * @param str
     * @return
     */
    private String reverseString(String str) {
        StringBuffer sb = new StringBuffer(str);
        sb = sb.reverse();
        return sb.toString();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (scrollText) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (0 == scrollDirection) {
                    matrixLeftMove(matrix);
                } else {
                    matrixRightMove(matrix);
                }
                postInvalidate();

                handler.sendEmptyMessageDelayed(1, sleepTime);
            }
        }
    };

    /**
     * 向左滚动时调用，列循环左移
     *
     * @param matrix
     */
    private void matrixLeftMove(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean tmp = matrix[i][0];
            System.arraycopy(matrix[i], 1, matrix[i], 0, matrix[0].length - 1);
            matrix[i][matrix[0].length - 1] = tmp;
        }
    }

    /**
     * 向右滚动时调用，列循环右移
     *
     * @param matrix
     */
    private void matrixRightMove(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean tmp = matrix[i][matrix[0].length - 1];
            System.arraycopy(matrix[i], 0, matrix[i], 1, matrix[0].length - 1);
            matrix[i][0] = tmp;
        }
    }

    /**
     * 主要是想处理AT_MOST的情况，我觉得View默认的情况就挺好的，由于继承自TextView，而TextView重
     * 写了onMeasure，因此这里参考View#onMeasure函数的写法即可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    private void drawText(Canvas canvas) {
        radius = (getHeight() - (dots + 1) * spacing) / (2 * dots);
        // 行
        int row = 0;
        // 列
        int column = 0;
        while (getYPosition(row) < getHeight()) {
            while (getXPosition(column) < getWidth()) {
                // just draw
                if (row < matrix.length && column < matrix[0].length
                        && matrix[row][column]) {
                    canvas.drawCircle(getXPosition(column), getYPosition(row),
                            radius, selectPaint);
                } else {
                    canvas.drawCircle(getXPosition(column), getYPosition(row),
                            radius, normalPaint);
                }
                column++;
            }
            row++;
            column = 0;
        }
    }

    /**
     * 获取绘制第column列的点的X坐标
     *
     * @param column
     * @return
     */
    private float getXPosition(int column) {
        return spacing + radius + (spacing + 2 * radius) * column;
    }

    /**
     * 获取绘制第row行的点的Y坐标
     *
     * @param row
     * @return
     */
    private float getYPosition(int row) {
        return spacing + radius + (spacing + 2 * radius) * row;
    }

    private void stopScroll() {
        scrollText = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        stopScroll();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        drawText(canvas);
    }
}