package com.android.framework.demo.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/5/25.
 */
public class LayoutAnimationActivity extends BaseCompactActivity {

    private ListView listView;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;


    private Animation animation;
    private LayoutAnimationController controller;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_layout_animation;
    }

    @Override
    public void findViews() {

        listView = (ListView) findViewById(R.id.list_view);

        dataList = new ArrayList<>();


        adapter = new ArrayAdapter<>(this, R.layout.item_animation_list, R.id.textview1, dataList);
        listView.setAdapter(adapter);


    }

    @Override
    public void setListeners() {

        findViewById(R.id.btn_anim_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = new TranslateAnimation(0f, 200f, 0f, 0f);
                animation.setDuration(100);
                animation.setFillAfter(true);

                controller = new LayoutAnimationController(animation, 1f);
                controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
                listView.setLayoutAnimation(controller);
                adapter.notifyDataSetInvalidated();

                for (int i = 0; i < 30; i++) {
                    dataList.add(i + "");
                }
            }
        });

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }

}
