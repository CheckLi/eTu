package com.yitu.etu.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yitu.etu.R
import com.yitu.etu.ui.fragment.ManageOrderFragment
import kotlinx.android.synthetic.main.activity_my_order.*


class ManageOrderActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_my_order

    override fun initActionBar() {
        title = "订单管理"
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
            val bundle= Bundle()
            bundle.putInt("type",position)
            val fragment= ManageOrderFragment()
            fragment.arguments=bundle
            return when (position) {
                0 -> fragment
                else ->fragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "已完成"
                else -> "待核销"
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }
}