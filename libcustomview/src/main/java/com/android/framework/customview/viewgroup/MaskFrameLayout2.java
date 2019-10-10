package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.android.framework.customview.R;

/**
 * 蒙板容器，对容器内的所有子view按蒙板的形状进行裁切
 * Created by meikai on 2019/09/18.
 */
public class MaskFrameLayout2 extends FrameLayout {

    private Paint paint;
    private PorterDuffXfermode xfermode;

    private Drawable maskDrawable;
    private Bitmap maskBmp;


    public MaskFrameLayout2(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MaskFrameLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MaskFrameLayout2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaskFrameLayout2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaskFrameLayout2);
        maskDrawable = typedArray.getDrawable(R.styleable.MaskFrameLayout2_mask_drawable2);
        typedArray.recycle();

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

    @Override
    protected void dispatchDraw(Canvas canvas) {
        /**
         * 新建立一个layer层，之后所有的绘制都在这个图层上，这样xfermode才能操作合并图形
         */
        int saveCount = canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);

        if (maskBmp != null) {
            paint.setXfermode(xfermode);
            canvas.drawBitmap(maskBmp, 0, 0, paint);
        }
        canvas.restoreToCount(saveCount);
    }

    private void createMaskBmp() {
        if (maskDrawable instanceof NinePatchDrawable) {
            maskBmp = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(maskBmp);
            maskDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            maskDrawable.draw(canvas);
        }else{
            //其它类型的 drawable 尚未支持，有需要时再扩展
            //注意考虑普通 drawable 生成的 bitmap与 容器宽度不一致的场景
        }
    }


}
