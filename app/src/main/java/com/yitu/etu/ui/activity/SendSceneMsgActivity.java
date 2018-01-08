package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.widget.MgridView1;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class SendSceneMsgActivity extends BaseActivity {

    private EditText err_msg;
    private MgridView1 gridView;
    private TextView tv_lat_lng;
    private TextView tv_address;
    private int type;
    private String id;

    @Override
    public int getLayout() {
        return R.layout.activity_send_scene_msg;
    }

    @Override
    public void initActionBar() {
        setTitle("景点报错");
        setRightText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendError();
            }
        });

    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getStringExtra("id");
        err_msg = (EditText) findViewById(R.id.err_msg);
        gridView = (MgridView1) findViewById(R.id.gridView);
        View btn_location = findViewById(R.id.btn_location);
        tv_lat_lng = (TextView) findViewById(R.id.tv_lat_lng);
        tv_address = (TextView) findViewById(R.id.tv_address);
        if (type == 0) {
            gridView.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            btn_location.setVisibility(View.VISIBLE);
            btn_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=  new Intent(SendSceneMsgActivity.this, MapActivity.class);
//                    intent.putExtra("data",new LatLng(30.783289972758958,104.09384295344354));
                    startActivityForResult(intent, 111);
                }
            });
        } else if (type == 2) {
            err_msg.setVisibility(View.VISIBLE);
        }
    }


    public void sendError() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("spot_id", id);
        params.put("type", "" + type);
        if (type == 0) {
            if (gridView.getAdapter().getCount() == 1) {
                showToast("请选择图片");
                return;
            }
        } else if (type == 1) {
            if (latLng == null) {
                showToast("请选择地址");
                return;
            } else {
                params.put("location", latLng.longitude+","+latLng.latitude);
            }
        } else if (type == 2) {
            if (err_msg.getText().toString().trim().equals("")) {
                showToast("请输入错误描述");
                return;
            } else {
                params.put("txt", "" + err_msg.getText().toString().trim());
            }
        }
        showWaitDialog("上传中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (type == 0) {
                    params.put("images", gridView.getImagePutString());
                }
                Http.post(Urls.SPOT_UP_ERROR, params, new GsonCallback<HttpStateEntity>() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onResponse(HttpStateEntity response, int i) {
                        hideWaitDialog();
                        ToastUtil.showMessage(response.getMessage());
                        if (response.success()) {
                            finish();
                        }

                    }
                });
            }
        }).start();
    }


    @Override
    public void selectSuccess(String path) {
        gridView.add(path);
    }

    @Override
    public void selectSuccess(List<String> pathList) {
        gridView.add(pathList.get(0));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }
    LatLng latLng;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (111 == requestCode && resultCode == RESULT_OK) {
            latLng=(LatLng)data.getParcelableExtra("latLng");
            tv_lat_lng.setVisibility(View.VISIBLE);
            tv_address.setVisibility(View.VISIBLE);
            tv_address.setText(data.getStringExtra("address"));
            tv_lat_lng.setText(latLng.latitude+","+latLng.longitude);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
