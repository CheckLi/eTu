package com.yitu.etu.ui.activity

import android.Manifest
import android.os.Build
import android.support.v4.app.ActivityCompat
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.yitu.etu.R


class SoftWareShareActivity : BaseActivity() {


    override fun getLayout(): Int = R.layout.activity_soft_ware_share
    override fun initActionBar() {
        title = "我的分享"
        setRightText("分享") {
            //先请求权限
            if (requestPermission()) {
                share()
            }
        }
    }

    private fun share() {

        val web=UMWeb("http://91eto.com")
        web.title="e途旅行平台"
        web.description="e途,一直等着你的旅行平台"
        web.setThumb(UMImage(this@SoftWareShareActivity,R.mipmap.ic_launcher))
        ShareAction(this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(object : UMShareListener {
                    override fun onResult(p0: SHARE_MEDIA?) {
                        showToast("分享成功")
                    }

                    override fun onCancel(p0: SHARE_MEDIA?) {
                        showToast("取消分享")
                    }

                    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                        when (p0) {
                            SHARE_MEDIA.WEIXIN -> {
                                if (!UMShareAPI.get(this@SoftWareShareActivity).isInstall(this@SoftWareShareActivity, SHARE_MEDIA.WEIXIN)) {
                                    showToast("请安装微信")
                                    return
                                }
                            }
                            SHARE_MEDIA.QQ -> {
                                if (!UMShareAPI.get(this@SoftWareShareActivity).isInstall(this@SoftWareShareActivity, SHARE_MEDIA.QQ)) {
                                    showToast("请安装QQ")
                                    return
                                }
                            }
                            else -> showToast("分享异常")
                        }
                    }

                    override fun onStart(p0: SHARE_MEDIA?) {
                        showToast("开始分享")

                    }

                })
                .open()
    }

    private fun requestPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val pArray = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS)
            val mPermissionList = pArray
            ActivityCompat.requestPermissions(this, mPermissionList, 123)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 123) {
            share()
        }

    }

    override fun initView() {
    }

    override fun getData() {
    }

    override fun initListener() {
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }
}
