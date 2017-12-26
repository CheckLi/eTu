package com.yitu.etu.entity;

import com.yitu.etu.ui.fragment.MapsFragment;

import java.io.Serializable;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapSceneEntity extends MerchantBaseEntity implements Serializable{
    {
        setType(MapsFragment.type_scene);
    }

    public String spot_id;
    public String xjdes;
    public String yjcount;
    public String price;
    public String title;
    public String address;
}
