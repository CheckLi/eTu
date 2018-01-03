package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.SceneServiceEntity;
import com.yitu.etu.entity.SceneShopProductEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.CarouselView;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2018/1/3.
 */
public class SceneShopYwDetailActivity extends BaseActivity {

    private TextView tv_des;
    private SceneServiceEntity.ListBean shopData;
    private TextView tv_address;
    private TextView tv_price;
    private TextView tv_ts;
    private CarouselView carouselView;
    private TextView tv_good;
    private TextView tv_money;
    private TextView tv_number;
    private int number = 1;
    double price = 0d;
    private ImageView image;
    private int type;
    private String id;

    @Override
    public int getLayout() {
        return R.layout.activity_scene_shop_yw_detail;
    }

    @Override
    public void initActionBar() {
//        shopData = (SceneServiceEntity.ListBean) getIntent().getSerializableExtra("SceneServiceEntity");
//        shopProductEntity = (ShopProductEntity) getIntent().getSerializableExtra("ShopProductEntity");
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
                            } else if (position == 1 && shopData != null) {
                                Poi end = new Poi(shopData.getAddress(), new LatLng(Double.valueOf(shopData.getAddress_lat()), Double.valueOf(shopData.getAddress_lng())), "");
                                Tools.navi(context, end);

                            }
                            if (position == 2) {
                                shopProductCollect();
                            }
                            if (position == 3) {
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
        tv_des = (TextView) findViewById(R.id.tv_des);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_ts = (TextView) findViewById(R.id.tv_ts);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        tv_good = (TextView) findViewById(R.id.tv_good);
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void getData() {
        setTitle(getIntent().getStringExtra("title"));

        type=getIntent().getIntExtra("type",0);
        id=getIntent().getIntExtra("id",0)+"";
        HashMap<String, String> params = new HashMap<>();
        params.put("shop_id",id + "");
        showWaitDialog("获取中...");
        params.put("page",  "1");
        Http.post(Urls.SHOP_GET_PRODUCT, params, new GsonCallback<ObjectBaseEntity<SceneShopProductEntity>>() {


            @Override
            public void onError(Call call, Exception e, int id) {
                hideWaitDialog();
                showToast("加载失败");
            }

            @Override
            public void onResponse(ObjectBaseEntity<SceneShopProductEntity> response, int id) {
                if (response.success()) {
                    findViewById(R.id.fr_content).setVisibility(View.VISIBLE);
                    shopData=response.getData().getInfo();
                    ImageLoadUtil.getInstance().loadImage(image, Urls.address + shopData.getUser().getHeader(),R.drawable.default_head, 50, 50);
                    tv_des.setText(shopData.getDes());
                    tv_address.setText("地址：" + shopData.getAddress());
                    tv_price.setText(shopData.getPrice() + "");
                    price = Double.valueOf(shopData.getPrice());
                    tv_money.setText(shopData.getPrice() + "");
                    tv_ts.setText("特色：" + shopData.getTese());
                    tv_good.setText(shopData.getGood() + "");
                    ArrayList<String> paths=new ArrayList<>();
                    paths.add(shopData.getImage());
                    carouselView.setPath(paths);

                }
                hideWaitDialog();

            }
        });
    }

    @Override
    public void initListener() {

    }

    public void shopProductCollect() {
        if (EtuApplication.getInstance().isLogin()) {
            HashMap<String, String> params = new HashMap<>();
            if (shopData == null) {
                return;
            }
            params.put("id", id + "");
            params.put("type", type+"");
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

    public void sendDz() {
        if (EtuApplication.getInstance().isLogin()) {
            if (shopData == null) {
                return;
            }
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
                        shopData.setGood(shopData.getGood() + 1);
                        tv_good.setText(String.valueOf(shopData.getGood()));
                    }
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
        if (v.getId() == R.id.tv_address && shopData != null) {
            Poi end = new Poi(shopData.getAddress(), new LatLng(Double.valueOf(shopData.getAddress_lat()), Double.valueOf(shopData.getAddress_lng())), "");
            Tools.navi(context, end);

        }
        if (v.getId() == R.id.btn_jia) {
            number = number + 1;
            tv_number.setText(number + "");
            tv_money.setText("￥" + price * number);
        }
        if (v.getId() == R.id.btn_jian) {
            number = number - 1;
            if (number < 1) {
                number = 1;
            }
            tv_number.setText(number + "");
            tv_money.setText("￥" + price * number);
        }
        if (v.getId() == R.id.img_my_shop) {
            startActivity(new Intent(context, BuyCarActivity.class));
        }
        if (v.getId() == R.id.btn_add_buy) {
//            BuyCarUtil.addBuyCar(shopProductEntity);
            showToast("加入成功");
        }

        if (v.getId() == R.id.btn_send_order) {
//            Map<String,String> params=new HashMap<>();
//            params.put("product_id",shopProductEntity.getId()+"");
//            params.put("count",number+"");
//            PayUtil.getInstance(-1,(float) (price * number),"购买"+shopProductEntity.getName(), BuyType.INSTANCE.getTYPE_BUY_SHOP_PROJECT())
//                    .toPayActivity(this,params);
        }
        if (v.getId() == R.id.image) {
            Tools.startChat(shopData.getName(),shopData.getUser_id()+"","可以一起去旅行吗？",this);
        }

    }
}
