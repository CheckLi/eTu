package com.yitu.etu.ui.fragment

import com.yitu.etu.R
import com.yitu.etu.ui.adapter.RouteAdapter
import kotlinx.android.synthetic.main.fragment_order_layout.*

/**
 *
 *@className:OrderFragment
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:28
 *
 */
class RouteFragment :BaseFragment(){
    override fun getLayout(): Int= R.layout.fragment_route_layout

    override fun initView() {
        listView_order.adapter=RouteAdapter(listOf())
    }

    override fun getData() {
    }

    override fun initListener() {
    }
}