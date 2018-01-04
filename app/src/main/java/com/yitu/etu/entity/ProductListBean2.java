package com.yitu.etu.entity;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2018/1/4.
 */
public class ProductListBean2{

    /**
     * id : 1
     * name : iphone7
     * image : /assets/data/20171210/0313961bc72899796f876179904ad261.png
     * price : 1
     * count : 9
     * sort : 0
     * created : 1512888322
     * updated : 1512888594
     */

    public int id;
    public String name;
    public String image;
    public int price;
    public int count;
    public int sort;
    public int created;
    public int updated;
   UserInfo user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
