package com.yitu.etu.ui.activity

import android.util.Log
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.eventBusItem.LoginSuccessEvent
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http.post
import com.yitu.etu.tools.Urls
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_login

    override fun initActionBar() {
        title = "用户登陆"
        setLeftText("取消") {
            finish()
        }

        setRightText("注册") {
            showToast("注册")
        }
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
        /**
         * 登陆
         */
        btn_login.setOnClickListener {
            val username = et_username.text
            val password = tv_password.text
            if (username.isNullOrBlank()) {
                showToast("用户名不能为空")
                return@setOnClickListener
            } else if (password.isNullOrBlank()) {
                showToast("密码不能为空")
                return@setOnClickListener
            }
            login(username.toString(),password.toString())
        }
    }

    /**
     * 登陆
     */
    private fun login(name: String, password: String) {
        showWaitDialog("登录中...")
        post(Urls.login, hashMapOf(
                "name" to name,
                "password" to password),
                object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.e("Exception", "Exception")
                        showToast("登陆失败")
                        hideWaitDialog()
                    }

                    override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                        hideWaitDialog()
                        if (response.success()) {
                            EtuApplication.getInstance().userInfo = response.data
                            EventBus.getDefault().post(LoginSuccessEvent(response.data))
                            finish()
                        } else {
                            showToast("登陆失败")
                        }
                    }
                })
    }
}
