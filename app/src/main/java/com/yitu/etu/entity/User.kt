package com.yitu.etu.entity
import com.google.gson.annotations.SerializedName


/**
 *
 *@className:User
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月19日 10:34
 *
 */

data class UserInfo(
		@SerializedName("id") val id: Int=0, //31
		@SerializedName("name") val name: String="", //182****9229
		@SerializedName("type") val type: Int=0, //0
		@SerializedName("usertype") val usertype: Int=0, //0
		@SerializedName("intro") val intro: String="", //这家伙很懒，什么都没留下
		@SerializedName("phone") val phone: String="", //18281619229
		@SerializedName("sex") val sex: Int=0, //0
		@SerializedName("header") val header: String="", ///assets/data/sys/userico.jpg
		@SerializedName("salt") val salt: String="", //9zvUsBTe
		@SerializedName("password") val password: String="", //be64e7e18decd33bae11dec86209e94d
		@SerializedName("address") val address: String="",
		@SerializedName("lat") val lat: String="", //0
		@SerializedName("lng") val lng: String="", //0
		@SerializedName("last_lat") val lastLat: String="", //30.654762
		@SerializedName("last_lng") val lastLng: String="", //104.098471
		@SerializedName("map_id") val mapId: Int=0, //0
		@SerializedName("balance") val balance: String="", //0.00
		@SerializedName("safecount") val safecount: Int=0, //0
		@SerializedName("status") val status: Int=0, //1
		@SerializedName("is_circle") val isCircle: Int=0, //0
		@SerializedName("is_del") val isDel: Int=0, //0
		@SerializedName("created") val created: Int=0, //1512573048
		@SerializedName("updated") val updated: Int=0, //1512573139
		@SerializedName("ispaypwd") val ispaypwd: Int=0, //0
		@SerializedName("token") val token: String="" //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkuOTFldG8uY29tIiwiaWF0IjoxNTEzNDExODc0LCJpZCI6MzF9.s-UtStiEgghabcAN8idmWrlC2EqXwN5puKUPyL9uQUI
)