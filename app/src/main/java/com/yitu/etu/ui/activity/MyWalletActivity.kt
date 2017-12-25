package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.dialog.InputPriceDialog
import com.yitu.etu.dialog.WithdrawDialog
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_wallet.*
import okhttp3.Call
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
            showToast("钱包记录")
        }
    }

    override fun initView() {
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
            nextActivity<ProductListActivity>()
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


}
