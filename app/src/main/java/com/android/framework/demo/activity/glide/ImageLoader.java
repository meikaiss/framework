package com.android.framework.demo.activity.glide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.android.framework.demo.R;
import com.android.framework.util.DimenUtil;
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

    /**
     * 加载CenterCrop缩放格式的圆角图片
     *
     * @param uri
     * @param iv
     * @param radiusDP 圆角半径，单位:dp
     */
    public void showCenterCropRound(String uri, ImageView iv, int radiusDP) {
        showCenterCropRound(uri, iv, radiusDP, 0, 0, null);
    }

    public void showCenterCropRound(String uri, ImageView iv, int radiusDP, int placeholderId, int errorId) {
        showCenterCropRound(uri, iv, radiusDP, placeholderId, errorId, null);
    }

    /**
     * 加载CenterCrop缩放格式的圆角图片
     * <p>
     * 注：加载中占位、错误占位 也会应用圆角效果
     *
     * @param uri
     * @param iv
     * @param radiusDP     圆角半径，单位:dp
     * @param radiusEnable 指定哪些角禁用圆角效果，长度=4，true:启用，false:禁用； 顺序：左上、右上、右下、左下 ；
     */
    public void showCenterCropRound(String uri, ImageView iv, int radiusDP,
                                    int placeholderId, int errorId, boolean[] radiusEnable) {
        int radiusPx = DimenUtil.dp2px(iv.getContext(), radiusDP);
        Glide.with(iv.getContext())
                .load(uri)
                .thumbnail(loadTransform(iv.getContext(), placeholderId, radiusDP, radiusEnable))
                .thumbnail(loadTransform(iv.getContext(), errorId, radiusDP, radiusEnable))
                .apply(RequestOptions.bitmapTransform(new CenterCropRoundCornerTransform(radiusPx, radiusEnable)))
                .into(iv);//四周都是圆角的圆角矩形图片。
    }

    //加载圆角占位符, radiusDP 半径，单位：dp
    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId, int radiusDP,
                                                          boolean[] radiusEnable) {
        return Glide.with(context)
                .load(placeholderId)
                .apply(new RequestOptions().centerCrop()
                        .transform(new CenterCropRoundCornerTransform(radiusDP, radiusEnable)));
    }

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
