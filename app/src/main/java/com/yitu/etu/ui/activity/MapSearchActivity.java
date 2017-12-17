package com.yitu.etu.ui.activity;

import com.yitu.etu.R;
import com.yitu.etu.ui.fragment.MapsFragment;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapSearchActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_map_search;
    }

    @Override
    public void initActionBar() {

    }

    @Override
    public void initView() {
        int type = getIntent().getIntExtra("type", MapsFragment.type_scene);
        if (type == MapsFragment.type_scene) {
            setTitle("dsad");
        } else if (type == MapsFragment.type_friend) {
            setTitle("dsad");
        } else if (type == MapsFragment.type_order_scene) {
            setTitle("dsad");
        }

    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }
}
