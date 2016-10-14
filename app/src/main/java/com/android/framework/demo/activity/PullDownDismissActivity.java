package com.android.framework.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.android.framework.customview.viewgroup.PullDownDismissFrameLayout;
import com.android.framework.demo.R;


/**
 * Created by meikai on 16/10/14.
 */

public class PullDownDismissActivity extends AppCompatActivity {

    PullDownDismissFrameLayout pullDownDismissFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_down_dismiss);

        pullDownDismissFrameLayout = (PullDownDismissFrameLayout) findViewById(R.id.pull_down_dismiss_root);

        pullDownDismissFrameLayout.setDragListener(new PullDownDismissFrameLayout.DragListener() {
            @Override
            public void onDrag(int deltaY) {

            }

            @Override
            public void onDragFinish() {
                PullDownDismissActivity.this.finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("PullDownActivity", "dispatchTouchEvent _ ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("PullDownActivity", "dispatchTouchEvent _ ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("PullDownActivity", "dispatchTouchEvent _ ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("PullDownActivity", "onTouchEvent _ ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("PullDownActivity", "onTouchEvent _ ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("PullDownActivity", "onTouchEvent _ ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
