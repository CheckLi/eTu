package com.yitu.etu.entity;

/**
 * @className:ceneShopProductDetailENTITY
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月01日 22:55
 */
public class SceneShopProductDetailEntity {
    BuyCar2 product;
    SceneServiceEntity.ListBean  shop;

    public BuyCar2 getProduct() {
        return product;
    }

    public void setProduct(BuyCar2 product) {
        this.product = product;
    }

    public SceneServiceEntity.ListBean getShop() {
        return shop;
    }

    public void setShop(SceneServiceEntity.ListBean shop) {
        this.shop = shop;
    }
}
