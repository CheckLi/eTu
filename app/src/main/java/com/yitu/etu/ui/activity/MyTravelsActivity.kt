package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.Iinterface.IDelListener
import com.yitu.etu.R
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.MyRouteBean
import com.yitu.etu.entity.MyTravels
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.TravelsAdapter
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_travels.*
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.Exception

class MyTravelsActivity : BaseActivity() {
    lateinit var adapter:TravelsAdapter
    override fun getLayout(): Int= R.layout.activity_my_travels

    override fun initActionBar() {
        title="我的游记"
        setRightText("发布"){
           nextActivity<ReleaseMyTravelsActivity>()
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        adapter=TravelsAdapter(listOf())
        listview.adapter=adapter
    }

    override fun getData() {
        showWaitDialog("获取中...")
        refresh(true)
    }

     fun refresh(isRefresh:Boolean) {
         if (isRefresh) {
             RefreshSuccessInit(layout_refresh,isRefresh)
         }
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
                RefreshSuccessInit(layout_refresh,isRefresh)
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

        adapter.setDelListener(object : IDelListener<MyTravels> {
            override fun del(delPosition: Int, bean: MyTravels) {
                delPost(delPosition,bean.id.toString())
            }

        })
    }

    fun delPost(position:Int,id: String){
        showWaitDialog("删除中...")
        post(Urls.URL_MY_TRAVELS_DEL, hashMapOf("id" to id), object : GsonCallback<ObjectBaseEntity<MyRouteBean>>() {
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
                showToast("游记删除失败")
            }

        })
    }

    @Subscribe
    fun onEventRefresh(event:EventRefresh){
        if(event.classname==className){
            refresh(true)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
