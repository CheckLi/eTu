package com.yitu.etu.ui.fragment

import com.yitu.etu.R
import kotlinx.android.synthetic.main.fragment_ly_layout.*
import org.jetbrains.anko.bundleOf

/**
 * @className:LYFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 17:23
 */
class LYFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_ly_layout
    companion object {
        fun getInstance(title: String): LYFragment {
            val bun = bundleOf("title" to title)
            val ly = LYFragment()
            ly.arguments = bun
            return ly
        }

    }

    override fun initView() {
        tv_title.text = arguments.getString("title")
    }

    override fun getData() {

    }

    override fun initListener() {

    }
}
