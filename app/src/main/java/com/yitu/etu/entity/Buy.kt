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
open class BuyCar(
        @SerializedName("id") val id: Int=0, //3
        @SerializedName("shop_id") val shopId: Int=0, //1
        @SerializedName("name") val name: String="", //甜皮鸭
        @SerializedName("image") val image: String="", ///assets/data/20171213/15131793219177.jpg
        @SerializedName("list_image1") val listImage: String="", ///assets/data/20171213/15131793219538.jpg|/assets/data/20171213/15131793214830.jpg
        @SerializedName("fxdes") val fxdes: String="",
        @SerializedName("des") val des: String="", //川味鸭子
        @SerializedName("salecount") val salecount: Int=0, //-1
        @SerializedName("price") var price: Float=0.0f, //1.00
        @SerializedName("is_del") val isDel: Int=-1, //0
        @SerializedName("created") val created: Int=0, //1512541751
        @SerializedName("updated") val updated: Int=0 //1513179321
){
    var isCheck: Boolean=true //选中状态
    var count: Int=1 //产品数量
    fun setpPrice(price:Float){
        this.price=price
    }
}
