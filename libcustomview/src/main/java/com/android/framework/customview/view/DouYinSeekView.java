package com.android.framework.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.framework.util.DimenUtil;
import com.android.framework.util.LogUtil;

/**
 * Created by meikai on 2018/03/08.
 */
public class DouYinSeekView extends View {

    private static final int THUMB_WIDTH = 6;// 滑块宽度，单位:dp
    private static final int THUMB_CORNER = 100;// 滑块圆角半径，单位:dp
    private static final int THUMB_COLOR = 0XFFFFC200; // 滑块的颜色

    private static final int DRAG_SLOP = 16; // 点击滑块左右附近距离时也可触发滑动，单位:dp

    private int touchSlop;

    private int thumbWidth;
    private int thumbCorner;
    private int dragSlop;

    private View target;

    private Paint paint;

    private long maxDuration; //视频的总时长
    private int currentPos;//当前选择的位置，单位:像素

    private RectF thumbRectF;
    private RectF thumbRectFDraw;

    private OnDragListener onDragListener;

    public DouYinSeekView(Context context) {
        super(context);
        init(context);
    }

    public DouYinSeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DouYinSeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
    }

    private void init(Context context) {

        touchSlop = 0;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        thumbWidth = DimenUtil.dp2px(context, THUMB_WIDTH);
        thumbCorner = DimenUtil.dp2px(context, THUMB_CORNER);
        dragSlop = DimenUtil.dp2px(context, DRAG_SLOP);

        thumbRectF = new RectF();
        thumbRectFDraw = new RectF();
    }

    public void setTarget(View target) {
        this.target = target;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        currentPos = target.getLeft();

        thumbRectF.set(currentPos - thumbWidth / 2, 0, currentPos + thumbWidth / 2, getMeasuredHeight());
        thumbRectFDraw.set(thumbRectF);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDragThumb(canvas);
    }

    private void drawDragThumb(Canvas canvas) {

        paint.setColor(THUMB_COLOR);
        canvas.drawRoundRect(thumbRectFDraw, thumbCorner, thumbCorner, paint);
    }

    private int downX;
    private boolean isInDrag;

    private void log(String msg) {
        LogUtil.e(msg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (canDrag(event)) {
                    downX = (int) (event.getX() + 0.5f);
                    log("ACTION_DOWN, downX=" + downX);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE: {
                int newX = (int) (event.getX() + 0.5f);

                if (isInDrag) {
                    boolean direction = newX > downX; // true:向右滑动; false:向左滑动

                    // 防止 第一次 触摸时 发生 抖动
                    newX = currentPos + newX - downX + (direction ? -1 : 1) * touchSlop;

                    // 防止 滑出 最左、最右 边
                    if (newX < target.getLeft()) {
                        newX = target.getLeft();
                    } else if (newX > target.getRight()) {
                        newX = target.getRight();
                    }

                    thumbRectFDraw.set(newX - thumbWidth / 2, 0, newX + thumbWidth / 2, getMeasuredHeight());
                    invalidate();

                    if (onDragListener != null) {
                        float factor = (float) (newX - target.getLeft()) / target.getWidth();
                        onDragListener.onDragMove((long) (factor * maxDuration + 0.5f));
                    }

                } else {
                    if (Math.abs(newX - downX) >= touchSlop) {
                        isInDrag = true;
                    }
                }
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                int newX = (int) (event.getX() + 0.5f);

                if (isInDrag) {
                    boolean direction = newX > downX; // true:向右滑动; false:向左滑动

                    // 防止 第一次 触摸时 发生 抖动
                    newX = currentPos + newX - downX + (direction ? -1 : 1) * touchSlop;

                    // 防止 滑出 最左、最右 边
                    if (newX < target.getLeft()) {
                        newX = target.getLeft();
                    } else if (newX > target.getRight()) {
                        newX = target.getRight();
                    }

                    thumbRectFDraw.set(newX - thumbWidth / 2, 0, newX + thumbWidth / 2, getMeasuredHeight());
                    invalidate();

                    if (onDragListener != null) {
                        float factor = (float) (newX - target.getLeft()) / target.getWidth();
                        onDragListener.onDragConfirm((long) (factor * maxDuration + 0.5f));
                    }

                    thumbRectF.set(thumbRectFDraw);
                    currentPos = newX; // 更新当前位置
                }

                isInDrag = false;
            }
            break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private boolean canDrag(MotionEvent event) {
        RectF thumbRectTemp = new RectF();
        thumbRectTemp.set(thumbRectF.left - dragSlop, thumbRectF.top, thumbRectF.right + dragSlop, thumbRectF.bottom);

        return thumbRectTemp.contains(event.getX(), event.getY());
    }

    public interface OnDragListener {
        void onDragMove(long currentTime);

        void onDragConfirm(long currentTime);
    }
}
