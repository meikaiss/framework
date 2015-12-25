package com.android.framework.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.framework.demo.R;
import com.android.framework.viewgroup.swiprefresh.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meikai on 15/12/24.
 */
public class SwipRefreshLayoutActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swip_refresh);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip_refresh_layout);
        listView = (ListView) findViewById(R.id.list_view);

        final List<Map<String, String>> dataList = new ArrayList<>();

        dataList.addAll(getDataList());

        final SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.item_demo, new String[]{"name"}, new int[]{R.id.tv_item_name});
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.e("", "onRefresh开始刷新");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        dataList.addAll(getDataList());
                        adapter.notifyDataSetChanged();

                        Toast.makeText(SwipRefreshLayoutActivity.this, "加载完成", Toast.LENGTH_SHORT).show();

                    }
                }, 0l);

            }
        });

        swipeRefreshLayout.setRefreshing(true);

    }

    private List<Map<String, String>> getDataList(){

        List<Map<String, String>> dataList = new ArrayList<>();

        for (int i = 0 ; i  < 4 ; i ++){
            Map<String, String> map = new HashMap<>();
            map.put("name", "meikai"+i);
            map.put("age", "25");
            dataList.add(map);
        }
        return dataList;
    }



}
