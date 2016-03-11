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

import com.android.framework.libui.R;

/**
 * Created by meikai on 16/3/11.
 */
public class DragScoreView extends View {
    /**
     * 影子偏离度
     */
    private static final int SHADOW_OFFSET = 10;
    /**
     * 刻度宽度
     */
    private float scaleWidth = 0;

    private Context mContext = null;

    private Paint railPaint = null;

    private Paint thumbPaint = null;

    private RectF unSelectRailRectF = null;

    private RectF selectRailRectF = null;

    private Bitmap thumbsBitmapEnable = null;

    private Bitmap thumbsBitmapDisable = null;

    private Bitmap thumbsBitmap = null;//是可用的,和不可用的引用

    private PointF thumb = null;

    private boolean isFirstDown = false;

    private float max = 100;

    private float min = 0;

    private int selectColor = Color.parseColor("#3190e8");

    private int unSelectColor = Color.parseColor("#333333");

    private int thumbIconEnableRes = 0;

    private int thumbIconDisableRes = 0;

    private float[] scaleArray = null;

    private boolean isTouchAble = true;

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
        this.mContext = context.getApplicationContext();

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DragScoreView);
            thumbIconEnableRes = a.getResourceId(R.styleable.DragScoreView_thumbIconEnable, 0);
            thumbIconDisableRes = a.getResourceId(R.styleable.DragScoreView_thumbIconDisable, 0);
            selectColor = a.getColor(R.styleable.DragScoreView_selectColor, Color.parseColor("#3190e8"));
            unSelectColor = a.getColor(R.styleable.DragScoreView_unSelectColor, Color.parseColor("#cccccc"));
            max = a.getFloat(R.styleable.DragScoreView_drag_max, 100);
            min = a.getFloat(R.styleable.DragScoreView_drag_min, 0);
            a.recycle();
        }

        //初始化轨道画笔
        initRailPaint();
        //初始化手柄画笔
        initThumbPaint();
        //初始化手柄图
        thumbsBitmapEnable = BitmapFactory.decodeResource(getResources(), thumbIconEnableRes);
        thumbsBitmapDisable = BitmapFactory.decodeResource(getResources(), thumbIconDisableRes);
        thumbsBitmap = thumbsBitmapDisable;
        //初始化刻度宽度
        scaleWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mContext.getResources().getDisplayMetrics());
    }

    private void initThumbPaint() {
        thumbPaint = new Paint();
        thumbPaint.setColor(Color.GRAY);
        BlurMaskFilter bf = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
        thumbPaint.setMaskFilter(bf);

    }

    private void initRailPaint() {
        railPaint = new Paint();
        railPaint.setAntiAlias(true);
        railPaint.setColor(unSelectColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                if (isTouchAble && event.getX() > thumb.x - 20
                        && event.getX() < thumb.x + thumbsBitmap.getWidth() + 20) {//判断点击区域
                    isFirstDown = true;
                    return true;
                }
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                float eventX = event.getX();
                if (isFirstDown) {
                    moveThumb(eventX);
                    isFirstDown = false;
                } else {
                    moveThumb(eventX);
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                isFirstDown = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                magnetism();
                invalidate();
                break;
            }
        }
        return true;
    }

    /**
     * 磁力吸引
     */
    private void magnetism() {
        float minX = unSelectRailRectF.left;
        float maxX = unSelectRailRectF.right;
        float centerX = thumb.x;
        if (centerX <= (minX + scaleArray[0]) / 2) {
            //归零
            moveThumbWithAnimation(centerX, minX);
        } else if (centerX >= (minX + scaleArray[0]) / 2 && centerX < (scaleArray[0] + scaleArray[1]) / 2) {
            //归第一刻度
            moveThumbWithAnimation(centerX, scaleArray[0]);
        } else if (centerX >= (scaleArray[0] + scaleArray[1]) / 2 && centerX < (scaleArray[1] + maxX) / 2) {
            //归第二刻度
            moveThumbWithAnimation(centerX, scaleArray[1]);
        } else if (centerX >= (scaleArray[1] + maxX) / 2) {
            //归最大
            moveThumbWithAnimation(centerX, maxX);
        }
    }

    private void moveThumbWithAnimation(float srcX, float dstX) {
        ValueAnimator animator = new ValueAnimator();
        PropertyValuesHolder pvh = PropertyValuesHolder.ofFloat("x", srcX, dstX);
        animator.setValues(pvh);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue("x");
                moveThumb(x);
                isTouchAble = false;
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isTouchAble = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isTouchAble = true;
            }
        });
        animator.start();

    }

    /**
     * 移动手柄
     *
     * @param x
     */
    private void moveThumb(float x) {
        if (rangeCheck(x)) {//检查范围
            thumb.x = x;
            selectRailRectF.right = thumb.x + thumbsBitmap.getWidth() / 2;
            if (this.scoreChangedListener != null) {
                this.scoreChangedListener
                        .onSelected((int) ((x - thumbsBitmap.getWidth() / 2) *
                                (max - min) / (unSelectRailRectF.right - unSelectRailRectF.left)));
            }
        }
        if (thumb.x <= unSelectRailRectF.left + 1 /*加1是因为体验好点...没别的,可以删*/ || thumb.x > unSelectRailRectF.right) {
            thumbsBitmap = thumbsBitmapDisable;
        } else {
            thumbsBitmap = thumbsBitmapEnable;
        }
        invalidate();
    }

    /**
     * 范围检查
     *
     * @return
     */
    private boolean rangeCheck(float x) {
        return x >= unSelectRailRectF.left
                && x <= unSelectRailRectF.right;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRail(canvas);
        drawThumb(canvas);
    }

    private void drawRail(Canvas canvas) {
        //背景
        railPaint.setColor(unSelectColor);
        canvas.drawRoundRect(unSelectRailRectF, 15, 15, railPaint);
        //选中部分
        railPaint.setColor(selectColor);
        canvas.drawRoundRect(selectRailRectF, 15, 15, railPaint);
        //画刻度
        railPaint.setColor(Color.WHITE);
        for (Float scale : scaleArray) {
            canvas.drawRect(scale - scaleWidth / 2, unSelectRailRectF.top,
                    scale + scaleWidth / 2, unSelectRailRectF.bottom, railPaint);
        }
    }

    private void drawThumb(Canvas canvas) {
        float top = (this.getMeasuredHeight() - thumbsBitmap.getHeight()) / 2;
        canvas.drawBitmap(thumbsBitmap.extractAlpha(thumbPaint, null), thumb.x - thumbsBitmap.getWidth() / 2,
                top, thumbPaint);//画影子,extractAlpha边缘羽化效果
        canvas.drawBitmap(thumbsBitmap, thumb.x - thumbsBitmap.getWidth() / 2, top, thumbPaint);//画手柄
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);

        switch (widthSpec) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY: {
                break;
            }
            case MeasureSpec.AT_MOST: {
                width = thumbsBitmap.getWidth() * 10;
            }
        }

        switch (heightSpec) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY: {
                break;
            }
            case MeasureSpec.AT_MOST: {
                height = thumbsBitmap.getHeight();
            }
        }
        //设置本控件大小
        setMeasuredDimension(width, height);
        //测量计算轨道高度宽度
        measureRailRectf(widthMeasureSpec, heightMeasureSpec);
        //初始化刻度
        initScale();
        //初始化手柄位置
        initThumbPosition();
    }

    private void measureRailRectf(int widthMeasureSpec, int heightMeasureSpec) {
        if (unSelectRailRectF == null) {
            unSelectRailRectF = new RectF();
        }

        unSelectRailRectF.left = this.getPaddingLeft() + thumbsBitmap.getWidth() / 2;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY: {
                unSelectRailRectF.top = (this.getMeasuredHeight() - thumbsBitmap.getHeight()) / 2 + thumbsBitmap.getHeight() / 3;
                break;
            }
            case MeasureSpec.AT_MOST: {
                unSelectRailRectF.top = thumbsBitmap.getHeight() / 3;
            }
        }

        unSelectRailRectF.bottom = unSelectRailRectF.top + thumbsBitmap.getHeight() / 3;
        unSelectRailRectF.right = this.getMeasuredWidth() - this.getPaddingRight() - thumbsBitmap.getWidth() / 2 - SHADOW_OFFSET;

        if (selectRailRectF == null) {
            selectRailRectF = new RectF(unSelectRailRectF);
        }
        selectRailRectF.right = selectRailRectF.left;
    }

    private void initThumbPosition() {
        if (thumb == null) {
            thumb = new PointF();
        }
        thumb.x = selectRailRectF.right;
        thumbsBitmap = thumbsBitmapDisable;//此时图标应为不可用
    }

    private void initScale() {
        if (scaleArray == null) {
            scaleArray = new float[2];
        }
        //60%
        scaleArray[0] = getPercentageX(0.6f);
        //90%
        scaleArray[1] = getPercentageX(0.9f);
    }

    private float getPercentageX(float percent) {
        percent = percent < 0 ? 0 : percent > 1 ? 1 : percent;
        return (percent * (unSelectRailRectF.right - unSelectRailRectF.left)) + thumbsBitmap.getWidth() / 2;
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
