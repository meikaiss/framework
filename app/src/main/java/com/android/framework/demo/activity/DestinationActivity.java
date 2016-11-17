package com.android.framework.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/11/9.
 */

public class DestinationActivity extends BaseCompactActivity {


    public static void start(Activity activity, String navUrl) {
        Intent intent = new Intent(activity, DestinationActivity.class);
        intent.putExtra("navUrl", navUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_destination;
    }

    @Override
    public void findViews() {

        TextView tv = (TextView) findViewById(R.id.tv_param);
        tv.setText(getIntent().getStringExtra("navUrl"));

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }
}
