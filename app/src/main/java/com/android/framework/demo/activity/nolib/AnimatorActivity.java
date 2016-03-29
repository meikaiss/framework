package com.android.framework.demo.activity.nolib;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.framework.demo.R;

/**
 * Created by meikai on 16/3/29.
 */
public class AnimatorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        imageViewAvatar = (ImageView) findViewById(R.id.img_avatar);

        findViewById(R.id.btn_ObjectAnimator).setOnClickListener(this);
        findViewById(R.id.btn_PropertyValuesHolder).setOnClickListener(this);
        findViewById(R.id.btn_ObjectAnimator_Group).setOnClickListener(this);
        findViewById(R.id.btn_AnimatorSet).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ObjectAnimator:
                start1();
                break;
            case R.id.btn_PropertyValuesHolder:
                start2();
                break;
            case R.id.btn_ObjectAnimator_Group:
                start3();
                break;
            case R.id.btn_AnimatorSet:
                start4();
                break;
            default:
                break;
        }
    }

    private void start1() {
        ObjectAnimator.ofFloat(imageViewAvatar, "rotationX", 0f, 360f).setDuration(1000).start();
    }

    private void start2() {
        PropertyValuesHolder pvhRotateY = PropertyValuesHolder.ofFloat("rotationY", 0f, 360f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);

        ObjectAnimator.ofPropertyValuesHolder(imageViewAvatar, pvhRotateY, pvhAlpha,
                pvhScaleX, pvhScaleY).setDuration(1000).start();
    }

    private void start3() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageViewAvatar, "", 1f, 0f, 1f);
        objectAnimator.setDuration(1000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                imageViewAvatar.setAlpha(value);
                imageViewAvatar.setScaleX(value);
                imageViewAvatar.setScaleY(value);
                imageViewAvatar.setTranslationX((1 - value) * 300);
            }
        });
        objectAnimator.start();
    }

    private void start4() {
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageViewAvatar, "rotationX", 0f, 360f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageViewAvatar, "translationX", 0, 300f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(imageViewAvatar, "rotationY", 0f, 360f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(imageViewAvatar, "translationX", 300f, 0f);

        AnimatorSet animatorSetAfter = new AnimatorSet();
        animatorSetAfter.play(objectAnimator3).with(objectAnimator4);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator2).before(animatorSetAfter);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }
}
