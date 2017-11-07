package com.android.framework.demo.activity.recyclerDecorat;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.framework.base.BaseCompactActivity;
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

    @Override
    public void afterView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new Color2GridPaddingDecoration(this));

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add("测试, position=" + i);
        }

        adapter = new RecyclerAdapter(dataList);
        recyclerView.setAdapter(adapter);

    }
}
