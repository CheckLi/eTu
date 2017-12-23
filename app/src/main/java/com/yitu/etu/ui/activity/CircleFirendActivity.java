package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.yitu.etu.R;
import com.yitu.etu.ui.adapter.CircleFirendAdapter;

public class CircleFirendActivity extends BaseActivity {


    private ListView listView;

    @Override
    public int getLayout() {
        return R.layout.activity_circle_firend2;
    }

    @Override
    public void initActionBar() {
        setTitle("朋友圈");
        mActionBarView.setRightText("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivityForResult(new Intent(CircleFirendActivity.this,HairDynamicActivity.class),100);
            }
        });
    }

    @Override
    public void initView() {
        listView=(ListView) findViewById(R.id.listView);
        listView.setAdapter(new CircleFirendAdapter(this));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100&&resultCode==RESULT_OK){

        }
    }
}
