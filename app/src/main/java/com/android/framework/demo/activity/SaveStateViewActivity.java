package com.android.framework.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.framework.demo.R;
import com.android.framework.demo.activity.nolib.AppBarLayoutActivity;
import com.android.framework.demo.activity.nolib.RippleActivity;
import com.android.framework.view.SaveStateView;

/**
 * Created by meikai on 15/12/26.
 */
public class SaveStateViewActivity extends AppCompatActivity {

    LinearLayout rootLayout;
    SaveStateView saveStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_save_state_view);

        rootLayout = (LinearLayout) findViewById(R.id.root_layout);

        saveStateView = (SaveStateView) rootLayout.getChildAt(0);
        saveStateView.setDrawString("hehe");

//        SaveStateView  saveStateView = new SaveStateView(this);
//        saveStateView.setDrawString("mike");
//
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        this.addContentView(saveStateView, lp);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaveStateViewActivity.this, RippleActivity.class);
                SaveStateViewActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("SaveStateViewActivity","SaveStateViewActivity__onSaveInstanceState111");
        super.onSaveInstanceState(outState);
        Log.e("SaveStateViewActivity","SaveStateViewActivity__onSaveInstanceState222");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("SaveStateViewActivity", "SaveStateViewActivity__onRestoreInstanceState");
    }
}
