package com.yitu.etu.ui.activity

import android.view.View
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.eventBusItem.LoginSuccessEvent
import com.yitu.etu.util.isLogin
import kotlinx.android.synthetic.main.activity_account_manage.*
import org.greenrobot.eventbus.EventBus

class AccountManageActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_account_manage

    override fun initActionBar() {
        title = "账号管理"
    }

    override fun initView() {
        login_out.visibility = if (isLogin()) View.VISIBLE else View.GONE
    }

    override fun getData() {
    }

    override fun initListener() {
        /**
         * 退出登陆
         */
        login_out.setOnClickListener {
            EtuApplication.getInstance().loginOut()
            finish()
        }
        /**
         * 退出登陆
         */
        tv_er.setOnClickListener {
            EventBus.getDefault().post(LoginSuccessEvent(null))
        }
    }

}
