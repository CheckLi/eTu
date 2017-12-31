package com.yitu.etu.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.R;
import com.yitu.etu.entity.ArrayBaseEntity;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ShopOrderEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.ManageOrderDetaileActivity;
import com.yitu.etu.ui.adapter.ManageOrderAdapter;

import java.util.HashMap;

import okhttp3.Call;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/29.
 */
public class ManageOrderFragment extends BaseFragment {
    private ListView listView;
    private SmartRefreshLayout layout_refresh;
    private ManageOrderAdapter manageOrderAdapter;
    private int type;

    @Override
    public int getLayout() {
        return R.layout.fragment_order_layout;
    }

    @Override
    public void initView() {
        type = getArguments().getInt("type");
        if (type == 0) {
            type = 2;
        } else {
            type = 1;
        }
        listView = (ListView) view.findViewById(R.id.listView_order);
        layout_refresh = (SmartRefreshLayout) view.findViewById(R.id.layout_refresh);
        manageOrderAdapter = new ManageOrderAdapter(getContext(),type);
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
        listView.setAdapter(manageOrderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),ManageOrderDetaileActivity.class);intent.putExtra("data",manageOrderAdapter.getItem(i));
                startActivity(intent);
            }
        });
        refresh(true);
    }

    public void refresh(final boolean isRefresh) {
        if (isRefresh) {
            showWaitDialog("加载中...");
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", page + "");
        hashMap.put("type", type + "");
        Http.post(Urls.SHOP_GET_SHOP_ORDER_LIST, hashMap, new GsonCallback<ArrayBaseEntity<ShopOrderEntity>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();

                hideWaitDialog();
            }

            @Override
            public void onResponse(ArrayBaseEntity<ShopOrderEntity> response, int id) {
                if (response.success()) {
                    if (isRefresh) {
                        manageOrderAdapter.clearAll();
                    }
                    manageOrderAdapter.addData(response.getData());
                    RefreshSuccess(layout_refresh, isRefresh, response.getData().size());
                } else {
                    RefreshSuccess(layout_refresh, isRefresh, 0);
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
}
