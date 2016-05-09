package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.framework.customview.viewgroup.NestGridView;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/5/9.
 */
public class SmartGridViewActivity extends AppCompatActivity {

    NestGridView gridView;
    GridView gridView2;
    List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_grid_view);

        for (int i = 0; i < 38; i++) {
            dataList.add(i + "");
        }

        gridView = (NestGridView) findViewById(R.id.my_grid_view);
        gridView2 = (GridView) findViewById(R.id.my_grid_view_2);
        gridView.setAdapter(adapter);
        gridView2.setAdapter(adapter);

    }


    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nest_grid_view_item, null);

            ((TextView) (convertView.findViewById(R.id.btn_grid_item))).setText(dataList.get(position) + "-");

//                convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
//
//                ((TextView) (convertView.findViewById(android.R.id.text1))).setText(dataList.get(position) + "-");

            return convertView;
        }
    };


}
