package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.SceneEntity;
import com.yitu.etu.eventBusItem.EventRefresh;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.widget.MgridView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class ReleaseMyTravelsActivity extends BaseActivity {

    private MgridView image_select;
    private TextView content;
    private TextView tv_address, tv_name;
    private String spot_id;

    @Override
    public int getLayout() {
        return R.layout.activity_release_my_travels;
    }

    @Override
    public void initActionBar() {
        setRightText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release();
            }
        });
    }

    @Override
    public void initView() {
        image_select = (MgridView) findViewById(R.id.image_select);
        content = (TextView) findViewById(R.id.content);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        image_select.showAdd(false);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Single(false);
            }
        });
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapSelectActivity.class);
                startActivityForResult(intent, 112);
            }
        });
    }

    @Override
    public void selectSuccess(String path) {

        content.setVisibility(View.GONE);

        image_select.setVisibility(View.VISIBLE);
        image_select.add(path);

    }


    @Override
    public void selectSuccess(List<String> pathList) {
        content.setVisibility(View.GONE);
        image_select.setVisibility(View.VISIBLE);
        image_select.add(pathList.get(0));

    }

    public void release() {
        if (tv_name.getText().toString().trim().equals("")) {
            showToast("请输入游记名称");
        } else if (spot_id == null) {
            showToast("请关联景区");
        } else if (content.getVisibility() == View.VISIBLE && content.getText().toString().trim().equals("")) {
            showToast("请输入游记内容");
        } else {
            final HashMap<String, String> params = new HashMap<>();
            params.put("title", tv_name.getText().toString().trim());
            params.put("spot_id", spot_id);
            params.put("type", "1");
            if (content.getVisibility() == View.VISIBLE) {
                params.put("text", content.getText().toString().trim());
            }
            showWaitDialog("发布中...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (content.getVisibility() != View.VISIBLE) {
                        params.put("text", image_select.getImagePutString());
                    }
                    Http.post(Urls.RELEASE_My_Travel, params, new GsonCallback<HttpStateEntity>() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112 && resultCode == RESULT_OK) {
            SceneEntity mapSceneEntity = (SceneEntity) data.getSerializableExtra("data");
            spot_id = mapSceneEntity.getSpot().getId() + "";
            tv_address.setText(mapSceneEntity.getSpot().getTitle());
        }
    }
}
