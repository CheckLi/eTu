package com.yitu.etu.ui.fragment

import com.huizhuang.zxsq.utils.nextActivityFromFragment
import com.yitu.etu.R
import com.yitu.etu.ui.activity.MyWalletActivity
import kotlinx.android.synthetic.main.fragment_account_layout.*

/**
 * @className:LYFragment
 * @description:`
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

    }

    override fun getData() {

    }

    override fun initListener() {
        tv_my_wallet.setOnClickListener {
            nextActivityFromFragment<MyWalletActivity>()
        }
    }
}