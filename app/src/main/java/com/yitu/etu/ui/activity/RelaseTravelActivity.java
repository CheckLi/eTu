package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.SceneEntity;
import com.yitu.etu.eventBusItem.EventRefresh;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.adapter.ShowImagAdapter2;
import com.yitu.etu.util.TextUtils;
import com.yitu.etu.widget.MgridView;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class RelaseTravelActivity extends BaseActivity {

    private MgridView image_select;
    private ShowImagAdapter2 showImagAdapter;
    private SceneEntity.SpotBean data;
    boolean showAdd = true;
    private TextView start_time;
    private TextView tv_name;
    private TextView tv_intro;
    private TextView tv_text;
    private TextView tv_address;
    private TextView tv_number;
    private TextView tv_money;
    private TextView join_starttime;
    private TextView join_endtime;
    private TextView end_time;
    String lat = "";
    String lng = "";
    String address = "";

    @Override
    public int getLayout() {
        return R.layout.activity_relase_travel;
    }

    @Override
    public void initActionBar() {
        if (getIntent().getSerializableExtra("data") != null) {
            data = (SceneEntity.SpotBean) getIntent().getSerializableExtra("data");
            showAdd = false;
        }
        setTitle("发起预约");
        setRightText("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release();
            }
        });
    }

    @Override
    public void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_money = (TextView) findViewById(R.id.tv_money);
        join_starttime = (TextView) findViewById(R.id.join_starttime);
        join_endtime = (TextView) findViewById(R.id.join_endtime);
        end_time = (TextView) findViewById(R.id.end_time);
        start_time = (TextView) findViewById(R.id.start_time);
        image_select = (MgridView) findViewById(R.id.image_select);
        showImagAdapter = new ShowImagAdapter2(this);
        if (!showAdd) {
            lat = data.getAddress_lat();
            lng = data.getAddress_lng();
            address = data.getAddress();
            tv_name.setText(data.getTitle());
            showImagAdapter.addData(data.getImages());
            image_select.setAdapter(showImagAdapter);
        }
        if (!TextUtils.isEmpty(address)) {
            tv_address.setText(address);
        }
    }

    @Override
    public void initListener() {
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelaseTravelActivity.this, MapActivity.class);
                startActivityForResult(intent, 111);
            }
        });
        findViewById(R.id.find_scene).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelaseTravelActivity.this, MapSelectActivity.class);
                startActivityForResult(intent, 112);
            }
        });
    }

    public void release() {
        if (tv_name.getText().toString().trim().equals("")) {
            showToast("请输入行程名称");
        } else if (tv_intro.getText().toString().trim().equals("")) {
            showToast("请输入行程简介");
        } else if (showAdd && image_select.getAdapter().getCount() == 1) {
            showToast("请选择图片");
        } else if (tv_text.getText().toString().trim().equals("")) {
            showToast("请输入行程内容");
        } else if (address.equals("")) {
            showToast("请选择行程地址");
        } else if (tv_number.getText().toString().trim().equals("")) {
            showToast("请输入参与人数");
        } else if (join_starttime.getText().toString().trim().equals("")) {
            showToast("请选择参与开始时间");
        } else if (join_endtime.getText().toString().trim().equals("")) {
            showToast("请选择参与结束时间");
        } else if (start_time.getText().toString().trim().equals("")) {
            showToast("请选择活动开始时间");
        } else if (end_time.getText().toString().trim().equals("")) {
            showToast("请选择活动结束时间");
        } else {
            final HashMap<String, String> params = new HashMap<>();
            params.put("end_time", end_time.getText().toString().trim());
            params.put("start_time", start_time.getText().toString().trim());
            params.put("join_starttime", join_starttime.getText().toString().trim());
            params.put("join_endtime", join_endtime.getText().toString().trim());
            params.put("money", tv_money.getText().toString().trim());
            params.put("name", tv_name.getText().toString().trim());
            params.put("intro", tv_intro.getText().toString().trim());
            params.put("text", tv_text.getText().toString().trim());
            params.put("address", address);
            params.put("number", tv_number.getText().toString().trim());
            params.put("address_lat", lat);
            params.put("address_lng", lng);
            showWaitDialog("发布中...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    params.put("images", image_select.getImagePutString());
                    Http.post(Urls.ACTION_ADD, params, new GsonCallback<HttpStateEntity>() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            hideWaitDialog();
                            showToast("发布失败");
                        }

                        @Override
                        public void onResponse(HttpStateEntity response, int i) {
                            hideWaitDialog();
                            if (response.success()) {
                                EventBus.getDefault().post(new EventRefresh(className));
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
    public void selectSuccess(String path) {
        image_select.add(path);
    }

    @Override
    public void selectSuccess(List<String> pathList) {
        image_select.add(pathList.get(0));
    }

    public void showDatePickDialog(final TextView tv) {
        DatePickDialog dialog = new DatePickDialog(RelaseTravelActivity.this);
        //设置上下年分限制
        dialog.setYearLimt(50);
        try {
            if (!tv.getText().toString().equals("")) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dialog.setStartDate(format.parse(tv.getText().toString()));
            }
        } catch (ParseException e) {
        }
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                tv.setText(simpleDateFormat.format(date));
            }
        });
        dialog.show();

    }

    public void chooseTime(View v) {
        showDatePickDialog((TextView) v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (111 == requestCode && resultCode == RESULT_OK) {
            LatLng latLng = (LatLng) data.getParcelableExtra("latLng");
            lat = latLng.latitude + "";
            lng = latLng.longitude + "";
            address=data.getStringExtra("address");
            tv_address.setText(address);
        }
        if (requestCode == 112 && resultCode == RESULT_OK) {
            SceneEntity mapSceneEntity = (SceneEntity) data.getSerializableExtra("data");
            lat = mapSceneEntity.getSpot().getAddress_lng();
            lng = mapSceneEntity.getSpot().getAddress_lat();
            address = mapSceneEntity.getSpot().getAddress();
            tv_name.setText(mapSceneEntity.getSpot().getTitle());
            tv_address.setText(address);
            List<String> images = mapSceneEntity.getSpot().getImages();
            if (showAdd) {
                image_select.getChooseImageAdapter().clearAll();
                for (String img : images) {
                    image_select.add(Urls.address+img);
                }
            } else {
                showImagAdapter.clearAll();
                showImagAdapter.addData(images);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
