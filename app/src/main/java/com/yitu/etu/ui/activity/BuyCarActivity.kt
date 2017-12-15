package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.ui.adapter.BuyCarAdapter
import kotlinx.android.synthetic.main.activity_buy_car.*

class BuyCarActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_buy_car

    override fun initActionBar() {
        title = "商品管理"
        setRightText("添加") {
            showToast("添加商品")
        }
    }

    override fun initView() {
        recyclerView.adapter = BuyCarAdapter(listOf())


    }

    override fun getData() {
    }

    override fun initListener() {

    }

}
