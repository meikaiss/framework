package com.android.framework.process;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.framework.aidl.R;

/**
 * Created by meikai on 16/3/29.
 */
public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_first);

        Log.e("FirstActivity", "在onCreate中, userId=" + UserManager.userId);

        findViewById(R.id.btn_modify1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.userId = 2;
                Log.e("FirstActivity", "onClick, userId=" + UserManager.userId);
            }
        });

    }


}
