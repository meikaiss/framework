package com.android.framework.demo.activity.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by meikai on 2017/11/20.
 */
@GlideModule
public final class CustomAppGlideModule extends AppGlideModule {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //重新设置内存限制
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);

        // 为App注册一个自定义的String类型的BaseGlideUrlLoader
//        registry.append(String.class, InputStream.class, new CustomBaseGlideUrlLoader.Factory());
    }

    /**
     * 关闭解析manifest清单里的 GlideModule，即不再需要配置 GlideModule
     * <p>
     * 这里不开启，避免添加相同的modules两次
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}