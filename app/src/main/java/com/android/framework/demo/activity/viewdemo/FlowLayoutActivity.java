package com.android.framework.demo.activity.viewdemo;

import android.app.Activity;
import android.os.Bundle;

import com.android.framework.demo.R;


public class FlowLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);

        this.setTitle(this.getClass().getSimpleName());
    }
}
