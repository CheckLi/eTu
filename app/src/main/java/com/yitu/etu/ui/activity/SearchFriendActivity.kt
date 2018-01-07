package com.yitu.etu.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.*
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.activity_search_friend.*
import okhttp3.Call
import java.lang.Exception

class SearchFriendActivity : BaseActivity() {
    private var userinfo: UserInfo? = null
    private var isAddGroup = false
    private var type = 0
    override fun getLayout(): Int = R.layout.activity_search_friend

    override fun initActionBar() {
        title = "社交"
    }

    override fun initView() {
        isAddGroup = intent.getBooleanExtra("addGroup", false)
        if (isAddGroup) {
            tv_start_chat.text = "添加"
        }
        if (!PermissionUtil.hasPermissions(this, Manifest.permission.CAMERA)) {
            PermissionUtil.requestPermissions(this, 100, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (!PermissionUtil.checkHaveAllPermissions(grantResults)) {
            finish()
        }
    }

    override fun getData() {

    }

    override fun initListener() {
        iv_search.setOnClickListener {
            if (et_search_keyword.text.isNullOrBlank()) {
                showToast("请输入昵称或手机号")
            } else {
                search()
            }
        }

        tv_start_chat.setOnClickListener {
            when {
                userinfo == null -> showToast("请先查询")
                isAddGroup -> {
                    val intent = Intent()
                    intent.putExtra("userinfo", userinfo)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                userinfo?.id == userInfo().id -> showToast("不能和自己聊天")
                else -> RongIM.getInstance().startPrivateChat(this@SearchFriendActivity, userinfo?.id.toString(), userinfo?.name.Empty())
            }
        }

        iv_two_code.setOnClickListener {
            nextActivity<CaptureActivity>(10001)
        }
    }

    fun search() {
        showWaitDialog("搜索中...")
        post(Urls.URL_SEARCH_USER, hashMapOf("type" to type.toString(), "name" to et_search_keyword.text.toString()), object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("用户不存在")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                hideWaitDialog()
                if (response.success() && response.data != null) {
                    response.data?.run {
                        this@SearchFriendActivity.type = 0
                        userinfo = this
                        rl_chat_search.visibility = View.VISIBLE
                        ImageLoadUtil.getInstance().loadImage(iv_head, header.addHost(), R.drawable.default_head, 60, 60)
                        tv_username.text = name
                        tv_desc.text = intro
                    }
                } else {
                    userinfo = null
                    rl_chat_search.visibility = View.GONE
                    showToast(response.message)
                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
                type = 1
                var content = data?.getStringExtra("content")
                if (!TextUtils.isEmpty(content) && content.indexOf("etuXs72hfjepIdnv76") >= 0) {
                    content = content.replace(Regex("etuXs72hfjepIdnv76://ID"), "")
                    et_search_keyword.setText(content)
                    et_search_keyword.setSelection(et_search_keyword.text.length)
                    iv_search.performClick()
                } else {
                    showToast("无法识别用户信息")
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
