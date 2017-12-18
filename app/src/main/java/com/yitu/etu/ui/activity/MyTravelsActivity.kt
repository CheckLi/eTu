package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.ui.adapter.TravelsAdapter
import kotlinx.android.synthetic.main.activity_my_travels.*

class MyTravelsActivity : BaseActivity() {
    override fun getLayout(): Int= R.layout.activity_my_travels

    override fun initActionBar() {
        title="我的游记"
        setRightText("发布"){
            showToast("发布")
        }
    }

    override fun initView() {
        listview.adapter=TravelsAdapter(listOf())
    }

    override fun getData() {
    }

    override fun initListener() {
    }

}
