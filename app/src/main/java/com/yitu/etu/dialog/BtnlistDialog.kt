package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import com.yitu.etu.R
import kotlinx.android.synthetic.main.btn_dialog.*

/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class BtnlistDialog : Dialog {

    constructor(context: Context) : super(context, R.style.LoadingDialog) {
        setContentView(R.layout.btn_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener) {}

    fun hideDialog() {
        dismiss()
    }

    fun showDialog() {
        show()
    }


    fun setOneBtn(content:String,onClick:(v:View)->Unit):BtnlistDialog{
        tv_chang_name.text = content
        tv_chang_name.setOnClickListener {
            onClick(it)
            dismiss()
        }
        return this
    }

    fun setTwoBtn(content:String,onClick:(v:View)->Unit):BtnlistDialog{
        tv_start_chat.text = content
        tv_start_chat.setOnClickListener {
            onClick(it)
            dismiss()
        }
        return this
    }
}