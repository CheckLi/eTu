package com.yitu.etu.ui.activity

import android.view.View
import com.yitu.etu.R
import com.yitu.etu.dialog.PayPasswordDialog
import com.yitu.etu.entity.PayBean
import com.yitu.etu.util.pay.PayUtil
import kotlinx.android.synthetic.main.activity_pay_order.*

class PayOrderActivity : BaseActivity() {
    lateinit var dialog: PayPasswordDialog
    var type = 0//0余额，1支付宝
    lateinit var bean: PayBean

    override fun getLayout(): Int = R.layout.activity_pay_order

    override fun initActionBar() {
        title = "支付订单"
    }

    override fun initView() {
        bean = intent.getSerializableExtra("pay") as PayBean
    }

    override fun getData() {
        with(bean) {
            tv_price.text = "￥$price"
            tv_des.text = desc
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
                dialog.setContent(bean.price.toString())
                dialog.show()
                dialog.setOnPasswordChangedListener {
                    PayUtil.getInstance(bean).startPay(it,type,this@PayOrderActivity)
                }
            }else{
                PayUtil.getInstance(bean).startPay(null,type,this@PayOrderActivity)
            }
        }
    }
}
