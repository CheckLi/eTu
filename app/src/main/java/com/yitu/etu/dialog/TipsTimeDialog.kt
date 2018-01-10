package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.yitu.etu.R
import kotlinx.android.synthetic.main.time_tips_dialog.*

/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class TipsTimeDialog : Dialog {

    constructor(context: Context) : super(context, R.style.LoadingDialog2) {
        setContentView(R.layout.time_tips_dialog)

        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener) {}

    fun setCount(count:Int){
        tv_message.setSpanText("获得平安符：%+ %%1%")
    }

    fun hideDialog() {
        dismiss()
    }

    fun showDialog() {
        show()
    }
}