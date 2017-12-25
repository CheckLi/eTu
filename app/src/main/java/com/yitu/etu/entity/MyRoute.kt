package com.yitu.etu.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 *
 *@className:MyRoute
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月23日 15:29
 *
 */

data class MyRouteBean(
        @SerializedName("id") val id: Int = 0, //3
        @SerializedName("user_id") val userId: Int = 0, //22
        @SerializedName("chat_id") val chatId: String = "",
        @SerializedName("name") val name: String = "", //上普·财富中心广场
        @SerializedName("intro") val intro: String = "", //He has
        @SerializedName("images") val images: String = "", ///assets/data/20171208/15127376676145.jpg|/assets/data/20171208/15127376677525.jpg
        @SerializedName("text") val text: String = "", //1231231
        @SerializedName("address") val address: String = "", //四川省成都市锦江区督院街街道上普国际写字楼上普·财富中心
        @SerializedName("address_lat") val addressLat: String = "", //30.652304
        @SerializedName("address_lng") val addressLng: String = "", //104.069599
        @SerializedName("number") val number: Int = 0, //1
        @SerializedName("hasnumber") val hasnumber: Int = 0, //0
        @SerializedName("money") val money: String = "", //0.01
        @SerializedName("hasmoney") val hasmoney: String = "", //0.00
        @SerializedName("sort") val sort: Int = 0, //99
        @SerializedName("is_recommend") val isRecommend: Int = 0, //0
        @SerializedName("status") val status: String = "", //审核中
        @SerializedName("join_starttime") val joinStarttime: Int = 0, //1513169580
        @SerializedName("join_endtime") val joinEndtime: Int = 0, //1513255980
        @SerializedName("start_time") val startTime: Long = 0, //1512737580
        @SerializedName("end_time") val endTime: Int = 0, //1513083180
        @SerializedName("start_type") val startType: Int = 0, //0
        @SerializedName("is_spot") val isSpot: Int = 0, //0
        @SerializedName("map_id") val mapId: Int = 0, //0
        @SerializedName("created") val created: String = "", //2017-12-08 20:54:28
        @SerializedName("updated") val updated: Int = 0, //0
        @SerializedName("image") val image: String = "" ///assets/data/20171208/15127376676145.jpg
)


data class MyTravels(
        @SerializedName("id") val id: Int = 0, //6
        @SerializedName("type") val type: Int = 0, //1
        @SerializedName("spot_id") val spotId: Int = 0, //1386
        @SerializedName("user_id") val userId: Int = 0, //22
        @SerializedName("title") val title: String = "", //哥哥哥哥
        @SerializedName("images") val images: String = "",
        @SerializedName("text") val text: String = "", //<img width="100%" src="http://api.91eto.com/assets/data/20171223/15140160287179.jpg"><br><img width="100%" src="http://api.91eto.com/assets/data/20171223/15140160475358.jpg"><br>
        @SerializedName("is_spot") val isSpot: Int = 0, //0
        @SerializedName("address") val address: String = "",
        @SerializedName("address_lat") val addressLat: String = "",
        @SerializedName("address_lng") val addressLng: String = "",
        @SerializedName("good") val good: Int = 0, //0
        @SerializedName("bad") val bad: Int = 0, //0
        @SerializedName("sort") val sort: Int = 0, //99
        @SerializedName("status") val status: Int = 0, //0
        @SerializedName("created") val created: Int = 0, //1514016065
        @SerializedName("updated") val updated: Int = 0 //0
)


data class MyTravelsDetail(
        @SerializedName("id") val id: Int = 0, //6
        @SerializedName("type") val type: Int = 0, //1
        @SerializedName("spot_id") val spotId: Int = 0, //1386
        @SerializedName("user_id") val userId: Int = 0, //22
        @SerializedName("title") val title: String = "", //哥哥哥哥
        @SerializedName("images") val images: List<String> = listOf(),
        @SerializedName("text") val text: String = "", //<img width="100%" src="http://api.91eto.com/assets/data/20171223/15140160287179.jpg"><br><img width="100%" src="http://api.91eto.com/assets/data/20171223/15140160475358.jpg"><br>
        @SerializedName("is_spot") val isSpot: Int = 0, //0
        @SerializedName("address") val address: String = "",
        @SerializedName("address_lat") val addressLat: String = "",
        @SerializedName("address_lng") val addressLng: String = "",
        @SerializedName("good") val good: Int = 0, //0
        @SerializedName("bad") val bad: Int = 0, //0
        @SerializedName("sort") val sort: Int = 0, //99
        @SerializedName("status") val status: Int = 0, //0
        @SerializedName("created") val created: Long = 0, //1514016065
        @SerializedName("updated") val updated: Int = 0, //0
        @SerializedName("user") val user: UserInfo,
        @SerializedName("comment") val comment: List<String> = listOf(),
        @SerializedName("collect") val collect: Int = 0 //0
)


data class MyCollectBean(
        @SerializedName("image") val image: String = "", ///assets/data/20170505/14939500433938jpg
        @SerializedName("name") val name: String = "", //西蜀人家
        @SerializedName("desc") val desc: String = "", //西蜀人家位于四川省成都
        @SerializedName("id") val id: Int = 0, //9
        @SerializedName("type") val type: Int = 0, //2
        @SerializedName("pid") val pid: Int = 0 //867
)


data class MyBoonBean (
        @SerializedName("id") val id: Int = 0, //1
        @SerializedName("name") val name: String = "", //测试景点门票
        @SerializedName("price") val price: String = "", //90.00
        @SerializedName("oldprice") val oldprice: String = "", //120.00
        @SerializedName("des") val des: String = "", //著名景点峨眉山门票一张
        @SerializedName("count") val count: Int = 0, //10
        @SerializedName("status") val status: Int = 0, //1
        @SerializedName("created") val created: Int = 0, //1513592543
        @SerializedName("updated") val updated: Int = 0 //1513592954
): Serializable


data class MyBoonListBean(
		@SerializedName("id") val id: Int = 0, //16
		@SerializedName("paytype") val paytype: Int = 0, //0
		@SerializedName("ticket_id") val ticketId: Int = 0, //1
		@SerializedName("user_id") val userId: Int = 0, //22
		@SerializedName("check_sn") val checkSn: String = "", //20171224193216377675
		@SerializedName("price") val price: String = "", //0.01
		@SerializedName("count") val count: Int = 0, //1
		@SerializedName("status") val status: String = "", //订单成功
		@SerializedName("created") val created: Long = 0, //1514115136
		@SerializedName("updated") val updated: Int = 0, //0
		@SerializedName("ticket") val ticket: Ticket = Ticket()
): Serializable

data class Ticket(
		@SerializedName("id") val id: Int = 0, //1
		@SerializedName("name") val name: String = "", //测试景点门票
		@SerializedName("price") val price: String = "", //0.01
		@SerializedName("oldprice") val oldprice: String = "", //120.00
		@SerializedName("des") val des: String = "", //著名景点峨眉山门票一张
		@SerializedName("count") val count: Int = 0, //10
		@SerializedName("status") val status: Int = 0, //1
		@SerializedName("created") val created: Int = 0, //1513592543
		@SerializedName("updated") val updated: Int = 0 //1514093617
): Serializable

data class PayBean(var id:Int,var price:Float,var desc:String,var rechargetype: Int,var classname: String,var buyType: Int):Serializable