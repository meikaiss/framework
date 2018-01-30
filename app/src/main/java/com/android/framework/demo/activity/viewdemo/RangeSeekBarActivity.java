package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.android.framework.customview.view.RangeSeekBar;
import com.android.framework.demo.R;

/**
 * Created by meikai on 2018/01/29.
 */
public class RangeSeekBarActivity extends FragmentActivity {

    private RangeSeekBar rangeSeekBar;
    private TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.range_seek_bar_activity);

        rangeSeekBar = (RangeSeekBar) findViewById(R.id.rang_seek_bar);
        tvResult = findViewById(R.id.tv_result);

        rangeSeekBar.setDuration(20, 80);

        rangeSeekBar.setOnDragChangeListener(new RangeSeekBar.OnDragChangeListener() {
            @Override
            public void onDragChange(long startPosition, long endPosition) {
                tvResult.setText("onDragChange=" + startPosition + "," + endPosition);
            }

            @Override
            public void onDragConfirm(long startPosition, long endPosition) {
                tvResult.setText("onDragConfirm=" + startPosition + "," + endPosition);
            }
        });


    }
}
