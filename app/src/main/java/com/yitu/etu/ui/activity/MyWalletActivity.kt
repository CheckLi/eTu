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
import com.yitu.etu.util.Empty
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
    var money: Float = 0.0f
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
                        money = balance.Empty("0").toFloat()
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
            dialog.setRightBtn("确认", "请输入充值金额") {
                PayUtil.getInstance(-1, it, "余额充值", 0, BuyType.TYPE_BUY_P_AN)
                        .toPayActivity(this)
                dialog.dismiss()
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
                getMoney(withdrawDialog.getType().toString(), withdrawDialog.getContent(1)
                        , withdrawDialog.getContent(2), withdrawDialog.getContent(0), withdrawDialog.getContent(3))
            }
            withdrawDialog.showDialog()
        }
    }

    /**
     * 提现类型type（1为⽀付宝，2为微
    信，3为银⾏卡）、身份证号
    sfz（type=3时传⼊）、持卡⼈姓
    名realname（type=3时传⼊）、账
    号name、提现⾦额money

     */
    fun getMoney(type: String, idCard: String, realName: String, name: String, money: String) {
        val params = hashMapOf<String, String>()
        params.put("type", type)
        params.put("name", name)
        params.put("money", money)
        if(!idCard.isNullOrBlank()){
            params.put("sfz", idCard)
        }
        if(!realName.isNullOrBlank()){
            params.put("realname", realName)
        }

        showWaitDialog("申请中...")
        post(Urls.URL_GET_MONEY, params, object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    showToast("申请成功")
                    withdrawDialog.dismiss()
                    this@MyWalletActivity.money -= money.toFloat()
                    tv_wallet.text = this@MyWalletActivity.money.toString()
                } else {
                    showToast(response.message)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("申请失败")
            }

        })
    }

    @Subscribe
    fun onEventRefresh(refresh: EventRefresh) {
        if (refresh.classname == className) {
            getData()
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
