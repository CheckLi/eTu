package com.yitu.etu.ui.activity

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.ui.fragment.AccountFragment
import com.yitu.etu.ui.fragment.LYFragment
import com.yitu.etu.ui.fragment.MapsFragment
import com.yitu.etu.util.LogUtil
import com.yitu.etu.util.Tools
import com.yitu.etu.widget.tablayout.OnTabSelectListener
import io.rong.common.FileUtils
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initActionBar() {
        mActionBarView.hideLeftImage()
    }

    override fun initView() {
        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        tab_select.setViewPager(viewPager, intArrayOf(R.drawable.icon136, -1, R.drawable.icon130))
        viewPager.offscreenPageLimit = 2
        tab_select.currentTab = 1
        select(1)
    }


    override fun getData() {
        EtuApplication.getInstance().connectChat()//连接聊天服务器
        val buff = StringBuffer("")
        buff.append("缓存目录1$cacheDir\n")
        buff.append("缓存目录2$filesDir\n")

        buff.append("缓存目录3${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}\n")
        buff.append("缓存目录4$externalCacheDir\n")
        buff.append("缓存目录5${FileUtils.getCachePath(this)}\n")
        val file = cacheDir
        getsize(file)
        buff.append("缓存目录1$cacheDir 大小 ${file.length() / 1024.0f}kb ${lenght}kb\n")
        LogUtil.e("cache", buff.toString())
    }

    var lenght = 0.0f
    fun getsize(file: File): Float {
        val files = file.listFiles()
        files.forEach {
            if (it.isDirectory) {
                getsize(it)
            } else {
                lenght += file.length() / 1024.0f
            }
        }
        return lenght
    }

    /**
     * 设置标题的显示与隐藏
     */
    fun select(position: Int) {
        when (position) {
            0 -> {
                mActionBarView.setTitle("旅 友")
                mActionBarView.hideLeftImage()
                mActionBarView.setRightImage(R.drawable.icon56) {
                    val pop = Tools.getPopupWindow(this@MainActivity, arrayOf("添加好友", "好友列表", "发起群聊"), object : AdapterView.OnItemClickListener {
                        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            when(position){
                                0->"添加好友"
                                1->"添加好友"
                                2->"发起群聊"
                            }
                        }

                    },"right")
                    pop.showAsDropDown(mActionBarView)
                }
            }
            1 -> {
                mActionBarView.setLeftImage(R.drawable.icon114) {
                    val fragments = supportFragmentManager.fragments
                    fragments?.forEach {
                        if (it is MapsFragment) {
                            it.search()
                            return@setLeftImage
                        }
                    }
                }
                mActionBarView.setTitle("e 途")
                mActionBarView.setRightImage(R.drawable.icon87) {
                    nextActivity<BoonActivity>("type" to 0)
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
        /**
         * viewpager点击事件
         */
        tab_select.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                select(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })

        /**
         * 中间按钮点击事件
         */
        iv_middle.setOnClickListener {
            tab_select.currentTab = 1
            select(1)
        }
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        Glide.get(this).clearMemory()
        RongIM.getInstance().disconnect()
        super.onDestroy()
    }
}

private class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "旅友"
            1 -> ""
            2 -> "个人"
            else -> ""
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> MapsFragment()
            2 -> AccountFragment.getInstance()
            else -> {
                LYFragment.getInstance()
            }
        }
    }

}