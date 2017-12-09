package com.yitu.etu.ui.activity

import android.view.View
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun initActionBar() {
        mActionBarView.setRightImage(R.drawable.share, View.OnClickListener {
            nextActivity<TestActivity>()
        })

        mActionBarView.setLeftImage{
            onBackPressed()
        }
    }

    override fun initView() {

    }


    override fun getData() {

    }

    override fun initListener() {
        single.setOnClickListener {
            Single(it)
        }
        mu.setOnClickListener {
            Multiselect(it)
        }
        camera.setOnClickListener {
            Camera(it)
        }
    }

    override fun selectSuccess(path: String?) {
        showToast(path)
    }

    override fun selectSuccess(pathList: MutableList<String>?) {
        showToast(pathList?.toString())
    }
}
