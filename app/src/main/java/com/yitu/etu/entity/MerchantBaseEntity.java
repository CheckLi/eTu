package com.yitu.etu.entity;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/12.
 */
public class MerchantBaseEntity {
    int id;
    public double lng;
    int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
