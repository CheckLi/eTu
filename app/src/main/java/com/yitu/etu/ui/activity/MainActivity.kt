package com.yitu.etu.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yitu.etu.R
import com.yitu.etu.ui.fragment.LYFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun initActionBar() {
        mActionBarView.hideLeftImage()
    }

    override fun initView() {
        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        tab_select.setViewPager(viewPager)
        tab_select.currentTab = 1
        select(1)
    }


    override fun getData() {

    }

    /**
     * 设置标题的显示与隐藏
     */
    fun select(position: Int) {
        when (position) {
            0 -> {
                mActionBarView.setTitle("旅友")
                mActionBarView.hideLeftImage()
                mActionBarView.setRightImage(R.drawable.icon56) {
                    showToast("添加")
                }
            }
            1 -> {
                mActionBarView.setLeftImage(R.drawable.icon114) {
                    showToast("搜索")
                }
                mActionBarView.setTitle("e途")
                mActionBarView.setRightImage(R.drawable.icon87) {
                    showToast("红包")
                }
            }
            2 -> {
                mActionBarView.setTitle("个人中心")
                mActionBarView.hideLeftImage()
                mActionBarView.hideRightImage()
            }
        }
    }

    override fun initListener() {
        tab_select.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                select(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

}

private class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "旅友"
            1 -> "中间模块"
            2 -> "个人"
            else -> ""
        }
    }

    override fun getItem(position: Int): Fragment {
        return LYFragment.getInstance(getPageTitle(position).toString())
    }
}