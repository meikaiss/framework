package com.android.framework.demo.activity.fragmentT;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.framework.demo.R;
import com.android.framework.util.LogUtil;

/**
 * 测试Fragment中 startActivityForResult 后，此Fragment和它所在的Activity能否onActivityResult，且request是否正确
 * Created by meikai on 2017/11/07.
 */
public class AActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, AFragment.newInstance())
                .commitAllowingStateLoss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("AActivity.onActivityResult, requestCode=" + requestCode);
    }

}
