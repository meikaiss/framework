package com.android.framework.demo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by meikai on 16/11/17.
 */
public class ListViewActivity extends BaseCompactActivity {

    ListView listView;

    List<HashMap<String, String>> dataList;

    HashMap<TestObject, String> dataList2;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_list_view;
    }

    @Override
    public void findViews() {
        listView = f(R.id.list_view);

        dataList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "test");
            map.put("age", "____" + i);
            dataList.add(map);
        }

        TextView tv = new TextView(this);
        tv.setBackgroundColor(Color.RED);
        tv.setText("头部");

        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 7000);
        tv.setLayoutParams(lp);

        listView.addHeaderView(tv);
        listView.setAdapter(new SimpleAdapter(this, dataList, R.layout.item_list_view_activity,
                new String[]{"name", "age"}, new int[]{R.id.tv_name, R.id.tv_age}));

        f(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listView.smoothScrollBy(5000, 400);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    listView.smoothScrollBy(5000, 0);
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListViewActivity", "onScrollStateChanged, scrollState=" + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("ListViewActivity", "onScroll, firstVisibleItem=" + firstVisibleItem);
            }
        });

        dataList2 = new HashMap<>();
        TestObject object1 = new TestObject();
        object1.name = "梅凯";
        object1.sort = 10;
        dataList2.put(object1, "测试能不能找的到");

        object1.sort = 20;
        // 测试hashCode 是否会改变
        if (dataList2.get(object1) != null) {
            Toast.makeText(this, "不为空", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "为空", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }
}
