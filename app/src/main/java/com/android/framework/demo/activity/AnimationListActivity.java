package com.android.framework.demo.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.widget.Button;
import android.widget.ImageView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/5/25.
 */
public class AnimationListActivity extends BaseCompactActivity {

    ImageView imageView;
    Button buttonStart;
    Button buttonStop;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_animation_list;
    }

    @Override
    public void findViews() {

        imageView = (ImageView) findViewById(R.id.image_animation);
        buttonStart = (Button) findViewById(R.id.btn_start_animation_list);
        buttonStop = (Button) findViewById(R.id.btn_stop_animation_list);

    }

    @Override
    public void setListeners() {

        buttonStart.setOnClickListener(v -> {

            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
            animationDrawable.start();
        });

        buttonStop.setOnClickListener(v -> {

            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
            animationDrawable.stop();
        });

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }


}
