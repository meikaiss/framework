package com.android.framework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.framework.aidl.ClientActivity;
import com.android.framework.aidl.R;
import com.android.framework.process.MultiProcessActivity;

/**
 * Created by meikai on 16/3/29.
 */
public class AidlMainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_main);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_multi_process) {

            Intent intent = new Intent(this, MultiProcessActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.btn_aidl_one_way) {

            Intent intent = new Intent(this, ClientActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.btn_aidl_two_way) {


        }
    }

}
