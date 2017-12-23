package com.yitu.etu.entity;

import com.yitu.etu.ui.fragment.MapsFragment;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapOrderSceneEntity extends MerchantBaseEntity{
    {
        setType(MapsFragment.type_order_scene);
    }

    public String title;
    public String address;
    public String is_spot;
    public String title_id;

}
