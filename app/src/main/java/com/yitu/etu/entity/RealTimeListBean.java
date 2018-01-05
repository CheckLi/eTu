package com.yitu.etu.entity;

import com.amap.api.maps.model.Marker;
import com.google.gson.annotations.SerializedName;

/**
 * @className:RealTimeBean
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月04日 23:39
 */
public class RealTimeListBean {

    /**
     * id : 31
     * header : http://api.91eto.com/assets/data/20171221/15138636209673.jpg
     * lat : 30.578833
     * lng : 104.060634
     */

    @SerializedName("id")
    public int id;
    @SerializedName("header")
    public String header;
    @SerializedName("lat")
    public double lat;
    @SerializedName("lng")
    public double lng;
    public Marker mMarker;

    @Override
    public String toString() {
        return "RealTimeListBean{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", mMarker=" + mMarker +
                '}';
    }
}
