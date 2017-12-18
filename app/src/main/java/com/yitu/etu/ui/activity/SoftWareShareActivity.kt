package com.yitu.etu.ui.activity

import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.yitu.etu.R

class SoftWareShareActivity : BaseActivity() {


    override fun getLayout(): Int = R.layout.activity_soft_ware_share
    override fun initActionBar() {
        title = "我的分享"
        setRightText("分享") {
            ShareAction(this)
                    .withText("hello")
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                    .setCallback(object : UMShareListener {
                        override fun onResult(p0: SHARE_MEDIA?) {
                            showToast("分享成功")
                        }

                        override fun onCancel(p0: SHARE_MEDIA?) {
                            showToast("取消分享")
                        }

                        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                            showToast(p1?.message)
                        }

                        override fun onStart(p0: SHARE_MEDIA?) {
                            showToast("开始分享")
                        }

                    })
                    .open()
        }
    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
    }


}
