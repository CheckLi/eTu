package com.yitu.etu.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
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
import com.yitu.etu.ui.adapter.ChooseAreaAdapter;
import com.yitu.etu.ui.adapter.CommentAdapter;
import com.yitu.etu.ui.adapter.YjImageAdapter;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.CarouselView;
import com.yitu.etu.widget.ExpandableTextView;
import com.yitu.etu.widget.ListSlideView;
import com.yitu.etu.widget.MListView;
import com.yitu.etu.widget.MgridView1;
import com.yitu.etu.widget.SendMsgView;
import com.yitu.etu.widget.chat.ShareMessage;

import java.util.ArrayList;
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
    private CarouselView carouselView;
    private MgridView1 gridView;
    private YjImageAdapter yjImageAdapter;
    private LinearLayout li_yj;

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
                            if (position == 0 && spotBean != null) {
                                Intent intent = new Intent(SearchResultSceneActivity.this, RelaseTravelActivity.class);
                                intent.putExtra("data", spotBean);
                                startActivity(intent);
                            } else if (position == 1) {
                                if (spotBean != null) {
                                    Poi end = new Poi(spotBean.address, new LatLng(Double.valueOf(spotBean.getAddress_lat()), Double.valueOf(spotBean.getAddress_lng())), "");
                                    Tools.navi(SearchResultSceneActivity.this, end);
                                }
                            }
                            if (position == 2) {
                                showErrorDialog();
                            }
                            if (position == 3) {
                                spotCollect();
                            }
                            if (position == 4&&spotBean!=null) {
                                ShareFriendActivity.startActivity(context,
                                        ShareMessage.obtain(spotBean.getAddress()
                                                ,spotBean.title,
                                                spotBean.getImages().get(0),
                                                "1",spotBean.id+""));
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

        view = getLayoutInflater().inflate(R.layout.activity_map_search_scene, null);
        view.setVisibility(View.GONE);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        bq_feature = (TextView) view.findViewById(R.id.bq_feature);
        tv_feature = (TextView) view.findViewById(R.id.tv_feature);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_good = (TextView) view.findViewById(R.id.tv_good);
        li_yj = (LinearLayout) view.findViewById(R.id.li_yj);
        gridView = (MgridView1) view.findViewById(R.id.gridView);
        yjImageAdapter = new YjImageAdapter(SearchResultSceneActivity.this);
        gridView.setAdapter(yjImageAdapter);
        expandable_text = (ExpandableTextView) view.findViewById(R.id.expandable_text);
        listView.addHeaderView(view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(SearchResultSceneActivity.this, TravelsDetailActivity.class);
                intent.putExtra("travels_id", yjImageAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
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

    public void showErrorDialog() {
        final Dialog dialog = new Dialog(this, R.style.LoadingDialog);
        View view = getLayoutInflater().inflate(R.layout.dialog_list, null);
        MListView listView = (MListView) view.findViewById(R.id.listView);
        TextView dialog_title = (TextView) view.findViewById(R.id.tv_title);
        dialog_title.setText("请选择报错项");
        ChooseAreaAdapter chooseAreaAdapter = new ChooseAreaAdapter(this);
        listView.setAdapter(chooseAreaAdapter);
        List<String> data = new ArrayList<>();
        data.add("图片报错");
        data.add("地址报错");
        data.add("错误描述");
        chooseAreaAdapter.addData(data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();
                if (spotBean != null) {
                    if (EtuApplication.getInstance().isLogin()) {
                        Intent intent = new Intent(SearchResultSceneActivity.this, SendSceneMsgActivity.class);
                        intent.putExtra("type", position);
                        intent.putExtra("id", spotBean.getId() + "");
                        startActivity(intent);
                    } else {
                        showToast("请登录");
                    }
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
        dialog.show();
    }

    //景点详情
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
                    spotBean = response.getData().getSpot();
                    commentAdapter.clearAll();
                    page = 1;
                    view.setVisibility(View.VISIBLE);
                    yjImageAdapter.clearAll();
                    yjImageAdapter.addData(response.getData().getTitlelist());
                    if (yjImageAdapter.getCount() == 0) {
                        li_yj.setVisibility(View.GONE);
                    } else {
                        li_yj.setVisibility(View.VISIBLE);
                    }
                    carouselView.setPath(spotBean.getImages());
                    tv_good.setText(spotBean.getGood() + "");
                    tv_address.setText(spotBean.getAddress());
                    tv_title.setText(spotBean.getTitle());
                    expandable_text.setText(spotBean.getText());
                    expandable_text.setListener(new ExpandableTextView.OnExpandStateChangeListener() {
                        @Override
                        public void onExpandStateChanged(boolean isExpanded) {
                            if (!isExpanded) {
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

    public void getComment(final boolean refresh) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        if (refresh) {
            params.put("page", "1");
        } else {
            params.put("page", page + "");
        }
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
                    if (refresh) {
                        commentAdapter.clearAll();
                    }
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
        Intent intent;
        if (v.getId() == R.id.img_dz) {
            sendDz();
        } else if (v.getId() == R.id.li_ms) {
            intent = new Intent(SearchResultSceneActivity.this, SceneServiceActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("spot_id", id);
            intent.putExtra("name", spotBean.getTitle());
            startActivity(intent);
        } else if (v.getId() == R.id.li_zs) {
            intent = new Intent(SearchResultSceneActivity.this, SceneServiceActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("name", spotBean.getTitle());
            intent.putExtra("spot_id", id);
            startActivity(intent);
        } else if (v.getId() == R.id.li_yw) {
            intent = new Intent(SearchResultSceneActivity.this, SceneServiceActivity.class);
            intent.putExtra("name", spotBean.getTitle());
            intent.putExtra("type", 3);
            intent.putExtra("spot_id", id);
            startActivity(intent);
        }
    }
}