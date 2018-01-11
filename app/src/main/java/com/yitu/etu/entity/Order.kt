package com.yitu.etu.entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 *
 *@className:Order
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月22日 11:27
 *
 */



data class OrderList (
		@SerializedName("id") val id: Int, //6
		@SerializedName("paytype") val paytype: String, //支付宝
		@SerializedName("shop_id") val shopId: Int, //1
		@SerializedName("shop_user_id") val shopUserId: Int, //22
		@SerializedName("product_id") val productId: String, //["3"]
		@SerializedName("user_id") val userId: Int, //22
		@SerializedName("username") val username: String,
		@SerializedName("userphone") val userphone: String,
		@SerializedName("count") val count: String, //["1"]
		@SerializedName("price") val price: String, //1.00
		@SerializedName("check_sn") val checkSn: String, //20171208125304125654
		@SerializedName("check_time") val checkTime: Int, //1513313584
		@SerializedName("status") val status: String, //完成使用
		@SerializedName("created") val created: Long, //1512708784
		@SerializedName("updated") val updated: Long, //1512711540
		@SerializedName("product") val product: Product
): Serializable

data class Product(
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
): Serializable


data class OrderDetail(
		@SerializedName("id") val id: Int, //6
		@SerializedName("paytype") val paytype: Int, //1
		@SerializedName("shop_id") val shopId: Int, //1
		@SerializedName("shop_user_id") val shopUserId: Int, //22
		@SerializedName("product_id") val productId: String, //["3"]
		@SerializedName("user_id") val userId: Int, //22
		@SerializedName("username") val username: String,
		@SerializedName("userphone") val userphone: String,
		@SerializedName("count") val count: String, //["1"]
		@SerializedName("price") val price: String, //1.00
		@SerializedName("check_sn") val checkSn: String, //20171208125304125654
		@SerializedName("check_time") val checkTime: Int, //1513313584
		@SerializedName("status") val status: Int, //2
		@SerializedName("created") val created: Long, //1512708784
		@SerializedName("updated") val updated: Int, //1512711540
		@SerializedName("product") val product: List<Product2>,
		@SerializedName("shop") val shop: Shop
)

data class Shop(
		@SerializedName("id") val id: Int, //1
		@SerializedName("user_id") val userId: Int, //22
		@SerializedName("type") val type: Int, //1
		@SerializedName("spot_id") val spotId: Int, //121
		@SerializedName("name") val name: String, //旅行小店
		@SerializedName("des") val des: String, //专营川味小吃
		@SerializedName("image") val image: String, ///assets/data/20171206/15125417609083.jpg
		@SerializedName("price") val price: Double, //0.5
		@SerializedName("good") val good: Int, //2
		@SerializedName("tese") val tese: String, //川味小食
		@SerializedName("address") val address: String, //四川省成都市武侯区桂溪街道吉庆一路388号香月湖
		@SerializedName("address_lat") val addressLat: Double, //30.554327
		@SerializedName("address_lng") val addressLng: Double, //104.060988
		@SerializedName("phone") val phone: String, //15982039158
		@SerializedName("sort") val sort: Int, //99999
		@SerializedName("status") val status: Int, //1
		@SerializedName("created") val created: Int, //1512540773
		@SerializedName("updated") val updated: Int //1513064325
)

data class Product2(
		@SerializedName("name") val name: String, //甜皮鸭
		@SerializedName("count") val count: String //1
)



data class HistoryPanBean(
		@SerializedName("image") val image: String = "", ///assets/data/20171113/15105394904552.jpg
		@SerializedName("desc") val desc: String = "", //余额支付购买门票
		@SerializedName("name") val name: String = "", //0.01
		@SerializedName("created") val created: String = "", //2017-12-24 19:32:16
		@SerializedName("status") val status: String = "" //已成功
)



data class OrderActionInfo(
		@SerializedName("id") val id: Int = 0, //3
		@SerializedName("action_id") val actionId: Int = 0, //23
		@SerializedName("user_id") val userId: Int = 0, //22
		@SerializedName("paytype") val paytype: Int = 0, //0
		@SerializedName("money") val money: String = "", //0.10
		@SerializedName("status") val status: String = "", //订单成功
		@SerializedName("created") val created: Int = 0, //1515594720
		@SerializedName("updated") val updated: Int = 0, //1515594720
		@SerializedName("user") val user: UserInfo = UserInfo(),
		@SerializedName("created_time") val createdTime: String = "" //2018-01-10 22:32:00
)

