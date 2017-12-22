package com.yitu.etu.entity

import com.google.gson.annotations.SerializedName

/**
 *
 *@className:BuyCar
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月22日 18:17
 *
 */
data class BuyCar(
        @SerializedName("id") val id: Int, //3
        @SerializedName("shop_id") val shopId: Int, //1
        @SerializedName("name") val name: String, //甜皮鸭
        @SerializedName("image") val image: String, ///assets/data/20171213/15131793219177.jpg
        @SerializedName("list_image") val listImage: String, ///assets/data/20171213/15131793219538.jpg|/assets/data/20171213/15131793214830.jpg
        @SerializedName("fxdes") val fxdes: String,
        @SerializedName("des") val des: String, //川味鸭子
        @SerializedName("salecount") val salecount: Int, //-1
        @SerializedName("price") val price: String, //1.00
        @SerializedName("is_del") val isDel: Int, //0
        @SerializedName("created") val created: Int, //1512541751
        @SerializedName("updated") val updated: Int //1513179321
)