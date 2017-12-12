package com.yitu.etu.util.imageLoad;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yitu.etu.R;
import com.yitu.etu.widget.GlideApp;

/**
 * @className:ImageLoad
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:10
 */
public class ImageLoadUtil {

    public static ImageLoadUtil getInstance() {
        return new ImageLoadUtil();
    }

    public void loadImage(ImageView img, String url, int width, int height) {
        loadImage(img, url, width, height, R.drawable.ic_default_image, R.drawable.ic_default_image,null);
    }

    /**
     * 加载图片
     *
     * @param img             载体
     * @param url             链接
     * @param width           宽
     * @param height          高
     * @param displayError    加载错误的时候显示的图片
     * @param displayLoadding 加载中的时候显示的图片
     */
    public void loadImage(ImageView img, String url, int width, int height, @DrawableRes int displayError, @DrawableRes int displayLoadding,RequestListener listener) {
        GlideApp.with(img.getContext())
                .load(url)
                .override(width, height)
                .centerCrop()
                .placeholder(displayLoadding)//加载中图片显示
                .error(displayError)
                .transition(new DrawableTransitionOptions().crossFade(200))//渐变动画
                .listener(listener==null?new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }:listener)
                .into(img);
    }
}
