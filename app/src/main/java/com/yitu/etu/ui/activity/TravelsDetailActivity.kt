package com.yitu.etu.ui.activity

import android.view.View
import android.webkit.WebView
import android.widget.AdapterView
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.entity.MyTravelsDetail
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.*
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import kotlinx.android.synthetic.main.actionbar_layout.view.*
import kotlinx.android.synthetic.main.activity_travels_detail.*
import okhttp3.Call
import java.lang.Exception

class TravelsDetailActivity : BaseActivity() {
    var web:WebView?=null
    override fun getLayout(): Int = R.layout.activity_travels_detail

    override fun initActionBar() {
        title="游记详情"
    }

    override fun initView() {

    }

    override fun getData() {
        showWaitDialog("获取中...")
        post(Urls.URL_MY_TRAVELS_DETAIL, hashMapOf("id" to intent.getIntExtra("travels_id",0).toString()), object : GsonCallback<ObjectBaseEntity<MyTravelsDetail>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
               showToast("详情获取失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<MyTravelsDetail>, id: Int) {
                hideWaitDialog()
                if(response.success()){
                    with(response.data){
                        ImageLoadUtil.getInstance().loadImage(iv_head,user.header.addHost(),50,50)
                        tv_title1.text=title
                        tv_name.text=user.name
                        tv_time.text="发布日期：${created.getTime()}"
                        web=WebView(this@TravelsDetailActivity)
                        ll_content.addView(web)
                        web?.loadDataWithBaseURL(null,text, "text/html","utf-8",null)
                        setRight(this)
                    }
                }else{
                    showToast(response.message)
                }
            }

        })
    }

    fun setRight(bean:MyTravelsDetail){
        if(userInfo().id!=bean.userId){
            setRightClick(R.drawable.icon145){
                val pop = Tools.getPopupWindow(this@TravelsDetailActivity, arrayOf("加入收藏", "发送给好友"), object : AdapterView.OnItemClickListener {
                    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (!isLogin()) {
                            showToast("请先登录")
                        } else {
                            when (position) {
                                0 -> nextActivity<SearchFriendActivity>()
                                1 -> nextActivity<FriendListActivity>()
                                else -> ""
                            }
                        }
                    }

                }, "right")
                pop.showAsDropDown(mActionBarView.iv_right, 20, 0)
            }
        }
    }

    override fun initListener() {
    }

    override fun onDestroy() {
        web?.clearHistory()
        web?.removeAllViews()
        web?.destroy()
        super.onDestroy()
    }
}
