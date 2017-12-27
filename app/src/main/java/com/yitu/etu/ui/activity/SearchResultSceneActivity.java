package com.yitu.etu.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.ArrayBaseEntity;
import com.yitu.etu.entity.CommentEntity;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.SceneEntity;
import com.yitu.etu.entity.UserInfo;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.CommentAdapter;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.ExpandableTextView;
import com.yitu.etu.widget.ListSlideView;
import com.yitu.etu.widget.SendMsgView;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class SearchResultSceneActivity extends BaseActivity {

    private ListSlideView listView;
    private CommentAdapter commentAdapter;
    private SendMsgView send_msg;
    private SmartRefreshLayout layout_refresh;
    private String id;
    private View view;
    private TextView tv_address;
    private TextView bq_feature;
    private TextView tv_feature;
    private TextView text;
    private TextView tv_good;
    private SceneEntity.SpotBean spotBean;
    private TextView tv_title;
    private ExpandableTextView expandable_text;

    @Override
    public int getLayout() {
        return R.layout.activity_search_result_scene;
    }

    @Override
    public void initActionBar() {
        setTitle("景点详情");
        id = getIntent().getStringExtra("id");
        mActionBarView.setRightImage(R.drawable.icon145, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EtuApplication.getInstance().isLogin()) {
                    Tools.getPopupWindow(SearchResultSceneActivity.this, new String[]{"发起行程", "导航过去", "我要报错", "加入收藏", "分享给朋友"}, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 3) {
                                spotCollect();
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
        listView = (ListSlideView) findViewById(R.id.listView);
        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);

        commentAdapter = new CommentAdapter(this);
        listView.setAdapter(commentAdapter);
        listView.setDividerHeight(0);
        send_msg = (SendMsgView) findViewById(R.id.send_msg);

        send_msg.setSendMsg(new SendMsgView.SendMsgListener() {
            @Override
            public void send(String text) {
                sendMsg(text);
            }
        });
        layout_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getSpotInfo(true);
            }
        });
        layout_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getComment(false);
            }
        });
        getSpotInfo(true);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    //活动收藏
    public void getSpotInfo(final boolean refresh) {
        showWaitDialog("加载中...");
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        Http.post(Urls.SPOT_INFO, params, new GsonCallback<ObjectBaseEntity<SceneEntity>>() {
            @Override
            public void onError(Call call, Exception e, int i) {
                hideWaitDialog();
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();
            }

            @Override
            public void onResponse(ObjectBaseEntity<SceneEntity> response, int i) {
                if (response.success()) {
                    commentAdapter.clearAll();
                    page = 1;
                    if (view == null) {
                        spotBean = response.getData().getSpot();
                        view = getLayoutInflater().inflate(R.layout.activity_map_search_scene, null);
                        tv_address = (TextView) view.findViewById(R.id.tv_address);
                        bq_feature = (TextView) view.findViewById(R.id.bq_feature);
                        tv_feature = (TextView) view.findViewById(R.id.tv_feature);
                        tv_title = (TextView) view.findViewById(R.id.tv_title);
                        tv_good = (TextView) view.findViewById(R.id.tv_good);
                        expandable_text = (ExpandableTextView) view.findViewById(R.id.expandable_text);
                        listView.addHeaderView(view);
                    }
                    tv_good.setText(spotBean.getGood() + "");
                    tv_address.setText(spotBean.getAddress());
                    tv_title.setText(spotBean.getTitle());
                    expandable_text.setText(spotBean.getText());
                    expandable_text.setListener(new ExpandableTextView.OnExpandStateChangeListener() {
                        @Override
                        public void onExpandStateChanged(boolean isExpanded) {
                            if (isExpanded) {
                                expandable_text.id_expand_textview.setVisibility(View.GONE);
                            }
                        }
                    });
                    List<String> feature = spotBean.getFeature();
                    if (feature == null || feature.size() == 0) {
                        tv_feature.setVisibility(View.GONE);
                        bq_feature.setVisibility(View.GONE);
                    } else {
                        tv_feature.setVisibility(View.VISIBLE);
                        bq_feature.setVisibility(View.VISIBLE);
                        StringBuffer strFeature = new StringBuffer("");
                        for (String str :
                                feature) {
                            strFeature.append(str + "、");
                        }
                        tv_feature.setText(strFeature.substring(0, strFeature.length() - 1));
                    }
                    getComment(refresh);

                } else {
                    hideWaitDialog();
                    layout_refresh.finishRefresh();
                    layout_refresh.finishLoadmore();
                }
            }
        });
    }

    public void getComment(boolean refresh) {
        if (!refresh) {
            showWaitDialog("加载中...");
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("page", page + "");
        Http.post(Urls.SPOT_GET_COMMENT, params, new GsonCallback<ArrayBaseEntity<CommentEntity>>() {
            @Override
            public void onError(Call call, Exception e, int i) {
                hideWaitDialog();
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();
            }

            @Override
            public void onResponse(ArrayBaseEntity<CommentEntity> response, int i) {
                Log.e("onResponse", response.message);
                if (response.success()) {
                    commentAdapter.addData(response.getData());
                    page++;
                }
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();
                hideWaitDialog();
            }
        });
    }

    public void sendDz() {
        if (EtuApplication.getInstance().isLogin()) {
            HashMap<String, String> params = new HashMap<>();
            params.put("spot_id", id);
            params.put("type", "1");
            Http.post(Urls.SPOT_ADD_GOOD, params, new GsonCallback<HttpStateEntity>() {
                @Override
                public void onError(Call call, Exception e, int i) {
                }

                @Override
                public void onResponse(HttpStateEntity response, int i) {
                    if (response.success()) {
                        spotBean.setGood(spotBean.getGood() + 1);
                        tv_good.setText(String.valueOf(spotBean.getGood()));
                    }
                    ToastUtil.showMessage(response.getMessage());
                }
            });
        } else {
            showToast("请登录");
        }
    }

    public void spotCollect() {
        if (EtuApplication.getInstance().isLogin()) {
            HashMap<String, String> params = new HashMap<>();
            params.put("spot_id", id);
            Http.post(Urls.SPOT_COLLECT, params, new GsonCallback<HttpStateEntity>() {
                @Override
                public void onError(Call call, Exception e, int i) {
                }

                @Override
                public void onResponse(HttpStateEntity response, int i) {
                    ToastUtil.showMessage(response.getMessage());
                }
            });
        } else {
            showToast("请登录");
        }
    }

    public void sendMsg(String text) {
        if (EtuApplication.getInstance().isLogin()) {
            HashMap<String, String> params = new HashMap<>();
            params.put("spot_id", id);
            params.put("text", text);
            params.put("pcomment_id", "0");
            Http.post(Urls.SPOT_ADD_COMMENT, params, new GsonCallback<ObjectBaseEntity<CommentEntity>>() {
                @Override
                public void onError(Call call, Exception e, int i) {
                }

                @Override
                public void onResponse(ObjectBaseEntity<CommentEntity> response, int i) {
                    CommentEntity.UserBean userBean = new CommentEntity.UserBean();
                    UserInfo userInfo = EtuApplication.getInstance().getUserInfo();
                    userBean.setHeader(userInfo.getHeader());
                    userBean.setName(userInfo.getName());
                    userBean.setId(userInfo.getId());
                    response.getData().setUser(userBean);
                    commentAdapter.addToFirst(response.getData());
                    ToastUtil.showMessage(response.getMessage());
                }
            });
        } else {
            showToast("请登录");
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.img_dz) {
            sendDz();
        }
    }
}