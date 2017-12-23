package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.yitu.etu.R;
import com.yitu.etu.entity.MapFriendEntity;
import com.yitu.etu.entity.MapOrderSceneEntity;
import com.yitu.etu.entity.MapSceneEntity;
import com.yitu.etu.ui.adapter.MapFriendSearchAdapter;
import com.yitu.etu.ui.adapter.MapOrderSceneSearchAdapter;
import com.yitu.etu.ui.adapter.MapSceneSearchAdapter;
import com.yitu.etu.ui.fragment.MapsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapSearchActivity extends BaseActivity implements View.OnClickListener {
    private View li_search;
    private View li_search_result;
    private ListView listView;
    private int type;
    double lat, lng;
    private EditText ed_search;

    @Override
    public int getLayout() {
        return R.layout.activity_map_search;
    }

    @Override
    public void initActionBar() {
        type = getIntent().getIntExtra("type", MapsFragment.type_scene);
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);

        if (type == MapsFragment.type_scene) {
            setTitle("dsad");
        } else if (type == MapsFragment.type_friend) {
            setTitle("dsad");
        } else if (type == MapsFragment.type_order_scene) {
            setTitle("dsad");
        }

    }

    @Override
    public void initView() {
        li_search = findViewById(R.id.li_search);
        li_search_result = findViewById(R.id.li_search_result);
        ed_search = (EditText) findViewById(R.id.ed_search);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == MapsFragment.type_scene) {
                    setTitle("dsad");
                } else if (type == MapsFragment.type_friend) {
                    startActivity(new Intent(MapSearchActivity.this, SearchResultUserActivity.class));
                } else if (type == MapsFragment.type_order_scene) {
                    startActivity(new Intent(MapSearchActivity.this, SearchResultOrderSceneActivity.class));
                }

            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    public void loadInfo() {
        CloudSearch mCloudSearch = new CloudSearch(this);// 初始化查询类
        mCloudSearch.setOnCloudSearchListener(new CloudSearch.OnCloudSearchListener() {
            @Override
            public void onCloudSearched(CloudResult cloudResult, int i) {
                Log.e("CloudSearch", "CloudSearch");
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
                                if (mapFirendEntitys.size() == 0) {
                                    mapFirendEntitys.add(mapFirendEntity);
                                }
                                for (int n = 0; n < mapFirendEntitys.size(); n++) {
                                    if (mapFirendEntitys.get(n).getId().equals(mapFirendEntity.id)) {
                                        break;
                                    }
                                    if (mapFirendEntitys.size() == n + 1) {
                                        mapFirendEntitys.add(mapFirendEntity);
                                    }
                                }
                            } else if (type == MapsFragment.type_order_scene) {
                                MapOrderSceneEntity mapOrderSceneEntity = new MapOrderSceneEntity();
                                mapOrderSceneEntity.is_spot = filld.get("is_spot");
                                mapOrderSceneEntity.image = filld.get("image");
                                mapOrderSceneEntity.title = filld.get("title");
                                mapOrderSceneEntity.title_id = filld.get("title_id");
                                mapOrderSceneEntity.latLonPoint = cloudItem.getLatLonPoint();
                                mapOrderSceneEntity.address = cloudItem.getSnippet();
                                mapOrderSceneEntity.id = mapOrderSceneEntity.title_id;
                                if (mapOrderSceneEntitys.size() == 0) {
                                    mapOrderSceneEntitys.add(mapOrderSceneEntity);
                                }
                                for (int n = 0; n < mapOrderSceneEntitys.size(); n++) {
                                    if (mapOrderSceneEntitys.get(n).getId().equals(mapOrderSceneEntity.id)) {
                                        break;
                                    }
                                    if (mapOrderSceneEntitys.size() == n + 1) {
                                        mapOrderSceneEntitys.add(mapOrderSceneEntity);
                                    }
                                }

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
                                if (mapSceneEntitys.size() == 0) {
                                    mapSceneEntitys.add(mapSceneEntity);
                                }
                                for (int n = 0; n < mapSceneEntitys.size(); n++) {
                                    if (mapSceneEntitys.get(n).getId().equals(mapSceneEntity.id)) {
                                        break;
                                    }
                                    if (mapSceneEntitys.size() == n + 1) {
                                        mapSceneEntitys.add(mapSceneEntity);
                                    }
                                }
                            }
                        }
                        if (type == MapsFragment.type_friend) {
                            listView.setAdapter(new MapFriendSearchAdapter(MapSearchActivity.this, mapFirendEntitys));
                        } else if (type == MapsFragment.type_order_scene) {
                            listView.setAdapter(new MapOrderSceneSearchAdapter(MapSearchActivity.this, mapOrderSceneEntitys));
                        } else if (type == MapsFragment.type_scene) {
                            listView.setAdapter(new MapSceneSearchAdapter(MapSearchActivity.this, mapSceneEntitys));
                        }

                    } else {
                    }
                } else {

                }
            }

            @Override
            public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {
            }
        });
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound("成都");
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
            CloudSearch.Query mQuery = new CloudSearch.Query(table, ed_search.getText().toString().trim(), bound);
            mQuery.setPageNum(1);
            mQuery.setPageSize(10);
            mCloudSearch.searchCloudAsyn(mQuery);
        } catch (AMapException e)

        {
            Log.e("CloudSearch", "CloudSearch");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                if (ed_search.getText().toString().trim().equals("")) {
                    showToast("请输入你需要搜索的地址");
                } else {
                    li_search.setVisibility(View.GONE);
                    li_search_result.setVisibility(View.VISIBLE);
                    loadInfo();
                }
                break;
            default:
                break;
        }
    }
}
