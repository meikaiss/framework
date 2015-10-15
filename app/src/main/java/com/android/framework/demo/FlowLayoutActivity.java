package com.android.framework.demo;

import android.app.Activity;
import android.os.Bundle;



public class FlowLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);

        this.setTitle(this.getClass().getSimpleName());
    }
}
