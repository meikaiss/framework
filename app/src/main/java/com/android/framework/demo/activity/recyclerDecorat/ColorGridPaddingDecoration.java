package com.android.framework.demo.activity.recyclerDecorat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.framework.util.DensityUtil;
import com.android.framework.util.LogUtil;

/**
 * Created by meikai on 2017/11/07.
 */
public class ColorGridPaddingDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int dividerDimen = 0;
    private Paint dividerPaint;

    public ColorGridPaddingDecoration(Context context) {
        this.context = context;
        dividerPaint = new Paint();
        dividerPaint.setColor(Color.BLUE);

        dividerDimen = DensityUtil.dp2px(context, 20)/2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View child, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, child, parent, state);

        int pos = parent.getChildAdapterPosition(child);

        if (pos % 2 == 0) {
            // 左边的
            // 对每一个 item 加一个 内边距，分割线的颜色只能是背景灰色
            outRect.right = dividerDimen;
            outRect.bottom = dividerDimen;
        } else {
            // 右边的
            // 对每一个 item 加一个 内边距，分割线的颜色只能是背景灰色
            outRect.left = dividerDimen;
            outRect.bottom = dividerDimen;
        }
    }

    /**
     * 绘制 item 外围(指getItemOffsets中偏移的区域), 不会覆盖 item内容
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft() + dividerDimen;
        int right = parent.getWidth() - parent.getPaddingRight() - dividerDimen;

        for (int i = 0; i < childCount - 1; i++) {

            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerDimen;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }

        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            LogUtil.e("pos=" + pos);

            if (pos % 2 == 0) {
                int left0 = parent.getPaddingLeft() + child.getWidth();
                int top0 = child.getTop();
                int right0 = left0 + dividerDimen;
                int bottom0 = top0 + child.getHeight();
                c.drawRect(left0, top0, right0, bottom0, dividerPaint);
            } else {

            }
        }
    }

}
