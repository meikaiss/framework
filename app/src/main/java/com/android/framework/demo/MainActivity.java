package com.android.framework.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meikai on 15/10/14.
 */
public class MainActivity extends Activity {

    private ListView listView;
    private Class<?>[] classes = {FlowLayoutActivity.class, ScrollLayoutActivity.class, ExplosionActivity.class,
            NumberSeekBarActivity.class, AppCompatTestActivity.class, StatusBarColorActivity.class,
            NotificationActivity.class, BonuceLoadingActivity.class, CircleLoadingActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addContentView(listView, lp);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_demo, R.id.tv_activity_name, getArray(classes));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);

    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Class<?> className = (Class<?>) parent.getAdapter().getItem(position);

            Class<?> className = classes[position];
            MainActivity.this.startActivity(new Intent(MainActivity.this, className));
        }
    };

    private String[] getArray(Class<?>[] classes){
        List<Class<?>> classList = Arrays.asList(classes);
        List<String> stringList = new ArrayList<>();

        for(int i = 0; i< classList.size() ; i++){
            stringList.add(classList.get(i).getSimpleName());
        }

        return stringList.toArray(new String[stringList.size()]);
    }

}
