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
        rl_yu_e.visibility = if (bean.rechargetype == 0) View.GONE else View.VISIBLE
        iv_gou_alipay.visibility = if (bean.rechargetype == 0) View.VISIBLE else View.GONE
    }

    override fun getData() {
        with(bean) {
            tv_price.text = "￥$price"
            tv_des.text = desc
        }
    }

    fun iconVisibile() {
        iv_gou.visibility = when (type) {
            0 -> {
                View.VISIBLE
            }
            else -> View.GONE
        }
        iv_gou_alipay.visibility = when (type) {
            1 -> {
                View.VISIBLE
            }
            else -> View.GONE
        }
        iv_gou_weixin.visibility = when (type) {
            2 -> {
                View.VISIBLE
            }
            else -> View.GONE
        }
    }

    override fun initListener() {
        rl_yu_e.setOnClickListener {
            type = 0
            iconVisibile()
        }

        rl_alipay.setOnClickListener {
            type = 1
            iconVisibile()
        }

        rl_weixin.setOnClickListener {
            type = 2
            iconVisibile()
        }

        btn_pay_confim.setOnClickListener {
            if (type == 0) {
                dialog = PayPasswordDialog(this@PayOrderActivity, if (type == 0) "余额支付" else "支付宝支付")
                dialog.setContent(bean.price.toString())
                dialog.show()
                dialog.setOnPasswordChangedListener {
                    PayUtil.getInstance(bean).startPay(it, type, this@PayOrderActivity)
                }
            } else {
                PayUtil.getInstance(bean).startPay(null, type, this@PayOrderActivity)
            }
        }
    }
}
