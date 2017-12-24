package com.yitu.etu.ui.fragment

import android.os.Bundle
import com.huizhuang.zxsq.utils.nextActivityFromFragment
import com.yitu.etu.Iinterface.IDelListener
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.MyRouteBean
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.activity.SearchResultOrderSceneActivity
import com.yitu.etu.ui.adapter.RouteAdapter
import com.yitu.etu.util.post
import com.yitu.etu.widget.ListSlideView
import kotlinx.android.synthetic.main.fragment_route_layout.*
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
class RouteFragment : BaseFragment() {
    var type = 1
    lateinit var adapter: RouteAdapter

    companion object {
        fun getInstance(type: Int): RouteFragment {
            val bundle = Bundle()
            bundle.putInt("type", type)
            val fragment = RouteFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayout(): Int = R.layout.fragment_route_layout

    override fun initView() {
        type = arguments.getInt("type")
        adapter = RouteAdapter(listOf())
        listView_order.adapter = adapter
    }

    override fun getData() {
        if (type == 1) {
            showWaitDialog("获取中...")
        }
        when (type) {
            1 -> {
                listView_order.setMode(ListSlideView.MODE_FORBID)
                postData(Urls.URL_MY_ADD_ROUTE, true)
            }
            2 -> {
                listView_order.setMode(ListSlideView.MODE_RIGHT)
                postData(Urls.URL_MY_PUBLISH_ROUTE, true)
            }
        }
    }

    fun postData(url: String, isRefresh: Boolean) {
        post(url, hashMapOf("page" to page.toString()), object : GsonCallback<ArrayBaseEntity<MyRouteBean>>() {
            override fun onResponse(response: ArrayBaseEntity<MyRouteBean>, id: Int) {
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
                showToast("行程获取失败")
            }

        })
    }

    fun delPost(position:Int,id: String){
        showWaitDialog("删除中...")
        post(Urls.URL_MY_PUBLISH_ROUTE_DEL, hashMapOf("action_id" to id), object : GsonCallback<ObjectBaseEntity<MyRouteBean>>() {
            override fun onResponse(response: ObjectBaseEntity<MyRouteBean>, id: Int) {
                hideWaitDialog()

                if (response.success()) {
                    showToast("删除成功")
                    adapter.remove(position)
                } else {
                    showToast(response.message)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("行程删除失败")
            }

        })
    }

    override fun initListener() {
        layout_refresh.setOnRefreshListener {
            when (type) {
                1 -> {
                    postData(Urls.URL_MY_ADD_ROUTE, true)
                }
                2 -> {
                    postData(Urls.URL_MY_PUBLISH_ROUTE, true)
                }
            }

        }
        layout_refresh.setOnLoadmoreListener {
            when (type) {
                1 -> {
                    postData(Urls.URL_MY_ADD_ROUTE, false)
                }
                2 -> {
                    postData(Urls.URL_MY_PUBLISH_ROUTE, false)
                }
            }
        }
        listView_order.setOnItemClickListener { parent, view, position, id ->
            nextActivityFromFragment<SearchResultOrderSceneActivity>()
        }

        adapter.setDelListener(object : IDelListener<MyRouteBean>{
            override fun del(delPosition: Int, bean: MyRouteBean) {
                delPost(delPosition,bean.id.toString())
            }

        })
    }
}