package com.yitu.etu.ui.activity

import android.content.Intent
import android.os.Handler
import android.view.View
import com.yitu.etu.R

class SplashActivity : BaseActivity() {
    private var mHandler = Handler()
    private var mTime = Runnable {
        startActivity(Intent(this,MainActivity::class.java))
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
//        nextActivity<MainActivity>(true)
        finish()
    }

    override fun getLayout(): Int = R.layout.activity_test

    override fun initActionBar() {
        mActionBarView.hideLeftImage()
        mActionBarView.visibility = View.GONE
    }

    override fun initView() {
        mHandler.postDelayed(mTime, 1000)
    }


    override fun getData() {
    }

    override fun initListener() {
    }

    override fun onDestroy() {
        mHandler.removeCallbacks(mTime)
        super.onDestroy()

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
    }


}
