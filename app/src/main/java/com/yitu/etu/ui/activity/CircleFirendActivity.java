package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.entity.ArrayBaseEntity;
import com.yitu.etu.entity.CircleFirendEntity;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.CircleFirendAdapter;
import com.yitu.etu.widget.ListSlideView;

import java.util.HashMap;

import okhttp3.Call;

public class CircleFirendActivity extends BaseActivity {


    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;
    private CircleFirendAdapter circleFirendAdapter;

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
                startActivityForResult(new Intent(CircleFirendActivity.this, HairDynamicActivity.class), 100);
            }
        });
        HashMap<String, String> hashMap = new HashMap<>();
        Http.post(Urls.CIRCLE_ALLOW_CIRCLE, hashMap, new GsonCallback<HttpStateEntity>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(HttpStateEntity response, int id) {
                Log.e("tag",response.message);
            }
        });
    }

    @Override
    public void initView() {
        listView = (ListSlideView) findViewById(R.id.listView);
        circleFirendAdapter = new CircleFirendAdapter(this, false);
        listView.setAdapter(circleFirendAdapter);

        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        layout_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getUserIndex(true);
            }
        });
        layout_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getUserIndex(false);
            }
        });

    }

    public void refresh(final boolean isRefresh) {
        RefreshSuccess(layout_refresh, isRefresh, 10);
    }

    @Override
    public void getData() {
        showWaitDialog("加载中...");
        getUserIndex(true);
    }

    @Override
    public void initListener() {

    }

    public void getUserIndex(final boolean isRefresh) {
        if (isRefresh) {
            page = 1;
            RefreshSuccessInit(layout_refresh,isRefresh);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("page", page + "");
        Http.post(Urls.CIRCLE_GET_LIST, params, new GsonCallback<ArrayBaseEntity<CircleFirendEntity.CircleBean>>() {
            @Override
            public void onError(Call call, Exception e, int i) {
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();
                hideWaitDialog();
            }

            @Override
            public void onResponse(ArrayBaseEntity<CircleFirendEntity.CircleBean> response, int i) {
                if (response.success()) {
                    if (isRefresh) {
                        circleFirendAdapter.clearAll();
                        hideWaitDialog();
                    }
                    circleFirendAdapter.addData(response.getData());
                    RefreshSuccess(layout_refresh, isRefresh, response.getData().size());
                } else {
                    RefreshSuccess(layout_refresh, isRefresh, 0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            CircleFirendEntity.CircleBean mdata=(CircleFirendEntity.CircleBean)data.getSerializableExtra("data");
            circleFirendAdapter.addToFirst(mdata);
        }
        if (requestCode == 10001 && resultCode == RESULT_OK) {
            circleFirendAdapter.sendMsg(data.getStringExtra("text"));

        }

    }
}
