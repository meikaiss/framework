package com.android.framework.demo.activity.design;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/5/17.
 */
public class FlexboxActivity extends BaseCompactActivity implements View.OnClickListener {


    ImageView imageView;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_flex_box;
    }

    @Override
    public void findViews() {
        imageView = (ImageView) findViewById(R.id.img_magic);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:

                imageView.setImageResource(R.drawable.toutiao__news_ic_video_selected);

                PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f);
                PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
                PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);

                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhAlpha,
                        pvhScaleX, pvhScaleY).setDuration(300);
                objectAnimator.setInterpolator(new DecelerateInterpolator());
                objectAnimator.start();


                break;
        }
    }
}
