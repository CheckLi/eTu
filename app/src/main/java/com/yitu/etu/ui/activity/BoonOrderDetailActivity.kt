package com.yitu.etu.ui.activity

import android.content.Intent
import android.widget.TextView
import com.yitu.etu.R
import com.yitu.etu.entity.MyBoonListBean
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.Empty
import com.yitu.etu.util.getTime
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_boon_order_detail.*
import okhttp3.Call
import java.lang.Exception

class BoonOrderDetailActivity : BaseActivity() {
    lateinit var id: String
    override fun getLayout(): Int = R.layout.activity_boon_order_detail

    override fun getIntentExtra(intent: Intent?) {
        id = intent?.getStringExtra("order_id")?:""
    }

    override fun initActionBar() {
        title = "订单详情"
    }

    override fun initView() {

    }

    override fun getData() {
        showWaitDialog("获取中...")
        post(Urls.URL_MY_BOON_ORDER_DETAIL, hashMapOf("id" to id), object : GsonCallback<ObjectBaseEntity<MyBoonListBean>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("数据获取失败")
            }

            override fun onResponse(response: ObjectBaseEntity<MyBoonListBean>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    with(response.data) {
                        tr_order.run {
                            for (pos in 0 until childCount) {
                                val text = (getChildAt(pos) as TextView)
                                when (pos) {
                                    0 -> text.text = ticket?.name.Empty()
                                    1 -> text.text = this@with.id.toString()
                                    2 -> text.text = ticket?.price.Empty()
                                    3 -> text.text = created.getTime()
                                }
                            }
                            tv_number.text = "兑换编码：$checkSn"
                        }
                    }
                } else {
                    showToast(response.message)
                }
            }

        })
    }

    override fun initListener() {

    }

}