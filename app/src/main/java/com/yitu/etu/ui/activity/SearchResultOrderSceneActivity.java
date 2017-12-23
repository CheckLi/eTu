package com.yitu.etu.ui.activity;

import android.view.View;

import com.yitu.etu.R;

public class SearchResultOrderSceneActivity extends BaseActivity {


    @Override
    public int getLayout() {
        return R.layout.activity_search_result_order_scene;
    }

    @Override
    public void initActionBar() {
        setTitle("dsa");
        mActionBarView.setRightImage(R.drawable.icon145, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }
}
