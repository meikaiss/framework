package com.android.framework.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.framework.demo.R;
import com.android.framework.view.explosion.ExplosionView;

/**
 * Created by meikai on 15/10/15.
 */
public class ExplosionActivity extends Activity implements View.OnClickListener{

    ExplosionView explosionView;

    ImageView imageView01;
    ImageView imageView02;
    ImageView imageView03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explosion);

        this.setTitle(this.getClass().getSimpleName());

        explosionView = ExplosionView.attach2Window(this);

        imageView01 = (ImageView)findViewById(R.id.imageview_01);
        imageView02 = (ImageView)findViewById(R.id.imageview_02);
        imageView03 = (ImageView)findViewById(R.id.imageview_03);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageview_01:
            case R.id.imageview_02:
            case R.id.imageview_03:
                explosionView.explode(v);
                v.setOnClickListener(null);
                break;
            case R.id.btn_reset:
                imageView01.setScaleX(1.0f);
                imageView01.setScaleY(1.0f);
                imageView01.setAlpha(1.0f);
                imageView01.setOnClickListener(this);

                imageView02.setScaleX(1.0f);
                imageView02.setScaleY(1.0f);
                imageView02.setAlpha(1.0f);
                imageView02.setOnClickListener(this);

                imageView03.setScaleX(1.0f);
                imageView03.setScaleY(1.0f);
                imageView03.setAlpha(1.0f);
                imageView03.setOnClickListener(this);
                break;
        }
    }

}


