package com.yitu.etu.ui.fragment

import com.yitu.etu.R
import com.yitu.etu.util.LogUtil

/**
 * @className:LYFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 17:23
 */
class AccountFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_account_layout
    companion object {
        fun getInstance(): AccountFragment {
            return AccountFragment()
        }

    }

    override fun initView() {
        LogUtil.e("初始化")
    }

    override fun getData() {

    }

    override fun initListener() {

    }
}