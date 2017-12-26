package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.CircleFirendEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.UserInfo;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.widget.MgridView;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class HairDynamicActivity extends BaseActivity {

    private MgridView gridView;
    private EditText text;

    @Override
    public int getLayout() {
        return R.layout.activity_hair_dynamic;
    }

    @Override
    public void initActionBar() {
        setTitle("发布动态");
        mActionBarView.setRightText("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString().trim().equals("")) {
                    showToast("请填写内容");
                    return;
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("text", text.getText().toString().trim());
                params.put("images", "");
                Http.post(Urls.CIRCLE_ADD, params, new GsonCallback<ObjectBaseEntity<CircleFirendEntity.CircleBean>>() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("s", "ds");

                    }

                    @Override
                    public void onResponse(ObjectBaseEntity<CircleFirendEntity.CircleBean> response, int i) {
                        if (response.success()) {
                            CircleFirendEntity.UserBean userBean = new CircleFirendEntity.UserBean();
                            UserInfo userInfo = EtuApplication.getInstance().getUserInfo();
                            userBean.setName(userInfo.getName());
                            userBean.setHeader(userInfo.getHeader());
                            response.getData().setUser(userBean);
                            Intent intent = new Intent();
                            intent.putExtra("data", response.getData());
                            intent.putExtra("data2", "ds");

                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void initView() {
        gridView = (MgridView) findViewById(R.id.gridView);
        text = (EditText) findViewById(R.id.text);
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
}
