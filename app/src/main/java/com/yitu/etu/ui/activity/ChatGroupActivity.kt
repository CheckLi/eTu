package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.TintContextWrapper
import android.view.View
import android.view.ViewGroup
import com.huizhuang.zxsq.utils.nextActivity
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.dialog.TipsDialog
import com.yitu.etu.entity.GroupBean
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http
import com.yitu.etu.tools.MyActivityManager
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.adapter.MyBaseAdapter
import com.yitu.etu.ui.adapter.ViewHolder
import com.yitu.etu.util.addHost
import com.yitu.etu.util.imageLoad.ImageLoadUtil
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import kotlinx.android.synthetic.main.activity_chat_group.*
import kotlinx.android.synthetic.main.adapter_chat_info_head_item.view.*
import okhttp3.Call
import org.jetbrains.anko.imageResource
import java.lang.Exception

class ChatGroupActivity : BaseActivity() {
    var users: String? = null
    var chat_id: String? = null
    var chat_name: String? = null
    var sendId: Int = 0
    var myUserid: Int = 0
    var actionId: Int = 0
    override fun getLayout(): Int = R.layout.activity_chat_group

    override fun getIntentExtra(intent: Intent?) {
        users = intent?.getStringExtra("users")
        chat_id = intent?.getStringExtra("chat_id")
        chat_name = intent?.getStringExtra("title")
        sendId = intent?.getIntExtra("sendId", 0) ?: 0
    }

    override fun initActionBar() {
        title = "群资料"
    }

    override fun initView() {
        tv_name.text = chat_name
    }

    override fun getData() {
        myUserid = EtuApplication.getInstance().userInfo.id
        showWaitDialog("获取中...")
        Http.post(Urls.URL_GET_GROUP_INFO, hashMapOf("users" to users, "chat_id" to chat_id), object : GsonCallback<ObjectBaseEntity<GroupBean>>() {
            override fun onResponse(response: ObjectBaseEntity<GroupBean>, id: Int) {
                hideWaitDialog()
                if (response.success() && response.data.result != null) {
                    with(response.data) {
                        this@ChatGroupActivity.actionId = actionId
                        var user: UserInfo? = null
                        val itor=response.data.result.iterator()
                        while (itor.hasNext()){
                            val value=itor.next() as UserInfo
                            if(value.id==sendId){
                                user=value
                                itor.remove()
                            }
                        }
                        if (user != null) {
                            response.data.result.add(0, user)
                        }
                        if (sendId == myUserid) {
                            val add = UserInfo()
                            add.viewType = 1
                            val cut = UserInfo()
                            cut.viewType = 2
                            response.data.result.add(add)
                            response.data.result.add(cut)
                        }
                        gv_heads.adapter = HeadAdapter(response.data.result)
                    }
                } else {
                    showToast(response.message)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                hideWaitDialog()
                showToast("发送失败")
            }

        })
    }

    override fun initListener() {
        tv_see_action.setOnClickListener {
            nextActivity<SearchResultOrderSceneActivity>("id" to actionId.toString(), "title" to "旅行招募")
        }

        /**
         * 退出并删除
         */
        btn_register.setOnClickListener {
            val dialog=TipsDialog(this@ChatGroupActivity,"温馨提示")
            dialog.setMessage("确认要退出聊天组吗？")
            dialog.setRightBtn("确认"){
                RongIM.getInstance().quitDiscussion(chat_id, object : RongIMClient.OperationCallback() {
                    override fun onSuccess() {
                        showToast("退出成功")
                        dialog.dismiss()
                        MyActivityManager.getInstance().finishActivity(ChatActivity::class.java)
                        finish()
                    }

                    override fun onError(p0: RongIMClient.ErrorCode?) {
                        showToast("退出失败")
                    }

                })
            }
            dialog.show()
        }
    }

    class HeadAdapter(var list: MutableList<UserInfo>) : MyBaseAdapter<UserInfo>(list) {
        override fun getItemResource(pos: Int): Int = R.layout.adapter_chat_info_head_item

        override fun getItemView(position: Int, convertView: View, holder: ViewHolder, parent: ViewGroup): View {
            convertView?.run {
                getItem(position)?.run {

                    when (viewType) {
                        1 -> {
                            iv_del.visibility = View.GONE
                            tv_name111.visibility = View.GONE
                            iv_head.imageResource = R.drawable.icon18
                        }
                        2 -> {
                            iv_del.visibility = View.GONE
                            tv_name111.visibility = View.GONE
                            iv_head.imageResource = R.drawable.icon99
                        }
                        else -> {
                            iv_del.visibility = if (isCheck) View.VISIBLE else View.GONE
                            tv_name111.visibility = View.VISIBLE
                            tv_name111.text = name
                            ImageLoadUtil.getInstance().loadImage(iv_head, header.addHost(), 40, 40)
                        }
                    }
                    setOnClickListener {
                        when (viewType) {
                            1 -> {
                                parent.context.nextActivity<SearchFriendActivity>(1001, "addGroup" to true)
                            }
                            2 -> {
                                list.forEach {
                                    it.isCheck = !it.isCheck
                                }
                                notifyDataSetChanged()
                            }
                        }
                    }
                    iv_del.setOnClickListener {
                        val activity = (it.context as TintContextWrapper).baseContext as ChatGroupActivity
                        if(getItem(position)?.id==activity.myUserid){
                            activity.showToast("不能删除自己")
                        }
                        RongIM.getInstance().removeMemberFromDiscussion(activity.chat_id, getItem(position)?.id.toString(), object : RongIMClient.OperationCallback() {
                            override fun onSuccess() {

                                activity.users = activity.users?.replace(Regex(getItem(position)?.id.toString()),"")
                                activity.getData()
                                activity.showToast("移除成功")
                            }

                            override fun onError(p0: RongIMClient.ErrorCode?) {
                                activity.showToast("添加失败")
                            }

                        })
                    }
                }

            }
            return convertView
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
                val userinfo = data.getSerializableExtra("userinfo") as? UserInfo
                RongIM.getInstance().addMemberToDiscussion(chat_id, mutableListOf(userinfo?.id.toString()), object : RongIMClient.OperationCallback() {
                    override fun onSuccess() {
                        users += "," + userinfo?.id
                        getData()
                        showToast("添加成功")
                    }

                    override fun onError(p0: RongIMClient.ErrorCode?) {
                        showToast("添加失败")
                    }

                })
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}


