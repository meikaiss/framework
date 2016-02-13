package com.android.framework.libui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.framework.libui.R;

/**
 * Created by meikai on 16/1/21.
 */
public class LevelImageView extends ImageView {

    private float level = 1;

    private Bitmap bitmapColor;
    private Bitmap bitmapEmpty;

    private Rect srcRect;
    private Rect rectLeft;
    private Rect rectRight;

    private Paint paint;



    public LevelImageView(Context context) {
        this(context, null);
    }

    public LevelImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LevelImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr){

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LevelImageView, defStyleAttr, 0);
        @DrawableRes int leftDrawable = a.getResourceId(R.styleable.LevelImageView_drawableLeft, 0);
        @DrawableRes int rightDrawable = a.getResourceId(R.styleable.LevelImageView_drawableRight, 0);
        level = a.getFloat(R.styleable.LevelImageView_level, 1);
        a.recycle();

        bitmapColor = BitmapFactory.decodeResource(getContext().getResources(), leftDrawable);
        bitmapEmpty = BitmapFactory.decodeResource(getContext().getResources(), rightDrawable);

        srcRect = new Rect(0, 0, bitmapColor.getWidth(), bitmapColor.getHeight());

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);


        float factor = level;
        int offset = (int) (factor*(srcRect.right-srcRect.left));
        rectLeft = new Rect(srcRect.left, srcRect.top, srcRect.left + offset, srcRect.bottom);
        rectRight = new Rect(srcRect.left + offset, srcRect.top, srcRect.right, srcRect.bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY ){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }else {
            setMeasuredDimension(bitmapColor.getWidth(), bitmapColor.getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmapColor, rectLeft, rectLeft, paint);

        canvas.drawBitmap(bitmapEmpty, rectRight, rectRight, paint);

    }
}
