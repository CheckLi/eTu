package com.yitu.etu.entity;

import com.amap.api.services.core.LatLonPoint;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/12.
 */
public class MerchantBaseEntity {
    int type;
    public String image;
    public String id;
    public LatLonPoint latLonPoint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.latLonPoint = latLonPoint;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
