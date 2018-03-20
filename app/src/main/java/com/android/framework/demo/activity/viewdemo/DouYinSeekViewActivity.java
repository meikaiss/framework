package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.framework.customview.view.DouYinSeekView;
import com.android.framework.demo.R;
import com.android.framework.util.LogUtil;

/**
 * Created by meikai on 2018/03/08.
 */
public class DouYinSeekViewActivity extends AppCompatActivity {

    View target;
    DouYinSeekView douYinSeekView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douyin_seek);

        target = findViewById(R.id.dou_yin_seek_target);

        douYinSeekView = findViewById(R.id.dou_yin_seek);
        douYinSeekView.setTarget(target);
        douYinSeekView.setMaxDuration(4_020_000);

        douYinSeekView.setOnDragListener(new DouYinSeekView.OnDragListener() {
            @Override
            public void onDragMove(long currentTime) {
                LogUtil.e("DouYinSeekView, onDragMove, currentTime=" + currentTime);
            }

            @Override
            public void onDragConfirm(long currentTime) {
                LogUtil.e("DouYinSeekView, onDragConfirm, currentTime=" + currentTime);
            }
        });

    }
}
