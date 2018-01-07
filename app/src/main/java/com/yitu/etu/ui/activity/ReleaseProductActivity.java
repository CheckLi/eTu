package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.squareup.picasso.Callback;
import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ShopProductEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.FileUtil;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.MgridView1;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class ReleaseProductActivity extends BaseActivity {

    private ShopProductEntity data;
    private TextView tv_name;
    private TextView tv_intro;
    private TextView tv_price;
    private MgridView1 image_select;
    private ImageView btn_img;
    boolean isChoosebtn_img;
    private String shop_id;
    private SwitchButton xl_switch;
    private TextView tv_xl;

    @Override
    public int getLayout() {

        return R.layout.activity_release_product;
    }

    @Override
    public void initActionBar() {
        setTitle("商品编辑");
        setRightText("保存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                release();
            }
        });
    }

    @Override
    public void initView() {
        data = (ShopProductEntity) getIntent().getSerializableExtra("data");
        shop_id = getIntent().getStringExtra("shop_id");
        image_select = (MgridView1) findViewById(R.id.image_select);
        btn_img = (ImageView) findViewById(R.id.btn_img);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_xl = (TextView) findViewById(R.id.tv_xl);
        xl_switch = (SwitchButton) findViewById(R.id.xl_switch);
        xl_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    tv_xl.setVisibility(View.INVISIBLE);
                } else {
                    tv_xl.setVisibility(View.VISIBLE);
                }

            }
        });
        if (data != null) {
            tv_name.setText(data.getName());
            tv_intro.setText(data.getDes());
            tv_price.setText(data.getPrice());
            String[] imgs = data.getList_image().split("\\|");
            for (String path :
                    imgs) {
                image_select.add(Urls.address + path);
            }
            loadImage(Urls.address + data.getImage());
            xl_switch.setChecked(data.getSalecount() > 0);
            if(data.getSalecount() > 0){
                tv_xl.setText(data.getSalecount()+"");
            }
        }

    }

    public void loadImage(String path) {
        ImageLoadUtil.getInstance().loadImage(btn_img, path, R.drawable.etu_default, 100, 100
                , new Callback() {
                    @Override
                    public void onSuccess() {
                        btn_img.setTag(FileUtil.GetImageStr(btn_img.getDrawable()));
                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    @Override
    public void selectSuccess(String path) {

        if (isChoosebtn_img) {
            loadImage(path);
        } else {
            image_select.add(path);
        }
    }


    @Override
    public void selectSuccess(List<String> pathList) {
        if (isChoosebtn_img) {
            loadImage(pathList.get(0));
        } else {
            image_select.add(pathList.get(0));
        }
    }

    public void release() {
        if (tv_name.getText().toString().trim().equals("")) {
            showToast("请输入商品名称");
        } else if (tv_intro.getText().toString().trim().equals("")) {
            showToast("请输入商品介绍");
        } else if (tv_price.getText().toString().trim().equals("")) {
            showToast("请输入商品价格");
        }else  if (tv_xl.getVisibility() == View.VISIBLE&&(tv_xl.getText().toString().trim().equals("")||Integer.parseInt(tv_xl.getText().toString().trim())<1)) {
            showToast("请输入商品数量");
        } else if (image_select.getAdapter().getCount() == 1) {
            showToast("请选择详情图片");
        } else if (btn_img.getTag() == null) {
            showToast("请选择商品图片");
        } else {
            final HashMap<String, String> params = new HashMap<>();
            params.put("price", tv_price.getText().toString().trim());
            params.put("name", tv_name.getText().toString().trim());
            params.put("des", tv_intro.getText().toString().trim());
            params.put("image", (String) btn_img.getTag());
            params.put("shop_id", shop_id);
            if (tv_xl.getVisibility() == View.VISIBLE&&Integer.parseInt(tv_xl.getText().toString().trim())>0) {
                params.put("salecount", tv_xl.getText().toString().trim());
            } else {
                params.put("salecount", "-1");
            }
            if (data != null) {
                params.put("id", data.getId() + "");
            }
            showWaitDialog("保存中...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    params.put("list_image", image_select.getImagePutString());
                    Http.post(Urls.SHOP_SAVE_PRODUCT, params, new GsonCallback<HttpStateEntity>() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            hideWaitDialog();
                            showToast("发布失败");
                        }

                        @Override
                        public void onResponse(HttpStateEntity response, int i) {
                            hideWaitDialog();
                            if (response.success()) {
                                setResult(RESULT_OK);
                                finish();
                            }
                            showToast(response.getMessage());

                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChoosebtn_img = true;
                Single(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isChoosebtn_img = false;
    }
}
