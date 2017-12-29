package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.MerchantBaseEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.OrderSceneEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.DateUtil;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.CarouselView;

import java.util.HashMap;

import okhttp3.Call;

public class SearchResultOrderSceneActivity extends BaseActivity {


    private String title;
    private String id;
    private TextView tv_address;
    private TextView tv_collect;
    private TextView tv_cy_time;
    private TextView tv_xj_time;
    private ImageView image;
    private TextView text;
    private TextView tv_jd;
    private TextView tv_state;
    private TextView tv_need_money;
    private CarouselView carouselView;

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
                        if (position == 0) {
                            if (EtuApplication.getInstance().isLogin()) {
                                startActivity(new Intent(SearchResultOrderSceneActivity.this, RelaseTravelActivity.class));
                            } else {
                                ToastUtil.showMessage("请登录");
                            }
                        }

                    }
                }, null).

                        showAsDropDown(v);

            }
        });
    }

    @Override
    public void initView() {
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_collect = (TextView) findViewById(R.id.tv_collect);

        tv_cy_time = (TextView) findViewById(R.id.tv_cy_time);

        tv_xj_time = (TextView) findViewById(R.id.tv_xj_time);
        tv_jd = (TextView) findViewById(R.id.tv_jd);
        text = (TextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_need_money = (TextView) findViewById(R.id.tv_need_money);
        carouselView.setTopRadius(Tools.dp2px(this, 5));
    }

    @Override
    public void getData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        setTitle(title);
        ActionInfo();
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
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        Http.post(Urls.ACTION_INFO, params, new GsonCallback<ObjectBaseEntity<OrderSceneEntity>>() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(ObjectBaseEntity<OrderSceneEntity> response, int i) {
                if (response.success()) {
                    OrderSceneEntity data = response.getData();
                    tv_address.setText("地址：" + data.getAddress());
                    tv_cy_time.setText("参与时间：" + DateUtil.getTime(data.getJoin_starttime() + "", "yyyy-MM-dd HH:mm") + "至" + DateUtil.getTime(data.getJoin_endtime() + "", "yyyy-MM-dd HH:mm"));
                    tv_xj_time.setText("行程时间：" + DateUtil.getTime(data.getStart_time() + "", "yyyy-MM-dd HH:mm") + "至" + DateUtil.getTime(data.getEnd_time() + "", "yyyy-MM-dd HH:mm"));
                    tv_state.setText("结束");
                    tv_jd.setText("进度 1/20");
                    carouselView.setPath(data.getImages());
                    text.setText(data.getText());
                    tv_need_money.setText("dsad");
                    ImageLoadUtil.getInstance().loadImage(image, Urls.address + data.getUser().getHeader(), 200, 200);

                }
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_collect:
                ActionCollect();
                break;
        }
    }
}