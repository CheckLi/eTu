package com.yitu.etu.ui.activity

import android.view.View
import com.yitu.etu.R
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.util.Empty
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import com.yitu.etu.util.post
import com.yitu.etu.util.userInfo
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.activity_search_friend.*
import okhttp3.Call
import java.lang.Exception

class SearchFriendActivity : BaseActivity() {
    private var userinfo: UserInfo? = null
    override fun getLayout(): Int = R.layout.activity_search_friend

    override fun initActionBar() {
        title = "社交"
    }

    override fun initView() {

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
                userinfo?.id==userInfo().id -> showToast("不能和自己聊天")
                else -> RongIM.getInstance().startPrivateChat(this@SearchFriendActivity, userinfo?.id.toString(), userinfo?.name.Empty())
            }
        }
    }

    fun search() {
        showWaitDialog("搜索中...")
        post(Urls.URL_SEARCH_USER, hashMapOf("type" to "0", "name" to et_search_keyword.text.toString()), object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("用户不存在")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                hideWaitDialog()
                if (response.success() && response.data != null) {
                    response.data?.run {
                        userinfo=this
                        rl_chat_search.visibility = View.VISIBLE
                        ImageLoadUtil.getInstance().loadImage(iv_head, header.addHost(), R.drawable.default_head, 60, 60)
                        tv_username.text = name
                        tv_desc.text = intro
                    }
                } else {
                    userinfo=null
                    rl_chat_search.visibility = View.GONE
                    showToast(response.message)
                }
            }

        })
    }
}
