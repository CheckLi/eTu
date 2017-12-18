package com.yitu.etu.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yitu.etu.R
import com.yitu.etu.ui.fragment.RouteFragment
import kotlinx.android.synthetic.main.activity_my_order.*

/**
 *
 * @className:my
 * @description:我的行程
 * @author: JIAMING.LI
 * @date:2017年12月18日下午03:04:56
*/

class MyRouteActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_my_order

    override fun initActionBar() {
        title = "我的行程"
        setRightText("发起预约"){
            showToast("发起预约")
        }
    }

    override fun initView() {
        viewPager.adapter = OrderFragmentAdapter(supportFragmentManager)
        tl_tab.setViewPager(viewPager)
        tl_tab.currentTab = 0
    }

    override fun getData() {
    }

    override fun initListener() {
    }

    class OrderFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> RouteFragment()
                else -> RouteFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "我参与的"
                else -> "我发起的"
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }
}


