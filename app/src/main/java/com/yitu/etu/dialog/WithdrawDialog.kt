package com.yitu.etu.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.view.View
import com.yitu.etu.R
import com.yitu.etu.tools.InputFilterHelper
import com.yitu.etu.util.showToast
import kotlinx.android.synthetic.main.withdraw_dialog.*

/**
 * @className:LoadingDialog
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:53
 */
class WithdrawDialog : Dialog {
    private var type=1//1支付宝，2微信，3银行卡
    constructor(context: Context, title: String) : super(context, R.style.LoadingDialog) {
        setContentView(R.layout.withdraw_dialog)
        tv_title_bg.text = title
        btn_cancel.setOnClickListener {
            dismiss()
        }
        et_user_price.filters = arrayOf(InputFilterHelper.getNumberInputFilter(20))
        et_user_card.filters = arrayOf(InputFilterHelper.IdCardInput())
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        rg_select_pay.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_alipay->{
                    type=1
                    et_user_account.inputType=InputType.TYPE_CLASS_NUMBER
                    et_user_account.setText("")
                    et_user_account.hint = "请输入到账支付宝账号"
                    et_user_card.visibility=View.GONE
                    et_user_name.visibility=View.GONE
                }
                R.id.rb_weixin->{
                    type=2
                    et_user_account.inputType=InputType.TYPE_CLASS_TEXT
                    et_user_account.setText("")
                    et_user_account.hint = "请输入到账微信账号"
                    et_user_card.visibility=View.GONE
                    et_user_name.visibility=View.GONE
                }
                R.id.rb_bank_card->{
                    type=3
                    et_user_account.inputType=InputType.TYPE_CLASS_NUMBER
                    et_user_account.setText("")
                    et_user_account.hint = "请输入到账银行卡账号"
                    et_user_card.visibility=View.VISIBLE
                    et_user_name.visibility=View.VISIBLE
                }

            }
        }
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener) {}

    fun hideDialog() {
        dismiss()
    }

    fun showDialog() {
        show()
    }

    fun check():Boolean{
        val userAccount=et_user_account.text.toString()
        val userCard=et_user_card.text.toString()
        val userName=et_user_name.text.toString()
        val userPrice=et_user_price.text.toString()
        return when(type){
            1->{
                when {
                    userAccount.isNullOrBlank() -> {

                        context.showToast("请输入支付宝账号")
                        false
                    }
                    userPrice.isNullOrBlank() -> {
                        context.showToast("请输入提现金额")
                        false
                    }
                    else -> true
                }
            }
            2->{
                when {
                    userAccount.isNullOrBlank() -> {

                        context.showToast("请输入微信账号")
                        false
                    }
                    userPrice.isNullOrBlank() -> {
                        context.showToast("请输入提现金额")
                        false
                    }
                    else -> true
                }
            }
            3->{
                when {
                    userAccount.isNullOrBlank() -> {
                        context.showToast("请输入银行账号")
                        false
                    }
                    userCard.isNullOrBlank() -> {
                        context.showToast("请输入身份证号")
                        false
                    }
                    userName.isNullOrBlank() -> {
                        context.showToast("请输入持卡人姓名")
                        false
                    }
                    userPrice.isNullOrBlank() -> {
                        context.showToast("请输入提现金额")
                        false
                    }
                    else -> true
                }
            }
            else -> false
        }
    }

    fun setRightBtn(content: String, onClick: (v: View) -> Unit) {
        btn_yes.text = content
        btn_yes.setOnClickListener {
            if (check()) {
                onClick(it)
            }
        }
    }

    fun setLeftBtn(content: String, onClick: (v: View) -> Unit) {
        btn_yes.text = content
        btn_yes.setOnClickListener {
            onClick(it)
            dismiss()
        }
    }

    fun getType():Int{
        return type
    }

    fun getContent(pos:Int):String{
        val userAccount=et_user_account.text.toString()
        val userCard=et_user_card.text.toString()
        val userName=et_user_name.text.toString()
        val userPrice=et_user_price.text.toString()
        return when(pos){
            0->userAccount
            1->userCard
            2->userName
            3->userPrice
            else ->""
        }
    }
}