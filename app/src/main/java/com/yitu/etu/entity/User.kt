package com.yitu.etu.entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 *
 *@className:User
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月19日 10:34
 *
 */

data class UserInfo(
		@SerializedName("id") var id: Int=0, //31
		@SerializedName("name") var name: String="", //182****9229
		@SerializedName("type") var type: Int=0, //表示认证状态，是否认证
		@SerializedName("wxopenid") var wxopenid: Int=0, //
		@SerializedName("wbopenid") var wbopenid: Int=0, //
		@SerializedName("usertype") var usertype: Int=0, //0为普通用户或者没认证的商户，1为美食商家，2为住宿商家，3为游玩商家
		@SerializedName("intro") var intro: String="", //这家伙很懒，什么都没留下
		@SerializedName("phone") var phone: String="", //18281619229
		@SerializedName("sex") var sex: Int=0, //0
		@SerializedName("header") var header: String="", ///assets/data/sys/userico.jpg
		@SerializedName("salt") var salt: String="", //9zvUsBTe
		@SerializedName("password") var password: String="", //be64e7e18decd33bae11dec86209e94d
		@SerializedName("address") var address: String="",
		@SerializedName("nomap") var nomap: Int=0,//1关闭探友
		@SerializedName("lat") var lat: String="", //0
		@SerializedName("lng") var lng: String="", //0
		@SerializedName("last_lat") var lastLat: String="", //30.654762
		@SerializedName("last_lng") var lastLng: String="", //104.098471
		@SerializedName("map_id") var mapId: Int=0, //0
		@SerializedName("balance") var balance: String="", //0.00
		@SerializedName("safecount") var safecount: Int=0, //0
		@SerializedName("status") var status: Int=0, //1
		@SerializedName("is_circle") var isCircle: Int=0, //0
		@SerializedName("is_del") var isDel: Int=0, //0
		@SerializedName("created") var created: Int=0, //1512573048
		@SerializedName("updated") var updated: Int=0, //1512573139
		@SerializedName("ispaypwd") var ispaypwd: Int=0, //0
		@SerializedName("token") var token: String="" //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkuOTFldG8uY29tIiwiaWF0IjoxNTEzNDExODc0LCJpZCI6MzF9.s-UtStiEgghabcAN8idmWrlC2EqXwN5puKUPyL9uQUI
):Serializable{
	var isCheck=false
	fun setUserinfo(userInfo:UserInfo){
		if(userInfo.id!=id){
			id=userInfo.id
		}
		if(userInfo.name!=name){
			name=userInfo.name
		}
		if(userInfo.type!=type){
			type=userInfo.type
		}
		if(userInfo.wxopenid!=wxopenid){
			wxopenid=userInfo.wxopenid
		}
		if(userInfo.wbopenid!=wbopenid){
			wbopenid=userInfo.wbopenid
		}
		if(userInfo.usertype!=usertype){
			usertype=userInfo.usertype
		}
		if(userInfo.intro!=intro){
			intro=userInfo.intro
		}
		if(userInfo.phone!=phone){
			phone=userInfo.phone
		}
		if(userInfo.sex!=sex){
			sex=userInfo.sex
		}
		if(userInfo.header!=header){
			header=userInfo.header
		}
		if(userInfo.salt!=salt){
			salt=userInfo.salt
		}
		if(userInfo.password!=password){
			password=userInfo.password
		}
		if(userInfo.address!=address){
			address=userInfo.address
		}
		if(userInfo.nomap!=nomap){
			nomap=userInfo.nomap
		}
		if(userInfo.lat!=lat){
			lat=userInfo.lat
		}
		if(userInfo.lng!=lng){
			lng=userInfo.lng
		}
		if(userInfo.lastLat!=lastLat){
			lastLat=userInfo.lastLat
		}
		if(userInfo.lastLng!=lastLng){
			lastLng=userInfo.lastLng
		}
		if(userInfo.mapId!=mapId){
			mapId=userInfo.mapId
		}
		if(userInfo.balance!=balance){
			balance=userInfo.balance
		}
		if(userInfo.safecount!=safecount){
			safecount=userInfo.safecount
		}
		if(userInfo.status!=status){
			status=userInfo.status
		}
		if(userInfo.isCircle!=isCircle){
			isCircle=userInfo.isCircle
		}
		if(userInfo.isDel!=isDel){
			isDel=userInfo.isDel
		}
		if(userInfo.created!=created){
			created=userInfo.created
		}
		if(userInfo.updated!=updated){
			updated=userInfo.updated
		}
		if(userInfo.ispaypwd!=ispaypwd){
			ispaypwd=userInfo.ispaypwd
		}
	}
}


data class RenZBean(
		@SerializedName("id") val id: Int, //10
		@SerializedName("user_id") val userId: Int, //31
		@SerializedName("type") val type: Int, //0表示普通用户认证，1表示美食，2表示住宿，3表示游玩
		@SerializedName("sfzzm") val sfzzm: String, ///assets/data/20171221/15138494398884.jpg
		@SerializedName("sfzfm") val sfzfm: String, ///assets/data/20171221/15138494398280.jpg
		@SerializedName("czz") val czz: String, ///assets/data/20171221/15138494395307.jpg
		@SerializedName("created") val created: Int, //1513849439
		@SerializedName("updated") val updated: Int, //0
		@SerializedName("status") val status: Int //0
)


data class ChatToken(
		@SerializedName("token") val token: String //oRMa6ptbTrU2HBlCGrCiRweA30kxoEAEOTt+WC0cNJzLkt2gOSPDFXWlIfh07G9XZcsW/B1xOImLbXkCd6WbGw==
)

