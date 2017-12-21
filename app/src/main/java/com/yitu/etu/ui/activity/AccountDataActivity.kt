package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.entity.AppConstant
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.*
import com.yitu.etu.util.imageLoad.ImageLoadGlideUtil
import kotlinx.android.synthetic.main.activity_account_data.*
import okhttp3.Call
import java.util.*

class AccountDataActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_account_data

    override fun initActionBar() {
        title = "资料修改"
    }

    override fun initView() {
        if (userInfo() != null) {
            with(userInfo()) {
                ImageLoadGlideUtil.getInstance().loadImage(iv_head, header.addHost(), 80, 80)
                tv_username.text = name.Empty()
                rg_sex.check(when (sex) {
                    0 -> R.id.rb_sex_girl
                    else -> R.id.rb_sex_boy
                })
                tv_intro.text = intro.Empty()
                rg_friend.check(when (sex) {
                    0 -> R.id.rg_friend_yes
                    else -> R.id.rg_friend_no
                })
            }
        }
    }

    override fun getData() {
    }

    override fun initListener() {
        /**
         * 头像修改
         */
        rl_head.setOnClickListener {
            Single()//打开单选图片
        }
        /**
         * 昵称修改
         */
        ll_nick_name.setOnClickListener {
            nextActivity<EditInputActivity>(1001,AppConstant.PARAM_TITLE to "昵称修改",AppConstant.PARAM_RESULT_STRING to userInfo().name)
        }
        /**
         * 说明修改
         */
        ll_intro.setOnClickListener {
            nextActivity<EditInputActivity>(1002,AppConstant.PARAM_TITLE to "说明设置",AppConstant.PARAM_RESULT_STRING to userInfo().intro)
        }
    }

    override fun selectSuccess(path: String?) {
        path?.run {
            postFile(this)
        }
    }

    override fun selectSuccess(pathList: MutableList<String>?) {
        pathList?.forEach {
            postFile(it)
        }
    }
    private var postFileBase64=""
    private var postFilePath=""
    fun postFile(path: String?) {
        ImageLoadGlideUtil.getInstance().loadImage(iv_head, "file://${path.Empty()}", 80, 80)
        postFileBase64=FileUtil.GetImageStr(path)
        postFilePath=path.Empty()
    }

    /**
     * 上传个人资料信息
     */
    private fun updateUserInfo() {

        val params = HashMap<String, String>()
        if(tv_username.text.toString()!=userInfo().name) {
            params.put("name", tv_username.text.toString())
        }
        if(tv_intro.text.toString()!=userInfo().intro) {
            params.put("intro", tv_intro.text.toString())
        }
        if(rg_sex.checkedRadioButtonId==R.id.rb_sex_girl&&userInfo().sex==1){
            params.put("sex", "0")
        }else if(rg_sex.checkedRadioButtonId==R.id.rb_sex_boy&&userInfo().sex==0){
            params.put("sex", "1")
        }
        if(!postFileBase64.isNullOrBlank()){
            params.put("header",postFileBase64)
        }
        if(params.isEmpty()){
            finish()
            return
        }
        showWaitDialog("资料上传中...")
        post(Urls.updateUserInfo, params, object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
            override fun onError(call: Call, e: Exception, id: Int) {
                showToast("资料存储失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                hideWaitDialog()
                if (response.success()) {
                    with(response.data){
                        if(!header.isNullOrBlank()) {
                            EtuApplication.getInstance().userInfo.header=header
                        }
                        if(!name.isNullOrBlank()) {
                            EtuApplication.getInstance().userInfo.name=name
                        }
                        if(!intro.isNullOrBlank()) {
                            EtuApplication.getInstance().userInfo.intro=intro
                        }
                        if(sex!=EtuApplication.getInstance().userInfo.sex) {
                            EtuApplication.getInstance().userInfo.sex=sex
                        }
                        EtuApplication.getInstance().userInfo=EtuApplication.getInstance().userInfo
                    }
                    finish()
                }else{
                    showToast(response.message)
                }
            }
        })

    }

    override fun onBackPressed() {
       updateUserInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(resultCode== Activity.RESULT_OK){
                when(requestCode){
                    1001->{tv_username.text=data?.getStringExtra(AppConstant.PARAM_RESULT_STRING)?:tv_username.text.toString()}  //修改昵称
                    1002->{tv_intro.text=data?.getStringExtra(AppConstant.PARAM_RESULT_STRING)?:tv_intro.text.toString()}      //修改intro

                }
            }
        }
    }
}
