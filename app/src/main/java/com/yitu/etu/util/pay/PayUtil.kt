package com.yitu.etu.util.pay

import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.alipay.sdk.app.PayTask
import com.tencent.mm.opensdk.modelpay.PayReq
import com.yitu.etu.EtuApplication
import com.yitu.etu.entity.PayBean
import com.yitu.etu.entity.PayResultBean
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.tools.Http.post
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.activity.MainActivity
import com.yitu.etu.ui.activity.PayOrderActivity
import com.yitu.etu.util.Empty
import com.yitu.etu.util.JsonUtil
import com.yitu.etu.util.activityUtil
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.bundleOf
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.concurrent.thread



/**
 * @className:PayUtil
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月25日 23:09
 */
class PayUtil(val id: Int, val price: Float, val desc: String, val rechargetype: Int, val classname: String, val buyType: Int, val params1: MutableMap<String, String>) {
    constructor(id: Int, price: Float, desc: String, buyType: Int) : this(id, price, desc, -1, "", buyType, mutableMapOf()) {}

    companion object {
        @JvmStatic
        fun getInstance(id: Int, price: Float, desc: String, buyType: Int): PayUtil {
            return PayUtil(id, price, desc, buyType)
        }

        @JvmStatic
        fun getInstance(id: Int, price: Float, desc: String, rechargetype: Int, buyType: Int): PayUtil {
            return PayUtil(id, price, desc, rechargetype, "", buyType, mutableMapOf())
        }

        @JvmStatic
        fun getInstance(payBean: PayBean): PayUtil {
            return PayUtil(payBean.id, payBean.price, payBean.desc, payBean.rechargetype, payBean.classname, payBean.buyType, payBean.params)
        }
    }

    /**
     * 开始进入支付
     */
    fun toPayActivity(activity: AppCompatActivity) {
        val payBean = PayBean(id, price, desc, rechargetype, activity.javaClass.simpleName, buyType)
        activityUtil.nextActivity(activity, PayOrderActivity::class.java, bundleOf("pay" to payBean), false)
    }

    /**
     * 开始进入支付
     */
    fun toPayActivity(activity: AppCompatActivity, params: MutableMap<String, String>) {
        val payBean = PayBean(id, price, desc, rechargetype, activity.javaClass.simpleName, buyType, params)
        activityUtil.nextActivity(activity, PayOrderActivity::class.java, bundleOf("pay" to payBean), false)
    }

    /**
     * type = 0//0余额，1支付宝
     */
    fun startPay(pwd: String?, type: Int, activity: PayOrderActivity) {
        pay(pwd, type, activity)
    }

    /**
     * 门票支付
     */
    private fun pay(paypassword: String?, type: Int, activity: PayOrderActivity) {
        activity.showWaitDialog("支付中...")
        val params = hashMapOf(
                "paytype" to type.toString()
        )

        if (id != -1) {
            params.put("id", id.toString())
        }
        if (type == 0 && !paypassword.isNullOrBlank()) {
            params.put("paypwd", paypassword.Empty())
        }
        if (rechargetype != -1) {
            params.put("rechargetype", rechargetype.toString())
        }
        for (param in params1) {
            params.put(param.key, param.value)
        }
        when (buyType) {
        //门票购买
            BuyType.TYPE_BUY_TICKET -> {
                payTicket(Urls.URL_MY_BOON_CREATE, params, activity, type)
            }
        //平安符购买
            BuyType.TYPE_BUY_P_AN -> {
                params.put("money", price.toString())
                payTicket(Urls.URL_BUY_P_AN, params, activity, type)
            }
        //店铺商品创建订单
            BuyType.TYPE_BUY_SHOP_PROJECT -> {
                payTicket(Urls.URL_SHOP_PROJECT, params, activity, type)
            }
        //活动支付
            BuyType.TYPE_BUY_ACTION -> {
                params.put("money", price.toString())
                payTicket(Urls.URL_BUY_P_AN, params, activity, type)
            }
        }

    }

    private fun payTicket(url: String, params: HashMap<String, String>, activity: PayOrderActivity, type: Int) {
        post(url, params, object : StringCallback() {
            override fun onResponse(response: String, id: Int) {
                val json = JSONObject(response)

                if (!activity.isFinishing) {
                    activity.hideWaitDialog()
                    if (json.getInt("status") == 1) {

                        if (type == 0) {
                            activity.showToast("支付成功")
                            activity.dialog.dismiss()
                            activity.finish()
                            EventBus.getDefault().post(EventRefresh(classname))
                            EventBus.getDefault().post(EventRefresh(MainActivity::class.java.simpleName))

                        } else if (type == 1) {
                            thread {
                                val alipay = PayTask(activity)
                                val result =alipay.payV2(json.optJSONObject("data").optString("parstr"), true)
                                activity.runOnUiThread {
                                    val msg = Message()
                                    msg.what = 1
                                    msg.obj = result
                                    activity.sendMessage(msg)
                                }

                            }
                        } else if (type == 2) {
                            val bean = JsonUtil.getInstance().getJsonBean(json.optString("data"), PayResultBean::class.java)
                            EtuApplication.getInstance().apP_ID = bean.parstr.appid.Empty(EtuApplication.getInstance().apP_ID)
                            val result = bean.parstr
                            val pay = PayReq()
                            result?.run {
                                pay.appId = appid
                                pay.partnerId = partnerid
                                pay.prepayId = prepayid
                                pay.packageValue = packageX
                                pay.nonceStr = noncestr
                                pay.timeStamp = timestamp
                                pay.sign = sign
                                EtuApplication.getInstance().mIWXAPI.sendReq(pay)
                            }

                        }
                    } else {
                        activity.showToast(json.optString("message"))
                        activity.dialog.clearPassword()
                    }
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                if (!activity.isFinishing) {
                    activity.hideWaitDialog()
                    activity.showToast("支付失败")
                    activity.dialog.clearPassword()
                }
            }

        })
    }
}
