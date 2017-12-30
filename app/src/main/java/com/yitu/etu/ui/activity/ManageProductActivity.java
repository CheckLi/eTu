package com.yitu.etu.ui.activity;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.ui.adapter.ManageProductAdapter;
import com.yitu.etu.widget.ListSlideView;

public class ManageProductActivity extends BaseActivity {
    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;
    private ManageProductAdapter manageProductAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_manage_product;
//        return R.layout.activity_map_search;
    }

    @Override
    public void initActionBar() {
        setTitle("商品管理");
    }

    @Override
    public void initView() {
        listView = (ListSlideView) findViewById(R.id.listview);
        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        manageProductAdapter = new ManageProductAdapter(this);
        listView.setAdapter(manageProductAdapter);
    }

    @Override
    public void initListener() {
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

    @Override
    public void getData() {

    }

    public void refresh(final boolean isRefresh) {

    }
}
