package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.SceneServiceEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.SceneServiceAdapter;
import com.yitu.etu.widget.CarouselView;
import com.yitu.etu.widget.ListSlideView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private View view;
    private String name;

    @Override
    public int getLayout() {
        return R.layout.activity_scene_service;
    }

    @Override
    public void initActionBar() {
        type = getIntent().getIntExtra("type", 1);
        name = getIntent().getStringExtra("name");
        setMTitle();
    }

    public void setMTitle() {
        if (type == 1) {
            setTitle("美食");
        } else if (type == 2) {
            setTitle("住宿");
        } else if (type == 3) {
            setTitle("游玩");
        }
    }

    @Override
    public void initView() {

        spot_id = getIntent().getStringExtra("spot_id");
        listView = (ListSlideView) findViewById(R.id.listview);

        sceneServiceAdapter = new SceneServiceAdapter(this, name);

        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        view = getLayoutInflater().inflate(R.layout.item_carouselview, null);
        view.setVisibility(View.GONE);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        menu_close = view.findViewById(R.id.menu_close);
        menu_open = view.findViewById(R.id.menu_open);
        listView.addHeaderView(view);
        listView.setAdapter(sceneServiceAdapter);
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
        refresh(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    if (type == 3) {
                        Intent intent = new Intent(context, SceneShopYwDetailActivity.class);
                        SceneServiceEntity.ListBean data = sceneServiceAdapter.getItem(i - 1);
                        intent.putExtra("id", data.getId());
                        intent.putExtra("type", type + 4);
                        intent.putExtra("title", data.getName());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, SceneShopProductActivity.class);
                        SceneServiceEntity.ListBean data = sceneServiceAdapter.getItem(i - 1);
                        intent.putExtra("id", data.getId());
                        intent.putExtra("title", data.getName());
                        intent.putExtra("type", type + 4);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void refresh(final boolean isRefresh) {
        if (isRefresh) {
            showWaitDialog("加载中...");
        }
        final HashMap<String, String> params = new HashMap<>();
        params.put("spot_id", spot_id);
        params.put("type", type + "");
        params.put("page", page + "");
        Http.post(Urls.SHOP_GET_LIST, params, new GsonCallback<ObjectBaseEntity<SceneServiceEntity>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();
                hideWaitDialog();
            }

            @Override
            public void onResponse(ObjectBaseEntity<SceneServiceEntity> response, int id) {
                if (response.success()) {
                    if (isRefresh) {
                        sceneServiceAdapter.clearAll();
                        if (response.getData().getBanner() != null) {
                            view.setVisibility(View.VISIBLE);
                            List<String> path = new ArrayList<>();
                            for (SceneServiceEntity.BannerBean data :
                                    response.getData().getBanner()) {
                                path.add(data.getImage());
                            }
                            carouselView.setPath(path);
                        }

                    }
                    sceneServiceAdapter.addData(response.getData().getList());
//                    carouselView.setPath(path);
                    RefreshSuccess(layout_refresh, isRefresh, response.getData().getList().size());
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
            type = 1;
            setMTitle();
            sceneServiceAdapter.clearAll();
            refresh(true);
        } else if (v.getId() == R.id.li_zs) {
            type = 2;
            setMTitle();
            sceneServiceAdapter.clearAll();
            refresh(true);
        } else if (v.getId() == R.id.li_yw) {
            type = 3;
            setMTitle();
            sceneServiceAdapter.clearAll();
            refresh(true);
        } else if (v.getId() == R.id.img_state) {
            menu_close.setVisibility(View.VISIBLE);
            menu_open.setVisibility(View.GONE);
        } else if (v.getId() == R.id.menu_close) {
            menu_close.setVisibility(View.GONE);
            menu_open.setVisibility(View.VISIBLE);
        }

    }
}
