package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.ui.adapter.BoonAdapter
import kotlinx.android.synthetic.main.activity_my_travels.*

class BoonActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_boon

    override fun initActionBar() {
        title="e途福利"
    }

    override fun initView() {
        listview.adapter= BoonAdapter(listOf())
    }

    override fun getData() {
    }

    override fun initListener() {
    }


}
