package com.yitu.etu.ui.fragment

import android.os.Bundle
import com.huizhuang.zxsq.utils.nextActivityFromFragment
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.OrderList
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.activity.OrderDetailActivity
import com.yitu.etu.ui.adapter.OrderAdapter
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.fragment_order_layout.*
import okhttp3.Call
import java.lang.Exception

/**
 *
 *@className:OrderFragment
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月14日 22:28
 *
 */
class OrderFragment :BaseFragment(){

    private var type=1//1未使用，2已使用
    private lateinit var adapter:OrderAdapter
    override fun getLayout(): Int= R.layout.fragment_order_layout

    companion object {
        fun getInstance(type:Int): OrderFragment {
            val bundle=Bundle()
            bundle.putInt("type",type)
            val fragment=OrderFragment()
            fragment.arguments=bundle
            return fragment
        }
    }

    override fun initView() {
        type=arguments.getInt("type")
        adapter=OrderAdapter(listOf())
        listView_order.adapter=adapter
    }

    override fun getData() {
        if(type==2){
            showWaitDialog("获取订单...")
        }
        refresh(true)
    }

    private fun refresh(isRefresh:Boolean) {
        if(isRefresh){
            RefreshSuccessInit(layout_refresh, isRefresh)
        }
        post(Urls.URL_ORDER_LIST_USE, hashMapOf("page" to page.toString(), "type" to type.toString()), object : GsonCallback<ArrayBaseEntity<OrderList>>() {
            override fun onResponse(response: ArrayBaseEntity<OrderList>, id: Int) {
                hideWaitDialog()

                if (response.success() && response.data.size > 0) {
                    if(isRefresh) {
                        adapter.replaceAll(response.data)
                    }else{
                        adapter.addAll(response.data)
                    }
                    RefreshSuccess(layout_refresh,isRefresh,response.data.size)
                } else if (!response.success()) {
                    RefreshSuccess(layout_refresh,isRefresh,0)
                    showToast(response.message)
                }else{
                    RefreshSuccess(layout_refresh,isRefresh,0)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                RefreshSuccessInit(layout_refresh, isRefresh)
                showToast("订单获取失败")
            }

        })
    }

    override fun initListener() {
        layout_refresh.setOnRefreshListener {
            refresh(true)
        }
        layout_refresh.setOnLoadmoreListener {
            refresh(false)
        }
        listView_order.setOnItemClickListener { parent, view, position, id ->
            nextActivityFromFragment<OrderDetailActivity>("order_detail" to adapter.getItem(position))
        }
    }
}