package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.ProductListBean
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.ProductAdapter
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_product_list.*
import okhttp3.Call
import java.lang.Exception

class ProductListActivity : BaseActivity() {
    lateinit var adapter: ProductAdapter
    override fun getLayout(): Int = R.layout.activity_product_list

    override fun initActionBar() {
        title = "产品列表"
    }

    override fun initView() {
        adapter = ProductAdapter(listOf())
        listview.adapter = adapter
    }

    override fun getData() {
        showWaitDialog("获取中...")
        refresh(true)
    }

    fun refresh(isRefresh: Boolean) {
        if(isRefresh){
            RefreshSuccessInit(layout_refresh,isRefresh)
        }
        post(Urls.URL_PRODUCT_LIST, hashMapOf("page" to page.toString()), object : GsonCallback<ArrayBaseEntity<ProductListBean>>() {
            override fun onResponse(response: ArrayBaseEntity<ProductListBean>, id: Int) {
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
                RefreshSuccessInit(layout_refresh, isRefresh)
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
            showToast("开发中")

        }
    }

}
