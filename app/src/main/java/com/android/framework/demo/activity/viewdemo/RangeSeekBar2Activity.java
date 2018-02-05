package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.android.framework.customview.view.VideoClipSeekBar;
import com.android.framework.demo.R;

/**
 * Created by meikai on 2018/01/29.
 */
public class RangeSeekBar2Activity extends FragmentActivity {

    private TextView tvResult;
    private VideoClipSeekBar rangSeekBar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.range_seek_bar_2_activity);

        tvResult = findViewById(R.id.tv_result);

        rangSeekBar2 = findViewById(R.id.rang_seek_bar2);

        rangSeekBar2.attachTarget(findViewById(R.id.horizontal_scroll_view));

        rangSeekBar2.setOnSelectListener(new VideoClipSeekBar.OnSelectListener() {
            @Override
            public void onMove(float left, float right) {
                tvResult.setText("onMove:  left=" + left + ", right=" + right);
            }

            @Override
            public void onConfirm(float left, float right) {
                tvResult.setText("onConfirm:  left=" + left + ", right=" + right);
            }
        });

    }
}
