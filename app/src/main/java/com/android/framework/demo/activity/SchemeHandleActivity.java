package com.android.framework.demo.activity;

import android.content.Intent;
import android.net.Uri;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.util.StringUtil;

/**
 * Created by meikai on 16/11/9.
 */

public class SchemeHandleActivity extends BaseCompactActivity {


    @Override
    public int getContentViewLayoutId() {
        return 0;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {
        Uri uri = this.getIntent().getData();
        if(uri != null) {
            String navUrl = uri.getQueryParameter("navUrl");
            if(StringUtil.isNotEmpty(navUrl)) {


                DestinationActivity.start(this, navUrl);
            }
        }

        this.finish();
    }
}
