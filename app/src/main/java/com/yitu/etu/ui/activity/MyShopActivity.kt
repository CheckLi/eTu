package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.Shop
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_order_shop.*
import okhttp3.Call
import java.lang.Exception


class MyShopActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_my_order_shop

    override fun initActionBar() {
        title = "我的店铺"
        setRightText("保存") {
            showToast("保存")
        }
    }

    override fun initView() {
    }

    override fun getData() {
        showWaitDialog("获取中...")
        post(Urls.URL_GET_USER_SHOP_DETAIL, hashMapOf(), object : GsonCallback<ObjectBaseEntity<Shop>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("获取失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Shop>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    with(response.data) {
                        et_shopname.setText(name)
                        et_shop_info.setText(des)
                        et_shop_price.setText(price.toString())
                        et_shop_tese_info.setText(tese)
                        et_shop_address.setText(address)
                        et_shop_phone.setText(phone)
                        ImageLoadUtil.getInstance().loadImage(iv_shop_img, image.addHost(), 100, 100)
                    }
                } else {
                    showToast(response.message)
                }
            }

        })
    }

    override fun initListener() {
    }

}
