package com.android.framework.libui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.android.framework.libui.R;

/**
 * Created by meikai on 16/3/11.
 */
public class DragScoreView extends View {

    /**
     * 默认 的 轨道高度值 (单位 px)
     */
    private static final int DEFAULT_RAIL_HEIGHT = 30;
    /**
     * 羽化阴影 的 偏移量
     */
    private static int SHADOW_OFFSET = 0;
    /**
     * 允许的用户点击偏移，即当用户点击的位置与拖拽图标的距离小于此值时，认为点击成功
     */
    private static final int CLICK_OFFSET = 20;
    private int mTouchSlop;
    /**
     * 刻度宽度
     */
    private float scaleWidth = 10;
    /**
     * 轨道高度
     */
    private float railHeight = DEFAULT_RAIL_HEIGHT;

    private Paint railPaint = null;
    private Paint holderPaint;

    private RectF unSelectRailRectF = new RectF();
    private RectF selectRailRectF = new RectF();
    private PointF holderCenterPoint = new PointF();

    private Bitmap holderBitmapEnable = null;
    private Bitmap holderBitmapDisable = null;

    private String scaleArrStrAttr;
    private float minScore = 0;
    private float maxScore = 100;
    private int selectColor;
    private int unSelectColor;

    private float currentScore = minScore;
    private float[] scaleArray = null;

    private boolean isInTouch = false;
    private boolean touchEnable = true;

    private Boolean isInDrag = null;
    private boolean dragEnable = true;

    private float downEventX = 0f;
    private float downEventY = 0f;
    private float dragDeltaX = 0f;

    private OnScoreChangedListener scoreChangedListener = null;

    public DragScoreView(Context context) {
        super(context);
        initialize(context, null);
    }

    public DragScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public DragScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DragScoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DragScoreView);
        int thumbIconEnableRes = a.getResourceId(R.styleable.DragScoreView_holderIconEnable, 0);
        int thumbIconDisableRes = a.getResourceId(R.styleable.DragScoreView_holderIconDisable, 0);
        selectColor = a.getColor(R.styleable.DragScoreView_selectColor, Color.parseColor("#3190e8"));
        unSelectColor = a.getColor(R.styleable.DragScoreView_unSelectColor, Color.parseColor("#cccccc"));
        minScore = a.getFloat(R.styleable.DragScoreView_dragMin, 0);
        maxScore = a.getFloat(R.styleable.DragScoreView_dragMax, 100);
        scaleArrStrAttr = a.getString(R.styleable.DragScoreView_scaleArr);
        railHeight = a.getDimension(R.styleable.DragScoreView_railHeight, 30);
        a.recycle();

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        initRailPaint();

        initHolderPaint();

        //初始化手柄图
        holderBitmapEnable = BitmapFactory.decodeResource(getResources(), thumbIconEnableRes);
        holderBitmapDisable = BitmapFactory.decodeResource(getResources(), thumbIconDisableRes);

        //初始化刻度宽度
        scaleWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());

        // 初始时 拖拽图标中心 需要与 轨道起点重叠
        holderCenterPoint.x = holderBitmapDisable.getWidth() / 2;
    }

    /**
     * 初始化轨道画笔
     */
    private void initRailPaint() {
        railPaint = new Paint();
        railPaint.setAntiAlias(true);
        railPaint.setColor(unSelectColor);
    }

    /**
     * 初始化手柄画笔
     */
    private void initHolderPaint() {
        holderPaint = new Paint();
        holderPaint.setColor(Color.GRAY);
        BlurMaskFilter bf = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
        holderPaint.setMaskFilter(bf);
    }

    /**
     * 初始化手柄位置
     */
    private void initHolderPosition() {
        holderCenterPoint.y = getHeight() / 2;
    }

    /**
     * 初始化刻度位置坐标 的数组
     *
     * @param scaleArrStr 格式例如 :  0.25,0.5,0.75
     */
    private void initScale(String scaleArrStr) {

        String[] scaleStrArr = scaleArrStr.split(",");

        if (scaleArray == null) {
            scaleArray = new float[scaleStrArr.length];
        }

        for (int i = 0; i < scaleArray.length; i++) {
            scaleArray[i] = getPercentageX(Float.parseFloat(scaleStrArr[i]));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY: {
                break;
            }
            case MeasureSpec.AT_MOST: {
                width = holderBitmapDisable.getWidth() * 10;
            }
        }

        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED: {
                height = holderBitmapDisable.getHeight() + Math.abs(SHADOW_OFFSET);
            }
            case MeasureSpec.EXACTLY: {
                break;
            }
            case MeasureSpec.AT_MOST: {
                height = holderBitmapDisable.getHeight() + Math.abs(SHADOW_OFFSET);
            }
        }

        setMeasuredDimension(width, height);

        initRailRectF();

        initHolderPosition();

        initScale(scaleArrStrAttr);
    }

    /**
     * 初始化 轨道 位置
     */
    private void initRailRectF() {

        unSelectRailRectF.left = this.getPaddingLeft() + holderBitmapDisable.getWidth() / 2;
        unSelectRailRectF.right = this.getMeasuredWidth() - this.getPaddingRight() - holderBitmapDisable.getWidth() / 2 + SHADOW_OFFSET;

        unSelectRailRectF.top = (this.getMeasuredHeight() - railHeight) / 2;
        unSelectRailRectF.bottom = unSelectRailRectF.top + railHeight;

        selectRailRectF.set(unSelectRailRectF);
        selectRailRectF.right = selectRailRectF.left;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean consumed = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                downEventX = event.getX();
                downEventY = event.getY();

                if (touchEnable && dragEnable && isEventInHolder(event)) {
                    consumed = true;
                    isInTouch = true;

                    isInDrag = null;
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {

                if (Math.abs(event.getX() - downEventX) < mTouchSlop && Math.abs(event.getY() - downEventY) < mTouchSlop) {
                    isInDrag = null;
                    consumed = true;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }


                if (isInDrag == Boolean.TRUE) {
                    consumed = true;

                    dragDeltaX = event.getX() - downEventX;

                    processDragDeltaXChanged(dragDeltaX);

                } else {
                    if (Math.abs(event.getX() - downEventX) > Math.abs(event.getY() - downEventY)) {
                        isInDrag = Boolean.TRUE;
                        consumed = true;
                        getParent().requestDisallowInterceptTouchEvent(true);

                        dragDeltaX = event.getX() - downEventX;

                        processDragDeltaXChanged(dragDeltaX);

                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        isInDrag = Boolean.FALSE;

                        consumed = false;
                    }
                }

            }
            break;
            case MotionEvent.ACTION_UP: {
                holderCenterPoint.x += dragDeltaX;

                if (holderCenterPoint.x < unSelectRailRectF.left - holderBitmapDisable.getWidth() / 2)
                    holderCenterPoint.x = unSelectRailRectF.left;
                if (holderCenterPoint.x > unSelectRailRectF.right - holderBitmapDisable.getWidth() / 2)
                    holderCenterPoint.x = unSelectRailRectF.right;

                dragDeltaX = 0;

                isInDrag = null;
                isInTouch = false;

                autoMoveToNearest();
            }
            break;
            default:
                break;

        }

        invalidate();

        return consumed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRailWay(canvas);
        drawHolder(canvas);

    }

    private void drawRailWay(Canvas canvas) {

        selectRailRectF.right = filterUnSelectRailRectFRight(holderCenterPoint.x + dragDeltaX);

        //画 轨道 的 灰色背景
        railPaint.setColor(unSelectColor);
        canvas.drawRoundRect(unSelectRailRectF, 15, 15, railPaint);

        //画 轨道 的 有效彩色背景
        railPaint.setColor(selectColor);
        canvas.drawRoundRect(selectRailRectF, 15, 15, railPaint);

        //画 节点刻度线
        railPaint.setColor(Color.WHITE);
//        canvas.drawRect(new RectF(unSelectRailRectF.left, unSelectRailRectF.top+1,unSelectRailRectF.right,unSelectRailRectF.bottom+1), railPaint);
        for (Float scale : scaleArray) {
            canvas.drawRect(scale - scaleWidth / 2, unSelectRailRectF.top, scale + scaleWidth / 2, unSelectRailRectF.bottom + 1, railPaint);
        }
    }

    private void drawHolder(Canvas canvas) {
        float left = holderCenterPoint.x - holderBitmapDisable.getWidth() / 2;
        float top = (this.getMeasuredHeight() - holderBitmapDisable.getHeight()) / 2;

        int[] offsetXY = {0, 0};
        //画 把柄的 边缘羽化背景图
        canvas.drawBitmap(holderBitmapDisable.extractAlpha(holderPaint, offsetXY), filterHolderX(left + dragDeltaX), top, holderPaint);
        SHADOW_OFFSET = offsetXY[0] * 2;

        //画 禁用模式 的 灰色 把柄图
        canvas.drawBitmap(holderBitmapDisable, filterHolderX(left + dragDeltaX), top, holderPaint);

        //画 启用模式 的 彩色 把柄图
        if ((isInTouch && (isInDrag == null || isInDrag == true)) || currentScore > 0)
            canvas.drawBitmap(holderBitmapEnable, filterHolderX(left + dragDeltaX), top, holderPaint);
    }

    private boolean isEventInHolder(MotionEvent event) {

        if (event.getX() + CLICK_OFFSET > holderCenterPoint.x - holderBitmapEnable.getWidth() / 2
                && event.getX() - CLICK_OFFSET < holderCenterPoint.x + holderBitmapEnable.getWidth() / 2
                && event.getY() + CLICK_OFFSET > holderCenterPoint.y - holderBitmapEnable.getHeight() / 2
                && event.getY() - CLICK_OFFSET < holderCenterPoint.y + holderBitmapEnable.getHeight() / 2)
            return true;

        return false;
    }

    private float filterHolderX(float logicHolderLeft) {
        if (logicHolderLeft < unSelectRailRectF.left - holderBitmapDisable.getWidth() / 2)
            return unSelectRailRectF.left - holderBitmapDisable.getWidth() / 2;
        if (logicHolderLeft > unSelectRailRectF.right - holderBitmapDisable.getWidth() / 2)
            return unSelectRailRectF.right - holderBitmapDisable.getWidth() / 2;

        return logicHolderLeft;
    }

    private float filterUnSelectRailRectFRight(float logicUnSelectRailRectFRight) {
        if (logicUnSelectRailRectFRight < unSelectRailRectF.left)
            return unSelectRailRectF.left;
        if (logicUnSelectRailRectFRight > unSelectRailRectF.right)
            return unSelectRailRectF.right;

        return logicUnSelectRailRectFRight;
    }

    private void processDragDeltaXChanged(float dragDeltaX) {

        if (scoreChangedListener == null)
            return;

        float valueX = holderCenterPoint.x + dragDeltaX - unSelectRailRectF.left;

        if (holderCenterPoint.x + dragDeltaX < unSelectRailRectF.left)
            valueX = 0;
        if (holderCenterPoint.x + dragDeltaX > unSelectRailRectF.right)
            valueX = unSelectRailRectF.right - unSelectRailRectF.left;

        currentScore = valueX * (maxScore - minScore) / (unSelectRailRectF.right - unSelectRailRectF.left)
                + minScore;

        scoreChangedListener.onSelected(Math.round(currentScore));
    }

    private void autoMoveToNearest() {
        float centerX = holderCenterPoint.x;

        float[] scaleArrayWithHeadTail = new float[scaleArray.length + 2];
        scaleArrayWithHeadTail[0] = unSelectRailRectF.left;
        scaleArrayWithHeadTail[scaleArrayWithHeadTail.length - 1] = unSelectRailRectF.right;

        for (int i = 0; i < scaleArray.length; i++) {
            scaleArrayWithHeadTail[i + 1] = scaleArray[i];
        }

        float[] scaleArrayHalfPos = new float[scaleArray.length + 1];
        for (int j = 0; j < scaleArrayWithHeadTail.length - 1; j++) {
            scaleArrayHalfPos[j] = (scaleArrayWithHeadTail[j] + scaleArrayWithHeadTail[j + 1]) / 2;
        }

        int k;
        for (k = 0; k < scaleArrayHalfPos.length; k++) {
            if (centerX < scaleArrayHalfPos[k])
                break;
        }

        moveHolderSmooth(centerX, scaleArrayWithHeadTail[k]);
    }

    private void moveHolderSmooth(float srcX, float dstX) {
        ValueAnimator animator = new ValueAnimator();
        PropertyValuesHolder pvh = PropertyValuesHolder.ofFloat("x", srcX, dstX);
        animator.setValues(pvh);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue("x");
                moveHolder(x);
                touchEnable = false;
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                touchEnable = true;
                dragEnable = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                touchEnable = true;
            }
        });
        animator.start();

    }

    /**
     * 移动手柄
     *
     * @param x
     */
    private void moveHolder(float x) {
        holderCenterPoint.x = x;
        selectRailRectF.right = holderCenterPoint.x + holderBitmapDisable.getWidth() / 2;
        if (scoreChangedListener != null) {

            currentScore = (holderCenterPoint.x - unSelectRailRectF.left) * (maxScore - minScore)
                    / (unSelectRailRectF.right - unSelectRailRectF.left)
                    + minScore;

            scoreChangedListener.onSelected(Math.round(currentScore));
        }
        invalidate();
    }

    private float getPercentageX(float percent) {
        percent = percent < 0 ? 0 : percent > 1 ? 1 : percent;
        return (percent * (unSelectRailRectF.right - unSelectRailRectF.left)) + holderBitmapDisable.getWidth() / 2;
    }

    public interface OnScoreChangedListener {
        void onSelected(int score);
    }

    public OnScoreChangedListener getScoreChangedListener() {
        return scoreChangedListener;
    }

    public void setOnScoreChangedListener(OnScoreChangedListener scoreSelectedListener) {
        this.scoreChangedListener = scoreSelectedListener;
    }

}
