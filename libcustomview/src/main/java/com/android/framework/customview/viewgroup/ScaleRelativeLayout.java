package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.android.framework.customview.R;

/**
 * Created by meikai on 2017/3/30.
 */
public class ScaleRelativeLayout extends RelativeLayout {

    private float widthScale = 1;
    private float heightScale = 1;
    private String scaleWidthHeight;
    private int spanCount = 3;
    private int spanDivider = 1;

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
        scaleWidthHeight = arr.getString(R.styleable.ScaleRelativeLayout_relative_layout_scale_wh);
        if (!TextUtils.isEmpty(scaleWidthHeight)) {
            String[] scaleWH = scaleWidthHeight.split(":");
            if (scaleWH != null && scaleWH.length == 2) {
                try {
                    widthScale = Integer.parseInt(scaleWH[0]);
                    heightScale = Integer.parseInt(scaleWH[1]);
                } catch (Exception e) {
                }
            }
        }
        spanCount = arr.getInt(R.styleable.ScaleRelativeLayout_relative_layout_span_count, 1);
        spanDivider = arr.getDimensionPixelSize(R.styleable.ScaleRelativeLayout_relative_layout_span_divider, 0);

        if (widthScale <= 0) {
            widthScale = 1;
        }
        if (heightScale <= 0) {
            heightScale = 1;
        }
        arr.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int newHeightMeasureSpec = 0;
        if (spanCount == 1) {
            int height = (int) (widthSize * heightScale / widthScale + 0.5f);
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        } else {
            // 多列，宽度等分屏幕，但高度需要按比例缩放，且多每一项的高度保证相等(可适当调节第一列的高度)
            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            int itemTotalWidth = screenWidth - spanDivider * (spanCount - 1);
            if (itemTotalWidth % spanCount == 0) {
                // 宽度 刚好能 均分，则高度直接按比例计算，就可以保证每一项的高度都相等
                int height = (int) (widthSize * heightScale / widthScale + 0.5f);
                newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            } else {
                // 宽度 不能等分, 需要计算出统一的高度
                int minWidth = itemTotalWidth / spanCount;
                int maxWidth = minWidth + 1;

                // 按 maxWidth 及 对应比例 来计算 统一高度
                int height = (int) (maxWidth * heightScale / widthScale + 0.5f);
                newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }

}

