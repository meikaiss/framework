package com.android.framework.demo.activity;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by meikai on 16/6/15.
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        int cacheSize100MegaBytes = 104857600;

        ExternalCacheDiskCacheFactory externalCacheDiskCacheFactory = new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes);
        builder.setDiskCache(externalCacheDiskCacheFactory);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }
}