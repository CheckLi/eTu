package com.yitu.etu.util.imageLoad;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.TintContextWrapper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.yitu.etu.R;

import java.io.File;

/**
 * @className:ImageLoad
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:10
 */
public class ImageLoadGlideUtil {

    public static ImageLoadGlideUtil getInstance() {
        return new ImageLoadGlideUtil();
    }

    public void loadImage(ImageView img, String url, int width, int height) {
        loadImage(img, url, width, height, R.drawable.ic_default_image, R.drawable.ic_default_image, null);
    }

    public void loadImage(ImageView img, String url, int defaultDrawable, int width, int height) {
        loadImage(img, url, width, height, defaultDrawable, defaultDrawable, null);
    }

    public void loadImage(Fragment fragment, ImageView img, String url, int defaultDrawable, int width, int height) {
        loadImage(fragment, img, url, width, height, defaultDrawable, defaultDrawable, null);
    }

    public void loadImage(Fragment fragment, ImageView img, String url, int defaultDrawable, int width, int height, RequestListener listener) {
        loadImage(fragment, img, url, width, height, defaultDrawable, defaultDrawable, listener);
    }

    public void loadImage(ImageView img, String url, int defaultDrawable, int width, int height, RequestListener listener) {
        loadImage(img, url, width, height, defaultDrawable, defaultDrawable, listener);
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
    public void loadImage(ImageView img, String url, int width, int height, @DrawableRes int displayError, @DrawableRes int displayLoadding, RequestListener listener) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(displayLoadding) //加载中图片
                .error(displayError) //加载失败图片
                .fallback(displayError) //url为空图片
                .centerCrop() // 填充方式

                .priority(Priority.HIGH) //优先级
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); //缓存策略，后面详细介绍
        width= (int) (img.getResources().getDisplayMetrics().density*width);
        height= (int) (img.getResources().getDisplayMetrics().density*height);
        if(width>0&&height<=0){
            requestOptions.override(width,width);
        }else if(height>0&&width<=0){
            requestOptions.override(height,height);
        }else if(width>0&&height>0){
            requestOptions.override(width,height);
        }
        Activity activity = null;
        if (img.getContext() instanceof TintContextWrapper) {
            activity = (Activity) (((TintContextWrapper) img.getContext()).getBaseContext());
        } else {
            activity = ((Activity) img.getContext());
        }
        if(url.contains("http:")||url.contains("https:")){
            Glide.with(activity)
                    .load(url)
                    .listener(listener)
                    .transition(new DrawableTransitionOptions().crossFade(200))
                    .apply(requestOptions).into(img);
        }else{
            Glide.with(activity)
                    .load(new File(url))
                    .listener(listener)
                    .transition(new DrawableTransitionOptions().crossFade(200))
                    .apply(requestOptions).into(img);
        }

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
    public void loadImage(Fragment fragment, ImageView img, String url, int width, int height, @DrawableRes int displayError, @DrawableRes int displayLoadding, RequestListener listener) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(displayLoadding) //加载中图片
                .error(displayError) //加载失败图片
                .fallback(displayError) //url为空图片
                .centerCrop() // 填充方式

                .priority(Priority.HIGH) //优先级
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); //缓存策略，后面详细介绍
        width= (int) (img.getResources().getDisplayMetrics().density*width);
        height= (int) (img.getResources().getDisplayMetrics().density*height);
        if(width>0&&height<=0){
            requestOptions.override(width,width);
        }else if(height>0&&width<=0){
            requestOptions.override(height,height);
        }else if(width>0&&height>0){
            requestOptions.override(width,height);
        }

        Glide.with(fragment)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .listener(listener)
                .apply(requestOptions).into(img);
    }

}
