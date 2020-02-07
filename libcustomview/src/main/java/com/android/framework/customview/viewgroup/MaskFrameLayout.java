package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
public class MaskFrameLayout extends FrameLayout {

    private Paint paint;
    private PorterDuffXfermode xfermode;

    private Drawable maskDrawable;
    private Bitmap maskBmp;


    public MaskFrameLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MaskFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MaskFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaskFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaskFrameLayout);
        maskDrawable = typedArray.getDrawable(R.styleable.MaskFrameLayout_mask_drawable);
        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        /**
         * 注：必须开启硬件加速，否则不能正常工作。（在关闭硬件加速的情况下：本应显示透明的部分却显示了黑色）
         * 虽然可能在某些机型上存在不生效的风险，但尚未找到其它解决方案。目前在小米、vivo上测试没有发现问题
         */
        setLayerType(LAYER_TYPE_HARDWARE, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        createMaskBmp();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (maskBmp != null) {
            paint.setXfermode(xfermode);
            canvas.drawBitmap(maskBmp, 0, 0, paint);
        }
    }

    private void createMaskBmp() {
        if (maskDrawable instanceof NinePatchDrawable) {
            maskBmp = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(maskBmp);
            maskDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            maskDrawable.draw(canvas);
        } else if (maskDrawable instanceof BitmapDrawable) {
            maskBmp = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(maskBmp);
            maskDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            maskDrawable.draw(canvas);  //当maskDrawable宽高和canvas宽高不一致时，系统竟然会自动缩放，不过这正是我所需要的；if else暂时保留
        } else if (maskDrawable instanceof GradientDrawable) {
            maskBmp = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(maskBmp);
            maskDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            maskDrawable.draw(canvas); //似乎各分支是一样，暂时不合并
        } else {
            //其它类型的 drawable 尚未支持，有需要时再扩展
        }
    }


}
