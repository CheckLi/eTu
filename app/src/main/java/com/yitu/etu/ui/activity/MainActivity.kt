package com.yitu.etu.ui.activity

import com.yitu.etu.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun initActionBar() {

    }

    override fun initView() {

    }


    override fun getData() {

    }

    override fun initListener() {
        tv_hello.setOnClickListener {
            showWaitDialog("我是加载框")
        }
    }

}
