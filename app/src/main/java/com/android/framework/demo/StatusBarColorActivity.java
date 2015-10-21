package com.android.framework.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.framework.compat.StatusBarCompat;

/**
 * Created by meikai on 15/10/21.
 */
public class StatusBarColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_statusbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        // 5.0 以上版本 第二参数 会 覆盖colorPrimaryDark的值
        StatusBarCompat.compat(this, Color.parseColor("#BF29F7"));

    }

}
