package com.yitu.etu.util.pay

import android.support.v7.app.AppCompatActivity
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.PayBean
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http.post
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.activity.PayOrderActivity
import com.yitu.etu.util.Empty
import com.yitu.etu.util.activityUtil
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.bundleOf
import java.lang.Exception

/**
 * @className:PayUtil
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月25日 23:09
 */
class PayUtil(val id: Int, val price: Float, val desc: String,val rechargetype: Int,val classname:String, val buyType: Int) {
    constructor(id: Int, price: Float, desc: String, buyType: Int) : this(id, price, desc, -1,"", buyType) {}

    companion object {
        @JvmStatic
        fun getInstance(id: Int, price: Float, desc: String, buyType: Int): PayUtil {
            return PayUtil(id, price, desc, buyType)
        }

        @JvmStatic
        fun getInstance(id: Int, price: Float, desc: String, rechargetype: Int, buyType: Int): PayUtil {
            return PayUtil(id, price, desc, rechargetype,"", buyType)
        }

        @JvmStatic
        fun getInstance(payBean: PayBean): PayUtil {
            return PayUtil(payBean.id, payBean.price, payBean.desc,payBean.rechargetype,payBean.classname, payBean.buyType)
        }
    }

    /**
     * 开始进入支付
     */
    fun toPayActivity(activity: AppCompatActivity) {
        val payBean = PayBean(id, price, desc,rechargetype,activity.javaClass.simpleName, buyType)
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

        if(id!=-1){
            params.put("id", id.toString())
        }
        if (type == 0 && !paypassword.isNullOrBlank()) {
            params.put("paypwd", paypassword.Empty())
        }
        if (rechargetype != -1) {
            params.put("rechargetype", rechargetype.toString())
        }
        when (buyType) {
        //门票购买
            BuyType.TYPE_BUY_TICKET -> {
                payTicket(Urls.URL_MY_BOON_CREATE, params, activity, type)
            }
        //平安符购买
            BuyType.TYPE_BUY_P_AN -> {
                params.put("money",price.toString())
                payTicket(Urls.URL_BUY_P_AN, params, activity, type)
            }
        }

    }

    private fun payTicket(url: String, params: HashMap<String, String>, activity: PayOrderActivity, type: Int) {
        post(url, params, object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                if (!activity.isFinishing) {
                    activity.hideWaitDialog()
                    if (response.success()) {
                        if (type == 0) {
                            activity.showToast("支付成功")
                            activity.dialog.dismiss()
                            activity.finish()
                            EventBus.getDefault().post(EventRefresh(classname))
                        } else {
                            activity.showToast("支付数据获取成功")
                        }
                    } else {
                        activity.showToast(response.message)
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