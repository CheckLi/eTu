package com.yitu.etu.ui.activity

import com.yitu.etu.R

class MyShopActivity : BaseActivity() {
    override fun getLayout(): Int= R.layout.activity_my_order_shop

    override fun initActionBar() {
        title="我的店铺"
        setRightText("保存"){
            showToast("保存")
        }
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
    }

}
