package com.android.framework.demo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/10/31.
 */

public class TouchFloatActivity extends BaseCompactActivity {


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_touch_float;
    }

    @Override
    public void findViews() {

        findViewById(R.id.float_drag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TouchFloatActivity.this, "点击了", Toast.LENGTH_LONG).show();
            }
        });

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
