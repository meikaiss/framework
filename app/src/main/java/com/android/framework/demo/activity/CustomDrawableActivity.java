package com.android.framework.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.android.framework.customview.view.MultiGradientDrawable;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 2018/01/11.
 */
public class CustomDrawableActivity extends AppCompatActivity {

    private FrameLayout testFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawable);

        testFrameLayout = findViewById(R.id.test_layout);

        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Integer> colorList = new ArrayList<>();
                colorList.add(Color.RED);
                colorList.add(Color.BLACK);
                colorList.add(Color.BLUE);

                List<Float> fracList = new ArrayList<>();
                fracList.add(0f);
                fracList.add(0.4f);
                fracList.add(1.0f);

                MultiGradientDrawable drawable = new MultiGradientDrawable(colorList, fracList);

                testFrameLayout.setBackground(drawable);
            }
        });

        findViewById(R.id.btn_change_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newHeight = testFrameLayout.getHeight() / 2;
                testFrameLayout.getLayoutParams().height = newHeight;
                testFrameLayout.setLayoutParams(testFrameLayout.getLayoutParams());

            }
        });


    }


}
