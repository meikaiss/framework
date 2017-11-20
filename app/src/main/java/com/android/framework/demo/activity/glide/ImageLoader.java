package com.android.framework.demo.activity.glide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.android.framework.demo.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by meikai on 2017/11/17.
 */
public class ImageLoader {

    public static void load(Context context, String url, ImageView imageView) {

        RequestOptions options = new RequestOptions();
//        options.placeholder(R.drawable.bottom_menu_recruit);
//        options.error(R.drawable.bottom_menu_mine);
        options.priority(Priority.HIGH);
        options.skipMemoryCache(true);
        options.transform(new RoundedCorners(20));
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        options.placeholder(R.drawable.thumb1);
        options.fallback(new ColorDrawable(Color.RED));
        options.transform(new MultiTransformation<>(new FitCenter(), new CircleCrop()));

        RequestBuilder builder = Glide.with(context)
                .load(url).apply(options);
        builder.into(imageView);

//        GlideApp.with(context).clear(imageView);
//
//        RequestBuilder<Drawable> builder = Glide.with(context).load(url).apply(options);
//        builder
//                .thumbnail(Glide.with(context)
//                        .load(R.drawable.thumb1))
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
//                            boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
//                            DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                }).into(imageView);

    }
}
