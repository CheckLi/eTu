package com.yitu.etu.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @className:GroupBean
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月06日 18:09
 */
public class GroupBean {

    /**
     * result : [{"id":22,"name":"t123456","header":"/assets/data/20171113/15105394904552.jpg"},{"id":31,"name":"李佳明2222","header":"/assets/data/20171221/15138636209673.jpg"}]
     * action_id : 0
     */

    @SerializedName("action_id")
    public int actionId;
    @SerializedName("result")
    public List<UserInfo> result;

}
