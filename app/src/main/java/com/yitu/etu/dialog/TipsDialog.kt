package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import com.yitu.etu.R
import kotlinx.android.synthetic.main.tips_dialog.*

/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class TipsDialog : Dialog {

    constructor(context: Context, title: String) : super(context, R.style.LoadingDialog) {
        setContentView(R.layout.tips_dialog)
        tv_title_bg.text = title
        btn_cancel.setOnClickListener {
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
    fun setMessage(content:String){
        tv_message.text=content
    }

    fun setRightBtn(content:String,onClick:(v:View)->Unit){
        btn_yes.text = content
        btn_yes.setOnClickListener {
            onClick(it)
            dismiss()
        }
    }

    fun setLeftBtn(content:String,onClick:(v:View)->Unit){
        btn_yes.text = content
        btn_yes.setOnClickListener {
            onClick(it)
            dismiss()
        }
    }
}