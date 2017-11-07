package com.android.framework.demo.activity.recyclerDecorat;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.itemDecoration.GridSpacingItemDecoration;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 2017/11/07.
 */
public class RecyclerViewActivity extends BaseCompactActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.recycler_view_activity;
    }

    @Override
    public void findViews() {
        recyclerView = f(R.id.recycler_view);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    private RecyclerView.ItemDecoration itemDecoration;

    @Override
    public void afterView() {

        f(R.id.btn_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        refresh();
    }

    private void refresh() {
        int spanCount = (int) (Math.random() * 5) + 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RecyclerViewActivity.this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        int spaceDp = 50; // 设计稿上两个item之间的距离，单位:dp

        if (itemDecoration != null) {
            recyclerView.removeItemDecoration(itemDecoration);
        }

        itemDecoration = new GridSpacingItemDecoration(RecyclerViewActivity.this, spanCount, spaceDp);
        recyclerView.addItemDecoration(itemDecoration);

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add("测试, position=" + i);
        }

        adapter = new RecyclerAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

}
