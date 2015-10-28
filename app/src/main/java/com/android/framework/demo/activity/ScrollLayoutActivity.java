package com.android.framework.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.framework.demo.R;


/**
 * Created by meikai on 15/10/14.
 */
public class ScrollLayoutActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolllayout);

        this.setTitle(this.getClass().getSimpleName());
    }


}
