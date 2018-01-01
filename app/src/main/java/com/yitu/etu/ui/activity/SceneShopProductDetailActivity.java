package com.yitu.etu.ui.activity;

import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.SceneShopProductEntity;

public class SceneShopProductDetailActivity extends BaseActivity {


    private SceneShopProductEntity response;
    private TextView tv_des;

    @Override
    public int getLayout() {
        return R.layout.activity_scene_shop_product_detail;
    }

    @Override
    public void initActionBar() {
        setTitle(getIntent().getStringExtra("title"));
        response = (SceneShopProductEntity) getIntent().getSerializableExtra("response");
    }

    @Override
    public void initView() {
        tv_des = (TextView) findViewById(R.id.tv_des);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }
}
