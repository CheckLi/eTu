package com.yitu.etu.ui.fragment;

import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yitu.etu.R;
import com.yitu.etu.ui.adapter.ManageOrderAdapter;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/29.
 */
public class ManageOrderFragment extends BaseFragment{
    private ListView listView;
    private SmartRefreshLayout layout_refresh;
    private ManageOrderAdapter manageOrderAdapter;
    @Override
    public int getLayout() {
        return R.layout.fragment_order_layout;
    }

    @Override
    public void initView() {
        getArguments().getInt("type");
        listView = (ListView) view.findViewById(R.id.listView_order);
        layout_refresh = (SmartRefreshLayout) view.findViewById(R.id.layout_refresh);
        manageOrderAdapter = new ManageOrderAdapter(getContext());
        listView.setAdapter(manageOrderAdapter);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }
}
