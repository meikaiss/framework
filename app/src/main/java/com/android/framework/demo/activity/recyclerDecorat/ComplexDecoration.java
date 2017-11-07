package com.android.framework.demo.activity.recyclerDecorat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.framework.demo.R;
import com.android.framework.util.DensityUtil;

/**
 * Created by meikai on 2017/11/07.
 */
public class ComplexDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int dividerHeight = 0;
    private Paint dividerPaint;

    private int dp10;
    private int tagWidth;
    private Paint leftPaint;
    private Paint rightPaint;


    public ComplexDecoration(Context context) {
        this.context = context;
        dividerPaint = new Paint();
        dividerPaint.setColor(Color.BLUE);

        leftPaint = new Paint();
        leftPaint.setColor(Color.GREEN);
        rightPaint = new Paint();
        rightPaint.setColor(Color.MAGENTA);

        dp10 = DensityUtil.dp2px(context, 10);
        tagWidth = DensityUtil.dp2px(context, 30);

        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.decoration_padding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

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
        int left = parent.getPaddingLeft() + dp10;
        int right = parent.getWidth() - parent.getPaddingRight() - dp10;

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }

    /**
     * 绘制 item 之上的内容, 会覆盖 item内容
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            boolean isLeft = pos % 2 == 0;
            if (isLeft) {
                float left = child.getLeft();
                float right = left + tagWidth;
                float top = child.getTop();
                float bottom = child.getBottom();
                c.drawRect(left, top, right, bottom, leftPaint);
            } else {
                float right = child.getRight();
                float left = right - tagWidth;
                float top = child.getTop();
                float bottom = child.getBottom();
                c.drawRect(left, top, right, bottom, rightPaint);

            }
        }
    }
}
