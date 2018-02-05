package com.android.framework.customview.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.framework.util.DensityUtil;

/**
 * 垂直方向 的 RecyclerView 分割线
 * Created by meikai on 2017/11/22.
 */
public class ListDividerDecoration extends RecyclerView.ItemDecoration {

    private int marginLeftRight = 0;

    private int dividerHeight = 0;

    private Paint dividerPaint;

    public ListDividerDecoration(Context context) {
        marginLeftRight = DensityUtil.dp2px(context, 12);
        dividerHeight = 1; //  分割线高度 固定为 1px

        dividerPaint = new Paint();
        dividerPaint.setColor(0xffeeeeee);
    }

    @Override
    public void getItemOffsets(Rect outRect, View child, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, child, parent, state);

        int pos = parent.getChildAdapterPosition(child);

        if (pos > 0) {
            // 如果第0项是 下拉刷新View， 可以在这里做特殊处理
            // 但不需要在 onDraw 里再针对 第0项 特殊处理，canvas 超出范围后会自动忽略或覆盖
        }

        // 对每一个 item 加一个 内边距，分割线的颜色只能是背景灰色
        outRect.bottom = dividerHeight;
    }

    /**
     * 绘制 item 外围, 不会覆盖 item内容
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft() + marginLeftRight;
        int right = parent.getWidth() - parent.getPaddingRight() - marginLeftRight;

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;

            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }

}
