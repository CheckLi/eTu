package com.yitu.etu.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @className:BuyCar2
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月01日 23:11
 */
public class BuyCar2 extends BuyCar{
    @SerializedName("list_image")
    private List<String> list_image;

    public List<String> getList_image() {
        return list_image;
    }

    public void setList_image(List<String> list_image) {
        this.list_image = list_image;
    }
}
