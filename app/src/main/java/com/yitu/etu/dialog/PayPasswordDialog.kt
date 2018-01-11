package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.jungly.gridpasswordview.GridPasswordView
import com.jungly.gridpasswordview.imebugfixer.ImeDelBugFixedEditText
import com.yitu.etu.R
import kotlinx.android.synthetic.main.pay_password_dialog.*
import java.util.*


/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class PayPasswordDialog : Dialog {
    var mInputView: ImeDelBugFixedEditText? = null

    constructor(context: Context, title: String) : super(context, R.style.LoadingDialog) {
        setContentView(R.layout.pay_password_dialog)
        tv_title_small.text = title
        mInputView = window.decorView.findViewById(R.id.inputView) as ImeDelBugFixedEditText
        iv_close.setOnClickListener {
            dismiss()
        }

        setOnShowListener {
            forceInputViewGetFocus()
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
        tv_price.visibility = View.VISIBLE
        tv_price.text = "￥$price"
    }

    fun clearPassword() {
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

    override fun show() {
        super.show()
    }

    private fun forceInputViewGetFocus() {
        mInputView?.isFocusable = true
        mInputView?.isFocusableInTouchMode = true
        mInputView?.requestFocus()
        KeyBoard(mInputView!!, "open")
        /* val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
         imm.showSoftInput(mInputView, InputMethodManager.SHOW_FORCED)*/
    }

    //强制显示或者关闭系统键盘
    fun KeyBoard(txtSearchKey: EditText, status: String) {

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val m = txtSearchKey.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (status == "open") {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED)
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.windowToken, 0)
                }
            }
        }, 300)
    }
}