package com.android.framework.demo.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.android.framework.demo.R;

/**
 * Created by meikai on 2020/04/02.
 */
public class BreatheActivity extends AppCompatActivity {

    ImageView imgBreathe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_activity);

        imgBreathe = findViewById(R.id.img_breathe);

        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void start() {
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.2f, 1.0f);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.2f, 1.0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imgBreathe, xHolder, yHolder);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);

        animator.start();


    }
}
