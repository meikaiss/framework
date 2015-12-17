package com.android.framework.demo.activity.nolib;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.framework.demo.R;

/**
 * Created by meikai on 15/12/16.
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);


        final TextView depentent = (TextView) findViewById(R.id.depentent);
        depentent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.offsetTopAndBottom(v, 25);
            }
        });

        final TextView depentent2 = (TextView) findViewById(R.id.depentent2);
        depentent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.offsetTopAndBottom(v, 25);
            }
        });
    }
}
