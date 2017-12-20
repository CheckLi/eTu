package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http.post
import com.yitu.etu.tools.Urls
import kotlinx.android.synthetic.main.activity_foreget_password.*
import okhttp3.Call

class ForegetPasswordActivity : BaseActivity() {
    private val timeTotal = 60//s
    private var time = timeTotal//s
    private val timeDelaed = 1000L//ms
    private var hand: Handler? = null
    val WHAT_SEND = 0//发送中
    val WHAT_SEND_ERROR = 1//发送错误
    val WHAT_SEND_FINISH = 2//发送完成
    val WHAT_SEND_RESET = 3//发送完成
    override fun getLayout(): Int = R.layout.activity_foreget_password

    override fun initActionBar() {
        title = "找回密码"
        setLeftText("取消") {
            finish()
        }
    }

    override fun initView() {
        initHand()
    }

    override fun getData() {
    }

    override fun initListener() {
        /**
         * 发送验证码
         */
        tv_send.setOnClickListener {
            sendCode()
        }
        /**
         * 注册
         */
        btn_ok.setOnClickListener {
            save()
        }
    }

    /**
     * 发送验证码
     */
    private fun sendCode() {
        val phone = et_username.text.toString()
        if (phone.isNullOrBlank()) {
            showToast("电话号码不能为空")
            return
        }
        showWaitDialog("发送中...")
        post(Urls.sendCode, hashMapOf("phone" to phone), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call, e: Exception, id: Int) {
                Log.e("Exception", e.message)
                showToast("获取失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    hand?.sendEmptyMessage(WHAT_SEND)
                } else {
                    showToast(response.message)
                }
            }
        })
    }

    /**
     * 开始注册
     */
    private fun save() {
        val code = et_code.text.toString()
        val phone = et_username.text.toString()
        val password = tv_password.text.toString()
        if (code.isNullOrBlank()) {
            showToast("验证码不能为空")
            return
        }
        if (phone.isNullOrBlank()) {
            showToast("电话号码不能为空")
            return
        }
        if (password.isNullOrBlank()) {
            showToast("密码不能为空")
            return
        }

        showWaitDialog("发送中...")
        post(Urls.foregetPassword, hashMapOf(
                "phone" to phone,
                "code" to code,
                "password" to password
        ), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call, e: Exception, id: Int) {
                Log.e("Exception", e.message)
                showToast("注册失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    val intent=Intent()
                    intent.putExtra("username",phone)
                    intent.putExtra("password",password)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                } else {
                    showToast(response.message)
                }
            }
        })
    }

    /**
     * 定时
     */
    private var mRunnable: Runnable = Runnable {
        if (time == 0) {
            hand?.sendEmptyMessage(WHAT_SEND_RESET)
        } else {
            hand?.sendEmptyMessage(WHAT_SEND)
        }
    }

    fun initHand() {
        hand = Handler { msg ->
            when (msg.what) {
                WHAT_SEND -> {
                    tv_send.isEnabled = false
                    tv_send.text = "${time--}s"
                    hand?.postDelayed(mRunnable, timeDelaed)
                }//发送中
                WHAT_SEND_ERROR -> {
                    time = timeTotal
                    tv_send.isEnabled = true
                    tv_send.text = "重新发送"
                }//错误
                WHAT_SEND_FINISH -> {
                    time = timeTotal
                    tv_send.isEnabled = true
                    tv_send.text = "重新发送"
                }//完成
                WHAT_SEND_RESET -> {
                    time = timeTotal
                    tv_send.isEnabled = true
                    tv_send.text = "重新发送"
                }//重置

            }
            false
        }
    }

    override fun onDestroy() {
        hand?.removeCallbacks(mRunnable)
        super.onDestroy()
    }
}

