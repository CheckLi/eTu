package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.huizhuang.zxsq.utils.nextActivity
import com.umeng.socialize.UMShareAPI
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.entity.AppConstant
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http.post
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.LoginUtil
import com.yitu.etu.util.PrefrersUtil
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call

class LoginActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_login

    override fun initActionBar() {
        title = "用户登录"
        setLeftText("取消") {
            finish()
        }

        setRightText("注册") {
            nextActivity<RegistActivity>(1001)
        }
    }

    override fun initView() {
        et_username.setText(PrefrersUtil.getInstance().getValue(AppConstant.PARAM_SAVE_USERNAME, ""))
        et_username.setSelection(et_username.text.length)
        if (!et_username.text.isNullOrEmpty()) {
            tv_password.requestFocus()
        }
    }

    override fun getData() {
    }

    override fun initListener() {
        /**
         * 登录
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
            login(username.toString(), password.toString())
        }

        /**
         * 忘记密码
         */
        tv_forget_password.setOnClickListener {
            nextActivity<ForgetPasswordActivity>(1001)
        }
        /**
         * 微信登录
         */
        iv_login_weixin.setOnClickListener {
            LoginUtil.getInstance(this@LoginActivity).startLogin(LoginUtil.TYPE_LOGIN_WEIXIN)
        }

        /**
         * 微博d登录
         */
        iv_login_weibo.setOnClickListener {
            LoginUtil.getInstance(this@LoginActivity).startLogin(LoginUtil.TYPE_LOGIN_WEIBO)
        }
    }

    /**
     * 登录
     */
    private fun login(name: String, password: String) {
        showWaitDialog("登录中...")
        post(Urls.login, hashMapOf(
                "name" to name,
                "password" to password),
                object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.e("Exception", "Exception")
                        showToast("登录失败")
                        hideWaitDialog()
                    }

                    override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                        hideWaitDialog()
                        if (response.success()) {
                            PrefrersUtil.getInstance().saveValue(AppConstant.PARAM_SAVE_USERNAME, name)
                            EtuApplication.getInstance().userInfo = response.data
                            EtuApplication.getInstance().connectChat()//登录聊天服务器
                            finish()
                        } else {
                            showToast(response.message)
                        }
                    }
                })
    }

    /**
     * 注册完成后需要模拟点击登录
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == 1001) {
            val username = data.getStringExtra("username")
            val password = data.getStringExtra("password")
            et_username.setText(username)
            tv_password.setText(password)
            btn_login.performClick()
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        LoginUtil.getInstance(this).onDestory()
        super.onDestroy()
    }
}
