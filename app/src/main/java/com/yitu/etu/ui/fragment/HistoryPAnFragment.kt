package com.yitu.etu.ui.fragment

import android.os.Bundle
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.HistoryPanBean
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.HistoryPAnAdapter
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
class HistoryPAnFragment : BaseFragment() {
    var type=0
    private lateinit var adapter: HistoryPAnAdapter
    override fun getLayout(): Int = R.layout.fragment_order_layout

    companion object {
        fun getInstance(type: Int): HistoryPAnFragment {
            val bundle = Bundle()
            bundle.putInt("type", type)
            val fragment = HistoryPAnFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        type=arguments.getInt("type")
        adapter = HistoryPAnAdapter(listOf(),type)
        listView_order.adapter = adapter
    }

    override fun getData() {
        if(type==0) {
            showWaitDialog("获取记录...")
        }
        refresh(true)
    }

    private fun refresh(isRefresh: Boolean) {
        post(if(type==0) Urls.URL_HISTORY_P_AN else Urls.URL_HISTORY_YU_E, hashMapOf("page" to page.toString()), object : GsonCallback<ArrayBaseEntity<HistoryPanBean>>() {
            override fun onResponse(response: ArrayBaseEntity<HistoryPanBean>, id: Int) {
                hideWaitDialog()

                if (response.success() && response.data.size > 0) {
                    if (isRefresh) {
                        adapter.replaceAll(response.data)
                    } else {
                        adapter.addAll(response.data)
                    }
                    RefreshSuccess(layout_refresh, isRefresh, response.data.size)
                } else if (!response.success()) {
                    RefreshSuccess(layout_refresh, isRefresh, 0)
                    showToast(response.message)
                } else {
                    RefreshSuccess(layout_refresh, isRefresh, 0)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                RefreshSuccess(layout_refresh, isRefresh, 0)
                showToast("记录取失败")
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
    }
}