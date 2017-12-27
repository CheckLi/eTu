package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.entity.MapFriendEntity;
import com.yitu.etu.entity.MapOrderSceneEntity;
import com.yitu.etu.entity.MapSceneEntity;
import com.yitu.etu.ui.adapter.MapFriendSearchAdapter;
import com.yitu.etu.ui.adapter.MapOrderSceneSearchAdapter;
import com.yitu.etu.ui.adapter.MapSceneSearchAdapter;
import com.yitu.etu.ui.fragment.MapsFragment;
import com.yitu.etu.widget.ListSlideView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapSearchActivity extends BaseActivity {
    private ListSlideView listView;
    private int type;
    private SmartRefreshLayout layout_refresh;
    public MapSceneSearchAdapter mapSceneSearchAdapter;
    public MapOrderSceneSearchAdapter mapOrderSceneSearchAdapter;
    public MapFriendSearchAdapter mapFriendSearchAdapter;
    String data;
    private String city;

    @Override
    public int getLayout() {
        return R.layout.activity_map_search;
    }

    @Override
    public void initActionBar() {
        type = getIntent().getIntExtra("type", MapsFragment.type_scene);
        city = getIntent().getStringExtra("city");
        setTitle("搜索结果");
    }

    @Override
    public void initView() {
        data = getIntent().getStringExtra("data");
        listView = (ListSlideView) findViewById(R.id.listview);
        View view = getLayoutInflater().inflate(R.layout.header_search_result, null);
        ((TextView) view.findViewById(R.id.text)).setText(data);
        listView.addHeaderView(view);
        if (type == MapsFragment.type_friend) {
            mapFriendSearchAdapter = new MapFriendSearchAdapter(MapSearchActivity.this);
            listView.setAdapter(mapFriendSearchAdapter);
        } else if (type == MapsFragment.type_order_scene) {
            mapOrderSceneSearchAdapter = new MapOrderSceneSearchAdapter(MapSearchActivity.this);
            listView.setAdapter(mapOrderSceneSearchAdapter);
        } else if (type == MapsFragment.type_scene) {
            mapSceneSearchAdapter = new MapSceneSearchAdapter(MapSearchActivity.this);
            listView.setAdapter(mapSceneSearchAdapter);
        }
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    if (type == MapsFragment.type_scene) {
                        Intent intent = new Intent(MapSearchActivity.this, SearchResultSceneActivity.class);
                        intent.putExtra("id", mapSceneSearchAdapter.getItem(position - 1).spot_id);
                        startActivity(intent);
                    } else if (type == MapsFragment.type_friend) {
                        MapFriendEntity data = mapFriendSearchAdapter.getData().get(position - 1);
                        Intent intent = new Intent(MapSearchActivity.this, SearchResultUserActivity.class);
                        intent.putExtra("user_id", data.user_id);
                        intent.putExtra("image", data.image);
                        intent.putExtra("sex", data.sex);
                        intent.putExtra("title", data.title);
                        startActivity(intent);
                    } else if (type == MapsFragment.type_order_scene) {
                        MapOrderSceneEntity data = mapOrderSceneSearchAdapter.getItem(position - 1);
                        Intent intent = new Intent(MapSearchActivity.this, SearchResultOrderSceneActivity.class);
                        intent.putExtra("title", data.title);
                        intent.putExtra("id", data.title_id);
                        startActivity(intent);
                    }
                }
            }
        });
        refresh(true);
    }


    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    public void refresh(final boolean isRefresh) {
        if (isRefresh) {
            showWaitDialog("搜索中...");
        }
        CloudSearch mCloudSearch = new CloudSearch(this);// 初始化查询类
        mCloudSearch.setOnCloudSearchListener(new CloudSearch.OnCloudSearchListener() {

            @Override
            public void onCloudSearched(CloudResult cloudResult, int i) {
                Log.e("CloudSearch", "CloudSearch");
                hideWaitDialog();
                if (isRefresh) {
                    if (type == MapsFragment.type_friend) {
                        mapFriendSearchAdapter.clearAll();
                    } else if (type == MapsFragment.type_order_scene) {
                        mapOrderSceneSearchAdapter.clearAll();
                    } else if (type == MapsFragment.type_scene) {
                        mapSceneSearchAdapter.clearAll();
                    }
                }
                if (cloudResult != null) {
                    ArrayList<CloudItem> cloudItems = cloudResult.getClouds();
                    if (cloudItems != null && cloudItems.size() > 0) {
                        List<MapFriendEntity> mapFirendEntitys = new ArrayList<>();
                        ArrayList<MapOrderSceneEntity> mapOrderSceneEntitys = new ArrayList<>();
                        ArrayList<MapSceneEntity> mapSceneEntitys = new ArrayList<>();
                        for (CloudItem cloudItem :
                                cloudItems) {
                            HashMap<String, String> filld = cloudItem.getCustomfield();
                            if (type == MapsFragment.type_friend) {
                                MapFriendEntity mapFirendEntity = new MapFriendEntity();
                                mapFirendEntity.image = filld.get("image");
                                mapFirendEntity.user_id = filld.get("user_id");
                                mapFirendEntity.sex = filld.get("sex");
                                mapFirendEntity.latLonPoint = cloudItem.getLatLonPoint();
                                mapFirendEntity.address = cloudItem.getSnippet();
                                mapFirendEntity.title = cloudItem.getTitle();
                                mapFirendEntity.id = mapFirendEntity.user_id;
                                mapFirendEntitys.add(mapFirendEntity);
                            } else if (type == MapsFragment.type_order_scene) {
                                MapOrderSceneEntity mapOrderSceneEntity = new MapOrderSceneEntity();
                                mapOrderSceneEntity.is_spot = filld.get("is_spot");
                                mapOrderSceneEntity.image = filld.get("image");
                                mapOrderSceneEntity.title = filld.get("title");
                                mapOrderSceneEntity.title_id = filld.get("title_id");
                                mapOrderSceneEntity.latLonPoint = cloudItem.getLatLonPoint();
                                mapOrderSceneEntity.address = cloudItem.getSnippet();
                                mapOrderSceneEntity.id = mapOrderSceneEntity.title_id;
                                mapOrderSceneEntitys.add(mapOrderSceneEntity);
                            } else if (type == MapsFragment.type_scene) {
                                MapSceneEntity mapSceneEntity = new MapSceneEntity();
                                mapSceneEntity.latLonPoint = cloudItem.getLatLonPoint();
                                mapSceneEntity.price = filld.get("price");
                                mapSceneEntity.yjcount = filld.get("yjcount");
                                mapSceneEntity.xjdes = filld.get("xjdes");
                                mapSceneEntity.spot_id = filld.get("spot_id");
                                mapSceneEntity.image = filld.get("image");
                                mapSceneEntity.address = cloudItem.getSnippet();
                                mapSceneEntity.title = cloudItem.getTitle();
                                mapSceneEntity.id = mapSceneEntity.spot_id;
                                mapSceneEntitys.add(mapSceneEntity);
                            }
                        }
                        if (type == MapsFragment.type_friend) {
                            mapFriendSearchAdapter.addData(mapFirendEntitys);
                            RefreshSuccess(layout_refresh, isRefresh, mapFirendEntitys.size());
                        } else if (type == MapsFragment.type_order_scene) {
                            mapOrderSceneSearchAdapter.addData(mapOrderSceneEntitys);
                            RefreshSuccess(layout_refresh, isRefresh, mapOrderSceneEntitys.size());
                        } else if (type == MapsFragment.type_scene) {
                            mapSceneSearchAdapter.addData(mapSceneEntitys);
                            RefreshSuccess(layout_refresh, isRefresh, mapSceneEntitys.size());
                        }

                    } else {
                    }
                } else {
                }
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();

            }

            @Override
            public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {
            }
        });
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(city);
        try

        {
            String table = "";
            if (type == MapsFragment.type_friend) {
                table = "58eb4c60afdf522b2822fda3";
            } else if (type == MapsFragment.type_order_scene) {
                table = "5905996cafdf522b281639ab";
            } else if (type == MapsFragment.type_scene) {
                table = "58e64edb2376c11620d5b2e7";
            }
            CloudSearch.Query mQuery = new CloudSearch.Query(table, data, bound);
            mQuery.setPageNum(page);
            mQuery.setPageSize(size);
            mCloudSearch.searchCloudAsyn(mQuery);
        } catch (AMapException e) {
        }
    }

}
