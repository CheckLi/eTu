package com.yitu.etu.ui.activity

import android.os.Handler
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R

class SplashActivity : BaseActivity() {
    private var mHandler = Handler()
    private var mTime = Runnable {
        nextActivity<MainActivity>(true)
    }

    override fun getLayout(): Int = R.layout.activity_test

    override fun initActionBar() {
        mActionBarView.hideLeftImage()
    }

    override fun initView() {
        mHandler.postDelayed(mTime, 1000)
    }

    override fun getData() {
    }

    override fun initListener() {
    }

    override fun onDestroy() {
        mHandler?.removeCallbacks(mTime)
        super.onDestroy()

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.open_animation, R.anim.close_animation)
    }
}
