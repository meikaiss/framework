package com.android.framework.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.android.framework.demo.R;
import com.android.framework.view.BounceLoadingView;


/**
 * Created by meikai on 15/10/22.
 */
public class BonuceLoadingActivity extends Activity {

    BounceLoadingView bounceLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonuce_loading);

        bounceLoadingView = (BounceLoadingView) findViewById(R.id.bounce_loading_view);

    }

    public void startBounce(View view){
        bounceLoadingView.playAnimator();
    }
}
