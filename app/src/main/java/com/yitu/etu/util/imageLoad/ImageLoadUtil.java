package com.yitu.etu.util.imageLoad;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.yitu.etu.R;

import java.io.File;

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
        loadImage(img, url, width, height, R.drawable.ic_default_image, R.drawable.ic_default_image, null);
    }

    public void loadImage(ImageView img, String url, int defaultDrawable, int width, int height) {
        loadImage(img, url, width, height, defaultDrawable, defaultDrawable, null);
    }
    

    public void loadImage(ImageView img, String url, int defaultDrawable, int width, int height, Callback listener) {
        loadImage(img, url, width, height, defaultDrawable, defaultDrawable, listener);
    }

    /**
     * 加载图片
     *
     * @param img             载体
     * @param url             链接
     * @param width           宽 dp
     * @param height          高 dp
     * @param displayError    加载错误的时候显示的图片
     * @param displayLoadding 加载中的时候显示的图片
     */
    public void loadImage(ImageView img, String url, int width, int height, @DrawableRes int displayError, @DrawableRes int displayLoadding, Callback listener) {
        RequestCreator creator=null;
        if(url.contains("http:")||url.contains("https:")){
            creator= Picasso.with(img.getContext()).load(url);
        }else{
            creator= Picasso.with(img.getContext()).load(new File(url));
        }

        width= (int) (img.getResources().getDisplayMetrics().density*width);
        height= (int) (img.getResources().getDisplayMetrics().density*height);
        if(width>0&&height<=0){
            creator=creator.resize(width,width);
        }else if(height>0&&width<=0){
            creator=creator.resize(height,height);
        }else if(width>0&&height>0){
            creator=creator.resize(width,height);
        }
        creator.centerCrop()
                .error(displayError)
                .placeholder(displayLoadding)
                .config(Bitmap.Config.RGB_565)
                .into(img, listener);
    }

}
