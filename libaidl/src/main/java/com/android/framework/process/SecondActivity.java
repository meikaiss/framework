package com.android.framework.process;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.framework.aidl.R;

/**
 * Created by meikai on 16/3/29.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_second);

        Log.e("SecondActivity", "在onCreate中, userId=" + UserManager.userId);

        findViewById(R.id.btn_modify2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.userId = 3;
                Log.e("SecondActivity", "onClick, userId=" + UserManager.userId);
            }
        });

    }


}
