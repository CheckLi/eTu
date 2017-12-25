package com.yitu.etu.ui.activity;

import android.view.View;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.ui.adapter.CircleFirendAdapter;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.ListSlideView;

public class SearchResultUserActivity extends BaseActivity {

    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;

    @Override
    public int getLayout() {
        return R.layout.activity_circle_firend2;
    }

    @Override
    public void initActionBar() {
        setTitle("用户信息");
        mActionBarView.setRightImage(R.drawable.icon145, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tools.getPopupWindow(SearchResultUserActivity.this, new String[]{"那是", "那是那是"}, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                },null).showAsDropDown(v, 0, 0);
            }
        });
    }

    @Override
    public void initView() {
        listView=(ListSlideView) findViewById(R.id.listView);
        listView.setAdapter(new CircleFirendAdapter(this));
        listView.addHeaderView(getLayoutInflater().inflate(R.layout.activity_search_result_user, null));

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
}
