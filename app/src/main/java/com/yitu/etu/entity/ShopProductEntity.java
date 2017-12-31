package com.yitu.etu.entity;

import java.io.Serializable;

/**
 * Created by liqi on 2017/12/30.
 */

public class ShopProductEntity implements Serializable{
    /**
     * id : 7
     * shop_id : 1
     * name : 2333
     * image : /assets/data/20171230/15146198374529.jpg
     * list_image : /assets/data/20171230/15146198379795.jpg
     * fxdes :
     * des : :333
     * salecount : -1
     * price : 2222.00
     * is_del : 0
     * created : 1514534806
     * updated : 1514619837
     */

    private int id;
    private int shop_id;
    private String name;
    private String image;
    private String list_image;
    private String fxdes;
    private String des;
    private int salecount;
    private String price;
    private int is_del;
    private int created;
    private int updated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getList_image() {
        return list_image;
    }

    public void setList_image(String list_image) {
        this.list_image = list_image;
    }

    public String getFxdes() {
        return fxdes;
    }

    public void setFxdes(String fxdes) {
        this.fxdes = fxdes;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getSalecount() {
        return salecount;
    }

    public void setSalecount(int salecount) {
        this.salecount = salecount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }
}
