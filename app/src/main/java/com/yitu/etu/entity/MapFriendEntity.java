package com.yitu.etu.entity;

import com.yitu.etu.ui.fragment.MapsFragment;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapFriendEntity extends MerchantBaseEntity{
    {
        setType(MapsFragment.type_friend);
    }
    public String title;
    public String address;
    public String sex;
    public String user_id;
}
