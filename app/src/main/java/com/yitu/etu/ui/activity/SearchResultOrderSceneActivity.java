package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.MerchantBaseEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.OrderSceneEntity;
import com.yitu.etu.entity.UserInfo;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.DateUtil;
import com.yitu.etu.util.TextUtils;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.util.pay.BuyType;
import com.yitu.etu.util.pay.PayUtil;
import com.yitu.etu.widget.CarouselView;
import com.yitu.etu.widget.chat.ShareMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;

public class SearchResultOrderSceneActivity extends BaseActivity {


    private String title;
    private String id;
    private TextView tv_address;
    private TextView tv_collect;
    private TextView tv_ql;
    private TextView tv_js;
    private TextView tv_cy_time;
    private TextView tv_xj_time;
    private ImageView image;
    private TextView text;
    private TextView tv_jd;
    private TextView tv_state;
    private TextView tv_need_money;
    private CarouselView carouselView;
    private LinearLayout li_content;
    private boolean isFromMe;
    private LinearLayout li2;
    private int status;
    private OrderSceneEntity data;

    @Override
    public int getLayout() {
        return R.layout.activity_search_result_order_scene;
    }

    @Override
    public void initActionBar() {
        mActionBarView.setRightImage(R.drawable.icon145, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.getPopupWindow(SearchResultOrderSceneActivity.this, new String[]{"发布出行", "分享给朋友"}, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (EtuApplication.getInstance().isLogin()) {
                            if (position == 0) {
                                startActivity(new Intent(SearchResultOrderSceneActivity.this, RelaseTravelActivity.class));
                            } else if (position == 1 && data != null) {
                                ShareFriendActivity.startActivity(context,
                                        ShareMessage.obtain(data.getAddress()
                                                , data.name,
                                                data.getImages().get(0),
                                                "4", data.id + ""));
                            }
                        } else {
                            ToastUtil.showMessage("请登录");
                        }

                    }
                }, null).

                        showAsDropDown(v);

            }
        });
    }

    @Override
    public void initView() {
        li_content = (LinearLayout) findViewById(R.id.li_content);
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        tv_ql = (TextView) findViewById(R.id.tv_ql);
        tv_js = (TextView) findViewById(R.id.tv_js);

        tv_cy_time = (TextView) findViewById(R.id.tv_cy_time);

        tv_xj_time = (TextView) findViewById(R.id.tv_xj_time);
        tv_jd = (TextView) findViewById(R.id.tv_jd);
        text = (TextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_need_money = (TextView) findViewById(R.id.tv_need_money);
        carouselView.setTopRadius(Tools.dp2px(this, 5));
        li2 = (LinearLayout) findViewById(R.id.li2);
    }

    @Override
    public void getData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        isFromMe = getIntent().getBooleanExtra("isFrom", false);
        initBtn(null);
        setTitle(title);
        ActionInfo();
    }

    private void initBtn(final OrderSceneEntity bean) {

        if (isFromMe) {
            li2.setVisibility(View.VISIBLE);
            tv_state.setVisibility(View.GONE);
            if (bean != null) {
                if (TextUtils.isEmpty(bean.getChat_id())) {
                    tv_ql.setText("创建群聊");
                } else {
                    tv_ql.setText("发起群聊");
                }
                tv_ql.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(bean.getChat_id())) {
                            //创建群聊
                            createChat(bean.id+"");
                        } else {
                            //发起群聊
                            Tools.startGroupChat(v.getContext(),bean.chat_id,bean.name+"群聊");
                        }
                    }
                });
               // 已申请结算这个，是根据status状态来的，当为3时表示 已申请结算；当为4时表示 结算完成
                if(bean.status==3) {
                    tv_js.setEnabled(false);
                    tv_js.setText("已申请结算");
                }else  if(bean.status==4){
                    tv_js.setEnabled(false);
                    tv_js.setText("结算完成");
                }
            }
        } else if(bean!=null){
            /**
             * 快乐 ^ 物语  16:55:19
             对于用户来说有一个is_join字段，
             如果是0的话，则表示没参与，
             就有一个确定预约按钮，
             如果是1的话，当状态为2时，
             则只有一个已确认付款的不可点击的按钮，当有chat_id时，则有进入群聊的按钮
             */
            switch(bean.is_join){
                case 2:
                    if(TextUtils.isEmpty(bean.chat_id)){
                        tv_state.setText("已确认付款");
                    }else{
                        tv_state.setText("进入群聊");
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 创建群聊
     * @param action_id
     */
    private void createChat(final String action_id){
        List<UserInfo> list=new ArrayList<>();
        UserInfo userInfo=new UserInfo();
        userInfo.setId(22);
        list.add(userInfo);
        data.setUserlist(list);
        if(data.getUserlist()==null||data.getUserlist().size()==0) {
            showToast("没有用户参与无法创建群聊");
            return;
        }
        List<String> targetUserIds=new ArrayList<>();
        for (UserInfo info : data.getUserlist()) {
            targetUserIds.add(info.getId()+"");
        }
        RongIM.getInstance().createDiscussionChat(context, targetUserIds, title, new RongIMClient.CreateDiscussionCallback() {
            @Override
            public void onSuccess(String s) {
                HashMap<String, String> map = new HashMap<>();
                map.put("chat_id", s);
                map.put("action_id", action_id);
                Http.post(Urls.URL_ACTION_CREATE_CHAT, map, new GsonCallback<ObjectBaseEntity<UserInfo>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(ObjectBaseEntity<UserInfo> response, int id) {
                        if (response.success()) {
                            UserInfo info = response.data;
                            io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(String.valueOf(info.getId()), info.getName(), Uri.parse(Urls.address + info.getHeader()));
                            RongIM.getInstance().refreshUserInfoCache(userInfo);
                        }else{
                            showToast(response.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }

    @Override
    public void initListener() {

    }

    //活动收藏

    public void ActionCollect() {
        if (EtuApplication.getInstance().isLogin()) {
            HashMap<String, String> params = new HashMap<>();
            params.put("action_id", id);
            Http.post(Urls.ACTION_COLLECT, params, new GsonCallback<ObjectBaseEntity<MerchantBaseEntity>>() {
                @Override
                public void onError(Call call, Exception e, int i) {

                }

                @Override
                public void onResponse(ObjectBaseEntity<MerchantBaseEntity> response, int i) {
                    if (response.success()) {
                        ToastUtil.showMessage("收藏成功");
                    } else {
                        ToastUtil.showMessage(response.getMessage());
                    }
                }
            });
        } else {
            ToastUtil.showMessage("请登录");
        }
    }

    //活动详情
    public void ActionInfo() {
        showWaitDialog("获取中...");
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        Http.post(Urls.ACTION_INFO, params, new GsonCallback<ObjectBaseEntity<OrderSceneEntity>>() {
            @Override
            public void onError(Call call, Exception e, int i) {
                hideWaitDialog();
            }

            @Override
            public void onResponse(ObjectBaseEntity<OrderSceneEntity> response, int i) {
                hideWaitDialog();
                if (response.success()) {
                    isFromMe = EtuApplication.getInstance().getUserInfo().getId() == response.data.getUser_id();
                    initBtn(response.data);
                    li_content.setVisibility(View.VISIBLE);
                    data = response.getData();
                    tv_address.setText("地址：" + data.getAddress());
                    tv_cy_time.setText("参与时间：" + DateUtil.getTime(data.getJoin_starttime() + "", "yyyy-MM-dd HH:mm") + "至" + DateUtil.getTime(data.getJoin_endtime() + "", "yyyy-MM-dd HH:mm"));
                    tv_xj_time.setText("行程时间：" + DateUtil.getTime(data.getStart_time() + "", "yyyy-MM-dd HH:mm") + "至" + DateUtil.getTime(data.getEnd_time() + "", "yyyy-MM-dd HH:mm"));
                    tv_state.setText(data.getStatus_name());
                    status = data.getStatus();
                    tv_jd.setText("进度 " + data.getHasnumber() + "/" + data.getNumber());
                    carouselView.setPath(data.getImages());
                    text.setText(data.getText());
                    if (Double.parseDouble(data.getMoney()) > 0d) {
                        tv_need_money.setText("需要出行费");
                    } else {
                        tv_need_money.setText("无需出行费");
                    }
                    ImageLoadUtil.getInstance().loadImage(image, Urls.address + data.getUser().getHeader(), 200, 200);

                }
            }
        });

    }

    public void ActionJs() {
        if (EtuApplication.getInstance().isLogin()) {
            showWaitDialog("申请中...");
            HashMap<String, String> params = new HashMap<>();
            params.put("action_id", id);
            Http.post(Urls.address + "/action/checkOrder", params, new GsonCallback<ObjectBaseEntity<MerchantBaseEntity>>() {
                @Override
                public void onError(Call call, Exception e, int i) {
                    hideWaitDialog();
                    showToast("申请失败");
                }

                @Override
                public void onResponse(ObjectBaseEntity<MerchantBaseEntity> response, int i) {
                    hideWaitDialog();
                    if(response.success()) {
                        tv_js.setText("已申请结算");
                        tv_js.setEnabled(false);
                    }
                    ToastUtil.showMessage(response.getMessage());
                }
            });
        } else {
            ToastUtil.showMessage("请登录");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_collect:
                ActionCollect();
                break;
            case R.id.tv_ql:
                //发起群聊
                Tools.createChagGroup(this);
                break;

            case R.id.tv_state:
                //参与活动
                if (status == 1) {
                    Map<String, String> params = new HashMap<>();
                    params.put("action_id", data.getId() + "");
                    PayUtil.getInstance(-1, data.getPrice(), "参与" + title + "付款", 2, BuyType.INSTANCE.getTYPE_BUY_ACTION())
                            .toPayActivity(this, params);
                }
                break;

            case R.id.tv_js:
                ActionJs();
                break;
        }
    }
}