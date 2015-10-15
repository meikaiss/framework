package com.android.framework.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by meikai on 15/10/14.
 */
public class MainActivity extends Activity {

    private ListView listView;
    private Class<?>[] classes = {FlowLayoutActivity.class, ScrollLayoutActivity.class, ExplosionActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addContentView(listView, lp);


        ArrayAdapter<Class<?>> adapter = new ArrayAdapter<Class<?>>(this, R.layout.item_demo, R.id.tv_activity_name, classes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);

    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Class<?> className = (Class<?>)parent.getAdapter().getItem(position);
            MainActivity.this.startActivity(new Intent(MainActivity.this, className));
        }
    };

}
