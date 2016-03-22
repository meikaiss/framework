package com.android.framework.customview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.framework.customview.R;

/**
 * Created by meikai on 16/2/3.
 */
public class FuseImageView extends ImageView {

    private Bitmap bitmapDefault;
    private Bitmap bitmapFused;

    private Paint bitmapPaint;

    private int fusedAlpha = 0;

    public void setFusedAlpha(int fusedAlpha) {
        this.fusedAlpha = fusedAlpha;
        invalidate();
    }

    public FuseImageView(Context context) {
        this(context, null);
    }

    public FuseImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FuseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FuseImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FuseImageView, defStyleAttr, 0);
        @DrawableRes int bitmapDefaultDrawableId = a.getResourceId(R.styleable.FuseImageView_drawableDefault, 0);
        @DrawableRes int bitmapFusedDrawableId = a.getResourceId(R.styleable.FuseImageView_drawableFused, 0);
        a.recycle();

        bitmapDefault = BitmapFactory.decodeResource(this.getResources(), bitmapDefaultDrawableId);
        bitmapFused = BitmapFactory.decodeResource(this.getResources(), bitmapFusedDrawableId);

        bitmapPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(bitmapDefault.getWidth(), bitmapDefault.getHeight());
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        bitmapPaint.setAlpha(255 - fusedAlpha);
        canvas.drawBitmap(bitmapDefault, 0, 0, bitmapPaint);
        bitmapPaint.setAlpha(fusedAlpha);
        canvas.drawBitmap(bitmapFused, 0, 0, bitmapPaint);

    }


}
