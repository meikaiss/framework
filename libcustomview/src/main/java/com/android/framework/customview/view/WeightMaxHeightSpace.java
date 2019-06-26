package com.android.framework.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.android.framework.customview.R;

/**
 * 布局规则：
 * 1、所有的weight控件会先按 weight 的方式以各自权重来分配宽高
 * 2、若权重分配的宽高超过 maxHeight 时，最终会以 maxHeight 来生效
 * 3、maxHeight 并不会影响 weight 权重来分配宽高的计算方式
 * 4、带weight控件的父布局若为 wrap，则此weight会让父布局撑到最大
 * Created by meikai on 2019/06/26.
 */
public class WeightMaxHeightSpace extends View {

    private int maxHeight = 0;

    public WeightMaxHeightSpace(Context context) {
        super(context);
        init(context, null, 0);
    }

    public WeightMaxHeightSpace(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public WeightMaxHeightSpace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray typedValue = context.obtainStyledAttributes(attrs, R.styleable.weight_max_height_space);

        maxHeight = (int) (typedValue.getDimension(R.styleable.weight_max_height_space_maxHeight, 0f) + 0.5f);

        typedValue.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (maxHeight > 0 && getMeasuredHeight() > maxHeight) {
            int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
            setMeasuredDimension(getMeasuredWidth(), newHeightMeasureSpec);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // do nothing
    }


}
