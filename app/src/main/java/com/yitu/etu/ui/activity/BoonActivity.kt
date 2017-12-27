package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.MyBoonBean
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.BoonAdapter
import com.yitu.etu.util.post
import com.yitu.etu.widget.ListSlideView
import kotlinx.android.synthetic.main.activity_my_travels.*
import okhttp3.Call
import java.lang.Exception

class BoonActivity : BaseActivity() {

    lateinit var adapter: BoonAdapter

    override fun getLayout(): Int = R.layout.activity_boon

    override fun initActionBar() {
        title = "e途福利"

    }

    override fun initView() {
        adapter = BoonAdapter(listOf())
        listview.adapter = adapter
        listview.setMode(ListSlideView.MODE_FORBID)
    }

    override fun getData() {
        showWaitDialog("获取中...")
        refresh(true)
    }

    fun refresh(isRefresh: Boolean) {
        if(isRefresh){
            RefreshSuccessInit(layout_refresh,isRefresh)
        }
        post(Urls.URL_MY_BOON_LIST, hashMapOf("page" to page.toString()), object : GsonCallback<ArrayBaseEntity<MyBoonBean>>() {
            override fun onResponse(response: ArrayBaseEntity<MyBoonBean>, id: Int) {
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
                RefreshSuccessInit(layout_refresh,isRefresh)
                showToast("福利获取失败")
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
            nextActivity<BoonPayDetailActivity>("detail" to adapter.getItem(position))
        }
    }


}
