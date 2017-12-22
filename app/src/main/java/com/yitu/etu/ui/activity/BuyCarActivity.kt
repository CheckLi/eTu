package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.ui.adapter.BuyCarAdapter
import kotlinx.android.synthetic.main.activity_buy_car2.*

class BuyCarActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_buy_car2

    override fun initActionBar() {
        title = "购物车"
    }

    override fun initView() {
        listview.adapter = BuyCarAdapter(listOf())
    }

    override fun getData() {

    }

    override fun initListener() {

    }

}
