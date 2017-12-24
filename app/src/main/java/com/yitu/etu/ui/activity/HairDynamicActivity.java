package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;

import com.yitu.etu.R;
import com.yitu.etu.widget.MgridView;

import java.util.List;

public class HairDynamicActivity extends BaseActivity {

    private MgridView gridView;

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
                Intent intent = new Intent();
                intent.putExtra("msg","dadsadsa");
                setResult(RESULT_OK, intent);
            }
        });
    }

    @Override
    public void initView() {
        gridView=(MgridView)findViewById(R.id.gridView);


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
