package com.android.framework.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.framework.util.DensityUtil;
import com.android.framework.util.ScreenUtil;

/**
 * 左右双端 都可以移动的范围选择控件
 * Created by meikai on 2018/02/02.
 */
public class VideoClipSeekBar extends View {

    private static final int DEFAULT_THUMB_WIDTH = 20;
    private static final int DEFAULT_TOP_BOT_LINE_WIDTH = 10;

    private View targetView;
    private int padding2Target;
    private float minX;
    private float maxX;

    private int verticalLineWidth = DEFAULT_TOP_BOT_LINE_WIDTH;
    private int thumbWidth = DEFAULT_THUMB_WIDTH;

    private RectF leftThumbRect;
    private RectF rightThumbRect;

    private RectF leftThumbRectDraw;
    private RectF rightThumbRectDraw;

    private Paint paint;

    private DragState dragState;

    private OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public VideoClipSeekBar(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public VideoClipSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public VideoClipSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        thumbWidth = DensityUtil.dp2px(context, 10);

        paint = new Paint();
        paint.setColor(0xFFFFC300);

        leftThumbRect = new RectF();
        rightThumbRect = new RectF();

        leftThumbRectDraw = new RectF();
        rightThumbRectDraw = new RectF();

    }

    public void attachTarget(View view) {
        this.targetView = view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        padding2Target = (ScreenUtil.getScreenWidth(getContext()) - targetView.getMeasuredWidth()) / 2;

        leftThumbRect.left = padding2Target - thumbWidth;
        leftThumbRect.right = leftThumbRect.left + thumbWidth;
        leftThumbRect.top = 0;
        leftThumbRect.bottom = MeasureSpec.getSize(heightMeasureSpec);

        rightThumbRect.left = getMeasuredWidth() - padding2Target;
        rightThumbRect.top = 0;
        rightThumbRect.right = getMeasuredWidth() - padding2Target + thumbWidth;
        rightThumbRect.bottom = MeasureSpec.getSize(heightMeasureSpec);

        minX = leftThumbRect.left;
        maxX = rightThumbRect.right;

        leftThumbRectDraw.set(leftThumbRect);
        rightThumbRectDraw.set(rightThumbRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制 左右的 滑块
        canvas.drawRoundRect(leftThumbRectDraw, 15f, 15f, paint);
        canvas.drawRoundRect(rightThumbRectDraw, 15f, 15f, paint);

        // 绘制 上下的 横线
        canvas.drawRect(leftThumbRectDraw.right - thumbWidth / 2,
                0,
                rightThumbRectDraw.left + thumbWidth / 2,
                verticalLineWidth,
                paint);
        canvas.drawRect(leftThumbRectDraw.right - thumbWidth / 2,
                getHeight() - verticalLineWidth,
                rightThumbRectDraw.left + thumbWidth / 2,
                getHeight(),
                paint);
    }

    private int downX;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (isDotInRect(event, leftThumbRect)) {
                    dragState = DragState.Left;
                    downX = (int) (event.getX() + 0.5f);
                    return true;
                } else if (isDotInRect(event, rightThumbRect)) {
                    dragState = DragState.Right;
                    downX = (int) (event.getX() + 0.5f);
                    return true;
                } else {
                    dragState = DragState.None;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (dragState == DragState.Left) {
                    int deltaX = (int) (event.getX() + 0.5f) - downX;

                    // 检验 是否可以移到这个位置(左不能移过右，右不能移过左)
                    if (leftThumbRect.right + deltaX >= rightThumbRect.left) {
                        return true;
                    }

                    leftThumbRectDraw.left = leftThumbRect.left + deltaX;
                    leftThumbRectDraw.right = leftThumbRect.right + deltaX;

                    if (leftThumbRectDraw.left < minX) {
                        leftThumbRectDraw.left = minX;
                        leftThumbRectDraw.right = leftThumbRectDraw.left + thumbWidth;
                    }

                    if (onSelectListener != null) {
                        onSelectListener.onMove(leftThumbRectDraw.right, rightThumbRectDraw.left);
                    }

                    invalidate();
                } else if (dragState == DragState.Right) {
                    int deltaX = (int) (event.getX() + 0.5f) - downX;

                    // 检验 是否可以移到这个位置(左不能移过右，右不能移过左)
                    if (rightThumbRect.left + deltaX <= leftThumbRect.right) {
                        return true;
                    }

                    rightThumbRectDraw.left = rightThumbRect.left + deltaX;
                    rightThumbRectDraw.right = rightThumbRect.right + deltaX;

                    if (rightThumbRectDraw.right > maxX) {
                        rightThumbRectDraw.right = maxX;
                        rightThumbRectDraw.left = rightThumbRectDraw.right - thumbWidth;
                    }

                    if (onSelectListener != null) {
                        onSelectListener.onMove(leftThumbRectDraw.right, rightThumbRectDraw.left);
                    }

                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (dragState == DragState.Left) {
                    int deltaX = (int) (event.getX() + 0.5f) - downX;

                    // 检验 是否可以移到这个位置(左不能移过右，右不能移过左)
                    if (leftThumbRect.right + deltaX >= rightThumbRect.left) {
                        leftThumbRect = new RectF(leftThumbRectDraw);
                        return true;
                    }

                    leftThumbRectDraw.left = leftThumbRect.left + deltaX;
                    leftThumbRectDraw.right = leftThumbRect.right + deltaX;

                    if (leftThumbRectDraw.left < minX) {
                        leftThumbRectDraw.left = minX;
                        leftThumbRectDraw.right = leftThumbRectDraw.left + thumbWidth;
                    }

                    if (onSelectListener != null) {
                        onSelectListener.onConfirm(leftThumbRectDraw.right, rightThumbRectDraw.left);
                    }
                    invalidate();

                    leftThumbRect = new RectF(leftThumbRectDraw);

                } else if (dragState == DragState.Right) {
                    int deltaX = (int) (event.getX() + 0.5f) - downX;

                    // 检验 是否可以移到这个位置(左不能移过右，右不能移过左)
                    if (rightThumbRect.left + deltaX <= leftThumbRect.right) {
                        rightThumbRect = new RectF(rightThumbRectDraw);
                        return true;
                    }

                    rightThumbRectDraw.left = rightThumbRect.left + deltaX;
                    rightThumbRectDraw.right = rightThumbRect.right + deltaX;

                    if (rightThumbRectDraw.right > maxX) {
                        rightThumbRectDraw.right = maxX;
                        rightThumbRectDraw.left = rightThumbRectDraw.right - thumbWidth;
                    }

                    if (onSelectListener != null) {
                        onSelectListener.onConfirm(leftThumbRectDraw.right, rightThumbRectDraw.left);
                    }

                    invalidate();

                    rightThumbRect = new RectF(rightThumbRectDraw);
                }

                dragState = DragState.None;
                break;
        }

        return super.onTouchEvent(event);
    }


    private boolean isDotInRect(MotionEvent event, RectF rect) {
        int x = (int) (event.getX() + 0.5f);
        int y = (int) (event.getY() + 0.5f);

        return rect.contains(x, y);
    }

    public interface OnSelectListener {
        void onMove(float left, float right);

        void onConfirm(float left, float right);
    }

    private enum DragState {
        None,
        Left,
        Right
    }

}
