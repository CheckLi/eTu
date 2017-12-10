package com.yitu.etu.ui.fragment

import android.view.LayoutInflater
import com.yitu.etu.R
import com.yitu.etu.ui.adapter.LYAdapter
import kotlinx.android.synthetic.main.fragment_ly_layout.*

/**
 * @className:LYFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 17:23
 */
class LYFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_ly_layout

    companion object {
        fun getInstance(): LYFragment {
            val ly = LYFragment()
            return ly
        }

    }

    override fun initView() {
        listView.addHeaderView(LayoutInflater.from(activity).inflate(R.layout.ly_head,null,false))
        listView.adapter = LYAdapter(activity, listOf())
    }

    override fun getData() {

    }

    override fun initListener() {

    }
}
