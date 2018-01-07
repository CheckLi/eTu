package com.yitu.etu.entity;

import java.io.Serializable;

/**
 * Created by liqi on 2017/12/30.
 */

public class ShopOrderEntity implements Serializable {

    /**
     * id : 30
     * paytype : 用户余额
     * shop_id : 1
     * shop_user_id : 22
     * product_id : ["2"]
     * user_id : 25
     * username :
     * userphone :
     * count : ["1"]
     * price : 1.00
     * check_sn : 20171209191934952484
     * check_time : 1513423174
     * status : 完成
     * created : 1512818374
     * updated : 1513922031
     * user : {"name":"创新动力","phone":"13408570875","header":"/assets/data/20171128/15118462276464.jpg"}
     */
    private int is_check;
    private int id;
    private String paytype;
    private int shop_id;
    private int shop_user_id;
    private String product_id;
    private int user_id;
    private String username;
    private String userphone;
    private String count;
    private String price;
    private String check_sn;
    private int check_time;
    private String status;
    private int created;
    private int updated;
    private UserBean user;

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getShop_user_id() {
        return shop_user_id;
    }

    public void setShop_user_id(int shop_user_id) {
        this.shop_user_id = shop_user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCheck_sn() {
        return check_sn;
    }

    public void setCheck_sn(String check_sn) {
        this.check_sn = check_sn;
    }

    public int getCheck_time() {
        return check_time;
    }

    public void setCheck_time(int check_time) {
        this.check_time = check_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable {
        /**
         * name : 创新动力
         * phone : 13408570875
         * header : /assets/data/20171128/15118462276464.jpg
         */

        private String name;
        private String phone;
        private String header;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }
    }
}
