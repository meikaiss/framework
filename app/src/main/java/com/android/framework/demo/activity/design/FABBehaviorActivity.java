package com.android.framework.demo.activity.design;

import android.graphics.Color;
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

import com.android.framework.customview.design.FixedRecyclerView;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/4/9.
 */
public class FABBehaviorActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    FixedRecyclerView fixedRecyclerView;

    List<String> dataList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher);
        fixedRecyclerView = (FixedRecyclerView) findViewById(R.id.recyclerView);

        for (int i = 0; i < 100; i++)
            dataList.add(i + "");

        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA);

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

        fixedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}
