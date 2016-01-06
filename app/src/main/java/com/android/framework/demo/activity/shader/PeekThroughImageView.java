package com.android.framework.demo.activity.shader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by meikai on 16/1/6.
 */
public class PeekThroughImageView extends ImageView {

    private final float radius = 100;
    private Paint paint = null;

    private float x;
    private float y;
    private boolean shouldDrawOpening = false;

    public PeekThroughImageView(Context context) {
        this(context, null);
    }

    public PeekThroughImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PeekThroughImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PeekThroughImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        shouldDrawOpening = (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_MOVE);
        x = motionEvent.getX();
        y = motionEvent.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (paint == null) {
            Bitmap original = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas originalCanvas = new Canvas(original);
            super.onDraw(originalCanvas);    // ImageView

            Shader shader = new BitmapShader(original,
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            paint = new Paint();
            paint.setShader(shader);
        }

        canvas.drawColor(Color.GRAY);
        if (shouldDrawOpening) {
            canvas.drawCircle(x, y - radius/2, radius, paint);
        }
    }
}
