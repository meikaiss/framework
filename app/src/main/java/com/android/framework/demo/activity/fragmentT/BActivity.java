package com.android.framework.demo.activity.fragmentT;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.framework.demo.R;

/**
 * Created by meikai on 2017/11/07.
 */
public class BActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity);
    }


}
