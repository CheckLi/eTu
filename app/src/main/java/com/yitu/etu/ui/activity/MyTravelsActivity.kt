package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.MyTravels
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.TravelsAdapter
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_travels.*
import okhttp3.Call
import java.lang.Exception

class MyTravelsActivity : BaseActivity() {
    lateinit var adapter:TravelsAdapter
    override fun getLayout(): Int= R.layout.activity_my_travels

    override fun initActionBar() {
        title="我的游记"
        setRightText("发布"){
            showToast("发布")
        }
    }

    override fun initView() {
        adapter=TravelsAdapter(listOf())
        listview.adapter=adapter
    }

    override fun getData() {
        refresh(true)
    }

     fun refresh(isRefresh:Boolean) {
         showWaitDialog("获取中...")
        post(Urls.URL_MY_TRAVELS, hashMapOf("page" to page.toString()), object : GsonCallback<ArrayBaseEntity<MyTravels>>() {
            override fun onResponse(response: ArrayBaseEntity<MyTravels>, id: Int) {
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
                showToast("游记获取失败")
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
