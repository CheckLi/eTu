package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

import com.yitu.etu.R
import com.yitu.etu.util.checkVisible
import kotlinx.android.synthetic.main.loading_dialog.*

/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class LoadingDialog : Dialog {

    constructor(context: Context, message: String) : super(context, R.style.LoadingDialog) {
        setContentView(R.layout.loading_dialog)
        tv_title.text = message + "..."
        tv_title.checkVisible(message)
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

    fun setMessage(message: String) {
        tv_title.text = message + "..."
        tv_title.checkVisible(message)
    }
}
