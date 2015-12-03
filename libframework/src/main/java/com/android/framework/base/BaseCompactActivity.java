package com.android.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by meikai on 15/10/29.
 */
public abstract class BaseCompactActivity extends AppCompatActivity implements BaseAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutId());

        findViews();
        setListeners();
        parseBundle();
    }


}
