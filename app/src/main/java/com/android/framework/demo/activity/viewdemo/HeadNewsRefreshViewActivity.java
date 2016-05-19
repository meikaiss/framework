package com.android.framework.demo.activity.viewdemo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.MotionEvent;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.view.HeadNewsRefreshView;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/5/19.
 */
public class HeadNewsRefreshViewActivity extends BaseCompactActivity {

    private HeadNewsRefreshView headNewsRefreshView;

    private float preY;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_head_news_refresh_view;
    }

    @Override
    public void findViews() {

        headNewsRefreshView = (HeadNewsRefreshView) findViewById(R.id.head_news_refresh_view);

    }

    @Override
    public void setListeners() {

        findViewById(R.id.btn_rest).setOnClickListener(v -> headNewsRefreshView.reset());
    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                headNewsRefreshView.update(event.getY() - preY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                CountDownTimer cdt = new CountDownTimer(3000, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        headNewsRefreshView.updatePosition();
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                cdt.start();
                break;
        }
        return super.onTouchEvent(event);
    }
}
