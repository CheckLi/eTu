package com.yitu.etu.ui.activity

import android.os.Handler
import android.util.Log
import com.jungly.gridpasswordview.GridPasswordView
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.Empty
import com.yitu.etu.util.userInfo
import kotlinx.android.synthetic.main.activity_re_pay_password.*
import okhttp3.Call

class RePayPasswordActivity : BaseActivity() {
    private val timeTotal = 60//s
    private var time = timeTotal//s
    private val timeDelaed = 1000L//ms
    private var hand: Handler? = null
    val WHAT_SEND = 0//发送中
    val WHAT_SEND_ERROR = 1//发送错误
    val WHAT_SEND_FINISH = 2//发送完成
    val WHAT_SEND_RESET = 3//发送完成
    private var password = ""
    override fun getLayout(): Int = R.layout.activity_re_pay_password

    override fun initActionBar() {
        title = "重设支付密码"
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
         * 重置支付密码
         */
        btn_re_password.setOnClickListener {
            reSet()
        }

        grid_password.setOnPasswordChangedListener(object : GridPasswordView.OnPasswordChangedListener {
            override fun onInputFinish(psw: String?) {
            }

            override fun onTextChanged(psw: String?) {
                password = psw.Empty()
            }

        })
    }

    /**
     * 发送验证码
     */
    private fun sendCode() {
        val phone = userInfo().phone.Empty()
        if (phone.isNullOrBlank()) {
            showToast("电话号码不能为空")
            return
        }
        showWaitDialog("发送中...")
        Http.post(Urls.paySms, hashMapOf("phone" to phone), object : GsonCallback<ObjectBaseEntity<Any>>() {
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
     * 开始重置密码
     */
    private fun reSet() {
        val code = et_code.text.toString()
        if (code.isNullOrBlank()) {
            showToast("验证码不能为空")
            return
        }
        if (password.isNullOrBlank()) {
            showToast("支付密码不能为空")
            return
        } else if (password.length < 6) {
            showToast("密码输入不完整")
            return
        }

        showWaitDialog("设置中...")
        Http.post(Urls.reSetPayPassword, hashMapOf(
                "code" to code,
                "paypwd" to password
        ), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call, e: Exception, id: Int) {
                Log.e("Exception", e.message)
                showToast("支付密码设置失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    showToast("设置成功")
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
