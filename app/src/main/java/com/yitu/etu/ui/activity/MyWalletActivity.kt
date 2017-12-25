package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.dialog.InputPriceDialog
import com.yitu.etu.dialog.WithdrawDialog
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.pay.BuyType
import com.yitu.etu.util.pay.PayUtil
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_wallet.*
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.Exception

/**
 * 我的钱包
 */
class MyWalletActivity : BaseActivity() {
    lateinit var dialog: InputPriceDialog
    lateinit var withdrawDialog: WithdrawDialog
    override fun getLayout(): Int = R.layout.activity_my_wallet

    override fun initActionBar() {
        title = "我的钱包"
        setRightText("记录") {
            nextActivity<MyWalletHistoryActivity>()
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        dialog = InputPriceDialog(this, "余额充值")
        withdrawDialog = WithdrawDialog(this, "申请提现")
    }

    override fun getData() {
        showWaitDialog("获取中...")
        post(Urls.URL_GET_USER_INFO, hashMapOf(), object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
            override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    with(response.data) {
                        tv_wallet.text = balance
                        tv_safe_count.setSpanText("平安福数量\n%${safecount}个%")
                    }
                } else {
                    showToast(response.message)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("订单获取失败")
            }

        })
    }

    override fun initListener() {
        /**
         * 充值
         */
        tv_wallet_recharge.setOnClickListener {
            dialog.setRightBtn("确认") {
                showToast("充值")
            }
            dialog.show()
        }

        /**
         * 商品购买
         */
        tv_shop_buy.setOnClickListener {
            nextActivity<ProductListActivity>()
        }

        /**
         * 商品购买按钮
         */
        tv_buy.setOnClickListener {
            val dialog = InputPriceDialog(this, "请输入购买数量")
            dialog.setHint("请输入购买数量", false)
            dialog.show()
            dialog.setRightBtn("确认", "请输入数量") {
                PayUtil.getInstance(-1, it, "平安符购买", 1, BuyType.TYPE_BUY_P_AN)
                        .toPayActivity(this)
                dialog.dismiss()
            }
        }

        /**
         * 提现弹窗
         */
        tv_withdraw.setOnClickListener {
            withdrawDialog.setRightBtn("确认") {
                showToast(when (withdrawDialog.getType()) {
                    1 -> "支付宝提现"
                    2 -> "微信提现"
                    3 -> "银行卡提现"
                    else -> ""

                })
            }
            withdrawDialog.showDialog()
        }
    }

    @Subscribe
    fun onEventRefresh(refresh:EventRefresh){
        if(refresh.classname==className){
            getData()
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
