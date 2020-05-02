package com.android.framework.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


import com.android.framework.util.DimenUtil;


/**
 * 圆形进度 蒙层ImageView
 * Created by meikai on 2019/09/18.
 */
public class ProgressImageView extends AppCompatImageView {

    private Paint paint;
    private PorterDuffXfermode xfermode;

    private Bitmap maskBmp;

    /**
     * 从顶部的点顺时针扫描的角度
     */
    private float sweepAngle = 360;
    /**
     * 进度圆环的厚度，单位：dp
     */
    private int ringWidth = 8;


    public ProgressImageView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ProgressImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ProgressImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }


    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        createMaskBmp();
    }

    private void createMaskBmp() {
        setSweepAngle(sweepAngle);
    }

    /**
     *
     * @param sweepAngle [0, 360]
     */
    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        if (!isInEditMode() && getMeasuredWidth() <= 0 && getMeasuredHeight() <= 0) {
            return;
        }

        ArcShape shape = new ArcShape(-90, sweepAngle);
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setStrokeWidth(DimenUtil.dp2px(getContext(), ringWidth));
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setDither(true);
        drawable.getPaint().setStyle(Paint.Style.STROKE);
        maskBmp = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(maskBmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 新建立一个layer层，之后所有的绘制都在这个图层上，这样xfermode才能操作合并图形
         */
        int saveCount = canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255, Canvas.ALL_SAVE_FLAG);

        super.onDraw(canvas);

        if (maskBmp != null) {
            paint.setXfermode(xfermode);
            canvas.drawBitmap(maskBmp, 0, 0, paint);
        }
        canvas.restoreToCount(saveCount);
    }


}
