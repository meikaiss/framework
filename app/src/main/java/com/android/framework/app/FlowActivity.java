package com.android.framework.app;

import android.app.Activity;
import android.os.Bundle;


public class FlowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flowlayout);
    }
}
