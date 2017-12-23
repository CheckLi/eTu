package com.yitu.etu.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yitu.etu.R;
import com.yitu.etu.ui.adapter.CircleFirendAdapter;
import com.yitu.etu.util.Tools;

public class SearchResultUserActivity extends BaseActivity {

    private ListView listView;

    @Override
    public int getLayout() {
        return R.layout.list;
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
        listView = (ListView) findViewById(R.id.listView);
        int px = Tools.dp2px(this, 10);
        listView.setPadding(px, 0, px, 0);
        listView.addHeaderView(getLayoutInflater().inflate(R.layout.activity_search_result_user, null));
        listView.setAdapter(new CircleFirendAdapter(this));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }
}
