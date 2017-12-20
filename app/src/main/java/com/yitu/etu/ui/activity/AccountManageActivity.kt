package com.yitu.etu.ui.activity

import android.view.View
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.eventBusItem.EventClearSuccess
import com.yitu.etu.util.isLogin
import kotlinx.android.synthetic.main.activity_account_manage.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AccountManageActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_account_manage

    override fun initActionBar() {
        title = "账号管理"
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        login_out.visibility = if (isLogin()) View.VISIBLE else View.GONE
    }

    override fun getData() {
    }

    override fun initListener() {

        /**
         * 我的二维码
         */
        tv_er.setOnClickListener {
            nextActivity<TwoCodeActivity>()
        }

        /**
         * 清理缓存
         */
        tv_clear_cache.setOnClickListener {
            showWaitDialog("清理中...")
            EtuApplication.getInstance().clearCache()
        }

        /**
         * 退出登陆
         */
        login_out.setOnClickListener {
            EtuApplication.getInstance().loginOut()
            finish()
        }

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
