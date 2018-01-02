package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.android.framework.customview.R;


/**
 * Created by meikai on 2017/3/30.
 */
public class ScaleRelativeLayout extends RelativeLayout {

    private float widthScale = 1;
    private float heightScale = 1;

    public ScaleRelativeLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ScaleRelativeLayout, defStyleAttr,
                defStyleRes);
        widthScale = arr.getFloat(R.styleable.ScaleRelativeLayout_relative_layout_width_scale, 1f);
        heightScale = arr.getFloat(R.styleable.ScaleRelativeLayout_relative_layout_height_scale, 1f);
        if (widthScale == 0) {
            widthScale = 1;
        }
        if (heightScale == 0) {
            heightScale = 1;
        }
        arr.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (widthSize * heightScale / widthScale + 0.5f);

        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }

}
