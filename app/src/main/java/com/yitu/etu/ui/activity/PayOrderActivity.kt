package com.yitu.etu.ui.activity

import android.view.View
import com.yitu.etu.R
import com.yitu.etu.dialog.PayPasswordDialog
import com.yitu.etu.entity.MyBoonBean
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.Empty
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_pay_order.*
import okhttp3.Call
import java.lang.Exception

class PayOrderActivity : BaseActivity() {
    lateinit var dialog: PayPasswordDialog
    var type = 0//0余额，1支付宝
    lateinit var bean: MyBoonBean

    override fun getLayout(): Int = R.layout.activity_pay_order

    override fun initActionBar() {
        title = "支付订单"
    }

    override fun initView() {
        bean = intent.getSerializableExtra("detail") as MyBoonBean
    }

    override fun getData() {
        with(bean) {
            tv_price.text = "￥$price"
            tv_des.text = "购买门票：$name"
        }
    }

    override fun initListener() {
        rl_yu_e.setOnClickListener {
            iv_gou.visibility = View.VISIBLE
            iv_gou_alipay.visibility = View.GONE
            type = 0
        }

        rl_alipay.setOnClickListener {
            iv_gou.visibility = View.GONE
            iv_gou_alipay.visibility = View.VISIBLE
            type = 1
        }

        btn_pay_confim.setOnClickListener {
            if(type==0) {
                dialog = PayPasswordDialog(this@PayOrderActivity, if (type == 0) "余额支付" else "支付宝支付")
                dialog.setContent(bean.price)
                dialog.show()
                dialog.setOnPasswordChangedListener {
                    pay(it)
                }
            }else{
                pay(null)
            }
        }
    }

    fun pay(paypassword: String?) {
        showWaitDialog("支付中...")
        val  params=hashMapOf(
                "id" to bean.id.toString(),
                "paytype" to type.toString()
        )
        if(type==0) {
            params.put("paypwd", paypassword.Empty())
        }
        post(Urls.URL_MY_BOON_CREATE, params, object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    if(this@PayOrderActivity.type==0) {
                        showToast("支付成功")
                        dialog.dismiss()
                        finish()
                    }else{
                        showToast("支付数据获取成功")
                    }
                } else {
                    showToast(response.message)
                    dialog.clearPassword()
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("支付失败")
                dialog.clearPassword()
            }

        })
    }
}
