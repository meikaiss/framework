package com.android.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        afterView();
    }

    public <T extends View> T f(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }

    public <T extends View> T f(View view, @IdRes int viewId) {
        return (T) view.findViewById(viewId);
    }

}
