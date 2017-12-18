package com.yitu.etu.ui.activity

import com.yitu.etu.R

class SoftWareShareActivity : BaseActivity() {
    override fun getLayout(): Int= R.layout.activity_soft_ware_share
    override fun initActionBar() {
        title="我的分享"
        setRightText("分享"){
            showToast("分享")
        }
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
    }


}
