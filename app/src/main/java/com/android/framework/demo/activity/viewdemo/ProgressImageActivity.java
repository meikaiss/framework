package com.android.framework.demo.activity.viewdemo;

import android.content.Intent;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.view.ProgressImageView;
import com.android.framework.demo.R;

/**
 * Created by meikai on 2020/03/18.
 */
public class ProgressImageActivity extends BaseCompactActivity {

    private ProgressImageView progressImageView;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_progress_image;
    }

    @Override
    public void findViews() {

        progressImageView = findViewById(R.id.img_cai_progress);


        /**
         * 注意 ProgressImageView 的 宽高需要 等于 src 圆环的半径x2
         */
        progressImageView.setSweepAngle(90);

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }

}
