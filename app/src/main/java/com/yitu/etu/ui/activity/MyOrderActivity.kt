package com.yitu.etu.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yitu.etu.R
import com.yitu.etu.ui.fragment.OrderFragment
import kotlinx.android.synthetic.main.activity_my_order.*

class MyOrderActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_my_order

    override fun initActionBar() {
        title = "订单中心"
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
                0 -> OrderFragment.getInstance(2)
                else -> OrderFragment.getInstance(1)
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "已使用"
                else -> "未使用"
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }
}


