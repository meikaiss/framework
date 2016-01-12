package com.android.framework.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

public class CustomGridView extends FrameLayout {
    private int defaultMaxViewCount = 9;//最多可以展示多少个view
    private int cellMarginRight;
    private int cellMarginTop;

    public CustomGridView(Context context) {
        super(context);
        initOnce();
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initOnce();

    }

    private void initOnce() {
        cellMarginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, getContext().getResources().getDisplayMetrics());
        cellMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, getContext().getResources().getDisplayMetrics());
    }


    public void display(List<? extends View> viewList) {
        removeAllViews();
        if (viewList == null || viewList.isEmpty()) {
            return;
        }
        if (viewList.size() > getMaxViewCount()) {
            viewList = viewList.subList(0, getMaxViewCount());
        }
        for (View view : viewList) {
            addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int columnCount = getRowColumnCount();

        int totalMarginGap = ((columnCount - 1) * cellMarginRight);
        totalMarginGap = totalMarginGap > 0 ? totalMarginGap : 0;

        int perWidth = (parentWidth - totalMarginGap) / columnCount;

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(perWidth + MeasureSpec.EXACTLY, perWidth + MeasureSpec.EXACTLY);
        }

        int rowCount = (getChildCount() % columnCount) > 0 ? (getChildCount() / columnCount + 1) : (getChildCount() / columnCount);
        int count = Math.max(0, rowCount - 1);

        int totalHeight = perWidth * rowCount + count * cellMarginTop;

        int marginLeft = 0;
        int marginRight = 0;
        int marginBottom = 0;
        int marginTop = 0;

        if (getLayoutParams() != null) {
            FrameLayout.LayoutParams lp = (LayoutParams) getLayoutParams();
            marginLeft = lp.leftMargin;
            marginRight = lp.rightMargin;
            marginTop = lp.topMargin;
            marginBottom = lp.bottomMargin;
        }

        int appendWidth = marginLeft + marginRight + getPaddingLeft() + getPaddingRight();
        int paddingHeight = marginTop + marginBottom + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(parentWidth + appendWidth + MeasureSpec.EXACTLY, totalHeight + paddingHeight + MeasureSpec.EXACTLY);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int marginLeft = 0;
        int marginTop = 0;

        if (getLayoutParams() != null) {
            FrameLayout.LayoutParams lp = (LayoutParams) getLayoutParams();
            marginLeft = lp.leftMargin;
            marginTop = lp.topMargin;
        }

        int lastTimeX = l + marginLeft + getPaddingLeft();
        int lastTimeY = t + marginTop + getPaddingTop();
        int columnCount = getRowColumnCount();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i != 0 && i % columnCount == 0) {
                lastTimeY += (cellMarginTop + child.getMeasuredHeight()) + marginTop + getPaddingTop();
                lastTimeX = marginLeft + getPaddingLeft();
            }
            child.layout(lastTimeX, lastTimeY, lastTimeX + child.getMeasuredWidth(), lastTimeY + child.getMeasuredHeight());
            lastTimeX += (child.getMeasuredWidth() + cellMarginRight);
        }
    }

    public void setMaxViewCount(int maxViewCount) {
        defaultMaxViewCount = maxViewCount;
    }

    public int getMaxViewCount() {
        return defaultMaxViewCount;
    }

    /**
     * 一行可以展示多少列个View
     */
    protected int getRowColumnCount() {
        return 3;
    }

    public int getCellMarginTop() {
        return cellMarginTop;
    }

    protected int getCellMarginRight() {
        return cellMarginRight;
    }

    protected int getCellMarginTo() {
        return cellMarginTop;
    }

    public void setCellMarginRight(int cellMarginRightPx) {
        this.cellMarginRight = cellMarginRightPx;
    }

    public void setCellMarginTop(int cellMarginTopPx) {
        this.cellMarginTop = cellMarginTopPx;
    }
}
