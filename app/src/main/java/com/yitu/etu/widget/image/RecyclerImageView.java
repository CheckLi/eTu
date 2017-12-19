package com.yitu.etu.widget.image;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @className:RecyclerImageView
 * @description:减速内存回收
 * @author: JIAMING.LI
 * @date:2017年12月19日 23:46
 */
public class RecyclerImageView extends ImageView{
    public RecyclerImageView(Context context) {
        super(context);
    }

    public RecyclerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}
