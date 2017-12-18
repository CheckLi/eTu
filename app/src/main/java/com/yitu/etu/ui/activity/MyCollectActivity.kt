package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.ui.adapter.CollectAdapter
import kotlinx.android.synthetic.main.activity_my_travels.*

class MyCollectActivity : BaseActivity() {
    override fun getLayout(): Int= R.layout.activity_my_travels

    override fun initActionBar() {
        title="我的收藏"
    }

    override fun initView() {
        listview.adapter=CollectAdapter(listOf())
    }

    override fun getData() {
    }

    override fun initListener() {
    }

}
