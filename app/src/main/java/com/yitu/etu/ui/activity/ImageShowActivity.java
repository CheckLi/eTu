package com.yitu.etu.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.yitu.etu.R;
import com.yitu.etu.widget.GlideApp;
import com.yitu.etu.widget.SmoothImageView;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/25.
 */
public class ImageShowActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_image_show;
    }

    @Override
    public void initActionBar() {
        mActionBarView.setVisibility(View.GONE);
    }

    @Override
    public void initView() {
        SmoothImageView imageView = (SmoothImageView) findViewById(R.id.smoothImageView);
        String path = getIntent().getStringExtra("path");
        int mLocationX = getIntent().getIntExtra("locationX", 0);
        int mLocationY = getIntent().getIntExtra("locationY", 0);
        int mWidth = getIntent().getIntExtra("width", 0);
        int mHeight = getIntent().getIntExtra("height", 0);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);
//        ImageLoadUtil.getInstance().loadImage(imageView, path, 0, 0);
        GlideApp.with(this)
                .load(path)
                .placeholder(R.drawable.ic_default_image)
                 .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.icon17).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(-1, -1);
    }
}
