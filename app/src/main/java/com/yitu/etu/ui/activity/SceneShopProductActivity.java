package com.yitu.etu.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.SceneServiceEntity;
import com.yitu.etu.entity.SceneShopProductEntity;
import com.yitu.etu.entity.ShopProductEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.ManageProductAdapter2;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.CarouselView;
import com.yitu.etu.widget.ListSlideView;
import com.yitu.etu.widget.chat.ShareMessage;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

public class SceneShopProductActivity extends BaseActivity {

    private View view;
    private ListSlideView listView;
    private ManageProductAdapter2 manageProductAdapter;
    private SmartRefreshLayout layout_refresh;
    private CarouselView carouselView;
    private SceneServiceEntity.ListBean data;
    private TextView tv_good;
    private TextView tv_des;
    private TextView tv_phone;
    private TextView tv_address;
    private int type;
    private int id;
    private String title;
    public SceneShopProductEntity response;
    private ImageView image;

    @Override
    public int getLayout() {
        return R.layout.activity_scene_service;
    }

    @Override
    public void initActionBar() {
        mActionBarView.setRightImage(R.drawable.icon145, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EtuApplication.getInstance().isLogin()) {
                    Tools.getPopupWindow(context, new String[]{"发起行程", "导航过去", "加入收藏", "分享给朋友"}, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                Intent intent = new Intent(context, RelaseTravelActivity.class);
//                                intent.putExtra("data", null);
                                startActivity(intent);
                            } else if (position == 1 && data != null) {
                                Poi end = new Poi(data.getAddress(), new LatLng(Double.valueOf(data.getAddress_lat()), Double.valueOf(data.getAddress_lng())), "");
                                Tools.navi(context, end);

                            }
                            if (position == 2) {
                                shopCollect();
                            }
                            if (position == 3&&data!=null) {
                                ShareFriendActivity.startActivity(context,
                                        ShareMessage.obtain(data.getAddress()
                                                ,data.getName(),
                                                data.getImage(),
                                                type+"",data.getId()+""));
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
//        data = (SceneServiceEntity.ListBean) getIntent().getSerializableExtra("data");
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        setTitle(title);
        listView = (ListSlideView) findViewById(R.id.listview);
        findViewById(R.id.img_my_shop).setVisibility(View.VISIBLE);
        view = getLayoutInflater().inflate(R.layout.activity_scene_shop_product, null);
        tv_good = (TextView) view.findViewById(R.id.tv_good);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        image = (ImageView) view.findViewById(R.id.image);
        manageProductAdapter = new ManageProductAdapter2(this);
        layout_refresh = (SmartRefreshLayout) findViewById(R.id.layout_refresh);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        view.setVisibility(View.GONE);
        listView.addHeaderView(view);
        listView.setAdapter(manageProductAdapter);
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
        refresh(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    if (type == 6) {
                        showYdDialog(manageProductAdapter.getItem(position - 1));
                    } else {
                        Intent intent = new Intent(context, SceneShopProductDetailActivity.class);
                        intent.putExtra("title", response.getInfo().getName());
                        intent.putExtra("type", type);
                        intent.putExtra("id", manageProductAdapter.getItem(position - 1).getId() + "");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void showYdDialog(final ShopProductEntity data) {
        final Dialog dialog = new Dialog(this, R.style.transparentDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_yd, null);
        TextView tv_order = (TextView) dialogView.findViewById(R.id.tv_order);
        TextView tv_price = (TextView) dialogView.findViewById(R.id.tv_price);
        TextView tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        TextView tv_des = (TextView) dialogView.findViewById(R.id.tv_des);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.imageView);
        tv_price.setText("￥" + data.getPrice());
        tv_title.setText(data.getName());
        tv_des.setText(data.getDes());
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(dialogView);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ChooseReservation.class);
                intent.putExtra("data",data);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageLoadUtil.getInstance().loadImage(imageView, Urls.address + data.getImage(), -1, -1);
        dialog.show();
    }

    public void shopCollect() {
        if (EtuApplication.getInstance().isLogin()) {
            HashMap<String, String> params = new HashMap<>();
            params.put("id", id + "");
            params.put("type", type + "");
            Http.post(Urls.SHOP_COLLECT, params, new GsonCallback<HttpStateEntity>() {
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

    public void refresh(final boolean isRefresh) {
        if (isRefresh) {
            showWaitDialog("加载中...");
        }
        final HashMap<String, String> params = new HashMap<>();
        params.put("shop_id", id + "");
        params.put("page", page + "");
        Http.post(Urls.SHOP_GET_PRODUCT, params, new GsonCallback<ObjectBaseEntity<SceneShopProductEntity>>() {


            @Override
            public void onError(Call call, Exception e, int id) {
                layout_refresh.finishRefresh();
                layout_refresh.finishLoadmore();
                hideWaitDialog();
            }

            @Override
            public void onResponse(ObjectBaseEntity<SceneShopProductEntity> response, int id) {
                SceneShopProductActivity.this.response = response.getData();
                if (response.success()) {
                    if (isRefresh) {
                        data = response.getData().getInfo();
                        view.setVisibility(View.VISIBLE);
                        manageProductAdapter.clearAll();
                        tv_des.setText(data.getDes());
                        tv_phone.setText("电话：" + data.getPhone());
                        tv_address.setText("地址：" + data.getAddress());
                        tv_good.setText(data.getGood() + "");
                        ArrayList<String> path = new ArrayList<>();
                        path.add(data.getImage());
                        carouselView.setPath(path);
                        ImageLoadUtil.getInstance().loadImage(image, Urls.address + data.getUser().getHeader(), R.drawable.default_head, 200, 200);
                    }
                    manageProductAdapter.addData(response.getData().getData());
                    RefreshSuccess(layout_refresh, isRefresh, response.getData().getData().size());
                }
                hideWaitDialog();

            }
        });
    }

    public void sendDz() {
        if (EtuApplication.getInstance().isLogin()) {
            HashMap<String, String> params = new HashMap<>();
            params.put("id", id + "");
            params.put("type", "0");
            Http.post(Urls.SHOP_ADD_GOOD, params, new GsonCallback<HttpStateEntity>() {
                @Override
                public void onError(Call call, Exception e, int i) {
                }

                @Override
                public void onResponse(HttpStateEntity response, int i) {
                    if (response.success()) {
                        data.setGood(data.getGood() + 1);
                        tv_good.setText(String.valueOf(data.getGood()));
                    }
                    ToastUtil.showMessage(response.getMessage());
                }
            });
        } else {
            showToast("请登录");
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    public void onClick(View v) {
        if (v.getId() == R.id.img_dz) {
            sendDz();
        }
        if (v.getId() == R.id.tv_address && data != null) {
            Poi end = new Poi(data.getAddress(), new LatLng(Double.valueOf(data.getAddress_lat()), Double.valueOf(data.getAddress_lng())), "");
            Tools.navi(context, end);

        }
        if (v.getId() == R.id.img_my_shop) {
            startActivity(new Intent(context, BuyCarActivity.class));
        }
        if (v.getId() == R.id.image) {
            Tools.startChat(response.getInfo().getName(), response.getInfo().getUser_id() + "", "", this);
        }
    }
}
