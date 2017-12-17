package com.yitu.etu.ui.fragment

import com.huizhuang.zxsq.utils.nextActivityFromFragment
import com.yitu.etu.R
import com.yitu.etu.ui.activity.BuyCarActivity
import com.yitu.etu.ui.activity.MapSelectActivity
import com.yitu.etu.ui.activity.MyOrderActivity
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
        /**
         * 我的钱包
         */
        tv_my_wallet.setOnClickListener {
            nextActivityFromFragment<MyWalletActivity>()
        }

        /**
         * 我的订单
         */
        tv_my_order.setOnClickListener {
            nextActivityFromFragment<MyOrderActivity>()
        }

        /**
         * 购物车
         */
        tv_buy_car.setOnClickListener {
            nextActivityFromFragment<BuyCarActivity>()
        }

        /**
         * 我的店铺
         */
        tv_my_shop.setOnClickListener {
            nextActivityFromFragment<MapSelectActivity>()
        }
    }
}