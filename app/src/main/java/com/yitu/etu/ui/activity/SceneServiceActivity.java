package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.SceneServiceAdapter;
import com.yitu.etu.widget.CarouselView;
import com.yitu.etu.widget.ListSlideView;

import java.util.HashMap;

import okhttp3.Call;

public class SceneServiceActivity extends BaseActivity {

    private int type;
    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;
    private SceneServiceAdapter sceneServiceAdapter;
    private CarouselView carouselView;
    private String spot_id;
    private View menu_close;
    private View menu_open;

    @Override
    public int getLayout() {
        return R.layout.activity_scene_service;
    }

    @Override
    public void initActionBar() {
        setTitle("  ");
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
        spot_id= getIntent().getStringExtra("spot_id");
        listView = (ListSlideView) findViewById(R.id.listview);

        sceneServiceAdapter = new SceneServiceAdapter(this);
        listView.setAdapter(sceneServiceAdapter);
        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        View view= getLayoutInflater().inflate(R.layout.item_carouselview,null);
        menu_close=view.findViewById(R.id.menu_close);
        menu_open=view.findViewById(R.id.menu_open);
        listView.addHeaderView(view);
        carouselView=(CarouselView)view.findViewById(R.id.carouselView);
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
        if (isRefresh) {
            showWaitDialog("加载中...");
        }
        final HashMap<String, String> params = new HashMap<>();
        params.put("spot_id",spot_id);
        params.put("type",type+"");
        params.put("page",page+"");
        Http.post(Urls.SHOP_GET_LIST, params, new GsonCallback<HttpStateEntity>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();
                hideWaitDialog();
            }

            @Override
            public void onResponse(HttpStateEntity response, int id) {
                if (response.success()) {
                    if (isRefresh) {
                        sceneServiceAdapter.clearAll();
                    }
//                    carouselView.setPath(path);
                    RefreshSuccess(layout_refresh, isRefresh, 10);
                }
                hideWaitDialog();

            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.li_ms) {
            type=1;
            refresh(true);
        } else if (v.getId() == R.id.li_zs) {
            type=2;
            refresh(true);
        } else if (v.getId() == R.id.li_yw) {
            type=3;
            refresh(true);
        } else if (v.getId() == R.id.img_state) {
            menu_close.setVisibility(View.VISIBLE);
            menu_open.setVisibility(View.GONE);
        }
        else if (v.getId() == R.id.menu_close) {
            menu_close.setVisibility(View.GONE);
            menu_open.setVisibility(View.VISIBLE);
        }

    }
}
