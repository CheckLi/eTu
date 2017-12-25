package com.yitu.etu.entity
import com.google.gson.annotations.SerializedName


/**
 *
 *@className:Product
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月25日 14:34
 *
 */


data class ProductListBean(
		@SerializedName("id") val id: Int = 0, //1
		@SerializedName("name") val name: String = "", //iphone7
		@SerializedName("image") val image: String = "", ///assets/data/20171210/0313961bc72899796f876179904ad261.png
		@SerializedName("price") val price: Int = 0, //1
		@SerializedName("count") val count: Int = 0, //9
		@SerializedName("sort") val sort: Int = 0, //0
		@SerializedName("created") val created: Int = 0, //1512888322
		@SerializedName("updated") val updated: Int = 0 //1512888594
)