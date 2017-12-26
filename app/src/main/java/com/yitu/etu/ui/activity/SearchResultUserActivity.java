package com.yitu.etu.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.CircleFirendEntity;
import com.yitu.etu.entity.MapFriendEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.CircleFirendAdapter;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.GlideApp;
import com.yitu.etu.widget.ListSlideView;

import java.util.HashMap;

import okhttp3.Call;

public class SearchResultUserActivity extends BaseActivity {

    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;
    private MapFriendEntity data;
    private CircleFirendAdapter circleFirendAdapter;

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
                if (EtuApplication.getInstance().isLogin()) {
                    Tools.getPopupWindow(SearchResultUserActivity.this, new String[]{"加为好友", "发起聊天"}, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    }, null).showAsDropDown(v, 0, 0);
                } else {
                    showToast("请登录");
                }
            }
        });
    }

    @Override
    public void initView() {
        data = new MapFriendEntity();
        data.title = getIntent().getStringExtra("title");
        data.image = getIntent().getStringExtra("image");
        data.sex = getIntent().getStringExtra("sex");
        data.user_id = getIntent().getStringExtra("user_id");
        listView = (ListSlideView) findViewById(R.id.listView);
        circleFirendAdapter = new CircleFirendAdapter(this, true);
        listView.setAdapter(circleFirendAdapter);
        View view = getLayoutInflater().inflate(R.layout.activity_search_result_user, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        ImageView sex = (ImageView) view.findViewById(R.id.sex);
        GlideApp.with(this)
                .load(Urls.address + data.getImage())
                .centerCrop()
                .error(R.drawable.default_head)
                .placeholder(R.drawable.default_head).into(image);
//        ImageLoadUtil.getInstance().loadImage(image, Urls.address + data.getImage(), R.drawable.default_head, 200, 200);
        tv_name.setText(data.title);
        if ("0".equals(data.sex)) {
            sex.setImageResource(R.drawable.icon2);
        } else {
            sex.setImageResource(R.drawable.icon0);
        }
        listView.addHeaderView(view);
        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        layout_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getUserIndex();
            }
        });
        getUserIndex();
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

    //活动收藏
    public void getUserIndex() {
        showWaitDialog("加载中...");
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", data.user_id);
        Http.post(Urls.CIRCLE_USER_INDEX, params, new GsonCallback<ObjectBaseEntity<CircleFirendEntity>>() {
            @Override
            public void onError(Call call, Exception e, int i) {
                hideWaitDialog();
            }

            @Override
            public void onResponse(ObjectBaseEntity<CircleFirendEntity> response, int i) {

                circleFirendAdapter.clearAll();
                circleFirendAdapter.addData(response.getData().getCircle());
                hideWaitDialog();
            }
        });
    }
}


