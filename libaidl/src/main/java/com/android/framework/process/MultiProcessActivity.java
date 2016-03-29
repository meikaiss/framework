package com.android.framework.process;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.framework.aidl.R;

/**
 * Created by meikai on 16/3/29.
 */
public class MultiProcessActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_main);

        findViewById(R.id.btn_start_process_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiProcessActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start_process_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiProcessActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }
}
