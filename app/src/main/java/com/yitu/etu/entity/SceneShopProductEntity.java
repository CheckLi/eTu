package com.yitu.etu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liqi on 2018/1/1.
 */

public class SceneShopProductEntity implements Serializable {
    /**
     * data : [{"id":7,"shop_id":1,"name":"2333","image":"/assets/data/20171231/15147095472944.jpg","list_image":"/assets/data/20171231/15147095471696.jpg|/assets/data/20171231/15147095476173.jpg","fxdes":"","des":":333","salecount":-1,"price":"2222.00","is_del":0,"created":1514534806,"updated":1514709547},{"id":3,"shop_id":1,"name":"甜皮鸭","image":"/assets/data/20171231/15147096632812.jpg","list_image":"/assets/data/20171231/15147096636340.jpg|/assets/data/20171231/15147096637687.jpg","fxdes":"","des":"川味鸭子","salecount":12,"price":"1.00","is_del":0,"created":1512541751,"updated":1514709663}]
     * info : {"id":1,"user_id":22,"type":1,"spot_id":121,"name":"旅行小店","des":"专营川味小吃啦啦啦啦","image":"/assets/data/20171231/15146951634930.jpg","price":0.5,"good":2,"tese":"川味小食","address":"四川省成都市武侯区桂溪街道吉庆一路388号香月湖","address_lat":30.554327,"address_lng":104.060988,"phone":"15982039158","sort":99999,"status":1,"created":1512540773,"updated":1514695163,"user":{"id":22,"name":"t123456","wxopenid":"","wbopenid":"","type":1,"usertype":2,"intro":"这家伙很懒，什么都没留下","phone":"18628394457","sex":0,"header":"/assets/data/20171113/15105394904552.jpg","salt":"WDey9lwF","password":"002f53565bf0b1b1b18f25a11e1c979f","paypwd":"123456","address":"","lat":"0","lng":"0","last_lat":"30.654906","last_lng":"104.09844","map_id":49,"balance":"7.11","safecount":2,"status":1,"is_circle":0,"is_del":0,"nomap":0,"created":1509001519,"updated":1514785762}}
     */
    SceneServiceEntity.ListBean info;

    public SceneServiceEntity.ListBean getInfo() {
        return info;
    }

    public void setInfo(SceneServiceEntity.ListBean info) {
        this.info = info;
    }

    private List<ShopProductEntity> data;

    public List<ShopProductEntity> getData() {
        return data;
    }

    public void setData(List<ShopProductEntity> data) {
        this.data = data;
    }
}
