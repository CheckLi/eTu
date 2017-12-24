package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_about_me.*
import okhttp3.Call
import java.lang.Exception

class RegistXyActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_about_me

    override fun initActionBar() {
      title="注册协议"
    }

    override fun initView() {
    }

    override fun getData() {
        tv_content.text=""
        showWaitDialog("获取中...")
        post(Urls.regist_xy, hashMapOf("type" to intent.getStringExtra("type")), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("获取失败")
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if(response.success()){
                    tv_content.text=response.data.toString()
                }else{
                    showToast(response.message)
                }
            }

        })
    }

    override fun initListener() {
    }


}