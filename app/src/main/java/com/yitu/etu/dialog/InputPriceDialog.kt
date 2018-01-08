package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.tools.InputFilterHelper
import com.yitu.etu.ui.activity.RegistXyActivity
import com.yitu.etu.util.showToast
import kotlinx.android.synthetic.main.input_price_dialog.*

/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class InputPriceDialog : Dialog {

    constructor(context: Context, title: String) : super(context, R.style.LoadingDialog) {
        setContentView(R.layout.input_price_dialog)
        tv_title_bg.text = title
        btn_cancel.setOnClickListener {
            dismiss()
        }
        tv_buy_xy.setOnClickListener {
            context.nextActivity<RegistXyActivity>("type" to "2")
        }
        tv_input_price.filters = arrayOf(InputFilterHelper.getNumberInputFilter(10))
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


    fun setRightBtn(content: String, onClick: (v: View) -> Unit) {
        btn_yes.text = content
        btn_yes.setOnClickListener {
            if (!tv_input_price.text.isNullOrBlank()) {
                onClick(it)
            } else {
                context.showToast("请输入金额")
            }
        }
    }

    fun setRightBtn(content: String, tips: String, onClick: (price: Float) -> Unit) {
        btn_yes.text = content
        btn_yes.setOnClickListener {
            if (!tv_input_price.text.isNullOrBlank()||tips.isNullOrBlank()) {
                if(tips.isNullOrBlank()){
                    onClick(0f)
                }else{
                    onClick(tv_input_price.text.toString().toFloat())
                }

            } else {
                context.showToast(tips)
            }
        }
    }

    fun setRightBtnResultText(content: String, tips: String, onClick: (content: String) -> Unit) {
        btn_yes.text = content
        btn_yes.setOnClickListener {
            if (!tv_input_price.text.isNullOrBlank()) {
                onClick(tv_input_price.text.toString())
            } else {
                context.showToast(tips)
            }
        }
    }

    fun setLeftBtnResultText(content: String, tips: String, onClick: (content: String) -> Unit) {
        btn_cancel.text = content
        btn_cancel.setOnClickListener {
            if (!tv_input_price.text.isNullOrBlank()||tips.isNullOrBlank()) {
                onClick(tv_input_price.text.toString())
                dismiss()
            } else {
                context.showToast(tips)
            }
        }
    }

    fun setLeftBtn(content: String, onClick: (v: View) -> Unit) {
        btn_yes.text = content
        btn_yes.setOnClickListener {
            it.tag=tv_input_price.text.toString()
            onClick(it)
            dismiss()
        }
    }

    fun setHint(content: String, showXy: Boolean) {
        tv_input_price.hint = content
        tv_input_price.inputType = EditorInfo.TYPE_CLASS_NUMBER
        if (!showXy) {
            tv_buy_xy.visibility = View.GONE
        } else {
            tv_buy_xy.visibility = View.VISIBLE
        }

    }

    fun setHint(content: String, showXy: Boolean, inputType: Int) {
        tv_input_price.hint = content
        tv_input_price.inputType = inputType
        if (!showXy) {
            tv_buy_xy.visibility = View.GONE
        } else {
            tv_buy_xy.visibility = View.VISIBLE
        }

    }

    fun setHint(content: String, showXy: Boolean, gravity: Int, inputType: Int): TextView {
        tv_input_price.hint = content
        tv_input_price.inputType = inputType

        if (!showXy) {
            tv_buy_xy.visibility = View.GONE
        } else {
            tv_buy_xy.visibility = View.VISIBLE
            val params = tv_buy_xy.layoutParams as LinearLayout.LayoutParams
            params.gravity = gravity
            tv_buy_xy.layoutParams = params
        }
        return tv_buy_xy
    }


    fun setXieYiClickNull(){
        tv_buy_xy.setOnClickListener(null)
    }
}