package com.yitu.etu.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @className:RealTimeBean
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月04日 23:39
 */
public class RealTimeBean {

    /**
     * user_id : 31
     * data : [{"id":31,"header":"http://api.91eto.com/assets/data/20171221/15138636209673.jpg"}]
     */

    @SerializedName("user_id")
    public int userId;
    @SerializedName("data")
    public List<RealTimeListBean> data;


    @Override
    public String toString() {
        return "RealTimeBean{" +
                "userId=" + userId +
                ", data=" + data +
                '}';
    }
}
