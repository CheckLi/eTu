package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import com.jungly.gridpasswordview.GridPasswordView
import com.yitu.etu.R
import kotlinx.android.synthetic.main.pay_password_dialog.*

/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class PayPasswordDialog : Dialog {

    constructor(context: Context, title: String) : super(context, R.style.LoadingDialog) {
        setContentView(R.layout.pay_password_dialog)
        tv_title_small.text = title
        iv_close.setOnClickListener {
            dismiss()
        }

        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener) {}

    fun hideDialog() {
        dismiss()
    }

    fun showDialog() {
        show()
    }

    /**
     * 设置价格
     */
    fun setContent(price: String) {
        tv_price.visibility=View.VISIBLE
        tv_price.text = "￥$price"
    }

    fun clearPassword(){
        grid_password.clearPassword()
    }

    /**
     * 设置支付密码监听
     */
    fun setOnPasswordChangedListener(onPasswordChang: (psw: String?) -> Unit) {
        grid_password.setOnPasswordChangedListener(object : GridPasswordView.OnPasswordChangedListener {
            override fun onInputFinish(psw: String?) {
                onPasswordChang(psw)
            }

            override fun onTextChanged(psw: String?) {
            }

        })
    }
}