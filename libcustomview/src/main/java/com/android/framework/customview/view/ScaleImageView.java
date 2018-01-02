package com.android.framework.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.android.framework.customview.R;


/**
 * Created by meikai on 2017/10/19.
 */
public class ScaleImageView extends AppCompatImageView {

    private float widthScale = 1;
    private float heightScale = 1;

    public ScaleImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView, defStyleAttr, defStyleRes);
        widthScale = arr.getFloat(R.styleable.ScaleImageView_width_scale, 1f);
        heightScale = arr.getFloat(R.styleable.ScaleImageView_height_scale, 1f);
        if (widthScale == 0) {
            widthScale = 1;
        }
        if (heightScale == 0) {
            heightScale = 1;
        }
        arr.recycle();
    }

    /**
     * 重新设置宽高比
     */
    public void setAspectRatio(float widthScale, float heightScale) {
        this.widthScale = widthScale;
        this.heightScale = heightScale;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (widthSize * heightScale / widthScale + 0.5f);

        this.setMeasuredDimension(widthSize, height);
    }
}
