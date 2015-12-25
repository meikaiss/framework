package com.android.framework.demo.activity.nolib;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meikai on 15/11/13.
 */
public class RippleActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        listView = (ListView) findViewById(R.id.list_view);

        final List<Map<String, String>> dataList = new ArrayList<>();

        dataList.addAll(getDataList());

        final SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.item_demo, new String[]{"name"}, new int[]{R.id.tv_item_name});
        listView.setAdapter(adapter);
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
