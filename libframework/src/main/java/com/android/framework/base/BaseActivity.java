package com.android.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by meikai on 15/10/29.
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutId());

        findViews();
        setListeners();
        parseBundle();
    }

    protected abstract int getContentViewLayoutId();

    protected abstract void findViews();

    protected abstract void setListeners();

    protected abstract void parseBundle();


}
