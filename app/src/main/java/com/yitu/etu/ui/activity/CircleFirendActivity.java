package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.ui.adapter.CircleFirendAdapter;
import com.yitu.etu.widget.ListSlideView;

public class CircleFirendActivity extends BaseActivity {


    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;

    @Override
    public int getLayout() {
        return R.layout.activity_circle_firend2;
    }

    @Override
    public void initActionBar() {
        setTitle("朋友圈");
        mActionBarView.setRightText("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivityForResult(new Intent(CircleFirendActivity.this,HairDynamicActivity.class),100);
            }
        });

    }

    @Override
    public void initView() {
        listView=(ListSlideView) findViewById(R.id.listView);
        listView.setAdapter(new CircleFirendAdapter(this));

        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        layout_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                refresh(true);
            }
        });
        layout_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refresh(false);
            }
        });
    }
    public void refresh(final boolean isRefresh) {
        RefreshSuccess(layout_refresh, isRefresh, 10);

    }
        @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100&&resultCode==RESULT_OK){

        }
    }
}
