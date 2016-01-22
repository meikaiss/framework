package com.android.framework.libui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by meikai on 16/1/21.
 */
public class LevelImageView extends ImageView {

    private Bitmap bitmapColor;
    private Bitmap bitmapEmpty;

    private Bitmap bitmapLeft;
    private Bitmap bitmapRight;

    private Rect srcRect;
    private Rect dstRect;

    private Paint paint;

    public LevelImageView(Context context) {
        this(context, null);
    }

    public LevelImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LevelImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        bitmapColor = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.star_color);
        bitmapEmpty = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.star_empty);

        srcRect = new Rect(0, 0, bitmapColor.getWidth(), bitmapColor.getHeight());
        dstRect = new Rect(0, 0, bitmapColor.getWidth(), bitmapColor.getHeight());

        bitmapLeft = Bitmap.createBitmap(bitmapColor.getWidth(), bitmapColor.getHeight(), Bitmap.Config.ARGB_8888);
        bitmapRight = Bitmap.createBitmap(bitmapColor.getWidth(), bitmapColor.getHeight(), Bitmap.Config.ARGB_8888);

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        paint.setDither(true);

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

        float factor = 0.7f;
        int offset = (int) (factor*(srcRect.right-srcRect.left));

        Rect rectLeft = new Rect(srcRect.left, srcRect.top, srcRect.left + offset, srcRect.bottom);
        canvas.drawBitmap(bitmapColor, rectLeft, rectLeft, paint);

        Rect rectRight = new Rect(srcRect.left + offset, srcRect.top, srcRect.right, srcRect.bottom);
        canvas.drawBitmap(bitmapEmpty, rectRight, rectRight, paint);

    }
}
