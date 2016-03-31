package com.android.framework.demo.activity.ndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.framework.demo.R;
import com.android.framework.ndk.NdkTest;

/**
 * Created by meikai on 16/3/30.
 */
public class NDKTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_test);

        TextView textView = (TextView) findViewById(R.id.tv_ndk_string);

        textView.setText(NdkTest.getStringInNDK());

        TextView textView2 = (TextView) findViewById(R.id.tv_ndk_encrypt);

        textView2.setText(NdkTest.encryptString("meikai"));

    }
}
