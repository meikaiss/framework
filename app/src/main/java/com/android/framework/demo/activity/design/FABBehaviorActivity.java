package com.android.framework.demo.activity.design;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.design.FixedRecyclerView;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/4/9.
 */
public class FABBehaviorActivity extends BaseCompactActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    FixedRecyclerView fixedRecyclerView;

    List<String> dataList = new ArrayList<>();

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_behavior;
    }

    @Override
    public void findViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA);

        fixedRecyclerView = (FixedRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FABBehaviorActivity.this.getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 5000l);
            }
        });
    }

    @Override
    public void parseBundle() {

    }

    @Override
    public void afterView() {
        for (int i = 0; i < 100; i++)
            dataList.add(i + "");

        fixedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fixedRecyclerView.addItemDecoration(new ItemDivider(this, R.drawable.item_decoration));
        fixedRecyclerView.setAdapter(new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View root = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
                ThisViewHolder viewHolder = new ThisViewHolder(root);

                return viewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                ((ThisViewHolder) holder).textView.setText(String.valueOf(position));

            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }

            class ThisViewHolder extends RecyclerView.ViewHolder {

                public TextView textView;

                public ThisViewHolder(View itemView) {
                    super(itemView);
                    textView = (TextView) itemView.findViewById(android.R.id.text1);
                }
            }
        });
    }

    class ItemDivider extends RecyclerView.ItemDecoration {

        private Drawable mDrawable;

        public ItemDivider(Context context, int resId) {
            //在这里我们传入作为Divider的Drawable对象
            mDrawable = context.getResources().getDrawable(resId);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                //以下计算主要用来确定绘制的位置
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int position, RecyclerView parent) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicWidth());
        }
    }
}
