package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import com.amap.api.maps.model.LatLng
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http.post
import com.yitu.etu.tools.Urls
import kotlinx.android.synthetic.main.activity_view_recomment.*
import okhttp3.Call
import kotlin.concurrent.thread

/**
 * 
 * @className:
 * @description:景点推荐
 * @author: JIAMING.LI
 * @date:2017年12月18日下午03:20:15
*/

class ViewRecommentActivity : BaseActivity() {
    var latLng:LatLng?=null
    var address:String?=null
    override fun getLayout(): Int= R.layout.activity_view_recomment

    override fun initActionBar() {
        title="我要推荐景区"
        setRightText("推荐"){
            when{
                et_title.text.isNullOrBlank()-> showToast("请输入标题")
                et_intro.text.isNullOrBlank()-> showToast("请输入介绍")
                latLng==null||address.isNullOrBlank()-> showToast("请选择地址")
                tv_ts.text.isNullOrBlank()-> showToast("请输入特色")
                et_price.text.isNullOrBlank()-> showToast("请输入价格")
                image_select.imagePutString.isNullOrBlank()-> showToast("请选择图片")
                et_phone.text.isNullOrBlank()-> showToast("请输入联系电话")
                else ->addPost(hashMapOf(
                        "title" to et_title.text.toString(),
                        "price" to et_price.text.toString(),
                        "images" to image_select.imagePutString,
                        "address" to address,
                        "address_lat" to latLng?.latitude.toString(),
                        "address_lng" to latLng?.longitude.toString(),
                        "text" to et_intro.text.toString(),
                        "feature" to tv_ts.text.toString(),
                        "phone" to et_phone.text.toString()
                ))
            }

        }
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
        tv_address.setOnClickListener {
            nextActivity<MapActivity>(1001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null) {
            if (1001 == requestCode && resultCode == Activity.RESULT_OK) {
                 latLng = data.getParcelableExtra<Parcelable>("latLng") as LatLng
                 address = data.getStringExtra("address")
                tv_address.text = address
            }
            image_select.onActivityResult(resultCode, requestCode, data)
        }
    }


    fun addPost(params: HashMap<String,String?>) {
        showWaitDialog("推荐中...")
        thread {
            post(Urls.URL_ADD_SPOT,params, object : GsonCallback<ObjectBaseEntity<Any>>() {
                override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                    runOnUiThread {
                        hideWaitDialog()
                        if (response.success()) {
                            showToast("推荐成功")
                            finish()
                        } else {
                            showToast(response.message)
                        }
                    }

                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    runOnUiThread {
                        hideWaitDialog()
                        showToast("推荐失败")
                    }

                }

            })
        }

    }
}
