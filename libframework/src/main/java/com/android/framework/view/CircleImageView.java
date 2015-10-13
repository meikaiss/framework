package com.android.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.framework.R;


/**
 * Created by meikai on 15/10/13.
 */
public class CircleImageView extends ImageView {


    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private int mDrawableWidth;
    private int mDrawableRadius;

    private int mBorderWidth = 8;
    private int mBorderRadius;
    private int mBorderColor = DEFAULT_BORDER_COLOR;

    private BitmapShader mBitmapShader;
    private Matrix mMatrix;
    private Paint mBitmapPaint;
    private Paint mBorderPaint;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_BorderWidth, DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(R.styleable.CircleImageView_BorderColor, DEFAULT_BORDER_COLOR);
        a.recycle();

        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDrawableWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mDrawableRadius = mDrawableWidth / 2;
        mBorderRadius = mDrawableRadius - mBorderWidth/2;
        setMeasuredDimension(mDrawableWidth, mDrawableWidth);

        initBitmapShader();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mDrawableRadius, mDrawableRadius, mDrawableRadius, mBitmapPaint);
        canvas.drawCircle(mDrawableRadius, mDrawableRadius, mBorderRadius, mBorderPaint);
    }

    void initBitmapShader() {

        Drawable drawable = getDrawable();

        Bitmap bitmap = drawable2Bitmap(drawable);

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        int minSize = Math.min(bitmap.getWidth(), bitmap.getHeight());

        float scale = mDrawableWidth * 1.0f / minSize;

        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);
    }

    public Bitmap drawable2Bitmap(Drawable drawable){

        if(drawable instanceof BitmapDrawable){
            return  ((BitmapDrawable)drawable).getBitmap();
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap resultBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        drawable.draw(canvas);

        return resultBitmap;
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }
}

