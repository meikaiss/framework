package com.android.framework.demo.activity.shareelement;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 2020/01/07.
 */
public class Share1Activity extends BaseCompactActivity {

    ImageView imageView1;
    ImageView imageView2;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.share_ele_1_activity;
    }

    @Override
    public void findViews() {

        imageView1 = findViewById(R.id.img_1);
        imageView2 = findViewById(R.id.img_2);

        /**
         * 共享元素的总结
         * 1、支持多个view 共享过渡动画
         * 2、支持两个view 不是同一种类型
         */
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

        findViewById(R.id.img_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Share1Activity.this, Share2Activity.class);
                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(Share1Activity.this, imageView1, "image");

                startActivity(intent, optionsCompat.toBundle());

            }
        });


        findViewById(R.id.img_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Share1Activity.this, Share2Activity.class);
                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(Share1Activity.this, imageView2, "image");

                startActivity(intent, optionsCompat.toBundle());

            }
        });

    }
}
