package com.yitu.etu.ui.activity

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.yitu.etu.R
import com.yitu.etu.dialog.PayPasswordDialog
import com.yitu.etu.entity.PayBean
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.util.AuthResult
import com.yitu.etu.util.PayResult
import com.yitu.etu.util.pay.PayUtil
import kotlinx.android.synthetic.main.activity_pay_order.*
import org.greenrobot.eventbus.EventBus

class PayOrderActivity : BaseActivity() {
    private val SDK_PAY_FLAG = 1
    private val SDK_AUTH_FLAG = 2
    lateinit var dialog: PayPasswordDialog
    var type = 0//0余额，1支付宝
    lateinit var bean: PayBean
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.result// 同步返回需要验证的信息
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(this@PayOrderActivity, "支付成功", Toast.LENGTH_SHORT).show()
                        finish()
                        if (bean.rechargetype == 0) {
                            EventBus.getDefault().post(EventRefresh(bean.classname))
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(this@PayOrderActivity, "支付失败", Toast.LENGTH_SHORT).show()
                    }
                }
                SDK_AUTH_FLAG -> {
                    val authResult = AuthResult(msg.obj as Map<String, String>, true)
                    val resultStatus = authResult.getResultStatus()

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(this@PayOrderActivity,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show()
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(this@PayOrderActivity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show()

                    }
                }
                else -> {
                }
            }
        }
    }

    fun sendMessage(message: Message){
        mHandler.sendMessage(message)
    }

    override fun getLayout(): Int = R.layout.activity_pay_order

    override fun initActionBar() {
        title = "支付订单"
    }

    override fun initView() {
        bean = intent.getSerializableExtra("pay") as PayBean
        rl_yu_e.visibility = if (bean.rechargetype == 0) View.GONE else View.VISIBLE
        iv_gou_alipay.visibility = if (bean.rechargetype == 0) View.VISIBLE else View.GONE
        type= if (bean.rechargetype == 0) 1 else 0
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
