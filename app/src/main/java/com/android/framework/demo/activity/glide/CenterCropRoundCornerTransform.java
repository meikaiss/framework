package com.android.framework.demo.activity.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

/**
 * Created by meikai on 2019/07/12.
 */
public class CenterCropRoundCornerTransform extends CenterCrop {

    private int radiusPx;

    //指定角 是否 启用 圆角效果，默认全部启用； true:启用，false:禁用； 顺序：左上、右上、右下、左下；
    private boolean[] radiusEnable = {true, true, true, true};

    public CenterCropRoundCornerTransform(int radiusPx) {
        this.radiusPx = radiusPx;
    }

    public CenterCropRoundCornerTransform(int radiusPx, boolean radiusEnable) {
        this.radiusPx = radiusPx;
        this.radiusEnable = new boolean[]{radiusEnable, radiusEnable, radiusEnable, radiusEnable};
    }

    public CenterCropRoundCornerTransform(int radiusPx, boolean[] radiusEnable) {
        this.radiusPx = radiusPx;
        this.radiusEnable = radiusEnable;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
        Bitmap transform = super.transform(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, transform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null)
            return null;
        Bitmap result = pool.get(source.getWidth(), source.getHeight(),
                Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                    Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radiusPx, radiusPx, paint);

        if (radiusPx > 0 && radiusEnable != null && radiusEnable.length == 4) {
            //暂不处理长度小于4的用法，虽然处理显得智能一些

            if (!radiusEnable[0]) {
                canvas.drawRect(0, 0, radiusPx, radiusPx, paint);
            }
            if (!radiusEnable[1]) {
                canvas.drawRect(canvas.getWidth() - radiusPx, 0, canvas.getWidth(), radiusPx, paint);
            }
            if (!radiusEnable[2]) {
                canvas.drawRect(canvas.getWidth() - radiusPx, canvas.getHeight() - radiusPx, canvas.getWidth(), canvas.getHeight(), paint);
            }
            if (!radiusEnable[3]) {
                canvas.drawRect(0, canvas.getHeight() - radiusPx, radiusPx, canvas.getHeight(), paint);
            }
        }

        return result;
    }

}