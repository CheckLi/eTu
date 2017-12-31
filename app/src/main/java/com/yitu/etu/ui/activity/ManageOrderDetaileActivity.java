package com.yitu.etu.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.OrderDetail;
import com.yitu.etu.entity.Product2;
import com.yitu.etu.entity.ShopOrderEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.DateUtil;
import com.yitu.etu.util.Tools;

import java.util.HashMap;

import okhttp3.Call;

public class ManageOrderDetaileActivity extends BaseActivity {
    private ShopOrderEntity data;
    private TableRow tr_order;
    private TextView tv_address;
    private TextView tv_phone;

    @Override
    public int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initActionBar() {
        setTitle("订单详情");
        data = (ShopOrderEntity) getIntent().getSerializableExtra("data");
        tr_order = (TableRow) findViewById(R.id.tr_order);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", data.getId() + "");
            Http.post(Urls.URL_ORDER_DETAIL, hashMap, new GsonCallback<ObjectBaseEntity<OrderDetail>>() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(ObjectBaseEntity<OrderDetail> response, int id) {
                    if (response.success()) {
                        final OrderDetail data= response.getData();
                        Product2 product= data.getProduct().get(0);
                        ((TextView) tr_order.getChildAt(0)).setText(product.getName()+"("+product.getCount()+"份)");
                        ((TextView) tr_order.getChildAt(1)).setText(data.getId()+"");
                        ((TextView) tr_order.getChildAt(2)).setText(data.getPrice()+"");
                        ((TextView) tr_order.getChildAt(3)).setText(DateUtil.getTime(data.getUpdated()+"",null));
                        tv_phone.setText("电话："+data.getShop().getPhone());
                        tv_address.setText("地址："+data.getShop().getAddress());
                        tv_address.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Poi end = new Poi(data.getShop().getAddress(), new LatLng(data.getShop().getAddressLat(), data.getShop().getAddressLng()), "");
                                Tools.navi(context, end);
                            }
                        });
                    }
                }
            });

    }

    @Override
    public void initListener() {

    }
}
