package com.yitu.etu.ui.activity

import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.post
import kotlinx.android.synthetic.main.activity_my_wallet.*
import okhttp3.Call
import java.lang.Exception

/**
 * 我的钱包
 */
class MyWalletActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_my_wallet

    override fun initActionBar() {
        title = "我的钱包"
        setRightText("记录"){
            showToast("钱包记录")
        }
    }

    override fun initView() {
    }

    override fun getData() {
        showWaitDialog("获取中...")
        post(Urls.URL_GET_USER_INFO, hashMapOf(), object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
            override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                hideWaitDialog()
                if(response.success()){
                    with(response.data){
                        tv_wallet.text=balance
                        tv_safe_count.setSpanText("平安福数量\n%${safecount}个%")
                    }
                }else{
                    showToast(response.message)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("订单获取失败")
            }

        })
    }

    override fun initListener() {
    }


}
