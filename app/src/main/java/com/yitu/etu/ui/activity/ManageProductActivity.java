package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yitu.etu.R;
import com.yitu.etu.entity.ArrayBaseEntity;
import com.yitu.etu.entity.ShopProductEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.ManageProductAdapter;
import com.yitu.etu.widget.ListSlideView;

import java.util.HashMap;

import okhttp3.Call;

public class ManageProductActivity extends BaseActivity {
    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;
    private ManageProductAdapter manageProductAdapter;
    private String shop_id;

    @Override
    public int getLayout() {
        return R.layout.activity_manage_product;
//        return R.layout.activity_map_search;
    }

    @Override
    public void initActionBar() {
        setTitle("商品管理");
        shop_id = getIntent().getStringExtra("shop_id");
        setRightText("添加", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReleaseProductActivity.class);
                intent.putExtra("shop_id",shop_id);
                startActivityForResult(intent, 10001);
            }
        });
    }

    @Override
    public void initView() {
        listView = (ListSlideView) findViewById(R.id.listview);
        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        layout_refresh.setEnableLoadmore(false);
        layout_refresh.setEnableRefresh(false);
        manageProductAdapter = new ManageProductAdapter(this);
        listView.setAdapter(manageProductAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                delete( manageProductAdapter.getItem(i));
                Intent intent = new Intent(context, ReleaseProductActivity.class);
                intent.putExtra("data", manageProductAdapter.getItem(i));
                intent.putExtra("shop_id",shop_id);
                startActivityForResult(intent, 10001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (10001 == requestCode && resultCode == RESULT_OK) {
            page=1;
            refresh(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initListener() {
//        layout_refresh.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                page = 1;
//                refresh(true);
//            }
//        });
//        layout_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refresh(false);
//            }
//        });
        refresh(true);
    }

    @Override
    public void getData() {

    }

    public void refresh(final boolean isRefresh) {
        if (isRefresh) {
            showWaitDialog("加载中...");
        }
        HashMap<String, String> hashMap = new HashMap<>();
        Http.post(Urls.GET_SHOP_PRODUCT, hashMap, new GsonCallback<ArrayBaseEntity<ShopProductEntity>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
//                layout_refresh.finishRefresh();
//                layout_refresh.finishLoadmore();
                hideWaitDialog();
            }

            @Override
            public void onResponse(ArrayBaseEntity<ShopProductEntity> response, int id) {
                if (response.success()) {
                    if (isRefresh) {
                        manageProductAdapter.clearAll();
                    }
                    manageProductAdapter.addData(response.getData());
//                    RefreshSuccess(layout_refresh, isRefresh, response.getData().size());
                } else {
//                    RefreshSuccess(layout_refresh, isRefresh, 0);
                }
                hideWaitDialog();
            }
        });
    }

}
