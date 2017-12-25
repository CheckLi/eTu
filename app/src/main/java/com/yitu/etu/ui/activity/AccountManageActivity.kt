package com.yitu.etu.ui.activity

import android.view.View
import com.huizhuang.zxsq.utils.nextActivity
import com.huizhuang.zxsq.utils.nextCheckLoginActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.dialog.PayPasswordDialog
import com.yitu.etu.dialog.TipsDialog
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.eventBusItem.EventClearSuccess
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.Empty
import com.yitu.etu.util.isLogin
import com.yitu.etu.util.post
import com.yitu.etu.util.userInfo
import kotlinx.android.synthetic.main.activity_account_manage.*
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.Exception

class AccountManageActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_account_manage
    lateinit var dialog: PayPasswordDialog
    override fun initActionBar() {
        title = "账号管理"
    }

    override fun initView() {
        dialog = PayPasswordDialog(this, "设置支付密码")
        EventBus.getDefault().register(this)
        login_out.visibility = if (isLogin()) View.VISIBLE else View.GONE
        tv_pay_password.append(if (isLogin() && userInfo().ispaypwd != 0) "（已设置）" else "（未设置）")
    }

    override fun getData() {

    }

    override fun initListener() {

        /**
         * 我的二维码
         */
        tv_er.setOnClickListener {
            nextCheckLoginActivity<TwoCodeActivity>()
        }

        /**
         * 资料修改
         */
        tv_chang_info.setOnClickListener {
            nextCheckLoginActivity<AccountDataActivity>()
        }

        /**
         * 密码修改
         */
        tv_password_change.setOnClickListener {
            nextCheckLoginActivity<PasswordChangActivity>()
        }

        /**
         * 我的认证
         */
        tv_my_renZ.setOnClickListener {
            nextCheckLoginActivity<MyCertificationActivity>()
        }

        /**
         * 我的认证
         */
        tv_my_renZ.setOnClickListener {
            nextCheckLoginActivity<MyCertificationActivity>()
        }

        /**
         * 设置支付密码
         */
        tv_pay_password.setOnClickListener {
            if (isLogin() && userInfo().ispaypwd != 0) {
                nextCheckLoginActivity<RePayPasswordActivity>()
            } else if (isLogin()) {
                if (dialog.isShowing) {
                    dialog.hideDialog()
                }
                dialog.showDialog()
                dialog.setOnPasswordChangedListener {
                    setPassword(it)
                }
            } else {
                nextActivity<LoginActivity>()
            }
        }

        /**
         * 清理缓存
         */
        tv_clear_cache.setOnClickListener {
            showWaitDialog("清理中...")
            EtuApplication.getInstance().clearCache()
        }


        /**
         * 关于软件
         */
        tv_about_me.setOnClickListener {
            nextActivity<AboutMeActivity>()
        }

        /**
         * 退出登陆
         */
        login_out.setOnClickListener {
            val dialog=TipsDialog(this,"温馨提示")
            dialog.setMessage("确认要退出登陆吗？")
            dialog.setRightBtn("确认"){
                EtuApplication.getInstance().loginOut()
                finish()
            }
            dialog.show()
        }

    }

    /**
     * 设置支付密码
     */
    private fun setPassword(pwd: String?) {
        showWaitDialog("密码设置中...")
        post(Urls.setPayPassword, hashMapOf("paypwd" to pwd.Empty()), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    showToast("设置成功")
                    dialog.hideDialog()
                    tv_pay_password.text = "支付密码（已设置）"
                    userInfo().ispaypwd = 1
                    EtuApplication.getInstance().userInfo = userInfo()
                } else {
                    showToast(response.message)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("设置失败")
            }

        })
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe
    fun onEventClearCacheSuccess(event: EventClearSuccess) {
        runOnUiThread {
            hideWaitDialog()
            showToast(when (event.success) {
                true -> "清理完成"
                else -> "清理失败"
            })
        }
    }

}
