package com.android.framework.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.android.framework.customview.R;
import com.android.framework.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 价格区间选择
 */
public class PriceRangeView extends View {

    private static final String TAG = "PriceRangeView";

    private boolean initedPrivates = false;
    private boolean dirty = true;

    private Paint paint;

    private int shadowBubbleHeight = DensityUtil.dp2px(getContext(), 30);
    private int bubbleHeight = DensityUtil.dp2px(getContext(), 20);
    private int bubbleMargin = DensityUtil.dp2px(getContext(), 0);
    private int numberHeight = DensityUtil.dp2px(getContext(), 14);
    private int scaleAreaHeight = DensityUtil.dp2px(getContext(), 40); //刻度区域高度
    private int scaleAreaPadding = DensityUtil.dp2px(getContext(), 20);
    private int scaleAreaOffset = DensityUtil.dp2px(getContext(), 30);
    private int scaleUnitHeight = DensityUtil.dp2px(getContext(), 10); //刻度高度
    private int scaleIntegerHeight = DensityUtil.dp2px(getContext(), 20); // 刻度整数高度
    private int xMainHeight = DensityUtil.dp2px(getContext(), 5);
    private int scaleWidth = DensityUtil.dp2px(getContext(), 1); //刻度粗细
    private int scaleUnitCount = 0;
    private int scaleCount = 0;

    private float xMainStartX;
    private float xMainWidth;

    private boolean preLeftSliding = false;
    private boolean preRightSliding = false;
    private float x, lastX, y;
    private PointF slideLeftLocation;
    private Bitmap bitmapLeft;
    private PointF slideRightLocation;
    private Bitmap bitmapRight;
    private float slidingMin, slidingMax;
    private int[] scaleUnit = new int[]{5, 10, 15, 20, 25, 30, 35, 50, 70, 100, 150};
    //private int[] values = new int[96];
    private List<ValuePixelMap> valuePixelMaps = new ArrayList<ValuePixelMap>();
    private float unitWidth;

    private Bitmap bitmapBubbleLeft, bitmapBubbleRight;

    private int rangeValueMin = 1; //选择区域最小2万

    private OnValueChangedListener onValueChangedListener;

    private volatile  int minValue = 4;
    private volatile int maxValue = 151;

    private int slideRangeAreaOffset = DensityUtil.dp2px(getContext(), 20);//滑块点击区域偏移

    private static final int OFFSET_Y = 90;

    public PriceRangeView(Context context) {
        super(context);
        init();
    }

    public PriceRangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceRangeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        bitmapLeft = BitmapFactory.decodeResource(getResources(), R.drawable.bj__ic_slide_left);
        bitmapRight = BitmapFactory.decodeResource(getResources(), R.drawable.bj__ic_slide_right);

        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f);
        bitmapBubbleRight = bitmapBubbleLeft = Bitmap.createBitmap(bitmapRight, 0, 0,
                bitmapRight.getWidth(),bitmapRight.getHeight(), matrix, true);

        bubbleHeight = bitmapRight.getHeight();
        shadowBubbleHeight = bitmapBubbleRight.getHeight();

        paint = new Paint();

        slideLeftLocation = new PointF(0f, 0f);
        slideRightLocation = new PointF(0f, 0f);

    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * x轴粗线
     */
    private void toXMainPaint() {
        paint.reset();
        paint.setColor(Color.parseColor("#CCCCCC"));
        paint.setStrokeWidth(xMainHeight);
    }

    /**
     * 刻度线
     */
    private void toScalePaint() {
        paint.reset();
        paint.setColor(Color.parseColor("#B5B5B5"));
        paint.setStrokeWidth(scaleWidth);
    }

    /**
     * x轴粗线-选择区域
     */
    private void toXMainSelectPaint() {
        paint.reset();
        paint.setColor(Color.parseColor("#FF4649"));
        paint.setStrokeWidth(xMainHeight);
    }

    /**
     * 刻度-选择区域
     */
    private void toScaleSelectPaint() {
        paint.reset();
        paint.setColor(Color.parseColor("#FF4649"));
        paint.setStrokeWidth(scaleWidth);
    }

    /**
     * 刻度文字
     */
    private void toScaleTextPaint() {
        paint.reset();
        paint.setColor(Color.parseColor("#32373B"));
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.price_range_view_scale_text_size));
        paint.setAntiAlias(true);
    }

    /**
     * 气泡文字
     */
    private void toValueTextPaint() {
        paint.reset();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.price_range_view_bubble_text_size));
        paint.setAntiAlias(true);
    }

    private void toShadowSlide() {
        paint.reset();
        paint.setAlpha(128);
    }

    private void toValueTextShadowPaint() {
        paint.reset();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.price_range_view_scale_text_size));
        paint.setAntiAlias(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!initedPrivates) {
            scaleUnitCount = scaleUnit.length;
            scaleCount = (scaleUnitCount - 1) * 5 + 1;

            xMainStartX = getPaddingLeft() + scaleAreaPadding;
            xMainWidth = getWidth() - getPaddingLeft() - getPaddingRight() - scaleAreaPadding * 2;
            float slideStartY = getPaddingTop() + bubbleHeight + bubbleMargin + numberHeight + scaleAreaHeight + OFFSET_Y;

            slideLeftLocation.set(xMainStartX - bitmapLeft.getWidth() * 0.5f, slideStartY);
            slideRightLocation.set(xMainStartX + xMainWidth - bitmapRight.getWidth() * 0.5f, getPaddingTop() + OFFSET_Y);
            slidingMin = slideLeftLocation.x;
            slidingMax = slideRightLocation.x;

            unitWidth = (xMainWidth - scaleAreaOffset * 2) / (scaleUnitCount - 1);
            ValuePixelMap lastValuePixelMap = null;
            for (int i = 0; i < scaleUnitCount; i++) {
                if (i == 0) {
                    ValuePixelMap valuePixelMap = new ValuePixelMap(4, xMainStartX, xMainStartX + scaleAreaOffset, 0);
                    valuePixelMaps.add(valuePixelMap);
                    lastValuePixelMap = valuePixelMap;
                } else {
                    //float startX = xMainStartX + scaleAreaOffset + unitWidth * i;
                    int valueRange = scaleUnit[i] - scaleUnit[i - 1];
                    float pixelRange = unitWidth;
                    float valuePixelRange = pixelRange / valueRange;
                    for (int m = 0; m < valueRange; m++) {
                        ValuePixelMap valuePixelMap = new ValuePixelMap(scaleUnit[i - 1] + m, lastValuePixelMap.getEndX(), lastValuePixelMap.getEndX() + valuePixelRange, valuePixelRange);
                        valuePixelMaps.add(valuePixelMap);
                        lastValuePixelMap = valuePixelMap;
                    }
                }
            }
            ValuePixelMap lastValuePixel = new ValuePixelMap(150, lastValuePixelMap.getEndX(), lastValuePixelMap.getEndX() + lastValuePixelMap.getRange(), lastValuePixelMap.getRange());
            valuePixelMaps.add(lastValuePixel);
            lastValuePixelMap = lastValuePixel;
            lastValuePixelMap = new ValuePixelMap(151, lastValuePixelMap.getEndX(), xMainStartX + xMainWidth, 0);
            valuePixelMaps.add(lastValuePixelMap);

            initedPrivates = true;
        }

        if (dirty) {
            setSlideLocationForValue(this.minValue, this.maxValue);
            dirty = false;
        }
        float xMainStartY = getPaddingTop() + bubbleHeight + bubbleMargin + numberHeight + scaleAreaHeight * 0.5f + OFFSET_Y;

        //刻度
        toScalePaint();
        float scaleWidth = (xMainWidth - scaleAreaOffset * 2) / (scaleCount - 1);
        for (int i = 0; i < scaleCount; i++) {
            float scaleHeight = scaleUnitHeight;
            if (i % 5 == 0)
                scaleHeight = scaleIntegerHeight;
            float scaleStartX = xMainStartX + scaleAreaOffset + scaleWidth * i;
            float scaleStartY = xMainStartY - scaleHeight * 0.5f;
            canvas.drawLine(scaleStartX, scaleStartY, scaleStartX, scaleStartY + scaleHeight, paint);
        }
        //单位刻度文字
        toScaleTextPaint();
        for (int i = 0; i < scaleUnitCount; i++) {
            float startX = xMainStartX + scaleAreaOffset + unitWidth * i;
            float startY = getPaddingTop() + bubbleHeight + bubbleMargin + OFFSET_Y;
            float textWidth = paint.measureText(scaleUnit[i] + "");
            canvas.drawText(scaleUnit[i] + "", startX - textWidth * 0.5f, startY + numberHeight, paint);
        }

        //主x轴
        toXMainPaint();
        canvas.drawLine(xMainStartX, xMainStartY, xMainStartX + xMainWidth, xMainStartY, paint);

        //左右滑块
        canvas.drawBitmap(bitmapLeft, slideLeftLocation.x, slideLeftLocation.y, paint);
        canvas.drawBitmap(bitmapRight, slideRightLocation.x, slideRightLocation.y, paint);

        //选择区域
        toXMainSelectPaint();
        canvas.drawLine(getSlideLeftPostion(), xMainStartY, getSlideRightPostion(), xMainStartY, paint);
        toScaleSelectPaint();
        for (int i = 0; i < scaleCount; i++) {
            float scaleHeight = scaleUnitHeight;
            if (i % 5 == 0)
                scaleHeight = scaleIntegerHeight;
            float scaleStartX = xMainStartX + scaleAreaOffset + scaleWidth * i;
            if (scaleStartX >= getSlideLeftPostion() && scaleStartX <= getSlideRightPostion()) {
                float scaleStartY = xMainStartY - scaleHeight * 0.5f;
                canvas.drawLine(scaleStartX, scaleStartY, scaleStartX, scaleStartY + scaleHeight, paint);
            }
        }

        toValueTextPaint();

        String valueStr = getValueStr(minValue);
        float textWidth = paint.measureText(valueStr);
        Rect bound = new Rect();
        paint.getTextBounds(valueStr, 0, 1, bound);
        canvas.drawText(valueStr, getSlideLeftPostion() - textWidth * 0.5f, slideLeftLocation.y + (bubbleHeight * 0.8f - bound.height()) * 0.5f + bound.height() + bubbleHeight * 0.15f, paint);

        valueStr = getValueStr(maxValue);
        textWidth = paint.measureText(valueStr);
        bound = new Rect();
        paint.getTextBounds(valueStr, 0, 1, bound);
        canvas.drawText(valueStr, getSlideRightPostion() - textWidth * 0.5f, slideRightLocation.y + (bubbleHeight * 0.8f - bound.height()) * 0.5f + bound.height(), paint);

        toShadowSlide();
        if (preLeftSliding) {
            canvas.drawBitmap(bitmapBubbleLeft, slideLeftLocation.x - (bitmapBubbleLeft.getWidth() - bitmapLeft.getWidth()) * 0.5f, slideLeftLocation.y - shadowBubbleHeight * 1.1f, paint);

            toValueTextShadowPaint();
            valueStr = getValueStr(getValueByPixcelForLeft());
            textWidth = paint.measureText(valueStr);
            bound = new Rect();
            paint.getTextBounds(valueStr, 0, 1, bound);
            canvas.drawText(valueStr, getSlideLeftPostion() - textWidth * 0.5f, slideLeftLocation.y + (bubbleHeight * 0.8f - bound.height()) * 0.5f + bound.height() - (shadowBubbleHeight * 1.1f + bubbleHeight) * 0.5f, paint);
        } else if (preRightSliding) {
            canvas.drawBitmap(bitmapBubbleRight, slideRightLocation.x - (bitmapBubbleRight.getWidth() - bitmapRight.getWidth()) * 0.5f, slideRightLocation.y - shadowBubbleHeight * 1.1f, paint);

            toValueTextShadowPaint();
            valueStr = getValueStr(getValueByPixcelForRight());

            textWidth = paint.measureText(valueStr);
            bound = new Rect();
            paint.getTextBounds(valueStr, 0, 1, bound);
            canvas.drawText(valueStr, getSlideRightPostion() - textWidth * 0.5f, slideRightLocation.y + (bubbleHeight * 0.8f - bound.height()) * 0.5f + bound.height() - (shadowBubbleHeight * 1.1f + bubbleHeight) * 0.5f, paint);
        }
    }

    private float getSlideLeftPostion() {
        return slideLeftLocation.x + bitmapLeft.getWidth() * 0.5f;
    }

    private float getSlideRightPostion() {
        return slideRightLocation.x + bitmapRight.getWidth() * 0.5f;
    }

    /**
     * 通过位置获取左滑块值
     *
     * @return
     */
    private int getValueByPixcelForLeft() {
        for (ValuePixelMap valuePixelMap : valuePixelMaps) {
            if (getSlideLeftPostion() >= valuePixelMap.getStartX() && getSlideLeftPostion() < valuePixelMap.getEndX()) {
                return valuePixelMap.getValue();
            }
        }
        return 999;
    }

    /**
     * 设置左滑块在它的刻度上
     */
    private void setSlideLeftPostionStart() {
        int value = getValueByPixcelForLeft();
        for (ValuePixelMap valuePixelMap : valuePixelMaps) {
            if (valuePixelMap.getValue() == value) {
                slideLeftLocation.x = valuePixelMap.getStartX() - bitmapLeft.getWidth() * 0.5f;
                break;
            }
        }
    }

    public void setSlideLocationForValue(int minValue, int maxValue) {
        for (ValuePixelMap valuePixelMap : valuePixelMaps) {
            if (valuePixelMap.getValue() == minValue) {
                slideLeftLocation.x = valuePixelMap.getStartX() - bitmapLeft.getWidth() * 0.5f;
                break;
            }
        }
        for (ValuePixelMap valuePixelMap : valuePixelMaps) {
            if (valuePixelMap.getValue() == maxValue) {
                slideRightLocation.x = valuePixelMap.getStartX() - bitmapRight.getWidth() * 0.5f + scaleWidth/2;
            }
        }
        if (slideLeftLocation.x < slidingMin + scaleAreaOffset) {
            slideLeftLocation.x = slidingMin;
        }
        if (slideRightLocation.x > slidingMax - scaleAreaOffset) {
            slideRightLocation.x = slidingMax;
        }
        this.minValue = getValueByPixcelForLeft();
        this.maxValue = getValueByPixcelForRight();
//        invalidate();
    }

    /**
     * 通过位置获取右滑块值
     *
     * @return
     */
    private int getValueByPixcelForRight() {
        for (ValuePixelMap valuePixelMap : valuePixelMaps) {
            if (getSlideRightPostion() > valuePixelMap.getStartX() && getSlideRightPostion() < valuePixelMap.getEndX()) {
                return valuePixelMap.getValue();
            }
        }
        return 999;
    }

    /**
     * 设置右滑块在它的刻度值上
     */
    private void setSlideRightPostionStart() {
        int value = getValueByPixcelForRight();
        for (ValuePixelMap valuePixelMap : valuePixelMaps) {
            if (valuePixelMap.getValue() == value) {
                slideRightLocation.x = valuePixelMap.getStartX() - bitmapRight.getWidth() * 0.5f + scaleWidth;
            }
        }
    }

    /**
     * 根据间隔调整左滑块
     */
    private void adjustSlideLeftLocation() {
        if (getValueByPixcelForRight() - getValueByPixcelForLeft() < rangeValueMin) {
            int newLeftValue = getValueByPixcelForLeft() - (rangeValueMin - (getValueByPixcelForRight() - getValueByPixcelForLeft()));
            for (ValuePixelMap valuePixelMap : valuePixelMaps) {
                if (valuePixelMap.getValue() == newLeftValue) {
                    slideLeftLocation.x = valuePixelMap.getStartX() - bitmapLeft.getWidth() * 0.5f;
                    break;
                }
            }
        }
    }

    /**
     * 根据间隔调整右滑块
     */
    private void adjustSlideRightLocation() {
        if (getValueByPixcelForRight() - getValueByPixcelForLeft() < rangeValueMin) {
            int newRightValue = getValueByPixcelForRight() + (rangeValueMin - (getValueByPixcelForRight() - getValueByPixcelForLeft()));
            for (ValuePixelMap valuePixelMap : valuePixelMaps) {
                if (valuePixelMap.getValue() == newRightValue) {
                    slideRightLocation.x = valuePixelMap.getEndX() + bitmapRight.getWidth() * 0.5f;
                    break;
                }
            }
        }
    }

//    private float getPostionByValue(int value) {
//        for (ValuePixcelMap valuePixcelMap : valuePixelMaps) {
//            if (valuePixcelMap.getValue() == value) {
//                return valuePixcelMap.getEndX() - bitmapRight.getWidth() * 0.5f;
//            }
//        }
//        return 0;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            return super.dispatchTouchEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preLeftSliding = inSlideLeftRect(x, y);
                preRightSliding = inSlideRightRect(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (preLeftSliding || preRightSliding) {
                    float deltaX = x - lastX;
                    if (preLeftSliding) {
                        if (slideLeftLocation.x + deltaX >= slidingMin && slideLeftLocation.x + deltaX <= slidingMax - scaleAreaOffset + 1) {
                            slideLeftLocation.x = slideLeftLocation.x + deltaX;
                            adjustSlideRightLocation();
                            invalidate();
                            lastX = x;
                            onValueChanging();
                            //告诉ViewPager不要拦截此控件的触摸事件
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                            return true;
                        }
                    }
                    if (preRightSliding) {
                        if (slideRightLocation.x + deltaX >= slidingMin + scaleAreaOffset && slideRightLocation.x + deltaX <= slidingMax) {
                            slideRightLocation.x = slideRightLocation.x + deltaX;
                            adjustSlideLeftLocation();
                            invalidate();
                            lastX = x;
                            onValueChanging();
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (preLeftSliding) {
                    if (slideLeftLocation.x < slidingMin + scaleAreaOffset) {
                        slideLeftLocation.x = slidingMin;
                    } else {
                        setSlideLeftPostionStart();
                    }
                }
                if (preRightSliding) {
                    if (slideRightLocation.x > slidingMax - scaleAreaOffset) {
                        slideRightLocation.x = slidingMax;
                    } else {
                        setSlideRightPostionStart();
                    }
                }
                preLeftSliding = false;
                preRightSliding = false;
                minValue = getValueByPixcelForLeft();
                maxValue = getValueByPixcelForRight();
                onValueChanged();
                invalidate();
                break;
            default:
                break;
        }
        lastX = x;
        return true;
    }

    /**
     * 是否点击在左滑块上
     *
     * @param x
     * @param y
     * @return
     */
    private boolean inSlideLeftRect(float x, float y) {
        if ( slideLeftLocation == null || bitmapLeft == null ){
            return false;
        }
        return x >= slideLeftLocation.x - slideRangeAreaOffset && x <= slideLeftLocation.x + bitmapLeft.getWidth() + slideRangeAreaOffset
                && y >= slideLeftLocation.y - slideRangeAreaOffset && y <= slideLeftLocation.y + bitmapLeft.getHeight() + slideRangeAreaOffset;
    }

    /**
     * 是否点击在右滑块上
     *
     * @param x
     * @param y
     * @return
     */
    private boolean inSlideRightRect(float x, float y) {
        if ( bitmapRight == null ){
            return false;
        }
        return x >= slideRightLocation.x - slideRangeAreaOffset && x <= slideRightLocation.x + bitmapRight.getWidth() + slideRangeAreaOffset
                && y >= slideRightLocation.y - slideRangeAreaOffset && y <= slideRightLocation.y + bitmapRight.getHeight() + slideRangeAreaOffset;
    }

    public String getValueStr(int value) {
        if (value < 5)
            return "0";
        else if (value > 150) {
            return "150+";
        } else {
            return String.valueOf(value);
        }
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    private void onValueChanging() {
        if (this.onValueChangedListener != null) {
            String valueDesc = "不限";
            if (getValueByPixcelForLeft() < 5 && getValueByPixcelForRight() <= 150) {
                valueDesc = getValueByPixcelForRight() + "万以下";
            } else if (getValueByPixcelForLeft() >= 5 && getValueByPixcelForRight() > 150) {
                valueDesc = getValueByPixcelForLeft() + "万以上";
            } else if (getValueByPixcelForLeft() >= 5 && getValueByPixcelForRight() <= 150) {
                valueDesc = getValueByPixcelForLeft() + "-" + getValueByPixcelForRight() + "万";
            }
            minValue = getValueByPixcelForLeft();
            maxValue = getValueByPixcelForRight();
            this.onValueChangedListener.onValueChanging(minValue, maxValue, valueDesc);
        }
    }

    private void onValueChanged() {
        if (this.onValueChangedListener != null) {
            String valueDesc = "不限";
            if (minValue < 5 && maxValue <= 150) {
                valueDesc = maxValue + "万以下";
            } else if (minValue >= 5 && maxValue > 150) {
                valueDesc = getValueByPixcelForLeft() + "万以上";
            } else if (minValue >= 5 && maxValue <= 150) {
                valueDesc = minValue + "-" + maxValue + "万";
            }
//            minValue = getValueByPixcelForLeft();
//            maxValue = getValueByPixcelForRight();
//            LogUtils.i("rangeView","onValueChanged=>"+maxValue+"");
            this.onValueChangedListener.onValueChanged(minValue, maxValue, valueDesc);
        }
    }

    public void reset() {
        slideLeftLocation.x = slidingMin;
        slideRightLocation.x = slidingMax;
        invalidate();
        onValueChanging();
    }


    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public boolean isReset() {
        return minValue == 4 && maxValue >= 151 ? true : false;
    }

    public interface OnValueChangedListener {
        void onValueChanging(int min, int max, String valueDesc);

        void onValueChanged();

        void onValueChanged(int min, int max, String valueDesc);
    }

    private class ValuePixelMap {
        int value;
        float startX;
        float endX;
        float range;

        private ValuePixelMap() {
        }

        private ValuePixelMap(int value, float startX, float endX, float range) {
            this.value = value;
            this.startX = startX;
            this.endX = endX;
            this.range = range;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public float getStartX() {
            return startX;
        }

        public void setStartX(float startX) {
            this.startX = startX;
        }

        public float getEndX() {
            return endX;
        }

        public void setEndX(float endX) {
            this.endX = endX;
        }

        public float getRange() {
            return range;
        }

        public void setRange(float range) {
            this.range = range;
        }

        public String getValueStr() {
            if (value < 5)
                return "5-";
            else if (value > 150) {
                return "150+";
            } else {
                return String.valueOf(value);
            }
        }
    }
}
