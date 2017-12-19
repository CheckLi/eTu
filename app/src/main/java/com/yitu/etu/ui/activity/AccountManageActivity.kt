package com.yitu.etu.ui.activity

import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import kotlinx.android.synthetic.main.activity_account_manage.*

class AccountManageActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_account_manage

    override fun initActionBar() {
        title = "账号管理"
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
        /**
         * 退出登陆
         */
        login_out.setOnClickListener {
            EtuApplication.getInstance().loginOut()
        }
    }

}
