package com.android.framework.demo.activity.glide;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by meikai on 2017/11/17.
 */
public class GlideActivity extends BaseCompactActivity implements View.OnClickListener {

    private ImageView imgNormal;
    private Button btnNormal;

    private ImageView imgCircle;
    private Button btnCircle;


    private String imgUrl
            = "http://img.blog.csdn.net/20170330110038501?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzM5NDUyNw"
            + "==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center";


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void findViews() {

        imgNormal = f(R.id.img_target_normal);
        btnNormal = f(R.id.btn_normal);

        imgCircle = f(R.id.img_target_circle);
        btnCircle = f(R.id.btn_circle);
    }

    @Override
    public void setListeners() {
        btnNormal.setOnClickListener(this);
        btnCircle.setOnClickListener(this);
    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {


        RequestOptions options1 = new RequestOptions();
        options1.placeholder(R.drawable.thumb1);
        options1.fallback(new ColorDrawable(Color.BLUE));
        options1.error(R.drawable.bottom_menu_mine);
        options1.diskCacheStrategy(DiskCacheStrategy.NONE);
        options1.skipMemoryCache(true);
        options1.transforms(new CenterCrop(), new RoundedCorners(50));
//        options1.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//                options1.dontTransform();

        RequestBuilder builder1 = Glide.with(this).load(imgUrl).apply(options1);
//        builder1.transition(new BitmapTransitionOptions());
        builder1.into(imgCircle);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                RequestOptions options = new RequestOptions();
//                options.placeholder(R.drawable.thumb1);
//                options.fallback(new ColorDrawable(Color.RED));
//                options.error(R.drawable.bottom_menu_mine);
                options.priority(Priority.HIGH);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
                options.skipMemoryCache(true);
//                options.dontTransform();

                RequestBuilder builder = Glide.with(this).load(imgUrl).apply(options);
                builder.into(imgNormal);
                break;
            case R.id.btn_circle:
                RequestOptions options1 = new RequestOptions();
                options1.placeholder(R.drawable.thumb1);
                options1.fallback(new ColorDrawable(Color.BLUE));
                options1.error(R.drawable.bottom_menu_mine);
                options1.diskCacheStrategy(DiskCacheStrategy.NONE);
                options1.skipMemoryCache(true);
                options1.transforms(new CenterCrop(), new RoundedCorners(50));
//                options1.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//                options1.dontTransform();

                RequestBuilder builder1 = Glide.with(this).load(imgUrl).apply(options1);
                builder1.transition(new BitmapTransitionOptions());
                builder1.into(imgCircle);

                break;
            default:

                break;
        }
    }
}
