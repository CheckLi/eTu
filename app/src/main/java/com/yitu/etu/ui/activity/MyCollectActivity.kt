package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.MyCollectBean
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.CollectAdapter
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_travels.*
import okhttp3.Call
import java.lang.Exception

class MyCollectActivity : BaseActivity() {
    lateinit var adapter: CollectAdapter
    override fun getLayout(): Int = R.layout.activity_my_travels

    override fun initActionBar() {
        title = "我的收藏"
    }

    override fun initView() {
        adapter=CollectAdapter(listOf())
        listview.adapter = adapter
    }


    override fun getData() {
        refresh(true)
    }

    fun refresh(isRefresh: Boolean) {
        showWaitDialog("获取中...")
        post(Urls.URL_MY_COLLECT, hashMapOf("page" to page.toString()), object : GsonCallback<ArrayBaseEntity<MyCollectBean>>() {
            override fun onResponse(response: ArrayBaseEntity<MyCollectBean>, id: Int) {
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
                showToast("收藏获取失败")
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
        listview.setOnItemClickListener { parent, view, position, id ->
            nextActivity<TravelsDetailActivity>("travels_id" to adapter.getItem(position).id)
        }
    }

}
