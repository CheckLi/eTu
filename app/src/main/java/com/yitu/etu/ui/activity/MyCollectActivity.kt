package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.Iinterface.IDelListener
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.MyCollectBean
import com.yitu.etu.entity.MyRouteBean
import com.yitu.etu.entity.ObjectBaseEntity
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
        adapter = CollectAdapter(listOf())
        listview.adapter = adapter
    }


    override fun getData() {
        showWaitDialog("获取中...")
        refresh(true)
    }

    fun refresh(isRefresh: Boolean) {
        if (isRefresh) {
            RefreshSuccessInit(layout_refresh, isRefresh)
        }
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
            //            type字段表示，0文章，1为文章，2为景点，3为出行活动，5为美食店铺，6为住宿店铺，7为游玩店铺
            when (adapter.getItem(position).type) {
                2 -> nextActivity<SearchResultSceneActivity>("id" to adapter.getItem(position).pid.toString())
                3 -> nextActivity<SearchResultOrderSceneActivity>("id" to adapter.getItem(position).pid.toString(), "title" to adapter.getItem(position).name)
                5,6,7 -> nextActivity<SceneShopProductActivity>("id" to adapter.getItem(position).pid, "title" to adapter.getItem(position).name,"type" to adapter.getItem(position).type)
                else -> showToast("开发中")
            }

        }

        adapter.setDelListener(object : IDelListener<MyCollectBean> {
            override fun del(delPosition: Int, bean: MyCollectBean) {
                delPost(delPosition, bean.id.toString())
            }

        })
    }


    fun delPost(position: Int, id: String) {
        showWaitDialog("删除中...")
        post(Urls.URL_MY_COLLECT_DEL, hashMapOf("id" to id), object : GsonCallback<ObjectBaseEntity<MyRouteBean>>() {
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
}
