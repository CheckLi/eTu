package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.ui.fragment.RealTimeMapFragment
import kotlinx.android.synthetic.main.activity_amap_real_time.*
import org.jetbrains.anko.bundleOf


class AMapRealTimeActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_amap_real_time

    override fun initActionBar() {
        setRightClick(R.drawable.icon146, resources.getColor(R.color.white)) {
            finish()
        }
    }

    override fun initView() {
        val fragment=RealTimeMapFragment()
        fragment.arguments= bundleOf("id" to intent.getStringExtra("id"))
        supportFragmentManager.beginTransaction().replace(content.id, fragment).commitAllowingStateLoss()
    }

    override fun getData() {

    }

    override fun initListener() {

    }
}