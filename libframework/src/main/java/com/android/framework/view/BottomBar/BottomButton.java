package com.android.framework.view.BottomBar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by meikai on 15/11/29.
 */
public class BottomButton extends View {
    //选中时的颜色
    public static final Integer DEFAULT_SELECT_COLOR = 0xff3190e8;
    //未选中时的颜色
    public static final Integer DEFAULT_UNSELECT_COLOR = 0xffb6b6b6;
    //点击间隔时间毫秒
    public static final Integer CLICK_INTERVAL = 200;

    //上下文
    private Context context = null;
    //摁扭的下标
    private Integer index = null;
    //摁扭是否被点击
    private boolean check = false;
    //标签
    private Object tag = null;
    //内存中的图片
    private Bitmap mBitmap;
    //画布
    private Canvas mCanvas;
    //图标画笔
    private Paint mBmpPaint;
    //透明度 0.0-1.0
    private float mAlpha = 0f;
    //图标
    private Bitmap mIconBitmap;
    private Bitmap mIconBitmap2;
    //图标的范围
    private Rect mIconRect;
    //图标下的文本
    private String mText;
    //设置字体大小,转换成sp
    private int mTextSize;
    //文字画笔
    private Paint mTextPaint;
    //文字的范围
    private Rect mTextBound = new Rect();
    //图片大小阀值
    private Integer imgThreshold = 0;
    //文字距离图片的距离
    private Integer textMarginImg = 0;
    //选中后字的颜色
    private Integer mSelectColor = DEFAULT_SELECT_COLOR;
    //未选中字的颜色
    private Integer mUnSelectColor = DEFAULT_UNSELECT_COLOR;

    public BottomButton(Context c, Bitmap bitmap, String text) {
        super(c);
        this.context = c;
        this.mIconBitmap = bitmap;
        this.mText = text;
        init();
    }

    public BottomButton(Context c, Bitmap bitmap, Bitmap bitmap2, String text) {
        this(c, bitmap, text);
        mIconBitmap2 = bitmap2;
    }

    private void init() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        param.weight = 1;
        this.setLayoutParams(param);
        this.setPadding(10, 10, 10, 10);
        mTextPaint = new Paint();

        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mUnSelectColor);
        // 得到text绘制范围
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(mIconBitmap2 == null)
            onDrawWithOne(canvas);
        else
            onDrawWithTwo(canvas);
    }

    private void onDrawWithTwo(Canvas canvas){
        int alpha = (int) Math.ceil((255 * mAlpha));
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mBmpPaint = new Paint();
        mBmpPaint.setColor(Color.RED);
        mBmpPaint.setAntiAlias(true);
        mBmpPaint.setDither(true);
        mBmpPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mBmpPaint);
        mBmpPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mBmpPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mBmpPaint);


        mBmpPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        mCanvas.drawBitmap(mIconBitmap2, null, mIconRect, mBmpPaint);



        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void onDrawWithOne(Canvas canvas){
        int alpha = (int) Math.ceil((255 * mAlpha));
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        setupTargetBitmap(alpha);
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mBmpPaint = new Paint();
        mBmpPaint.setColor(Color.RED);
        mBmpPaint.setAntiAlias(true);
        mBmpPaint.setDither(true);
        mBmpPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mBmpPaint);
        mBmpPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mBmpPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mBmpPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mUnSelectColor);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height() + textMarginImg, mTextPaint);
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mSelectColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height() + textMarginImg, mTextPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //阀值,用来控制图片的大小
        int threshold = imgThreshold;
        // 得到绘制icon的宽
        int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
                - getPaddingRight() - threshold, getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom() - mTextBound.height() - threshold);

        int left = getMeasuredWidth() / 2 - bitmapWidth / 2;
        int top = (getMeasuredHeight() - mTextBound.height()) / 2 - bitmapWidth
                / 2;
        // 设置icon的绘制范围
        mIconRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);

    }

    /**
     * 设置遮罩层透明度
     * @param alpha 0.0 - 1.0
     */
    public void setBtnAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void setTextMarginImg(Integer textMarginImg) {
        this.textMarginImg = textMarginImg;
    }

    public void setImgThreshold(Integer imgThreshold) {
        this.imgThreshold = imgThreshold;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void setCheck(boolean check) {
        this.check = check;
        if (this.check) {
            setBtnAlpha(1.0f);
        } else {
            setBtnAlpha(0.0f);
        }
    }

    public void setCheckWithoutAlpha(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public Integer getmSelectColor() {
        return mSelectColor;
    }

    public void setSelectColor(Integer mSelectColor) {
        this.mSelectColor = mSelectColor;
    }

    public Integer getmUnSelectColor() {
        return mUnSelectColor;
    }

    public void setUnSelectColor(Integer mUnSelectColor) {
        this.mUnSelectColor = mUnSelectColor;
    }


}
