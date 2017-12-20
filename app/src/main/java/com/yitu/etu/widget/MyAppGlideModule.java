package com.yitu.etu.widget;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @className:MyAppGlideModule
 * @description:仅用于生成glideapp
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:08
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }
}
