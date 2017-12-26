package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.ui.fragment.MapsFragment
import kotlinx.android.synthetic.main.activity_map_select.*
import org.jetbrains.anko.bundleOf


class MapSelectActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_map_select



    override fun initActionBar() {
        title="地图选择"
    }

    override fun initView() {
        val maps=MapsFragment()
        maps.arguments= bundleOf("isChooseScene" to true)
        supportFragmentManager.beginTransaction().replace(rl_content.id,maps).commitAllowingStateLoss()
    }

    override fun getData() {
    }

    override fun initListener() {
    }
}
