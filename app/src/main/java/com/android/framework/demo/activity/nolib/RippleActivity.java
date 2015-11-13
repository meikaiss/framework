package com.android.framework.demo.activity.nolib;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.framework.demo.R;

/**
 * Created by meikai on 15/11/13.
 */
public class RippleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
    }
}
