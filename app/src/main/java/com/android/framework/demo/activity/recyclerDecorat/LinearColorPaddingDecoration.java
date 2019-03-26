package com.android.framework.demo.activity.recyclerDecorat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.framework.demo.R;
import com.android.framework.util.DimenUtil;

/**
 * Created by meikai on 2017/11/07.
 */
public class LinearColorPaddingDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int dividerHeight = 0;
    private Paint dividerPaint;

    private int dp10;
    private int tagWidth;
    private Paint leftPaint;
    private Paint rightPaint;

    public LinearColorPaddingDecoration(Context context) {
        this.context = context;
        dividerPaint = new Paint();
        dividerPaint.setColor(Color.BLUE);

        leftPaint = new Paint();
        leftPaint.setColor(Color.GREEN);
        rightPaint = new Paint();
        rightPaint.setColor(Color.MAGENTA);

        dp10 = DimenUtil.dp2px(context, 10);
        tagWidth = DimenUtil.dp2px(context, 30);

        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.decoration_padding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // 对每一个 item 加一个 内边距，分割线的颜色只能是背景灰色
        outRect.bottom = dividerHeight;
    }

}
