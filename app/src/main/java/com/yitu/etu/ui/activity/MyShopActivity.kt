package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import com.amap.api.maps.model.LatLng
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.Shop
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.FileUtil
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_order_shop.*
import okhttp3.Call
import java.lang.Exception


class MyShopActivity : BaseActivity() {
    var shop: Shop? = null
    override fun getLayout(): Int = R.layout.activity_my_order_shop

    override fun initActionBar() {
        title = "我的店铺"
        setRightText("保存") {
            saveData()
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
                        shop = this
                        et_shopname.setText(name)
                        et_shop_info.setText(des)
                        et_shop_price.setText(price.toString())
                        et_shop_tese_info.setText(tese)
                        et_shop_address.setText(address)
                        et_shop_phone.setText(phone)
                        ImageLoadUtil.getInstance().loadImage(iv_shop_img, image.addHost(), 100, 100)
                        latLng = LatLng(addressLat, addressLng)
                    }
                } else {
                    showToast(response.message)
                }
            }

        })
    }

    /**
     * 存储店铺信息
     */
    fun saveData() {
        val params = hashMapOf("id" to shop?.id.toString())
        shop?.run {
            if (!et_shopname.text.isNullOrBlank()) {
                params.put("name", et_shopname.text.toString())
            }
            if (!et_shop_info.text.isNullOrBlank()) {
                params.put("des", et_shop_info.text.toString())
            }
            if (!et_shop_price.text.isNullOrBlank()) {
                params.put("price", et_shop_price.text.toString())
            }
            if (!et_shop_tese_info.text.isNullOrBlank()) {
                params.put("tese", et_shop_tese_info.text.toString())
            }
            if (!et_shop_address.text.isNullOrBlank()) {
                params.put("address", et_shop_address.text.toString())
                params.put("address_lat", latLng?.latitude?.toString() ?: "")
                params.put("address_lng", latLng?.longitude?.toString() ?: "")
            }
            if (!et_shop_phone.text.isNullOrBlank()) {
                params.put("phone", et_shop_phone.text.toString())
            }
            if (iv_shop_img.tag != null) {
                params.put("image", iv_shop_img.tag.toString())
            }
        }
        showWaitDialog("保存中...")
        post(Urls.URL_SAVE_USER_SHOP_DETAIL, params, object : GsonCallback<ObjectBaseEntity<Shop>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("保存失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Shop>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    showToast("保存成功")
                } else {
                    showToast(response.message)
                }
            }

        })
    }

    override fun initListener() {
        iv_shop_img.setOnClickListener {
            Single()
        }

        btn_location.setOnClickListener {
            nextActivity<MapActivity>(1001)
        }
        btn_manage_product.setOnClickListener {
            if(shop==null){
                showToast("店铺信息获取异常")
            }else {
                nextActivity<ManageProductActivity>("shop_id" to shop?.id.toString())
            }
        }
        btn_manage_order.setOnClickListener {
            nextActivity<ManageOrderActivity>()
        }
    }

    override fun imageResult(path: String?) {
        iv_shop_img.tag = FileUtil.GetImageStr(path)
        ImageLoadUtil.getInstance().loadImage(iv_shop_img, path, 100, 100)
    }

    private var latLng: LatLng? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (1001 == requestCode && resultCode == Activity.RESULT_OK) {
                latLng = data.getParcelableExtra<Parcelable>("latLng") as LatLng
                val address = data.getStringExtra("address")
                et_shop_address.setText(address)
            }
        }
    }
}
