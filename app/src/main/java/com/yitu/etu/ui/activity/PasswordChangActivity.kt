package com.yitu.etu.ui.activity

import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_password_chang.*
import okhttp3.Call
import java.lang.Exception

class PasswordChangActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_password_chang

    override fun initActionBar() {
       title="密码修改"
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
        btn_confim.setOnClickListener {
            putData()
        }
    }

    fun putData(){
        if(et_old_password.text.isNullOrBlank()){
            showToast("原密码不能为空")
            return
        }
        if(et_new_password.text.isNullOrBlank()){
            showToast("新密码不能为空")
            return
        }
        if(et_password_ok.text.isNullOrBlank()){
            showToast("确认密码不能为空")
            return
        }
        if(et_password_ok.text.toString()!=et_new_password.text.toString()){
            showToast("确认密码不一样")
            return
        }
        showWaitDialog("修改中...")
        post(Urls.loginPasswordChange, hashMapOf(
                "oldpwd" to et_old_password.text.toString(),
                "newpwd" to et_new_password.text.toString(),
                "repwd" to et_password_ok.text.toString()

        ), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("密码修改失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if(response?.success()) {
                    showToast("密码修改成功")
                    EtuApplication.getInstance().loginOut()
                    nextActivity<LoginActivity>(true)
                }else{
                    showToast(response.message)
                }
            }

        })
    }
}
