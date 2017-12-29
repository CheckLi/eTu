package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import com.amap.api.maps.model.LatLng
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import kotlinx.android.synthetic.main.activity_view_recomment.*

/**
 * 
 * @className:
 * @description:景点推荐
 * @author: JIAMING.LI
 * @date:2017年12月18日下午03:20:15
*/

class ViewRecommentActivity : BaseActivity() {
    override fun getLayout(): Int= R.layout.activity_view_recomment

    override fun initActionBar() {
        title="我要推荐景区"
        setRightText("推荐"){
            showToast("推荐")
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
                val latLng = data.getParcelableExtra<Parcelable>("latLng") as LatLng
                val address = data.getStringExtra("address")
                tv_address.text = address
            }
            image_select.onActivityResult(resultCode, requestCode, data)
        }
    }
}
