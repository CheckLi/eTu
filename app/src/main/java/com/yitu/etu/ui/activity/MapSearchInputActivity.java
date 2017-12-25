package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.yitu.etu.R;
import com.yitu.etu.ui.fragment.MapsFragment;

public class MapSearchInputActivity extends BaseActivity {

    private EditText ed_search;
    private int type;

    @Override
    public int getLayout() {
        return R.layout.activity_map_search_input;
    }

    @Override
    public void initActionBar() {
        setTitle("搜索");

    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", MapsFragment.type_scene);

        ed_search = (EditText) findViewById(R.id.ed_search);

    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                if (ed_search.getText().toString().trim().equals("")) {
                    showToast("请输入你需要搜索的地址");
                } else {
                    Intent intent = new Intent(this, MapSearchActivity.class);
                    intent.putExtra("type", type);

                    intent.putExtra("city", getIntent().getStringExtra("city"));
                    intent.putExtra("data", ed_search.getText().toString().trim());
                    startActivity(intent);

                }
                break;
            default:
                break;
        }
    }
}
