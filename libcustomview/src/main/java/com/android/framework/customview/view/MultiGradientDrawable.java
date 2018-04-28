package com.android.framework.customview.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 垂直方向的多段渐变Drawable
 * Created by meikai on 2018/01/11.
 */
public class MultiGradientDrawable extends Drawable {

    /**
     * 用法用例
     *
     * List<Integer> colorList = new ArrayList<>();
     * colorList.add(0x00000000);
     * colorList.add(0x1a000000);
     * colorList.add(0x33000000);
     * colorList.add(0x4D000000);
     *
     * List<Float> fracList = new ArrayList<>();
     * fracList.add(0f);
     * fracList.add(1 / 3f);
     * fracList.add(2 / 3f);
     * fracList.add(1f);
     * MultiGradientDrawable drawable = new MultiGradientDrawable(colorList, fracList);
     * botLayoutViewMode.setBackground(drawable);
     */

    private List<Integer> colorList;
    private List<Float> fracList;

    private Paint paint;

    private List<Rect> rectList = new ArrayList<>();
    private List<Shader> shaderList = new ArrayList<>();


    public MultiGradientDrawable(List<Integer> colorList, List<Float> fracList) {
        this.colorList = colorList;
        this.fracList = fracList;

        paint = new Paint();
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        for (int i = 0; i < rectList.size(); i++) {
            paint.setShader(shaderList.get(i));
            canvas.drawRect(rectList.get(i), paint);
        }
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        rectList.clear();
        shaderList.clear();

        for (int i = 0; i < this.colorList.size() - 1; i++) {

            Rect rect = new Rect();
            rect.left = left;
            rect.right = right;

            if (i == 0) {
                rect.bottom = (int) (bottom * fracList.get(i + 1) + 0.5f);
                rectList.add(rect);
            } else {
                rect.top = rectList.get(i - 1).bottom;
                rect.bottom = (int) (bottom * fracList.get(i + 1) + 0.5f);
                rectList.add(rect);
            }

            //因为是垂直渐变，因此第三个参数也是left
            Shader shader = new LinearGradient(rect.left, rect.top, rect.left, rect.bottom,
                    new int[]{colorList.get(i), colorList.get(i + 1)},
                    null, Shader.TileMode.CLAMP);
            shaderList.add(shader);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}