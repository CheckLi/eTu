package com.yitu.etu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liqi on 2018/1/1.
 */

public class SceneServiceEntity {
    private List<ListBean> list;
    private List<BannerBean> banner;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 1
         * user_id : 22
         * type : 1
         * spot_id : 121
         * name : 旅行小店
         * des : 专营川味小吃啦啦啦啦
         * image : /assets/data/20171231/15146951634930.jpg
         * price : 0.5
         * good : 2
         * tese : 川味小食
         * address : 四川省成都市武侯区桂溪街道吉庆一路388号香月湖
         * address_lat : 30.554327
         * address_lng : 104.060988
         * phone : 15982039158
         * sort : 99999
         * status : 1
         * created : 1512540773
         * updated : 1514695163
         */

        private int id;
        private int user_id;
        private int type;
        private int spot_id;
        private String name;
        private String des;
        private String image;
        private double price;
        private int good;
        private String tese;
        private String address;
        private double address_lat;
        private double address_lng;
        private String phone;
        private int sort;
        private int status;
        private int created;
        private int updated;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSpot_id() {
            return spot_id;
        }

        public void setSpot_id(int spot_id) {
            this.spot_id = spot_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public String getTese() {
            return tese;
        }

        public void setTese(String tese) {
            this.tese = tese;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getAddress_lat() {
            return address_lat;
        }

        public void setAddress_lat(double address_lat) {
            this.address_lat = address_lat;
        }

        public double getAddress_lng() {
            return address_lng;
        }

        public void setAddress_lng(double address_lng) {
            this.address_lng = address_lng;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

    public static class BannerBean {
        /**
         * id : 2
         * image : /assets/data/20170329/771aa6b0a60ba6562c89f2a3aba7306f.jpg
         * spot_id : 64
         * url :
         * sort : 0
         * created : 1490775026
         * updated : 0
         */

        private int id;
        private String image;
        private int spot_id;
        private String url;
        private int sort;
        private int created;
        private int updated;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getSpot_id() {
            return spot_id;
        }

        public void setSpot_id(int spot_id) {
            this.spot_id = spot_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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
}
