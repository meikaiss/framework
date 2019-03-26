package com.android.framework.customview.itemDecoration;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.framework.util.DimenUtil;

/**
 * Created by meikai on 2017/12/28.
 */
public class GridSpaceTopItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int dividerWidth;
    private int dividerWidthTopFirstLine;
    private int dividerWidthTop;
    private int dividerWidthBot;

    private Paint dividerPaint;
    private IDataProvider iDataProvider;

    /**
     * @param dividerWidthDp 分割块宽高,单位:dp
     */
    public GridSpaceTopItemDecoration(Context context, int spanCount, int dividerWidthDp, IDataProvider iDataProvider) {
        this.spanCount = spanCount;

        this.dividerPaint = new Paint();
        this.dividerPaint.setColor(Color.BLUE);

        this.dividerWidth = DimenUtil.dp2px(context, dividerWidthDp);
        this.dividerWidthTop = dividerWidth / 2;
        this.dividerWidthBot = dividerWidth - dividerWidthTop;

        //第一行 距离 顶部 的 padding
        int paddingTopDp = 0;
        this.dividerWidthTopFirstLine = DimenUtil.dp2px(context, paddingTopDp);

        this.iDataProvider = iDataProvider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View child, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, child, parent, state);

        int pos = parent.getChildAdapterPosition(child) - 1; // -1 是因为 下拉刷新的动画 占了一个位置

        if (pos < 0 || pos >= iDataProvider.getTotal() || iDataProvider.getTotal() == 0) {
            // 非业务层的 pos 不需要处理
            return;
        }

        int column = pos % spanCount;

        boolean isFirstLine = pos < spanCount;

        int total = iDataProvider.getTotal();
        int countInLastLine = (total % spanCount) == 0 ? spanCount : (total % spanCount);//最后一行的 内容总个数
        boolean isLastLine = (total - (pos + 1)) <= countInLastLine; // +1是因为从0开始

        outRect.top = isFirstLine ? dividerWidthTopFirstLine : dividerWidthTop;
        outRect.bottom = isLastLine ? 0 : dividerWidthBot;

        outRect.left = (column * dividerWidth / spanCount);
        outRect.right = dividerWidth - (column + 1) * dividerWidth / spanCount;

    }

    public interface IDataProvider {
        int getTotal();
    }

}
