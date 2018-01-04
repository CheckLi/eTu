package com.yitu.etu.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.ProductListBean;
import com.yitu.etu.entity.ProductListBean2;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;

import java.util.HashMap;

import okhttp3.Call;

public class ExchangeProductDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView image;
    private ProductListBean data;
    private TextView tv_name;
    private TextView tv_sy_count;
    private TextView tv_price;
    private TextView tv_my_count;

    @Override
    public int getLayout() {
        return R.layout.activity_exchange_product_detail;
    }

    @Override
    public void initActionBar() {
        setTitle("商品详情");
    }

    @Override
    public void initView() {
        data = (ProductListBean) getIntent().getSerializableExtra("data");
        image = (ImageView) findViewById(R.id.image);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sy_count = (TextView) findViewById(R.id.tv_sy_count);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_my_count = (TextView) findViewById(R.id.tv_my_count);
    }

    @Override
    public void getData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", data.getId() + "");
        Http.post(Urls.address + "/product/info", params, new GsonCallback<ObjectBaseEntity<ProductListBean2>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showToast("网络异常");
            }

            @Override
            public void onResponse(ObjectBaseEntity<ProductListBean2> response, int id) {
                if (response.success()) {
                    findViewById(R.id.li_content).setVisibility(View.VISIBLE);
                    ProductListBean2 data = response.getData();
                    tv_name.setText(data.getName());
                    tv_price.setText("现需平安符:" + data.getPrice());
                    tv_sy_count.setText("剩余数量:" + data.getCount());
                    tv_my_count.setText("您的平安符:" + data.getUser().getSafecount());
                    ImageLoadUtil.getInstance().loadImage(image, Urls.address + data.getImage(), -1, -1);
                }
            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        HashMap<String, String> params = new HashMap<>();
        params.put("product_id", data.getId() + "");
        Http.post(Urls.address + "/product/buy", params, new GsonCallback<HttpStateEntity>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showToast("网络异常");
            }

            @Override
            public void onResponse(HttpStateEntity response, int id) {
                showToast(response.getMessage());
                if (response.success()) {
                    finish();
                }

            }
        });
    }
}
