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
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.ListSlideView;

import java.util.HashMap;

import okhttp3.Call;

public class SearchResultUserActivity extends BaseActivity {

    private ListSlideView listView;
    private SmartRefreshLayout layout_refresh;
    private MapFriendEntity data;
    private CircleFirendAdapter circleFirendAdapter;
    private TextView tv_name;
    private ImageView image;
    private ImageView sex;
    private View view;
    private CircleFirendEntity info;

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
                            if (position == 0) {
                                addFriend();
                            } else if (position == 1) {
                                if (info != null && info.getUser() != null) {
                                    Tools.startChat(info.getUser().name, info.getUser().getId() + "", SearchResultUserActivity.this);
                                } else {
                                    showToast("请稍等...");
                                }
                            }
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
        data.user_id = getIntent().getStringExtra("user_id");
        listView = (ListSlideView) findViewById(R.id.listView);
        circleFirendAdapter = new CircleFirendAdapter(this, true);
        view = getLayoutInflater().inflate(R.layout.activity_search_result_user, null);
        listView.addHeaderView(view);
        listView.setAdapter(circleFirendAdapter);
        view.setVisibility(View.GONE);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        image = (ImageView) view.findViewById(R.id.image);
        sex = (ImageView) view.findViewById(R.id.sex);


        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        layout_refresh.setEnableLoadmore(false);
        layout_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                RefreshSuccessInit(layout_refresh, true);
                getUserIndex();
            }
        });
        getUserIndex();
    }

    public void refresh(final boolean isRefresh) {
        if (isRefresh) {
            RefreshSuccessInit(layout_refresh, isRefresh);
        }
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
        showWaitDialog("获取中...");
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", data.user_id);
        Http.post(Urls.CIRCLE_USER_INDEX, params, new GsonCallback<ObjectBaseEntity<CircleFirendEntity>>() {
            @Override
            public void onError(Call call, Exception e, int i) {
                hideWaitDialog();
                layout_refresh.finishRefresh();
            }

            @Override
            public void onResponse(ObjectBaseEntity<CircleFirendEntity> response, int i) {
                hideWaitDialog();
                if (response.success()) {
                    info = response.data;
                    view.setVisibility(View.VISIBLE);
                    circleFirendAdapter.clearAll();
                    circleFirendAdapter.addData(response.getData().getCircle());
                   /* GlideApp.with(SearchResultUserActivity.this)
                            .load(Urls.address + info.getUser().getHeader())
                            .centerCrop()
                            .error(R.drawable.default_head)
                            .placeholder(R.drawable.default_head).into(image);*/
                    ImageLoadUtil.getInstance().loadImage(image, Urls.address + info.getUser().getHeader(), R.drawable.default_head, 60, 60);
                    tv_name.setText(info.getUser().getName());
                    if (info.getUser().sex == 0) {
                        sex.setImageResource(R.drawable.icon2);
                    } else {
                        sex.setImageResource(R.drawable.icon0);
                    }
                } else {
                    showToast(response.getMessage());
                }
            }
        });
    }


    /**
     * 添加好友
     */
    private void addFriend() {
        Tools.addFriend(this, data.user_id);
    }
}


