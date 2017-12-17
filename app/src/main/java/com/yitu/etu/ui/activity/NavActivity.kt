package com.yitu.etu.ui.activity

import android.widget.FrameLayout
import android.widget.LinearLayout
import com.amap.api.navi.AmapRouteActivity
import com.yitu.etu.widget.ActionBarView

class NavActivity : AmapRouteActivity() {
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        //添加标题栏
        val layout = window.decorView as FrameLayout
        val mActionBarView = ActionBarView(this)
        mActionBarView.setTitle("导航")
        try {
            val content = layout.getChildAt(0) as LinearLayout
            content.addView(mActionBarView, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            layout.addView(mActionBarView)
        }

    }
}
