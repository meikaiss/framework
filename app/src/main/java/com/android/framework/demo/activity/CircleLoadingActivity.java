package com.android.framework.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.android.framework.demo.R;
import com.android.framework.view.CircleLoadingView;

/**
 * Created by meikai on 15/10/23.
 */
public class CircleLoadingActivity extends Activity {

    CircleLoadingView circleLoadingView;
    CircleLoadingView circleLoadingView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_loading);


        circleLoadingView = (CircleLoadingView) findViewById(R.id.circle_loading_view);
        circleLoadingView2 = (CircleLoadingView) findViewById(R.id.circle_loading_view2);
    }

    public void startLoad(View view){
        circleLoadingView.playAnimator();
        circleLoadingView2.playAnimator();
    }

    public void stopLoad(View view){
        circleLoadingView.cancleAnimator();
        circleLoadingView2.cancleAnimator();
    }

}
