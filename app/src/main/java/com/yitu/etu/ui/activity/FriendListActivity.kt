package com.yitu.etu.ui.activity

import android.content.Intent
import android.view.inputmethod.EditorInfo
import com.yitu.etu.R
import com.yitu.etu.dialog.BtnlistDialog
import com.yitu.etu.dialog.InputPriceDialog
import com.yitu.etu.entity.ArrayBaseEntity
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.FriendListAdapter
import com.yitu.etu.util.Empty
import com.yitu.etu.util.Tools
import com.yitu.etu.util.post
import com.yitu.etu.util.userInfo
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.activity_friend_list.*
import okhttp3.Call
import java.lang.Exception

class FriendListActivity : BaseActivity() {
    lateinit var adapter: FriendListAdapter
    lateinit var dialog: InputPriceDialog
    var id: String? = null
    var select: Boolean = false
    override fun getLayout(): Int = R.layout.activity_friend_list

    override fun getIntentExtra(intent: Intent?) {
        select = intent?.getBooleanExtra("select", false) ?: false
    }

    override fun initActionBar() {
        title = "好友列表"
        if (select) {
            setRightText("发起群聊") {
                val list = adapter.getIds()
                if (list.size > 0) {
                    Tools.startChatGroup(this@FriendListActivity, list, userInfo().name+"的群聊")
                    finish()
                } else {
                    showToast("请选择用户")
                }
            }
        }
    }

    override fun initView() {
        dialog = InputPriceDialog(this@FriendListActivity, "修改备注")
        dialog.setHint("请输入备注名", false, EditorInfo.TYPE_CLASS_TEXT)
        adapter = FriendListAdapter(mutableListOf(), select)
        listview.adapter = adapter
    }

    override fun getData() {
        refresh(true)
    }

    override fun initListener() {
        layout_refresh.setOnRefreshListener {
            refresh(true)
        }
        layout_refresh.setOnLoadmoreListener {
            refresh(false)
        }
        listview.setOnItemClickListener { parent, view, position, id ->
            //            type字段表示，0文章，1为文章，2为景点，3为出行活动，5为美食店铺，6为住宿店铺，7为游玩店铺
            BtnlistDialog(this@FriendListActivity)
                    .setOneBtn("修改备注") {
                        dialog.showDialog()
                        this@FriendListActivity.id = adapter.getItem(position).id.toString()
                    }
                    .setTwoBtn("发起聊天") {
                        RongIM.getInstance().startPrivateChat(this@FriendListActivity, adapter.getItem(position)?.id.toString(), adapter.getItem(position)?.name.Empty())
                    }.showDialog()
        }

        /**
         * 修改备注名
         */
        dialog.setRightBtnResultText("确认", "请输入备注名") {
            changBeizhu(it)
            dialog.dismiss()
        }
    }

    fun refresh(isRefresh: Boolean) {
        if (isRefresh) {
            showWaitDialog("获取中...")
            RefreshSuccessInit(layout_refresh, isRefresh)
        }

        post(Urls.URL_FRIEND_LIST, hashMapOf("page" to page.toString()), object : GsonCallback<ArrayBaseEntity<UserInfo>>() {
            override fun onResponse(response: ArrayBaseEntity<UserInfo>, id: Int) {
                hideWaitDialog()

                if (response.success() && response.data.size > 0) {
                    if (isRefresh) {
                        adapter.replaceAll(response.data)
                    } else {
                        adapter.addAll(response.data)
                    }
                    RefreshSuccess(layout_refresh, isRefresh, response.data.size)
                } else if (!response.success()) {
                    RefreshSuccess(layout_refresh, isRefresh, 0)
                    showToast(response.message)
                } else {
                    RefreshSuccess(layout_refresh, isRefresh, 0)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                RefreshSuccessInit(layout_refresh, isRefresh)
                showToast("好友获取失败")
            }

        })
    }

    /**
     * 修改备注
     */
    fun changBeizhu(name: String) {
        showWaitDialog("修改中...")
        post(Urls.URL_CHANG_BEIZHU, hashMapOf("name" to name, "user_id" to id.Empty()), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("修改失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id: Int) {
                hideWaitDialog()
                if (response.success() && response.data != null) {
                    response.data?.run {
                        showToast("修改成功")
                        refresh(true)
                    }
                    this@FriendListActivity.id = null
                } else {
                    showToast(response.message)
                }
            }

        })
    }
}
