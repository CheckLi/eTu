package com.yitu.etu.wxapi


import android.content.Intent
import android.util.Log
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.eventBusItem.EventRefresh
import com.yitu.etu.ui.activity.BaseActivity
import com.yitu.etu.ui.activity.MyWalletActivity
import kotlinx.android.synthetic.main.pay_result.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.imageResource

class WXPayEntryActivity : BaseActivity(), IWXAPIEventHandler {

    private var TAG = "MicroMsg.SDKSample.WXPayEntryActivity"

    private var api: IWXAPI? = null

    override fun getLayout(): Int = R.layout.pay_result

    override fun initActionBar() {
        title = "支付结果"
    }

    override fun initView() {
        api = WXAPIFactory.createWXAPI(this, EtuApplication.getInstance().apP_ID)
        api!!.handleIntent(intent, this)
    }

    override fun getData() {

    }

    override fun initListener() {
        tv_result.setOnClickListener {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {}

    override fun onResp(resp: BaseResp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode)

        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            tv_pay_result.text=getString(R.string.pay_result_callback_msg, when (resp.errCode) {
                -1 -> "支付失败可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。"
                -2 -> "取消支付"
                else -> "支付成功"
            })
            when (resp.errCode) {
                0 -> iv_pay_img.imageResource = R.drawable.icon_success_pay
                else -> iv_pay_img.imageResource = R.drawable.pay_error
            }
            if(resp.errCode==0){
                EventBus.getDefault().post(EventRefresh(MyWalletActivity::class.java.simpleName))
            }
        }
    }
}