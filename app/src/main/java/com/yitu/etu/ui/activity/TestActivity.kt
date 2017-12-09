package com.yitu.etu.ui.activity

import android.view.View
import com.yitu.etu.R

class TestActivity : BaseActivity() {
    override fun getLayout(): Int= R.layout.activity_test

    override fun initActionBar() {
        mActionBarView.setLeftImage(View.OnClickListener {
            onBackPressed()
        })
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
    }


}
