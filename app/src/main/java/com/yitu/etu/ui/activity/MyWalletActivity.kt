package com.yitu.etu.ui.activity

import com.yitu.etu.R

/**
 * 我的钱包
 */
class MyWalletActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_my_wallet

    override fun initActionBar() {
        title = "我的钱包"
        setRightText("记录"){
            showToast("钱包记录")
        }
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
    }


}
