package com.android.framework.demo.activity.recyclerDecorat;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.framework.demo.R;

/**
 * Created by meikai on 2017/11/07.
 */
public class SimplePaddingDecoration extends RecyclerView.ItemDecoration {

    private int padding = 0;

    public SimplePaddingDecoration(Context context) {
        padding = context.getResources().getDimensionPixelSize(R.dimen.decoration_padding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);


        // 对每一个 item 加一个 内边距，分割线的颜色只能是背景灰色
        outRect.bottom = padding;
    }

}
